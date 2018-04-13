package com.example.kayleighcoffey.myfinalyearproject.algorithm.section4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kayleighcoffey.myfinalyearproject.R;
import com.example.kayleighcoffey.myfinalyearproject.algorithm.Algorithm_firstpage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * CLASS TAKES IN DRINKS AND DISPLAYS RESULTS
 */

public class Sec4_Steps_Drinks extends AppCompatActivity
{
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    //storing list
    private ArrayList<String> mDrinkLogs = new ArrayList<>();
    private ListView mListView;

    //average drinks
    private int aver_drinks = 4;

    TextView resulttext;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);
        resulttext = findViewById(R.id.results);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Algorithm_firstpage.class));
            }
        });

        // Getting Selected date
        Intent i = getIntent();
        final String product = i.getStringExtra("itemRef");

        // Referencing Database
        database = FirebaseDatabase.getInstance();
        myRef = database.getReferenceFromUrl("https://finalyearproject-e0102.firebaseio.com/0/users/user/days/day/" + product);

        myDrinks();
    }

    public void myDrinks(){

        myRef.child("personal_logs").child("drink_logs").child("log").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String drink = (String) snapshot.child("Drink").getValue();
                    mDrinkLogs.add(drink);


                }

                int countdrinks = mDrinkLogs.size();

                if(countdrinks == 0 )
                {
                    System.out.println("not enough sleep");
                    resulttext.setText("not enough sleep");
                }
                // if number is less than the average
                else if(countdrinks < aver_drinks)
                {
                    System.out.println("not enough sleep");
                    resulttext.setText("not enough sleep");

                }
                else if(countdrinks >=  aver_drinks)
                {
                    System.out.println("Not enough slepp, too much caffine/alcohol");
                    resulttext.setText("not enough sleep, too much caffine/alcohol");

                }
                else if(mDrinkLogs == null || mDrinkLogs.isEmpty())
                {
                    System.out.println("not enough sleep");
                    resulttext.setText("not enough sleep");
                }
                else if(mDrinkLogs == null || mDrinkLogs.isEmpty())
                {
                    System.out.println("not enough sleep");
                    resulttext.setText("not enough sleep");
                }






            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }
}
