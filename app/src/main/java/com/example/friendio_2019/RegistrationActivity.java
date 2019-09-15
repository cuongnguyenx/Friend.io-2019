package com.example.friendio_2019;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class RegistrationActivity extends AppCompatActivity{

    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users");
    DatabaseReference locationRef = FirebaseDatabase.getInstance().getReference("location");

    private EditText mFirstName;
    private EditText mLastName;
    private EditText mAge;
    private EditText mInterests;
    private EditText mBio;
    private Button mAddProfileImage;
    private Button mCreateProfileButton;
    private TextView mProfileImageName;
    private String encodedImage;


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mFirstName = findViewById(R.id.fieldFirstName);
        mLastName = findViewById(R.id.fieldLastName);
        mAge = findViewById(R.id.fieldAge);
        mInterests = findViewById(R.id.fieldInterests);
        mBio = findViewById(R.id.fieldBio);
        mAddProfileImage = findViewById(R.id.addImageButton);
        mAddProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImageFromGallery();
            }
        });

        mCreateProfileButton = findViewById(R.id.createProfileButton);
        mCreateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateForm())
                {
                    return;
                }
                createProfileForAccount(mFirstName.getText().toString(), mLastName.getText().toString(),
                        mBio.getText().toString(), encodedImage, Integer.parseInt(mAge.getText().toString()),
                        mInterests.getText().toString());
            }
        });

        mProfileImageName = findViewById(R.id.profileImageText);
        mAuth = FirebaseAuth.getInstance();

        if (ContextCompat.checkSelfPermission(RegistrationActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RegistrationActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    0);
        }

        encodedImage = "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";
    }

    private void createProfileForAccount(String firstName, String lastName, String bio,
                                         String encodedProfileImage, int age, String interests) {
        if (!validateForm()) {
            return;
        }
        String currentUserId = mAuth.getCurrentUser().getUid();
        User userToBeAdded = new User(firstName, lastName, bio, encodedProfileImage, age, interests);
        userRef.child(currentUserId).setValue(userToBeAdded);
        locationRef.child(currentUserId).setValue(new Coordinate(0.0, 0.0));
    }

    private void getImageFromGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);

                                Bitmap bm = BitmapFactory.decodeFile(picturePath);
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                                byte[] imageBytes = baos.toByteArray();
                                encodedImage = Base64.encodeToString(imageBytes, Base64.NO_WRAP);
                                mProfileImageName.setText(picturePath);
                                cursor.close();
                            }
                        }

                    }
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(RegistrationActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    private boolean validateForm() {
        boolean valid = true;

        String firstName = mFirstName.getText().toString();
        if (TextUtils.isEmpty(firstName)) {
            mFirstName.setError("Required.");
            valid = false;
        } else {
            mFirstName.setError(null);
        }

        String lastName = mLastName.getText().toString();
        if (TextUtils.isEmpty(lastName)) {
            mLastName.setError("Required.");
            valid = false;
        } else {
            mLastName.setError(null);
        }

        String age = mAge.getText().toString();
        if (TextUtils.isEmpty(age)) {
            mAge.setError("Required.");
            valid = false;
            if (TextUtils.isEmpty(lastName)) {
            } else {
                mAge.setError(null);
            }

            String interests = mInterests.getText().toString();
            if (TextUtils.isEmpty(interests)) {
                mInterests.setError("Required.");
                valid = false;
            } else {
                mInterests.setError(null);
            }

            String bio = mBio.getText().toString();
            if (TextUtils.isEmpty(bio)) {
                mBio.setError("Required.");
                valid = false;
            } else {
                mBio.setError(null);
            }
        }
        return valid;
    }
}

