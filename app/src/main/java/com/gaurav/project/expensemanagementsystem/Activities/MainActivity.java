package com.gaurav.project.expensemanagementsystem.Activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gaurav.project.expensemanagementsystem.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.graphics.Color.parseColor;

public class MainActivity extends AppCompatActivity {

    private LinearLayout linearLayoutCardsRoot;

    ActionBarDrawerToggle toggle;
    DrawerLayout Drawer;
    android.support.v7.widget.Toolbar toolbar;
    android.support.v7.app.ActionBar actionBar;
    RelativeLayout toolbars;
    NestedScrollView nested_content;
    TextView txtdate, txtdatee, txthome, txtentertainment, txttravelling, txtsports, txtcloths, rlincome;
    TextView mhome, tvedit, mentertainment, mtravelling, mcloth, msport, mincome;
    TextView savingamount, expenseamount, balanceamount;
    RelativeLayout sharelink;
    String sdate = "";
    LinearLayout llshowhome, llshowentertainment, llshowtravelling, llshowcloth, llshowsports, llshowincome;
    DatabaseHelper mydb;
    String home, entertainment, travelling, cloth, sport, income;

    int flag = 0;

    private LocalBackup localBackup;

    private static final String TAG = "Google Drive Activity";

    public static final int REQUEST_CODE_SIGN_IN = 0;
    public static final int REQUEST_CODE_OPENING = 1;
    public static final int REQUEST_CODE_CREATION = 2;
    public static final int REQUEST_CODE_PERMISSIONS = 2;
    private boolean isBackup = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(parseColor("#3f8342"));
        }

        mydb = new DatabaseHelper(MainActivity.this);

        txtdate = findViewById(R.id.txtdate);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy");
        final String currentDate = sdf.format(new Date());
        txtdate.setText("All");

        localBackup = new LocalBackup(this);
