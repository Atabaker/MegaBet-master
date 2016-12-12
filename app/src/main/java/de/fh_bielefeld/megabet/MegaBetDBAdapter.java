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
 * Created by Jessi on 29.11.2016.
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
            HEIM + " Text Not Null," +
            GAST + " Text Not Null, " +
            TORE_HEIM + " INTEGER Not Null, " +
            TORE_GAST + " INTEGER Not Null, " +
            DATUM + " Double Not Null," +
            UHRZEIT + " Double Not Null, " +
            ERGEBNIS + " INTEGER Not Null);";

    // Variables TABLE Wette / column names
//    private static final String TABLE_WETTE = "wett_id";
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
            EINSATZ + " Double Not Null," +
            WETTGEWINN + " DOUBLE Not Null);";


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
        initialValues.put(TALER, user.getTalerbestand());
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

    public Cursor fetchAllUser() {
        return database.query(TABLE_USER, new String[] {KEY_USER_ID, USERNAME,
                PASSWORT, AKTIV, TALER, ADMIN}, null, null, null, null, null);
    }

    public Cursor fetchAllSpiele(){
        return database.query(TABLE_SPIEL, new String[] {KEY_SPIEL_ID, HEIM, GAST,
                TORE_HEIM, TORE_GAST, UHRZEIT, DATUM, ERGEBNIS}, null, null,null, null, null);
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
                        USERNAME, PASSWORT, AKTIV, TALER, ADMIN}, KEY_USER_ID + "=" + userID, null,
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
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_USER);
            db.execSQL(SQL_CREATE_SPIEL);
            db.execSQL(SQL_CREATE_WETTE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Not used, but you could upgrade the database with ALTER scripts
        }
    }
}



/*
    // Constructor
    public MegaBetDBAdapter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(LOG_TAG, "DB-Helper hat die Datenbank: " + getDatabaseName() + " erzeugt.");
    }



    // Create TABLE
    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(TABLE_SPIELER);
        db.execSQL(TABLE_SPIEL);
        db.execSQL(TABLE_WETTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPIELER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPIEL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WETTE);

        // create new tables
        onCreate(db);
        //   throw new UnsupportedOperationException();
    }
}

*/