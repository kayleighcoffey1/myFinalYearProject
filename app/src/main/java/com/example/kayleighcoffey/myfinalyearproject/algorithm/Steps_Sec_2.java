package com.example.kayleighcoffey.myfinalyearproject.algorithm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.kayleighcoffey.myfinalyearproject.algorithm.section3.Sec3_Steps_Sleep;
import com.example.kayleighcoffey.myfinalyearproject.algorithm.section4.Sec4_Steps_Sleep;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

/**
 * Class that takes in the steps
 */

public class Steps_Sec_2 extends AppCompatActivity {
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

        myRef.child("day_activities").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String mSteps = (String) snapshot.getValue();
                    // changing string to integer to compare values

                    if (!"null".equals(mSteps)) {
                        int iSteps = Integer.parseInt(mSteps);

                        if (iSteps < mySteps) {
                            // If the number is less than the recommended amount, changes to this class
                            // in Section_3
                            Intent intent = new Intent(Steps_Sec_2.this, Sec3_Steps_Sleep.class);
                            intent.putExtra("itemRef", product);
                            startActivity(intent);

                        } else if (iSteps >= mySteps) {
                            // If the number is more than the recommended amount, changes to this class
                            // in Section_4
                            Intent secondIntent = new Intent(Steps_Sec_2.this, Sec4_Steps_Sleep.class);
                            secondIntent.putExtra("itemRef", product);
                            startActivity(secondIntent);
                        }
                        else if(Objects.equals(mSteps, "null"))
                        {
                            Intent intent = new Intent(Steps_Sec_2.this, Sec3_Steps_Sleep.class);
                            intent.putExtra("itemRef", product);
                            startActivity(intent);
                        }

                    }

                    else
                    {
                        Intent intent = new Intent(Steps_Sec_2.this, Sec3_Steps_Sleep.class);
                        intent.putExtra("itemRef", product);
                        startActivity(intent);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
