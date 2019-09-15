package com.example.friendio_2019;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseReference;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Looper;

public class LocationActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION = 1;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Database localDatabase = new Database();
        DatabaseReference ref = localDatabase.getUserDatabaseReference();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ContextCompat.checkSelfPermission(LocationActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(LocationActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                new AlertDialog.Builder(this)
                        .setTitle("Location permission required")
                        .setMessage("Please allow location permission for usability.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(LocationActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        LOCATION_PERMISSION);
                            }
                        })
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(LocationActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_PERMISSION);
            }
        } else {
            updateLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    updateLocation();
                } else {
                    new AlertDialog.Builder(this)
                            .setTitle("Shutting down ...")
                            .setMessage("Location permission isn't granted.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                            .create()
                            .show();
                }
            }
        }
    }

    private void updateLocation() {
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location == null) {
                        continue;
                    }
                    Double latitude = location.getLatitude();
                    Double longitude = location.getLongitude();
                    new AlertDialog.Builder(LocationActivity.this)
                            .setTitle("Current location")
                            .setMessage(latitude + " " + longitude)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create()
                            .show();
                }
            }
        };

        fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper());
    }
}
