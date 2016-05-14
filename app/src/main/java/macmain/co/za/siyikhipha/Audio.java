package macmain.co.za.siyikhipha;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.Serializable;

/**
 * Created by ProJava on 12/11/2015.
 */
public class Audio implements Serializable {

    private int id;
    private String artistName, songName, audioName;
    private Uri audio;
    private String picture;
    private Bitmap bitImage;


    public Audio(){

    }

    public Audio(int id, String artistName, String songName, String audioName, String picture, Uri audio, Bitmap bitImage) {

        this.id = id;
        this.artistName = artistName;
        this.songName = songName;
        this.audioName = audioName;
        this.audio = audio;
        this.picture = picture;
        this.bitImage = bitImage;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getAudioName() {
        return audioName;
    }

    public void setAudioName(String audioName) {
        this.audioName = audioName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public Uri getAudio() {
        return audio;
    }

    public void setAudio(Uri audio) {
        this.audio = audio;
    }

    public Bitmap getBitImage() {
        return bitImage;
    }

    public void setBitImage(Bitmap bitImage) {
        this.bitImage = bitImage;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
