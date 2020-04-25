package com.example.job;

import android.os.Bundle;
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

import com.example.job.Adapter.EventListofSpeaker;
import com.example.job.Model.User;

public class ViewListofSpeaker extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference dbUserRef;
    TextView wlcMsg;
    List<User> users;
    EventListofSpeaker adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_listof_speaker);

        users = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        dbUserRef = firebaseDatabase.getReference("users");

        List<String> tmp = Common.selectedEvent.getSpeakers();
        for (int i = 1; i < tmp.size(); i++) {
            getUsers(tmp.get(i), i, tmp.size());
        }


    }


    private void getUsers(final String phone, final int i, final int max) {
        dbUserRef.orderByKey().equalTo(phone).addListenerForSingleValueEvent
                (new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            User tmp = dataSnapshot.child(phone).getValue(User.class);
                            users.add(tmp);

                        }
                        if (i == max - 1) {
                            onLoad(users);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

    private void onLoad(List<User> users) {


        //SORT EVENT
        // list.sort( dateNewOld );
//Collections.reverse(list, dateNewOld);
//        print(list);

        //SORT EVENTS DONE


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rveventSpeakerList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EventListofSpeaker(recyclerView, this, users);
        recyclerView.setAdapter(adapter);
    }
}
