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
public class activity_resume_extracuricular extends AppCompatActivity {
    final int PICK_IMAGE_REQUEST = 71;
    FirebaseStorage storage;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dbUserRef = database.getReference("users/" + Common.currentuser.getUserphone());
    Button btnLogout, btnProfileUpdate,btnresumepersonal,btnresumeeducation,btnresumeexperiance,btnresumeproject,btnresumeextracurricular,btnresumeothers;
    CircularImageView imguserprofile;
    EditText etusername, etcurrentuserabout, currentusergithub, currentuserlinkedin, currentuserbussinessaddress;
    EditText currentuserwork, currentuserbussinessemail, currentuserbussinessphone;
    TextView currentuserpersonalphone, currentuserpersonalemail, tvTotalconnection, tvTotalConnectionRequest, tvTotalconnectionPending;
    Uri filePath;
    StorageReference storageReference;
    String imgurl;
    boolean checkphoto = false;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    //     toolbar.setTitle("Shop");
                    Intent intent = new Intent(activity_resume_extracuricular.this, UserDashboard.class);
                    startActivity(intent);
                    finish();
//
//                    Toast.makeText(Home.this, "Home Click", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_search:
//                    Intent intent = new Intent(Home.this, User_Search_Event.class);
//                    startActivity(intent);
//                    finish();
//                    Toast.makeText(Home.this, "Search Click", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_upcomingEvent:
                    Intent intent1 = new Intent(activity_resume_extracuricular.this, UserGoingEvent.class);
                    startActivity(intent1);
                    finish();
                    return true;
                case R.id.navigation_resume:
              //   Intent intent3= new Intent(activity_resume_extracuricular.this, resume_dashboard.class);
                //    startActivity(intent3);
                  //  finish();
                    return true;
                case R.id.navigation_profile:
                    Intent intent2 = new Intent(activity_resume_extracuricular.this, UserProfile.class);
                    startActivity(intent2);
                    finish();
                    return true;
            }
            return true;
        }
    };
    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_extracuricular);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_resume);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        btnLogout = findViewById(R.id.btnLogout);
        btnresumepersonal=findViewById(R.id.resume_personal);
        btnresumeeducation=findViewById(R.id.resume_education);
        btnresumeexperiance=findViewById(R.id.resume_experience);
        btnresumeproject=findViewById(R.id.resume_project);
        btnresumeextracurricular=findViewById(R.id.resume_extracurricular);
        btnresumeothers=findViewById(R.id.resume_others);
        imguserprofile = findViewById(R.id.imguserprofile);
        btnProfileUpdate = findViewById(R.id.btnUpdateUserProfile);
        etusername = findViewById(R.id.currentusername);
        etcurrentuserabout = findViewById(R.id.currentuserabout);
        currentusergithub = findViewById(R.id.currentusergithub);
        currentuserlinkedin = findViewById(R.id.currentuserlinkedin);
        currentuserwork = findViewById(R.id.currentuserwork);
        currentuserbussinessemail = findViewById(R.id.currentuserbussinessemail);
        currentuserbussinessphone = findViewById(R.id.currentuserbussinessphone);
        currentuserpersonalphone = findViewById(R.id.currentuserpersonalphone);
        currentuserpersonalemail = findViewById(R.id.currentuserpersonalemail);
        currentuserbussinessaddress = findViewById(R.id.currentuserbussinessaddress);
        tvTotalconnection = findViewById(R.id.tvTotalconnection);
        tvTotalConnectionRequest = findViewById(R.id.tvTotalConnectionRequest);
        tvTotalconnectionPending = findViewById(R.id.tvTotalconnectionPending);
