package com.example.familytracking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AddMember extends AppCompatActivity {
Button b6;
TextView t6;
String enterCode;
String amname,amlat,amlng;
    FirebaseUser user;
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);
        getSupportActionBar().setTitle("Add Member");
        b6=(Button)findViewById(R.id.add_memeber);
        t6=(TextView)findViewById(R.id.enter_code);
        auth= FirebaseAuth.getInstance();
        databaseReference=FirebaseDatabase.getInstance().getReference();

        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        enterCode=t6.getText().toString();

                databaseReference= FirebaseDatabase.getInstance().getReference().child("Users");
               Query editQuery=databaseReference.orderByChild("code").equalTo(enterCode);
                editQuery.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                         for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {

                            CreateUser  createUser= dataSnapshot1.getValue(CreateUser.class);
                             amname=createUser.getName();
                             amlat=createUser.getLat();
                             amlng=createUser.getLng();
                           //  Toast.makeText(AddMember.this, createUser.getName(),Toast.LENGTH_LONG).show();
                             //Toast.makeText(AddMember.this, createUser.getLat(),Toast.LENGTH_LONG).show();
                           //  Toast.makeText(AddMember.this, createUser.getLng(),Toast.LENGTH_LONG).show();
                             Intent intent=new Intent(getApplicationContext(),MapsActivity.class);
                             intent.putExtra("aname",amname);
                             intent.putExtra("alat",amlat);
                             intent.putExtra("alng",amlng);
                             startActivity(intent);
                            /* setResult(RESULT_OK,intent);
                             finish();
*/

                         }

                 }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });



            }
        });
    }
}
