package dsg.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import dsg.grpc.SocialNetworkServiceGrpc.SocialNetworkServiceBlockingStub;
import dsg.grpc.SocialNetworkProto.*;

public class SocialNetworkClient {

    private final ManagedChannel channel;
    private final SocialNetworkServiceBlockingStub stub;

    public SocialNetworkClient(String host, int port) {
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        this.stub = SocialNetworkServiceGrpc.newBlockingStub(channel);
    }

    public void createProfile(String userId, String firstname, String lastname, String bio) {
        stub.createProfile(CreateProfileRequest.newBuilder()
                .setUserId(userId)
                .setFirstname(firstname)
                .setLastname(lastname)
                .setBio(bio)
                .build());
    }

    public void getProfile (String userId){
        stub.getProfile(GetProfileRequest.newBuilder()
                .setUserId(userId)
                .build());
    }
    public void updateProfile (String userId, String firstname, String lastname, String bio) {
        stub.updateProfile(UpdateProfileRequest.newBuilder()
                .setUserId(userId)
                .setFirstname(firstname)
                .setLastname(lastname)
                .setBio(bio)
                .build());
    }
    public void publishPost (String userId, String content){
        stub.publishPost(PublishPostRequest.newBuilder()
                .setUserId(userId)
                .setContent(content)
                .build());
    }
    public void deletePost (String userId, int postId){
        stub.deletePost(DeletePostRequest.newBuilder()
                .setUserId(userId)
                .setPostId(postId)
                .build());
    }
    public void followStudent (String followerId, String targetId){
        stub.followStudent(FollowStudentRequest.newBuilder()
                .setFollowerId(followerId)
                .setTargetId(targetId)
                .build());
    }
    public void unfollowStudent (String followerId, String targetId){
        stub.unfollowStudent(UnfollowStudentRequest.newBuilder()
                .setFollowerId(followerId)
                .setTargetId(targetId)
                .build());
    }
    public GetFeedResponse getFeed(String userId) {
        return stub.getFeed(GetFeedRequest.newBuilder()
                .setUserId(userId)
                .build());
    }

    public void shutdown() {
        channel.shutdown();
    }

    public static void main(String[] args) {
        SocialNetworkClient client = new SocialNetworkClient("localhost", 8080);
        client.shutdown();
    }
}