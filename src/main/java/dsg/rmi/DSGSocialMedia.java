package dsg.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface DSGSocialMedia extends Remote {

void createProfile (String userID, String Firstname, String Lastname) throws RemoteException;
void publishpost (String userID, String content) throws RemoteException;
User getProfile (String userID) throws RemoteException;
void deletePost (String postID) throws RemoteException;
List<Post> getFeed (String userID) throws RemoteException;
void followStudent(String followerID, String targetID) throws RemoteException;
void unfollowStudent(String followerID, String targetID) throws RemoteException;

}