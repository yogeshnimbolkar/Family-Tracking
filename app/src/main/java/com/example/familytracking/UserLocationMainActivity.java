package com.example.familytracking;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import android.os.Looper;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;

import android.widget.TextView;
import android.widget.Toast;

public class UserLocationMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private AppBarConfiguration mAppBarConfiguration;
    GoogleMap mMap;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference databaseReference;
    String current_user_name;
    String current_user_email;
    String current_user_code;
    Double current_user_lat;
    Double current_user_lng;
    String name,lat=null,lng=null;
    Double mlat,mlng;
    int PERMISSION_ID = 44;
    FusedLocationProviderClient mFusedLocationClient;

    TextView current_name,current_email;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_location_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
      toolbar.setTitle("Family Tracking");
        Intent i29=getIntent();
        if(i29!=null)
        {
            mlat=Double.valueOf(i29.getStringExtra("slat"));
            mlng=Double.valueOf(i29.getStringExtra("slng"));



        }

        auth=FirebaseAuth.getInstance();
         user=auth.getCurrentUser();
         mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
          databaseReference= FirebaseDatabase.getInstance().getReference().child("Users");
         current_name=(TextView)findViewById(R.id.t1_user) ;
         current_email=(TextView)findViewById(R.id.t2_user);
        data();
        setSupportActionBar(toolbar);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


       ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,toolbar
       ,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
       drawer.addDrawerListener(toggle);
       toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
          navigationView.setNavigationItemSelectedListener(this);





    }
    public void onMapReady(GoogleMap googleMap){
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

        LatLng sydney = new LatLng(mlat, mlng);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Your Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }


    public void data(){

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(user.getUid())) {
                    current_user_name = dataSnapshot.child(user.getUid()).child("name").getValue(String.class);
                    current_user_email = dataSnapshot.child(user.getUid()).child("email").getValue(String.class);
                    current_user_code = dataSnapshot.child(user.getUid()).child("code").getValue(String.class);



                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.



        getMenuInflater().inflate(R.menu.user_location_main, menu);


        return true;
    }
    public boolean onOptionsItemSelected(MenuItem menu) {
      int id=menu.getItemId();
      if(id==R.id.action_settings)
      {
          return true;
      } else if(id==R.id.direct_signout)
      {
//sign out

          if(user!=null)
          {
              auth.signOut();
              finish();
              Intent i10=new Intent(UserLocationMainActivity.this,SignWithEmail.class);
              startActivity(i10);
          }

      }


        return super.onOptionsItemSelected(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_view);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
         int id=menuItem.getItemId();
         if(id==R.id.nav_home)
         {
           // Intent intent=new Intent(UserLocationMainActivity.this,AddMember.class);


                 // add Member
             Intent i11=new Intent(UserLocationMainActivity.this,AddMember.class);
            startActivity(i11);

         }
         else if(id==R.id.nav_gallery)
         {

             //share location
             Intent i12=new Intent(UserLocationMainActivity.this,Dashboard.class);

             i12.putExtra("shareLocation",current_user_code);
             startActivity(i12);


         } else if(id==R.id.nav_tools){
             FirebaseUser user=auth.getCurrentUser();
             if(user!=null)
             {
                 auth.signOut();
                 finish();
                 Intent i10=new Intent(UserLocationMainActivity.this,SignWithEmail.class);
                 startActivity(i10);
             }
             //sign out
         }

        return false;


    }
}
