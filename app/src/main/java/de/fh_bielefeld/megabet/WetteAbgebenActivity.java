package de.fh_bielefeld.megabet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class WetteAbgebenActivity extends AppCompatActivity {

    static User eingeloggterUser;

    String user_taler = Double.toString(LoginActivity.getEingeloggterUser().getTaler());

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
    int tipp;

    private MegaBetDBAdapter dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wette_abgeben);

        dbHelper = new MegaBetDBAdapter(this);

        eingeloggterUser = LoginActivity.getEingeloggterUser();

        loadData();
    }

    /*
    Die loadWettEinsatz()-Methode dient dazu, das vorliegende Numberfield auszulesen und den
    Wert in ein Double umzuwandeln. Dieser wird anschließend zurückgegeben.
    Bei unerwarteten NumberFormatExceptions, wird der Wert 0.0 gesetzt und returned.
     */

    private double loadWettEinsatz() throws NumberFormatException{


        try {
            editTextEinsatz = (EditText) findViewById(R.id.wette_editTextWetteinsatz);
            String einsatz = editTextEinsatz.getText().toString();
            double wetteinsatz = Double.parseDouble(einsatz);

            return wetteinsatz;

        }catch (NumberFormatException nfe){
            return 0.0;
        }
    }

    /*
    Die loadWettausgang()-Methode dient dazu, dass vom Benutzer getippte Ergebnis
    auszuwerten und zurückzugeben.
    Zunächst werden die RadioButtons per findViewById initialisiert.
    Anschließend wird mit durch eine If-Abfrage überprüft, welcher der RadioButton ausgewählt wurde
    und der dementsprechende Wert (1-3) zurückgegeben. Die 1 steht für den Heimsieg, die 2 für den
    Gastsieg und die 3 für ein Unentschieden der beiden Mannschaften.
     */

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
        } else if (gastGewinnt == true) {
            tipp = 2;
            return tipp;
        } else if (unentschieden == true) {
            tipp = 3;
            return tipp;
            }
        return tipp = 0;
    }

    /*
    In der loadData()-Methode wir zuerst allen "TextView" die dazugehörigen Variablen
    zugewiesen.
    Anschließend wird das aus der UserActivity übergebene Bundle ausgelesen
    und damit die jeweiligen "TextView" befüllt.
     */

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

        textViewDatum.setText(datum);
        textViewUhrzeit.setText(uhrzeit);
        textViewHeim.setText(heim);
        textViewGast.setText(gast);

        textViewUser.setText(LoginActivity.getEingeloggterUser().getUsername());
        textViewTaler.setText(user_taler);
    }

    /*
     Bei Klick auf den "Abbrechen"-Button gelangt der Benutzer in die UserActivity zurück.
     */

    public void onClickWetteCancel(View view) {

        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }

    /*
    Die onClickWetteAbgeben()-Methode dient dazu, die vom Benutzer abgegebene Wette auszuwerten
    und bei erfolgreicher Überprüfung des Talerbestand und der gesetzten Taler, ein neues Wett-
    Objekt zu erzeugen und diese mit Hilfe der createWette()-Methode in die Datenbank zu schreiben.
    Die benötigten Parameter zur Erzeugung des Wett-Objektes werden sowohl vom Bundle als auch
    von den Methoden: loadWettausgang(), loadWettEinsatz() entnommen.
    Daraufhin findet, mit Hilfe der setUser_taler()- Methode aus der UserActivity, eine Überprüfung
    der gesetzten Taler und des Talerbestand statt.
    Bei erfolgreicher Überprüfung wird das Wettobjekt in die Datenbank geschrieben und die
    UserActivity aufgerufen. Bei nicht ausreichenden Talerbestand wird ein Toast aufgerufen mit
    folgender Fehlermeldung: "Wette konnte nicht gesetzt werden, da Talerbestand zu niedrig!"
    und der Spieler verbleibt in der WetteAbgebenActicity.
     */

    public void onClickWetteAbgeben(View view){

        Intent intent = getIntent();
        Bundle spielbundle = intent.getExtras();

        long spielID = spielbundle.getLong(UserActivity.KEY_SPIEL_ID);
        String username = eingeloggterUser.getUsername();
        int tipp = loadWettausgang();
        double wettEinsatz = loadWettEinsatz();

        Wette wette = new Wette(spielID, username, tipp, wettEinsatz);
        UserActivity activity = new UserActivity();

        if(activity.setUser_taler(wettEinsatz) == true) {
            dbHelper.createWette(wette);
            Intent intenti = new Intent(this, UserActivity.class);
            startActivity(intenti);
        }
        else{
            Toast.makeText(getApplicationContext(), "Wette konnte nicht gesetzt werden, da Talerbestand zu niedrig!", Toast.LENGTH_LONG).show();
        }
    }

}
