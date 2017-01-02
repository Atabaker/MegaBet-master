package de.fh_bielefeld.megabet;


public class User {

// Tabelle Spieler
    private String username;
    private String passwort;
    private String aktiv;
    private double taler;
    private String admin;
    private long userID;

// GETTER und SETTER Methoden

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public String getAktiv() {
        return aktiv;
    }

    public void setAktiv(String aktiv) {
        this.aktiv = aktiv;
    }

    public double getTaler() {
        return taler;
    }

    public void setTaler(double talerUser) {
        this.taler = taler - talerUser;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String toString(){
        return username;

    }

// Konstruktor für die Tabelle Login
    public User (String username, String passwort, String aktiv, double taler, String admin, long userID) {
        this.username = username;
        this.passwort = passwort;
        this.aktiv = aktiv;
        this.taler = taler;
        this.admin = admin;
        this.userID = userID;
    }

}