package com.example.friendio_2019;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Database {
    private DatabaseReference mUserDatabase;
    private DatabaseReference mGeoDatabase;

    public Database() {
        mUserDatabase = FirebaseDatabase.getInstance().getReference();
        mGeoDatabase = FirebaseDatabase.getInstance().getReference("/geofire");
    }

    public DatabaseReference getUserDatabaseReference() {
        return mUserDatabase;
    }

    public DatabaseReference getGeoDatabaseReference() {
        return mGeoDatabase;
    }

    public void writeNewUser(String userID, User user) {

        mUserDatabase.child("users").child(userID).child("firstname").setValue(user.getFirstName());
        mUserDatabase.child("users").child(userID).child("lastname").setValue(user.getLastName());
        mUserDatabase.child("users").child(userID).child("bio").setValue(user.getBio());
        mUserDatabase.child("users").child(userID).child("interest").setValue(user.getInterest());
        mUserDatabase.child("users").child(userID).child("age").setValue(user.getAge());
        mUserDatabase.child("users").child(userID).child("profilepic").setValue(user.getEncodedProfilePicture());
        mUserDatabase.child("users").child(userID).child("status").setValue(user.isAvail());

    }
}
