package de.fh_bielefeld.megabet;
// Erzeugen der Datenbank (MegaBetDBAdapter)

// DatabaseHelper contains all the methods to perform database operations like opening connection,
// closing connection, insert, update, read, delete and other things.
// As this class is helper class, place this under helper package.
// Create a Class named MegaBetDBAdapter.java and extends the class from SQLiteOpenHelper.
// public class MegaBetDBAdapter extends SQLiteOpenHelper{
// Add requires variables like database name, database version, column names.
// Executed TABLE CREATE statements in onCreate() method.

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MegaBetDBAdapter {

    // LOGTAG MegaBetDBAdapter
    private static final String LOG_TAG = MegaBetDBAdapter.class.getName();
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "MegaBet";

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    //Table Names
    private static final String TABLE_USER = "user";
    private static final String TABLE_SPIEL = "spiel";
    private static final String TABLE_WETTE = "wette";
    private static final String TABLE_GEWETTETESPIELE = "gewettetespiele";

    // Variables TABLE login / column names
    public static final String KEY_USER_ID = "_userID";
    public static final String USERNAME = "username";
    public static final String PASSWORT = "passwort";
    public static final String AKTIV = "aktiv";
    public static final String TALER = "taler";
    public static final String ADMIN = "admin";

    //TABLE CREATE STATEMENT ( User )
    private static final String SQL_CREATE_USER = "CREATE TABLE " + TABLE_USER +
            "(" + KEY_USER_ID + " Integer PRIMARY KEY AUTOINCREMENT, " +
            USERNAME + " Text Not Null," +
            PASSWORT + " Text Not Null, " +
            AKTIV + " Text Not Null, " +
            TALER + " Double Not Null, " +
            ADMIN + " Text Not Null);";

    // Variables TABLE Spiel / column names
    //  private static final String TABLE_SPIEL = "spiel_id";
    public static final String KEY_SPIEL_ID = "_spiel_id";

    public static final String HEIM = "heim";
    public static final String GAST = "gast";
    public static final String TORE_HEIM = "tore_heim";
    public static final String TORE_GAST = "tore_gast";
    public static final String DATUM = "datum";
    public static final String UHRZEIT = "uhrzeit";
    public static final String ERGEBNIS = "ergebnis";


    //TABLE CREATE STATEMENT ( Spiel )
    private static final String SQL_CREATE_SPIEL = "CREATE TABLE " + TABLE_SPIEL +
            "(" + KEY_SPIEL_ID + " Integer PRIMARY KEY AUTOINCREMENT, " +
            HEIM + " Text Not Null, " +
            GAST + " Text Not Null, " +
            TORE_HEIM + " INTEGER Not Null, " +
            TORE_GAST + " INTEGER Not Null, " +
            DATUM + " Double Not Null, " +
            UHRZEIT + " Double Not Null, " +
            ERGEBNIS + " INTEGER Not Null);";

    // Variables TABLE Wette / column names

    public static final String KEY_WETTE_ID = "_wettID";

    //   public static final String KEY_USERNAME = "username";
    public static final String TIPP = "tipp";
    public static final String EINSATZ = "einsatz";
    public static final String WETTGEWINN = "wettgewinn";

    //TABLE CREATE STATEMENT ( Wette )
    private static final String SQL_CREATE_WETTE = "CREATE TABLE " + TABLE_WETTE +
            "(" + KEY_WETTE_ID + " Integer PRIMARY KEY AUTOINCREMENT, " +
            KEY_SPIEL_ID + " Text Not Null, " +
            USERNAME + " Text Not Null, " +
            TIPP + " INTEGER Not Null, " +
            EINSATZ + " Double Not Null, " +
            WETTGEWINN + " DOUBLE Not Null, " +
            "FOREIGN KEY(" + KEY_SPIEL_ID + ") REFERENCES " + TABLE_SPIEL + "(" + KEY_SPIEL_ID + "));";

    /*
     SQL-Befehl um eine View aus zwei Tabellen (TABLE_SPIEL und TABLE_WETTE) zu erstellen.
     Die Spalten werden in der SELECT_Anweisung definiert und die Tabellen werden über den PK/FK
     spiel_id miteinander verknüpft. Dies geschieht nur, wenn die View<gewetteteSpiele> noch nicht
     in der Datenbank existiert.
     */

    private static final String SQL_CREATE_GEWETTETESPIELE =
            "CREATE VIEW IF NOT EXISTS gewettetespiele AS SELECT spiel.datum, wette.einsatz, " +
                    "spiel.heim, spiel.gast, wette.tipp FROM spiel, wette " +
                    "WHERE wette._spiel_id = spiel._spiel_id;";


    private final Context ctx;

    public MegaBetDBAdapter(Context ctx) {
        this.ctx = ctx;
    }

    /*
    Erzeugt eine Lesbare und Beschreibbare Datenbank durch zur Hilfenahme des dbHelper.
    Durch einkommentieren der auskommentierten database.delete-Anweisungen können bereits
    angelegte Tabellen wieder gelöscht werden.
     */

    public MegaBetDBAdapter open() throws SQLException {

        dbHelper = new DatabaseHelper(ctx);
        database = dbHelper.getWritableDatabase();

        // database.delete(TABLE_SPIEL, null, null);
        // database.delete(TABLE_WETTE, null, null);
        // database.delete(TABLE_USER, null, null);
        // DROP_GEWETTESPIELE();
        // database.delete(TABLE_GEWETTETESPIELE, null, null);

        return this;
    }

    /*
    Schließt den dbHelper.
     */
    public void close() {
        dbHelper.close();
    }

    /*
    In den drei create-Methoden -createUser(), createSpiel(),createWette()- werden die
    Objekte -user, spiel, wette- übergeben. Diese Attribute werden herausgelesen und dem
    ContentValue hinzugefügt und anschließend in die Datenbank geschrieben.
     */
    public long createUser(User user) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(USERNAME, user.getUsername());
        initialValues.put(PASSWORT, user.getPasswort());
        initialValues.put(AKTIV, user.getAktiv());
        initialValues.put(TALER, user.getTaler());
        initialValues.put(ADMIN, user.getAdmin());

        dbHelper = new DatabaseHelper(ctx);
        database = dbHelper.getWritableDatabase();

        return database.insert(TABLE_USER, null, initialValues);
    }

    public long createSpiel(Spiel spiel) {
        ContentValues iniValues = new ContentValues();
        iniValues.put(HEIM, spiel.getHeim());
        iniValues.put(GAST, spiel.getGast());
        iniValues.put(TORE_HEIM, spiel.getHeimtore());
        iniValues.put(TORE_GAST, spiel.getGasttore());
        iniValues.put(UHRZEIT, spiel.getUhrzeit());
        iniValues.put(DATUM, spiel.getDatum());
        iniValues.put(ERGEBNIS, spiel.getErgebnis());

        dbHelper = new DatabaseHelper(ctx);
        database = dbHelper.getWritableDatabase();

        return database.insert(TABLE_SPIEL, null, iniValues);
    }

    public long createWette(Wette wette) {
        ContentValues iniVaulues = new ContentValues();
        iniVaulues.put(KEY_SPIEL_ID, wette.getSpielID());
        iniVaulues.put(USERNAME, wette.getUsername());
        iniVaulues.put(TIPP, wette.getTipp());
        iniVaulues.put(EINSATZ, wette.getEinsatz());
        iniVaulues.put(WETTGEWINN, wette.getWettgewinn());

        dbHelper = new DatabaseHelper(ctx);
        database = dbHelper.getWritableDatabase();
        return database.insert(TABLE_WETTE, null, iniVaulues);
    }

    /*
    In den 3 fetch-Methoden -fetchAllUser(), fetchAllSpiele(), fetchAllWetten- wird mit Hilfe des
    Datenbank query-Befehl die jeweiligen Datensätze aus den Tabellen ausgelesen und dem Cursor
    übergeben. Der Cursor wird anschließend zurückgegeben.
     */
    public Cursor fetchAllUser() {
        return database.query(TABLE_USER, new String[]{KEY_USER_ID, USERNAME,
                PASSWORT, AKTIV, TALER, ADMIN}, null, null, null, null, null);
    }

    public Cursor fetchAllSpiele() {
        return database.query(TABLE_SPIEL, new String[]{KEY_SPIEL_ID, HEIM, GAST,
                TORE_HEIM, TORE_GAST, DATUM, UHRZEIT, ERGEBNIS}, null, null, null, null, null);
    }

    public Cursor fetchAllWetten() {
        CREATE_GEWETTETESPIELE();
        return database.query(true, TABLE_GEWETTETESPIELE, new String[]{DATUM, EINSATZ, HEIM,
                GAST, TIPP}, null, null, null, null, null, null);

    }

    /*
    Erzeugung der View durch execSQL-Anweisung.
     */

    public void CREATE_GEWETTETESPIELE() {
        database.execSQL(SQL_CREATE_GEWETTETESPIELE);
    }

    /*
    Löschen der View
     */
    public void DROP_GEWETTESPIELE() {
        String sqldrop = "DROP VIEW gewettetespiele;";
        database.execSQL(sqldrop);
    }

    /*
    In den drei folgenden fetch()-Methode, werden mit Hilfe des Cursor, alle Attribute, über die
    übergebene _id ausgelesen und dem Cursor zurückgegeben.
     */
    public Cursor fetchSpiele(String spielID) throws SQLException {
        Cursor cursor = database.query(false, TABLE_SPIEL, new String[]{KEY_SPIEL_ID,
                        HEIM, GAST, TORE_HEIM, TORE_GAST, UHRZEIT, DATUM, ERGEBNIS}, KEY_SPIEL_ID + "=" + spielID, null,
                null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchUser(String userID) throws SQLException {

        Cursor cursor = database.query(false, TABLE_USER, new String[]{KEY_USER_ID,
                        USERNAME, PASSWORT, AKTIV, TALER}, KEY_USER_ID + "=" + userID, null,
                null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchWette(String wettID) throws SQLException {

        Cursor cursor = database.query(true, TABLE_WETTE, new String[]{KEY_WETTE_ID,
                        KEY_SPIEL_ID, USERNAME, TIPP, EINSATZ, WETTGEWINN},
                            KEY_SPIEL_ID + "=" + wettID, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    /*
    Der Talerbestand, des übergeben Users, wird hier ausgelesen und der entsprechende Datenbanksatz
    aktualisiert mit den ausgelesenen Talerbestand.
    Hierbei kommt es zu Problemen bei der Anwendung. Es wird eine NullPointerException geworfen,
    die derzeit noch nicht behoben werden konnte.
     */
    public boolean setTalerDB(User eingeloggterUser) {
        ContentValues iniVaulues = new ContentValues();
        iniVaulues.put(TALER, eingeloggterUser.getTaler());
        return database.update(TABLE_USER, iniVaulues, KEY_USER_ID + " = "
                + eingeloggterUser.getUserID(), null) > 0;
    }

    /*
    Hier wird eine neue Klasse eingeführt -DatabaseHelper- die bei der Erzeugung der
    Datenbank und deren Tabellen in der onCreate()-Methode angewendet wird.
     */

    public class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            Log.d(LOG_TAG, "DB-Helper hat die Datenbank: " + getDatabaseName() + " erzeugt.");
        }

        @Override
        public void onCreate(SQLiteDatabase database) {
            database.execSQL(SQL_CREATE_USER);
            database.execSQL(SQL_CREATE_SPIEL);
            database.execSQL(SQL_CREATE_WETTE);
        }

        /*
        Kann bei einen Versionswechsel zur Anwendung kommen.
         */

        @Override
        public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
            // wird zur Zeit noch nicht genutzt.
        }
    }
}