//        Glide.with(this).load(Common.currentuser.getProfileurl())..into(viewHolder.imgevent);
        /*try {
            if (!Common.currentuser.getProfileurl().equalsIgnoreCase(""))
                Picasso.get().load(Common.currentuser.getProfileurl()).into(imguserprofile);
        }catch (Exception e){}
        imguserprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkphoto = true;
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });*/
        btnresumepersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity_resume_extracuricular.this,activity_resume_personal.class);
                startActivity(intent);
            }
        });
        btnresumeeducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity_resume_extracuricular.this,activity_resume_educational.class);
                startActivity(intent);
            }
        });
        btnresumeexperiance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity_resume_extracuricular.this,activity_resume_experiance.class);
                startActivity(intent);
            }
        });
        btnresumeproject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity_resume_extracuricular.this,activity_resume_project.class);
                startActivity(intent);
            }
        });
        btnresumeextracurricular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    Intent intent=new Intent(activity_resume_extracuricular.this,activity_resume_extracuricular.class);
              //  startActivity(intent);
            }
        });
        btnresumeothers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                       Intent intent=new Intent(activity_resume_extracuricular.this,activity_resume_others.class);
                      startActivity(intent);
            }
        });
        imgurl = Common.currentuser.getProfileurl();
        //tvTotalconnection.setText(String.format("%d", Common.currentuser.getConnection().size() - 1));
        /*tvTotalConnectionRequest.setText(String.format("%d", Common.currentuser.getRequestedconnection().size() - 1));
        tvTotalconnectionPending.setText(String.format("%d", Common.currentuser.getPendingconnection().size() - 1));
        etusername.setText(Common.currentuser.getName());
        etcurrentuserabout.setText(Common.currentuser.getAbout());
        currentusergithub.setText(Common.currentuser.getGithub());
        currentuserlinkedin.setText(Common.currentuser.getLinkedin());
        currentuserwork.setText(Common.currentuser.getWork());
        currentuserbussinessemail.setText(Common.currentuser.getBussinessemail());
        currentuserbussinessphone.setText(Common.currentuser.getBussinessphone());
        currentuserbussinessaddress.setText(Common.currentuser.getBussinessaddress());
        currentuserpersonalphone.setText(Common.currentuser.getUserphone());
        currentuserpersonalemail.setText(Common.currentuser.getEmail());
        tvTotalconnection.setMovementMethod(LinkMovementMethod.getInstance());
        tvTotalconnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_resume_personal.this, ViewConnection.class);
                startActivity(intent);
            }
        });
        tvTotalConnectionRequest.setMovementMethod(LinkMovementMethod.getInstance());
        tvTotalConnectionRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_resume_personal.this, ViewRequestedConnection.class);
                startActivity(intent);
            }
        });
        tvTotalconnectionPending.setMovementMethod(LinkMovementMethod.getInstance());
        tvTotalconnectionPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_resume_personal.this, ViewPendingConnection.class);
                startActivity(intent);
            }
        });*/
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("remember", "false");
                editor.remove("userphone");
                editor.remove("password");
                editor.apply();
                Common.currentuser = null;
                Intent intent = new Intent(activity_resume_extracuricular.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        dbUserRef.addListenerForSingleValueEvent
                (new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User currentuser = dataSnapshot.getValue(User.class);
                        Common.currentuser = currentuser;
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
            final StorageReference ref = storageReference.child("userprofileimages/" + Common.currentuser.getUserphone());
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
                            Toast.makeText(activity_resume_extracuricular.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
        User tmp = new User(
                Common.currentuser.getEmail()
                , etusername.getText().toString()
                , Common.currentuser.getPass(),
                "User",
                Common.currentuser.getExtraType(),
                currentuserpersonalphone.getText().toString(),
                etcurrentuserabout.getText().toString(),
                currentusergithub.getText().toString(),
                currentuserlinkedin.getText().toString(),
                currentuserwork.getText().toString(),
                currentuserbussinessemail.getText().toString(),
                currentuserbussinessphone.getText().toString(),
                currentuserbussinessaddress.getText().toString(),
                Common.currentuser.getGoingevent(),
                Common.currentuser.getConnection(),
                Common.currentuser.getPendingconnection(),
                Common.currentuser.getRequestedconnection(),
                imgurl
        );
        dbUserRef.setValue(tmp);
        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("remember", "true");
        Gson gson = new Gson();
        String userjson = gson.toJson(tmp);
        editor.putString("_USER", userjson);
        editor.apply();
        Toast.makeText(activity_resume_extracuricular.this, "Profile Updated", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(activity_resume_extracuricular.this, UserDashboard.class);
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

