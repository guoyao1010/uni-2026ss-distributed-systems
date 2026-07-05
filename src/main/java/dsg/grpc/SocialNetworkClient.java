package dsg.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import dsg.grpc.SocialNetworkServiceGrpc.SocialNetworkServiceBlockingStub;
import dsg.grpc.SocialNetworkProto.*;
import dsg.DSGSocialMediaClient;
import java.util.List;
import java.util.ArrayList;

public class SocialNetworkClient extends DSGSocialMediaClient {

    private ManagedChannel channel;
    private SocialNetworkServiceBlockingStub stub;

    public SocialNetworkClient( ) { }

    // #############################
    // # INITIALIZATION & SHUTDOWN #
    // #############################

    @Override
    public void init (String host, int port) {
        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        stub = SocialNetworkServiceGrpc.newBlockingStub(channel);
        System.out.println("gRPC client initialized.");
    }

    @Override
    public void shutdown () {
        channel.shutdown();
        System.out.println("gRPC client shutdown.");
    }

    @Override
    protected void createProfile (String userID, String firstname, String lastname, String bio) {
        CreateProfileRequest request = CreateProfileRequest.newBuilder()
                .setUserId(userID)
                .setFirstname(firstname)
                .setLastname(lastname)
                .setBio(bio)
                .build();
        stub.createProfile(request);
    }

    @Override
    protected String getProfile (String userID) {
        GetProfileRequest request = GetProfileRequest.newBuilder()
                .setUserId(userID)
                .build();
        GetProfileResponse response = stub.getProfile(request);
        return response.getFirstname() + " " + response.getLastname() + " " + response.getBio();
    }

    @Override
    protected void updateProfile (String userID, String firstname, String lastname, String bio) {
        UpdateProfileRequest request = UpdateProfileRequest.newBuilder()
                .setUserId(userID)
                .setFirstname(firstname)
                .setLastname(lastname)
                .setBio(bio)
                .build();
        stub.updateProfile(request);
    }

    @Override
    protected void publishPost (String userID, String content) {
        PublishPostRequest request = PublishPostRequest.newBuilder()
                .setUserId(userID)
                .setContent(content)
                .build();
        stub.publishPost(request);
    }

    @Override
    protected void deletePost (String userID, String postID) {
        DeletePostRequest request =DeletePostRequest.newBuilder()
                .setUserId(userID)
                .setPostId(postID)
                .build();
        stub.deletePost(request);
    }

    @Override
    protected void followStudent (String followerID, String targetID) {
        FollowStudentRequest request = FollowStudentRequest.newBuilder()
                .setFollowerId(followerID)
                .setTargetId(targetID)
                .build();
        stub.followStudent(request);
    }

    @Override
    protected void unfollowStudent (String followerID, String targetID) {
        UnfollowStudentRequest request = UnfollowStudentRequest.newBuilder()
                .setFollowerId(followerID)
                .setTargetId(targetID)
                .build();
        stub.unfollowStudent(request);
    }

    @Override
    protected List<String> getFeed (String userID) {
        GetFeedRequest request = GetFeedRequest.newBuilder()
                .setUserId(userID)
                .build();
        GetFeedResponse response = stub.getFeed(request);
        List<String> feed = new ArrayList<>();
        for (Post post: response.getPostsList()) {
            feed.add(post.getPostId() + " " + post.getContent());
        }
        return feed;
    }

    @Override
    protected List<String> getPosts (String userID) {
        GetPostsRequest request = GetPostsRequest.newBuilder()
                .setUserId(userID)
                .build();
        GetPostsResponse response = stub.getPosts(request);
        List<String> postsresult = new ArrayList<>();
        for (Post post: response.getPostsList()) {
            postsresult.add(post.getPostId() + " " + post.getContent());
        }
        return postsresult;
    }

    // ########
    // # MAIN #
    // ########

    public static void main(String[] args) throws Exception {
        SocialNetworkClient client = new SocialNetworkClient();
        client.init("localhost", 8080);
        client.shell();
        client.shutdown();
    }
}