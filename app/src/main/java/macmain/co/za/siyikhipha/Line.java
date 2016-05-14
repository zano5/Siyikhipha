package macmain.co.za.siyikhipha;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by ProJava on 12/24/2015.
 */
public class Line implements Serializable {

    private String artistName;
    private String start;
    private String end;
    private String picture;
    private Bitmap bitImage;


    public Line(){

    }

    public Line(String artistName, String start, String end, String picture){

        this.artistName = artistName;
        this.start = start;
        this.end = end;
        this.picture = picture;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
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
}
