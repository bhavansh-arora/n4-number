package com.sugarbaby.luckywheel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class mobile_number_authentication extends AppCompatActivity {
    TextInputEditText mobile_number;
    TextInputLayout mobile;

    ProgressDialog progressDialog;


    String phonenumber,phone;
    Intent intent;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_number_authentication);
        db = FirebaseFirestore.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait...");
        progressDialog.setMessage("Registering Your Mobile Number....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);


        mobile=findViewById(R.id.mobile);
        mobile_number=findViewById(R.id.mobile_number);
        mobile_number.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mobile_number.setCompoundDrawablesWithIntrinsicBounds(R.drawable.mobile,0,0,0);

            }
        });
    }


    public void registe(View view) {
        if(mobile.getEditText().getText().toString().trim().length()<10){
            mobile.setError("Invalid Mobile Number");
        }else{
            mobile.setError(null);
            phonenumber="+"+91+mobile.getEditText().getText().toString().trim();
phone=mobile.getEditText().getText().toString().trim();
            intent = new Intent(getApplicationContext(), otp_verification.class);

            intent.putExtra("phonenumber",phonenumber);
            intent.putExtra("phone",phone);

            startActivity(intent);

            progressDialog.dismiss();
            finish();
        }
    }


}



