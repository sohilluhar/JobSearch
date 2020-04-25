package com.example.job;

import android.os.Bundle;
import android.widget.Button;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.example.job.Adapter.SpeakerEvent;
import com.example.job.Model.Event;

public class ViewSpeakersEvent extends AppCompatActivity {

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
    DatabaseReference databaseReference, dbSpeakerevent;
    TextView wlcMsg;
    SpeakerEvent adapter;
    List<Event> events;
    Button btnViewquestion;

    List<String> speakereventkey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_speakers_event);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Events");
        dbSpeakerevent = firebaseDatabase.getReference("EventSpeaker/" + Common.currentuser.getUserphone());
        events = new ArrayList<>();
        speakereventkey = new ArrayList<>();

        dbSpeakerevent.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    int size = (int) dataSnapshot.getChildrenCount();
                    int i = 0;
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        String eventid = ds.getValue(String.class);
//                        users.add(tmpusr);
                        getEvent(eventid, i, size);
                        i++;

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void getEvent(final String phone, final int i, final int max) {
        databaseReference.orderByKey().equalTo(phone).addListenerForSingleValueEvent
                (new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Event tmp = dataSnapshot.child(phone).getValue(Event.class);
                            events.add(tmp);


                        }

                        onLoad(events);
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }


    private void onLoad(List<Event> events) {


        //SORT EVENT
        Collections.sort(events, dateNewOld);
        Collections.reverse(events);
        // list.sort( dateNewOld );
//Collections.reverse(list, dateNewOld);
//        print(list);

        //SORT EVENTS DONE


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvSpeakersEvent);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SpeakerEvent(recyclerView, this, events);
        recyclerView.setAdapter(adapter);
    }
}
