package edu.brandeis.jjwang95.inminder;

/**
 * Created by WangJingjing on 11/13/16.
 */

public class BillObject {
    long id;
    String title;
    Double amount;
    String note;

    public BillObject(){

    }
    public BillObject(String title, Double amount, String note){
        this.title = title;
        this.amount = amount;
        this.note = note;

    }

    // Setters ####################
    public void setId(long id) {
        this.id = id;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setAmount(Double amount){
        this.amount = amount;
    }
    public void setNote(String note){this.note = note;}

    // Getters ####################
    public long getId(){
        return id;
    }
    public String getTitle(){
        return title;
    }
    public Double getAmount(){
        return amount;
    }
    public String getNote(){return note;}

}
