package de.fh_bielefeld.megabet;
// Erzeugen der Datenbank (MegaBetDBAdapter)

// DatabaseHelper contains all the methods to perform database operations like opening connection,
// closing connection, insert, update, read, delete and other things.
// As this class is helper class, place this under helper package.

// Create a Class named MegaBetDBAdapter.java and extends the class from SQLiteOpenHelper.
// public class MegaBetDBAdapter extends SQLiteOpenHelper{

// Add requires variables like database name, database version, column names.
// Executed TABLE CREATE statements in onCreate() method.

/**
 * Created by Jessi on 29.11.201 6.
 */

//import info.androidhive.sqlite.helper;

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
    private static final String WETTE_SPIEL = "wette_spiel";

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
            TALER + " Integer Not Null, " +
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
            "FOREIGN KEY("+ KEY_SPIEL_ID +") REFERENCES " + TABLE_SPIEL + "("+KEY_SPIEL_ID+"));";




    private final Context ctx;

    public MegaBetDBAdapter(Context ctx){

        this.ctx = ctx;
    }

    public MegaBetDBAdapter open() throws SQLException{

        dbHelper = new DatabaseHelper(ctx);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){

        dbHelper.close();
    }

    public long createUser(User user){
        ContentValues initialValues = new ContentValues();
        initialValues.put(USERNAME, user.getUsername());
        initialValues.put(PASSWORT, user.getPasswort());
        initialValues.put(AKTIV, user.getAktiv());
        initialValues.put(TALER, user.getTaler());
        initialValues.put(ADMIN, user.getAdmin());

        return database.insert(TABLE_USER,null,initialValues);
    }

    public long createSpiel(Spiel spiel){
        ContentValues iniValues = new ContentValues();
        iniValues.put(HEIM, spiel.getHeim());
        iniValues.put(GAST, spiel.getGast());
        iniValues.put(TORE_HEIM, spiel.getHeimtore());
        iniValues.put(TORE_GAST, spiel.getGasttore());
        iniValues.put(UHRZEIT, spiel.getUhrzeit());
        iniValues.put(DATUM, spiel.getDatum());
        iniValues.put(ERGEBNIS, spiel.getErgebnis());

        return database.insert(TABLE_SPIEL, null, iniValues);

    }

    public long createWette(Wette wette){
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

    public Cursor fetchAllUser() {
        return database.query(TABLE_USER, new String[] {KEY_USER_ID, USERNAME,
                PASSWORT, AKTIV, TALER, ADMIN}, null, null, null, null, null);
    }

    public Cursor fetchAllSpiele(){

   /*     String SQL_CREATE_GEWETTET = "CREATE VIEW wette_spiel_ as SELECT spiel.datum, wette.einsatz, spiel.heim, spiel.gast " +
                "FROM wette, spiel " +
                "WHERE spiel._spiel_id = wette._spiel_id;";

        database.execSQL(SQL_CREATE_GEWETTET);

        String squery = "SELECT * FROM wette_spiel";

        return database.query(WETTE_SPIEL,new String [] {DATUM, EINSATZ, HEIM, GAST}, null, null, null, null, null);
*/
        //String squery = "select spiel.datum, wette.einsatz, spiel.heim, spiel.gast from wette, spiel where spiel._spiel_id = wette._spiel_id;";

       // String selectquery = "SELECT " + TABLE_SPIEL + "." + DATUM + ", " + TABLE_WETTE + "." + EINSATZ + ", "  + TABLE_SPIEL + "." + HEIM + ", " + TABLE_SPIEL + "." + GAST + " FROM " + TABLE_SPIEL + ", " + TABLE_WETTE + " WHERE " + TABLE_SPIEL + "." + KEY_SPIEL_ID + " = " + TABLE_WETTE + "." + KEY_SPIEL_ID+"";


        //return database.query(TABLE_SPIEL, new String[] {KEY_SPIEL_ID, HEIM, GAST,
        //       TORE_HEIM, TORE_GAST}, null, null,null, null, null);

        return null;
    }

    public Cursor fetchAllWetten(){
        return database.query(TABLE_WETTE, new String[] {KEY_WETTE_ID, KEY_SPIEL_ID, USERNAME,
                TIPP, EINSATZ, WETTGEWINN}, null, null,null, null, null);
    }

    public Cursor fetchSpieler(String spielID) throws SQLException {

        Cursor cursor = database.query(false, TABLE_SPIEL, new String[] {KEY_SPIEL_ID,
                        HEIM, GAST, TORE_HEIM, TORE_GAST, UHRZEIT, DATUM, ERGEBNIS}, KEY_SPIEL_ID + "=" + spielID, null,
                null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchUser(String userID) throws SQLException {

        Cursor cursor = database.query(false, TABLE_USER, new String[] {KEY_USER_ID,
                        USERNAME, PASSWORT, AKTIV, TALER}, KEY_USER_ID + "=" + userID, null,
                null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchWette(String wettID) throws SQLException {



           Cursor cursor = database.query(true, TABLE_WETTE, new String[] {KEY_WETTE_ID,
                        KEY_SPIEL_ID, USERNAME, TIPP, EINSATZ, WETTGEWINN}, KEY_SPIEL_ID + "=" + wettID, null,
                null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }


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

        @Override
        public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
            // Not used, but you could upgrade the database with ALTER scripts
        }
    }
}



