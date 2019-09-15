package com.example.friendio_2019;

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

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegistrationActivity extends AppCompatActivity {

    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users");
    DatabaseReference locationRef = FirebaseDatabase.getInstance().getReference("location");

    private EditText mFirstName;
    private EditText mLastName;
    private EditText mAge;
    private EditText mInterests;
    private EditText mBio;
    private Button mAddProfileImage;
    private TextView mProfileImageName;


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mFirstName = findViewById(R.id.fieldFirstName);
        mLastName = findViewById(R.id.fieldLastName);
        mAge = findViewById(R.id.fieldAge);
        mInterests = findViewById(R.id.fieldInterests);
        mBio = findViewById(R.id.fieldBio);
        mAddProfileImage = findViewById(R.id.addImageButton);
        mProfileImageName = findViewById(R.id.profileImageText);

        mAuth = FirebaseAuth.getInstance();

    }

    private void createDataForAccount

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


        return valid;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.createProfileButton) {
            createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
        } else if (i == R.id.addImageButton) {
            signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
        }
    }

}
