package dsg.rmi.socialmedia;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface DSGSocialMedia extends Remote {

    // Interfaces by Osama and Yao
    void createProfile (String userID, String firstname, String lastname, String bio) throws RemoteException;
    void updateProfile (String userID, String firstname, String lastname, String bio) throws RemoteException;
    void publishPost (String userID, String content) throws RemoteException;
    DSGSocialMediaUser getProfile (String userID) throws RemoteException;
    void deletePost (String userID, String postID) throws RemoteException;
    List<DSGSocialMediaPost> getFeed (String userID) throws RemoteException;
    void followStudent(String followerID, String targetID) throws RemoteException;
    void unfollowStudent(String followerID, String targetID) throws RemoteException;

}