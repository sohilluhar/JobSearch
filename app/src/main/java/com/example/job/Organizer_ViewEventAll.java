package com.example.job;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.job.Adapter.MyAdapter;
import com.example.job.Model.Job;
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

import com.example.job.Adapter.Org_View_Eventadapter;
import com.example.job.Model.Event;

public class Organizer_ViewEventAll extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    //TextView wlcMsg;
    List<Job> events;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer__view_event_all);
        getSupportActionBar().setTitle("Job List");
        events = new ArrayList<>();
        //  wlcMsg=(TextView)findViewById(R.id.wlcmsg);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Job");


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Job job = dataSnapshot1.getValue(Job.class);
                    try {
                        if (job.getId().equals(Common.currentuser1.getPhonenumber()))
                            events.add(job);
                    } catch (Exception e) {
                    }
                }

                onEventLoad(events);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Failed", databaseError.getMessage());

            }
        });


    }

    private void onEventLoad(List<Job> events) {

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.ngoeventRecycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(recyclerView, this, events);
        recyclerView.setAdapter(adapter);
    }


}