package com.example.friendio_2019;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private TextView mText1;
    private TextView mText2;
    private TextView mText3;
    private TextView mText4;
    private TextView mText5;
    private ImageView mImage1;
    private FirebaseAuth mAuth;
    private User currentUser;
    private String currentUserID;
    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users");
    DatabaseReference locationRef = FirebaseDatabase.getInstance().getReference("location");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mText1 = findViewById(R.id.text1);
        mText2 = findViewById(R.id.text2);
        mText3 = findViewById(R.id.text3);
        mText4 = findViewById(R.id.text4);
        mText5 = findViewById(R.id.text5);
        mImage1 = findViewById(R.id.image1);
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        Log.d("UID", currentUserID);
        setData();
    }

    private void setData()
    {
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                byte[] decodedString = Base64.decode(dataSnapshot.child("encodedProfilePicture").getValue().toString(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                mImage1.setImageBitmap(decodedByte);

                mText1.setText(dataSnapshot.child("firstName").getValue().toString());
                mText2.setText(dataSnapshot.child("lastName").getValue().toString());
                mText3.setText(dataSnapshot.child("age").getValue().toString());
                mText4.setText(dataSnapshot.child("bio").getValue().toString());
                mText5.setText(dataSnapshot.child("interest").getValue().toString());

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