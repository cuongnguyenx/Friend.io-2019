package com.example.friendio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class login_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText usernameEditText = findViewById(R.id.username);
        String username = usernameEditText.getText().toString();
        final EditText passwordEditText = findViewById(R.id.password);
        String password = passwordEditText.getText().toString();



    }
}


