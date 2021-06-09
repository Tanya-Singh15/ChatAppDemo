package com.example.chatapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class Settings extends AppCompatActivity {
    CircleImageView rec_profile_image;
    FirebaseStorage firebaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        firebaseStorage = FirebaseStorage.getInstance();

        rec_profile_image = (CircleImageView) findViewById(R.id.rec_profile_image);
        rec_profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 12);
            }
        });
       // Picasso.get().load(Uri.parse(profilePic)).placeholder(R.drawable.tanya).error(R.drawable.ic_launcher_background).into(profile_pic);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data.getData() != null)
        {
            Uri uri = data.getData();
            rec_profile_image.setImageURI(uri);
            //to store the pic in firebase storage.
            StorageReference storageReference = firebaseStorage.getReference().child("Profile_pic").child(FirebaseAuth.getInstance().getUid());
            storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            FirebaseDatabase mReference = FirebaseDatabase.getInstance();

                            mReference.getReference().child("User").child(FirebaseAuth.getInstance().getUid()).child("dp").setValue(uri.toString());
                        }
                    });
                }
            });


        }
    }
}