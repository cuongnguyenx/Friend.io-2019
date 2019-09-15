package com.example.friendio_2019;

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
        setData();
    }

    private void setData()
    {
        String currentUserID = mAuth.getCurrentUser().getUid();
        Log.d("UID", currentUserID);

        userRef.child(currentUserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentUser = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mText1.setText(currentUser.getFirstName());
        mText2.setText(currentUser.getLastName());
        mText3.setText(currentUser.getAge());
        mText4.setText(currentUser.getBio());
        mText5.setText(currentUser.getInterest());

        byte[] decodedString = Base64.decode(currentUser.getEncodedProfilePicture(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        mImage1.setImageBitmap(decodedByte);
    }

}
