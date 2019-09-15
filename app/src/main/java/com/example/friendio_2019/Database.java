package com.example.friendio_2019;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Database {
    private DatabaseReference mUserDatabase;

    public Database() {
        mUserDatabase = FirebaseDatabase.getInstance().getReference("users");
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
        mUserDatabase.child(userID).child("latitude").setValue(user.getLatitude());
        mUserDatabase.child(userID).child("longitude").setValue(user.getLatitude());
    }
}
