package com.example.friendio_2019;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class Database {
    private DatabaseReference mUserDatabase;

    private ArrayList<User> mUserList;
    private User currentUser;

    public Database() {
        mUserDatabase = FirebaseDatabase.getInstance().getReference("testing-users");

        // Add listener to get all user data
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                collectAllUsers((Map<String,Object>) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void collectAllUsers(Map<String, Object> users) {
        ArrayList<User> userList = new ArrayList<>();

        if (users != null) {
            for (Map.Entry<String, Object> entry: users.entrySet()) {
                Map singleUser = (Map) entry.getValue();
                User usr = new User(singleUser);
                userList.add(usr);
            }
        }

        mUserList = userList;

    }


    public ArrayList<User> getUsers() {
        return mUserList;
    }

    public DatabaseReference getUserDatabaseReference() {
        return mUserDatabase;
    }

    public void writeUser(String userID, User user) {

        mUserDatabase.child(userID).child("firstName").setValue(user.getFirstName());
        mUserDatabase.child(userID).child("lastName").setValue(user.getLastName());
        mUserDatabase.child(userID).child("bio").setValue(user.getBio());
        mUserDatabase.child(userID).child("interest").setValue(user.getInterest());
        mUserDatabase.child(userID).child("age").setValue(user.getAge());
        mUserDatabase.child(userID).child("encodedProfilePicture").setValue(user.getEncodedProfilePicture());
        mUserDatabase.child(userID).child("status").setValue(user.getStatus());
        mUserDatabase.child(userID).child("latitude").setValue(user.getLatitude());
        mUserDatabase.child(userID).child("longitude").setValue(user.getLongitude());
    }

}
