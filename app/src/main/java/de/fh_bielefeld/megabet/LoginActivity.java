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
import android.widget.TextView;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {


    final Context context = this;

    ArrayAdapter<User> adapter;

    ArrayList<User> user = new ArrayList<User>();

    private MegaBetDBAdapter dbHelper;

    static User eingeloggterUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new MegaBetDBAdapter(this);

        loadUser();

        fillData();

        createTableView();
    }

    public static User getEingeloggterUser() {
        return eingeloggterUser;
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

        User user1 = new User("afoerster@fh-bielefeld.de" ,"apple", "true", 100, "true", 1);
        User user2 = new User("asediqi@fh-bielefeld.de" ,"htc", "true", 77, "false", 2);
        User user3 = new User("sbrokmeier@fh-bielefeld.de" ,"samsung", "true", 80, "true", 3);
        User user4 = new User("jkleemann@fh-bielefeld.de" ,"lg", "true", 84, "false", 4);

        dbHelper.open();

        Cursor cursor = dbHelper.fetchAllUser();
        if(cursor.getCount() == 0) {

            dbHelper.createUser(user1);
            dbHelper.createUser(user2);
            dbHelper.createUser(user3);
            dbHelper.createUser(user4);
        }

    }


    public void createTableView(){

        final ListView userListe = (ListView) findViewById(R.id.login_ListViewUser);
        adapter = new ArrayAdapter<User>(this, android.R.layout.simple_expandable_list_item_1, user);
        userListe.setAdapter(adapter);

        userListe.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){

                eingeloggterUser = user.get(position);


                   if(user.get(position).getAdmin().equals("false")){

                    Intent intent = new Intent(context, UserActivity.class);

                    startActivityForResult(intent, 0);

                }else if (user.get(position).getAdmin().equals("true")){
                    Intent intent = new Intent(context, AdminActivity.class);

                    startActivityForResult(intent, 0);
                }




            }
        });


    }
}

