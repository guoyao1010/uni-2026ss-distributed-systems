package dsg.grpc;

import io.grpc.stub.StreamObserver;

public class SocialNetworkServiceImpl extends SocialNetworkServiceGrpc.SocialNetworkServiceImplBase {

    @Override
    public void createProfile(CreateProfileRequest request, StreamObserver<EmptyResponse> responseObserver) {

        System.out.println("Create profile request received for user: " + request.getUserId());

        EmptyResponse response = EmptyResponse.newBuilder().build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}