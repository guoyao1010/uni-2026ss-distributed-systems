package dsg.rmi.socialmedia;

import  java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class DSGSocialMediaRMIClient extends DSGShell{

    private DSGSocialMedia socialMedia;

    // #############################
    // # INITIALIZATION & SHUTDOWN #
    // #############################

    public void init(String registryHost, int registryPort) throws Exception {
        // 1. Connect the registry
        Registry registry = LocateRegistry.getRegistry(registryHost, registryPort);

        // 2. lookup SocialMedia stub
        socialMedia = (DSGSocialMedia) registry.lookup("SocialMedia");
        System.out.println("Connected to Social Media RMI server.");
    }


    public void shutdown() {
        System.out.println("Client shutdown.");
    }


    // ##################
    // # CLIENT METHODS #
    // ##################



    // #########
    // # SHELL #
    // #########

    @Override
    protected boolean processCommand(String[] args) {

        try {
            if (args[0].equals("createprofile")) {
                socialMedia.createProfile(args[1], args[2], args[3], args[4]);
                System.out.println("Profile created.");
            }

            if (args[0].equals("getprofile")) {
                DSGSocialMediaUser user = socialMedia.getProfile(args[1]);
                System.out.println(user.firstname+ " " +user.lastname + " " + user.bio);
            }

            if (args[0].equals("updateprofile")) {
                socialMedia.updateProfile(args[1], args[2], args[3], args[4]);
                System.out.println("Profile updated.");
            }

            if (args[0].equals("publishpost")) {
                socialMedia.publishPost(args[1], args[2]);
                System.out.println("Post published.");
            }

            if (args[0].equals("deletepost")) {
                socialMedia.deletePost(args[1], args[2]);
                System.out.println("Post deleted.");
            }

            if (args[0].equals("getfeed")) {
                List<DSGSocialMediaPost> feed = socialMedia.getFeed(args[1]);

                if (feed.isEmpty()) {
                    System.out.println("Students you are following did not publish anything yet.");
                }

                for (DSGSocialMediaPost post : feed) {
                    System.out.println(post.userID + " " + post.postID + " " + post.content);
                }
            }

            if (args[0].equals("followstudent")) {
                socialMedia.followStudent(args[1], args[2]);
                System.out.println("Student followed.");
            }

            if (args[0].equals("unfollowstudent")) {
                socialMedia.unfollowStudent(args[1], args[2]);
                System.out.println("Student unfollowed.");
            }

            if (args[0].equals("exit")) {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }


    // ########
    // # MAIN #
    // ########

    public static void main(String[] args) throws Exception {
        DSGSocialMediaRMIClient client = new DSGSocialMediaRMIClient();
        client.init("h5129.pi.uni-bamberg.de", 1099);
        client.shell();
        client.shutdown();
    }
}
