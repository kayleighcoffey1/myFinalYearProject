package com.example.kayleighcoffey.myfinalyearproject.algorithm;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

/**
 Algorithm part 1 - Taking in the MOOD
 */

public class First_Step_Mood extends Algorithm_firstpage {

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    //Storing Moods
    private String mContent   = "Content";
    private String mAnxious   = "Anxious";
    private String mDepressed = "Depressed";
    private String mGood      = "Good";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Getting Selected date
        Intent i = getIntent();
        final String product = i.getStringExtra("itemRef");

        // Referencing Database
        database = FirebaseDatabase.getInstance();
        myRef = database.getReferenceFromUrl("https://finalyearproject-e0102.firebaseio.com/0/users/user/days/day/" + product);

        System.out.println("-----------------Mood Class");
        myMood();
    }

    public void myMood()
    {
        Intent i = getIntent();
        final String product = i.getStringExtra("itemRef");

        // Mood
        myRef.child("personal_logs").child("health_logs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String mood = (String) dataSnapshot.child("Mood").getValue();

                if(Objects.equals(mood, mAnxious))
                {
                    //Links to the first Sec_1
                    Intent intent = new Intent(First_Step_Mood.this, Steps_Sec_1.class);
                    intent.putExtra("itemRef", product);
                    startActivity(intent);
                }
                else if(Objects.equals(mood, mContent))
                {
                    //Links to the second section Sec_2
                    Intent secondIntent = new Intent(First_Step_Mood.this, Steps_Sec_2.class);
                    secondIntent.putExtra("itemRef", product);
                    startActivity(secondIntent);
                }
                else if(Objects.equals(mood, mDepressed))
                {
                    // Links to the first section Sec_1
                    Intent intent = new Intent(First_Step_Mood.this, Steps_Sec_1.class);
                    intent.putExtra("itemRef", product);
                    startActivity(intent);
                }
                else if(Objects.equals(mood, mGood)){
                    // Links to the third section Sec_3
                    Intent thirdIntent = new Intent(First_Step_Mood.this, Steps_Sec_3.class);
                    thirdIntent.putExtra("itemRef", product);
                    startActivity(thirdIntent);
                }
                else if(Objects.equals(mood, "null")){
                    System.out.println("Mood is null");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}