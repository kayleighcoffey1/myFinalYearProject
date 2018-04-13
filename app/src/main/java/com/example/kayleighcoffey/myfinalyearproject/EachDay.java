package com.example.kayleighcoffey.myfinalyearproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



public class EachDay extends MainActivity
{
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private DatabaseReference myQueryRef;
    private ListView mListView;
    private ArrayList<String> mDrinkLogs = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_info);
        final TextView textSteps = findViewById(R.id.userInfo);
        final TextView textSleep = findViewById(R.id.sleep);
        final TextView textMood  = findViewById(R.id.mood);

        Intent i = getIntent();
        //getting attached intent
        final String product = i.getStringExtra("itemRef");
        database = FirebaseDatabase.getInstance();
        myRef = database.getReferenceFromUrl("https://finalyearproject-e0102.firebaseio.com/0/" +
                "users/user/days/day/" + product);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        mListView = findViewById(R.id.listview);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_item, mDrinkLogs);
        mListView.setAdapter(arrayAdapter);



        // Displays Steps for the day - STEPS
        myRef.child("day_activities").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String mSteps = (String) snapshot.getValue();
                    textSteps.setText("Steps: " + mSteps);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        // Day metrics - SLEEP
        myRef.child("day_metrics").child("Sleep_Duration").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String mSleep = (String) dataSnapshot.getValue();
                textSleep.setText("Sleep(mins): " + mSleep);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        // Drink History

        myRef.child("personal_logs").child("drink_logs").child("log").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String drink = (String) snapshot.child("Drink").getValue();
                    arrayAdapter.add(drink);
                }




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //
        // Mood
        myRef.child("personal_logs").child("health_logs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String mood = (String) dataSnapshot.child("Mood").getValue();
                textMood.setText(mood);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }






}
