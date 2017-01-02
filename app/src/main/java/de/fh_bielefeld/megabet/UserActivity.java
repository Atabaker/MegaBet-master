package de.fh_bielefeld.megabet;



import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaDataSource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;


public class UserActivity extends AppCompatActivity {

    final Context context = this;

    ArrayAdapter<Spiel> adapterS;

    ArrayAdapter<Wette> adapterW;

    ArrayList<Wette> wette = new ArrayList<Wette>();

    ArrayList<Spiel> spiel = new ArrayList<Spiel>();


    private MegaBetDBAdapter dbHelper;

    public static final String KEY_SPIEL_ID = "_spiel_id";
    public static final String HEIM = "heim";
    public static final String GAST = "gast";
    public static final String TORE_HEIM = "tore_heim";
    public static final String TORE_GAST = "tore_gast";
    public static final String DATUM = "datum";
    public static final String UHRZEIT = "uhrzeit";
    public static final String ERGEBNIS = "ergebnis";

    private User eingeloggterUser;

    String user_taler = Double.toString(LoginActivity.getEingeloggterUser().getTaler());

    private TextView textViewUser;
    private TextView textViewTaler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        dbHelper = new MegaBetDBAdapter(this);

        eingeloggterUser = LoginActivity.getEingeloggterUser();

        loadData();
        loadSpiel();
        fillDataSpiel();
        fillDataWette();
        createTableViewSpiel();
        createTableViewWette();
    }

    /*
    In der loadData()-Methode werden die Textviews per findViewbyID ausgelesen und Variabeln zugewiesen
     */

    private void loadData(){
        textViewUser = (TextView) findViewById(R.id.user_textViewUsername);
        textViewTaler = (TextView) findViewById(R.id.user_TextViewTaler);
        // findview for all views



        textViewUser.setText(LoginActivity.getEingeloggterUser().getUsername());
        textViewTaler.setText(user_taler);
        //set text
    }

    public void fillDataWette(){

        dbHelper.open();

        Cursor cursor = dbHelper.fetchAllWetten();
        wette.removeAll(wette);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            String datum = cursor.getString(cursor.getColumnIndexOrThrow(MegaBetDBAdapter.DATUM));
            double einsatz = cursor.getDouble(cursor.getColumnIndexOrThrow(MegaBetDBAdapter.EINSATZ));
            String heim = cursor.getString(cursor.getColumnIndexOrThrow(MegaBetDBAdapter.HEIM));
            String gast = cursor.getString(cursor.getColumnIndexOrThrow(MegaBetDBAdapter.GAST));
            int tipp = cursor.getInt(cursor.getColumnIndexOrThrow(MegaBetDBAdapter.TIPP));


            wette.add(new Wette(datum, einsatz, heim, gast, tipp));
            cursor.moveToNext();
        }
        dbHelper.close();
    }

    public void fillDataSpiel() {

        dbHelper.open();


        Cursor cursor = dbHelper.fetchAllSpiele();
        spiel.removeAll(spiel);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            long spielID = cursor.getLong(cursor.getColumnIndexOrThrow(MegaBetDBAdapter.KEY_SPIEL_ID));
            String heim = cursor.getString(cursor.getColumnIndexOrThrow(MegaBetDBAdapter.HEIM));
            String gast = cursor.getString(cursor.getColumnIndexOrThrow(MegaBetDBAdapter.GAST));
            int tore_gast = cursor.getInt(cursor.getColumnIndexOrThrow(MegaBetDBAdapter.TORE_GAST));
            int tore_heim = cursor.getInt(cursor.getColumnIndexOrThrow(MegaBetDBAdapter.TORE_HEIM));
            String uhrzeit = cursor.getString(cursor.getColumnIndexOrThrow(MegaBetDBAdapter.UHRZEIT));
            String datum = cursor.getString(cursor.getColumnIndexOrThrow(MegaBetDBAdapter.DATUM));
            int ergebnis = cursor.getInt(cursor.getColumnIndexOrThrow(MegaBetDBAdapter.ERGEBNIS));

            spiel.add(new Spiel(heim, gast, tore_gast, tore_heim, uhrzeit, datum,  ergebnis,  spielID));
            cursor.moveToNext();
        }
        dbHelper.close();
    }


    public void loadSpiel(){

        Spiel spiel1 = new Spiel("SC Paderborn", "DSC Bielefeld" , 0, 0, "18:00", "31.12.16", 0, 1);
        Spiel spiel2 = new Spiel("FC Bayern", "Hertha BSC" , 0, 0, "18:00", "12.01.17", 0, 2);
        Spiel spiel3 = new Spiel("RB Leipzig", "Vfl Stuttgart" , 0, 0, "18:00", "21.01.17", 0, 3);
        Spiel spiel4 = new Spiel("Werder Bremen", "Hamburg SV" , 0, 0, "18:00", "27.01.17", 0, 4);


        dbHelper.open();

        Cursor cursor = dbHelper.fetchAllSpiele();
        if(cursor.getCount() == 0) {
            dbHelper.createSpiel(spiel1);
            dbHelper.createSpiel(spiel2);
            dbHelper.createSpiel(spiel3);
            dbHelper.createSpiel(spiel4);
        }

    }


    public boolean setUser_taler(double user_taler){


        eingeloggterUser = LoginActivity.getEingeloggterUser();
        if(user_taler<=eingeloggterUser.getTaler()) {
            eingeloggterUser.setTaler(user_taler);

          // if(dbHelper.setTalerDB(eingeloggterUser) == true) {
                return true;
         //  }

        }
        return false;
    }


    public void createTableViewWette() {

        final ListView wettListe = (ListView) findViewById(R.id.user_ListViewMeineWetten);
        adapterW = new ArrayAdapter<Wette>(this, android.R.layout.simple_expandable_list_item_1, wette);
        wettListe.setAdapter(adapterW);
    }

    public void createTableViewSpiel(){

        final ListView spielListe = (ListView) findViewById(R.id.user_ListViewWettereignisse);
        adapterS = new ArrayAdapter<Spiel>(this, android.R.layout.simple_expandable_list_item_1, spiel);
        spielListe.setAdapter(adapterS);

        spielListe.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){

                Intent intent = new Intent(context, WetteAbgebenActivity.class);

                Bundle bundle = new Bundle();
                bundle.putLong(KEY_SPIEL_ID, spiel.get(position).getSpielID());
                bundle.putString(HEIM, spiel.get(position).getHeim());
                bundle.putString(GAST, spiel.get(position).getGast());
                bundle.putString(DATUM, spiel.get(position).getDatum());
                bundle.putString(UHRZEIT, spiel.get(position).getUhrzeit());

                intent.putExtras(bundle);

                startActivityForResult(intent, 0);
            }
        });
    }
}

