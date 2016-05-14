package macmain.co.za.siyikhipha;

import java.io.Serializable;

/**
 * Created by ProJava on 12/18/2015.
 */
public class Clothering implements Serializable {

    private int id;
    private String producer, line,contact;
    private double price;
    private String picture;

    public Clothering(){

    }

    public Clothering(int id, String producer, String line, String contact, double price, String picture){

        this.id= id;
        this.producer=producer;
        this.line=line;
        this.price=price;
        this.picture = picture;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }
}
