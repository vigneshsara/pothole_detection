package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class Mainsample extends AppCompatActivity  {
    private LocationManager locationManager;
    private LocationListener locationListener;
    FirebaseAuth.AuthStateListener myauthlen;
    FirebaseAuth myauth;
    double latitude;
    double longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainsample);
        myauth=FirebaseAuth.getInstance();
        myauthlen=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null)
                {


                   // Intent it=new Intent(Mainsample.this,sensorservice.class);
                   // startService(it);
                   Intent it1=new Intent(getApplicationContext(),location.class);
                    startActivity(it1);
                }
                else
                {
                    Intent it1=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(it1);
                }

            }
        };
    }



    @Override
    protected void onStart() {
        super.onStart();
        myauth.addAuthStateListener(myauthlen);
    }
}
