package de.fh_bielefeld.megabet;

public class Spiel {


// Variablen/Spalten der Tabelle Spiel
    private String heim;
    private String gast;
    private int tore_gast;
    private int tore_heim;
    private String uhrzeit;
    private String datum;
    private int ergebnis;
    private long spielID;

//Konstruktor für die Tabelle Spiel
    public Spiel(String heim, String gast, int tore_gast, int tore_heim, String uhrzeit, String datum, int ergebnis, long spielID){
        this.heim = heim;
        this.gast = gast;
        this.tore_gast = tore_gast;
        this.tore_heim = tore_heim;
        this.uhrzeit = uhrzeit;
        this.datum = datum;
        this.ergebnis = ergebnis;
        this.spielID = spielID;
    }


// SETTER und GETTER Methoden

    public long getSpielID(){
        return spielID;
    }

    public void setSpielID(long spielID){
        this.spielID = spielID;
    }

    public String getHeim() {
        return heim;
    }

    public void setHeim(String heim) {
        this.heim = heim;
    }

    public String getGast() {
        return gast;
    }

    public void setGast(String gast) {
        this.gast = gast;
    }

    public int getGasttore() {
        return tore_gast;
    }

    public void setGasttore(int tore_gast) {
        this.tore_gast = tore_gast;
    }

    public int getHeimtore() {
        return tore_heim;
    }

    public void setHeimtore(int tore_heim) {
        this.tore_heim = tore_heim;
    }

    public String getUhrzeit() {
        return uhrzeit;
    }

    public void setUhrzeit(String uhrzeit) {
        this.uhrzeit = uhrzeit;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public int getErgebnis() {
        return ergebnis;
    }

    public void setErgebnis(int ergebnis) {
        this.ergebnis = ergebnis;
    }


    public String toString() {
        return datum +  " " + uhrzeit + " " + heim + " - " + gast;

    }

}
