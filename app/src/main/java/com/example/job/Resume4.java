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

public class Resume4 extends AppCompatActivity {
    final int PICK_IMAGE_REQUEST = 71;
    FirebaseStorage storage;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dbUserRef = database.getReference("users/" + Common.currentuser1.getPhonenumber());
    CircularImageView imguserprofile;
    DatabaseReference dbresumeref = database.getReference("Resume");

    Calendar c;
    DatePickerDialog datePickerDialog;
    EditText resumeprojectname, resumeprojectdescription, resumeprojectyear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume4);

        resumeprojectname = findViewById(R.id.resumeprojectname);
        resumeprojectdescription = findViewById(R.id.resumeprojectdescription);
        resumeprojectyear = findViewById(R.id.resumeprojectyear);


        resumeprojectname.setText(Common.userresume.getProjectname());
        resumeprojectdescription.setText(Common.userresume.getProjectdescription());
        resumeprojectyear.setText(Common.userresume.getProjectyear());

        findViewById(R.id.saveresume4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savetodb();
            }
        });


    }

    private void savetodb() {

        String strresumeprojectname = resumeprojectname.getText().toString();
        String strresumeprojectdescription = resumeprojectdescription.getText().toString();
        String strresumeprojectyear = resumeprojectyear.getText().toString();

//        this.fullname = fullname;
//        this.url = url;
//        this.phonenumber = phonenumber;
//        this.email = email;
//        this.gender = gender;
//        this.dob = dob;
//        this.location = location;

        //TODO: Change Resume
        UserResume resume = Common.userresume;
        resume.setProjectname(strresumeprojectname);
        resume.setProjectdescription(strresumeprojectdescription);
        resume.setProjectyear(strresumeprojectyear);

        resume.setStatus("100");

        dbresumeref.child(Common.currentuser1.getPhonenumber() + "").setValue(resume);
        Toast.makeText(this, "Resume Created", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Resume4.this, UserDashboard.class);
        startActivity(intent);
        finish();
    }
}
