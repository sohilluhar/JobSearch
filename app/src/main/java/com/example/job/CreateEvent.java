package com.example.job;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.job.Model.Job;
import com.example.job.Model.JobApply;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.example.job.Model.Annoucement;
import com.example.job.Model.Event;
import com.example.job.Model.EventGoing;

public class CreateEvent extends AppCompatActivity {
    final int PICK_IMAGE_REQUEST = 71;

    Uri filePath;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, ref1;
    String imgurl = " ";


    EditText newjobname, newjobdetails, newjoblocation, newjobsalary, newjobvacancy, newjobdesignation;
    EditText newjobskill, newjobworkexperience, criteriahsc, criteriagraduate, criteriapostgraduate;
    EditText newjobcompanyname, newjobcompanyabout, newjobcontactname, newjobcontactnumber;
    Button newjobcompanyimage;
    Spinner newjobtype;
    ImageView previewImg;
    Button addjob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        newjobname = findViewById(R.id.newjobname);
        newjobdetails = findViewById(R.id.newjobdetails);
        newjoblocation = findViewById(R.id.newjoblocation);
        newjobsalary = findViewById(R.id.newjobsalary);
        newjobvacancy = findViewById(R.id.newjobvacancy);
        newjobdesignation = findViewById(R.id.newjobdesignation);
        newjobtype = findViewById(R.id.newjobtype);

        newjobskill = findViewById(R.id.newjobskill);
        newjobworkexperience = findViewById(R.id.newjobworkexperience);
        criteriahsc = findViewById(R.id.criteriahsc);
        criteriagraduate = findViewById(R.id.criteriagraduate);
        criteriapostgraduate = findViewById(R.id.criteriapostgraduate);

        newjobcompanyname = findViewById(R.id.newjobcompanyname);
        newjobcompanyabout = findViewById(R.id.newjobcompanyabout);
        newjobcontactname = findViewById(R.id.newjobcontactname);
        newjobcontactnumber = findViewById(R.id.newjobcontactnumber);
        newjobcompanyimage = findViewById(R.id.newjobcompanyimage);
        previewImg = findViewById(R.id.newjobimgpreview);


        addjob = (Button) findViewById(R.id.btncreatejob);
        newjobcompanyimage = (Button) findViewById(R.id.newjobcompanyimage);


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Job");
        ref1 = firebaseDatabase.getReference("JobApply");
        newjobcompanyimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });


        addjob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (filePath != null) {
                    uploadimg();
                } else {
                    addEventtoDB();
                }
            }
        });
    }


    private void uploadimg() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        final StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
        ref.putFile(filePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                setimguri(uri);
                            }
                        });
//                            progressDialog.dismiss();
                        // Toast.makeText(CreateEvent.this, "Uploaded", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
//                            progressDialog.dismiss();
                        Toast.makeText(CreateEvent.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                .getTotalByteCount());
                        progressDialog.setMessage("Uploading " + (int) progress + "%");
                    }
                });

    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                previewImg.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void addEventtoDB() {

        final String strnewjobname = newjobname.getText().toString();
        final String strnewjobdetails = newjobdetails.getText().toString();
        final String strnewjoblocation = newjoblocation.getText().toString();
        final String strnewjobsalary = newjobsalary.getText().toString();
        final String strnewjobvacancy = newjobvacancy.getText().toString();
        final String strnewjobdesignation = newjobdesignation.getText().toString();
        final String strjobtype = newjobtype.getSelectedItem().toString();

        final String strnewjobskill = newjobskill.getText().toString();
        final String strnewjobworkexperience = newjobworkexperience.getText().toString();
        final String strcriteriahsc = criteriahsc.getText().toString();
        final String strcriteriagraduate = criteriagraduate.getText().toString();
        final String strcriteriapostgraduate = criteriapostgraduate.getText().toString();
        final String strnewjobcompanyname = newjobcompanyname.getText().toString();
        final String strnewjobcompanyabout = newjobcompanyabout.getText().toString();
        final String strnewjobcontactname = newjobcontactname.getText().toString();
        final String strnewjobcontactnumber = newjobcontactnumber.getText().toString();
        final String idstr = Common.currentuser1.getPhonenumber();

        final List<Annoucement> annoucemts = new ArrayList<>();
        annoucemts.add(new Annoucement("", "", "", ""));
//        annoucemts.add(" ");
        final List<JobApply> jobApplies = new ArrayList<>();
        jobApplies.add(new JobApply("", ""));

        if (strnewjobname.isEmpty() ||
                strnewjobdetails.isEmpty() ||
                strnewjoblocation.isEmpty() ||
                strnewjobsalary.isEmpty() ||
                strnewjobcompanyabout.isEmpty() ||
                strnewjobvacancy.isEmpty() ||
                strnewjobdesignation.isEmpty() ||
                strjobtype.isEmpty() ||
                strnewjobskill.isEmpty() ||
                strnewjobworkexperience.isEmpty() ||
                strcriteriahsc.isEmpty() ||
                strcriteriagraduate.isEmpty() ||
                strcriteriapostgraduate.isEmpty() ||
                strnewjobcompanyname.isEmpty() ||
                strnewjobcontactname.isEmpty() ||
                strnewjobcontactnumber.isEmpty()

        ) {
            Toast.makeText(CreateEvent.this, "Please fill all details ", Toast.LENGTH_SHORT).show();
        } else {

            long key = new Date().getTime();
            List<String> tmp = new ArrayList<>();
            tmp.add(" ");

            Job newjob = new Job(
                    strnewjobname,
                    strnewjobdetails,
                    strnewjoblocation,
                    strnewjobsalary,
                    strnewjobvacancy,
                    strnewjobdesignation,
                    strjobtype,
                    strnewjobskill,
                    strnewjobworkexperience,
                    strcriteriahsc,
                    strcriteriagraduate,
                    strcriteriapostgraduate,
                    strnewjobcompanyname,
                    strnewjobcontactname,
                    strnewjobcontactnumber,
                    idstr,
                    imgurl, strnewjobcompanyabout, key + ""
            );

//                        UUID eventkey= UUID.randomUUID();


            databaseReference.child(key + "").setValue(newjob);

            ref1.child(key + "").setValue(jobApplies);


            // databaseReference.child(eventkey + "").child("question").child("1").setValue(tmp);
            Toast.makeText(CreateEvent.this, "Job Added Successfully ", Toast.LENGTH_SHORT).show();
            finish();


        }

    }

    private void setimguri(Uri uri) {
        imgurl = uri.toString();
        addEventtoDB();
    }
}
