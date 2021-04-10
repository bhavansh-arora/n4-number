package com.sugarbaby.luckywheel;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class buyTicket extends AppCompatActivity {

    private Button add0, minus0, add1, minus1, add2, minus2, add3, minus3, add4, minus4, add5, minus5, add6, minus6, add7, minus7, add8, minus8, add9, minus9;
    private TextView total, sum0, sum1, sum2, sum3, sum4, sum5, sum6, sum7, sum8, sum9;
    private int x0 = 0, x1 = 0, x2 = 0, x3 = 0, x4 = 0, x5 = 0, x6 = 0, x7 = 0, x8 = 0, x9 = 0, in0 = 0, in1 = 0, in2 = 0, in3 = 0, in4 = 0,
            in5 = 0, in6 = 0, in7 = 0, in8 = 0, in9 = 0, mi0 = 0;
    private RadioButton zero, one, two, three, four, five, six, seven, eight, nine;
    List<Double> sum;
    ProgressDialog progressDialog, progressDialog1;
    TextView time, bal;
    Integer balance;
    List<Double> values = new ArrayList<>();
    List<Integer> amount = new ArrayList<>();
    List<Integer> val = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    int a;
    String phone;
    int flag = 0, flag1 = 0;
    int number;
    String num;
long game_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_ticket);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait...");
        progressDialog.setMessage("Updating Credentials....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
        progressDialog1 = new ProgressDialog(this);
        progressDialog1.setTitle("Please Wait...");
        progressDialog1.setMessage("Updating Credentials....");
        progressDialog1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog1.setIndeterminate(true);
        progressDialog1.setCanceledOnTouchOutside(false);
        progressDialog1.setCancelable(false);
        progressDialog1.hide();
        SharedPreferences sharedpreferences = getSharedPreferences("preference", MODE_PRIVATE);
        String timing = sharedpreferences.getString("time", "00:00:00");
        balance = sharedpreferences.getInt("balance", 0);
        phone = sharedpreferences.getString("phone", "");
game_id = sharedpreferences.getLong("game_id",0);
        Intent i = getIntent();
        num = i.getStringExtra("number");
        number = Integer.parseInt(num);

        time = findViewById(R.id.time);
        bal = findViewById(R.id.balance);
        bal.setText("Total Balance = " + balance);
        time.setText(timing);
        db.collection("currentgame")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Toast.makeText(getApplicationContext(), "OOPS! Some error occured.", Toast.LENGTH_LONG).show();
                            return;
                        }

                        sum = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : value) {
                            if (doc.get("value") != null) {
                                values.add(doc.getDouble("value"));
                                val.add(doc.getDouble("value").intValue());
                                // System.out.println(doc.getDouble("value").intValue());
                                progressDialog.hide();
                            }
                        }
                        Collections.sort(values);
                        Collections.sort(val);

                        System.out.println(val);
                        sum.add(values.get(0));
                        for (int i = 0; i < values.size() - 1; i++) {
                            sum.add(values.get(i + 1) - values.get(i));
                        }
                        System.out.println(sum);
                    }
                });


        setDefault();
        setsum();
    }

    public void setDefault() {
        add0 = (Button) findViewById(R.id.add_cash_0);
        minus0 = (Button) findViewById(R.id.subtract_cash_0);
        sum0 = (TextView) findViewById(R.id.sum_0);
        add1 = (Button) findViewById(R.id.add_cash_1);
        minus1 = (Button) findViewById(R.id.subtract_cash_1);
        sum1 = (TextView) findViewById(R.id.sum_1);
        add2 = (Button) findViewById(R.id.add_cash_2);
        minus2 = (Button) findViewById(R.id.subtract_cash_2);
        sum2 = (TextView) findViewById(R.id.sum_2);
        add3 = (Button) findViewById(R.id.add_cash_3);
        minus3 = (Button) findViewById(R.id.subtract_cash_3);
        sum3 = (TextView) findViewById(R.id.sum_3);
        add4 = (Button) findViewById(R.id.add_cash_4);
        minus4 = (Button) findViewById(R.id.subtract_cash_4);
        sum4 = (TextView) findViewById(R.id.sum_4);
        add5 = (Button) findViewById(R.id.add_cash_5);
        minus5 = (Button) findViewById(R.id.subtract_cash_5);
        sum5 = (TextView) findViewById(R.id.sum_5);
        add6 = (Button) findViewById(R.id.add_cash_6);
        minus6 = (Button) findViewById(R.id.subtract_cash_6);
        sum6 = (TextView) findViewById(R.id.sum_6);
        add7 = (Button) findViewById(R.id.add_cash_7);
        minus7 = (Button) findViewById(R.id.subtract_cash_7);
        sum7 = (TextView) findViewById(R.id.sum_7);
        add8 = (Button) findViewById(R.id.add_cash_8);
        minus8 = (Button) findViewById(R.id.subtract_cash_8);
        sum8 = (TextView) findViewById(R.id.sum_8);
        add9 = (Button) findViewById(R.id.add_cash_9);
        minus9 = (Button) findViewById(R.id.subtract_cash_9);
        sum9 = (TextView) findViewById(R.id.sum_9);
        total = (TextView) findViewById(R.id.total_sum);
        zero = (RadioButton) findViewById(R.id.number_zero);
        one = (RadioButton) findViewById(R.id.number_one);
        two = (RadioButton) findViewById(R.id.number_two);
        three = (RadioButton) findViewById(R.id.number_three);
        four = (RadioButton) findViewById(R.id.number_four);
        five = (RadioButton) findViewById(R.id.number_five);
        six = (RadioButton) findViewById(R.id.number_six);
        seven = (RadioButton) findViewById(R.id.number_seven);
        eight = (RadioButton) findViewById(R.id.number_eight);
        nine = (RadioButton) findViewById(R.id.number_nine);
        //minus0.setEnabled(false);
        //minus1.setEnabled(false);
        //minus2.setEnabled(false);
        //minus3.setEnabled(false);
        //minus4.setEnabled(false);
        //minus5.setEnabled(false);
        //minus6.setEnabled(false);
        //minus7.setEnabled(false);
        //minus8.setEnabled(false);
        //minus9.setEnabled(false);


    }

    /*
    public void setsum() {
        add0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minus0.setEnabled(true);
                if (in0 < sum.size()) {

                    double val0 = sum.get(in0);

                    x0 = x0 + Integer.valueOf((int) val0);
                    if ((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) > balance) {
                        Toast.makeText(getApplicationContext(), "Balance Low", Toast.LENGTH_LONG).show();
                        x0 = x0 - Integer.valueOf((int) val0);

                    } else {
                        bal.setText("Total Balance = " + ((balance) - (x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9)));
                        total.setText("Total amount = " + String.valueOf(x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9));

                        sum0.setText(String.valueOf(x0));
                        in0 = in0 + 1;
                        if (in0 >= sum.size()) {
                            add0.setEnabled(false);
                        }
                    }

                }


            }
        });
        minus0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (in0 > 0) {
                    in0 = in0 - 1;

                    double val0 = sum.get(in0);


                    x0 = x0 - Integer.valueOf((int) val0);
                    //balance = balance + x0;
                    sum0.setText(String.valueOf(x0));
                    //  balance = balance + (x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9);
                    bal.setText("Total Balance = " + (balance - (x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9)));

                    total.setText("Total amount = " + String.valueOf(x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9));
                    if (in0 == 0) {
                        minus0.setEnabled(false);
                    }
                    if (in0 < sum.size()) {
                        add0.setEnabled(true);
                    }
                }

            }
        });
        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minus1.setEnabled(true);
                if (in1 < sum.size()) {

                    double val1 = sum.get(in1);

                    x1 = x1 + Integer.valueOf((int) val1);
                    if ((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) > balance) {
                        Toast.makeText(getApplicationContext(), "Balance Low", Toast.LENGTH_LONG).show();
                        x1 = x1 - Integer.valueOf((int) val1);
                    } else {
                        sum1.setText(String.valueOf(x1));

                        total.setText("Total amount = " + String.valueOf(x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9));
                        bal.setText("Total Balance = " + ((balance) - (x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9)));

                        in1 = in1 + 1;
                        if (in1 >= sum.size()) {
                            add1.setEnabled(false);
                        }
                    }

                }


            }
        });
        minus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (in1 > 0) {
                    in1 = in1 - 1;

                    double val1 = sum.get(in1);


                    x1 = x1 - Integer.valueOf((int) val1);
                    sum1.setText(String.valueOf(x1));
                    bal.setText("Total Balance = " + (balance - (x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9)));

                    total.setText("Total amount = " + String.valueOf(x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9));
                    if (in1 == 0) {
                        minus1.setEnabled(false);
                    }
                    if (in1 < sum.size()) {
                        add1.setEnabled(true);
                    }
                }
            }
        });
        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minus2.setEnabled(true);
                if (in2 < sum.size()) {

                    double val2 = sum.get(in2);

                    x2 = x2 + Integer.valueOf((int) val2);

                    if ((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) > balance) {
                        Toast.makeText(getApplicationContext(), "Balance Low", Toast.LENGTH_LONG).show();
                        x2 = x2 - Integer.valueOf((int) val2);

                    } else {
                        sum2.setText(String.valueOf(x2));
                        bal.setText("Total Balance = " + ((balance) - (x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9)));

                        total.setText("Total amount = " + String.valueOf(x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9));
                        in2 = in2 + 1;
                        if (in2 >= sum.size()) {
                            add2.setEnabled(false);
                        }
                    }

                }


            }
        });
        minus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (in2 > 0) {
                    in2 = in2 - 1;

                    double val2 = sum.get(in2);


                    x2 = x2 - Integer.valueOf((int) val2);
                    sum2.setText(String.valueOf(x2));
                    bal.setText("Total Balance = " + (balance - (x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9)));

                    total.setText("Total amount = " + String.valueOf(x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9));
                    if (in2 == 0) {
                        minus2.setEnabled(false);
                    }
                    if (in2 < sum.size()) {
                        add2.setEnabled(true);
                    }
                }
            }
        });
        add3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minus3.setEnabled(true);
                if (in3 < sum.size()) {

                    double val3 = sum.get(in3);

                    x3 = x3 + Integer.valueOf((int) val3);
                    if ((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) > balance) {
                        Toast.makeText(getApplicationContext(), "Balance Low", Toast.LENGTH_LONG).show();
                        x3 = x3 - Integer.valueOf((int) val3);

                    } else {
                        sum3.setText(String.valueOf(x3));
                        bal.setText("Total Balance = " + ((balance) - (x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9)));
                        total.setText("Total amount = " + String.valueOf(x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9));
                        in3 = in3 + 1;
                        if (in3 >= sum.size()) {
                            add3.setEnabled(false);
                        }
                    }

                }
            }
        });
        minus3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (in3 > 0) {
                    in3 = in3 - 1;

                    double val3 = sum.get(in3);


                    x3 = x3 - Integer.valueOf((int) val3);
                    sum3.setText(String.valueOf(x3));
                    bal.setText("Total Balance = " + (balance - (x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9)));

                    total.setText("Total amount = " + String.valueOf(x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9));
                    if (in3 == 0) {
                        minus3.setEnabled(false);
                    }
                    if (in3 < sum.size()) {
                        add3.setEnabled(true);
                    }
                }
            }
        });
        add4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minus4.setEnabled(true);
                if (in0 < sum.size()) {

                    double val4 = sum.get(in4);

                    x4 = x4 + Integer.valueOf((int) val4);
                    if ((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) > balance) {
                        Toast.makeText(getApplicationContext(), "Balance Low", Toast.LENGTH_LONG).show();
                        x4 = x4 - Integer.valueOf((int) val4);

                    } else {
                        bal.setText("Total Balance = " + ((balance) - (x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9)));

                        sum4.setText(String.valueOf(x4));
                        total.setText("Total amount = " + String.valueOf(x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9));
                        in4 = in4 + 1;
                        if (in4 >= sum.size()) {
                            add4.setEnabled(false);
                        }
                    }

                }
            }
        });
        minus4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (in4 > 0) {
                    in4 = in4 - 1;

                    double val4 = sum.get(in4);


                    x4 = x4 - Integer.valueOf((int) val4);
                    sum4.setText(String.valueOf(x4));
                    bal.setText("Total Balance = " + (balance - (x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9)));

                    total.setText("Total amount = " + String.valueOf(x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9));
                    if (in4 == 0) {
                        minus4.setEnabled(false);
                    }
                    if (in4 < sum.size()) {
                        add4.setEnabled(true);
                    }
                }
            }
        });
        add5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minus5.setEnabled(true);
                if (in5 < sum.size()) {

                    double val5 = sum.get(in5);

                    x5 = x5 + Integer.valueOf((int) val5);
                    if ((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) > balance) {
                        Toast.makeText(getApplicationContext(), "Balance Low", Toast.LENGTH_LONG).show();
                        x5 = x5 - Integer.valueOf((int) val5);

                    } else {
                        bal.setText("Total Balance = " + ((balance) - (x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9)));

                        sum5.setText(String.valueOf(x5));
                        total.setText("Total amount = " + String.valueOf(x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9));
                        in5 = in5 + 1;
                        if (in5 >= sum.size()) {
                            add5.setEnabled(false);
                        }
                    }

                }
            }
        });
        minus5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (in5 > 0) {
                    in5 = in5 - 1;

                    double val5 = sum.get(in5);


                    x5 = x5 - Integer.valueOf((int) val5);
                    sum5.setText(String.valueOf(x5));
                    bal.setText("Total Balance = " + (balance - (x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9)));

                    total.setText("Total amount = " + String.valueOf(x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9));
                    if (in5 == 0) {
                        minus5.setEnabled(false);
                    }
                    if (in5 < sum.size()) {
                        add5.setEnabled(true);
                    }
                }
            }
        });
        add6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minus6.setEnabled(true);
                if (in6 < sum.size()) {

                    double val6 = sum.get(in6);

                    x6 = x6 + Integer.valueOf((int) val6);
                    if ((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) > balance) {
                        Toast.makeText(getApplicationContext(), "Balance Low", Toast.LENGTH_LONG).show();
                        x6 = x6 - Integer.valueOf((int) val6);

                    } else {
                        bal.setText("Total Balance = " + ((balance) - (x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9)));

                        sum6.setText(String.valueOf(x6));
                        total.setText("Total amount = " + String.valueOf(x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9));
                        in6 = in6 + 1;
                        if (in6 >= sum.size()) {
                            add6.setEnabled(false);
                        }
                    }

                }
            }
        });
        minus6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (in6 > 0) {
                    in6 = in6 - 1;

                    double val6 = sum.get(in6);


                    x6 = x6 - Integer.valueOf((int) val6);
                    sum6.setText(String.valueOf(x6));
                    bal.setText("Total Balance = " + (balance - (x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9)));

                    total.setText("Total amount = " + String.valueOf(x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9));
                    if (in6 == 0) {
                        minus6.setEnabled(false);
                    }
                    if (in6 < sum.size()) {
                        add6.setEnabled(true);
                    }
                }
            }
        });
        add7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minus7.setEnabled(true);
                if (in7 < sum.size()) {

                    double val7 = sum.get(in7);

                    x7 = x7 + Integer.valueOf((int) val7);
                    if ((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) > balance) {
                        Toast.makeText(getApplicationContext(), "Balance Low", Toast.LENGTH_LONG).show();
                        x7 = x7 - Integer.valueOf((int) val7);

                    } else {
                        bal.setText("Total Balance = " + ((balance) - (x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9)));

                        sum7.setText(String.valueOf(x7));
                        total.setText("Total amount = " + String.valueOf(x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9));
                        in7 = in7 + 1;
                        if (in7 >= sum.size()) {
                            add7.setEnabled(false);
                        }
                    }

                }
            }
        });
        minus7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (in7 > 0) {
                    in7 = in7 - 1;

                    double val7 = sum.get(in7);


                    x7 = x7 - Integer.valueOf((int) val7);
                    sum7.setText(String.valueOf(x7));
                    bal.setText("Total Balance = " + (balance - (x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9)));

                    total.setText("Total amount = " + String.valueOf(x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9));
                    if (in7 == 0) {
                        minus7.setEnabled(false);
                    }
                    if (in7 < sum.size()) {
                        add7.setEnabled(true);
                    }
                }
            }
        });
        add8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minus8.setEnabled(true);
                if (in8 < sum.size()) {

                    double val8 = sum.get(in8);

                    x8 = x8 + Integer.valueOf((int) val8);
                    if ((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) > balance) {
                        Toast.makeText(getApplicationContext(), "Balance Low", Toast.LENGTH_LONG).show();
                        x8 = x8 - Integer.valueOf((int) val8);

                    } else {
                        sum8.setText(String.valueOf(x8));
                        bal.setText("Total Balance = " + ((balance) - (x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9)));

                        total.setText("Total amount = " + String.valueOf(x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9));
                        in8 = in8 + 1;
                        if (in8 >= sum.size()) {
                            add8.setEnabled(false);
                        }
                    }

                }
            }
        });
        minus8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (in8 > 0) {
                    in8 = in8 - 1;

                    double val8 = sum.get(in8);


                    x8 = x8 - Integer.valueOf((int) val8);
                    sum8.setText(String.valueOf(x8));
                    bal.setText("Total Balance = " + (balance - (x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9)));

                    total.setText("Total amount = " + String.valueOf(x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9));
                    if (in8 == 0) {
                        minus8.setEnabled(false);
                    }
                    if (in8 < sum.size()) {
                        add8.setEnabled(true);
                    }
                }
            }
        });
        add9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minus9.setEnabled(true);
                if (in9 < sum.size()) {

                    double val9 = sum.get(in9);

                    x9 = x9 + Integer.valueOf((int) val9);
                    if ((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) > balance) {
                        Toast.makeText(getApplicationContext(), "Balance Low", Toast.LENGTH_LONG).show();
                        x9 = x9 - Integer.valueOf((int) val9);

                    } else {
                        bal.setText("Total Balance = " + ((balance) - (x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9)));

                        sum9.setText(String.valueOf(x9));
                        total.setText("Total amount = " + String.valueOf(x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9));
                        in9 = in9 + 1;
                        if (in9 >= sum.size()) {
                            add9.setEnabled(false);
                        }
                    }


                }
            }
        });
        minus9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (in9 > 0) {
                    in9 = in9 - 1;

                    double val9 = sum.get(in9);


                    x9 = x9 - Integer.valueOf((int) val9);
                    sum9.setText(String.valueOf(x9));
                    bal.setText("Total Balance = " + (balance - (x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9)));

                    total.setText("Total amount = " + String.valueOf(x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9));
                    if (in9 == 0) {
                        minus9.setEnabled(false);
                    }
                    if (in9 < sum.size()) {
                        add9.setEnabled(true);
                    }
                }
            }
        });
    }
    */

    public void setsum() {
        add0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((balance-(x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) * number) < number) {
                    Toast.makeText(getApplicationContext(), "Balance Low", Toast.LENGTH_LONG).show();
                } else {
                    x0=x0+1;
                    int b=balance-((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) * number);
                    bal.setText("Total Balance = " + b);
                    total.setText("Total amount = " + String.valueOf((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) * number));
                    sum0.setText(String.valueOf(x0));

                }
            }
        });
        minus0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (x0!=0) {
                    x0=x0-1;
                    int b=balance-((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) * number);
                    bal.setText("Total Balance = " + b);
                    total.setText("Total amount = " + String.valueOf((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9)*number));
                    sum0.setText(String.valueOf(x0));

                }
            }
        });

        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((balance-(x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) * number) < number) {
                    Toast.makeText(getApplicationContext(), "Balance Low", Toast.LENGTH_LONG).show();
                } else {
                    x1=x1+1;
                    int b=balance-((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) * number);
                    bal.setText("Total Balance = " + b);
                    total.setText("Total amount = " + String.valueOf((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) * number));
                    sum1.setText(String.valueOf(x1));

                }
            }
        });
        minus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (x1!=0) {
                    x1=x1-1;
                    int b=balance-((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) * number);
                    bal.setText("Total Balance = " + b);
                    total.setText("Total amount = " + String.valueOf((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9)*number));
                    sum1.setText(String.valueOf(x1));

                }
            }
        });

        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((balance-(x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) * number) < number) {
                    Toast.makeText(getApplicationContext(), "Balance Low", Toast.LENGTH_LONG).show();
                } else {
                    x2=x2+1;
                    int b=balance-((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) * number);
                    bal.setText("Total Balance = " + b);
                    total.setText("Total amount = " + String.valueOf((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) * number));
                    sum2.setText(String.valueOf(x2));

                }
            }
        });
        minus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (x2!=0) {
                    x2=x2-1;
                    int b=balance-((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) * number);
                    bal.setText("Total Balance = " + b);
                    total.setText("Total amount = " + String.valueOf((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9)*number));
                    sum2.setText(String.valueOf(x2));

                }
            }
        });

        add3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((balance-(x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) * number) < number) {
                    Toast.makeText(getApplicationContext(), "Balance Low", Toast.LENGTH_LONG).show();
                } else {
                    x3=x3+1;
                    int b=balance-((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) * number);
                    bal.setText("Total Balance = " + b);
                    total.setText("Total amount = " + String.valueOf((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) * number));
                    sum3.setText(String.valueOf(x3));

                }
            }
        });
        minus3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (x3!=0) {
                    x3=x3-1;
                    int b=balance-((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) * number);
                    bal.setText("Total Balance = " + b);
                    total.setText("Total amount = " + String.valueOf((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9)*number));
                    sum3.setText(String.valueOf(x3));

                }
            }
        });

        add4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((balance-(x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) * number) < number) {
                    Toast.makeText(getApplicationContext(), "Balance Low", Toast.LENGTH_LONG).show();
                } else {
                    x4=x4+1;
                    int b=balance-((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) * number);
                    bal.setText("Total Balance = " + b);
                    total.setText("Total amount = " + String.valueOf((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) * number));
                    sum4.setText(String.valueOf(x4));

                }
            }
        });
        minus4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (x4!=0) {
                    x4=x4-1;
                    int b=balance-((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) * number);
                    bal.setText("Total Balance = " + b);
                    total.setText("Total amount = " + String.valueOf((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9)*number));
                    sum4.setText(String.valueOf(x4));

                }
            }
        });

        add5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((balance-(x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) * number) < number) {
                    Toast.makeText(getApplicationContext(), "Balance Low", Toast.LENGTH_LONG).show();
                } else {
                    x5=x5+1;
                    int b=balance-((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) * number);
                    bal.setText("Total Balance = " + b);
                    total.setText("Total amount = " + String.valueOf((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) * number));
                    sum5.setText(String.valueOf(x5));

                }
            }
        });
        minus5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (x5!=0) {
                    x5=x5-1;
                    int b=balance-((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) * number);
                    bal.setText("Total Balance = " + b);
                    total.setText("Total amount = " + String.valueOf((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9)*number));
                    sum5.setText(String.valueOf(x5));

                }
            }
        });

        add6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((balance-(x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) * number) < number) {
                    Toast.makeText(getApplicationContext(), "Balance Low", Toast.LENGTH_LONG).show();
                } else {
                    x6=x6+1;
                    int b=balance-((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) * number);
                    bal.setText("Total Balance = " + b);
                    total.setText("Total amount = " + String.valueOf((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) * number));
                    sum6.setText(String.valueOf(x6));

                }
            }
        });
        minus6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (x6!=0) {
                    x6=x6-1;
                    int b=balance-((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) * number);
                    bal.setText("Total Balance = " + b);
                    total.setText("Total amount = " + String.valueOf((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9)*number));
                    sum6.setText(String.valueOf(x6));

                }
            }
        });

        add7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((balance-(x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) * number) < number) {
                    Toast.makeText(getApplicationContext(), "Balance Low", Toast.LENGTH_LONG).show();
                } else {
                    x7=x7+1;
                    int b=balance-((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) * number);
                    bal.setText("Total Balance = " + b);
                    total.setText("Total amount = " + String.valueOf((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) * number));
                    sum7.setText(String.valueOf(x7));

                }
            }
        });
        minus7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (x7!=0) {
                    x7=x7-1;
                    int b=balance-((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) * number);
                    bal.setText("Total Balance = " + b);
                    total.setText("Total amount = " + String.valueOf((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9)*number));
                    sum7.setText(String.valueOf(x7));

                }
            }
        });

        add8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((balance-(x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) * number) < number) {
                    Toast.makeText(getApplicationContext(), "Balance Low", Toast.LENGTH_LONG).show();
                } else {
                    x8=x8+1;
                    int b=balance-((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) * number);
                    bal.setText("Total Balance = " + b);
                    total.setText("Total amount = " + String.valueOf((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) * number));
                    sum8.setText(String.valueOf(x8));

                }
            }
        });
        minus8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (x8!=0) {
                    x8=x8-1;
                    int b=balance-((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) * number);
                    bal.setText("Total Balance = " + b);
                    total.setText("Total amount = " + String.valueOf((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9)*number));
                    sum8.setText(String.valueOf(x8));

                }
            }
        });

        add9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((balance-(x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) * number) < number) {
                    Toast.makeText(getApplicationContext(), "Balance Low", Toast.LENGTH_LONG).show();
                } else {
                    x9=x9+1;
                    int b=balance-((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) * number);
                    bal.setText("Total Balance = " + b);
                    total.setText("Total amount = " + String.valueOf((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) * number));
                    sum9.setText(String.valueOf(x9));

                }
            }
        });
        minus9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (x9!=0) {
                    x9=x9-1;
                    int b=balance-((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) * number);
                    bal.setText("Total Balance = " + b);
                    total.setText("Total amount = " + String.valueOf((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9)*number));
                    sum9.setText(String.valueOf(x9));

                }
            }
        });
    }

    public void confirm(View view) {
        if ((x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9) > 0) {
            progressDialog1.show();
            for (int i = 0; i < values.size(); i++) {
                int j = 0;
                if (x0 == values.get(i)) {
                    j = j + x0;
                    System.out.println("x"+x0);
                    System.out.println("j"+j);

                }
                if (x1 == values.get(i)) {
                    j = j + x1;
                }
                if (x2 == values.get(i)) {
                    j = j + x2;
                }
                if (x3 == values.get(i)) {
                    j = j + x3;
                }
                if (x4 == values.get(i)) {
                    j = j + x4;
                }
                if (x5 == values.get(i)) {
                    j = j + x5;
                }
                if (x6 == values.get(i)) {
                    j = j + x6;
                }
                if (x7 == values.get(i)) {
                    j = j + x7;
                }
                if (x8 == values.get(i)) {
                    j = j + x8;
                }
                if (x9 == values.get(i)) {
                    j = j + x9;
                }
                amount.add(j);

            }
            System.out.println(values.size());
                final int va = (x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9)*number;
                System.out.println("confirm lsitener");

System.out.println(num);
                db.collection("currentgame")
                        .whereEqualTo("value", number)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                System.out.println("complete listener");

                                if (task.isSuccessful()) {
                                    System.out.println("task successful");

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        System.out.println("querrying");

                                        int amt, users;
                                        String docid;
                                        amt = document.getDouble("amount").intValue();
                                        users = document.getDouble("users").intValue();
                                        docid = document.getString("docid");
                                        System.out.println(amt);
                                        System.out.println(users);
                                        System.out.println(docid);
                                        Map<String, Object> data = new HashMap<>();
                                        if (va == 0) {
                                            data.put("amount", amt);
                                            data.put("users", users);
                                        } else {
                                            int am = amt + va;
                                            data.put("amount", am);
                                            data.put("users", users + 1);
                                        }


                                        db.collection("currentgame").document(docid)
                                                .set(data, SetOptions.merge())
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        bets_updater();
System.out.println("complete");

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
                                } else {
                                    Toast.makeText(getApplicationContext(), "OOPS! Some error occured. Restarting the app may help.", Toast.LENGTH_LONG).show();
                                    progressDialog1.dismiss();
                                }
                            }
                        });



        } else {
            Toast.makeText(getApplicationContext(), "Please Choose a Valid Amount.", Toast.LENGTH_LONG).show();
        }

    }

    private void bets_updater() {
        System.out.println("updated");
        final List<Integer> numbers = new ArrayList<>();
        numbers.add(x0*number);
        numbers.add(x1*number);
        numbers.add(x2*number);
        numbers.add(x3*number);
        numbers.add(x4*number);
        numbers.add(x5*number);
        numbers.add(x6*number);
        numbers.add(x7*number);
        numbers.add(x8*number);
        numbers.add(x9*number);

        for (int k = 0; k <= 9; k++) {

            final int finalK = k;
            db.collection("bets")
                    .whereEqualTo("number", k)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                System.out.println("bets processing");
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    int amt = 0, users = 0;
                                    String docid;
                                    amt = document.getDouble("amount").intValue();
                                    users = document.getDouble("users").intValue();
                                    docid = document.getId();
                                    Map<String, Object> data = new HashMap<>();

                                    if (numbers.get(finalK) == 0) {
                                        data.put("amount", amt);
                                        data.put("users", users);
                                    } else {
                                        int am = amt + numbers.get(finalK);
                                        data.put("amount", am);
                                        data.put("users", users + 1);
                                    }


                                    db.collection("bets").document(docid)
                                            .set(data, SetOptions.merge())
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    flag1++;
                                                    if (flag1 == 10) {
                                                        user_updater();

                                                    }

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
                            } else {
                                Toast.makeText(getApplicationContext(), "OOPS! Some error occured. Restarting the app may help.", Toast.LENGTH_LONG).show();
                                progressDialog1.dismiss();
                            }
                        }
                    });
        }
    }

    private void user_updater() {
        final int updated_balance = balance - (x0 + x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9)*number;
        Map<String, Object> data = new HashMap<>();
        data.put("balance", updated_balance);
        data.put("bets_placed", true);
        data.put("zero", x0*number);
        data.put("one", x1*number);
        data.put("two", x2*number);
        data.put("three", x3*number);
        data.put("four", x4*number);
        data.put("five", x5*number);
        data.put("six", x6*number);
        data.put("seven", x7*number);
        data.put("eight", x8*number);
        data.put("nine", x9*number);
        data.put("game_id",game_id);

        db.collection("users").document(phone)
                .set(data, SetOptions.merge())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        SharedPreferences sharedpreferences = getSharedPreferences("preference", MODE_PRIVATE);

                        final SharedPreferences.Editor editor = sharedpreferences.edit();

                        editor.putBoolean("bets_placed", true);
                        editor.putInt("balance", updated_balance);
                        editor.putLong("unupdated_game_id",game_id);
                        editor.commit();


                        System.out.println("Completed");
                        Intent intent = new Intent(buyTicket.this, profile.class);
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
