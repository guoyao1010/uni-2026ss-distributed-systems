package dsg.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class DSGSocialMediaImplement extends UnicastRemoteObject implements DSGSocialMedia {
    
    private final Map<String, UserProfile> profiles = new HashMap<>();
    
    private final Map<String, List<String>> following = new HashMap<>();
	private int postCounter = 0;

    public DSGSocialMediaImplement() throws RemoteException {
        super();
    }
@Override
public synchronized void createProfile (String userID, String firstname, String lastname, String bio) throws RemoteException{
if (!profiles.containsKey (userID)) {
	DSGSocialMediaUser newProfile = new DSGSocialMediaUser (userID, firstname, lastname, bio);
	profiles.put (userID, newProfile);
	following.put(userID, new ArrayList<>();
}
}
@Override
public synchronized void updateProfile(String userID, String bio, String firstname, String lastname) throws RemoteException {
DSGSocialMediaUser profile = profiles.get(userID)
if (profile != null) {
	if ( bio != null){
		profile.bio = bio;}
	if(firstname != null){
		profile.firstname = firstname;}
	if (lastname != null) {
		profile.lastname = lastname;}

}
}
@Override
public synchronized DSGSocialMediaUser getProfile (String userID) throws RemoteException{
return profiles.get (userID);
}

@Override
public synchronized void publishPost (String userID, String Content) throws RemoteException{
DSGSocialMediaUser profile = profiles.get(userID) ;
if (profile != null) {
postCounter ++;
DSGSocialMediaPost newPost= new DSGSocialMediaPost(userID, Content);
newPost.postId = "P" + postCounter;
Profile.posts.add(newPost);
}
}

}