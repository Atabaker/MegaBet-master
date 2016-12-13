package de.fh_bielefeld.megabet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static de.fh_bielefeld.megabet.R.id.textViewTaler;

public class WetteAbgebenActivity extends AppCompatActivity {

    static User eingeloggertUser;

    String user_taler = Double.toString(LoginActivity.getEingeloggertUser().getTaler());

    private TextView textViewUser;
    private TextView textViewTaler;

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
        // findview for all views



        textViewUser.setText(LoginActivity.getEingeloggertUser().getUsername());
        textViewTaler.setText(user_taler);
        //set text
    }


    public void onClickWetteAbgeben(View view) {

        // Do something in response to button
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);


    }
}
