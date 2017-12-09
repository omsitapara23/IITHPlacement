package com.android.example.iithplacement.Utils;

import android.Manifest;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.example.iithplacement.AnnouncementAdapter;
import com.android.example.iithplacement.AnnouncementLoader;
import com.android.example.iithplacement.DateActivity;
import com.android.example.iithplacement.DateEvent;
import com.android.example.iithplacement.EventAdapter;
import com.android.example.iithplacement.EventsLoader;
import com.android.example.iithplacement.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AnnouncementActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<DateEvent>> {

    private AnnouncementAdapter mAdapter;
    private ListView listView;
    private static final int ANNOUNCEMENT_LOADER_ID = 2115;
    private static final int CALANDER_PERMISSION_REQUEST_CODE = 22;
    public static String requestUrl ="https://script.google.com/macros/s/AKfycbygukdW3tt8sCPcFDlkMnMuNu9bH5fpt7bKV50p2bM/exec?id=1RPpl6WY_T3Stn2-FG5fvaM9EYVmcMJ3DluJXZiaQj1Y&sheet=Sheet1";
    public static String formUrl = "https://docs.google.com/forms/d/e/1FAIpQLSeu_8L8i3zIVZRS2jYpgKrgGvnqECnebbU5GX2UGbf2Lkr4OQ/viewform";
    private ImageView noInternet;
    private ImageView loadingimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri=Uri.parse(formUrl);
                Intent intent=new Intent(Intent.ACTION_VIEW,uri);
                if(intent.resolveActivity(getPackageManager())!=null){
                    startActivity(intent);
                }
            }
        });

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
        mAdapter = new AnnouncementAdapter(this, new ArrayList<DateEvent>());
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(mAdapter);

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(ANNOUNCEMENT_LOADER_ID, null, this);


    }

    public void onCalanderUpdate(View v, String date, String event) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //We are on M or above so we need to ask for runtime permissions
            if (checkSelfPermission(Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
                addCalanderEvent(date, event);

            } else {
                // we are here if we do not all ready have permissions
                String[] permisionRequest = {Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR};
                requestPermissions(permisionRequest, CALANDER_PERMISSION_REQUEST_CODE);
            }
        } else {
            //We are on an older devices so we dont have to ask for runtime permissions
            addCalanderEvent(date, event);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CALANDER_PERMISSION_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            //We got a GO from the user
            //addCalanderEvent();
        } else {
            Toast.makeText(this, "Permissions not granted", Toast.LENGTH_LONG).show();
        }
    }

    public void addCalanderEvent(String indate, String event) {
        long startTime, endTime;


        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(indate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert date != null;
        startTime = date.getTime() + 1000*60*60*8;
        endTime = startTime + 1000 * 60 * 60;


        Intent intent = new Intent(Intent.ACTION_INSERT).
                setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime)
                .putExtra(CalendarContract.Events.TITLE, event);
        startActivity(intent);

    }

    @Override
    public Loader<List<DateEvent>> onCreateLoader(int id, Bundle args) {
        return new AnnouncementLoader(AnnouncementActivity.this, requestUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<DateEvent>> loader, final List<DateEvent> data) {


        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
        loadingimage.setVisibility(View.GONE);

        if (mAdapter != null) {
            mAdapter.clear();
        }
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                assert data != null;
                onCalanderUpdate(view, data.get(position).getmDate(), data.get(position).getmEvent());
            }
        });

    }

    @Override
    public void onLoaderReset(Loader<List<DateEvent>> loader) {
        mAdapter.clear();

    }

}
