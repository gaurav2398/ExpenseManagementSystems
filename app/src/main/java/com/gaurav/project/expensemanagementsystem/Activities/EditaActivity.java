package com.gaurav.project.expensemanagementsystem.Activities;

import android.content.Intent;
import android.database.Cursor;
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

public class EditaActivity extends AppCompatActivity {

    TextView txtdate;
    EditText edtincome,edtid,edtname;
    ImageView back;
    Button update,delete;

    DatabaseHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edita);

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
                        Toast.makeText(EditaActivity.this, "Cannot be too large", Toast.LENGTH_SHORT).show();
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
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deletedRows = mydb.deleteData(edtid.getText().toString());
                if(deletedRows > 0)
                {
                    Intent i = new Intent(EditaActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                    Toast.makeText(EditaActivity.this, "Data deleted", Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(EditaActivity.this, "Enter valid id to delete data", Toast.LENGTH_SHORT).show();

                }
            }
        });

        edtname = findViewById(R.id.edtname);
        edtname.setText("");
        edtname.setHint("Enter valid id");
        edtid = findViewById(R.id.edtid);
        edtid.requestFocus();
        String s = edtid.getText().toString();
        if (s.length()==0){
            edtname.setText("");
            edtname.setHint("Expense Category");
        }
        edtid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{

                    Cursor mCur = mydb.getData(edtid.getText().toString());
                    String name = mCur.getString(mCur.getColumnIndex("NAME"));
                    if (mCur != null && mCur.getCount()>0) {
                        mCur.moveToFirst();
                        if (name.length()>0)
                        {
                            edtname.setText(name);
                        }

                    }else{
                        edtname.setHint("Category Not Found");
                        edtname.setText("");
                    }
                }catch(Exception e){
                    edtname.setText("");
                    edtname.setHint("Category Not Found");
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()==0){
                    edtname.setText("");
                    edtname.setHint("Expense Category");
                }

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate = mydb.updateData(edtid.getText().toString(),edtname.getText().toString(),edtincome.getText().toString());
                if (edtid.length()==0)
                {
                    Toast.makeText(EditaActivity.this, "Enter valid ID", Toast.LENGTH_SHORT).show();
                }
                else if (edtincome.length()==0)
                {
                    Toast.makeText(EditaActivity.this, "Enter Amount", Toast.LENGTH_SHORT).show();
                }
                else if (edtincome.getText().toString().equals("0")||edtincome.getText().toString().equals("00")||edtincome.getText().toString().equals("000")||edtincome.getText().toString().equals("0000")||edtincome.getText().toString().equals("00000")||edtincome.getText().toString().equals("000000")||edtincome.getText().toString().equals("0000000")||edtincome.getText().toString().equals("00000000")||edtincome.getText().toString().equals("000000000")||edtincome.getText().toString().equals("000000000")||edtincome.getText().toString().equals("0000000000"))
                {
                    Toast.makeText(EditaActivity.this, "Amount should be greater than 0", Toast.LENGTH_SHORT).show();
                    edtincome.requestFocus();
                }

                else {

                if (isUpdate == true)
                {
                    Toast.makeText(EditaActivity.this, "Data updated", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(EditaActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else{
                    Toast.makeText(EditaActivity.this, "Data not updated", Toast.LENGTH_SHORT).show();
                }
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



