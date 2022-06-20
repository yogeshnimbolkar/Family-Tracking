package com.example.familytracking;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class Name extends AppCompatActivity {
private Button n1;
private EditText name2;
String email,password;
CircleImageView civ;
    Uri resultUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        getSupportActionBar().setTitle("Set Profile");
        name2=findViewById(R.id.namep);
        civ=findViewById(R.id.cimg);
        n1=findViewById(R.id.buttonp);
        Intent myintent=getIntent();
        if(myintent!=null)
        {
            email=myintent.getStringExtra("email");
            password=myintent.getStringExtra("password");
        }
        n1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=name2.getText().toString();
                Date mydate=new Date();
                if(name.length()==0)
                {
                    name2.setError("Please enter name");
                    name2.requestFocus();
                }
                else {
                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:SS a", Locale.getDefault());
                    String date = format1.format(mydate);
                    Random r = new Random();
                    int n = 100000 + r.nextInt(900000);
                    String code = String.valueOf(n);
                    Intent myintent = new Intent(Name.this, GenerateCode.class);
                    myintent.putExtra("name", name);
                    myintent.putExtra("email", email);
                    myintent.putExtra("password", password);
                    myintent.putExtra("code", code);
                    myintent.putExtra("date", date);
                    myintent.putExtra("isSharing", "false");
                    myintent.putExtra("img", resultUri);
                    startActivity(myintent);
                }

            }
        });
        civ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i7=new Intent();
                i7.setAction(Intent.ACTION_GET_CONTENT);
                i7.setType("image/*");
                startActivityForResult(i7,1);

            }


        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
if(requestCode==1&& resultCode==RESULT_OK&& data!=null)
{
    CropImage.activity()
            .setGuidelines(CropImageView.Guidelines.ON)
            .setAspectRatio(1,1)
            .start(this);

}
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                civ.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }

    }
    }
}
