package dsg.grpc;

import io.grpc.stub.StreamObserver;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class SocialNetworkServiceImpl extends SocialNetworkServiceGrpc.SocialNetworkServiceImplBase {

    private final Map<String, ProfileData> profiles = new ConcurrentHashMap<>();
    private final Map<String, List<Post>> userPosts = new ConcurrentHashMap<>();
    private final Map<String, Set<String>> following = new ConcurrentHashMap<>();
    private final AtomicInteger postIdCounter = new AtomicInteger(1);

    private static class ProfileData {
        String firstname;
        String lastname;
        String bio;
        ProfileData(String firstname, String lastname, String bio) { this.firstname = firstname; this.lastname = lastname; this.bio = bio; }
    }

    @Override
    public void createProfile(CreateProfileRequest request, StreamObserver<EmptyResponse> responseObserver) {
        profiles.put(request.getUserId(), new ProfileData(request.getFirstname(),request.getLastname(), request.getBio()));
        userPosts.putIfAbsent(request.getUserId(), new CopyOnWriteArrayList<>());
        following.putIfAbsent(request.getUserId(), ConcurrentHashMap.newKeySet());

        responseObserver.onNext(EmptyResponse.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void updateProfile(UpdateProfileRequest request, StreamObserver<EmptyResponse> responseObserver) {
        if (profiles.containsKey(request.getUserId())) {
            profiles.put(request.getUserId(), new ProfileData(request.getFirstname(),request.getLastname(), request.getBio()));
        }
        responseObserver.onNext(EmptyResponse.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void getProfile(GetProfileRequest request, StreamObserver<GetProfileResponse> responseObserver) {
        ProfileData p = profiles.get(request.getUserId());
        GetProfileResponse.Builder response = GetProfileResponse.newBuilder();
        if (p != null) {
            response.setFirstname(p.firstname).setLastname(p.lastname).setBio(p.bio);
        }
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }

    @Override
    public void publishPost(PublishPostRequest request, StreamObserver<EmptyResponse> responseObserver) {
        List<Post> posts = userPosts.get(request.getUserId());
        if (posts != null) {
            Post newPost = Post.newBuilder()
                    .setPostId(String.valueOf(postIdCounter.getAndIncrement()))
                    .setContent(request.getContent())
                    .build();
            posts.add(0, newPost);
        }
        responseObserver.onNext(EmptyResponse.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void deletePost(DeletePostRequest request, StreamObserver<EmptyResponse> responseObserver) {
        List<Post> posts = userPosts.get(request.getUserId());
        if (posts != null) {
            posts.removeIf(post -> String.valueOf(post.getPostId()).equals(request.getPostId()));
        }
        responseObserver.onNext(EmptyResponse.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void followStudent(FollowStudentRequest request, StreamObserver<EmptyResponse> responseObserver) {
        Set<String> f = following.get(request.getFollowerId());
        if (f != null && profiles.containsKey(request.getTargetId())) {
            f.add(request.getTargetId());
        }
        responseObserver.onNext(EmptyResponse.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void unfollowStudent(UnfollowStudentRequest request, StreamObserver<EmptyResponse> responseObserver) {
        Set<String> f = following.get(request.getFollowerId());
        if (f != null) {
            f.remove(request.getTargetId());
        }
        responseObserver.onNext(EmptyResponse.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void getFeed(GetFeedRequest request, StreamObserver<GetFeedResponse> responseObserver) {
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
}