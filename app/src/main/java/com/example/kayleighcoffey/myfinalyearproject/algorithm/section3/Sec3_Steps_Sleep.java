package com.example.kayleighcoffey.myfinalyearproject.algorithm.section3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.kayleighcoffey.myfinalyearproject.algorithm.Steps_Sec_3;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Algorithm part 3 - Taking the sleep
 */

public class Sec3_Steps_Sleep extends AppCompatActivity
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

        Intent i = getIntent();
        final String product = i.getStringExtra("itemRef");

        // Day metrics - SLEEP
        myRef.child("day_metrics").child("Sleep_Duration").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String mSleep = (String) dataSnapshot.getValue();
                int iSleep = Integer.parseInt(mSleep);

                if(iSleep < mySleep)
                {
                    // If user got less than recomended amount of sleep
                    Intent intent = new Intent(Sec3_Steps_Sleep.this, Sec3_Steps_Drinks.class);
                    intent.putExtra("itemRef", product);
                    startActivity(intent);

                }
                else if(iSleep >= mySleep)
                {
                    // if user got more sleep than recommended amount
                    Intent secondintent = new Intent(Sec3_Steps_Sleep.this, Sec3_Step_Drinks_2.class);
                    secondintent.putExtra("itemRef", product);
                    startActivity(secondintent);
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
