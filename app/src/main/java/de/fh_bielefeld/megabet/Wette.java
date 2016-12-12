package de.fh_bielefeld.megabet;

/**
 * Created by Jessi on 30.11.2016.
 */

public class Wette {
    // Tabellenspalten Tabelle Wette
    private int spieleId;
    private String username;
    private int tipp;
    private double einsatz;
    private double wettgewinn;

// SETTER und GETTER SPIEL

    public int getSpieleId() {
        return spieleId;
    }

    public void setSpieleId(int spieleId) {
        this.spieleId = spieleId;
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
    public Wette(int spieleId, String username, int tipp, double einsatz, double wettgewinn) {
        this.spieleId = spieleId;
        this.username = username;
        this.tipp = tipp;
        this.einsatz = einsatz;
        this.wettgewinn = wettgewinn;
    }

    public Wette(int spieleId, String username, int tipp, double einsatz) {
        this.spieleId = spieleId;
        this.username = username;
        this.tipp = tipp;
        this.einsatz = einsatz;
        wettgewinn = 0;
    }

    public Wette() {

    }

    // gibt als String zurück: SpielId + Username + Tipp + Einsatz + Wettgewinn
    public String toString() {
        return spieleId + " , " + username + " , " + tipp + " , " + einsatz + " , " + wettgewinn;
    }
}
