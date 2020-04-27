package com.example.job;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.job.Model.UserResume;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.Calendar;

public class Resume3 extends AppCompatActivity {
    final int PICK_IMAGE_REQUEST = 71;
    FirebaseStorage storage;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dbUserRef = database.getReference("users/" + Common.currentuser1.getPhonenumber());
    CircularImageView imguserprofile;
    DatabaseReference dbresumeref = database.getReference("Resume");

    Calendar c;
    DatePickerDialog datePickerDialog;
    EditText resumeexpcompanyname, resumeexpdesignation, resumeexpdescription,
            resumeexpstartdate, resumeexpenddate;
    Spinner resumeexptype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume3);

        resumeexpcompanyname = findViewById(R.id.resumeexpcompanyname);
        resumeexpdesignation = findViewById(R.id.resumeexpdesignation);
        resumeexpdescription = findViewById(R.id.resumeexpdescription);
        resumeexpstartdate = findViewById(R.id.resumeexpstartdate);
        resumeexpenddate = findViewById(R.id.resumeexpenddate);
        resumeexpcompanyname = findViewById(R.id.resumeexpcompanyname);
        resumeexptype = findViewById(R.id.resumeexptype);

        resumeexpstartdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                Log.e("DAte", "test");

                datePickerDialog = new DatePickerDialog(Resume3.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int myear, int mmonth, int mdayOfMonth) {

                        String[] MONTH =
                                {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

                        Log.e("Month", mmonth + "");

                        String tmpday = mdayOfMonth + "";


                        resumeexpstartdate.setText(tmpday + " " + MONTH[mmonth] + " " + myear);
                    }
                }, year, month, day);

                datePickerDialog.show();
            }
        });
        resumeexpenddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                Log.e("DAte", "test");

                datePickerDialog = new DatePickerDialog(Resume3.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int myear, int mmonth, int mdayOfMonth) {

                        String[] MONTH =
                                {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

                        Log.e("Month", mmonth + "");

                        String tmpday = mdayOfMonth + "";


                        resumeexpenddate.setText(tmpday + " " + MONTH[mmonth] + " " + myear);
                    }
                }, year, month, day);

                datePickerDialog.show();
            }
        });


        resumeexpcompanyname.setText(Common.userresume.getExpcompanyname());
        resumeexpdesignation.setText(Common.userresume.getExpdesignation());
        resumeexpdescription.setText(Common.userresume.getExpdescription());
        resumeexpstartdate.setText(Common.userresume.getExpstartdate());
        resumeexpenddate.setText(Common.userresume.getExpenddate());
    }

    private void savetodb() {

        String strresumeexpcompanyname = resumeexpcompanyname.getText().toString();
        String strresumeexpdesignation = resumeexpdesignation.getText().toString();
        String strresumeexpdescription = resumeexpdescription.getText().toString();
        String strresumeexpstartdate = resumeexpstartdate.getText().toString();
        String strresumeexpenddate = resumeexpenddate.getText().toString();
        String strtype = resumeexptype.getSelectedItem().toString();

//        this.fullname = fullname;
//        this.url = url;
//        this.phonenumber = phonenumber;
//        this.email = email;
//        this.gender = gender;
//        this.dob = dob;
//        this.location = location;

        //TODO: Change Resume
        UserResume resume = Common.userresume;
        resume.setExpcompanyname(strresumeexpcompanyname);
        resume.setExpdesignation(strresumeexpdesignation);
        resume.setExpdescription(strresumeexpdescription);
        resume.setExpstartdate(strresumeexpstartdate);
        resume.setExpenddate(strresumeexpenddate);
        resume.setExttype(strtype);
        resume.setStatus("75");

        dbresumeref.child(Common.currentuser1.getPhonenumber() + "").setValue(resume);
        Toast.makeText(this, "Experience Details Saved", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Resume3.this, Resume4.class);
        startActivity(intent);
        finish();
    }
}
