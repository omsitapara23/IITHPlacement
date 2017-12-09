package com.android.example.iithplacement.sync;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.example.iithplacement.DateActivity;
import com.android.example.iithplacement.DateEvent;
import com.android.example.iithplacement.Utils.AnnouncementActivity;
import com.android.example.iithplacement.Utils.FetchNotifications;
import com.android.example.iithplacement.Utils.PrefrenceManagment;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by om on 9/12/17.
 */

public class AnnouncementSync extends JobService {

    private AsyncTask mTask;
    @Override
    public boolean onStartJob(final JobParameters job) {
        mTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                Context context = AnnouncementSync.this;
                DateEvent dateEvent = FetchNotifications.fetchFirstAnnouncement(AnnouncementActivity.requestUrl);
                String last_event = PrefrenceManagment.getLast_announcement(context);
                Log.v("Got event",dateEvent.getmEvent());
                if(last_event.equals(dateEvent.getmEvent()) || dateEvent == null){

                    return null;
                }
                String date = dateEvent.getmDate();
                String[] seperatedDate = date.split("T");
                PrefrenceManagment.setLast_announcement(context,dateEvent.getmEvent());
                NotificationBuilding.remindUserBecauseNewAnnouncement(context,seperatedDate[0],dateEvent.getmEvent(),2);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                jobFinished(job, false);
            }
        };

        mTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        if (mTask != null) mTask.cancel(true);
        return true;
    }
}
