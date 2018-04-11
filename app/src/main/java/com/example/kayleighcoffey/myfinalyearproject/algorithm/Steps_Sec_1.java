package com.example.kayleighcoffey.myfinalyearproject.algorithm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.kayleighcoffey.myfinalyearproject.algorithm.section1.Sec1_Steps_Sleep;
import com.example.kayleighcoffey.myfinalyearproject.algorithm.section2.Sec2_Steps_Sleep;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

/**
 * Algorithm part 2 - Taking in the Steps
 */

public class Steps_Sec_1 extends AppCompatActivity
{
    private FirebaseDatabase database;
    private DatabaseReference myRef;


    private int mySteps = 10000;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Getting Selected date
        Intent i = getIntent();
        final String product = i.getStringExtra("itemRef");

        // Referencing Database
        database = FirebaseDatabase.getInstance();
        myRef = database.getReferenceFromUrl("https://finalyearproject-e0102.firebaseio.com/0/users/user/days/day/" + product);

        System.out.println("-------------------------Steps sec1 started");



        myRef.child("day_activities").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String mSteps = (String) dataSnapshot.child("Steps").getValue();
                // changing string to integer to compare values
                if(Objects.equals(mSteps, "null"))
                {
                    Intent intent = new Intent(Steps_Sec_1.this, Sec1_Steps_Sleep.class);
                    intent.putExtra("itemRef", product);
                    startActivity(intent);
                }
                else
                {
                    int iSteps = Integer.parseInt(mSteps);
                    if (iSteps < mySteps) {
                        //if the number is less than the recommended amount, changes to this class in
                        //Section1_1 folder
                        Intent intent = new Intent(Steps_Sec_1.this, Sec1_Steps_Sleep.class);
                        intent.putExtra("itemRef", product);
                        startActivity(intent);

                    } else if(iSteps >= mySteps){

                        //If the number is more than the recommended amount, changes to this class in
                        //section_2 folder
                        Intent sleepintent;
                        sleepintent = new Intent(getApplicationContext(), Sec2_Steps_Sleep.class);
                        sleepintent.putExtra("itemRef", product);
                        startActivity(sleepintent);


                    }
                }








            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
