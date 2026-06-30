package dsg.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import dsg.grpc.SocialNetworkServiceGrpc.SocialNetworkServiceBlockingStub;
import dsg.grpc.SocialNetworkProto.*;

public class SocialNetworkClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext()
                .build();

        SocialNetworkServiceBlockingStub stub = SocialNetworkServiceGrpc.newBlockingStub(channel);

        CreateProfileRequest request = CreateProfileRequest.newBuilder()
                .setUserId("user123")
                .setName("Osama")
                .setBio("Bamberg Student")
                .build();

        stub.createProfile(request);
        System.out.println("Anfrage erfolgreich gesendet!");

        channel.shutdown();
    }
}