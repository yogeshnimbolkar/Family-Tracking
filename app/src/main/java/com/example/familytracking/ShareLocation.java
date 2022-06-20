package com.example.familytracking;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ShareLocation extends AppCompatActivity {

    int PERMISSION_ID = 44;
    FusedLocationProviderClient mFusedLocationClient;
    TextView userCode;
    String key;
    String lat ,lng;
    DatabaseReference ref;
    Double l1,l2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_location);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getSupportActionBar().setTitle("Show Code");
        userCode=(TextView)findViewById(R.id.user_code);
        ref= FirebaseDatabase.getInstance().getReference();
        Intent i16=getIntent();
        if(i16!=null)
        {
            key=i16.getStringExtra("shareLocation");
            lat=i16.getStringExtra("dlat");
            lng=i16.getStringExtra("dlng");

        }
        userCode.setText(key);
        update();
    }


    private void update() {


            ref= FirebaseDatabase.getInstance().getReference().child("Users");
            Query editQuery=ref.orderByChild("code").equalTo(key);
            editQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot edtData: dataSnapshot.getChildren())
                    {
                        edtData.getRef().child("lat").setValue(lat); //in child represent database column
                        edtData.getRef().child("lng").setValue(lng);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }


            });



    }


    }
