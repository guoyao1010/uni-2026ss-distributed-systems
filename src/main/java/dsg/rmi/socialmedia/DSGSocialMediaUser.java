package dsg.rmi.socialmedia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DSGSocialMediaUser implements Serializable {

private static final long serialVersionUID = 1L; 
public String userID;
public String Firstname;
public String Lastname;
public String bio;
public List<DSGSocialMediaPost> posts = new ArrayList<> ();
public List <DSGSocialMediaUser> followedUsers = new ArrayList <> ();

public DSGSocialMediaUser (String userID, String Firstname, String Lastname, String bio) {
this.userID = userID;
this.Firstname= Firstname;
this.Lastname = Lastname;
this.bio = bio;

}
}