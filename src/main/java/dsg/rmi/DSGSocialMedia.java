package dsg.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Leansho extends Remote {

void createProfile (String userID, String name) throws RemoteException;
void publishpost (String userID, String content) throws RemoteException;
UserProfile getProfile (String userID) throws RemoteException;
void deletePost (String postID) throws RemoteException;
List<Post> getFeed (String userID) throws RemoteException;
void followStudent(String followerID, String targetID) throws RemoteException;
void unfollowStudent(String followerID, String targetID) throws RemoteException;

}