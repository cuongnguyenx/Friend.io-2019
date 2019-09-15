package com.example.friendio_2019;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.EventListener;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    private EditText mText1;
    private EditText mText2;
    private EditText mText3;
    private EditText mText4;
    private EditText mText5;
    private ImageView mImage1;
    private Button mUpdateButton;
    private FirebaseAuth mAuth;
    private User currentUser;
    private String currentUserID;
    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users");
    DatabaseReference locationRef = FirebaseDatabase.getInstance().getReference("location");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mText1 = findViewById(R.id.textFN);
        mText2 = findViewById(R.id.textLN);
        mText3 = findViewById(R.id.textAge);
        mText4 = findViewById(R.id.textHB);
        mText5 = findViewById(R.id.textIN);
        mImage1 = findViewById(R.id.image1);
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        Log.d("UID", currentUserID);

        setData();

        mUpdateButton = findViewById(R.id.buttonUpdate);
        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateForm())
                {
                    return;
                }
                userRef.child(currentUserID).child("firstName").setValue(mText1.getText().toString());
                userRef.child(currentUserID).child("lastName").setValue(mText2.getText().toString());
                userRef.child(currentUserID).child("age").setValue(Long.parseLong(mText3.getText().toString()));
                userRef.child(currentUserID).child("interest").setValue(mText4.getText().toString());
                userRef.child(currentUserID).child("bio").setValue(mText5.getText().toString());
                Toast.makeText(ProfileActivity.this, "Changes Successfully Made",
                        Toast.LENGTH_LONG).show();

            }
        });


    }

    private boolean validateForm() {
        boolean valid = true;

        String firstName = mText1.getText().toString();
        if (TextUtils.isEmpty(firstName)) {
            mText1.setError("Required.");
            valid = false;
        } else {
            mText1.setError(null);
        }

        String lastName = mText2.getText().toString();
        if (TextUtils.isEmpty(lastName)) {
            mText2.setError("Required.");
            valid = false;
        } else {
            mText2.setError(null);
        }

        String age = mText3.getText().toString();
        if (TextUtils.isEmpty(age)) {
            mText3.setError("Required.");
            valid = false;
            if (TextUtils.isEmpty(lastName)) {
            } else {
                mText3.setError(null);
            }

            String interests = mText4.getText().toString();
            if (TextUtils.isEmpty(interests)) {
                mText4.setError("Required.");
                valid = false;
            } else {
                mText4.setError(null);
            }

            String bio = mText5.getText().toString();
            if (TextUtils.isEmpty(bio)) {
                mText5.setError("Required.");
                valid = false;
            } else {
                mText5.setError(null);
            }
        }
        return valid;
    }

    private void setCurrentUser(User user)
    {
        this.currentUser = user;
    }
    private void setData()
    {
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentUser = new User((Map<String, Object>) dataSnapshot.getValue());
                setCurrentUser(currentUser);
                Log.d("dasd", currentUser.getFirstName());

                byte[] decodedString = Base64.decode(currentUser.getEncodedProfilePicture(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                mImage1.setImageBitmap(decodedByte);

                mText1.setText(currentUser.getFirstName());
                mText2.setText(currentUser.getLastName());
                mText3.setText(Long.toString(currentUser.getAge()));
                mText4.setText(currentUser.getInterest());
                mText5.setText(currentUser.getBio());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("ABCDE", "loadUser:onCancelled", databaseError.toException());
                // ...
            }
        };
        userRef.child(currentUserID).addListenerForSingleValueEvent(userListener);
    }

}
