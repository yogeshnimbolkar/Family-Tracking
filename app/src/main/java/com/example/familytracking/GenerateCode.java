package com.example.familytracking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class GenerateCode extends AppCompatActivity {
TextView tv;
Button reg;
String name,email,password,date,code,isSharing;
Uri imageUri;
FirebaseAuth auth;
FirebaseUser user;
DatabaseReference databaseReference;

String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_code);
        getSupportActionBar().setTitle("Set Circle");
        tv=findViewById(R.id.ccode);
        reg=findViewById(R.id.register);
        auth=FirebaseAuth.getInstance();
       // storageReference=FirebaseStorage.getInstance().getReference().child("Images");


            databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");




        Intent myintent=getIntent();
        if(myintent!=null)
        {
            name=myintent.getStringExtra("name");
            email=myintent.getStringExtra("email");
            password=myintent.getStringExtra("password");
            date=myintent.getStringExtra("date");
            code=myintent.getStringExtra("code");
            isSharing=myintent.getStringExtra("isSharing");
            imageUri=myintent.getParcelableExtra("img");


        }
        tv.setText(code);
        reg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
    auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
            CreateUser createUser = new CreateUser(name, email, password, code, "false", "na", "na", "na");
            user = auth.getCurrentUser();
            userId = user.getUid();
            databaseReference.child(userId).setValue(createUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(GenerateCode.this, "Registered Successful", Toast.LENGTH_LONG).show();
                                                Intent i8 = new Intent(getApplicationContext(), SignWithEmail.class);
                                                startActivity(i8);
                                            }
                    else {
                        Toast.makeText(GenerateCode.this, "Could not register", Toast.LENGTH_LONG).show();
                    }
                    }
            });
        }
    }

    });
            }
        });
    }
}