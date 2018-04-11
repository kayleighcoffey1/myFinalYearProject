package com.example.kayleighcoffey.myfinalyearproject.algorithm;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kayleighcoffey.myfinalyearproject.Constructors.GetData;
import com.example.kayleighcoffey.myfinalyearproject.Line_Chart;
import com.example.kayleighcoffey.myfinalyearproject.MainActivity;
import com.example.kayleighcoffey.myfinalyearproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * This class gets all the dates from the JSON file, ready for it the user to select which one they
 * would like to view in the algorithm. - Similar to the view
 */

public class Algorithm_firstpage extends AppCompatActivity{
    private FirebaseDatabase database;

    private DatabaseReference myRef;
    private ListView mListView;
    private ArrayList<String> mDate = new ArrayList<>();
    private ArrayList<String> mKeys = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.algorithm_main); // using same layout
        database = FirebaseDatabase.getInstance();
        myRef = database.getReferenceFromUrl("https://finalyearproject-e0102.firebaseio.com/0/users/user/days");

        System.out.println("---------------Algorithm firstpage--------------------");

        mListView = findViewById(R.id.listview);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_item, mDate);
        mListView.setAdapter(arrayAdapter);

        myRef.child("day")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            GetData mdate = snapshot.getValue(GetData.class);
                            arrayAdapter.add(mdate.getDate());
                            System.out.println(mdate.getDate());
                            String keys = snapshot.getKey();
                            mKeys.add(keys);

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        //selected Item

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Sending the selected value to EachDay class and viewing all info related
                String itemRef = mKeys.get(position);
                Intent i = new Intent(getApplicationContext(),First_Step_Mood.class);
                i.putExtra("itemRef", itemRef);
                startActivity(i);


            }
        });

        //Bottom Navigation, changes activities once selected
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.calender:
                        Intent intent = new Intent(Algorithm_firstpage.this, MainActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.stats:
                        Toast.makeText(Algorithm_firstpage.this, "current page", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.Mood:
                        Intent line_chart = new Intent(Algorithm_firstpage.this, Line_Chart.class);
                        startActivity(line_chart);
                        break;

                }
            }
        });


    }

}
