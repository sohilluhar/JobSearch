package com.example.job;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.example.job.Model.Group;

public class CreateGroup extends AppCompatActivity {
    final int PICK_IMAGE_REQUEST = 71;
    Button btnAddEvent, btnUploadGroupImage, btnUploadFile, btnChoose;
    EditText etnew_group_name;
    EditText etnew_group_details;
    EditText etnew_group_category;
    EditText etnew_group_address;
    TimePickerDialog timePicker;
    ImageView ivGroupImage;
    Uri filePath;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, ref1;
    String grpimgurl = " ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        btnAddEvent = findViewById(R.id.btnAddEvent);
        etnew_group_name = findViewById(R.id.new_group_name);
        etnew_group_details = findViewById(R.id.new_group_details);
        etnew_group_category = findViewById(R.id.new_group_category);
        etnew_group_address = findViewById(R.id.new_group_address);

        btnUploadGroupImage = findViewById(R.id.btnUploadGroupImage);
        btnUploadFile = findViewById(R.id.btnUploadFile);

        ivGroupImage = findViewById(R.id.ivGroupImage);


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("EventGroup");


        btnUploadGroupImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (filePath != null) {
                    uploadimg();
                } else {
                    addGrouptoDB();
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
                        Toast.makeText(CreateGroup.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                ivGroupImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setimguri(Uri uri) {
        grpimgurl = uri.toString();
        addGrouptoDB();
    }

    private void addGrouptoDB() {

        final String new_group_name = etnew_group_name.getText().toString();
        final String new_group_details = etnew_group_details.getText().toString();
        final String new_group_category = etnew_group_category.getText().toString();
        final String new_group_location = etnew_group_address.getText().toString();

        final String org_idstr = Common.currentuser.getUserphone();


        final String imgurlf = grpimgurl;


//                ,event_address,event_city,event_pincode;


        if (new_group_name.isEmpty() || new_group_details.isEmpty() || new_group_category.isEmpty() || imgurlf.equals(" ")) {
            Toast.makeText(CreateGroup.this, "Please fill all details ", Toast.LENGTH_SHORT).show();
        } else {

            long gpkey = new Date().getTime();


//                        UUID eventkey= UUID.randomUUID();

            Group group = new Group(
                    new_group_category, " ", new_group_details, gpkey + "", imgurlf, new_group_location, new_group_name, org_idstr);

            List<String> tmp = new ArrayList<>();

            databaseReference.child(gpkey + "").setValue(group);

            Toast.makeText(CreateGroup.this, "Group Added Successfully ", Toast.LENGTH_SHORT).show();
            finish();


        }

    }


}
