package com.gaurav.project.expensemanagementsystem.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.gaurav.project.expensemanagementsystem.Activities.Adapters.ExpenseAdapter;
import com.gaurav.project.expensemanagementsystem.Activities.Model.ExpenseModel;
import com.gaurav.project.expensemanagementsystem.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DeleteActivity extends AppCompatActivity implements ExpenseAdapter.OnItemClickListener{

    private RecyclerView mRecyclerView;
    ExpenseAdapter expenseAdapter;
    DatabaseHelper mydb;
    ImageView back;
    private List<ExpenseModel> list = new ArrayList<>();
    Spinner spn;
    ArrayList<String> mainname = new ArrayList<String>();
    ArrayList<String> mainid = new ArrayList<String>();
    String idmain;
    String id,name,amount,date;
    int positions;
    ImageView noexpense;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

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

        AdView adView1 = new AdView(this);                      //real add
        adView1.setAdSize(AdSize.BANNER);
        adView1.setAdUnitId("ca-app-pub-4250344724353850/9635091257");
        AdView mAdView1 = findViewById(R.id.adView);
        AdRequest adRequest1 = new AdRequest.Builder().build();
        mAdView1.loadAd(adRequest1);



        noexpense = findViewById(R.id.noexpense);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mydb = new DatabaseHelper(this);

        mRecyclerView = findViewById(R.id.expenserecycler);
        mRecyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        spn=findViewById(R.id.spn);
        mainname.add("Select Expenses");
        mainid.add("0");
        mainname.add("HOME");
        mainid.add("1");
        mainname.add("ENTERTAINMENT");
        mainid.add("2");
        mainname.add("TRAVELLING");
        mainid.add("3");
        mainname.add("CLOTH");
        mainid.add("4");
        mainname.add("SPORT");
        mainid.add("5");
        mainname.add("INCOME");
        mainid.add("6");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DeleteActivity.this, android.R.layout.simple_list_item_1, mainname);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spn.setAdapter(adapter);

        list.clear();

        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                list.clear();

                if(mainid.get(position).equals("0")){
                    positions=0;
                    list.clear();
                    expenseAdapter = new ExpenseAdapter(getApplicationContext(),list);
                    mRecyclerView.setAdapter(expenseAdapter);
                    idmain=mainid.get(position);
                    Log.d("cid",idmain);

                    if (list.isEmpty())
                    {
                        noexpense.setVisibility(View.VISIBLE);
                    }
                    else {
                        noexpense.setVisibility(View.GONE);
                    }
                }
                else  if (mainid.get(position).equals("1"))
                {
                    positions=1;
                    list.addAll(mydb.homedeletedata());
                    expenseAdapter = new ExpenseAdapter(getApplicationContext(),list);
                    mRecyclerView.setAdapter(expenseAdapter);
                    expenseAdapter.setOnItemClickListener(DeleteActivity.this);
                    if (list.isEmpty())
                    {
                        noexpense.setVisibility(View.VISIBLE);
                    }
                    else {
                        noexpense.setVisibility(View.GONE);
                    }


                }
                else  if (mainid.get(position).equals("2"))
                {
                    positions=2;
                    list.addAll(mydb.entertainmentdeletedata());
                    expenseAdapter = new ExpenseAdapter(getApplicationContext(),list);
                    mRecyclerView.setAdapter(expenseAdapter);
                    expenseAdapter.setOnItemClickListener(DeleteActivity.this);
                    if (list.isEmpty())
                    {
                        noexpense.setVisibility(View.VISIBLE);
                    }
                    else {
                        noexpense.setVisibility(View.GONE);
                    }
                }
                else  if (mainid.get(position).equals("3"))
                {
                    positions=3;
                    list.addAll(mydb.travellingdeletedata());
                    expenseAdapter = new ExpenseAdapter(getApplicationContext(),list);
                    mRecyclerView.setAdapter(expenseAdapter);
                    expenseAdapter.setOnItemClickListener(DeleteActivity.this);
                    if (list.isEmpty())
                    {
                        noexpense.setVisibility(View.VISIBLE);
                    }
                    else {
                        noexpense.setVisibility(View.GONE);
                    }
                }
                else  if (mainid.get(position).equals("4"))
                {
                    positions=4;
                    list.addAll(mydb.clothdeletedata());
                    expenseAdapter = new ExpenseAdapter(getApplicationContext(),list);
                    mRecyclerView.setAdapter(expenseAdapter);
                    expenseAdapter.setOnItemClickListener(DeleteActivity.this);
                    if (list.isEmpty())
                    {
                        noexpense.setVisibility(View.VISIBLE);
                    }
                    else {
                        noexpense.setVisibility(View.GONE);
                    }
                }
                else  if (mainid.get(position).equals("5"))
                {
                    positions=5;
                    list.addAll(mydb.sportdeletedata());
                    expenseAdapter = new ExpenseAdapter(getApplicationContext(),list);
                    mRecyclerView.setAdapter(expenseAdapter);
                    expenseAdapter.setOnItemClickListener(DeleteActivity.this);
                    if (list.isEmpty())
                    {
                        noexpense.setVisibility(View.VISIBLE);
                    }
                    else {
                        noexpense.setVisibility(View.GONE);
                    }
                }
                else  if (mainid.get(position).equals("6"))
                {
                    positions=6;
                    list.addAll(mydb.incomedeletedata());
                    expenseAdapter = new ExpenseAdapter(getApplicationContext(),list);
                    mRecyclerView.setAdapter(expenseAdapter);
                    expenseAdapter.setOnItemClickListener(DeleteActivity.this);
                    if (list.isEmpty())
                    {
                        noexpense.setVisibility(View.VISIBLE);
                    }
                    else {
                        noexpense.setVisibility(View.GONE);
                    }
                }
                else {
                    list.clear();
                    expenseAdapter = new ExpenseAdapter(getApplicationContext(),list);
                    mRecyclerView.setAdapter(expenseAdapter);
                    expenseAdapter.setOnItemClickListener(DeleteActivity.this);
                    if (list.isEmpty())
                    {
                        noexpense.setVisibility(View.VISIBLE);
                    }
                    else {
                        noexpense.setVisibility(View.GONE);
                    }
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onItemClick(int position) {

        Toast.makeText(this, "Long click to edit or delete data", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onEditClick(int position,String i,String n,String a,String d) {

        id = i;
        name = n;
        amount = a;
        date = d;

        EditDialog();
    }

    @Override
    public void onDeleteClick(int position,String s) {

        mydb.deleteDatas(s);
        mydb.removePlace(list.get(position));
        list.remove(position);
        expenseAdapter.notifyDataSetChanged();
        Toast.makeText(DeleteActivity.this, "Data Deleted", Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onBackPressed() {
        finish();
        Intent intent  = new Intent(DeleteActivity.this,MainActivity.class);
        startActivity(intent);
        super.onBackPressed();

    }

    private void EditDialog() {
        final Dialog dialogs = new Dialog(DeleteActivity.this);
        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogs.setContentView(R.layout.deletedialog);
        dialogs.setCanceledOnTouchOutside(false);
        EditText names = dialogs.findViewById(R.id.name);
        EditText amounts = dialogs.findViewById(R.id.amount);
        EditText dates = dialogs.findViewById(R.id.date);
        ImageView back = dialogs.findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogs.dismiss();
            }
        });
        names.setText(name);
        amounts.setText(amount);
        dates.setText(date);

        dates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(DeleteActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                c.set(Calendar.YEAR, year);
                                c.set(Calendar.MONTH, monthOfYear);
                                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                String monthName = "";
                                switch (monthOfYear) {
                                    case 0:
                                        monthName = "January";
                                        break;
                                    case 1:
                                        monthName = "February";
                                        break;
                                    case 2:
                                        monthName = "March";
                                        break;
                                    case 3:
                                        monthName = "April";
                                        break;
                                    case 4:
                                        monthName = "May";
                                        break;
                                    case 5:
                                        monthName = "June";
                                        break;
                                    case 6:
                                        monthName = "July";
                                        break;
                                    case 7:
                                        monthName = "August";
                                        break;
                                    case 8:
                                        monthName = "September";
                                        break;
                                    case 9:
                                        monthName = "October";
                                        break;
                                    case 10:
                                        monthName = "November";
                                        break;
                                    case 11:
                                        monthName = "December";
                                        break;
                                    default:
                                        monthName = "Invalid month";
                                        break;

                                }
                                String dof = null;
                                if (dayOfMonth < 10) {
                                    dof= "0" + dayOfMonth;
                                }
                                else
                                {
                                    dof = String.valueOf(dayOfMonth);
                                }

                                dates.setText(dof + "-" + (monthName) + "-" + year);
                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.show();
            }
        });

        Button save = dialogs.findViewById(R.id.save);

        amounts.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(8,2)});

        amounts.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{

                    if (amounts.length()==8) {
                        Toast.makeText(DeleteActivity.this, "Cannot be too large", Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){

                    amounts.setHint("Cannot be too large");
                    e.printStackTrace();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (amounts.length()==0 )
                {
                    Toast.makeText(DeleteActivity.this, "Enter Valid Amount", Toast.LENGTH_SHORT).show();
                    amounts.requestFocus();
                }
                else if (amounts.getText().toString().equals("0")||amounts.getText().toString().equals("00")||amounts.getText().toString().equals("000")||amounts.getText().toString().equals("0000")||amounts.getText().toString().equals("00000")||amounts.getText().toString().equals("000000")||amounts.getText().toString().equals("0000000")||amounts.getText().toString().equals("00000000")||amounts.getText().toString().equals("000000000")||amounts.getText().toString().equals("000000000")||amounts.getText().toString().equals("0000000000"))
                {
                    Toast.makeText(DeleteActivity.this, "Amount should be greater than 0", Toast.LENGTH_SHORT).show();
                    amounts.requestFocus();
                }
                else
                {
                    mydb.updateDatas(id,names.getText().toString(),amounts.getText().toString(),dates.getText().toString());
                    dialogs.dismiss();


                    if(positions==0){
                        list.clear();
                        expenseAdapter = new ExpenseAdapter(getApplicationContext(),list);
                        mRecyclerView.setAdapter(expenseAdapter);
                        idmain=mainid.get(positions);
                        Log.d("cidsss",idmain);

                        //    list.clear();
                        if (list.isEmpty())
                        {
                            noexpense.setVisibility(View.VISIBLE);
                        }
                        else {
                            noexpense.setVisibility(View.GONE);
                        }
                    }
                    else  if (positions==1)
                    {
                        list.clear();

                        list.addAll(mydb.homedeletedata());
                        expenseAdapter = new ExpenseAdapter(getApplicationContext(),list);
                        mRecyclerView.setAdapter(expenseAdapter);
                        expenseAdapter.setOnItemClickListener(DeleteActivity.this);
                        if (list.isEmpty())
                        {
                            noexpense.setVisibility(View.VISIBLE);
                        }
                        else {
                            noexpense.setVisibility(View.GONE);
                        }

                    }
                    else  if (positions==2)
                    {
                        list.clear();

                        list.addAll(mydb.entertainmentdeletedata());
                        expenseAdapter = new ExpenseAdapter(getApplicationContext(),list);
                        mRecyclerView.setAdapter(expenseAdapter);
                        expenseAdapter.setOnItemClickListener(DeleteActivity.this);
                        if (list.isEmpty())
                        {
                            noexpense.setVisibility(View.VISIBLE);
                        }
                        else {
                            noexpense.setVisibility(View.GONE);
                        }
                    }
                    else  if (positions==3)
                    {
                        list.clear();

                        list.addAll(mydb.travellingdeletedata());
                        expenseAdapter = new ExpenseAdapter(getApplicationContext(),list);
                        mRecyclerView.setAdapter(expenseAdapter);
                        expenseAdapter.setOnItemClickListener(DeleteActivity.this);
                        if (list.isEmpty())
                        {
                            noexpense.setVisibility(View.VISIBLE);
                        }
                        else {
                            noexpense.setVisibility(View.GONE);
                        }
                    }
                    else  if (positions==4)
                    {
                        list.clear();

                        list.addAll(mydb.clothdeletedata());
                        expenseAdapter = new ExpenseAdapter(getApplicationContext(),list);
                        mRecyclerView.setAdapter(expenseAdapter);
                        expenseAdapter.setOnItemClickListener(DeleteActivity.this);
                        if (list.isEmpty())
                        {
                            noexpense.setVisibility(View.VISIBLE);
                        }
                        else {
                            noexpense.setVisibility(View.GONE);
                        }
                    }
                    else  if (positions==5)
                    {
                        list.clear();

                        list.addAll(mydb.sportdeletedata());
                        expenseAdapter = new ExpenseAdapter(getApplicationContext(),list);
                        mRecyclerView.setAdapter(expenseAdapter);
                        expenseAdapter.setOnItemClickListener(DeleteActivity.this);
                        if (list.isEmpty())
                        {
                            noexpense.setVisibility(View.VISIBLE);
                        }
                        else {
                            noexpense.setVisibility(View.GONE);
                        }
                    }
                    else  if (positions==6)
                    {
                        list.clear();

                        list.addAll(mydb.incomedeletedata());
                        expenseAdapter = new ExpenseAdapter(getApplicationContext(),list);
                        mRecyclerView.setAdapter(expenseAdapter);
                        expenseAdapter.setOnItemClickListener(DeleteActivity.this);
                        if (list.isEmpty())
                        {
                            noexpense.setVisibility(View.VISIBLE);
                        }
                        else {
                            noexpense.setVisibility(View.GONE);
                        }
                    }
                    else {
                        list.clear();
                        expenseAdapter = new ExpenseAdapter(getApplicationContext(),list);
                        mRecyclerView.setAdapter(expenseAdapter);
                        expenseAdapter.setOnItemClickListener(DeleteActivity.this);
                        if (list.isEmpty())
                        {
                            noexpense.setVisibility(View.VISIBLE);
                        }
                        else {
                            noexpense.setVisibility(View.GONE);
                        }
                    }

                    Toast.makeText(DeleteActivity.this, "Data Saved", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialogs.show();
    }

}
