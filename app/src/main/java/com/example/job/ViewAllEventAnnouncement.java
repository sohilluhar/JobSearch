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

import java.util.ArrayList;
import java.util.List;

import com.example.job.Adapter.ViewAnnouncementAdapter;
import com.example.job.Model.Annoucement;

public class ViewAllEventAnnouncement extends AppCompatActivity {


    FirebaseDatabase firebaseDatabase;
    DatabaseReference dbAnnounceRef;
    List<Annoucement> announcements;
    ViewAnnouncementAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_event_announcement);

//        annoucements = Common.selectedEvent.getAnnoucement();

        firebaseDatabase = FirebaseDatabase.getInstance();
        dbAnnounceRef = firebaseDatabase.getReference("Events/" + Common.selectedEvent.getEvent_key() + "/annoucement");

        dbAnnounceRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                announcements = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

//                    Log.e("ann",announcements.toString());
                    Annoucement tmp = dataSnapshot1.getValue(Annoucement.class);
                    assert tmp != null;
                    announcements.add(tmp);
                }


                setRecycleAnnouncement(announcements);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Failed", databaseError.getMessage());

            }
        });
    }

    private void setRecycleAnnouncement(List<Annoucement> announcement) {


        //SORT EVENT
        // list.sort( dateNewOld );
//Collections.reverse(list, dateNewOld);
//        print(list);

        //SORT EVENTS DONE
        announcement.remove(0);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleAnnouncement);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ViewAnnouncementAdapter(recyclerView, this, announcement);
        recyclerView.setAdapter(adapter);
    }
}
