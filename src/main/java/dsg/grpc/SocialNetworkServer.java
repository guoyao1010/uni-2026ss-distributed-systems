package dsg.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;

public class SocialNetworkServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(8080)
                .addService(new SocialNetworkServiceImpl())
                .build();

        server.start();
        System.out.println("gRPC Server connected...");
        server.awaitTermination();
    }
}