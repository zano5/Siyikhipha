package macmain.co.za.siyikhipha;

import android.graphics.Bitmap;

/**
 * Created by ProJava on 11/28/2015.
 */
public class Artist {

    private int id;
    private String name, lastest_single,image, lastest_album;
    private int number_of_albums;
    private Bitmap bitImage;


    public Artist(){

    }

    public Artist(int id, String name, String lastest_single,int number_of_albums, String latest_album,String image, Bitmap bitImage){


        this.id = id;
        this.name = name;
        this.number_of_albums=number_of_albums;
        this.image = image;
        this.lastest_single = lastest_single;
        this.lastest_album =latest_album;
        this.bitImage = bitImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLastest_single() {
        return lastest_single;
    }

    public void setLastest_single(String lastest_single) {
        this.lastest_single = lastest_single;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber_of_albums() {
        return number_of_albums;
    }

    public void setNumber_of_albums(int number_of_albums) {
        this.number_of_albums = number_of_albums;
    }

    public String getLastest_album() {
        return lastest_album;
    }

    public void setLastest_album(String lastest_album) {
        this.lastest_album = lastest_album;
    }

    public Bitmap getBitImage() {
        return bitImage;
    }

    public void setBitImage(Bitmap bitImage) {
        this.bitImage = bitImage;
    }
}
