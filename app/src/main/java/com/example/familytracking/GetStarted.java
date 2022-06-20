package com.example.familytracking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class GetStarted extends AppCompatActivity {
Button b11;
TextView t1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getstarted);
        getSupportActionBar().hide();
        b11=findViewById(R.id.b1);
        t1=findViewById(R.id.signin);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2=new Intent(GetStarted.this,SignWithEmail.class);
                startActivity(i2);


            }
        });



            b11.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                Intent i4=new Intent(GetStarted.this,SignUp.class);
                startActivity(i4);


                }
            });

    }
}
