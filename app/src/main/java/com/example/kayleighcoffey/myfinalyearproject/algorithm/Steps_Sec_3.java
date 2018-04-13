package com.example.kayleighcoffey.myfinalyearproject.algorithm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.kayleighcoffey.myfinalyearproject.algorithm.section5.Sec5_Steps_Sleep;
import com.example.kayleighcoffey.myfinalyearproject.algorithm.section6.Sec6_Steps_Sleep;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

/**
 * Class that takes in the steps
 */

public class Steps_Sec_3 extends AppCompatActivity
{
    private FirebaseDatabase database;
    private DatabaseReference myRef;


    private int mySteps = 5117;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Getting Selected date
        Intent i = getIntent();
        final String product = i.getStringExtra("itemRef");

        // Referencing Database
        database = FirebaseDatabase.getInstance();
        myRef = database.getReferenceFromUrl("https://finalyearproject-e0102.firebaseio.com/0/users/user/days/day/" + product);


        System.out.println("------------------------Stpes_Sec_3 started");


        myRef.child("day_activities").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                String mSteps = (String) dataSnapshot.child("Steps").getValue();
                // changing string to integer to compare values

                if (Objects.equals(mSteps, "null")) {
                    Intent intent = new Intent(Steps_Sec_3.this, Sec5_Steps_Sleep.class);
                    intent.putExtra("itemRef", product);
                    startActivity(intent);
                } else {
                    int iSteps = Integer.parseInt(mSteps);


                    if (iSteps < mySteps) {
                        Intent intent = new Intent(Steps_Sec_3.this, Sec5_Steps_Sleep.class);
                        intent.putExtra("itemRef", product);
                        startActivity(intent);

                    } else if (iSteps >= mySteps) {
                        Intent secondintent = new Intent(Steps_Sec_3.this, Sec6_Steps_Sleep.class);
                        secondintent.putExtra("itemRef", product);
                        startActivity(secondintent);
                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



}
