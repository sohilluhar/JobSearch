package com.example.job;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.job.Model.User1;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import com.example.job.Model.User;

public class UserProfile extends AppCompatActivity {

    final int PICK_IMAGE_REQUEST = 71;
    FirebaseStorage storage;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dbUserRef = database.getReference("users/" + Common.currentuser1.getPhonenumber());
    Button btnLogout, btnProfileUpdate;
    CircularImageView imguserprofile;
    EditText etusername, etcurrentuserabout, currentusergithub, currentuserlinkedin, currentuserbussinessaddress;
    EditText currentuserwork, currentuserbussinessemail, currentuserbussinessphone;
    TextView currentuserpersonalphone, currentuserpersonalemail, tvTotalconnection, tvTotalConnectionRequest, tvTotalconnectionPending;
    Uri filePath;
    EditText etDOB, etlocation;
    StorageReference storageReference;
    String imgurl;
    TextView userphone, useremail;

    boolean checkphoto = false;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            return true;
        }
    };

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_profile);


        findViewById(R.id.navigation_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfile.this, UserDashboard.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.navigation_resume).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Common.userresume.getStatus().equals("100")) {
                    startActivity(new Intent(UserProfile.this, ResumeComplete.class));

                } else {
                    Intent intent = new Intent(UserProfile.this, Resume1.class);
                    startActivity(intent);
                }
            }
        });
        findViewById(R.id.navigation_upcomingEvent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserProfile.this, AppliedJobs.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.navigation_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(UserProfile.this, "Profile Click", Toast.LENGTH_SHORT).show();
            }
        });

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        btnLogout = findViewById(R.id.btnLogout);
        imguserprofile = findViewById(R.id.imguserprofile);
        btnProfileUpdate = findViewById(R.id.btnupdate);

        etusername = findViewById(R.id.userfullname);
        etDOB = findViewById(R.id.etDOB);
        etlocation = findViewById(R.id.etlocation);

        userphone = findViewById(R.id.userphone);
        useremail = findViewById(R.id.useremail);


//        Glide.with(this).load(Common.currentuser.getProfileurl())..into(viewHolder.imgevent);
        try {

            if (!Common.currentuser1.getUrl().equalsIgnoreCase(""))
                Picasso.get().load(Common.currentuser1.getUrl()).into(imguserprofile);
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

        imgurl = Common.currentuser1.getUrl();


        etusername.setText(Common.currentuser1.getName());
        etDOB.setText(Common.currentuser1.getDob());
        etlocation.setText(Common.currentuser1.getLocation());
        userphone.setText(Common.currentuser1.getPhonenumber());
        useremail.setText(Common.currentuser1.getEmail());


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("remember", "false");
                editor.remove("userphone");
                editor.remove("password");
                editor.apply();

                Common.currentuser1 = null;

                Intent intent = new Intent(UserProfile.this, MainActivity.class);
                startActivity(intent);
                finish();


            }
        });

        dbUserRef.addListenerForSingleValueEvent
                (new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        User1 currentuser1 = dataSnapshot.getValue(User1.class);
                        Common.currentuser1 = currentuser1;

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


        btnProfileUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                uploadimg();


            }
        });

    }

    private void uploadimg() {
        if (checkphoto) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            final StorageReference ref = storageReference.child("userprofileimages/" + Common.currentuser1.getUrl());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    progressDialog.dismiss();
                                    imgurl = uri.toString();
                                    updateuserprofile();
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
                            Toast.makeText(UserProfile.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
            updateuserprofile();
        }
    }

    private void updateuserprofile() {

        User1 tmp = Common.currentuser1;

        tmp.setName(etusername.getText().toString());
        tmp.setDob(etDOB.getText().toString());
        tmp.setLocation(etlocation.getText().toString());
        tmp.setUrl(imgurl);

        dbUserRef.setValue(tmp);

        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("remember", "true");


        Gson gson = new Gson();
        String userjson = gson.toJson(tmp);
        editor.putString("_USER", userjson);


        editor.apply();
        Toast.makeText(UserProfile.this, "Profile Updated", Toast.LENGTH_SHORT).show();


        Intent intent = new Intent(UserProfile.this, UserDashboard.class);
        startActivity(intent);
        finish();
    }


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
}
