package com.example.job;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import com.example.job.Adapter.MyAdapter;
import com.example.job.Model.Event;

public class SearchResult extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    TextView tv;
    MyAdapter adapter;
    List<Event> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        tv = findViewById(R.id.searchres_catname);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Events");

        Intent intent = getIntent();
        String cat = intent.getStringExtra("category");
        String date = intent.getStringExtra("date");
        if (!cat.equals("NONE")) {

            tv.setText("Category: " + cat);
            databaseReference.orderByChild("category").equalTo(cat.trim()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    events = new ArrayList<>();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                        Event event = dataSnapshot1.getValue(Event.class);
                        events.add(event);
                    }

                    onEventLoad(events);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("Failed", databaseError.getMessage());

                }
            });

        } else if (!date.equals("NONE")) {

            tv.setText("Date: " + date);
            databaseReference.orderByChild("date").equalTo(date.trim()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    events = new ArrayList<>();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                        Event event = dataSnapshot1.getValue(Event.class);
                        events.add(event);
                    }

                    onEventLoad(events);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("Failed", databaseError.getMessage());

                }
            });

        }


//        recycleSearchResult
    }

    private void onEventLoad(List<Event> events) {


        // list.sort( dateNewOld );
//Collections.reverse(list, dateNewOld);
//        print(list);

        //SORT EVENTS DONE

//
//        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleSearchResult);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new MyAdapter(recyclerView, this, events);
//        recyclerView.setAdapter(adapter);
    }
}
