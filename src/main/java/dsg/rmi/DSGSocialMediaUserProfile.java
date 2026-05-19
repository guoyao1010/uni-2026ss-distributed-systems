package dsg.rmi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserProfile implements Serializable {

private static final long serialVersionUID = 1L; 
public String userID;
public String Vorname;
public String Nachname;
public String bio;
public List<Post> posts = new ArrayList<> ();

public UserProfile (String userID, String Vorname, String Nachname, String bio) {
this.userID = userID;
this.Vorname= Vorname;
this.Nachname = Nachname;
this.bio = bio;

}
}