package de.fh_bielefeld.megabet;

/**
 * Created by Jessi on 30.11.2016.
 */

public class Wette {
    // Tabellenspalten Tabelle Wette
    private long spielID;
    private String username;
    private int tipp;
    private double einsatz;
    private double wettgewinn;
    private long wettID;



    // SETTER und GETTER SPIEL


    public long getSpielID() {
        return spielID;
    }

    public void setSpielID(long spielID) {
        this.spielID = spielID;
    }


    public long getWettID() {
        return wettID;
    }

    public void setWettID(long wettID) {
        this.wettID = wettID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTipp() {
        return tipp;
    }

    public void setTipp(int tipp) {
        this.tipp = tipp;
    }

    public double getEinsatz() {
        return einsatz;
    }

    public void setEinsatz(double einsatz) {
        this.einsatz = einsatz;
    }

    public double getWettgewinn() {
        return wettgewinn;
    }

    public void setWettgewinn(double wettgewinn) {
        this.wettgewinn = wettgewinn;
    }

    //Konstruktor Wette
    public Wette(long spieleId, long wettID, String username, int tipp, double einsatz, double wettgewinn) {
        this.spielID = spieleId;
        this.wettID = wettID;
        this.username = username;
        this.tipp = tipp;
        this.einsatz = einsatz;
        this.wettgewinn = wettgewinn;
    }

    public Wette(long spieleId, String username, int tipp, double einsatz) {
        this.spielID = spieleId;
        this.username = username;
        this.tipp = tipp;
        this.einsatz = einsatz;
        wettgewinn = 0;


    }

    public Wette() {

    }

    // gibt als String zurück: SpielId + Username + Tipp + Einsatz + Wettgewinn
    public String toString() {
        return   "datum +  + einsatz + T  + heim +  -  + gast";
    }
}
