package dsg;

import dsg.rmi.socialmedia.DSGShell;
import java.util.List;

import dsg.rmi.socialmedia.DSGSocialMediaRMIClient;
import dsg.grpc.SocialNetworkClient;

import java.util.Scanner;

public abstract class DSGSocialMediaClient extends DSGShell {
    public abstract void init (String host, int port) throws Exception;
    public abstract void shutdown ();


    protected abstract void createProfile (String userID, String firstname, String lastname, String bio) throws Exception;
    protected abstract String getProfile (String userID) throws Exception;
    protected abstract void updateProfile (String userID, String firstname, String lastname, String bio) throws Exception;
    protected abstract void publishPost (String userID, String content) throws Exception;
    protected abstract void deletePost (String userID, String postID) throws Exception;
    protected abstract void followStudent (String followerID, String targetID) throws Exception;
    protected abstract void unfollowStudent (String followerID, String targetID) throws Exception;
    protected abstract List<String> getFeed (String userID) throws Exception;

    @Override
    protected boolean processCommand (String[] args) {
        try {
            if (args[0].equals("createprofile")) {
                createProfile(args[1], args[2], args[3], args[4]);
                System.out.println("Profile created.");
            }

            if (args[0].equals("getprofile")) {
                String profile = getProfile(args[1]);
                System.out.println(profile);
            }

            if (args[0].equals("updateprofile")) {
                updateProfile(args[1], args[2], args[3], args[4]);
                System.out.println("Profile updated.");
            }

            if (args[0].equals("publishpost")) {
                publishPost(args[1], args[2]);
                System.out.println("Post published.");
            }

            if (args[0].equals("deletepost")) {
                deletePost(args[1], args[2]);
                System.out.println("Post deleted.");
            }

            if (args[0].equals("followstudent")) {
                followStudent(args[1], args[2]);
                System.out.println("Student followed.");
            }

            if (args[0].equals("unfollowstudent")) {
                unfollowStudent(args[1], args[2]);
                System.out.println("Student unfollowed.");
            }

            if (args[0].equals("getfeed")) {
                List<String> feed = getFeed(args[1]);
                if (feed.isEmpty()) {
                    System.out.println("Student you are following did not publish anythin yet.");
                }
                for (String post: feed) {
                    System.out.println(post);
                }
            }

            if (args[0].equals("exit")) {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static void main (String[] args) throws Exception {
        DSGSocialMediaClient client;
        String type;

        if (args.length > 0) {
            type = args[0];
        } else {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Choose client type: rmi or grpc.");
            System.out.print("> ");
            type = scanner.nextLine();
        }

        if (type.equals("rmi")) {
            client = new DSGSocialMediaRMIClient();
            client.init("h5129.pi.uni-bamberg.de", 1099);
            System.out.println("You are using the RMI client.");
        } else if (type.equals("grpc")) {
            client = new SocialNetworkClient();
            client.init("h5129.pi.uni-bamberg.de", 8080);
            System.out.println("You are using the gRPC client.");
        } else {
            System.out.println("Please run this file again and choose the client type first: rmi or grpc.");
            return;
        }

        client.shell();
        client.shutdown();
    }
}
