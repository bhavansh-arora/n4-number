package com.sugarbaby.luckywheel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bluehomestudio.luckywheel.LuckyWheel;
import com.bluehomestudio.luckywheel.WheelItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class wheel extends AppCompatActivity {
    LuckyWheel lmw;
    List list;

    private ImageView wheel;
    private Button spin;
    private String txt;
    private int z = 0;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
private String phone,status;
    ProgressDialog progressDialog1;
String number="";
long amount,profit,updated_balance;
String time;
long money;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheel);
        getSupportActionBar().hide();
        SharedPreferences sharedpreferences = getSharedPreferences("preference", MODE_PRIVATE);
        phone = sharedpreferences.getString("phone", "");
        progressDialog1 = new ProgressDialog(this);
        progressDialog1.setTitle("Please Wait...");
        progressDialog1.setMessage("Updating Credentials....");
        progressDialog1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog1.setCanceledOnTouchOutside(false);
        progressDialog1.setIndeterminate(true);
        //lmw=findViewById(R.id.lwv);
       /* List<WheelItem> wheelItems = new ArrayList<>();
        wheelItems.add(new WheelItem(Color.rgb(172,32,225),BitmapFactory.decodeResource(getResources(),R.drawable.dot),"0"));
        wheelItems.add(new WheelItem(Color.rgb(226,70,79),BitmapFactory.decodeResource(getResources(),R.drawable.dot),"1"));
        wheelItems.add(new WheelItem(Color.rgb(213,193,36),BitmapFactory.decodeResource(getResources(),R.drawable.dot),"2"));
        wheelItems.add(new WheelItem(Color.rgb(29,170,118),BitmapFactory.decodeResource(getResources(),R.drawable.dot),"3"));
        wheelItems.add(new WheelItem(Color.rgb(28,133,224),BitmapFactory.decodeResource(getResources(),R.drawable.dot),"4"));
        wheelItems.add(new WheelItem(Color.rgb(172,32,225),BitmapFactory.decodeResource(getResources(),R.drawable.dot),"5"));
        wheelItems.add(new WheelItem(Color.rgb(226,70,79),BitmapFactory.decodeResource(getResources(),R.drawable.dot),"6"));
        wheelItems.add(new WheelItem(Color.rgb(213,193,36),BitmapFactory.decodeResource(getResources(),R.drawable.dot),"7"));
        wheelItems.add(new WheelItem(Color.rgb(29,170,118),BitmapFactory.decodeResource(getResources(),R.drawable.dot),"8"));
        wheelItems.add(new WheelItem(Color.rgb(28,133,224),BitmapFactory.decodeResource(getResources(),R.drawable.dot),"9"));
        lmw.addWheelItems(wheelItems);
        */

        setDefault();

        spin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNumber();

            }
        });
    }
 void database_updater(){
      final  String TAG = "database";
     SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
     Calendar c = Calendar.getInstance();
     Date date = Calendar.getInstance().getTime();
     String sDate = format.format(date);
     Calendar calendar = Calendar.getInstance();
     int hour24hrs = calendar.get(Calendar.HOUR_OF_DAY);
     int minutes = calendar.get(Calendar.MINUTE);
     int seconds = calendar.get(Calendar.SECOND);
     System.out.println("Current hour 24hrs format:  " + hour24hrs + ":" + minutes +":"+ seconds);
     time=hour24hrs + ":" + minutes +":"+ seconds;
     Map<String, Object> data = new HashMap<>();
     data.put("mobile", phone);
     data.put("status", status);
     data.put("time",time);
     data.put("date",sDate);
     data.put("amount",money);
     db.collection("database")
             .add(data)
             .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                 @Override
                 public void onSuccess(DocumentReference documentReference) {
                     Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                 }
             })
             .addOnFailureListener(new OnFailureListener() {
                 @Override
                 public void onFailure(@NonNull Exception e) {
                     Log.w(TAG, "Error adding document", e);
                 }
             });


 }
    void getNumber() {
        progressDialog1.show();
        db.collection("users").document(phone).get()

                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                long rr;

                                rr =document.getLong("winning_number");
                                updated_balance = document.getLong("balance");
                                //     System.out.println("text"+txt);

                                progressDialog1.hide();
                                if (rr >= 0 && rr <= 9) {
                                    spinWheel((int)rr);
                                }
                                if(rr==0){
                                    number="zero";
                                }
                               else if(rr==1){
                                    number = "one";
                                }
                               else if(rr==2){
                                   number = "two";
                                }
                                else if(rr==3){
                                    number = "three";
                                }
                                else if(rr==4){
                                    number = "four";
                                }
                                else if(rr==5){
                                    number = "five";
                                }
                                else if(rr==6){
                                    number = "six";
                                }
                                else if(rr==7){
                                    number = "seven";
                                }
                                else if(rr==8){
                                    number = "eight";
                                }
                                else if(rr==9){
                                    number = "nine";
                                }
                            } else {
                                Toast.makeText(wheel.this, "OOPS! Some error occured.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            System.out.println("get failed with "+task.getException());
                            Toast.makeText(wheel.this, "OOPS! Some error occured.", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
            .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(wheel.this, "Error004", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setDefault() {
        wheel = (ImageView) findViewById(R.id.spin_wheel_image);
        // value = (EditText) findViewById(R.id.edit_text);
        spin = (Button) findViewById(R.id.spin_button);
        // gift = (ImageView) findViewById(R.id.gift_image);
        //  bonus = (ImageView) findViewById(R.id.bonus_image);
    }

    public void spinWheel(final int rr) {
        RotateAnimation rotateAnimation = new RotateAnimation(0, (360 - (rr * 36)) + 720,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);

        rotateAnimation.setDuration(3000);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setInterpolator(new DecelerateInterpolator());


        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }
            @Override
            public void onAnimationEnd(Animation animation) {
                z = z + rr;
                final String result = String.valueOf(z);
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run() {
                        progressDialog1.show();
                        final DocumentReference docRef = db.collection("users").document(phone);
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                     amount = document.getLong(number);
                                     long one = document.getLong("one");
                                     long two = document.getLong("two");
                                     long three = document.getLong("three");
                                     long four = document.getLong("four");
                                     long five = document.getLong("five");
                                     long six = document.getLong("six");
                                     long seven = document.getLong("seven");
                                     long eight = document.getLong("eight");
                                     long nine = document.getLong("nine");
                                     long zero = document.getLong("zero");
money = one + two + three + four + five + six + seven + eight + nine + zero;




                                        System.out.println(amount);
                                     if(amount==0){
                                         status = "LOSE";
                                         Map<String, Object> data = new HashMap<>();
                                         data.put("bets_placed", false);
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
                                         data.put("game_id",0);

                                         db.collection("users").document(phone)
                                                 .set(data, SetOptions.merge())
                                                 .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                     @Override
                                                     public void onComplete(@NonNull Task<Void> task) {
                                                         SharedPreferences sharedpreferences = getSharedPreferences("preference", MODE_PRIVATE);

                                                         final SharedPreferences.Editor editor = sharedpreferences.edit();

                                                         editor.putBoolean("bets_placed", false);
                                                         editor.putInt("balance", (int)updated_balance);
                                                         editor.commit();


                                                         System.out.println("Completed");
                                                         database_updater();
                                                         Intent intent = new Intent(wheel.this, profile.class);
                                                         startActivity(intent);
                                                         finish();
                                                         progressDialog1.dismiss();
                                                     }
                                                 })
                                                 .addOnFailureListener(new OnFailureListener() {
                                                     @Override
                                                     public void onFailure(@NonNull Exception e) {
                                                         Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                                         progressDialog1.dismiss();
                                                     }
                                                 });

                                     }
                                     else{
                                         status = "WIN";
                                         amount = amount*100;
                                         profit = amount/11;
                                         System.out.println(profit);
                                         Map<String, Object> data = new HashMap<>();
                                         data.put("balance", updated_balance+profit);
                                         data.put("bets_placed", false);
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
                                         data.put("game_id",0);
                                         db.collection("users").document(phone)
                                                 .set(data, SetOptions.merge())
                                                 .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                     @Override
                                                     public void onComplete(@NonNull Task<Void> task) {
                                                         SharedPreferences sharedpreferences = getSharedPreferences("preference", MODE_PRIVATE);
money = updated_balance + profit;
                                                         final SharedPreferences.Editor editor = sharedpreferences.edit();

                                                         editor.putBoolean("bets_placed", false);
                                                         editor.putInt("balance", (int)updated_balance+(int)profit);
                                                         editor.commit();


                                                         System.out.println("Completed");
                                                         database_updater();
                                                         Intent intent = new Intent(wheel.this, profile.class);
                                                         startActivity(intent);
                                                         finish();
                                                         progressDialog1.dismiss();
                                                     }
                                                 })
                                                 .addOnFailureListener(new OnFailureListener() {
                                                     @Override
                                                     public void onFailure(@NonNull Exception e) {
                                                         Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                                         progressDialog1.dismiss();
                                                     }
                                                 });

                                     }


                                    }

                                } else {
                                    Toast.makeText(getApplicationContext(), "OOPS! Some error occurred.", Toast.LENGTH_LONG).show();
                                }
                            }
                        });



                    }
                }, 500);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

        });
        wheel.startAnimation(rotateAnimation);
    }
}