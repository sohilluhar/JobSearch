package com.example.job;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.job.Model.Sponser;

public class SponsorProfile extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dbUserRef = database.getReference("users/" + Common.currentuser.getUserphone());
    DatabaseReference dbSponsorref = database.getReference("Sponsers/" + Common.currentuser.getUserphone());

    Button btnUpdateSponsorProfile;

    EditText etsponsorname;
    EditText etprofileSponsorDescription;
    EditText etprofileOffer;
    EditText etporfileSponsorEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor_profile);
        btnUpdateSponsorProfile = findViewById(R.id.btnUpdateSponsorProfile);
        etsponsorname = findViewById(R.id.etsponsorname);
        etprofileSponsorDescription = findViewById(R.id.etprofileSponsorDescription);
        etprofileOffer = findViewById(R.id.etprofileOffer);
        etporfileSponsorEmail = findViewById(R.id.etporfileSponsorEmail);

        etsponsorname.setText(Common.currentSponsor.getName());
        etprofileSponsorDescription.setText(Common.currentSponsor.getDescription());
        etprofileOffer.setText(Common.currentSponsor.getType());
        etporfileSponsorEmail.setText(Common.currentSponsor.getEmailid());


        btnUpdateSponsorProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sponser tmp = new Sponser(
                        Common.currentSponsor.getCategory(),
                        etprofileSponsorDescription.getText().toString(),
                        etporfileSponsorEmail.getText().toString(),
                        Common.currentSponsor.getKey(),
                        etsponsorname.getText().toString(),
                        etprofileOffer.getText().toString(),
                        Common.currentSponsor.getImg()
                );

                dbSponsorref.setValue(tmp);

                dbUserRef.child("name").setValue(etsponsorname.getText().toString());
                Toast.makeText(SponsorProfile.this, "Profile Updated", Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(SponsorProfile.this, Sponsor_Dashboard.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
