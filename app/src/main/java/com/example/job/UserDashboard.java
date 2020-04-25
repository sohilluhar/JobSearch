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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.example.job.Adapter.FeturedEventAdapter;
import com.example.job.Adapter.MyAdapter;
import com.example.job.Model.Event;
import com.example.job.Model.User;

public class UserDashboard extends AppCompatActivity {
//TODO:: add payment, add qr code,notification & mail

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
    DatabaseReference databaseReference, dbUserRef;
    TextView wlcMsg;
    MyAdapter adapter;
    FeturedEventAdapter feturedEventAdapter;
    List<Event> events;
    Button btnViewquestion;
    //ViewPager viewPager;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    //     toolbar.setTitle("Shop");
//                    Intent intent = new Intent(Home.this, ngo_home.class);
//                    startActivity(intent);
//
//                    Toast.makeText(Home.this, "Home Click", Toast.LENGTH_SHORT).show();
//                    return true;
                case R.id.navigation_search:
//                    Intent intent = new Intent(Home.this, User_Search_Event.class);
//                    startActivity(intent);
//                    finish();

//                    Toast.makeText(ProfileActivity.this, "Search Click", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_upcomingEvent:
                    Intent intent1 = new Intent(UserDashboard.this, UserGoingEvent.class);
                    startActivity(intent1);
                    finish();
                    return true;
                case R.id.navigation_resume:

                    Intent intent3 = new Intent(UserDashboard.this, resume_dashboard.class);
                    startActivity(intent3);
                    finish();
                    return true;

                case R.id.navigation_profile:

                    Intent intent2 = new Intent(UserDashboard.this, UserProfile.class);
                    startActivity(intent2);
                    finish();
                    return true;
                default:
                    return true;
            }
//            return true;
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
//            Toast.makeText(this, "Search Click", Toast.LENGTH_SHORT).show();
            Intent intent2 = new Intent(UserDashboard.this, Search.class);
            startActivity(intent2);
        }

        if (item.getItemId() == 76445) {
            Intent intent = new Intent(UserDashboard.this, ViewSpeakersEvent.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.action_bar_logout) {
            SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("remember", "false");
            editor.remove("userphone");
            editor.remove("password");
            editor.apply();

            Common.currentuser = null;

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
        databaseReference = firebaseDatabase.getReference("Events");
        dbUserRef = firebaseDatabase.getReference("users");

        dbUserRef.child(Common.currentuser.getUserphone()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Common.currentuser = dataSnapshot.getValue(User.class);

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


        wlcMsg = (TextView) findViewById(R.id.wlcmsg);
        wlcMsg.setVisibility(View.GONE);
        btnViewquestion = findViewById(R.id.btnViewquestion);


        if (Common.currentuser.getType().equals("Sponsor")) {
            navigation.setVisibility(View.GONE);
            wlcMsg.setVisibility(View.GONE);
        }
//        try {
//
//            if (Common.currentuser.getExtraType().equals("Speaker")) {
//                btnViewquestion.setVisibility(View.VISIBLE);
//            }
//
//        } catch (Exception e) {
//
//        }

        btnViewquestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDashboard.this, ViewSpeakersEvent.class);
                startActivity(intent);
            }
        });
//load eventRecyclerFeatured
        events = new ArrayList<>();

        databaseReference.orderByChild("ngo_logo").equalTo("True").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    Event event = dataSnapshot1.getValue(Event.class);
                    events.add(event);
                }

                try {
                    featuredEventLoad(events);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Failed", databaseError.getMessage());

            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                events = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    Event event = dataSnapshot1.getValue(Event.class);
                    events.add(event);
                }

                try {
                    onEventLoad(events);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Failed", databaseError.getMessage());

            }
        });


        wlcMsg.setText("Hey, " + Common.currentuser.getName());
    }

    private void featuredEventLoad(List<Event> events) throws ParseException {


        //SORT EVENT
        Collections.sort(events, dateNewOld);


        Collections.reverse(events);


        // list.sort( dateNewOld );
//Collections.reverse(list, dateNewOld);
//        print(list);

        //SORT EVENTS DONE


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.eventRecyclerFeatured);
//        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        feturedEventAdapter = new FeturedEventAdapter(recyclerView, this, events);
        recyclerView.setAdapter(feturedEventAdapter);

        // viewPager.setAdapter(adapter);
        //   viewPager.setPadding(130, 0, 130, 0);
//        //   viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//        @Override
//        public void onPageScrolled ( int position, float positionOffset, int positionOffsetPixels){
//
//            if (position < (adapter.getCount() - 1) && position < (colors.length - 1)) {
//                viewPager.setBackgroundColor(
//
//                        (Integer) argbEvaluator.evaluate(
//                                positionOffset,
//                                colors[position],
//                                colors[position + 1]
//                        )
//                );
//            } else {
//                viewPager.setBackgroundColor(colors[colors.length - 1]);
//            }
//        }
//
//        @Override
//        public void onPageSelected ( int position){
//
//        }
//
//        @Override
//        public void onPageScrollStateChanged ( int state){
//
//        }
//    });


    }

    private void onEventLoad(List<Event> events) throws ParseException {


        //SORT EVENT
        Collections.sort(events, dateNewOld);
        Collections.reverse(events);


        //remove past events

        for (int i = 0; i < events.size(); i++) {

            //long timestamp = new SimpleDateFormat("dd MMM yyyy").parse(new Date()).getTime();

            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
            Date today = sdf.parse(sdf.format(new Date()));
            long timestamp = today.getTime();
            @SuppressLint("SimpleDateFormat")
            Date dt = new SimpleDateFormat("dd MMM yyyy").parse(events.get(i).getDate());
            long eventtimestamp = dt.getTime();
            if (timestamp > eventtimestamp) {
                events.remove(i);
            }
        }
        // list.sort( dateNewOld );
//Collections.reverse(list, dateNewOld);
//        print(list);

        //SORT EVENTS DONE


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.eventRecycler);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(recyclerView, this, events);
        recyclerView.setAdapter(adapter);
    }
}
