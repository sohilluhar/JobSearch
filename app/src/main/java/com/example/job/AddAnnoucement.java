package com.example.job;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.example.job.Model.Annoucement;
import com.example.job.Model.EventGoing;
import com.example.job.Model.User;
import com.example.job.utilities.SendMail;

public class AddAnnoucement extends AppCompatActivity {

    EditText annctitle, anncdescr;
    TextView evename;
    String ann;
    Button btnaddAnnc;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, userdbref, usergoing;
    List<User> users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_annoucement);

        annctitle = findViewById(R.id.new_announcement_title);
        anncdescr = findViewById(R.id.new_announcement_details);
        evename = findViewById(R.id.anneventname);
        btnaddAnnc = findViewById(R.id.btnAddAnnoucement);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Events");
        userdbref = firebaseDatabase.getReference("users");
        usergoing = firebaseDatabase.getReference("EventGoing");

        evename.setText(Common.selectedEvent.getEvent_name());

        btnaddAnnc.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                final List<Annoucement> annoucement
//                        = new ArrayList<Annoucement>();
                        = Common.selectedEvent.getAnnoucement();

                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM YYYY");
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
                LocalDateTime now = LocalDateTime.now();
                String date = dateFormatter.format(now);
                String time = timeFormatter.format(now);
                ann = annctitle.getText().toString();
                annoucement.add(new Annoucement(annctitle.getText().toString(), anncdescr.getText().toString(), date, time));

                databaseReference.child(Common.selectedEvent.getEvent_key()).child("annoucement").setValue(annoucement);
                //getSupportActionBar().hide();
                final EventGoing[] eventGoingtmp = new EventGoing[1];

                users = new ArrayList<User>();
                usergoing.child(Common.selectedEvent.getEvent_key()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        eventGoingtmp[0] = dataSnapshot.getValue(EventGoing.class);


                        for (int i = 0; i < eventGoingtmp[0].getGoingppl().size(); i++) {
                            getUsers(eventGoingtmp[0].getGoingppl().get(i), i, eventGoingtmp[0].getGoingppl().size());
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });

    }

    private void getUsers(final String phone, final int i, final int max) {
        userdbref.orderByKey().equalTo(phone).addListenerForSingleValueEvent
                (new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {
                            User tmp = dataSnapshot.child(phone).getValue(User.class);
                            users.add(tmp);
                            if (i == max - 1) {
                                sendemail(users);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

    private void sendemail(List<User> users) {

        System.out.println("Button Click");
        String to = "";
        for (int i = 0; i <= users.size(); i++) {
            to = to + users.get(i).getEmail() + ",";
        }
        //todo:: Add Mail
        //to = "sohil.l@somaiya.edu";
        //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
        try {
            SendMail.sendMail(to.substring(0, to.length() - 1), "Test Mail: New Announcement -" + Common.selectedEvent.getEvent_name(), "<p>New announcement has been added </p><br><p>Ignore this mail<p>");
//            SendMail.sendMail(to.substring(0, to.length() - 1), "Test Mail: New Announcement - " + Common.selectedEvent.getEvent_name(), "<p>New announcement has been added </p>");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("msgsend", e.toString());
        }
        Toast.makeText(this, "Announcement Added", Toast.LENGTH_SHORT).show();
        finish();
    }

}
