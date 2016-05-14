package macmain.co.za.siyikhipha;

import android.graphics.Bitmap;

/**
 * Created by ProJava on 11/30/2015.
 */
public class Merchandise {

    private int id;
    private String title,city,town,area,contact,picture;
    private double price;
    private Bitmap bitImage;

    public Merchandise(){

    }

    public Merchandise(int id,String title,String city,String town, String area, String contact, String picture, double price){

        this.id = id;
        this.title=title;
        this.city = city;
        this.area = area;
        this.contact = contact;
        this.picture = picture;
        this.price = price;


    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }


    public Bitmap getBitImage() {
        return bitImage;
    }

    public void setBitImage(Bitmap bitImage) {
        this.bitImage = bitImage;
    }
}
