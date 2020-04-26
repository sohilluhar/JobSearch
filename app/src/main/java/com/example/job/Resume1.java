package com.example.job;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.job.Model.UserResume;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Calendar;

public class Resume1 extends AppCompatActivity {

    final int PICK_IMAGE_REQUEST = 71;
    FirebaseStorage storage;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dbUserRef = database.getReference("users/" + Common.currentuser1.getPhonenumber());
    CircularImageView imguserprofile;
    DatabaseReference dbresumeref = database.getReference("Resume");

    Calendar c;
    DatePickerDialog datePickerDialog;
    EditText etfullname, etmail, etphone, etDOB, etaddress, etskill, etobjective;
    Spinner gender;
    //    EditText etusername, etcurrentuserabout, currentusergithub, currentuserlinkedin, currentuserbussinessaddress;
//    EditText currentuserwork, currentuserbussinessemail, currentuserbussinessphone;
//    TextView currentuserpersonalphone, currentuserpersonalemail, tvTotalconnection, tvTotalConnectionRequest, tvTotalconnectionPending;
    Uri filePath;
    StorageReference storageReference;
    String imgurl;
    boolean checkphoto = false;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imguserprofile.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_resume1);

        imguserprofile = findViewById(R.id.personal_image);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        etfullname = findViewById(R.id.resumefullname);
        etmail = findViewById(R.id.resumeemail);
        etphone = findViewById(R.id.resume_phone);
        etDOB = findViewById(R.id.resume_dob);
        etaddress = findViewById(R.id.resume_address);
        gender = findViewById(R.id.resumegender);
        etskill = findViewById(R.id.resume_skill);
        etobjective = findViewById(R.id.resume_objective);
        etDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                Log.e("DAte", "test");

                datePickerDialog = new DatePickerDialog(Resume1.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int myear, int mmonth, int mdayOfMonth) {

                        String[] MONTH =
                                {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

                        Log.e("Month", mmonth + "");

                        String tmpday = mdayOfMonth + "";


                        etDOB.setText(tmpday + " " + MONTH[mmonth] + " " + myear);
                    }
                }, year, month, day);

                datePickerDialog.show();
            }
        });

        try {

            if (!Common.userresume.getUrl().equalsIgnoreCase("")) {
                imgurl = Common.userresume.getUrl();
                Picasso.get().load(Common.userresume.getUrl()).into(imguserprofile);
            }
        } catch (Exception e) {
        }
        imguserprofile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                checkphoto = true;
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });


        findViewById(R.id.saveresume1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadimg();
            }
        });

        etfullname.setText(Common.userresume.getFullname());
        etmail.setText(Common.userresume.getEmail());
        etDOB.setText(Common.userresume.getDob());
        etphone.setText(Common.userresume.getPhonenumber());
        etaddress.setText(Common.userresume.getLocation());
        etskill.setText(Common.userresume.getSkill());
        etobjective.setText(Common.userresume.getObjective());


    }

    private void uploadimg() {
        if (checkphoto) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            final StorageReference ref = storageReference.child("userprofileimages/" + Common.currentuser1.getPhonenumber());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    progressDialog.dismiss();
                                    imgurl = uri.toString();
                                    savetodb();
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
                            Toast.makeText(Resume1.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
        } else {
            savetodb();
        }
    }

    private void savetodb() {

        String fullname = etfullname.getText().toString();
        String strmail = etmail.getText().toString();
        String strphone = etphone.getText().toString();
        String strdob = etDOB.getText().toString();
        String straddress = etaddress.getText().toString();
        String strgender = gender.getSelectedItem().toString();
        String strskill = etskill.getText().toString();
        String strobjective = etobjective.getText().toString();
//        this.fullname = fullname;
//        this.url = url;
//        this.phonenumber = phonenumber;
//        this.email = email;
//        this.gender = gender;
//        this.dob = dob;
//        this.location = location;

        //TODO: Change Resume
        UserResume resume = Common.userresume;
        resume.setSkill(strskill);
        resume.setObjective(strobjective);
        dbresumeref.child(Common.currentuser1.getPhonenumber() + "").setValue(resume);
        Toast.makeText(this, "Personal Details Saved", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Resume1.this, Resume2.class);
        startActivity(intent);
        finish();
    }
}
