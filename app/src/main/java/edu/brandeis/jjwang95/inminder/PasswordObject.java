package edu.brandeis.jjwang95.inminder;

/**
 * Created by WangJingjing on 11/13/16.
 */

public class PasswordObject {
    int id;
    String website;
    String password;

    public PasswordObject(){

    }
    public PasswordObject(String website, String password){
        this.website = website;
        this.password = password;
    }

    // Setters ####################
    public void setId(int id) {
        this.id = id;
    }
    public void setWebsite(String website){
        this.website = website;
    }
    public void setPassword(String password){
        this.password = password;
    }

    // Getters ####################
    public int getId(){
        return id;
    }
    public String getWebsite(){
        return website;
    }
    public String getPassword(){
        return password;
    }
}
