package com.sugarbaby.luckywheel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class otp_verification extends AppCompatActivity {
    String code, email, password, phonenumber, name,phone;
    Button register;
    TextView otp_sent;
    SharedPreferences preferences;
    String balance;


    private FirebaseAuth mAuth;
    ProgressDialog progressDialog,progressDialog1;

    Pinview pinview;

    private String verificationid;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        progressDialog1 = new ProgressDialog(this);
        progressDialog1.setTitle("Please Wait...");
        progressDialog1.setMessage("Updating Credentials....");
        progressDialog1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog1.setCanceledOnTouchOutside(false);
        progressDialog1.setIndeterminate(true);
        db = FirebaseFirestore.getInstance();
        progressDialog1.show();
        DocumentReference docRef = db.collection("timings").document("time");
                            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            System.out.println(document.get("balance"));

progressDialog1.hide();
balance = (String) document.get("balance");


                                        }

                                    } else {
                                        Toast.makeText(getApplicationContext(), "OOPS! Some error occurred.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });





        otp_sent = findViewById(R.id.otp_sent);

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait...");
        progressDialog.setMessage("Verifying Your Mobile Number....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(true);


        pinview = (Pinview) findViewById(R.id.pinview);
        register = findViewById(R.id.send);
        phonenumber = getIntent().getStringExtra("phonenumber");
        phone = getIntent().getStringExtra("phone");

        email = getIntent().getStringExtra("email");
        name = getIntent().getStringExtra("name");

        password = getIntent().getStringExtra("password");
        otp_sent.setText("OTP sent to " + String.valueOf(phonenumber));

        sendVerificationCode(phonenumber);

        pinview.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean fromUser) {
                closekeyboard();
                progressDialog.show();
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                code = pinview.getValue().trim();

                verifyCode(code);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pinview.getValue().trim().length() < 6) {
                    Toast.makeText(otp_verification.this, "Please Enter a Valid Code", Toast.LENGTH_LONG).show();

                } else {
                    code = pinview.getValue().trim();
                    //Toast.makeText(getApplicationContext(),code.toString(),Toast.LENGTH_LONG).show();
                    progressDialog.show();
                   verifyCode(code);
                }
            }
        });


    }

    private void verifyCode(String code) {
        if(verificationid!=null) {

            System.out.println(verificationid);
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationid, code);
            signInWithCredential(credential);

        }
        else{
            Toast.makeText(getApplicationContext(),"OOPS! Something went wrong. Please re-enter the mobile number.",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(otp_verification.this,mobile_number_authentication.class);
            startActivity(intent);
            finish();
        }
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            DocumentReference docRef = db.collection("users").document(phone);
                            SharedPreferences sharedpreferences = getSharedPreferences("preference", MODE_PRIVATE);
                            final SharedPreferences.Editor editor = sharedpreferences.edit();
                            docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                    if(value.exists()){
                                        editor.putBoolean("logged",true);
                                        editor.putString("phone",phone);
                                        editor.commit();
                                        progressDialog.hide();
                                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                        Intent intent = new Intent(otp_verification.this, profile.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else{
                                        Map<String, Object> data = new HashMap<>();
                                        data.put("balance", Integer.parseInt(balance));
                                        data.put("winnings", 0);
                                        data.put("bets_placed", false);
                                        data.put("game_id",0);
                                        data.put("zero", 0);
                                        data.put("one", 0);
                                        data.put("two", 0);
                                        data.put("three", 0);
                                        data.put("four", 0);
                                        data.put("five", 0);
                                        data.put("six", 0);
                                        data.put("seven", 0);
                                        data.put("eight", 0);
                                        data.put("nine", 0);
                                        data.put("winning_number",0);




                                        db.collection("users").document(phone)
                                                .set(data, SetOptions.merge())
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        editor.putBoolean("logged",true);
                                                        editor.putString("phone",phone);
                                                        editor.commit();

                                                        progressDialog.hide();
                                                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                                        Intent intent = new Intent(otp_verification.this, profile.class);
                                                        startActivity(intent);
                                                        finish();

                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                                                    }
                                                });
                                    }
                                }
                            });







                        } else {
                            Toast.makeText(otp_verification.this, task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();
                            progressDialog.hide();
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        }
                    }

                });
    }

    private void sendVerificationCode(String number) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            verificationid = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            code = phoneAuthCredential.getSmsCode();
            if (code != null) {

                progressDialog.show();
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                verifyCode(code);
            }
            signInWithCredential(phoneAuthCredential);

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            progressDialog.hide();
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            Toast.makeText(otp_verification.this, e.getMessage(), Toast.LENGTH_LONG).show();

        }
    };




    private void closekeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

        }
    }





}