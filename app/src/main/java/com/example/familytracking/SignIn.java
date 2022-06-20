package com.example.familytracking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {

EditText e1;
Button b1;
FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().setTitle("Forgot Password");
        e1=findViewById(R.id.femail);
        b1=findViewById(R.id.fsend);
        firebaseAuth=FirebaseAuth.getInstance();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String femail=e1.getText().toString();
                if(femail.length()==0)
                {
                    e1.setError("Enter email ");
                    e1.requestFocus();

                }
                else {

                    e1.setError("please enter email");
                    firebaseAuth.sendPasswordResetEmail(femail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SignIn.this, "reset link sent to registered email id", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(), SignWithEmail.class));
                            } else {
                                Toast.makeText(SignIn.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                            }
                        }
                    });

                }
            }
        });

    }
}
