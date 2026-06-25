package dsg.rmi.socialmedia;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class DSGSocialMediaServer {

    public static void main(String[] args) throws Exception {

        //1. Create the social media implementation
        DSGSocialMediaImplement service = new DSGSocialMediaImplement();

        // 2. Export the remote object
        DSGSocialMedia stub = (DSGSocialMedia) UnicastRemoteObject.exportObject(service, 0);

        // 3. Create the RMI registry
        Registry registry = LocateRegistry.createRegistry(1099);

        // 4. Register the remote object
        registry.rebind("SocialMedia", stub);

        System.out.println("SocialMedia RMI Server is running.");
    }
}
