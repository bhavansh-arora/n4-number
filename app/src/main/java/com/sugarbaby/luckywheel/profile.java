package com.sugarbaby.luckywheel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;
import android.os.*;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class profile extends AppCompatActivity {

    private CircleImageView dp;
    public static final int STORAGE_REQUEST_CODE = 400;
    public static final int IMAGE_PICK_GALLERY_CODE = 1000;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private TextView timeLeft, nextGameText;
    private String time, tt = "00:00:00";
    private Button play;
    private LinearLayout nextGame;

    String storagePermission[];
    ProgressDialog progressDialog;
    boolean a = false, b = false,c=false;
    TextView rewards, balance;
    Boolean placed;
    Button buyTicket;
    TextView no1, no2, no3;
    private String number;
    long[] values = new long[3];
    long temp;
    long updated_game_id,unupdated_game_id;

int loop=0;
    @Override
    protected void onResume() {
        super.onResume();
        buyTicket = findViewById(R.id.buy_ticket);
        no1.setText("₹"+" "+String.valueOf(values[0]));
        no2.setText("₹"+" "+String.valueOf(values[1]));
        no3.setText("₹"+" "+String.valueOf(values[2]));

        SharedPreferences sharedpreferences = getSharedPreferences("preference", MODE_PRIVATE);
        int ba;
        ba = sharedpreferences.getInt("balance", 0);

        balance.setText(String.valueOf(ba));

        placed = sharedpreferences.getBoolean("bets_placed", false);

        if (placed) {
            buyTicket.setEnabled(false);
            buyTicket.setText("Ticket Bought");
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();
        buyTicket = findViewById(R.id.buy_ticket);
        no1 = findViewById(R.id.no1);
        no2 = findViewById(R.id.no2);
        no3 = findViewById(R.id.no3);
        rewards = findViewById(R.id.reward);
        balance = findViewById(R.id.balance);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait...");
        progressDialog.setMessage("Updating Credentials....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        db = FirebaseFirestore.getInstance();
        SharedPreferences sharedpreferences = getSharedPreferences("preference", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedpreferences.edit();
        final String phone = sharedpreferences.getString("phone", "abc");

        db.collection("currentgame")
                .whereEqualTo("id", 5)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                System.out.println(document.getData().get("value"));
                                values[loop] = (long) document.getData().get("value");
                                loop++;
                                c = true;
                            }
                            for (int i = 0; i < values.length; i++) {
                                for (int j = i + 1; j < values.length; j++) {
                                    if (values[i] > values[j]) {
                                        temp = values[i];
                                        values[i] = values[j];
                                        values[j] = temp;
                                    }
                                }
                            }
                            no1.setText("₹"+" "+String.valueOf(values[0]));
                            no2.setText("₹"+" "+String.valueOf(values[1]));
                            no3.setText("₹"+" "+String.valueOf(values[2]));
                                DocumentReference docRef = db.collection("users").document(phone);
                                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                System.out.println(document.get("balance"));
                                                balance.setText(document.get("balance").toString());
                                                rewards.setText(document.get("winnings").toString());
                                                placed = Boolean.parseBoolean(document.get("bets_placed").toString());
                                               unupdated_game_id = document.getLong("game_id");
                                                System.out.println("bool");
                                                System.out.println(placed);

                                                if (Boolean.parseBoolean(document.get("bets_placed").toString())) {
                                                    System.out.print("bool");
                                                    System.out.println(placed);
                                                    buyTicket.setEnabled(false);
                                                    buyTicket.setText("Ticket Bought");
                                                } else {
                                                    buyTicket.setEnabled(true);
                                                    buyTicket.setText("Buy Ticket");
                                                }
                                                a = true;
                                                SharedPreferences sharedpreferences = getSharedPreferences("preference", MODE_PRIVATE);

                                                final SharedPreferences.Editor editor = sharedpreferences.edit();

                                                editor.putBoolean("bets_placed", Boolean.parseBoolean(document.get("bets_placed").toString()));
                                                editor.putInt("balance", Integer.parseInt(document.get("balance").toString()));

                                                editor.commit();

                                                if (a == true && b == true && c==true) {


                                                    progressDialog.hide();
                                                }
                                            }

                                        } else {
                                            Toast.makeText(getApplicationContext(), "OOPS! Some error occurred.", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });

                        } else {
                            Toast.makeText(getApplicationContext(),"OOPS! Some Error Occured.",Toast.LENGTH_LONG).show();
                        }
                    }
                });

        number = String.valueOf(values[0]);


        setDefault();


        dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkStoragePermission()) {

                    requestStoragePermission();
                } else {
                    pickGallery();
                }
            }
        });
        setTimeLeft();

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(profile.this, wheel.class));
            }
        });

    }

    private void setTimeLeft() {
      db.collection("timings").document("time").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                time = documentSnapshot.getString("time");
                long game_id = documentSnapshot.getLong("game_id");
updated_game_id = documentSnapshot.getLong("game_id");
            //    String ar[] = time.split(" ");
              //  nextGameText.setText(ar[1]);
                nextGameText.setText(time);
               SharedPreferences sharedpreferences = getSharedPreferences("preference", MODE_PRIVATE);
                final SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("time",time);
                editor.putLong("game_id",game_id);
              //  editor.putString("time", ar[1]);
                editor.commit();
                try {
                    progressDialog.hide();
                    storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

                    if (!checkStoragePermission()) {

                        requestStoragePermission();
                    } else {
                        File folder = Environment.getExternalStoragePublicDirectory("/spinwheel/");
                        if (folder.exists()) {
                            Uri file = Uri.fromFile(new File(folder, "profile_pic.jpg"));
                            if (file != null) {
                                Picasso
                                        .get()
                                        .load(file)
                                        .into(dp);

                            }
                        }
                    }
                    compareTime(time);
                } catch (ParseException e) {
                    e.printStackTrace();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(profile.this, "Error004", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void compareTime(String time1) throws ParseException {
        System.out.println(updated_game_id);
        System.out.println(unupdated_game_id);
        SharedPreferences sharedpreferences = getSharedPreferences("preference", MODE_PRIVATE);

        unupdated_game_id = sharedpreferences.getLong("unupdated_game_id", 0);
boolean b = updated_game_id != unupdated_game_id;
System.out.println(b);
        if(b&&placed){
            System.out.println("if ran");
            timeLeft.setVisibility(View.INVISIBLE);
            nextGame.setVisibility(View.INVISIBLE);
            play.setVisibility(View.VISIBLE);
            play.setClickable(true);
        }
        else {


            String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            System.out.println(date);
            progressDialog.hide();
            b = true;
            if (a == true && b == true && c == true) {
                progressDialog.hide();
            }
            String time_builder = date + " " + time1;
            SimpleDateFormat sdf
                    = new SimpleDateFormat(
                    "dd-MM-yyyy HH:mm:ss");

            Date d1 = Calendar.getInstance().getTime();
            Date d2 = sdf.parse(String.valueOf(time_builder));
            long difference_In_Time1
                    = d2.getTime() - d1.getTime();


            new CountDownTimer(difference_In_Time1, 1000) {

                public void onTick(long millisUntilFinished) {

                    long difference_In_Time = millisUntilFinished;
                    long difference_In_Seconds
                            = (difference_In_Time
                            / 1000)
                            % 60;

                    long difference_In_Minutes
                            = (difference_In_Time
                            / (1000 * 60))
                            % 60;

                    long difference_In_Hours
                            = (difference_In_Time
                            / (1000 * 60 * 60))
                            % 24;

                    if (difference_In_Hours / 10 == 0 && difference_In_Minutes / 10 == 0 && difference_In_Seconds / 10 == 0)
                        tt = "0" + difference_In_Hours + ":0" + difference_In_Minutes + ":0" + difference_In_Seconds;
                    if (difference_In_Hours / 10 == 0 && difference_In_Minutes / 10 == 0 && difference_In_Seconds / 10 != 0)
                        tt = "0" + difference_In_Hours + ":0" + difference_In_Minutes + ":" + difference_In_Seconds;
                    if (difference_In_Hours / 10 == 0 && difference_In_Minutes / 10 != 0 && difference_In_Seconds / 10 == 0)
                        tt = "0" + difference_In_Hours + ":" + difference_In_Minutes + ":0" + difference_In_Seconds;
                    if (difference_In_Hours / 10 != 0 && difference_In_Minutes / 10 == 0 && difference_In_Seconds / 10 == 0)
                        tt = difference_In_Hours + ":0" + difference_In_Minutes + ":0" + difference_In_Seconds;
                    if (difference_In_Hours / 10 != 0 && difference_In_Minutes / 10 != 0 && difference_In_Seconds / 10 == 0)
                        tt = difference_In_Hours + ":" + difference_In_Minutes + ":0" + difference_In_Seconds;
                    if (difference_In_Hours / 10 != 0 && difference_In_Minutes / 10 == 0 && difference_In_Seconds / 10 != 0)
                        tt = difference_In_Hours + ":0" + difference_In_Minutes + ":" + difference_In_Seconds;
                    if (difference_In_Hours / 10 == 0 && difference_In_Minutes / 10 != 0 && difference_In_Seconds / 10 != 0)
                        tt = "0" + difference_In_Hours + ":" + difference_In_Minutes + ":" + difference_In_Seconds;
                    if (difference_In_Hours / 10 != 0 && difference_In_Minutes / 10 != 0 && difference_In_Seconds / 10 != 0)
                        tt = difference_In_Hours + ":" + difference_In_Minutes + ":" + difference_In_Seconds;
                    timeLeft.setText(tt);
                }

                @Override
                public void onFinish() {
                    if (placed) {
                        timeLeft.setVisibility(View.INVISIBLE);
                        nextGame.setVisibility(View.INVISIBLE);
                        play.setVisibility(View.VISIBLE);
                        play.setClickable(true);
                    } else {
                        progressDialog.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setTimeLeft();

                            }
                        }, 8000);

                    }

                }
            }.start();
        }
    }

    public void setDefault() {
        nextGame = (LinearLayout) findViewById(R.id.linear_profile);
        dp = (CircleImageView) findViewById(R.id.hm_profilePic);
        timeLeft = (TextView) findViewById(R.id.p_time_left);
        play = (Button) findViewById(R.id.p_play);
        nextGameText = (TextView) findViewById(R.id.p_next_game);
    }

    private void pickGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "select image"), IMAGE_PICK_GALLERY_CODE);
    }

    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this, storagePermission, STORAGE_REQUEST_CODE);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case STORAGE_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean writeStorageAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    if (writeStorageAccepted) {
                        //pickGallery();
                    } else {
                        Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_PICK_GALLERY_CODE) {
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(this);
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                dp.setImageURI(resultUri);

                BitmapDrawable bitmapDrawable = (BitmapDrawable) dp.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();

                saveImage(bitmap);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void saveImage(Bitmap path) {

        String p = Environment.getExternalStorageDirectory() + "/" + "SpinWheel" + "/";
        File dir = new File(p);
        dir.mkdir();
        File file = new File(dir, "profile_pic" + ".jpg");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            path.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            Toast.makeText(getApplicationContext(), "File saved ", Toast.LENGTH_SHORT).show();
            fos.flush();
            fos.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public void logout(View view) {
        SharedPreferences sharedpreferences = getSharedPreferences("preference", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean("logged", false);
        editor.commit();

        Intent intent = new Intent(profile.this, new_login.class);
        startActivity(intent);
        finish();
    }

    public void buy(View view) {
        if(number.equals("0")){
            number = String.valueOf(values[0]);
        }
        Intent intent = new Intent(profile.this, buyTicket.class);
        intent.putExtra("number", number);
        startActivity(intent);
    }

    public void one(View view) {
        no1.setBackgroundResource(R.drawable.selected);
        no2.setBackgroundResource(R.drawable.rounded_button);
        no3.setBackgroundResource(R.drawable.rounded_button);
        number = String.valueOf(values[0]);
        // no1.setBackgroundColor(R.drawable.selected);
        //  no1.getResources().getDrawable(R.drawable.selected);
        // no2.getResources().getDrawable(R.drawable.rounded_button);

        // no3.getResources().getDrawable(R.drawable.rounded_button);

        //   no2.setBackgroundColor(R.drawable.rounded_button);
        // no3.setBackgroundColor(R.drawable.rounded_button);

    }

    public void two(View view) {
        // no2.setBackgroundResource(R.color.green);
        //  no1.setBackgroundResource(R.color.yellow);
        //no3.setBackgroundResource(R.color.yellow);
        //no1.setBackgroundColor(ContextCompat.getColor(this,R.color.green));
        no2.setBackgroundResource(R.drawable.selected);
        no1.setBackgroundResource(R.drawable.rounded_button);
        no3.setBackgroundResource(R.drawable.rounded_button);
        number = String.valueOf(values[1]);

        //  no2.setBackground(ContextCompat.getDrawable(this, R.drawable.selected));
        //no1.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_button));
        // no3.setBackground(ContextCompat.getDrawable(profile.this, R.drawable.rounded_button));

        //   no2.getResources().getDrawable(R.drawable.selected);
        // no1.getResources().getDrawable(R.drawable.rounded_button);

        //  no3.getResources().getDrawable(R.drawable.rounded_button);
    }

    public void three(View view) {

        no3.setBackgroundResource(R.drawable.selected);
        no1.setBackgroundResource(R.drawable.rounded_button);
        no2.setBackgroundResource(R.drawable.rounded_button);
        number = String.valueOf(values[2]);
        // no3.getResources().getDrawable(R.drawable.selected);
        //  no2.getResources().getDrawable(R.drawable.rounded_button);

        //  no1.getResources().getDrawable(R.drawable.rounded_button);

    }
}

