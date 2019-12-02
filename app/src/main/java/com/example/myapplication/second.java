package com.example.myapplication;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class second extends AppCompatActivity {
    EditText name,email,pass;
    Button sign;
    FirebaseAuth auth;
    ProgressDialog pbr;
    FirebaseDatabase db=FirebaseDatabase.getInstance();
    DatabaseReference mdata=db.getReference().child("user");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        name=(EditText)findViewById(R.id.editname);
        email=(EditText)findViewById(R.id.editemail);
        pass=(EditText)findViewById(R.id.editpassword);
        sign=(Button)findViewById(R.id.sign);

        //mdata= FirebaseDatabase.getInstance().
        pbr= new ProgressDialog(this);
        auth=FirebaseAuth.getInstance();
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pbr.setMessage("Sign up");
                //pbr.show();
                auth.createUserWithEmailAndPassword(email.getText().toString(),pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                         // String user_id=auth.getCurrentUser().getUid();
                         //  DatabaseReference current= mdata.child(user_id);
                           //current.child("name").setValue(name.getText().toString());
                           //pbr.dismiss();
                        }
                    }
                });
            }
        });

    }
}
