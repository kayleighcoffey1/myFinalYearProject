package com.example.kayleighcoffey.myfinalyearproject.algorithm.section6;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Algorithm part 3 - Taking the sleep
 */

public class Sec6_Steps_Sleep extends AppCompatActivity
{
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    //average sleep in minutes
    private int mySleep = 480;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Getting Selected date
        Intent i = getIntent();
        final String product = i.getStringExtra("itemRef");

        // Referencing Database
        database = FirebaseDatabase.getInstance();
        myRef = database.getReferenceFromUrl("https://finalyearproject-e0102.firebaseio.com/0/users/user/days/day/" + product);

        mySleep();
    }

    public void mySleep(){

        // Day metrics - SLEEP
        myRef.child("day_metrics").child("Sleep_Duration").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String mSleep = (String) dataSnapshot.getValue();
                int iSleep = Integer.parseInt(mSleep);

                if(iSleep < mySleep)
                {
                    //.. go to function
                }
                else if(iSleep >= mySleep)
                {
                    //.. go to function
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
