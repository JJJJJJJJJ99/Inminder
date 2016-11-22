package edu.brandeis.jjwang95.inminder;

/**
 * Created by WangJingjing on 11/13/16.
 */

public class BillObject {
    long id;
    String title;
    String amount;

    public BillObject(){

    }
    public BillObject(String title, String amount){
        this.title = title;
        this.amount = amount;
    }

    // Setters ####################
    public void setId(long id) {
        this.id = id;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setAmount(String amount){
        this.amount = amount;
    }

    // Getters ####################
    public long getId(){
        return id;
    }
    public String getTitle(){
        return title;
    }
    public String getAmount(){
        return amount;
    }

}
