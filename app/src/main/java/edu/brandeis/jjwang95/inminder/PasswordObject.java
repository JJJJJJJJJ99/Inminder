package edu.brandeis.jjwang95.inminder;

/**
 * Created by WangJingjing on 11/13/16.
 */

public class PasswordObject {
    long id;
    String website;
    String email;
    String password;

    public PasswordObject(){

    }
    public PasswordObject(String website, String email, String password){
        this.website = website;
        this.email = email;
        this.password = password;
    }

    // Setters ####################
    public void setId(long id) {
        this.id = id;
    }
    public void setWebsite(String website){
        this.website = website;
    }
    public void setEmail(String email) {this.email = email;}
    public void setPassword(String password){
        this.password = password;
    }

    // Getters ####################
    public long getId(){
        return id;
    }
    public String getWebsite(){
        return website;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword(){
        return password;
    }
}
