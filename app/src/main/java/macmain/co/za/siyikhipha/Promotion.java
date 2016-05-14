package macmain.co.za.siyikhipha;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by ProJava on 12/23/2015.
 */
public class Promotion implements Serializable {

    private int id;
    private String name,start,end;
    private String picture;
    private Bitmap bitImage;
    private double price;

    public Promotion(){

    }


    public Promotion(int id, String name, String start, String end, String picture,double price, Bitmap bitImage){

        this.id= id;
        this.name = name;
        this.start = start;
        this.end = end;
        this.picture =picture;
        this.price = price;
        this.bitImage = bitImage;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public Bitmap getBitImage() {
        return bitImage;
    }

    public void setBitImage(Bitmap bitImage) {
        this.bitImage = bitImage;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
