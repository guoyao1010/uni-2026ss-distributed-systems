package dsg.rmi.socialmedia;
import java.io.Serializable;

public class DSGSocialMediaPost implements Serializable {

private static final long serialVersionUID = 1L;
public String postID;
public String userID;
public String content;
public long timestamp;

public DSGSocialMediaPost (String userID, String content){
this.userID = userID;
this.content= content;
this.timestamp = System.currentTimeMillis();
}
}