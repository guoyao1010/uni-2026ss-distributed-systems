package dsg.rmi.socialmedia;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;


public class DSGSocialMediaImplement implements DSGSocialMedia {

    // In-memory database:
    // profiles stores all user profiles
    private final Map<String, DSGSocialMediaUser> profiles = new HashMap<>();
    // posts stores all published posts
    private final Map<String, DSGSocialMediaPost> posts = new HashMap<>();
    // following stores who follows whom
    private final Map<String, Set<String>> following = new HashMap<>();
	private int postCounter = 0;

    public DSGSocialMediaImplement() throws RemoteException {
        super();
    }

    // Method createProfile by Osama
    @Override
    public synchronized void createProfile (String userID, String firstname, String lastname, String bio) throws RemoteException{
        if (!profiles.containsKey (userID)) {
            DSGSocialMediaUser newProfile = new DSGSocialMediaUser (userID, firstname, lastname, bio);
            profiles.put (userID, newProfile);
            following.put(userID, new HashSet<>());
        }
    }

    // Method updateProfile by Osama
    @Override
    public synchronized void updateProfile(String userID, String firstname, String lastname, String bio) throws RemoteException {
        DSGSocialMediaUser profile = profiles.get(userID);
        if (profile != null) {
            if ( bio != null){
                profile.bio = bio;}
            if(firstname != null){
                profile.firstname = firstname;}
            if (lastname != null) {
                profile.lastname = lastname;}

        }
    }

    // Method getProfile by Osama
    @Override
    public synchronized DSGSocialMediaUser getProfile (String userID) throws RemoteException{
        return profiles.get (userID);
    }

    // Method publishPost by Yao and Osama
    @Override
    public synchronized void publishPost (String userID, String Content) throws RemoteException{
        DSGSocialMediaUser profile = profiles.get(userID) ;
        if (profile != null) {
            postCounter ++;
            DSGSocialMediaPost newPost= new DSGSocialMediaPost(userID, Content);
            newPost.postID = "P" + postCounter;
            posts.put(newPost.postID, newPost);
        }
    }

    // Method deletePost by Yao
    public void deletePost (String postID) throws RemoteException {
        if (!posts.containsKey(postID)) {
            throw new RemoteException("This post does not exist.");
        }
        posts.remove(postID);
    }

    // Method getFeed by Yao
    public List<DSGSocialMediaPost> getFeed (String userID) throws RemoteException {
        if (!profiles.containsKey(userID)) {
            throw new RemoteException("User does not exist: " + userID);
        }
        List<DSGSocialMediaPost> feed = new ArrayList<>(); //create an empty list for the feed
        Set<String> followedStudents = following.get(userID); //get all the users set that current userID follows
        if (followedStudents == null) {
            return feed;  //feed is empty
        }
        for (DSGSocialMediaPost post : posts.values()) {
            if (followedStudents.contains(post.userID)) {
                feed.add(post);
            }
        }
        return feed;
    }

    // Method followStudent by Yao
    public void followStudent(String followerID, String targetID) throws RemoteException {
        if (!profiles.containsKey(followerID)) {
            throw new RemoteException("This ID does not exist: " + followerID);// or maybe create your own profile first
        }
        if (!profiles.containsKey(targetID)) {
            throw new RemoteException("Target student does not exist: " + targetID);
        }
        if (followerID.equals(targetID)) {
            throw new RemoteException("Sorry, you cannot follow yourself.");
        }
        // create an empty following set, if the user hasn't followed anyone
        following.putIfAbsent(followerID, new HashSet<>());

        // followedStudents stores the set of all followed students
        Set<String> followedStudents = following.get(followerID);

        followedStudents.add(targetID);
    }

    // Method unfollowStudent by Yao
    public void unfollowStudent(String followerID, String targetID) throws RemoteException {
        if (!profiles.containsKey(followerID)) {
            throw new RemoteException("Follower does not exist: " + followerID);
        }
        if (!profiles.containsKey(targetID)) {
            throw new RemoteException("Target student does not exist: " + targetID);
        }

        if (!following.containsKey(followerID)) {
            throw new RemoteException(followerID + " does not follow anyone.");
        }
        Set<String> followedStudents = following.get(followerID);

        if (!followedStudents.contains(targetID)) {
            throw new RemoteException(followerID + " does not follow " + targetID);
        }

        followedStudents.remove(targetID);
    }

}