package com.example.job;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
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

import java.util.ArrayList;
import java.util.List;

import com.example.job.Adapter.EventListofQuestion;
import com.example.job.Model.Event;

public class ViewEventQuestion extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    TextView msg;
    TextView msg1;
    EventListofQuestion adapter;
    List<Event> events;
    Button btnViewquestion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event_question);

        msg = findViewById(R.id.tveventnamequestion);
        msg1 = findViewById(R.id.tvNoques);
        msg.setText(Common.selectedEvent.getEvent_name());


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Events/" + Common.selectedEvent.getEvent_key() + "/" + "question/" + Common.currentuser.getUserphone());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> questions = new ArrayList<>();

                if (dataSnapshot.exists()) {
                    int size = (int) dataSnapshot.getChildrenCount();
                    int i = 0;
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        String que = ds.getValue(String.class);
                        questions.add(que);

                    }
                    questions.remove(0);


                }
                onLoad(questions);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @SuppressLint("SetTextI18n")
    private void onLoad(List<String> questions) {

        if (questions.size() == 0) {
            msg1.setVisibility(View.VISIBLE);
        } else
            msg1.setVisibility(View.GONE);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvEventQuestion);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EventListofQuestion(recyclerView, this, questions);
        recyclerView.setAdapter(adapter);
    }
}
