package com.android.example.iithplacement;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.example.iithplacement.Utils.FetchNotifications;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<DateEvent>> {

    public static final String requestUrl = "https://script.googleusercontent.com/macros/echo?user_content_key=6dK352YL0-VXljfSGJ7_WvFRnps4ttnLaLMsBLQj8YAy0Fe75SDVIxOSO32Z_KFOXazyqcgU_8C0Yaq-NqD2iFuV-SqKTPgDOJmA1Yb3SEsKFZqtv3DaNYcMrmhZHmUMWojr9NvTBuBLhyHCd5hHa1ZsYSbt7G4nMhEEDL32U4DxjO7V7yvmJPXJTBuCiTGh3rUPjpYM_V0PJJG7TIaKp8T7yeZVoihxBHejfQBMmjnvlDxeNvazTxXdNnh2t8fdQyhlPNUog2oYn3GcTCMX9sKiW3k6MDkf31SIMZH6H4k&lib=MbpKbbfePtAVndrs259dhPT7ROjQYJ8yx";
    private static final int CALANDER_PERMISSION_REQUEST_CODE = 22;
    Button notificationButton;
    Button calanderButton;
    String startDate = "08 oct 2017";
    private static final int Events_LOADER_ID = 10;
    private EventAdapter mAdapter;
    private ListView listView;
    private ArrayList<DateEvent> dateList;
    private ImageView noInternet;
    private ImageView loadingimage;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        noInternet = (ImageView) findViewById(R.id.noInternetImage);
        loadingimage = (ImageView) findViewById(R.id.loadingImage);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            displayInfo();

        } else {
            View locationIndicator = (View) findViewById(R.id.loading_indicator);
            locationIndicator.setVisibility(View.GONE);
            noInternet.setVisibility(View.VISIBLE);
        }

        ActionBar actionBar = this.getSupportActionBar();

        // Set the action bar back button to look like an up button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }



    }

    @Override
    protected void onStart() {
        displayInfo();
        super.onStart();
    }



    public void displayInfo() {
        mAdapter = new EventAdapter(this, new ArrayList<DateEvent>());
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(mAdapter);

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(Events_LOADER_ID, null, this);


    }

    public void onCalanderUpdate(View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //We are on M or above so we need to ask for runtime permissions
            if (checkSelfPermission(Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
                addCalanderEvent();

            } else {
                // we are here if we do not all ready have permissions
                String[] permisionRequest = {Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR};
                requestPermissions(permisionRequest, CALANDER_PERMISSION_REQUEST_CODE);
            }
        } else {
            //We are on an older devices so we dont have to ask for runtime permissions
            addCalanderEvent();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CALANDER_PERMISSION_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            //We got a GO from the user
            addCalanderEvent();
        } else {
            Toast.makeText(this, "Permissions not granted", Toast.LENGTH_LONG).show();
        }
    }

    public void addCalanderEvent() {
        long startTime, endTime;


        Date date = null;
        try {
            date = new SimpleDateFormat("dd MMM yyyy").parse(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        startTime = date.getTime();
        endTime = startTime + 1000 * 60 * 60;


        Intent intent = new Intent(Intent.ACTION_INSERT).
                setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime)
                .putExtra(CalendarContract.Events.TITLE, "Human Library Event");
        startActivity(intent);

    }

    @Override
    public Loader<List<DateEvent>> onCreateLoader(int id, Bundle args) {
        return new EventsLoader(DateActivity.this, requestUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<DateEvent>> loader, List<DateEvent> data) {


        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
        loadingimage.setVisibility(View.GONE);

        if (mAdapter != null) {
            mAdapter.clear();
        }
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<DateEvent>> loader) {
        mAdapter.clear();

    }
}

