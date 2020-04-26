package com.example.job;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.job.Model.Job;
import com.example.job.Model.User1;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.example.job.Adapter.MyAdapter;
import com.example.job.Model.Event;

public class UserDashboard extends AppCompatActivity {
//TODO:: add payment, add qr code,notification & mail


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, dbUserRef;
    TextView wlcMsg;
    MyAdapter adapter;
    List<Job> jobs;
    Button btnViewquestion;
    //ViewPager viewPager;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.actionbar_icon, menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_bar_search) {
            Toast.makeText(this, "Search Click", Toast.LENGTH_SHORT).show();
//            Intent intent2 = new Intent(UserDashboard.this, Search.class);
//            startActivity(intent2);
        }


        if (item.getItemId() == R.id.action_bar_logout) {
            SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("remember", "false");
            editor.remove("userphone");
            editor.remove("password");
            editor.apply();

            Common.currentuser1 = null;

            Intent intent = new Intent(UserDashboard.this, MainActivity.class);
            startActivity(intent);
            finish();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Job");
        dbUserRef = firebaseDatabase.getReference("users");

        dbUserRef.child(Common.currentuser1.getPhonenumber()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Common.currentuser1 = dataSnapshot.getValue(User1.class);

                SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                Gson gson = new Gson();
                String userjson = gson.toJson(Common.currentuser);
                editor.putString("_USER", userjson);


                editor.apply();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        setContentView(R.layout.activity_profile);


        // Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        findViewById(R.id.navigation_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toast.makeText(UserDashboard.this, "Search Click", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.navigation_resume).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserDashboard.this, "Resume Click", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.navigation_upcomingEvent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserDashboard.this, "Applied job Click", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.navigation_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserDashboard.this, "Profile Click", Toast.LENGTH_SHORT).show();
            }
        });


        wlcMsg = (TextView) findViewById(R.id.wlcmsg);
        // wlcMsg.setVisibility(View.GONE);
        btnViewquestion = findViewById(R.id.btnViewquestion);


        btnViewquestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDashboard.this, ViewSpeakersEvent.class);
                startActivity(intent);
            }
        });
//load eventRecyclerFeatured
        jobs = new ArrayList<>();


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                jobs = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    Job job = dataSnapshot1.getValue(Job.class);
                    jobs.add(job);
                }

                try {
                    onEventLoad(jobs);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Failed", databaseError.getMessage());

            }
        });
        try {
            wlcMsg.setText("Hey, " + Common.currentuser1.getName());

        } catch (Exception ignore) {
        }
    }


    private void onEventLoad(List<Job> jobs) throws ParseException {


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.eventRecycler);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(recyclerView, this, jobs);
        recyclerView.setAdapter(adapter);
    }
}
