package macmain.co.za.siyikhipha;

import java.io.Serializable;

/**
 * Created by ProJava on 12/1/2015.
 */
public class Ticket implements Serializable {

    private int fkId;
    private String area;
    private String numberOfTickets;

    public Ticket(){

    }

    public Ticket(int fkId, String area, String numberOfTickets){

        this.fkId = fkId;
        this.area = area;
        this.numberOfTickets = numberOfTickets;


    }

    public void setFkId(int fkId){
        this.fkId = fkId;
    }

    public int getFkId() {
        return fkId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }


    public String getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(String numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }
}
