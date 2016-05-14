package macmain.co.za.siyikhipha;

import java.io.Serializable;

/**
 * Created by ProJava on 12/16/2015.
 */
public class EventSchedule implements Serializable {

    private int fkEventID;
    private String eventName,start,end,artist;


    public EventSchedule(){

    }

    public  EventSchedule(String artist){

        this.artist =artist;

    }


    public EventSchedule(int fkEventID, String eventName, String start, String end,String artist){

        this.fkEventID = fkEventID;
        this.eventName = eventName;
        this.start= start;
        this.end = end;
        this.artist = artist;


    }


    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public int getFkEventID() {
        return fkEventID;
    }

    public void setFkEventID(int fkEventID) {
        this.fkEventID = fkEventID;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
