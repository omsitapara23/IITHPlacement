package com.android.example.iithplacement;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.AsyncTaskLoader;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.example.iithplacement.Utils.FetchNotifications;
import com.android.example.iithplacement.sync.NotificationBuilding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DateActivity extends AppCompatActivity {

    Button notificationButton;
    Button calanderButton;
    private static final int CALANDER_PERMISSION_REQUEST_CODE = 22;
    String startDate = "08 oct 2017";
    public static final String requestUrl = "https://api.myjson.com/bins/13ffa7";
    private EventAdapter mAdapter;
    private ListView listView;
    private ArrayList<DateEvent> dateList;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        mAdapter = new EventAdapter(this, new ArrayList<DateEvent>());
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(mAdapter);



        notificationButton = (Button) findViewById(R.id.notification_date);
        calanderButton = (Button) findViewById(R.id.calander_button);
        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationBuilding.remindUserBecauseNewAnnouncement(DateActivity.this,"898989","hehehe");
            }
        });

        calanderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCalanderUpdate(v);
            }
        });

        dateasyncTask event = new dateasyncTask();
        event.execute();



    }

    private void updateUi(ArrayList<DateEvent> events){
        mAdapter.addAll(events);

    }

    private class dateasyncTask extends AsyncTask<Void, Void, ArrayList<DateEvent>>{



        @Override
        protected ArrayList<DateEvent> doInBackground(Void... voids) {
            dateList = FetchNotifications.fetchBooksData(requestUrl);
            return dateList;
        }

        @Override
        protected void onPostExecute(ArrayList<DateEvent> dateEvents) {
            updateUi(dateEvents);
        }
    }

    public void onCalanderUpdate(View v){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //We are on M or above so we need to ask for runtime permissions
            if (checkSelfPermission(Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
                addCalanderEvent();

            } else {
                // we are here if we do not all ready have permissions
                String[] permisionRequest = {Manifest.permission.READ_CALENDAR,Manifest.permission.WRITE_CALENDAR};
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

    public void addCalanderEvent(){
        long startTime,endTime;


        Date date = null;
        try {
            date = new SimpleDateFormat("dd MMM yyyy").parse(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        startTime=date.getTime();
        endTime = startTime + 1000*60*60;


        Intent intent = new Intent(Intent.ACTION_INSERT).
                setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,startTime)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME,endTime)
                .putExtra(CalendarContract.Events.TITLE,"Human Library Event");
        startActivity(intent);

    }

}

