package com.android.example.iithplacement.sync;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Action;
import android.support.v4.content.ContextCompat;

import com.android.example.iithplacement.DateActivity;
import com.android.example.iithplacement.R;
import com.android.example.iithplacement.Utils.PrefrenceManagment;


/**
 * Created by om on 7/12/17.
 */

public class NotificationBuilding  {

    private static final int new_notification_id = 17;


    public static void clearAllNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        assert notificationManager != null;
        notificationManager.cancelAll();
    }


    public static void remindUserBecauseNewAnnouncement(Context context,String date, String event) {



        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        android.support.v7.app.NotificationCompat.Builder mBuilder = new android.support.v7.app.NotificationCompat.Builder(context);
        Intent intent = new Intent(context, DateActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,0);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.drawable.iith_logo);
        mBuilder.setContentTitle("New Announcement");
        mBuilder.setContentText(date + ":" + event);
        mBuilder.setAutoCancel(true);
        mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);

        notificationManager.notify(new_notification_id, mBuilder.build());

    }
}
