package macmain.co.za.siyikhipha;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by ProJava on 12/24/2015.
 */
public class LineUp implements Serializable {

    private int id;
    private int fkEventID;
    private String artistName, start,end, picture;
    private Bitmap bitImage;

    public LineUp(){

    }


    public LineUp(int id, int fkEventID, String artistName, String start, String end, String picture, Bitmap bitImage){

        this.id = id;
        this.fkEventID = fkEventID;
        this.artistName = artistName;
        this.start = start;
        this.end =end;
        this.picture = picture;
        this.bitImage = bitImage;

    }


    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public Bitmap getBitImage() {
        return bitImage;
    }

    public void setBitImage(Bitmap bitImage) {
        this.bitImage = bitImage;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getFkEventID() {
        return fkEventID;
    }

    public void setFkEventID(int fkEventID) {
        this.fkEventID = fkEventID;
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

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }
}
