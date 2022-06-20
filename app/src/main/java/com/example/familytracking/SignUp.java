package com.example.familytracking;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {
private EditText e1,e2,cfm2;
String email,password;
 private Button b1;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().setTitle("Sign Up With Email");
       b1=findViewById(R.id.button2);
        e1=findViewById(R.id.editText);
        e2=findViewById(R.id.editText5);
        cfm2=findViewById(R.id.cfm);
        firebaseAuth=FirebaseAuth.getInstance();
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
    email=e1.getText().toString().trim();
    password=e2.getText().toString().trim();
    String cp=cfm2.getText().toString().trim();
                if(email.length()==0)
                {
                    e1.setError("Enter your email id ");
                    e1.requestFocus();

                }
                else if(password.length()==0)
                {
                    e2.setError("Enter your password ");
                    e2.requestFocus();
                }

                else if (cp.length()==0)
                {
                    cfm2.setError("Renter password");
                    cfm2.requestFocus();
                }

                           else if(cp.contentEquals(password))
                            {


                                Intent myintent=new Intent(getApplicationContext(),Name.class);
                                myintent.putExtra("email",email);
                                myintent.putExtra("password",password);
                                startActivity(myintent);


                            }
                           else
                           {
                               Toast.makeText(SignUp.this,"Password not matched",Toast.LENGTH_SHORT).show();
                           }
                            }
                        });
            }

            }



