package dsg.grpc;

import io.grpc.stub.StreamObserver;
import java.util.*;

public class SocialNetworkServiceImpl extends SocialNetworkServiceGrpc.SocialNetworkServiceImplBase {

    /*
    Change to standard collection and a regular int instead of concurrent collection and AtomicInteger.
    Purpose: to keep the server-side implementations of RMI and gRPC consistent for a fair benchmark. Otherwise, the benchmark will also measure the overhead of concurrent data structure rather than the primary gRPC framework.
    Thread safety is instead ensured by synchronizing each method so that only one thread executes a method at a time.
     */
    private final Map<String, ProfileData> profiles = new HashMap<>();
    private final Map<String, List<Post>> userPosts = new HashMap<>();
    private final Map<String, Set<String>> following = new HashMap<>();
    private int postIdCounter = 0;

    private static class ProfileData {
        String firstname;
        String lastname;
        String bio;
        ProfileData(String firstname, String lastname, String bio) { this.firstname = firstname; this.lastname = lastname; this.bio = bio; }
    }

    @Override
    public synchronized void createProfile(CreateProfileRequest request, StreamObserver<EmptyResponse> responseObserver) {
        if (!profiles.containsKey(request.getUserId())) {
            profiles.put(request.getUserId(), new ProfileData(
                    request.getFirstname(),
                    request.getLastname(),
                    request.getBio()
            ));
            userPosts.putIfAbsent(request.getUserId(), new ArrayList<>());
            following.putIfAbsent(request.getUserId(), new HashSet<>());
        }
        responseObserver.onNext(EmptyResponse.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public synchronized void updateProfile(UpdateProfileRequest request, StreamObserver<EmptyResponse> responseObserver) {
        if (profiles.containsKey(request.getUserId())) {
            profiles.put(request.getUserId(), new ProfileData(request.getFirstname(),request.getLastname(), request.getBio()));
        }
        responseObserver.onNext(EmptyResponse.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public synchronized void getProfile(GetProfileRequest request, StreamObserver<GetProfileResponse> responseObserver) {
        ProfileData p = profiles.get(request.getUserId());
        GetProfileResponse.Builder response = GetProfileResponse.newBuilder();
        if (p != null) {
            response.setFirstname(p.firstname).setLastname(p.lastname).setBio(p.bio);
        }
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }

    @Override
    public synchronized void publishPost(PublishPostRequest request, StreamObserver<EmptyResponse> responseObserver) {
        List<Post> posts = userPosts.get(request.getUserId());
        if (posts != null) {
            postIdCounter++;
            Post newPost = Post.newBuilder()
                    .setPostId(String.valueOf(postIdCounter))
                    .setContent(request.getContent())
                    .build();
            posts.add(newPost);
        }
        responseObserver.onNext(EmptyResponse.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public synchronized void deletePost(DeletePostRequest request, StreamObserver<EmptyResponse> responseObserver) {
        List<Post> posts = userPosts.get(request.getUserId());
        if (posts != null) {
            Post postToDelete = null;
            for (Post post: posts) {
                if (post.getPostId().equals(request.getPostId())) {
                    postToDelete = post;
                    break;
                }
            }
            if (postToDelete != null) {
                posts.remove(postToDelete);
            }
        }
        responseObserver.onNext(EmptyResponse.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public synchronized void followStudent(FollowStudentRequest request, StreamObserver<EmptyResponse> responseObserver) {
        Set<String> f = following.get(request.getFollowerId());
        if (f != null && profiles.containsKey(request.getTargetId()) && !request.getFollowerId().equals(request.getTargetId())) {
            f.add(request.getTargetId());
        }
        responseObserver.onNext(EmptyResponse.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public synchronized void unfollowStudent(UnfollowStudentRequest request, StreamObserver<EmptyResponse> responseObserver) {
        if (profiles.containsKey(request.getFollowerId()) && profiles.containsKey(request.getTargetId())) {
            Set<String> f = following.get(request.getFollowerId());
            if (f != null && f.contains(request.getTargetId())) {
                f.remove(request.getTargetId());
            }
        }
        responseObserver.onNext(EmptyResponse.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public synchronized void getFeed(GetFeedRequest request, StreamObserver<GetFeedResponse> responseObserver) {
        GetFeedResponse.Builder response = GetFeedResponse.newBuilder();
        Set<String> followedUsers = following.get(request.getUserId());

        if (followedUsers != null) {
            List<Post> feedPosts = new ArrayList<>();
            for (String targetId : followedUsers) {
                List<Post> posts = userPosts.get(targetId);
                if (posts != null) {
                    feedPosts.addAll(posts);
                }
            }
            feedPosts.sort((p1, p2) -> Integer.compare(Integer.parseInt(p2.getPostId()), Integer.parseInt(p1.getPostId())));
            response.addAllPosts(feedPosts);
        }

        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }

    @Override
    public synchronized void getPosts (GetPostsRequest request, StreamObserver<GetPostsResponse> responseObserver) {
        GetPostsResponse.Builder response = GetPostsResponse.newBuilder();
        List<Post> posts = userPosts.get(request.getUserId());
        if (posts != null) {
            List<Post> postsResult = new ArrayList<>(posts);
            postsResult.sort((post1, post2) -> Integer.compare(Integer.parseInt(post2.getPostId()), Integer.parseInt(post1.getPostId())));
            response.addAllPosts(postsResult);
        }
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }
}