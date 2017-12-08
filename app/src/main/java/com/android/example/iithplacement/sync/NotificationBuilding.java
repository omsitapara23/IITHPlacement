package com.android.example.iithplacement.sync;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import com.android.example.iithplacement.DateActivity;
import com.android.example.iithplacement.R;


/**
 * Created by om on 7/12/17.
 */

public class NotificationBuilding {

    private static int new_notification_id = 17;
    private static final int ACTION_IGNORE_PENDING_INTENT_ID = 3114;


    public static void clearAllNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        assert notificationManager != null;
        notificationManager.cancelAll();
    }


    public static void remindUserBecauseNewAnnouncement(Context context, String date, String event) {


        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        Intent intent = new Intent(context, DateActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.drawable.iith_logo);
        mBuilder.setContentTitle("New Announcement");
        mBuilder.setContentText(date + ":" + event);
        mBuilder.setAutoCancel(true);
        mBuilder.setVibrate(new long[]{1000, 1000});
        mBuilder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);

        mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);

        notificationManager.notify(new_notification_id, mBuilder.build());
        new_notification_id++;
        if (new_notification_id > 100)
            new_notification_id = 17;

    }

}
