package com.example.myapplication;

import android.Manifest;
import android.content.Context;
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
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class location extends AppCompatActivity {
    private LocationManager locationManager;
    private LocationListener locationListener;
    Button bt;
   static  double[] latitude = new double[1];
    static  double[] longitude = new double[1];

//    long idvalue = 0;
  //  FirebaseAuth auth = FirebaseAuth.getInstance();
    //FirebaseDatabase db = FirebaseDatabase.getInstance();
   // DatabaseReference mdata = db.getReference().child("user");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        bt = (Button) findViewById(R.id.buttonloc);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        getlocation();
     /*   String user_id = auth.getCurrentUser().getUid();
        DatabaseReference current = mdata.child(user_id);

        current.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                idvalue = dataSnapshot.getChildrenCount() + 1;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // obj.getlocation();


        DatabaseReference user1 = current.child(idvalue + "");
        // idvalue=idvalue+1;

        user1.child("xaxis").setValue(getIntent().getStringExtra("a"));
        user1.child("yaxis").setValue(getIntent().getStringExtra("b"));
        user1.child("zaxis").setValue(getIntent().getStringExtra("c"));
        user1.child("latitude").setValue(latitude);
        user1.child("longitude").setValue(longitude);
        Intent it=new Intent(getApplicationContext(),sensorservice.class);
        startService(it);*/
       Intent it=new Intent(getApplicationContext(),sensorservice.class);
        startService(it);

    }

     void getlocation() {


        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //msg=("EMEREGNCY \n http://www.google.com/maps/place/"+location.getLatitude()+","+location.getLongitu
                latitude[0] = location.getLatitude();
                longitude[0] = location.getLongitude();


            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent it = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(it);

            }
        };
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
                }, 10);
                return;
            } else {
                locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);

            }

        locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), latitude[0] + "" + longitude[0], Toast.LENGTH_LONG).show();
            }
        });
    }

    private void conf() {

    }

}
