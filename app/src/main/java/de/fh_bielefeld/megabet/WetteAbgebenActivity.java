package de.fh_bielefeld.megabet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import static de.fh_bielefeld.megabet.R.id.textViewTaler;

public class WetteAbgebenActivity extends AppCompatActivity {

    static User eingeloggertUser;

    String user_taler = Double.toString(LoginActivity.getEingeloggertUser().getTaler());

    private TextView textViewUser;
    private TextView textViewTaler;
    private TextView textViewDatum;
    private TextView textViewUhrzeit;
    private TextView textViewHeim;
    private TextView textViewGast;

    //Deklarieren der RadioButton Auswahl
    //TODO: !!! RadioButton in eine Methode einarbeiten und dann wieder auskommentieren !!!
    /*  RadioButton heimRadioButton = (RadioButton)findViewById(R.id.wette_radioButtonHeim);
    boolean heimGewinnt = heimRadioButton.isChecked();
    RadioButton gastRadioButton = (RadioButton)findViewById(R.id.wette_radioButtonGast);
    boolean gastGewinnt = heimRadioButton.isChecked();
    RadioButton untenschiedenRadioButton = (RadioButton)findViewById(R.id.wette_radioButtonUnentschieden);
    boolean unentschiedenGewinnt = heimRadioButton.isChecked();
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wette_abgeben);

        eingeloggertUser = LoginActivity.getEingeloggertUser();

        loadData();
    }

    private void loadData(){
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
    public void Auswahl(){
        // TODO : Abfrage der Gruppenbutton
    }
}
