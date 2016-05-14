package macmain.co.za.siyikhipha;

import java.io.Serializable;

/**
 * Created by ProJava on 12/26/2015.
 */
public class User implements Serializable {

    private String username;
    private String email;


    public User(){

    }


    public User(String username, String email){

        this.username = username;
        this.email = email;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
