package com.gaurav.project.expensemanagementsystem.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gaurav.project.expensemanagementsystem.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NewIncomeAcitivty extends AppCompatActivity {
    TextView txtdate;
    EditText edtincome,edtid;
    ImageView back;
    Button submit;
    DatabaseHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_income_acitivty);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#3f8342"));
        }

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView adView = new AdView(this);                   //test add
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
        AdView mAdView = findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        AdView adView1 = new AdView(this);                      //real add
        adView1.setAdSize(AdSize.BANNER);
        adView1.setAdUnitId("ca-app-pub-4250344724353850/9635091257");
        AdView mAdView1 = findViewById(R.id.adView);
        AdRequest adRequest1 = new AdRequest.Builder().build();
        mAdView1.loadAd(adRequest1);

        mydb = new DatabaseHelper(this);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy");

        final String currentDate = sdf.format(new Date());

        txtdate = findViewById(R.id.txtdate);
        txtdate.setText(currentDate);


        edtincome = findViewById(R.id.edtincome);
        edtincome.requestFocus();
        edtincome.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(12,2)});

        edtincome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{

                    if (edtincome.length()==12) {
                        Toast.makeText(NewIncomeAcitivty.this, "Cannot be too large", Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    //    Toast.makeText(EditaActivity.this, "Invalid Id", Toast.LENGTH_SHORT).show();

                    edtincome.setHint("Cannot be too large");
                    e.printStackTrace();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtid = findViewById(R.id.edtid);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
               onBackPressed();
            }
        });

        submit = findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtincome.length()==0 )
                {
                    Toast.makeText(NewIncomeAcitivty.this, "Enter Valid Amount", Toast.LENGTH_SHORT).show();
                    edtincome.requestFocus();
                }
                else if (edtincome.getText().toString().equals("0")||edtincome.getText().toString().equals("00")||edtincome.getText().toString().equals("000")||edtincome.getText().toString().equals("0000")||edtincome.getText().toString().equals("00000")||edtincome.getText().toString().equals("000000")||edtincome.getText().toString().equals("0000000")||edtincome.getText().toString().equals("00000000")||edtincome.getText().toString().equals("000000000")||edtincome.getText().toString().equals("000000000")||edtincome.getText().toString().equals("0000000000"))
                {
                    Toast.makeText(NewIncomeAcitivty.this, "Amount should be greater than 0", Toast.LENGTH_SHORT).show();
                    edtincome.requestFocus();
                }

                else
                {
                    boolean isInserted = mydb.inertData("INCOME",edtincome.getText().toString(),currentDate);

                    if (isInserted == true)
                    {
                        Toast.makeText(NewIncomeAcitivty.this, "Data inserted", Toast.LENGTH_SHORT).show();
                    }
                    else if (edtincome.getText().toString().equals("0")||edtincome.getText().toString().equals("00")||edtincome.getText().toString().equals("000")||edtincome.getText().toString().equals("0000")||edtincome.getText().toString().equals("00000")||edtincome.getText().toString().equals("000000")||edtincome.getText().toString().equals("0000000")||edtincome.getText().toString().equals("00000000")||edtincome.getText().toString().equals("000000000")||edtincome.getText().toString().equals("000000000")||edtincome.getText().toString().equals("0000000000"))
                    {
                        Toast.makeText(NewIncomeAcitivty.this, "Amount should be greater than 0", Toast.LENGTH_SHORT).show();
                        edtincome.requestFocus();
                    }

                    else
                    {
                        Toast.makeText(NewIncomeAcitivty.this, "Data not inserted", Toast.LENGTH_SHORT).show();
                    }

                    Intent i = new Intent(NewIncomeAcitivty.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        });

        }
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

}

