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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    EditText email,password;
    Button btn,reg;
    String str;
    FirebaseAuth myauth;
    public double latitude;
    public double longitude;
    FirebaseDatabase db=FirebaseDatabase.getInstance();
    FirebaseAuth.AuthStateListener myauthlen;
    DatabaseReference dbref=db.getReference("root");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myauth=FirebaseAuth.getInstance();
        email=(EditText)findViewById(R.id.emailfield);
        password=(EditText)findViewById(R.id.password);
        btn=(Button)findViewById(R.id.btn);
        reg=(Button)findViewById(R.id.reg);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(getApplicationContext(),second.class);
                startActivity(it);

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str = email.getText().toString();
                String str1 = password.getText().toString();
                //dbref.child(str).setValue("viki");
                myauth.signInWithEmailAndPassword(str,str1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),"firebaseconnect",Toast.LENGTH_LONG).show();
                            Intent it=new Intent(getApplicationContext(),sensorservice.class);
                            startService(it);
                        }
                    }
                });
            }
        });
    }



}

