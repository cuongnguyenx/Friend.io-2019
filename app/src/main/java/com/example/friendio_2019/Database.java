package com.example.friendio_2019;


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

    public Database() {
        mUserDatabase = FirebaseDatabase.getInstance().getReference("/user");

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
                userList.add((User) singleUser);
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

    public void writeNewUser(String userID, User user) {

        mUserDatabase.child(userID).child("firstname").setValue(user.getFirstName());
        mUserDatabase.child(userID).child("lastname").setValue(user.getLastName());
        mUserDatabase.child(userID).child("bio").setValue(user.getBio());
        mUserDatabase.child(userID).child("interest").setValue(user.getInterest());
        mUserDatabase.child(userID).child("age").setValue(user.getAge());
        mUserDatabase.child(userID).child("profilepic").setValue(user.getEncodedProfilePicture());
        mUserDatabase.child(userID).child("status").setValue(user.isAvail());

    }

}