/*
        tvedit = findViewById(R.id.tvedit);
        tvedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditaActivity.class);
                startActivity(intent);
            }
        });

        mhome = findViewById(R.id.mhome);
        long home_count = mydb.getTotalOfAmountHome();
        mhome.setText(String.valueOf(home_count));

        mentertainment = findViewById(R.id.mentertainment);
        int entertainment_count = mydb.getTotalOfAmountEntertainment();
        mentertainment.setText(String.valueOf(entertainment_count));

        mtravelling = findViewById(R.id.mtravelling);
        int travelling_count = mydb.getTotalOfAmountTravelling();
        mtravelling.setText(String.valueOf(travelling_count));

        mcloth = findViewById(R.id.mcloth);
        int cloth_count = mydb.getTotalOfAmountCloth();
        mcloth.setText(String.valueOf(cloth_count));

        msport = findViewById(R.id.msport);
        int sport_count = mydb.getTotalOfAmountSport();
        msport.setText(String.valueOf(sport_count));

        mincome = findViewById(R.id.mincome);
        int income_count = mydb.getTotalOfAmountIncome();
        mincome.setText(String.valueOf(income_count));

        home = mhome.getText().toString();
        entertainment = mentertainment.getText().toString();
        travelling = mtravelling.getText().toString();
        cloth = mcloth.getText().toString();
        sport = msport.getText().toString();
        income = mincome.getText().toString();

        int expensesum = 0;
        expensesum = expensesum + Integer.parseInt(String.valueOf(Integer.parseInt(home) + Integer.parseInt(entertainment) + Integer.parseInt(travelling) + Integer.parseInt(cloth) + Integer.parseInt(sport)));
        int savingsum = 0;
        savingsum = savingsum + Integer.parseInt(income);

        savingamount = findViewById(R.id.savingammount);
        savingamount.setText(String.valueOf(savingsum));

        expenseamount = findViewById(R.id.expenseamount);
        expenseamount.setText(String.valueOf(expensesum));

        balanceamount = findViewById(R.id.balanceamount);
        int balamount = 0;
        balamount = savingsum - expensesum;
        if (savingsum > expensesum) {
            balanceamount.setTextColor(Color.parseColor("#4CAF50"));
            balanceamount.setText(String.valueOf(balamount));

        } else if (savingsum < expensesum) {
            balanceamount.setTextColor(Color.parseColor("#F44336"));
            balanceamount.setText("" + String.valueOf(balamount));
        } else {
            balanceamount.setTextColor(Color.parseColor("#404a57"));
            balanceamount.setText("0");
        }

        llshowhome = findViewById(R.id.llshowhome);
        llshowhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getHomeData();
                if (res.getCount() == 0) {
                    showMessage("Error", "Nothing found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {

                    buffer.append("Id :" + res.getString(0) + "\n");
                    buffer.append("Money :" + res.getString(2) + "\n");
                    buffer.append("Date :" + res.getString(3) + "\n");
                    buffer.append("Name :" + res.getString(1) + "\n\n");
                }

                //show all data

                showMessage("Home Expenses on " + currentDate, buffer.toString());
            }
        });

        llshowentertainment = findViewById(R.id.llshowentertainment);
        llshowentertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getEntertainmentData();
                if (res.getCount() == 0) {
                    showMessage("Error", "Nothing found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {

                    buffer.append("Id :" + res.getString(0) + "\n");
                    buffer.append("Money :" + res.getString(2) + "\n");
                    buffer.append("Name :" + res.getString(1) + "\n\n");
                }

                //show all data

                showMessage("Entertainment Expenses on " + currentDate, buffer.toString());
            }
        });

        llshowtravelling = findViewById(R.id.llshowtravelling);
        llshowtravelling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.gettTravellingData();
                if (res.getCount() == 0) {
                    showMessage("Error", "Nothing found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {

                    buffer.append("Id :" + res.getString(0) + "\n");
                    buffer.append("Money :" + res.getString(2) + "\n");
                    buffer.append("Name :" + res.getString(1) + "\n\n");
                }

                //show all data

                showMessage("Travelling Expenses on " + currentDate, buffer.toString());
            }
        });

        llshowcloth = findViewById(R.id.llshowcloth);
        llshowcloth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getcClothData();
                if (res.getCount() == 0) {
                    showMessage("Error", "Nothing found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {

                    buffer.append("Id :" + res.getString(0) + "\n");
                    buffer.append("Money :" + res.getString(2) + "\n");
                    buffer.append("Name :" + res.getString(1) + "\n\n");
                }

                //show all data

                showMessage("Cloth Expenses on " + currentDate, buffer.toString());
            }
        });
        llshowsports = findViewById(R.id.llshowsport);
        llshowsports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getSportData();
                if (res.getCount() == 0) {
                    showMessage("Error", "Nothing Found ");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {

                    buffer.append("Id :" + res.getString(0) + "\n");
                    buffer.append("Money :" + res.getString(2) + "\n");
                    buffer.append("Name :" + res.getString(1) + "\n\n");
                }

                //show all data

                showMessage("Sport Expenses on " + currentDate, buffer.toString());
            }
        });


        llshowincome = findViewById(R.id.llshowincome);
        llshowincome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getIncomeData();
                if (res.getCount() == 0) {
                    showMessage("Error", "Nothing Found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {

                    buffer.append("Id :" + res.getString(0) + "\n");
                    buffer.append("Money :" + res.getString(2) + "\n");
                    buffer.append("Name :" + res.getString(1) + "\n\n");
                }

                //show all data

                showMessage("Income on " + currentDate, buffer.toString());
            }
        });

*/
        if(flag == 0) {
            getAllData();
            /*mydb.inertData1();
            mydb.inertData2();
*/
            flag=1;
        }
        txtdatee = findViewById(R.id.txtdatee);
        txtdatee.setText("All");


        txthome = findViewById(R.id.txthome);
        txthome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, HomeExpense.class);
                i.putExtra("house", "house");
                startActivity(i);
            }
        });

        txtentertainment = findViewById(R.id.txtentertainment);
        txtentertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, EntertainmentActivity.class);
                startActivity(i);
            }
        });
        txttravelling = findViewById(R.id.txttravelling);
        txttravelling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, TravellingActivity.class);
                startActivity(i);
            }
        });
        txtsports = findViewById(R.id.txtsports);
        txtsports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SportActivity.class);
                startActivity(i);
            }
        });
        txtcloths = findViewById(R.id.txtcloths);
        txtcloths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ClothsActivity.class);
                startActivity(i);
            }
        });
        rlincome = findViewById(R.id.rlincome);
        rlincome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, NewIncomeAcitivty.class);
                startActivity(i);
            }
        });

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        actionBar = getSupportActionBar();

        toolbars = (RelativeLayout) findViewById(R.id.layout1);


        nested_content = (NestedScrollView) findViewById(R.id.nested_scroll_view);


        sharelink = findViewById(R.id.sharelink);
        sharelink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int applicationNameId = getApplicationInfo().labelRes;
                final String appPackageName = getPackageName();
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, getString(applicationNameId));
                String text = "Install this cool application: ";
                String link = "https://github.com/gaurav2398/ExpenseManagementSystem";
                i.putExtra(Intent.EXTRA_TEXT, text + " " + link);
                startActivity(Intent.createChooser(i, "Share link:"));
            }
        });

        linearLayoutCardsRoot = findViewById(R.id.moving_cards_activity_cards_root_view);
        linearLayoutCardsRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        animateCards();

        NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);
        Drawer = (DrawerLayout) findViewById(R.id.drawer_layout);        // Drawer object Assigned to the view
        toggle = new ActionBarDrawerToggle(this, Drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }
        }; // Drawer Toggle Object Made
        Drawer.addDrawerListener(toggle); // Drawer Listener set to the Drawer toggle
        toggle.syncState();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date monthFirstDay = calendar.getTime();
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date monthLastDay = calendar.getTime();

        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        final Date currentyear = calendar.getTime();                                    //getting current year


        SimpleDateFormat df = new SimpleDateFormat("MMMM");
        final String startDateStr = df.format(monthFirstDay);                           //getting start date and end date of year
        final String endDateStr = df.format(monthLastDay);


        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DATE, -1);
        }
        final Date week = calendar.getTime();

        nav_view.setCheckedItem(R.id.all);

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(final MenuItem item) {
                actionBar.setTitle(item.getTitle());
                Drawer.closeDrawers();
                //  drawer.closeDrawers();
                switch (item.getItemId()) {
                    case R.id.all:
                        getAllData();
                        break;
                    case R.id.day:
                        getDayData();
                        break;
                    case R.id.week:

                        getWeekData();
                        break;

                    case R.id.month:
                        getMonthData();
                        break;
                    case R.id.year:

                        getYearData();

                        break;
                    /*case R.id.choosedate:

                        getChooseDateData();
                        break;*/
                }
                return true;
            }
        });

        // open drawer at start
        //  drawer.openDrawer(GravityCompat.START);
    }

    private void animateCards() {
        float cardOffsetY = getResources().getDimensionPixelSize(R.dimen.moving_cards_activity_card_offset_y);
        Interpolator cardInterpolator = AnimationUtils.loadInterpolator(this, android.R.interpolator.linear_out_slow_in);

        final float CARD_START_OPACITY = 0.85f;
        final float CARD_FINAL_OPACITY = 1f;
        final float CARD_FINAL_Y_AXIS_VALUE = 0f;
        final long CARD_ANIMATION_DURATION_MS = 1000L;
        final float CARD_OFFSET_MULTIPLIER = 1.5f;
        final int CARDS_COUNT = linearLayoutCardsRoot.getChildCount();

        for (int position = 0; position < CARDS_COUNT; position++) {
            View card = linearLayoutCardsRoot.getChildAt(position);
            card.setVisibility(View.VISIBLE);
            card.setTranslationY(cardOffsetY);
            //  change card opacity
            card.setAlpha(CARD_START_OPACITY);

            card.animate()
                    .translationY(CARD_FINAL_Y_AXIS_VALUE)
                    .alpha(CARD_FINAL_OPACITY)
                    .setInterpolator(cardInterpolator)
                    .setDuration(CARD_ANIMATION_DURATION_MS)
                    .start();

            cardOffsetY *= CARD_OFFSET_MULTIPLIER;
        }
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.backup:
                String outFileName = Environment.getExternalStorageDirectory() + File.separator + getResources().getString(R.string.app_name) + File.separator;
                localBackup.performBackup(mydb, outFileName);

               // Toast.makeText(getApplicationContext(), "Backup Successfully Done", Toast.LENGTH_LONG).show();

                return true;

            case R.id.restore:
                localBackup.performRestore(mydb);
                return true;

            case R.id.pdf:
                Toast.makeText(getApplicationContext(), "pdf created", Toast.LENGTH_LONG).show();
                return true;

            case R.id.delete:
                this.deleteDatabase(DatabaseHelper.DATABASE_NAME);
                String myPath = DatabaseHelper.DATABASE_NAME;
                SQLiteDatabase.deleteDatabase(new File(myPath));
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Data Deleted Successfully", Toast.LENGTH_LONG).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void getAllData() {
        Toast.makeText(getApplicationContext(), "All Selected", Toast.LENGTH_SHORT).show();


        txtdate = findViewById(R.id.txtdate);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy");
        final String currentDate = sdf.format(new Date());

        tvedit = findViewById(R.id.tvedit);
        tvedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditaActivity.class);
                startActivity(intent);
            }
        });

        mhome = findViewById(R.id.mhome);
        long home_count = mydb.getTotalOfAmountHome();
        mhome.setText(String.valueOf(home_count));

        mentertainment = findViewById(R.id.mentertainment);
        long entertainment_count = mydb.getTotalOfAmountEntertainment();
        mentertainment.setText(String.valueOf(entertainment_count));

        mtravelling = findViewById(R.id.mtravelling);
        long travelling_count = mydb.getTotalOfAmountTravelling();
        mtravelling.setText(String.valueOf(travelling_count));

        mcloth = findViewById(R.id.mcloth);
        long cloth_count = mydb.getTotalOfAmountCloth();
        mcloth.setText(String.valueOf(cloth_count));

        msport = findViewById(R.id.msport);
        long sport_count = mydb.getTotalOfAmountSport();
        msport.setText(String.valueOf(sport_count));

        mincome = findViewById(R.id.mincome);
        long income_count = mydb.getTotalOfAmountIncome();
        mincome.setText(String.valueOf(income_count));

        home = mhome.getText().toString();
        entertainment = mentertainment.getText().toString();
        travelling = mtravelling.getText().toString();
        cloth = mcloth.getText().toString();
        sport = msport.getText().toString();
        income = mincome.getText().toString();

        Long expensesum = Long.valueOf(0);
        expensesum = expensesum + Long.valueOf(String.valueOf(Long.valueOf(home) + Long.valueOf(entertainment) + Long.valueOf(travelling) + Long.valueOf(cloth) + Long.valueOf(sport)));

        Long savingsum = Long.valueOf(0);

        savingsum = savingsum + Long.valueOf(income);

        savingamount = findViewById(R.id.savingammount);
        savingamount.setText(String.valueOf(savingsum));

        expenseamount = findViewById(R.id.expenseamount);
        expenseamount.setText(String.valueOf(expensesum));

        balanceamount = findViewById(R.id.balanceamount);
        Long balamount = Long.valueOf(0);
        balamount = savingsum - expensesum;
        if (savingsum > expensesum) {
            balanceamount.setTextColor(Color.parseColor("#4CAF50"));
            balanceamount.setText(String.valueOf(balamount));

        } else if (savingsum < expensesum) {
            balanceamount.setTextColor(Color.parseColor("#F44336"));
            balanceamount.setText("" + String.valueOf(balamount));
        } else {
            balanceamount.setTextColor(Color.parseColor("#404a57"));
            balanceamount.setText("0");
        }

        llshowhome = findViewById(R.id.llshowhome);
        llshowhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getHomeData();
                if (res.getCount() == 0) {
                    showMessage("Error", "Nothing found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {

                    buffer.append("Id :" + res.getString(0) + "\n");
                    buffer.append("Money :" + res.getString(2) + "\n");
                    buffer.append("Date :" + res.getString(3) + "\n");
                    buffer.append("Name :" + res.getString(1) + "\n\n");
                }

                //show all data

                showMessage("Home Expenses on " + currentDate, buffer.toString());
            }
        });

        llshowentertainment = findViewById(R.id.llshowentertainment);
        llshowentertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getEntertainmentData();
                if (res.getCount() == 0) {
                    showMessage("Error", "Nothing found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {


                    buffer.append("Id :" + res.getString(0) + "\n");
                    buffer.append("Money :" + res.getString(2) + "\n");
                    buffer.append("Date :" + res.getString(3) + "\n");
                    buffer.append("Name :" + res.getString(1) + "\n\n");
                }

                //show all data

                showMessage("Entertainment Expenses on " + currentDate, buffer.toString());
            }
        });

        llshowtravelling = findViewById(R.id.llshowtravelling);
        llshowtravelling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.gettTravellingData();
                if (res.getCount() == 0) {
                    showMessage("Error", "Nothing found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {


                    buffer.append("Id :" + res.getString(0) + "\n");
                    buffer.append("Money :" + res.getString(2) + "\n");
                    buffer.append("Date :" + res.getString(3) + "\n");
                    buffer.append("Name :" + res.getString(1) + "\n\n");
                }

                //show all data

                showMessage("Travelling Expenses on " + currentDate, buffer.toString());
            }
        });

        llshowcloth = findViewById(R.id.llshowcloth);
        llshowcloth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getcClothData();
                if (res.getCount() == 0) {
                    showMessage("Error", "Nothing found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {


                    buffer.append("Id :" + res.getString(0) + "\n");
                    buffer.append("Money :" + res.getString(2) + "\n");
                    buffer.append("Date :" + res.getString(3) + "\n");
                    buffer.append("Name :" + res.getString(1) + "\n\n");
                }

                //show all data

                showMessage("Cloth Expenses on " + currentDate, buffer.toString());
            }
        });
        llshowsports = findViewById(R.id.llshowsport);
        llshowsports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getSportData();
                if (res.getCount() == 0) {
                    showMessage("Error", "Nothing Found ");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {


                    buffer.append("Id :" + res.getString(0) + "\n");
                    buffer.append("Money :" + res.getString(2) + "\n");
                    buffer.append("Date :" + res.getString(3) + "\n");
                    buffer.append("Name :" + res.getString(1) + "\n\n");
                }

                //show all data

                showMessage("Sport Expenses on " + currentDate, buffer.toString());
            }
        });


        llshowincome = findViewById(R.id.llshowincome);
        llshowincome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getIncomeData();
                if (res.getCount() == 0) {
                    showMessage("Error", "Nothing Found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {


                    buffer.append("Id :" + res.getString(0) + "\n");
                    buffer.append("Money :" + res.getString(2) + "\n");
                    buffer.append("Date :" + res.getString(3) + "\n");
                    buffer.append("Name :" + res.getString(1) + "\n\n");
                }
                //show all data
                showMessage("Income on " + currentDate, buffer.toString());
            }
        });

        txtdatee = findViewById(R.id.txtdatee);
        txtdate.setText("All");
        txtdatee.setText("All");
    }

    public void getDayData() {
        Toast.makeText(getApplicationContext(), "Day Selected", Toast.LENGTH_SHORT).show();
        txtdate = findViewById(R.id.txtdate);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy");
        final String currentDate = sdf.format(new Date());
        txtdate.setText(currentDate);

        tvedit = findViewById(R.id.tvedit);
        tvedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditaActivity.class);
                startActivity(intent);
            }
        });

        mhome = findViewById(R.id.mhome);
        long home_count = mydb.getTotalOfAmountHomeDay(currentDate);
        mhome.setText(String.valueOf(home_count));

        mentertainment = findViewById(R.id.mentertainment);
        long entertainment_count = mydb.getTotalOfAmountEntertainmentDay(currentDate);
        mentertainment.setText(String.valueOf(entertainment_count));

        mtravelling = findViewById(R.id.mtravelling);
        long travelling_count = mydb.getTotalOfAmountTravellingDay(currentDate);
        mtravelling.setText(String.valueOf(travelling_count));

        mcloth = findViewById(R.id.mcloth);
        long cloth_count = mydb.getTotalOfAmountClothDay(currentDate);
        mcloth.setText(String.valueOf(cloth_count));

        msport = findViewById(R.id.msport);
        long sport_count = mydb.getTotalOfAmountSportDay(currentDate);
        msport.setText(String.valueOf(sport_count));

        mincome = findViewById(R.id.mincome);
        long income_count = mydb.getTotalOfAmountIncomeDay(currentDate);
        mincome.setText(String.valueOf(income_count));



        home = mhome.getText().toString();
        entertainment = mentertainment.getText().toString();
        travelling = mtravelling.getText().toString();
        cloth = mcloth.getText().toString();
        sport = msport.getText().toString();
        income = mincome.getText().toString();

        Long expensesum = Long.valueOf(0);
        expensesum = expensesum + Long.valueOf(String.valueOf(Long.valueOf(home) + Long.valueOf(entertainment) + Long.valueOf(travelling) + Long.valueOf(cloth) + Long.valueOf(sport)));

        Long savingsum = Long.valueOf(0);

        savingsum = savingsum + Long.valueOf(income);

        savingamount = findViewById(R.id.savingammount);
        savingamount.setText(String.valueOf(savingsum));

        expenseamount = findViewById(R.id.expenseamount);
        expenseamount.setText(String.valueOf(expensesum));

        balanceamount = findViewById(R.id.balanceamount);
        Long balamount = Long.valueOf(0);
        balamount = savingsum - expensesum;
        if (savingsum > expensesum) {
            balanceamount.setTextColor(Color.parseColor("#4CAF50"));
            balanceamount.setText(String.valueOf(balamount));

        } else if (savingsum < expensesum) {
            balanceamount.setTextColor(Color.parseColor("#F44336"));
            balanceamount.setText("" + String.valueOf(balamount));
        } else {
            balanceamount.setTextColor(Color.parseColor("#404a57"));
            balanceamount.setText("0");
        }

        llshowhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getHomeDataDateWise(currentDate);
                if (res.getCount() == 0) {
                    showMessage("Error", "Nothing found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {

                    buffer.append("Id :" + res.getString(0) + "\n");
                    buffer.append("Money :" + res.getString(2) + "\n");
                    buffer.append("Date :" + res.getString(3) + "\n");
                    buffer.append("Name :" + res.getString(1) + "\n\n");
                }
                //show all data
                showMessage("Home Expenses on " + currentDate, buffer.toString());
            }
        });


        llshowentertainment = findViewById(R.id.llshowentertainment);
        llshowentertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getEntertainmentDateWise(currentDate);
                if (res.getCount() == 0) {
                    showMessage("Error", "Nothing found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {


                    buffer.append("Id :" + res.getString(0) + "\n");
                    buffer.append("Money :" + res.getString(2) + "\n");
                    buffer.append("Date :" + res.getString(3) + "\n");
                    buffer.append("Name :" + res.getString(1) + "\n\n");
                }

                //show all data

                showMessage("Entertainment Expenses on " + currentDate, buffer.toString());
            }
        });

        llshowtravelling = findViewById(R.id.llshowtravelling);
        llshowtravelling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.gettTravellingDateWise(currentDate);
                if (res.getCount() == 0) {
                    showMessage("Error", "Nothing found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {

                    buffer.append("Id :" + res.getString(0) + "\n");
                    buffer.append("Money :" + res.getString(2) + "\n");
                    buffer.append("Name :" + res.getString(1) + "\n\n");
                }

                //show all data

                showMessage("Travelling Expenses on " + currentDate, buffer.toString());
            }
        });

        llshowcloth = findViewById(R.id.llshowcloth);
        llshowcloth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getcClothDateWise(currentDate);
                if (res.getCount() == 0) {
                    showMessage("Error", "Nothing found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {


                    buffer.append("Id :" + res.getString(0) + "\n");
                    buffer.append("Money :" + res.getString(2) + "\n");
                    buffer.append("Date :" + res.getString(3) + "\n");
                    buffer.append("Name :" + res.getString(1) + "\n\n");
                }

                //show all data

                showMessage("Cloth Expenses on " + currentDate, buffer.toString());
            }
        });
        llshowsports = findViewById(R.id.llshowsport);
        llshowsports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getSportDateWise(currentDate);
                if (res.getCount() == 0) {
                    showMessage("Error", "Nothing Found ");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {


                    buffer.append("Id :" + res.getString(0) + "\n");
                    buffer.append("Money :" + res.getString(2) + "\n");
                    buffer.append("Date :" + res.getString(3) + "\n");
                    buffer.append("Name :" + res.getString(1) + "\n\n");
                }

                //show all data

                showMessage("Sport Expenses on " + currentDate, buffer.toString());
            }
        });


        llshowincome = findViewById(R.id.llshowincome);
        llshowincome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getIncomeDateWise(currentDate);
                if (res.getCount() == 0) {
                    showMessage("Error", "Nothing Found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {


                    buffer.append("Id :" + res.getString(0) + "\n");
                    buffer.append("Money :" + res.getString(2) + "\n");
                    buffer.append("Date :" + res.getString(3) + "\n");
                    buffer.append("Name :" + res.getString(1) + "\n\n");
                }
                //show all data
                showMessage("Income on " + currentDate, buffer.toString());
            }
        });

        txtdatee = findViewById(R.id.txtdatee);
        txtdatee.setText(currentDate);

    }

    public void getMonthData() {

        final String monthdata;

        Toast.makeText(getApplicationContext(), "Month Selected", Toast.LENGTH_SHORT).show();

        SimpleDateFormat sdf = new SimpleDateFormat("MMMM-yyyy");
        final String currentMonth = sdf.format(new Date());

        txtdate.setText(currentMonth);
        txtdatee.setText(currentMonth);

        tvedit = findViewById(R.id.tvedit);
        tvedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditaActivity.class);
                startActivity(intent);
            }
        });

        mhome = findViewById(R.id.mhome);
        long home_count = mydb.getTotalOfAmountHomeMonth(currentMonth);
        mhome.setText(String.valueOf(home_count));

        mentertainment = findViewById(R.id.mentertainment);
        long entertainment_count = mydb.getTotalOfAmountEntertainmentMonth(currentMonth);
        mentertainment.setText(String.valueOf(entertainment_count));

        mtravelling = findViewById(R.id.mtravelling);
        long travelling_count = mydb.getTotalOfAmountTravellingMonth(currentMonth);
        mtravelling.setText(String.valueOf(travelling_count));

        mcloth = findViewById(R.id.mcloth);
        long cloth_count = mydb.getTotalOfAmountClothMonth(currentMonth);
        mcloth.setText(String.valueOf(cloth_count));

        msport = findViewById(R.id.msport);
        long sport_count = mydb.getTotalOfAmountSportMonth(currentMonth);
        msport.setText(String.valueOf(sport_count));

        mincome = findViewById(R.id.mincome);
        long income_count = mydb.getTotalOfAmountIncomeMonth(currentMonth);
        mincome.setText(String.valueOf(income_count));

        home = mhome.getText().toString();
        entertainment = mentertainment.getText().toString();
        travelling = mtravelling.getText().toString();
        cloth = mcloth.getText().toString();
        sport = msport.getText().toString();
        income = mincome.getText().toString();

        Long expensesum = Long.valueOf(0);
        expensesum = expensesum + Long.valueOf(String.valueOf(Long.valueOf(home) + Long.valueOf(entertainment) + Long.valueOf(travelling) + Long.valueOf(cloth) + Long.valueOf(sport)));

        Long savingsum = Long.valueOf(0);

        savingsum = savingsum + Long.valueOf(income);

        savingamount = findViewById(R.id.savingammount);
        savingamount.setText(String.valueOf(savingsum));

        expenseamount = findViewById(R.id.expenseamount);
        expenseamount.setText(String.valueOf(expensesum));

        balanceamount = findViewById(R.id.balanceamount);
        Long balamount = Long.valueOf(0);
        balamount = savingsum - expensesum;
        if (savingsum > expensesum) {
            balanceamount.setTextColor(Color.parseColor("#4CAF50"));
            balanceamount.setText(String.valueOf(balamount));

        } else if (savingsum < expensesum) {
            balanceamount.setTextColor(Color.parseColor("#F44336"));
            balanceamount.setText("" + String.valueOf(balamount));
        } else {
            balanceamount.setTextColor(Color.parseColor("#404a57"));
            balanceamount.setText("0");
        }

        llshowhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getHomeDataMonthWise(currentMonth);
                if (res.getCount() == 0) {
                    showMessage("Error", "Nothing found");
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {

                    buffer.append("Id :" + res.getString(0) + "\n");
                    buffer.append("Money :" + res.getString(2) + "\n");
                    buffer.append("Date :" + res.getString(3) + "\n");
                    buffer.append("Name :" + res.getString(1) + "\n\n");
                }

                //show all data

                showMessage("Home Expenses on " +String.valueOf(currentMonth)+ " Month", buffer.toString());
            }
        });

        llshowentertainment = findViewById(R.id.llshowentertainment);
        llshowentertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getEntertainmentMonthWise(currentMonth);
                if (res.getCount() == 0) {
                    showMessage("Error", "Nothing found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {


                    buffer.append("Id :" + res.getString(0) + "\n");
                    buffer.append("Money :" + res.getString(2) + "\n");
                    buffer.append("Date :" + res.getString(3) + "\n");
                    buffer.append("Name :" + res.getString(1) + "\n\n");
                }

                //show all data
                showMessage("Entertainment Expenses on " +String.valueOf(currentMonth)+ " Month", buffer.toString());
            }
        });
        llshowtravelling = findViewById(R.id.llshowtravelling);
        llshowtravelling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.gettTravellingMonthWise(currentMonth);
                if (res.getCount() == 0) {
                    showMessage("Error", "Nothing found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {


                    buffer.append("Id :" + res.getString(0) + "\n");
                    buffer.append("Money :" + res.getString(2) + "\n");
                    buffer.append("Date :" + res.getString(3) + "\n");
                    buffer.append("Name :" + res.getString(1) + "\n\n");
                }

                //show all data

                showMessage("Travelling Expenses on " +String.valueOf(currentMonth)+ " Month", buffer.toString());
            }
        });
        llshowcloth = findViewById(R.id.llshowcloth);
        llshowcloth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getcClothMonthWise(currentMonth);
                if (res.getCount() == 0) {
                    showMessage("Error", "Nothing found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("Id :" + res.getString(0) + "\n");
                    buffer.append("Money :" + res.getString(2) + "\n");
                    buffer.append("Date :" + res.getString(3) + "\n");
                    buffer.append("Name :" + res.getString(1) + "\n\n");
                }
                //show all data
                showMessage("Cloth Expenses on " +String.valueOf(currentMonth)+ " Month", buffer.toString());
            }
        });
        llshowsports = findViewById(R.id.llshowsport);
        llshowsports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getSportMonthWise(currentMonth);
                if (res.getCount() == 0) {
                    showMessage("Error", "Nothing Found ");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {

                    buffer.append("Id :" + res.getString(0) + "\n");
                    buffer.append("Money :" + res.getString(2) + "\n");
                    buffer.append("Date :" + res.getString(3) + "\n");
                    buffer.append("Name :" + res.getString(1) + "\n\n");
                }

                //show all data

                showMessage("Sport Expenses on " +String.valueOf(currentMonth)+ " Month", buffer.toString());
            }
        });
        llshowincome = findViewById(R.id.llshowincome);
        llshowincome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getIncomeMonthWise(currentMonth);
                if (res.getCount() == 0) {
                    showMessage("Error", "Nothing Found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("Id :" + res.getString(0) + "\n");
                    buffer.append("Money :" + res.getString(2) + "\n");
                    buffer.append("Date :" + res.getString(3) + "\n");
                    buffer.append("Name :" + res.getString(1) + "\n\n");
                }
                //show all data
                showMessage("Income on " +String.valueOf(currentMonth)+ " Month", buffer.toString());
            }
        });
    }

    public void getYearData(){
        final String yeardata;
        Calendar calendar = Calendar.getInstance();
        final Date currentyear = calendar.getTime();                                    //getting current year

        SimpleDateFormat df1 = new SimpleDateFormat("yyyy");
        final String curryear = df1.format(currentyear);                                //getting current year

        txtdate.setText(curryear);
        txtdatee.setText(curryear);
        Toast.makeText(getApplicationContext(), "Year Selected", Toast.LENGTH_SHORT).show();

        yeardata = txtdate.getText().toString();
        tvedit = findViewById(R.id.tvedit);
        tvedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditaActivity.class);
                startActivity(intent);
            }
        });

        mhome = findViewById(R.id.mhome);
        long home_count =  mydb.getTotalOfAmountYearWise("HOME",curryear);
        mhome.setText(String.valueOf(home_count));

        mentertainment = findViewById(R.id.mentertainment);
        long entertainment_count = mydb.getTotalOfAmountYearWise("ENTERTAINMENT",curryear);
        mentertainment.setText(String.valueOf(entertainment_count));

        mtravelling = findViewById(R.id.mtravelling);
        long travelling_count = mydb.getTotalOfAmountYearWise("TRAVELLING",curryear);
        mtravelling.setText(String.valueOf(travelling_count));

        mcloth = findViewById(R.id.mcloth);
        long cloth_count = mydb.getTotalOfAmountYearWise("CLOTH",curryear);
        mcloth.setText(String.valueOf(cloth_count));

        msport = findViewById(R.id.msport);
        long sport_count = mydb.getTotalOfAmountYearWise("SPORT",curryear);
        msport.setText(String.valueOf(sport_count));

        mincome = findViewById(R.id.mincome);
        long income_count = mydb.getTotalOfAmountYearWise("INCOME",curryear);
        mincome.setText(String.valueOf(income_count));

        home = mhome.getText().toString();
        entertainment = mentertainment.getText().toString();
        travelling = mtravelling.getText().toString();
        cloth = mcloth.getText().toString();
        sport = msport.getText().toString();
        income = mincome.getText().toString();

        Long expensesum = Long.valueOf(0);
        expensesum = expensesum + Long.valueOf(String.valueOf(Long.valueOf(home) + Long.valueOf(entertainment) + Long.valueOf(travelling) + Long.valueOf(cloth) + Long.valueOf(sport)));

        Long savingsum = Long.valueOf(0);

        savingsum = savingsum + Long.valueOf(income);

        savingamount = findViewById(R.id.savingammount);
        savingamount.setText(String.valueOf(savingsum));

        expenseamount = findViewById(R.id.expenseamount);
        expenseamount.setText(String.valueOf(expensesum));

        balanceamount = findViewById(R.id.balanceamount);
        Long balamount = Long.valueOf(0);
        balamount = savingsum - expensesum;
        if (savingsum > expensesum) {
            balanceamount.setTextColor(Color.parseColor("#4CAF50"));
            balanceamount.setText(String.valueOf(balamount));

        } else if (savingsum < expensesum) {
            balanceamount.setTextColor(Color.parseColor("#F44336"));
            balanceamount.setText("" + String.valueOf(balamount));
        } else {
            balanceamount.setTextColor(Color.parseColor("#404a57"));
            balanceamount.setText("0");
        }

        llshowhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getHomeDataYearWise("HOME",yeardata);
                if (res.getCount() == 0) {
                    showMessage("Error", "Nothing found");
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {

                    buffer.append("Id :" + res.getString(0) + "\n");
                    buffer.append("Money :" + res.getString(2) + "\n");
                    buffer.append("Date :" + res.getString(3) + "\n");
                    buffer.append("Name :" + res.getString(1) + "\n\n");
                }

                //show all data

                showMessage("Home Expenses on " +String.valueOf(yeardata)+ " Year", buffer.toString());
            }
        });

        llshowentertainment = findViewById(R.id.llshowentertainment);
        llshowentertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getHomeDataYearWise("ENTERTAINMENT",yeardata);
                if (res.getCount() == 0) {
                    showMessage("Error", "Nothing found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("Id :" + res.getString(0) + "\n");
                    buffer.append("Money :" + res.getString(2) + "\n");
                    buffer.append("Date :" + res.getString(3) + "\n");
                    buffer.append("Name :" + res.getString(1) + "\n\n");
                }
                showMessage("Entertainment Expenses on " +String.valueOf(yeardata)+ " Year", buffer.toString());
            }
        });
        llshowtravelling = findViewById(R.id.llshowtravelling);
        llshowtravelling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getHomeDataYearWise("TRAVELLING",yeardata);
                if (res.getCount() == 0) {
                    showMessage("Error", "Nothing found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {


                    buffer.append("Id :" + res.getString(0) + "\n");
                    buffer.append("Money :" + res.getString(2) + "\n");
                    buffer.append("Date :" + res.getString(3) + "\n");
                    buffer.append("Name :" + res.getString(1) + "\n\n");
                }

                //show all data

                showMessage("Travelling Expenses on " +String.valueOf(yeardata)+ " Year", buffer.toString());
            }
        });
        llshowcloth = findViewById(R.id.llshowcloth);
        llshowcloth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getHomeDataYearWise("CLOTH",yeardata);
                if (res.getCount() == 0) {
                    showMessage("Error", "Nothing found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("Id :" + res.getString(0) + "\n");
                    buffer.append("Money :" + res.getString(2) + "\n");
                    buffer.append("Date :" + res.getString(3) + "\n");
                    buffer.append("Name :" + res.getString(1) + "\n\n");
                }
                //show all data
                showMessage("Cloth Expenses on " +String.valueOf(yeardata)+ " Year", buffer.toString());
            }
        });
        llshowsports = findViewById(R.id.llshowsport);
        llshowsports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getHomeDataYearWise("SPORT",yeardata);
                if (res.getCount() == 0) {
                    showMessage("Error", "Nothing Found ");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {

                    buffer.append("Id :" + res.getString(0) + "\n");
                    buffer.append("Money :" + res.getString(2) + "\n");
                    buffer.append("Date :" + res.getString(3) + "\n");
                    buffer.append("Name :" + res.getString(1) + "\n\n");
                }

                //show all data

                showMessage("Sport Expenses on " +String.valueOf(yeardata)+ " Year", buffer.toString());
            }
        });
        llshowincome = findViewById(R.id.llshowincome);
        llshowincome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getHomeDataYearWise("INCOME",yeardata);
                if (res.getCount() == 0) {
                    showMessage("Error", "Nothing Found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("Id :" + res.getString(0) + "\n");
                    buffer.append("Money :" + res.getString(2) + "\n");
                    buffer.append("Date :" + res.getString(3) + "\n");
                    buffer.append("Name :" + res.getString(1) + "\n\n");
                }
                //show all data
                showMessage("Income on " +String.valueOf(yeardata)+ " Year", buffer.toString());
            }
        });


    }

    public void getWeekData() {
        Calendar c1 = Calendar.getInstance();
        //first day of week
        c1.set(Calendar.DAY_OF_WEEK, 1);

        int year1 = c1.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH) + 1;
        String day1 = String.valueOf(c1.get(Calendar.DAY_OF_MONTH));

        //last day of week
        c1.set(Calendar.DAY_OF_WEEK, 7);

        int year7 = c1.get(Calendar.YEAR);
        int month7 = c1.get(Calendar.MONTH) + 1;
        String day7 = String.valueOf(c1.get(Calendar.DAY_OF_MONTH));

        c1.set(Calendar.DAY_OF_WEEK, 2);
        String day2 = String.valueOf(c1.get(Calendar.DAY_OF_MONTH));

        c1.set(Calendar.DAY_OF_WEEK, 3);
        String day3 = String.valueOf(c1.get(Calendar.DAY_OF_MONTH));

        c1.set(Calendar.DAY_OF_WEEK, 4);
        String day4 = String.valueOf(c1.get(Calendar.DAY_OF_MONTH));

        c1.set(Calendar.DAY_OF_WEEK, 5);
        String day5 = String.valueOf(c1.get(Calendar.DAY_OF_MONTH));

        c1.set(Calendar.DAY_OF_WEEK, 6);
        String day6 = String.valueOf(c1.get(Calendar.DAY_OF_MONTH));

        if (Integer.parseInt(day1) < 10) {
            day1 = "0" + day1;
        }
        if (Integer.parseInt(day2) < 10) {
            day2 = "0" + day2;
        }
        if (Integer.parseInt(day3) < 10) {
            day3 = "0" + day3;
        }
        if (Integer.parseInt(day4) < 10) {
            day4 = "0" + day4;
        }
        if (Integer.parseInt(day5) < 10) {
            day5 = "0" + day5;
        }
        if (Integer.parseInt(day6) < 10) {
            day6 = "0" + day6;
        }
        if (Integer.parseInt(day7) < 10) {
            day7 = "0" + day7;
        }
        String monthName = "";
        switch (month1) {
            case 1:
                monthName = "January";
                break;
            case 2:
                monthName = "February";
                break;
            case 3:
                monthName = "March";
                break;
            case 4:
                monthName = "April";
                break;
            case 5:
                monthName = "May";
                break;
            case 6:
                monthName = "June";
                break;
            case 7:
                monthName = "July";
                break;
            case 8:
                monthName = "August";
                break;
            case 9:
                monthName = "September";
                break;
            case 10:
                monthName = "October";
                break;
            case 11:
                monthName = "November";
                break;
            case 12:
                monthName = "December";
                break;
            default:
                monthName = "Invalid month";
                break;
        }
        String monthName2 = "";
        switch (month7) {
            case 1:
                monthName2 = "January";
                break;
            case 2:
                monthName2 = "February";
                break;
            case 3:
                monthName2 = "March";
                break;
            case 4:
                monthName2 = "April";
                break;
            case 5:
                monthName2 = "May";
                break;
            case 6:
                monthName2 = "June";
                break;
            case 7:
                monthName2 = "July";
                break;
            case 8:
                monthName2 = "August";
                break;
            case 9:
                monthName2 = "September";
                break;
            case 10:
                monthName2 = "October";
                break;
            case 11:
                monthName2 = "November";
                break;
            case 12:
                monthName2 = "December";
                break;
            default:
                monthName2 = "Invalid month";
                break;
        }

        txtdate.setText(day1 + "-" + monthName + "-" + year1 + " to " + day7 + "-" + monthName2 + "-" + year7);
        txtdatee.setText(day1 + "-" + monthName + "-" + year1 + " to " + day7 + "-" + monthName2 + "-" + year7);

        final String week1, week2;
        week1 = day1 + "-" + monthName + "-" + year1;
        week2 = day7 + "-" + monthName2 + "-" + year7;

        Toast.makeText(getApplicationContext(), "Week Selected", Toast.LENGTH_SHORT).show();

        tvedit = findViewById(R.id.tvedit);
        tvedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditaActivity.class);
                startActivity(intent);
            }
        });

        mhome = findViewById(R.id.mhome);
        long home_count = mydb.getTotalOfAmountWeekWise("HOME", week1, week2);
        mhome.setText(String.valueOf(home_count));

        mentertainment = findViewById(R.id.mentertainment);
        long entertainment_count = mydb.getTotalOfAmountWeekWise("ENTERTAINMENT", week1, week2);
        mentertainment.setText(String.valueOf(entertainment_count));

        mtravelling = findViewById(R.id.mtravelling);
        long travelling_count = mydb.getTotalOfAmountWeekWise("TRAVELLING", week1, week2);
        mtravelling.setText(String.valueOf(travelling_count));

        mcloth = findViewById(R.id.mcloth);
        long cloth_count = mydb.getTotalOfAmountWeekWise("CLOTH", week1, week2);
        mcloth.setText(String.valueOf(cloth_count));

        msport = findViewById(R.id.msport);
        long sport_count = mydb.getTotalOfAmountWeekWise("SPORT", week1, week2);
        msport.setText(String.valueOf(sport_count));

        mincome = findViewById(R.id.mincome);
        long income_count = mydb.getTotalOfAmountWeekWise("INCOME", week1, week2);
        mincome.setText(String.valueOf(income_count));

        home = mhome.getText().toString();
        entertainment = mentertainment.getText().toString();
        travelling = mtravelling.getText().toString();
        cloth = mcloth.getText().toString();
        sport = msport.getText().toString();
        income = mincome.getText().toString();

        Long expensesum = Long.valueOf(0);
        expensesum = expensesum + Long.valueOf(String.valueOf(Long.valueOf(home) + Long.valueOf(entertainment) + Long.valueOf(travelling) + Long.valueOf(cloth) + Long.valueOf(sport)));

        Long savingsum = Long.valueOf(0);

        savingsum = savingsum + Long.valueOf(income);

        savingamount = findViewById(R.id.savingammount);
        savingamount.setText(String.valueOf(savingsum));

        expenseamount = findViewById(R.id.expenseamount);
        expenseamount.setText(String.valueOf(expensesum));

        balanceamount = findViewById(R.id.balanceamount);
        Long balamount = Long.valueOf(0);
        balamount = savingsum - expensesum;
        if (savingsum > expensesum) {
            balanceamount.setTextColor(Color.parseColor("#4CAF50"));
            balanceamount.setText(String.valueOf(balamount));

        } else if (savingsum < expensesum) {
            balanceamount.setTextColor(Color.parseColor("#F44336"));
            balanceamount.setText("" + String.valueOf(balamount));
        } else {
            balanceamount.setTextColor(Color.parseColor("#404a57"));
            balanceamount.setText("0");
        }

        llshowhome = findViewById(R.id.llshowhome);
        llshowhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getHomeDataWeekWise("HOME",week1, week2);
                if (res.getCount() == 0) {
                    showMessage("Error", "Nothing found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {

                    buffer.append("Id :" + res.getString(0) + "\n");
                    buffer.append("Money :" + res.getString(2) + "\n");
                    buffer.append("Date :" + res.getString(3) + "\n");
                    buffer.append("Name :" + res.getString(1) + "\n\n");
                }

                //show all data

                showMessage("Home Expenses on " + week1 + " " + week2, buffer.toString());
            }
        });
        llshowentertainment = findViewById(R.id.llshowentertainment);
        llshowentertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res =  mydb.getHomeDataWeekWise("ENTERTAINMENT",week1, week2);
                if (res.getCount() == 0) {
                    showMessage("Error", "Nothing found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {

                    buffer.append("Id :" + res.getString(0) + "\n");
                    buffer.append("Money :" + res.getString(2) + "\n");
                    buffer.append("Date :" + res.getString(3) + "\n");
                    buffer.append("Name :" + res.getString(1) + "\n\n");
                }

                //show all data

                showMessage("Entertainment Expenses on " + week1 + " " + week2, buffer.toString());
            }
        });

        llshowtravelling = findViewById(R.id.llshowtravelling);
        llshowtravelling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res =  mydb.getHomeDataWeekWise("TRAVELLING",week1, week2);
                if (res.getCount() == 0) {
                    showMessage("Error", "Nothing found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {


                    buffer.append("Id :" + res.getString(0) + "\n");
                    buffer.append("Money :" + res.getString(2) + "\n");
                    buffer.append("Date :" + res.getString(3) + "\n");
                    buffer.append("Name :" + res.getString(1) + "\n\n");
                }

                //show all data

                showMessage("Travelling Expenses on " + week1 + " " + week2, buffer.toString());
            }
        });

        llshowcloth = findViewById(R.id.llshowcloth);
        llshowcloth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res =  mydb.getHomeDataWeekWise("CLOTH",week1, week2);
                if (res.getCount() == 0) {
                    showMessage("Error", "Nothing found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {


                    buffer.append("Id :" + res.getString(0) + "\n");
                    buffer.append("Money :" + res.getString(2) + "\n");
                    buffer.append("Date :" + res.getString(3) + "\n");
                    buffer.append("Name :" + res.getString(1) + "\n\n");
                }

                //show all data

                showMessage("Cloth Expenses on " + week1 + " " + week2, buffer.toString());
            }
        });
        llshowsports = findViewById(R.id.llshowsport);
        llshowsports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res =  mydb.getHomeDataWeekWise("SPORT",week1, week2);
                if (res.getCount() == 0) {
                    showMessage("Error", "Nothing Found ");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {


                    buffer.append("Id :" + res.getString(0) + "\n");
                    buffer.append("Money :" + res.getString(2) + "\n");
                    buffer.append("Date :" + res.getString(3) + "\n");
                    buffer.append("Name :" + res.getString(1) + "\n\n");
                }

                //show all data

                showMessage("Sport Expenses on " + week1 + " " + week2, buffer.toString());
            }
        });


        llshowincome = findViewById(R.id.llshowincome);
        llshowincome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res =  mydb.getHomeDataWeekWise("INCOME",week1, week2);
                if (res.getCount() == 0) {
                    showMessage("Error", "Nothing Found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {


                    buffer.append("Id :" + res.getString(0) + "\n");
                    buffer.append("Money :" + res.getString(2) + "\n");
                    buffer.append("Date :" + res.getString(3) + "\n");
                    buffer.append("Name :" + res.getString(1) + "\n\n");
                }
                //show all data
                showMessage("Income on " + week1 + " " + week2, buffer.toString());
            }
        });
    }
    }
