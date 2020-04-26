package com.example.job;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.job.Model.UserResume;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.Calendar;

public class Resume2 extends AppCompatActivity {


    final int PICK_IMAGE_REQUEST = 71;
    FirebaseStorage storage;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dbUserRef = database.getReference("users/" + Common.currentuser1.getPhonenumber());
    CircularImageView imguserprofile;
    DatabaseReference dbresumeref = database.getReference("Resume");

    Calendar c;
    DatePickerDialog datePickerDialog;
    EditText etsscname, etsscboard, etsscper;
    EditText ethscname, ethscboard, ethscper;
    EditText etugname, etugboard, etugper;
    EditText etpgname, etpgboard, etpgper;
    Spinner gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume2);


        etsscname = findViewById(R.id.resumesscname);
        etsscboard = findViewById(R.id.resume_sscboard);
        etsscper = findViewById(R.id.resume_sscper);


        etsscname.setText(Common.userresume.getSscname());
        etsscboard.setText(Common.userresume.getSscboard());
        etsscper.setText(Common.userresume.getSscper());


        ethscname = findViewById(R.id.resumehscname);
        ethscboard = findViewById(R.id.resume_hscboard);
        ethscper = findViewById(R.id.resume_hscper);


        ethscname.setText(Common.userresume.getHscname());
        ethscboard.setText(Common.userresume.getHscboard());
        ethscper.setText(Common.userresume.getHscper());


        etpgname = findViewById(R.id.resumepgname);
        etpgboard = findViewById(R.id.resume_pgboard);
        etpgper = findViewById(R.id.resume_pgper);


        etpgname.setText(Common.userresume.getPgname());
        etpgboard.setText(Common.userresume.getPgboard());
        etpgper.setText(Common.userresume.getPgper());

        etugname = findViewById(R.id.resumeugname);
        etugboard = findViewById(R.id.resume_ugboard);
        etugper = findViewById(R.id.resume_ugper);


        etugname.setText(Common.userresume.getUgname());
        etugboard.setText(Common.userresume.getUgboard());
        etugper.setText(Common.userresume.getUgper());

        findViewById(R.id.saveresume2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savetodb();
            }
        });
    }

    private void savetodb() {

        String strsscname = etsscname.getText().toString();
        String strsscboard = etsscboard.getText().toString();
        String strsscper = etsscper.getText().toString();

        String strhscname = ethscname.getText().toString();
        String strhscboard = ethscboard.getText().toString();
        String strhscper = ethscper.getText().toString();

        String strpgname = etpgname.getText().toString();
        String strpgboard = etpgboard.getText().toString();
        String strpgper = etpgper.getText().toString();


        String strugname = etugname.getText().toString();
        String strugboard = etugboard.getText().toString();
        String strugper = etugper.getText().toString();
//        this.fullname = fullname;
//        this.url = url;
//        this.phonenumber = phonenumber;
//        this.email = email;
//        this.gender = gender;
//        this.dob = dob;
//        this.location = location;

        //TODO: Change Resume
        UserResume resume = Common.userresume;

        resume.setSscboard(strsscboard);
        resume.setSscname(strsscname);
        resume.setSscper(strsscper);

        resume.setHscboard(strhscboard);
        resume.setHscname(strhscname);
        resume.setHscper(strhscper);

        resume.setPgboard(strpgboard);
        resume.setPgname(strpgname);
        resume.setPgper(strpgper);

        resume.setUgboard(strugboard);
        resume.setUgname(strugname);
        resume.setUgper(strugper);

        resume.setStatus("50");


        dbresumeref.child(Common.currentuser1.getPhonenumber() + "").setValue(resume);
        Toast.makeText(this, "Education Details Saved", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Resume2.this, Resume3.class);
        startActivity(intent);
        finish();
    }
}
