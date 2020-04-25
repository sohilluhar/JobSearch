package com.example.job;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
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
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class AddSocialPhoto extends AppCompatActivity {

    final int PICK_IMAGE_REQUEST = 71;
    Uri filePath;
    Calendar c;
    DatePickerDialog datePickerDialog;
    FirebaseStorage storage;
    StorageReference storageReference;
    Button btnchoosephoto, btnAddPhoto;
    String imgurl = " ";
    ImageView previewImg;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, ref1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_social_photo);
        btnchoosephoto = (Button) findViewById(R.id.btnchooseeventphoto);
        btnAddPhoto = (Button) findViewById(R.id.btnaddeventphoto);
        previewImg = (ImageView) findViewById(R.id.ivpreviewphoto);


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        firebaseDatabase = FirebaseDatabase.getInstance();
        ref1 = firebaseDatabase.getReference("Events/" + Common.selectedEvent.getEvent_key());

        btnchoosephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadimg();
            }
        });

    }

    private void uploadimg() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        final StorageReference ref = storageReference.child("eventsimages/" + Common.selectedEvent.getEvent_key() + "/" + UUID.randomUUID().toString());
        ref.putFile(filePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                progressDialog.dismiss();
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
                        Toast.makeText(AddSocialPhoto.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void setimguri(Uri uri) {
        imgurl = uri.toString();

        List<String> photo = Common.selectedEvent.getPhotos();

        photo.add(imgurl);
        ref1.child("photos").setValue(photo);
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
                previewImg.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
