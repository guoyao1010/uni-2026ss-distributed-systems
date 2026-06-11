package dsg.rmi.socialmedia;
import java.io.Serializable;

public class DSGSocialMediaUser implements Serializable {

    private static final long serialVersionUID = 1L;
    public String userID;
    public String firstname;
    public String lastname;
    public String bio;

    public DSGSocialMediaUser (String userID, String firstname, String lastname, String bio) {
        this.userID = userID;
        this.firstname= firstname;
        this.lastname = lastname;
        this.bio = bio;
    }
}