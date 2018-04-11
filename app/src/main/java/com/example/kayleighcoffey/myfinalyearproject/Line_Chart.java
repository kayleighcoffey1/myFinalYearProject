package com.example.kayleighcoffey.myfinalyearproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.kayleighcoffey.myfinalyearproject.Constructors.GetData;

import com.example.kayleighcoffey.myfinalyearproject.algorithm.Algorithm_firstpage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

import static com.example.kayleighcoffey.myfinalyearproject.R.*;

/**
 * Created by Kayleigh Coffey on 05/04/2018.
 */

public class Line_Chart extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private ArrayList<Float> fMood = new ArrayList<>();
    LineGraphSeries<DataPoint> series;
    //Storing Moods
    private String mContent   = "Content";
    private String mAnxious   = "Anxious";
    private String mDepressed = "Depressed";
    private String mGood      = "Good";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.linechart);
        database = FirebaseDatabase.getInstance();
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Mood History");

        final GraphView graph = findViewById(R.id.graph);
        series = new LineGraphSeries<>();

        series.setTitle("Moods");
        series.setColor(Color.WHITE);
        series.setDataPointsRadius(10);
        series.setThickness(8);
        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    // show normal x values
                    return super.formatLabel(value, isValueX) + "day";
                } else {
                    // show currency for y values
                    return super.formatLabel(value, isValueX) + "mood";
                }
            }
        });
        for (int i = 0; i < 27; i++)
        {

            myRef = database.getReferenceFromUrl("https://finalyearproject-e0102.firebaseio.com/0/users/user/days/day/" + i);

            final int finalI = i;
            myRef.child("personal_logs").child("health_logs").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String mood = (String) dataSnapshot.child("Mood").getValue();

                    if (Objects.equals(mood, mAnxious)) {
                        float intMood = 2;
                        series.appendData(new DataPoint(finalI, intMood), true, 27);
                        graph.addSeries(series);

                    } else if (Objects.equals(mood, mContent)) {
                        float intMood = 5;
                        series.appendData(new DataPoint(finalI, intMood), true, 27);
                        graph.addSeries(series);

                    } else if (Objects.equals(mood, mDepressed)) {
                        float intMood = 0;
                        series.appendData(new DataPoint(finalI, intMood), true, 27);
                        graph.addSeries(series);

                    } else if (Objects.equals(mood, mGood)) {

                        float intMood = 10;
                        series.appendData(new DataPoint(finalI, intMood), true, 27);
                        graph.addSeries(series);

                    } else {

                        float intMood = 0;
                        series.appendData(new DataPoint(finalI, intMood), true, 27);
                        graph.addSeries(series);

                    }

                    System.out.println(fMood);

                }// end on datachange
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }



            });


        }


        //Bottom Navigation, changes activities once selected
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);


        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.calender:
                                Intent mainActivity = new Intent(Line_Chart.this, MainActivity.class);
                                startActivity(mainActivity);

                            case R.id.stats:
                                Intent intent = new Intent(Line_Chart.this, Algorithm_firstpage.class);
                                startActivity(intent);
                                break;
                            case R.id.Mood:

                                break;
                        }
                        return false;
                    }
                });
    }

}






