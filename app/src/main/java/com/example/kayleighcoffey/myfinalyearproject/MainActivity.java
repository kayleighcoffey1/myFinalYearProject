package com.example.kayleighcoffey.myfinalyearproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.design.widget.BottomNavigationView;
import android.widget.TextView;

import com.example.kayleighcoffey.myfinalyearproject.algorithm.Algorithm_firstpage;
import com.example.kayleighcoffey.myfinalyearproject.Constructors.GetData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FirebaseDatabase database;
    private FirebaseAuth auth;

    private DatabaseReference myRef;
    private ListView mListView;
    private ArrayList<String> mDate = new ArrayList<>();
    private ArrayList<String> mKeys = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReferenceFromUrl("https://finalyearproject-e0102.firebaseio.com/0/users/user/days");
        System.out.println("---------------Main Activity----------");


        auth = FirebaseAuth.getInstance();

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

                String itemRef = mKeys.get(position);
                Intent i = new Intent(getApplicationContext(),EachDay.class);
                i.putExtra("itemRef", itemRef);
                startActivity(i);


            }
        });

        //Bottom Navigation, changes activities once selected
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);


        bottomNavigationView.setOnNavigationItemSelectedListener
            (new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.calender:
                            break;
                        case R.id.stats:
                            Intent intent = new Intent(MainActivity.this, Algorithm_firstpage.class);
                            startActivity(intent);
                            break;
                        case R.id.Mood:
                            Intent intent2 = new Intent(MainActivity.this, Line_Chart.class);
                            startActivity(intent2);
                            break;
                    }
                    return false;
                }
            });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.log_out:
                FirebaseAuth.getInstance().signOut();
                Intent intentLogout = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intentLogout);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }







}
