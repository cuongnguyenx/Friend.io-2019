package com.example.friendio_2019;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.util.Map;

public class User {
    private String firstName;
    private String lastName;
    private String bio;
    private String encodedProfilePicture;
    private String interest;
    private boolean status;
    private long age;
    private double latitude;
    private double longitude;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public User(String firstName, String lastName, String bio, String encodedProfilePicture,
                int age, String interest) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;
        this.encodedProfilePicture = encodedProfilePicture;
        this.age = age;
        this.interest = interest;
        this.latitude = this.longitude = 0.01;
        this.status = true;
    }

    public User(Map<String, Object> user) {
        this.firstName = (String) user.get("firstName");
        this.lastName = (String) user.get("lastName");
        this.bio = (String) user.get("bio");
        this.age = (long) user.get("age");
        this.interest = (String) user.get("interest");
        this.encodedProfilePicture = (String) user.get("encodedProfilePicture");
        this.status = (boolean) user.get("status");
        this.latitude = (double) user.get("latitude");
        this.longitude = (double) user.get("longitude");
    }

    // Age
    public void setAge(long age) {
        age = age;
    }

    public long getAge() {
        return age;
    }

    // Interests
    public void setInterest(String interest) {
        interest = interest;
    }

    public String getInterest() {
        return interest;
    }

    // Set name of the user
    public void setName(String firstName, String lastName) {
        firstName = firstName;
        lastName = lastName;
    }

    public void setBio(String bio) {
        bio = bio;
    }

    public String getBio() {
        return bio;
    }

    // Get name of user
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    // Returns true if the user is available
    public boolean getStatus() {
        return status;
    }

    // Coordinates
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    // Takes an ImageView and converts it into String to store in database
    public void setEncodedProfilePicture(ImageView profilePicImageView) {
        // Conversion to bitmap
        BitmapDrawable drawable = (BitmapDrawable) profilePicImageView.getDrawable();
        Bitmap bitmapProfilePic = drawable.getBitmap();

        // Conversion to String
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmapProfilePic.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] byteArrayImage = baos.toByteArray();

        encodedProfilePicture = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
    }

    // Returns a bitmap image of user's profile picture
    public String getEncodedProfilePicture() {
        if (encodedProfilePicture != null) {
            return encodedProfilePicture;
        }
        return null;
    }
}
