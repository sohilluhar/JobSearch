package com.example.job;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    DatabaseReference databaseReference;
    //TextView wlcMsg;
    List<Event> events;
    Org_View_Eventadapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer__view_event_all);
        getSupportActionBar().setTitle("Event List");
        events = new ArrayList<>();
        //  wlcMsg=(TextView)findViewById(R.id.wlcmsg);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Events");


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Event event = dataSnapshot1.getValue(Event.class);
                    try {
                        if (event.getNgo_id().equals(Common.currentuser.getUserphone()))
                            events.add(event);
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


        //     wlcMsg.setText("Hey, "+ Common.currentuser.getName());


//        adapter.setiLoadMore(new ILoadMore() {
//            @Override
//            public void onLoadMore() {
//                if(events.size()<=20){
//                    events.add(null);
//                    adapter.notifyItemInserted(events.size()-1);
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            events.remove(events.size()-1);
//                            adapter.notifyItemRemoved(events.size());
//
//                            int index=events.size();
//                            int end=index+10;
//
//                            adapter.notifyDataSetChanged();
//                            adapter.setLoaded();
//
//                        }
//                    },5000);
//                }
//                else {
//                    Toast.makeText(Home.this, "Data Loaded", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

    }

    private void onEventLoad(List<Event> events) {

        //SORT EVENT
        Collections.sort(events, dateNewOld);
        Collections.reverse(events);
        // list.sort( dateNewOld );
//Collections.reverse(list, dateNewOld);
//        print(list);

        //SORT EVENTS DONE


//        Log.e("Event",events.size()+"");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.ngoeventRecycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Org_View_Eventadapter(recyclerView, this, events);
        recyclerView.setAdapter(adapter);
    }


}