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
    private final Map<String, List<DSGSocialMediaPost>> userPosts = new HashMap<>();
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
            userPosts.put(userID, new ArrayList<>());
            following.put(userID, new HashSet<>());
        }
    }

    // Method updateProfile by Osama
    @Override
    public synchronized void updateProfile(String userID, String firstname, String lastname, String bio) throws RemoteException {
        if (profiles.containsKey(userID)) {
            profiles.put(userID, new DSGSocialMediaUser(userID, firstname, lastname, bio));
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
            userPosts.get(userID).add(newPost);
        }
    }

    // Method deletePost by Yao
    @Override
    public synchronized void deletePost (String userID, String postID) throws RemoteException {
        List<DSGSocialMediaPost> postsOfUser = userPosts.get(userID);
        if (postsOfUser == null) {
            throw new RemoteException("This user does not exist: " + userID);
        }
        DSGSocialMediaPost postToDelete = null;
        for (DSGSocialMediaPost post: postsOfUser) {
            if (post.postID.equals(postID)) {
                postToDelete = post;
                break;
            }
        }
        if (postToDelete == null) {
            throw new RemoteException("This post does not exist.");
        }
        postsOfUser.remove(postToDelete);
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
            throw new RemoteException("This user does not exist: " + userID);
        }
        List<DSGSocialMediaPost> feed = new ArrayList<>();
        Set<String> followedStudents = following.get(userID);

        for (String followedUserID: followedStudents) {
            feed.addAll(userPosts.get(followedUserID));
        }
        feed.sort((post1, post2) -> Integer.compare(Integer.parseInt(post2.postID), Integer.parseInt(post1.postID)));
        return feed;
    }

    // Method getPosts by Yao
    @Override
    public synchronized List<DSGSocialMediaPost> getPosts (String userID) throws RemoteException {
        if (!profiles.containsKey(userID)) {
            throw new RemoteException("This user does not exist.");
        }
        List<DSGSocialMediaPost> postsResult = new ArrayList<>(userPosts.get(userID));
        postsResult.sort((post1, post2) -> Integer.compare(Integer.parseInt(post2.postID), Integer.parseInt(post1.postID)));
        return postsResult;
    }
}