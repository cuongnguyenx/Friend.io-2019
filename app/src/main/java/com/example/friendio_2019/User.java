package com.example.friendio_2019;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.util.Map;

public class User {
    private String mFirstName;
    private String mLastName;
    private String mBio;
    private String mEncodedProfilePicture;
    private String mInterest;
    private boolean mStatus;
    private long mAge;
    private double mLatitude;
    private double mLongitude;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public User(String firstName, String lastName, String bio, String encodedProfilePicture,
                int age, String interest) {
        this.mFirstName = firstName;
        this.mLastName = lastName;
        this.mBio = bio;
        this.mEncodedProfilePicture = encodedProfilePicture;
        this.mAge = age;
        this.mInterest = interest;
        this.mLatitude = this.mLongitude = 0.01;
        this.mStatus = true;
    }

    public User(Map<String, Object> user) {
        this.mFirstName = (String) user.get("firstname");
        this.mLastName = (String) user.get("lastname");
        this.mBio = (String) user.get("bio");
        this.mAge = (long) user.get("age");
        this.mInterest = (String) user.get("interest");
        this.mEncodedProfilePicture = (String) user.get("profilepic");
        this.mStatus = (boolean) user.get("status");
        this.mLatitude = (double) user.get("latitude");
        this.mLongitude = (double) user.get("longitude");
    }

    // Age
    public void setAge(long age) {
        mAge = age;
    }

    public long getAge() {
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

    public void setBio(String bio) {
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

    // Returns true if the user is available
    public boolean isAvail() {
        return mStatus;
    }

    // Coordinates
    public void setLatitude(Double latitude) {
        this.mLatitude = latitude;
    }

    public double getLatitude() {
        return this.mLatitude;
    }

    public void setLongitude(Double longitude) {
        this.mLongitude = longitude;
    }

    public double getLongitude() {
        return this.mLongitude;
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
    public String getEncodedProfilePicture() {
        if (mEncodedProfilePicture != null) {
            return mEncodedProfilePicture;
        }
        return null;
    }
}
