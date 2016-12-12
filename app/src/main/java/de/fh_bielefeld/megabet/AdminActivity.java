package de.fh_bielefeld.megabet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;



public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
    }


    /** Called when the user clicks the button */
    public void onClickSpielHinzufuegen(View view) {

        // Do something in response to button
        Intent intent = new Intent(this, SpielBearbeitenActivity.class);
        startActivity(intent);


        }
    }





