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

    ArrayAdapter<Spiel> adapter;

    ArrayList<Spiel> spiel = new ArrayList<Spiel>();

    private MegaBetDBAdapter dbHelper;


    public static final String HEIM = "heim";
    public static final String GAST = "gast";
    public static final String TORE_HEIM = "tore_heim";
    public static final String TORE_GAST = "tore_gast";
    public static final String DATUM = "datum";
    public static final String UHRZEIT = "uhrzeit";
    public static final String ERGEBNIS = "ergebnis";

    private User eingeloggertUser;

    private TextView textViewUser;
    private TextView textViewTaler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        dbHelper = new MegaBetDBAdapter(this);

        eingeloggertUser = LoginActivity.getEingeloggertUser();


        loadData();

        fillData();

        loadSpiel();

        createTableView();
    }

    private void loadData(){
        textViewUser = (TextView) findViewById(R.id.user_textViewUsername);
        textViewTaler = (TextView) findViewById(R.id.user_TextViewTaler);
        // TODO: findview for all views

        textViewUser.setText(LoginActivity.getEingeloggertUser().getUsername());
        textViewTaler.setText(LoginActivity.getEingeloggertUser().getTaler());
        // TODO: set text
    }


    public void fillData() {

        dbHelper.open();

        spiel.removeAll(spiel);
        Cursor cursor = dbHelper.fetchAllSpiele();

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

        spiel.add(new Spiel("SC Paderborn", "DSC Bielefeld" , 0, 0, "18:00", "31.12.16", 0, 1));
        spiel.add(new Spiel("FC Bayern", "Borussia Dortmund" , 0, 0, "18:00", "12.01.17", 0, 2));
        spiel.add(new Spiel("RB Leipzig", "Vfl Stuttgart" , 0, 0, "18:00", "21.01.17", 0, 3));
        spiel.add(new Spiel("Werder Bremen", "Hamburg SV" , 0, 0, "18:00", "27.01.17", 0, 4));




    }


    public void createTableView(){

        final ListView spielListe = (ListView) findViewById(R.id.ListViewWettereignisse);
        adapter = new ArrayAdapter<Spiel>(this, android.R.layout.simple_expandable_list_item_1, spiel);
        spielListe.setAdapter(adapter);

        spielListe.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){


                Intent intent = new Intent(context, WetteAbgebenActivity.class);

                startActivityForResult(intent, 0);




            }
        });
    }
}

