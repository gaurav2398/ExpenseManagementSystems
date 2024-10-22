package com.gaurav.project.expensemanagementsystem.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

import com.gaurav.project.expensemanagementsystem.Activities.notification.NotificationHelper;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gaurav.project.expensemanagementsystem.R;
import com.itextpdf.text.DocumentException;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.graphics.Color.parseColor;

public class MainActivity extends AppCompatActivity {

    public static final String GOOGLE_ACCOUNT = "google_account";
    String profileName="", profileEmail="";

    private static final int PERMISSION_REQUEST_CODE = 200;
    private LinearLayout linearLayoutCardsRoot;
    private ForCreatingBackup localBackup;
    ActionBarDrawerToggle toggle;
    DrawerLayout Drawer;
    androidx.appcompat.widget.Toolbar toolbar;
    androidx.appcompat.app.ActionBar actionBar;
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
    GoogleApiClient mGoogleApiClient;
    private InterstitialAd mInterstitialAd;
    int flag = 0,flag1=0;
    private Context mContext;
    public static final int REQUEST_CODE_PERMISSIONS = 2;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer);

        mContext = getApplicationContext();

        NotificationHelper.scheduleRepeatingRTCNotification(mContext,"0", "1");
        NotificationHelper.enableBootReceiver(mContext);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(parseColor("#3f8342"));
        }

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        MobileAds.initialize(this, "ca-app-pub-4250344724353850~6274430743");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-4250344724353850/1387907902");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });


        AdView adView1 = new AdView(this);              //real add
        adView1.setAdSize(AdSize.BANNER);
        adView1.setAdUnitId("ca-app-pub-4250344724353850/8469349248");
        AdView mAdView1 = findViewById(R.id.adView1);
        AdRequest adRequest1 = new AdRequest.Builder().build();
        mAdView1.loadAd(adRequest1);


        localBackup = new ForCreatingBackup(this);

        mydb = new DatabaseHelper(this);

        requestPermission();

        GoogleSignInAccount googleSignInAccount = getIntent().getParcelableExtra(GOOGLE_ACCOUNT);

        getAllData();

        if (flag == 0) {
            if(googleSignInAccount != null) {

                profileName = (googleSignInAccount.getDisplayName());
                profileEmail = (googleSignInAccount.getEmail());
            }

            getAllData();

            flag = 1;
        }


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (profileName.length()>1) {

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("Name", profileName);
            editor.putString("Email", profileEmail);

            editor.apply();
        }

        String n = null,e= null;
        String name = preferences.getString("Name", "");
        String email = preferences.getString("Email", "");
        if(!name.equalsIgnoreCase(""))
        {
            n = name;
            e = email;

        }


        txtdatee = findViewById(R.id.txtdatee);
        txtdatee.setText("All");


        txthome = findViewById(R.id.txthome);
        txthome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, HomeExpense.class);
                startActivity(i);

                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
                mInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        // Code to be executed when an ad finishes loading.
                    }

                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        // Code to be executed when an ad request fails.
                    }

                    @Override
                    public void onAdOpened() {
                        // Code to be executed when the ad is displayed.
                    }

                    @Override
                    public void onAdClicked() {
                        // Code to be executed when the user clicks on an ad.
                    }

                    @Override
                    public void onAdLeftApplication() {

                    }

                    @Override
                    public void onAdClosed() {

                    }
                });

            }
        });

        txtentertainment = findViewById(R.id.txtentertainment);
        txtentertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext = getApplicationContext();

                NotificationHelper.scheduleRepeatingRTCNotification(mContext,"0", "1");
                NotificationHelper.enableBootReceiver(mContext);

                Intent i = new Intent(MainActivity.this, EntertainmentActivity.class);
                startActivity(i);
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
                mInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        // Code to be executed when an ad finishes loading.
                    }

                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        // Code to be executed when an ad request fails.
                    }

                    @Override
                    public void onAdOpened() {
                        // Code to be executed when the ad is displayed.
                    }

                    @Override
                    public void onAdClicked() {
                        // Code to be executed when the user clicks on an ad.
                    }

                    @Override
                    public void onAdLeftApplication() {
                        // Code to be executed when the user has left the app.
                    }

                    @Override
                    public void onAdClosed() {
                        // Code to be executed when the interstitial ad is closed.
                    }
                });

            }
        });
        txttravelling = findViewById(R.id.txttravelling);
        txttravelling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, TravellingActivity.class);
                startActivity(i);

                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
                mInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        // Code to be executed when an ad finishes loading.
                    }

                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        // Code to be executed when an ad request fails.
                    }

                    @Override
                    public void onAdOpened() {
                        // Code to be executed when the ad is displayed.
                    }

                    @Override
                    public void onAdClicked() {
                        // Code to be executed when the user clicks on an ad.
                    }

                    @Override
                    public void onAdLeftApplication() {
                        // Code to be executed when the user has left the app.
                    }

                    @Override
                    public void onAdClosed() {
                        // Code to be executed when the interstitial ad is closed.
                    }
                });

            }
        });
        txtsports = findViewById(R.id.txtsports);
        txtsports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SportActivity.class);
                startActivity(i);

                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
                mInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        // Code to be executed when an ad finishes loading.
                    }

                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        // Code to be executed when an ad request fails.
                    }

                    @Override
                    public void onAdOpened() {
                        // Code to be executed when the ad is displayed.
                    }

                    @Override
                    public void onAdClicked() {
                        // Code to be executed when the user clicks on an ad.
                    }

                    @Override
                    public void onAdLeftApplication() {
                        // Code to be executed when the user has left the app.
                    }

                    @Override
                    public void onAdClosed() {
                        // Code to be executed when the interstitial ad is closed.
                    }
                });

            }
        });
        txtcloths = findViewById(R.id.txtcloths);
        txtcloths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ClothsActivity.class);
                startActivity(i);

                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
                mInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        // Code to be executed when an ad finishes loading.
                    }

                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        // Code to be executed when an ad request fails.
                    }

                    @Override
                    public void onAdOpened() {
                        // Code to be executed when the ad is displayed.
                    }

                    @Override
                    public void onAdClicked() {
                        // Code to be executed when the user clicks on an ad.
                    }

                    @Override
                    public void onAdLeftApplication() {
                        // Code to be executed when the user has left the app.
                    }

                    @Override
                    public void onAdClosed() {
                        // Code to be executed when the interstitial ad is closed.
                    }
                });

            }
        });
        rlincome = findViewById(R.id.rlincome);
        rlincome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, NewIncomeAcitivty.class);
                startActivity(i);

                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
                mInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        // Code to be executed when an ad finishes loading.
                    }

                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        // Code to be executed when an ad request fails.
                    }

                    @Override
                    public void onAdOpened() {
                        // Code to be executed when the ad is displayed.
                    }

                    @Override
                    public void onAdClicked() {
                        // Code to be executed when the user clicks on an ad.
                    }

                    @Override
                    public void onAdLeftApplication() {
                        // Code to be executed when the user has left the app.
                    }

                    @Override
                    public void onAdClosed() {
                        // Code to be executed when the interstitial ad is closed.
                    }
                });

            }
        });

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        actionBar = getSupportActionBar();

        toolbars = findViewById(R.id.layout1);


        nested_content = findViewById(R.id.nested_scroll_view);


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
                String link = "Available soon";
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

        NavigationView nav_view = findViewById(R.id.nav_view);
        Drawer = findViewById(R.id.drawer_layout);



        View headerView = nav_view.getHeaderView(0);
        TextView name1 = headerView.findViewById(R.id.profile_text);
        TextView email1 = headerView.findViewById(R.id.profile_email);

        name1.setText(n);
        email1.setText(e);


        toggle = new ActionBarDrawerToggle(this, Drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }
        };
        Drawer.addDrawerListener(toggle);
        toggle.syncState();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date monthFirstDay = calendar.getTime();
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date monthLastDay = calendar.getTime();

        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));


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
                    case R.id.feedback:
                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                        intent.setData(Uri.parse(String.format("mailto:%s?subject=%s", getString(R.string.email), getString(R.string.app_name))));

                        try {
                            startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            Toast.makeText(MainActivity.this, R.string.no_email, Toast.LENGTH_SHORT).show();
                        }


                break;
                    case R.id.signout:

                        deleteDatabase(DatabaseHelper.DATABASE_NAME);
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                        alertDialogBuilder.setTitle("Alert\n");
                        alertDialogBuilder.setMessage("After Logout All data will get delete ?");
                        alertDialogBuilder.setPositiveButton("Confirm",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {

                                        String myPath = DatabaseHelper.DATABASE_NAME;
                                        SQLiteDatabase.deleteDatabase(new File(myPath));

                                        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                                                new ResultCallback<Status>() {
                                                    @Override
                                                    public void onResult(Status status) {

                                                        flag1=1;

                                                        Toast.makeText(getApplicationContext(),"Logged Out Successfully",Toast.LENGTH_SHORT).show();
                                                        Intent i=new Intent(getApplicationContext(),LoginActivity.class);
                                                        startActivity(i);
                                                    }
                                                });
                                    }
                                });

                        alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();


                        break;
                }
                return true;
            }
        });

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
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);

            super.onBackPressed();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportActionBar().setDisplayShowTitleEnabled(false);

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

                return true;

            case R.id.restore:

                localBackup.performRestore(mydb);

                getAllData();
                return true;


            case R.id.delete:
                this.deleteDatabase(DatabaseHelper.DATABASE_NAME);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Alert\n");
                alertDialogBuilder.setMessage("Are you sure you want to delete data?");
                alertDialogBuilder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                String myPath = DatabaseHelper.DATABASE_NAME;
                                SQLiteDatabase.deleteDatabase(new File(myPath));
                                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "Data Delete Successfully", Toast.LENGTH_LONG).show();

                            }
                        });

                alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                return true;
            case R.id.pdf:

                try {
                    mydb.createPdf();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), "PDF Successfully Created", Toast.LENGTH_LONG).show();
                return true;
            case R.id.viewpdf:

                openPdf(getApplication(),Environment.getExternalStorageDirectory() + File.separator + "Expenses Pdf/Expense.pdf");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getAllData() {
        txtdate = findViewById(R.id.txtdate);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy");
        final String currentDate = sdf.format(new Date());

        tvedit = findViewById(R.id.tvedit);
        tvedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DeleteActivity.class);
                startActivity(intent);
            }
        });

        mhome = findViewById(R.id.mhome);
        mentertainment = findViewById(R.id.mentertainment);
        mtravelling = findViewById(R.id.mtravelling);
        mcloth = findViewById(R.id.mcloth);
        msport = findViewById(R.id.msport);
        mincome = findViewById(R.id.mincome);
        savingamount = findViewById(R.id.savingammount);
        expenseamount = findViewById(R.id.expenseamount);
        balanceamount = findViewById(R.id.balanceamount);

        long home_count = mydb.getTotalOfAmountHome();
        mhome.setText(String.valueOf(home_count));

        long entertainment_count = mydb.getTotalOfAmountEntertainment();
        mentertainment.setText(String.valueOf(entertainment_count));

        long travelling_count = mydb.getTotalOfAmountTravelling();
        mtravelling.setText(String.valueOf(travelling_count));

        long cloth_count = mydb.getTotalOfAmountCloth();
        mcloth.setText(String.valueOf(cloth_count));

        long sport_count = mydb.getTotalOfAmountSport();
        msport.setText(String.valueOf(sport_count));

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

        savingamount.setText(String.valueOf(savingsum));

        expenseamount.setText(String.valueOf(expensesum));

        Long balamount = Long.valueOf(0);
        balamount = savingsum - expensesum;
        if (savingsum > expensesum) {
            balanceamount.setTextColor(Color.parseColor("#4CAF50"));
            balanceamount.setText(String.valueOf(balamount));

        } else if (savingsum < expensesum) {
            balanceamount.setTextColor(Color.parseColor("#F44336"));
            balanceamount.setText("" + balamount);
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
                    showMessage("Empty", "Nothing found");
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

                showMessage("All Home Expenses", buffer.toString());
            }
        });


        llshowentertainment = findViewById(R.id.llshowentertainment);
        llshowentertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getEntertainmentData();
                if (res.getCount() == 0) {
                    showMessage("Empty", "Nothing found");
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

                showMessage("All Entertainment Expenses", buffer.toString());
            }
        });

        llshowtravelling = findViewById(R.id.llshowtravelling);
        llshowtravelling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.gettTravellingData();
                if (res.getCount() == 0) {
                    showMessage("Empty", "Nothing found");
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

                showMessage("All Travelling Expenses", buffer.toString());
            }
        });

        llshowcloth = findViewById(R.id.llshowcloth);
        llshowcloth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getcClothData();
                if (res.getCount() == 0) {
                    showMessage("Empty", "Nothing found");
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

                showMessage("All Cloth Expenses", buffer.toString());
            }
        });
        llshowsports = findViewById(R.id.llshowsport);
        llshowsports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getSportData();
                if (res.getCount() == 0) {
                    showMessage("Empty", "Nothing Found ");
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

                showMessage("All Sports Expenses", buffer.toString());
            }
        });


        llshowincome = findViewById(R.id.llshowincome);
        llshowincome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getIncomeData();
                if (res.getCount() == 0) {
                    showMessage("Empty", "Nothing Found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {


                    buffer.append("Id :" + res.getString(0) + "\n");
                    buffer.append("Money :" + res.getString(2) + "\n");
                    buffer.append("Date :" + res.getString(3) + "\n");
                    buffer.append("Name :" + res.getString(1) + "\n\n");
                }

                showMessage("All Income Expenses", buffer.toString());
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
                Intent intent = new Intent(MainActivity.this, DeleteActivity.class);
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
            balanceamount.setText("" + balamount);
        } else {
            balanceamount.setTextColor(Color.parseColor("#404a57"));
            balanceamount.setText("0");
        }

        llshowhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getHomeDataDateWise(currentDate);
                if (res.getCount() == 0) {
                    showMessage("Empty", "Nothing found");
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
                    showMessage("Empty", "Nothing found");
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
                    showMessage("Empty", "Nothing found");
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
                    showMessage("Empty", "Nothing found");
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
                    showMessage("Empty", "Nothing Found ");
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
                    showMessage("Empty", "Nothing Found");
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
                Intent intent = new Intent(MainActivity.this, DeleteActivity.class);
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
            balanceamount.setText("" + balamount);
        } else {
            balanceamount.setTextColor(Color.parseColor("#404a57"));
            balanceamount.setText("0");
        }

        llshowhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getHomeDataMonthWise(currentMonth);
                if (res.getCount() == 0) {
                    showMessage("Empty", "Nothing found");
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {

                    buffer.append("Id :" + res.getString(0) + "\n");
                    buffer.append("Money :" + res.getString(2) + "\n");
                    buffer.append("Date :" + res.getString(3) + "\n");
                    buffer.append("Name :" + res.getString(1) + "\n\n");
                }

                //show all data

                showMessage("Home Expenses on " + currentMonth + " Month", buffer.toString());
            }
        });

        llshowentertainment = findViewById(R.id.llshowentertainment);
        llshowentertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getEntertainmentMonthWise(currentMonth);
                if (res.getCount() == 0) {
                    showMessage("Empty", "Nothing found");
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
                showMessage("Entertainment Expenses on " + currentMonth + " Month", buffer.toString());
            }
        });
        llshowtravelling = findViewById(R.id.llshowtravelling);
        llshowtravelling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.gettTravellingMonthWise(currentMonth);
                if (res.getCount() == 0) {
                    showMessage("Empty", "Nothing found");
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

                showMessage("Travelling Expenses on " + currentMonth + " Month", buffer.toString());
            }
        });
        llshowcloth = findViewById(R.id.llshowcloth);
        llshowcloth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getcClothMonthWise(currentMonth);
                if (res.getCount() == 0) {
                    showMessage("Empty", "Nothing found");
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
                showMessage("Cloth Expenses on " + currentMonth + " Month", buffer.toString());
            }
        });
        llshowsports = findViewById(R.id.llshowsport);
        llshowsports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getSportMonthWise(currentMonth);
                if (res.getCount() == 0) {
                    showMessage("Empty", "Nothing Found ");
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

                showMessage("Sport Expenses on " + currentMonth + " Month", buffer.toString());
            }
        });
        llshowincome = findViewById(R.id.llshowincome);
        llshowincome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getIncomeMonthWise(currentMonth);
                if (res.getCount() == 0) {
                    showMessage("Empty", "Nothing Found");
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
                showMessage("Income on " + currentMonth + " Month", buffer.toString());
            }
        });
    }

    public void getYearData() {
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
                Intent intent = new Intent(MainActivity.this, DeleteActivity.class);
                startActivity(intent);
            }
        });

        mhome = findViewById(R.id.mhome);
        long home_count = mydb.getTotalOfAmountYearWise("HOME", curryear);
        mhome.setText(String.valueOf(home_count));

        mentertainment = findViewById(R.id.mentertainment);
        long entertainment_count = mydb.getTotalOfAmountYearWise("ENTERTAINMENT", curryear);
        mentertainment.setText(String.valueOf(entertainment_count));

        mtravelling = findViewById(R.id.mtravelling);
        long travelling_count = mydb.getTotalOfAmountYearWise("TRAVELLING", curryear);
        mtravelling.setText(String.valueOf(travelling_count));

        mcloth = findViewById(R.id.mcloth);
        long cloth_count = mydb.getTotalOfAmountYearWise("CLOTH", curryear);
        mcloth.setText(String.valueOf(cloth_count));

        msport = findViewById(R.id.msport);
        long sport_count = mydb.getTotalOfAmountYearWise("SPORT", curryear);
        msport.setText(String.valueOf(sport_count));

        mincome = findViewById(R.id.mincome);
        long income_count = mydb.getTotalOfAmountYearWise("INCOME", curryear);
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
            balanceamount.setText("" + balamount);
        } else {
            balanceamount.setTextColor(Color.parseColor("#404a57"));
            balanceamount.setText("0");
        }

        llshowhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getHomeDataYearWise("HOME", yeardata);
                if (res.getCount() == 0) {
                    showMessage("Empty", "Nothing found");
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {

                    buffer.append("Id :" + res.getString(0) + "\n");
                    buffer.append("Money :" + res.getString(2) + "\n");
                    buffer.append("Date :" + res.getString(3) + "\n");
                    buffer.append("Name :" + res.getString(1) + "\n\n");
                }

                //show all data

                showMessage("Home Expenses on " + yeardata + " Year", buffer.toString());
            }
        });

        llshowentertainment = findViewById(R.id.llshowentertainment);
        llshowentertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getHomeDataYearWise("ENTERTAINMENT", yeardata);
                if (res.getCount() == 0) {
                    showMessage("Empty", "Nothing found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("Id :" + res.getString(0) + "\n");
                    buffer.append("Money :" + res.getString(2) + "\n");
                    buffer.append("Date :" + res.getString(3) + "\n");
                    buffer.append("Name :" + res.getString(1) + "\n\n");
                }
                showMessage("Entertainment Expenses on " + yeardata + " Year", buffer.toString());
            }
        });
        llshowtravelling = findViewById(R.id.llshowtravelling);
        llshowtravelling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getHomeDataYearWise("TRAVELLING", yeardata);
                if (res.getCount() == 0) {
                    showMessage("Empty", "Nothing found");
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

                showMessage("Travelling Expenses on " + yeardata + " Year", buffer.toString());
            }
        });
        llshowcloth = findViewById(R.id.llshowcloth);
        llshowcloth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getHomeDataYearWise("CLOTH", yeardata);
                if (res.getCount() == 0) {
                    showMessage("Empty", "Nothing found");
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
                showMessage("Cloth Expenses on " + yeardata + " Year", buffer.toString());
            }
        });
        llshowsports = findViewById(R.id.llshowsport);
        llshowsports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getHomeDataYearWise("SPORT", yeardata);
                if (res.getCount() == 0) {
                    showMessage("Empty", "Nothing Found ");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {

                    buffer.append("Id :" + res.getString(0) + "\n");
                    buffer.append("Money :" + res.getString(2) + "\n");
                    buffer.append("Date :" + res.getString(3) + "\n");
                    buffer.append("Name :" + res.getString(1) + "\n\n");

                }

                showMessage("Sport Expenses on " + yeardata + " Year", buffer.toString());
            }
        });
        llshowincome = findViewById(R.id.llshowincome);
        llshowincome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getHomeDataYearWise("INCOME", yeardata);
                if (res.getCount() == 0) {
                    showMessage("Empty", "Nothing Found");
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
                showMessage("Income on " + yeardata + " Year", buffer.toString());
            }
        });


    }

    public void getWeekData() {
        Calendar c1 = Calendar.getInstance(new Locale("en","USA"));
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
                Intent intent = new Intent(MainActivity.this, DeleteActivity.class);
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
            balanceamount.setText("" + balamount);
        } else {
            balanceamount.setTextColor(Color.parseColor("#404a57"));
            balanceamount.setText("0");
        }

        llshowhome = findViewById(R.id.llshowhome);
        llshowhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getHomeDataWeekWise("HOME", week1, week2);
                if (res.getCount() == 0) {
                    showMessage("Empty", "Nothing found");
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
                Cursor res = mydb.getHomeDataWeekWise("ENTERTAINMENT", week1, week2);
                if (res.getCount() == 0) {
                    showMessage("Empty", "Nothing found");
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
                Cursor res = mydb.getHomeDataWeekWise("TRAVELLING", week1, week2);
                if (res.getCount() == 0) {
                    showMessage("Empty", "Nothing found");
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
                Cursor res = mydb.getHomeDataWeekWise("CLOTH", week1, week2);
                if (res.getCount() == 0) {
                    showMessage("Empty", "Nothing found");
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
                Cursor res = mydb.getHomeDataWeekWise("SPORT", week1, week2);
                if (res.getCount() == 0) {
                    showMessage("Empty", "Nothing Found ");
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
                Cursor res = mydb.getHomeDataWeekWise("INCOME", week1, week2);
                if (res.getCount() == 0) {
                    showMessage("Empty", "Nothing Found");
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
    private void requestPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]
                {READ_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }
    @Override
    protected void onStart() {

        getAllData();

        if (flag1>0) {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {

                            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(i);
                        }
                    });
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        super.onStart();
    }
    public void openPdf(Context context, String path) {
        File file = new File(path);
        if (file.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/pdf");

            PackageManager pm = context.getPackageManager();
            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
            sendIntent.setType("application/pdf");
            Intent openInChooser = Intent.createChooser(intent, "Choose");
            List<ResolveInfo> resInfo = pm.queryIntentActivities(sendIntent, 0);
            if (resInfo.size() > 0) {
                try {
                    context.startActivity(openInChooser);
                } catch (Throwable throwable) {
                    Toast.makeText(MainActivity.this, "PDF apps are not installed \nOpen Manualy From \n Storage/Expenses Pdf/Expense.pdf", Toast.LENGTH_LONG).show();

                }
            } else {
                Toast.makeText(MainActivity.this, "PDF apps are not installed\nOpen Manualy From \n Storage/Expenses Pdf/Expense.pdf", Toast.LENGTH_LONG).show();
            }

        }
        else {
            Toast.makeText(MainActivity.this, "PDF file not exists\nPlease create PDF file", Toast.LENGTH_LONG).show();
        }

    }
}
