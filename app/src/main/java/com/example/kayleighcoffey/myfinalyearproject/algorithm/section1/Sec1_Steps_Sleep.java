package com.example.kayleighcoffey.myfinalyearproject.algorithm.section1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.kayleighcoffey.myfinalyearproject.R;
import com.example.kayleighcoffey.myfinalyearproject.algorithm.Algorithm_firstpage;
import com.example.kayleighcoffey.myfinalyearproject.algorithm.Steps_Sec_1;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static java.lang.Integer.parseInt;

/**
 * Algorithm part 3 - Taking the sleep
 */

public class Sec1_Steps_Sleep extends AppCompatActivity
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

        System.out.println("-----------------Sec1_Steps_Sleep");
        mySleep();
    }

    public void mySleep(){

        // Getting Selected date
        Intent i = getIntent();
        final String product = i.getStringExtra("itemRef");


        // Day metrics - SLEEP
        myRef.child("day_metrics").child("Sleep_Duration").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String mSleep = (String) dataSnapshot.getValue();
                int iSleep = parseInt(mSleep);

                // algorithm breaks into 2 parts depending on how much sleep user has gotten
                // NOTE : SLEEP IS COUNTED IN MINUTES
                if(iSleep < mySleep)
                {
                    // if user got less than recommended amount -
                    Intent intent = new Intent(Sec1_Steps_Sleep.this, Sec1_Steps_Drinks.class);
                    intent.putExtra("itemRef", product);
                    startActivity(intent);
                }
                else if(iSleep >= mySleep)
                {
                    // if user got more than recommended amount -
                    Intent secondintent = new Intent(Sec1_Steps_Sleep.this, Sec1_Steps_Drinks_2.class);
                    // Getting Selected date
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
