package com.example.friendio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class profile_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        final EditText firstNameEditText = findViewById(R.id.firstName);
        String firstName = firstNameEditText.getText().toString();

        final EditText lastNameEditText = findViewById(R.id.lastName);
        String lastName = firstNameEditText.getText().toString();

        final EditText ageEditText = findViewById(R.id.age);
        String age = firstNameEditText.getText().toString();

        final EditText bioEditText = findViewById(R.id.bio);
        String bio = firstNameEditText.getText().toString();

        final EditText hobbiesEditText = findViewById(R.id.hobbies);
        String hobbies = firstNameEditText.getText().toString();

    }
}
