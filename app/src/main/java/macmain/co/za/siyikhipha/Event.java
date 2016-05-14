package macmain.co.za.siyikhipha;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by ProJava on 11/27/2015.
 */
public class Event implements Serializable {


    private int id;
    private String title,area,date,start,end, image;
    private double price,vip,door;
    private Bitmap bitImage;
    private int recommandation;

    public Event(){

    }

    public Event(int id,String title,String area,String start,String end, double price, double vip , String image, Bitmap bitImage){

        this.id= id;
        this.title=title;
        this.area=area;
        this.date=date;
        this.start=start;
        this.end=end;
        this.price=price;
        this.vip=vip;
        this.image = image;
        this.bitImage = bitImage;

    }

    public Event(int id,String title,String area,String start,String end, double price, double vip , String image, Bitmap bitImage, double door){

        this.id= id;
        this.title=title;
        this.area=area;
        this.date=date;
        this.start=start;
        this.end=end;
        this.price=price;
        this.vip=vip;
        this.image = image;
        this.bitImage = bitImage;
        this.door = door;

    }



    public String getArea() {
        return area;
    }

    public String getDate() {
        return date;
    }

    public String getEnd() {
        return end;
    }

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    public double getVip() {
        return vip;
    }

    public String getStart() {
        return start;
    }

    public String getImage(){

        return image;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVip(double vip) {
        this.vip = vip;
    }

    public Bitmap getBitImage() {
        return bitImage;
    }

    public void setBitImage(Bitmap bitImage) {
        this.bitImage = bitImage;
    }

    public int getRecommandation() {
        return recommandation;
    }

    public void setRecommandation(int recommandation) {
        this.recommandation = recommandation;
    }

    public double getDoor() {
        return door;
    }

    public void setDoor(double door) {
        this.door = door;
    }
}
