package com.example.job;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.example.job.Adapter.MyAdapter;
import com.example.job.Model.Event;
import com.example.job.Model.User;

public class UserGoingEvent extends AppCompatActivity {
    public Comparator<Event> dateNewOld = new Comparator<Event>() {
        @Override
        public int compare(Event o1, Event o2) {
            DateFormat f = new SimpleDateFormat("dd MMM yyyy");
            try {
                return f.parse(o2.getDate()).compareTo(f.parse(o1.getDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return 0;
        }
    };
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, userref, dbUserRef;
    TextView wlcMsg;
    List<Event> events;
    MyAdapter adapter;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    //     toolbar.setTitle("Shop");
                    Intent intent = new Intent(UserGoingEvent.this, UserDashboard.class);
                    startActivity(intent);
                    finish();
//
//                    Toast.makeText(Home.this, "Home Click", Toast.LENGTH_SHORT).show();
                   return true;
                case R.id.navigation_search:
//                    Intent intent = new Intent(Home.this, User_Search_Event.class);
//                    startActivity(intent);
//                    finish();

//                    Toast.makeText(Home.this, "Search Click", Toast.LENGTH_SHORT).show();
                   return true;
                case R.id.navigation_upcomingEvent:
//                    Intent intent1 = new Intent(Home.this, User_Upcoming_Event.class);
//                    startActivity(intent1);
//                    finish();
                    return true;

                case R.id.navigation_resume:

                    Intent intent3 = new Intent(UserGoingEvent.this, resume_dashboard.class);
                    startActivity(intent3);
                    finish();
                   return true;


                case R.id.navigation_profile:

                    Intent intent2 = new Intent(UserGoingEvent.this, UserProfile.class);
                    startActivity(intent2);
                    finish();
                   return true;
            }
            return true;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_going_event);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_upcomingEvent);

        events = new ArrayList<>();
        wlcMsg = (TextView) findViewById(R.id.wlcmsg);
        wlcMsg.setVisibility(View.GONE);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Events");
        dbUserRef = firebaseDatabase.getReference("users");
        dbUserRef.orderByKey().equalTo(Common.currentuser.getUserphone()).addListenerForSingleValueEvent
                (new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            User currentuser = dataSnapshot.child(Common.currentuser.getUserphone()).getValue(User.class);

                            Common.currentuser = currentuser;


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Event event = dataSnapshot1.getValue(Event.class);
                    if (Common.currentuser.getGoingevent().contains(event.getEvent_key()))
                        events.add(event);
                }

                onEventLoad(events);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Failed", databaseError.getMessage());

            }
        });


        wlcMsg.setText("Hey, " + Common.currentuser.getName());
    }

    private void onEventLoad(List<Event> events) {


        //SORT EVENT
        Collections.sort(events, dateNewOld);
        Collections.reverse(events);
        // list.sort( dateNewOld );
//Collections.reverse(list, dateNewOld);
//        print(list);

        //SORT EVENTS DONE


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.eventRecycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(recyclerView, this, events);
        recyclerView.setAdapter(adapter);
    }
}