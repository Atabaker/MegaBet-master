package de.fh_bielefeld.megabet;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/*import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
*/
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
    private EditText editTextEinsatz;
    boolean heimGewinnt;
    boolean gastGewinnt;
    boolean unentschieden;
    private Handler handler;
    int tipp;
    String einsatz;

    private MegaBetDBAdapter dbHelper;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wette_abgeben);

        dbHelper = new MegaBetDBAdapter(this);

        eingeloggertUser = LoginActivity.getEingeloggertUser();
        loadData();

        //:TODO Diese beiden Methoden müssten erst bei der Wettabgabe erzeugt werden und nicht bei der Erzeugung der Activity
        //loadWettausgang();
        //loadWettEinsatz();

    }

    private double loadWettEinsatz() throws NumberFormatException{


        try {
            editTextEinsatz = (EditText) findViewById(R.id.wette_editTextWetteinsatz);

            einsatz = editTextEinsatz.getText().toString();

            double wetteinsatz = Double.parseDouble(einsatz);

            return wetteinsatz;


        }catch (NumberFormatException nfe){
            return 0.0;
        }
    }

    private int loadWettausgang() {

        RadioButton heimRadioButton = (RadioButton) findViewById(R.id.wette_radioButtonHeim);
        RadioButton gastRadioButton = (RadioButton) findViewById(R.id.wette_radioButtonGast);
        RadioButton untenschiedenRadioButton = (RadioButton) findViewById(R.id.wette_radioButtonUnentschieden);


        heimGewinnt = heimRadioButton.isChecked();
        gastGewinnt = gastRadioButton.isChecked();
        unentschieden = untenschiedenRadioButton.isChecked();

        if (heimGewinnt == true) {
            tipp = 1;
            return tipp;

            }
        else if (gastGewinnt == true) {
            tipp = 2;
            return tipp;

        } else if (unentschieden == true) {
            tipp = 3;
            return tipp;
            }
        return tipp = 0;
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

    public void wetteAbgeben(){


    }

    public void onClickWetteCancel(View view) {

        // Do something in response to button
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);




    }

    public void onClickWetteAbgeben(View view){

        Intent intent = getIntent();
        Bundle spielbundle = intent.getExtras();

        long spielID = spielbundle.getLong(UserActivity.KEY_SPIEL_ID);
        String username = eingeloggertUser.getUsername();
        int tipp = loadWettausgang();
        double einsatz = loadWettEinsatz();

        Wette wette = new Wette(spielID, username, tipp, einsatz);

        dbHelper.createWette(wette);

        Intent intenti = new Intent(this, UserActivity.class);
        startActivity(intenti);
    }


 /*   @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }
*/
}
