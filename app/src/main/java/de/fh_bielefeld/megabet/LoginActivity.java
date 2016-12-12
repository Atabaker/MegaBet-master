package de.fh_bielefeld.megabet;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {


    final Context context = this;

    ArrayAdapter<User> adapter;

    ArrayList<User> user = new ArrayList<User>();

    private MegaBetDBAdapter dbHelper;

    public final static String USERNAME = "username";
    public final static String PASSWORT = "password";
    public static final String AKTIV = "aktiv";
    public static final String TALER = "taler";
    public static final String ADMIN = "admin";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new MegaBetDBAdapter(this);

        fillData();

        loadUser();

        createTableView();
    }


    public void fillData() {

        dbHelper.open();

        user.removeAll(user);
        Cursor cursor = dbHelper.fetchAllUser();

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            long userID = cursor.getLong(cursor.getColumnIndexOrThrow(MegaBetDBAdapter.KEY_USER_ID));
            String username = cursor.getString(cursor.getColumnIndexOrThrow(MegaBetDBAdapter.USERNAME));
            String passwort = cursor.getString(cursor.getColumnIndexOrThrow(MegaBetDBAdapter.PASSWORT));
            String aktiv = cursor.getString(cursor.getColumnIndexOrThrow(MegaBetDBAdapter.AKTIV));
            double taler = cursor.getDouble(cursor.getColumnIndexOrThrow(MegaBetDBAdapter.TALER));
            String admin = cursor.getString(cursor.getColumnIndexOrThrow(MegaBetDBAdapter.ADMIN));

            user.add(new User(username,passwort, aktiv, taler, admin, userID));
            cursor.moveToNext();
        }
        dbHelper.close();
    }


    public void loadUser(){

        user.add(new User("afoerster@fh-bielefeld.de" ,"apple", "true", 100, "true", 1));
        user.add(new User("asediqi@fh-bielefeld.de" ,"htc", "true", 77, "false", 2));
        user.add(new User("sbrokmeier@fh-bielefeld.de" ,"samsung", "true", 80, "true", 3));
        user.add(new User("skleemann@fh-bielefeld.de" ,"lg", "true", 80, "false", 4));


    }


    public void createTableView(){

        final ListView userListe = (ListView) findViewById(R.id.ListViewUser);
        adapter = new ArrayAdapter<User>(this, android.R.layout.simple_expandable_list_item_1, user);
        userListe.setAdapter(adapter);

        userListe.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                //TODO: Wenn Admin true, Admin-Screen öffnen

                if(user.get(position).getAdmin() == "false") {
                    Intent intent = new Intent(context, UserActivity.class);

                    startActivityForResult(intent, 0);

                }else if(user.get(position).getAdmin() == "true") {
                    Intent intent = new Intent(context, AdminActivity.class);

                    startActivityForResult(intent, 0);
                }




            }
        });
    }
}