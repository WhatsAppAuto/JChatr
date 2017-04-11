package Chatr.Converstation.sharedCMU;

import Chatr.Helper.Enums.Status;
import Chatr.Helper.HashGen;

import java.util.Objects;

/**
 * Created by haags on 11.04.2017.
 */
public class User {

    private String userName = "";
    private String userID = HashGen.getID(false);
    public String userPicture = "";
    private String email = "";
    private String emailMD5 = "";
    private Status status;

    /**
     * Constructor to create user object with the user name.
     * @param userName The name of the user.
     */
    public User(String userName){
        this.userName = userName;
    }

    /**
     * This method is used to get the user name.
     * @return Returns the user name.
     */
    public String getUserName(){
        return userName;
    }

    /**
     * This method is used to set the path of the profile path.
     */
    public User setProfileImg(String imgPath){
        userPicture = imgPath;
        return this;
    }

    /**
     * This method is used to get the path of the profile picture.
     * @return Path of profile picture.
     */
    public String getProfileImg(){
        return userPicture;
    }

    /**
     * This method is used to set the user name.
     * @param newName The new user name.
     */
    public User setUserName(String newName){
        userName = newName;
        return this;
    }

    /**
     * This method is used to get the user ID.
     * @return Returns the user ID.
     */
    public String getUserID(){
        return userID;
    }

    public User setEmail(String email){
        this.email = email.toLowerCase();
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public User setStatus(Status status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return userID;
    }

    @Override
    public boolean equals(Object o) {
        return Objects.equals(userID, o.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID);
    }
}