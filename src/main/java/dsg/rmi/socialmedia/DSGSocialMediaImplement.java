package dsg.rmi.socialmedia;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;


public class DSGSocialMediaImplement implements DSGSocialMedia {

    private final Map<String, DSGSocialMediaUser> profiles = new HashMap<>();
    private final Map<String, DSGSocialMediaPost> posts = new HashMap<>();
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
            profile.firstname = firstname;
            profile.lastname = lastname;
            profile.bio = bio;
        }
    }

    // Method getProfile by Osama
    @Override
    public synchronized DSGSocialMediaUser getProfile (String userID) throws RemoteException{
        return profiles.get (userID);
    }

    // Method publishPost by Yao and Osama
    @Override
    public synchronized void publishPost (String userID, String content) throws RemoteException{
        DSGSocialMediaUser profile = profiles.get(userID) ;
        if (profile != null) {
            postCounter ++;
            DSGSocialMediaPost newPost= new DSGSocialMediaPost(userID, content);
            newPost.postID = String.valueOf(postCounter);
            posts.put(newPost.postID, newPost);
        }
    }

    // Method deletePost by Yao
    @Override
    public synchronized void deletePost (String userID, String postID) throws RemoteException {
        if (!posts.containsKey(postID)) {
            throw new RemoteException("This post does not exist.");
        }

        DSGSocialMediaPost post = posts.get(postID);
        if (!post.userID.equals(userID)) {
            throw new RemoteException("You can only delete your own post.");
        }
        posts.remove(postID);
    }

    // Method followStudent by Yao
    @Override
    public synchronized void followStudent(String followerID, String targetID) throws RemoteException {
        if (!profiles.containsKey(followerID)) {
            throw new RemoteException("This ID does not exist: " + followerID);// or maybe create your own profile first
        }
        if (!profiles.containsKey(targetID)) {
            throw new RemoteException("Target student does not exist: " + targetID);
        }
        if (followerID.equals(targetID)) {
            throw new RemoteException("Sorry, you cannot follow yourself.");
        }
        following.putIfAbsent(followerID, new HashSet<>());
        Set<String> followedStudents = following.get(followerID);
        followedStudents.add(targetID);
    }

    // Method unfollowStudent by Yao
    @Override
    public synchronized void unfollowStudent(String followerID, String targetID) throws RemoteException {
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

    // Method getFeed by Yao
    @Override
    public synchronized List<DSGSocialMediaPost> getFeed (String userID) throws RemoteException {
        if (!profiles.containsKey(userID)) {
            throw new RemoteException("User does not exist: " + userID);
        }
        List<DSGSocialMediaPost> feed = new ArrayList<>();
        Set<String> followedStudents = following.get(userID);
        if (followedStudents == null) {
            return feed;
        }
        for (DSGSocialMediaPost post : posts.values()) {
            if (followedStudents.contains(post.userID)) {
                feed.add(post);
            }
        }
        feed.sort((post1, post2) -> Integer.compare(Integer.parseInt(post2.postID), Integer.parseInt(post1.postID)));
        return feed;
    }

}