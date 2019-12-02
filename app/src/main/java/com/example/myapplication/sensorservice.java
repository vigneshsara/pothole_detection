package com.example.myapplication;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class sensorservice extends Service implements SensorEventListener {



  FirebaseAuth auth = FirebaseAuth.getInstance();
  FirebaseDatabase db = FirebaseDatabase.getInstance();
  DatabaseReference mdata = db.getReference().child("user");

    float xaxis, yaxis, zaxis;
    float xprevious, yprevious, zprevious;
    boolean firstupdate = true;
    boolean shakeinitiatrd = false;
    float shakeThewshold = 8.0f;
    float a = 0.0f, b = 0.0f, c = 0.0f;
    long idvalue = 0;
     //Mainsample obj=new Mainsample();

    Sensor accelerometer;
    SensorManager sm;
    double a1,a2;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        updateAccelparameter(sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]);
        if ((!shakeinitiatrd) && isAccelerationChanged()) {
            shakeinitiatrd = true;

        } else if ((shakeinitiatrd) && isAccelerationChanged()) {
            executeShakeAction();
        } else if ((shakeinitiatrd) && !isAccelerationChanged()) {
            shakeinitiatrd = false;
        }
    }

    private void executeShakeAction() {
        auth=FirebaseAuth.getInstance();
     // location obj=new location();
       // obj.getlocation();
       // a1=obj.latitude;
        //a2=obj.longitude;
      //  Toast.makeText(getApplicationContext(), "shake x=" + a + " y=" + b + " c=" + c +" "+location.latitude[0], Toast.LENGTH_SHORT).show();
      //  Intent it1=new Intent(getApplicationContext(),location.class);
       // it1.putExtra("a",a);
        //it1.putExtra("b",b);
       // it1.putExtra("c",c);
      //  startActivity(it1);
      String user_id = auth.getCurrentUser().getUid();
        DatabaseReference current = mdata.child(user_id);
       if(location.latitude[0]!=0 && location.longitude[0]!=0 ) {
           current.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   idvalue = dataSnapshot.getChildrenCount() + 1;
               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
           });

           //obj.getlocation();


           DatabaseReference user1 = current.child(idvalue + "");
           idvalue = idvalue + 1;

           user1.child("xaxis").setValue(a);
           user1.child("yaxis").setValue(b);
           user1.child("zaxis").setValue(c);
           user1.child("latitude").setValue(location.latitude[0]);
           user1.child("longitude").setValue(location.longitude[0]);
       }

    }

    private boolean isAccelerationChanged() {
        float deltax = Math.abs(xprevious - xaxis);
        float deltay = Math.abs(yprevious - yaxis);
        float deltaz = Math.abs(zprevious - zaxis);
        a = deltax;
        b = deltay;
        c = deltaz;
        return (deltax > shakeThewshold && deltay > shakeThewshold)
                || (deltax > shakeThewshold && deltaz > shakeThewshold)
                || (deltay > shakeThewshold && deltaz > shakeThewshold);
    }

    private void updateAccelparameter(float xnew, float ynew, float znew) {
        if (firstupdate) {
            xprevious = xnew;
            yprevious = ynew;
            zprevious = znew;
            firstupdate = false;
        } else {
            xprevious = xaxis;
            yprevious = yaxis;
            zprevious = zaxis;
        }
        xaxis = xnew;
        yaxis = ynew;
        zaxis = znew;
    }


}
