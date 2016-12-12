package de.fh_bielefeld.megabet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class WetteAbgebenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wette_abgeben);
    }


    public void onClickWetteAbgeben(View view) {

        // Do something in response to button
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);


    }
}
