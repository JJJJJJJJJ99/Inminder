package edu.brandeis.jjwang95.inminder;

/**
 * Created by WangJingjing on 11/13/16.
 */

public class ReminderObject {
    long id;
    String time;
    String name;
    String note;

    public ReminderObject(){

    }
    public ReminderObject(String time, String name, String note){
        this.time = time;
        this.name = name;
        this.note = note;
    }

    // Setters ####################
    public void setId(long id) {
        this.id = id;
    }
    public void setTime(String date){
        this.time = date;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setNote(String note){
        this.note = note;
    }

    // Getters ####################
    public long getId(){
        return id;
    }
    public String getTime(){
        return time;
    }
    public String getName(){
        return name;
    }
    public String getNote(){
        return note;
    }
}
