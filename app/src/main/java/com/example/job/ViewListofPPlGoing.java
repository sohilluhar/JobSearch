package com.example.job;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.job.Model.JobApply;
import com.example.job.Model.User1;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import com.example.job.Adapter.ListofPplGoingAdapter;
import com.example.job.Model.EventGoing;
import com.example.job.Model.User;

public class ViewListofPPlGoing extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference everntgoing, dbUserRef;
    TextView wlcMsg;
    List<User1> users;
    ListofPplGoingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_listof_p_pl_going);
        users = new ArrayList<>();
        //wlcMsg = (TextView) findViewById(R.id.wlcmsg);
        firebaseDatabase = FirebaseDatabase.getInstance();
        dbUserRef = firebaseDatabase.getReference("users");
        everntgoing = firebaseDatabase.getReference("JobApply");

        everntgoing.child(Common.selectedJob.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                List<String> jobApplies = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

//                    Log.e("ann",announcements.toString());
                    JobApply tmp = dataSnapshot1.getValue(JobApply.class);
                    assert tmp != null;
                    jobApplies.add(tmp.getPhone());

                }


                for (int i = 0; i < jobApplies.size(); i++) {
                    getUsers(jobApplies.get(i), i, jobApplies.size());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //     wlcMsg.setText("Hey, " + Common.currentuser.getName());
    }

    private void getUsers(final String phone, final int i, final int max) {
        dbUserRef.orderByKey().equalTo(phone).addListenerForSingleValueEvent
                (new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            User1 tmp = dataSnapshot.child(phone).getValue(User1.class);
                            users.add(tmp);

                            if (i == max - 1) {
                                onEventLoad(users);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

    private void onEventLoad(List<User1> users) {


        //SORT EVENT
        // list.sort( dateNewOld );
//Collections.reverse(list, dateNewOld);
//        print(list);

        //SORT EVENTS DONE


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.eventPeopleGoing);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ListofPplGoingAdapter(recyclerView, this, users);
        recyclerView.setAdapter(adapter);
    }
}
