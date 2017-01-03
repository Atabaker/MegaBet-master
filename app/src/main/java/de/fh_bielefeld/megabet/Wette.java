package de.fh_bielefeld.megabet;



public class Wette {
    // Tabellenspalten Tabelle Wette
    private long spielID;
    private String username;
    private int tipp;
    private double einsatz;
    private double wettgewinn;
    private long wettID;
    private String datum;
    private String heim;
    private String gast;



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

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
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

    //Konstruktor Wette

    public Wette(long spielID, String username, int tipp, double einsatz) {
        this.spielID = spielID;
        this.username = username;
        this.tipp = tipp;
        this.einsatz = einsatz;
        wettgewinn = 0;
    }

   public Wette(String datum, double einsatz, String heim, String gast, int tipp) {

        this.datum = datum;
        this.einsatz = einsatz;
        this.heim = heim;
        this.gast = gast;
        this.tipp = tipp;

    }

    /*
    In der toString()-Methode findet eine Üperprüfung des abgegebenen Tipp statt.
    Das getippte Wettereignis -Heimsieg/Gastsieg/Unentschieden- wird nach Wettabgabe
    dem String hinzugefügt.
     */

    public String toString() {

        String tip;
        if(tipp == 1){
            tip = "Heimsieg";
            return datum + " | " + heim + " - " + gast + " | " + einsatz + "T | " +tip;
        }
        if(tipp == 2){
            tip = "Gastsieg";
            return datum + " | " + heim + " - " + gast + " | " + einsatz + "T | " +tip;
        }
        if(tipp == 3){
            tip = "Unentschieden";
            return datum + " | " + heim + " - " + gast + " | " + einsatz + "T | " +tip;
        }

        return datum + " | " + heim + " - " + gast + " | " + einsatz + "T | " +tipp;
    }
}
