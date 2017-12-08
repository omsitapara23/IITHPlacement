package com.android.example.iithplacement.sync;

import android.content.Context;
import android.os.AsyncTask;

import com.android.example.iithplacement.DateActivity;
import com.android.example.iithplacement.DateEvent;
import com.android.example.iithplacement.Utils.FetchNotifications;
import com.android.example.iithplacement.Utils.PrefrenceManagment;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by om on 7/12/17.
 */

public class DateSyncServiceJob extends JobService {

    private AsyncTask mTask;
    @Override
    public boolean onStartJob(final JobParameters job) {
        mTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                Context context = DateSyncServiceJob.this;
                DateEvent dateEvent = FetchNotifications.fetchFirstEvent(DateActivity.requestUrl);
                String last_event = PrefrenceManagment.getLast_event_key(context);
                if(last_event.equals(dateEvent.getmEvent())){

                    return null;
                }
                PrefrenceManagment.setLast_event_key(context,dateEvent.getmEvent());
                NotificationBuilding.remindUserBecauseNewAnnouncement(context,dateEvent.getmDate(),dateEvent.getmEvent());
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
