package dsg.rmi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

private static final long serialVersionUID = 1L; 
public String userID;
public String Firstname;
public String Lastname;
public String bio;
public List<Post> posts = new ArrayList<> ();
public List <User> followedUser = new ArrayList <> ();

public User (String userID, String Firstname, String Lastname, String bio) {
this.userID = userID;
this.Firstname= Firstname;
this.Lastname = Lastname;
this.bio = bio;

}
}