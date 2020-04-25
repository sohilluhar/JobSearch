package com.example.job;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AskQuestion extends AppCompatActivity {
    Button btnsubmitquestion;
    EditText etQuestion;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, ref1;
    List<String> question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question);

        btnsubmitquestion = findViewById(R.id.btnsubmitquestion);
        etQuestion = findViewById(R.id.etQuestion);


        firebaseDatabase = FirebaseDatabase.getInstance();

        question = new ArrayList<>();


        final Intent intent = getIntent();
        final String speakerphone = intent.getStringExtra("userphone");

        databaseReference = firebaseDatabase.getReference("Events/" + Common.selectedEvent.getEvent_key());


        btnsubmitquestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                assert speakerphone != null;
                databaseReference.child("question").child(speakerphone).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {

                            for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                String tmpque = ds.getValue(String.class);
//                        users.add(tmpusr);

                                question.add(tmpque);

                            }
                            question.add(etQuestion.getText().toString());


                            databaseReference.child("question").child(speakerphone).setValue(question);

                            Toast.makeText(AskQuestion.this, "Question Submitted", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });

    }
}
