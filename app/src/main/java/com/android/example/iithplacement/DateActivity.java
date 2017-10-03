package com.android.example.iithplacement;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

public class DateActivity extends AppCompatActivity {

    Button notificationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        notificationButton = (Button) findViewById(R.id.notification_date);
        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext());
                Intent intent =  new Intent(getApplicationContext(),DateActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent,0);
                mBuilder.setContentIntent(pendingIntent);
                mBuilder.setSmallIcon(R.drawable.iith_logo);
                mBuilder.setContentTitle("New Announcement");
                mBuilder.setContentText("Placement cell meeting at .....");

                NotificationManager notificationManager = (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
                notificationManager.notify(001,mBuilder.build());
            }
        });
    }
}
