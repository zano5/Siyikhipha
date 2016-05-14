package macmain.co.za.siyikhipha;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by ProJava on 12/8/2015.
 */
public class Amateur implements  Serializable {

    private int id;
    private String name, imageName,picture;
    private int view,rating,like;
    private Bitmap bitImage;


    public Amateur(){

    }

    public Amateur(int id,String name, String imageName, int view, int rating, int like, String picture, Bitmap bitImage){

         this.id= id;
        this.name = name;
        this.imageName = imageName;
        this.view = view;
        this.rating = rating;
        this.like = like;
        this.picture = picture;
        this.bitImage = bitImage;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public Bitmap getBitImage() {
        return bitImage;
    }

    public void setBitImage(Bitmap bitImage) {
        this.bitImage = bitImage;
    }
}
