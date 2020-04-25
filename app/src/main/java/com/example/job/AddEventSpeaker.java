package com.example.job;

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

public class AddEventSpeaker extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dbUserRef = database.getReference("users");
    DatabaseReference dbSpeakerRef = database.getReference("EventSpeaker");
    DatabaseReference dbEventRef = database.getReference("Events/" + Common.selectedEvent.getEvent_key());
    List<String> spekersevent;
    private EditText etspeakerphone;
    private Button btnaddspeaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_speaker);

        spekersevent = new ArrayList<>();

        etspeakerphone = (EditText) findViewById(R.id.etspeakerphone);

        btnaddspeaker = (Button) findViewById(R.id.btnaddspeaker);
        btnaddspeaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String strphone = "+91" + etspeakerphone.getText().toString();


                dbUserRef.orderByKey().equalTo(strphone).addListenerForSingleValueEvent
                        (new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    dbSpeakerRef.child(strphone).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists()) {

                                                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                                    String tmpspevents = ds.getValue(String.class);
                                                    spekersevent.add(tmpspevents);
                                                }


                                            }


                                            if (!spekersevent.contains(Common.selectedEvent.getEvent_key())) {
                                                spekersevent.add(Common.selectedEvent.getEvent_key());
                                                dbSpeakerRef.child(strphone).setValue(spekersevent);
                                                List<String> tmp = new ArrayList<>();
                                                tmp.add("");


                                                List<String> sp = Common.selectedEvent.getSpeakers();
                                                sp.add(strphone);


                                                dbEventRef.child("speakers").setValue(sp);

                                                dbEventRef.child("question").child(strphone).setValue(tmp);
                                                dbUserRef.child(strphone).child("extraType").setValue("Speaker");
                                                finish();
                                                Toast.makeText(AddEventSpeaker.this, "Speaker Added", Toast.LENGTH_SHORT).show();
                                            }
                                            Toast.makeText(AddEventSpeaker.this, "Speaker Already Added", Toast.LENGTH_SHORT).show();

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                } else {
                                    maketoast();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

            }
        });


    }

    private void maketoast() {
        Toast.makeText(this, "Wrong Phone Number", Toast.LENGTH_SHORT).show();

    }
}
