package com.example.friendio_2019;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class User {
    private String mFirstName;
    private String mLastName;
    private String mBio;
    private String mEncodedProfilePicture;

    private int mAge;
    private String mInterest;

    private float mLongitude;
    private float mLatitude;

    private String mStatus;

    // Setting status
    public void setStatusAvail() {
        mStatus = "AVAIL";
    }
    public void setStatusUnavail() {
        mStatus = "UNAVAIL";
    }

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public User(String firstName, String lastName, String bio, String encodedProfilePicture,
                int age, String interest)
    {
        this.mFirstName = firstName;
        this.mLastName = lastName;
        this.mBio = bio;
        this.mEncodedProfilePicture = encodedProfilePicture;
        this.mAge = age;
        this.mInterest = interest;
        this.mLatitude = (float) 0.0;
        this.mLongitude = (float) 0.0;
    }


    // Age
    public void setAge(int age) {
        mAge = age;
    }
    public int getAge() {
        return mAge;
    }

    // Interests
    public String getInterest() {
        return mInterest;
    }

    public void setInterest(String interest) {
        mInterest = interest;
    }

    // Set name of the user
    public void setName(String firstName, String lastName) {
        mFirstName = firstName;
        mLastName = lastName;
    }

    public void setBio(String bio){
        mBio = bio;
    }
    public String getBio() {
        return mBio;
    }

    // Get name of user
    public String getFirstName() {
        return mFirstName;
    }
    public String getLastName() {
        return mLastName;
    }

    // Set coordinates of the user
    public void setCoords(float latitude, float longitude) {
        mLatitude = latitude;
        mLongitude = longitude;
    }

    // Get coordinates of the user
    public ArrayList<Float> getCoords() {
        ArrayList<Float> coords = new ArrayList<>();
        coords.add(mLatitude);
        coords.add(mLongitude);
        return coords;
    }

    // Returns true if the user is available
    public boolean isAvail() {
        if (mStatus == "AVAIL") {
            return true;
        } else {
            return false;
        }
    }

    // Takes an ImageView and converts it into String to store in database

    public void setProfilePicture(ImageView profilePicImageView) {
        // Conversion to bitmap
        BitmapDrawable drawable = (BitmapDrawable) profilePicImageView.getDrawable();
        Bitmap bitmapProfilePic = drawable.getBitmap();

        // Conversion to String
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmapProfilePic.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] byteArrayImage = baos.toByteArray();

        mEncodedProfilePicture = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
    }

    // Returns a bitmap image of user's profile picture

    public Bitmap getProfilePicture() {
        byte[] decodedString = Base64.decode(mEncodedProfilePicture, Base64.DEFAULT);
        Bitmap decodeByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodeByte;
    }

}
