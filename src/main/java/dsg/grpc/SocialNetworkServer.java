package dsg.grpc;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;
public class SocialNetworkServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        // Startet den Server auf Port 8080 mit deiner Service-Logik
        Server server = ServerBuilder.forPort(8080)
                .addService(new SocialNetworkServiceImpl())
                .build();

        server.start();
        System.out.println("gRPC Server gestartet...");
        server.awaitTermination();
    }
}