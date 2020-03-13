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
import java.text.SimpleDateFormat;
import java.util.Date;

public class EntertainmentActivity extends AppCompatActivity {


    TextView txtdate;
    EditText edtincome,edtid;
    ImageView back;
    Button submit,update,delete;
    DatabaseHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entertainment);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#3f8342"));
        }


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
                        Toast.makeText(EntertainmentActivity.this, "Cannot be too large", Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){

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
                    Toast.makeText(EntertainmentActivity.this, "Enter Valid Amount", Toast.LENGTH_SHORT).show();
                    edtincome.requestFocus();
                }
                else if (edtincome.getText().toString().equals("0")||edtincome.getText().toString().equals("00")||edtincome.getText().toString().equals("000")||edtincome.getText().toString().equals("0000")||edtincome.getText().toString().equals("00000")||edtincome.getText().toString().equals("000000")||edtincome.getText().toString().equals("0000000")||edtincome.getText().toString().equals("00000000")||edtincome.getText().toString().equals("000000000")||edtincome.getText().toString().equals("000000000")||edtincome.getText().toString().equals("0000000000"))
                {
                    Toast.makeText(EntertainmentActivity.this, "Amount should be greater than 0", Toast.LENGTH_SHORT).show();
                    edtincome.requestFocus();
                }

                else
                {
                    boolean isInserted = mydb.inertData("ENTERTAINMENT",edtincome.getText().toString(),currentDate);

                    if (isInserted == true)
                    {
                        Toast.makeText(EntertainmentActivity.this, "Data inserted", Toast.LENGTH_SHORT).show();
                    }
                    else if (edtincome.getText().toString().equals("0")||edtincome.getText().toString().equals("00")||edtincome.getText().toString().equals("000")||edtincome.getText().toString().equals("0000")||edtincome.getText().toString().equals("00000")||edtincome.getText().toString().equals("000000")||edtincome.getText().toString().equals("0000000")||edtincome.getText().toString().equals("00000000")||edtincome.getText().toString().equals("000000000")||edtincome.getText().toString().equals("000000000")||edtincome.getText().toString().equals("0000000000"))
                    {
                        Toast.makeText(EntertainmentActivity.this, "Amount should be greater than 0", Toast.LENGTH_SHORT).show();
                        edtincome.requestFocus();
                    }

                    else
                    {
                        Toast.makeText(EntertainmentActivity.this, "Data not inserted", Toast.LENGTH_SHORT).show();
                    }

                    Intent i = new Intent(EntertainmentActivity.this, MainActivity.class);
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

