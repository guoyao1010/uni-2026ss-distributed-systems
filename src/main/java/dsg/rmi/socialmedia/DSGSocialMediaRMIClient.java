package dsg.rmi.socialmedia;

import  java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.ArrayList;
import dsg.DSGSocialMediaClient;

public class DSGSocialMediaRMIClient extends DSGSocialMediaClient{

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
        System.out.println("RMIClient shutdown.");
    }

    @Override
    protected void createProfile (String userID, String firstname, String lastname, String bio) throws Exception {
        socialMedia.createProfile(userID, firstname, lastname, bio);
    }

    @Override
    protected String getProfile (String userID) throws Exception {
        DSGSocialMediaUser user = socialMedia.getProfile(userID);
        return user.firstname + " " + user.lastname + " " + user.bio;
    }

    @Override
    protected void updateProfile (String userID, String firstname, String lastname, String bio) throws Exception {
        socialMedia.updateProfile(userID, firstname, lastname, bio);
    }

    @Override
    protected void publishPost (String userID, String content) throws Exception {
        socialMedia.publishPost(userID, content);
    }

    @Override
    protected void deletePost (String userID, String postID) throws Exception {
        socialMedia.deletePost(userID, postID);
    }

    @Override
    protected void followStudent (String followerID, String targetID) throws Exception {
        socialMedia.followStudent(followerID, targetID);
    }

    @Override
    protected void unfollowStudent (String followerID, String targetId) throws Exception {
        socialMedia.unfollowStudent(followerID, targetId);
    }

    @Override
    protected List<String> getFeed (String userID) throws Exception {
        List<DSGSocialMediaPost> posts = socialMedia.getFeed(userID);
        List<String> feed = new ArrayList<>();
        for (DSGSocialMediaPost post : posts) {
            feed.add(post.userID + " " +post.postID + " " + post.content);
        }
        return feed;
    }

    @Override
    protected List<String> getPosts (String userID) throws Exception {
        List<DSGSocialMediaPost> posts = socialMedia.getPosts(userID);
        List<String> postsresult = new ArrayList<>();
        for (DSGSocialMediaPost post: posts) {
            postsresult.add(post.postID + " " +post.content);
        }
        return postsresult;
    }

    // ########
    // # MAIN #
    // ########

    public static void main(String[] args) throws Exception {
        DSGSocialMediaRMIClient client = new DSGSocialMediaRMIClient();
        client.init(args[0], 1099);
        client.shell();
        client.shutdown();
    }
}
