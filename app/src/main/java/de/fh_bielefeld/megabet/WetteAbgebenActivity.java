package de.fh_bielefeld.megabet;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import static de.fh_bielefeld.megabet.R.id.textViewTaler;
import static de.fh_bielefeld.megabet.R.id.wette_radioButtonHeim;

public class WetteAbgebenActivity extends AppCompatActivity {

    static User eingeloggertUser;

    String user_taler = Double.toString(LoginActivity.getEingeloggertUser().getTaler());

    private TextView textViewUser;
    private TextView textViewTaler;
    private TextView textViewDatum;
    private TextView textViewUhrzeit;
    private TextView textViewHeim;
    private TextView textViewGast;
    boolean heimGewinnt;
    boolean gastGewinnt;
    boolean unentschieden;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wette_abgeben);

        eingeloggertUser = LoginActivity.getEingeloggertUser();
        loadData();

        //Deklarieren der RadioButton Auswahl
        //TODO: !!! RadioButton in eine Methode einarbeiten und dann wieder auskommentieren !!!
        RadioButton heimRadioButton = (RadioButton) findViewById(wette_radioButtonHeim);
        heimGewinnt = heimRadioButton.isChecked();

        RadioButton gastRadioButton = (RadioButton) findViewById(R.id.wette_radioButtonGast);
        gastGewinnt = gastRadioButton.isChecked();

        RadioButton untenschiedenRadioButton = (RadioButton) findViewById(R.id.wette_radioButtonUnentschieden);
        unentschieden = untenschiedenRadioButton.isChecked();

        loadWettausgang();

    }

    private void loadWettausgang() {
        if (heimGewinnt == true) {
            int x = 1;

            }
        else if (gastGewinnt == true) {
            int x = 2;

        } else if (unentschieden == true) {
                int x = 3;
            }else {

        }
    }


    private void loadData() {
        textViewUser = (TextView) findViewById(R.id.wette_textViewUsername);
        textViewTaler = (TextView) findViewById(R.id.wette_TextViewTaler);
        textViewDatum = (TextView) findViewById(R.id.wette_textViewSpieldatum);
        textViewUhrzeit = (TextView) findViewById(R.id.wette_textViewUhrzeit);
        textViewHeim = (TextView) findViewById(R.id.wette_textViewHeimmannschaft);
        textViewGast = (TextView) findViewById(R.id.wette_textViewGastmannschaft);

        Intent intent = getIntent();
        Bundle spielbundle = intent.getExtras();

        String datum = spielbundle.getString(UserActivity.DATUM);
        String uhrzeit = spielbundle.getString(UserActivity.UHRZEIT);
        String heim = spielbundle.getString(UserActivity.HEIM);
        String gast = spielbundle.getString(UserActivity.GAST);

        // findview for all views

        textViewDatum.setText(datum);
        textViewUhrzeit.setText(uhrzeit);
        textViewHeim.setText(heim);
        textViewGast.setText(gast);


        textViewUser.setText(LoginActivity.getEingeloggertUser().getUsername());
        textViewTaler.setText(user_taler);
        //set text
    }

    public void onClickWetteAbgeben(View view) {

        // Do something in response to button
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);


    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }
}
