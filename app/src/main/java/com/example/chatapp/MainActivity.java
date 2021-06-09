
package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    Button btnSendOTP, btnVerify;
    EditText txtPhone, txtOTP, txtName;
    String mVerificationId;

    final int rqstCode1 = 1234;
    View cLayout;
    CircleImageView profile_image;
    private static int RESULT_LOAD_IMAGE = 1;
    Uri filePath;

    PhoneAuthProvider.ForceResendingToken mResendToken;
    PhoneAuthCredential credential;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;

    FirebaseDatabase database;
    private DatabaseReference mReference; //writing user data to database.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSendOTP = findViewById(R.id.btnSendOTP);
        btnVerify = findViewById(R.id.btnVerify);

        txtPhone = findViewById(R.id.txtPhone);
        txtOTP = findViewById(R.id.txtOTP);
        txtName = findViewById(R.id.textName);
        profile_image = findViewById(R.id.rec_profile_image);
        cLayout = findViewById(R.id.clayout);

        ChatFragment.getUsers();
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        callBack();
        if (database != null) {
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                mReference = database.getReference().child("User").child(FirebaseAuth.getInstance().getUid());
            }
        }

        startWhatsAppHome(); // this func will be used when user already exist.

        //to change profile pic
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        //for internet permission : to add user from firebase.
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.INTERNET}, rqstCode1);

        txtName.setVisibility(View.GONE);
        btnSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Send OTP .", Toast.LENGTH_LONG).show();

                //btnVerify.setVisibility(View.VISIBLE);
                //txtOTP.setVisibility(View.VISIBLE);
                btnSendOTP.setVisibility(View.VISIBLE);
                txtPhone.setVisibility(View.VISIBLE);
                cLayout.setBackgroundColor(Color.LTGRAY);

                String phoneNumber = txtPhone.getText().toString().trim();
                PhoneAuthOptions options =
                        PhoneAuthOptions.newBuilder(mAuth)
                                .setPhoneNumber(phoneNumber)      // Phone number to verify
                                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                .setActivity(MainActivity.this)                 // Activity (for callback binding)
                                .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                                .build();
                PhoneAuthProvider.verifyPhoneNumber(options);
            }
        });


        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = txtOTP.getText().toString();
                credential = PhoneAuthProvider.getCredential(mVerificationId, code);
                signInWithPhoneAuthCredential(credential);
            }
        });
    }

    public void callBack() {
        Toast.makeText(MainActivity.this, "Inside callback method", Toast.LENGTH_LONG).show();
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                // Log.d(TAG, "onVerificationFailedVerificationCompleted:" + credential);

                Toast.makeText(MainActivity.this, "Verification completed.", Toast.LENGTH_LONG).show();
                txtName.setVisibility(View.VISIBLE);

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                // Log.w(TAG, "onVerificationFailed", e);

                Toast.makeText(MainActivity.this, "Sorry !!!", Toast.LENGTH_LONG).show();
                Toast.makeText(MainActivity.this, e.getMessage().toString(), Toast.LENGTH_LONG).show();

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(MainActivity.this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
                    // Invalid request
                    // ...

                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Toast.makeText(MainActivity.this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
                    // Invalid request // The SMS quota for the project has been exceeded
                    // ...
                }

                // Show a message and update the UI
                // ...
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                //Log.d(TAG, "onCodeSent:" + verificationId);

                Toast.makeText(MainActivity.this, "Code sent", Toast.LENGTH_LONG).show();

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                txtOTP.setVisibility(View.VISIBLE);
                btnVerify.setVisibility(View.VISIBLE);
                txtPhone.setVisibility(View.GONE);
                // btnSendOTP.setVisibility(View.GONE);
                // txtName.setVisibility(View.VISIBLE);
                // ...
            }
        };
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithCredential:success");
                            txtName.setVisibility(View.VISIBLE);
                            Toast.makeText(MainActivity.this, "Login Successful !!! ", Toast.LENGTH_LONG).show();
                            writeNewUser(txtName.getText().toString(), FirebaseAuth.getInstance().getUid().toString(), txtPhone.getText().toString());

                            startWhatsAppHome(); // for new user.
                        } else {
                            // Sign in failed, display a message and update the UI
                            //Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn =
                    {
                            MediaStore.Images.Media.DATA
                    };
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            profile_image.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }

    public void startWhatsAppHome() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent intent = new Intent(MainActivity.this, ChatAppHome.class);
            startActivity(intent);
        }
    }

    public void writeNewUser(String userName, String uID, String phoneNumber) {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            return;
        }

        Users user = new Users(userName, uID, phoneNumber);

        if ((database != null) && (FirebaseAuth.getInstance().getCurrentUser() != null)) {

            mReference = database.getReference().child("User").child(FirebaseAuth.getInstance().getUid());
            Toast.makeText(MainActivity.this, FirebaseAuth.getInstance().getUid(), Toast.LENGTH_LONG).show();

            mReference.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists())
                    {
                        for(DataSnapshot ds:dataSnapshot.getChildren()){
                            Users tempUser=ds.getValue(Users.class);
                            if(tempUser.getUserName().length()<=1){

                                tempUser.setUserName("New User " +(new Date().getTime()));
                                mReference.setValue(tempUser);

                            }
                    }}
                }
            });



        } else {
            Toast.makeText(MainActivity.this, "User not added ", Toast.LENGTH_LONG).show();
        }
    }
};






