package com.example.job;

import android.os.Bundle;

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

import com.example.job.Adapter.UserListofPplGoingAdapter;
import com.example.job.Model.User;

public class ViewRequestedConnection extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference dbUserRef, dbUserRefAll;
    List<User> users;
    UserListofPplGoingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_requested_connection);
        users = new ArrayList<>();
        //wlcMsg = (TextView) findViewById(R.id.wlcmsg);
        firebaseDatabase = FirebaseDatabase.getInstance();
        dbUserRef = firebaseDatabase.getReference("users/" + Common.currentuser.getUserphone());
        dbUserRefAll = firebaseDatabase.getReference("users");


        dbUserRef.child("requestedconnection").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    int size = (int) dataSnapshot.getChildrenCount();
                    int i = 0;
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        String tmpusrphone = ds.getValue(String.class);
//                        users.add(tmpusr);
                        getUsers(tmpusrphone, i, size);
                        i++;

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //     wlcMsg.setText("Hey, " + Common.currentuser.getName());
    }


    private void getUsers(final String phone, final int i, final int max) {
        dbUserRefAll.orderByKey().equalTo(phone).addListenerForSingleValueEvent
                (new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            User tmp = dataSnapshot.child(phone).getValue(User.class);
                            users.add(tmp);


                        }

                        onLoad(users);
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


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvUsersrequestedconnection);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserListofPplGoingAdapter(recyclerView, this, users);
        recyclerView.setAdapter(adapter);
    }
}