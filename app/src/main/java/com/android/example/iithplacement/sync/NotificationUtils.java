package com.android.example.iithplacement.sync;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;

/**
 * Created by om on 7/12/17.
 */

public class NotificationUtils {

    private static final int REMINDER_INTERVAL_MINUTES = 1;
    private static final int REMINDER_INTERVAL_SECONDS = (int) (TimeUnit.MINUTES.toSeconds(REMINDER_INTERVAL_MINUTES));
    private static final int SYNC_FLEXTIME_SECONDS = REMINDER_INTERVAL_SECONDS;

    private static final String REMINDER_JOB_TAG = "new_announcement_tag";
    private static final String ANNOUNCEMENT_JOB_TAG = "new_announcement";

    private static boolean sInitialized;
    private static boolean issInitializedann;

    synchronized public static void scheduleNotificationRemainder(Context context) {


        // COMPLETED (17) If the job has already been initialized, return
        if (sInitialized) return;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            // COMPLETED (18) Create a new GooglePlayDriver
            Driver driver = new GooglePlayDriver(context);
            // COMPLETED (19) Create a new FirebaseJobDispatcher with the driver
            FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

            // COMPLETED (20) Use FirebaseJobDispatcher's newJobBuilder method to build a job which:
            // - has WaterReminderFirebaseJobService as it's service
            // - has the tag REMINDER_JOB_TAG
            // - only triggers if the device is charging
            // - has the lifetime of the job as forever
            // - has the job recur
            // - occurs every 15 minutes with a window of 15 minutes. You can do this using a
            //   setTrigger, passing in a Trigger.executionWindow
            // - replaces the current job if it's already running
            // Finally, you should build the job.
        /* Create the Job to periodically create reminders to drink water */
            Job constraintReminderJob = dispatcher.newJobBuilder()
                /* The Service that will be used to write to preferences */
                    .setService(DateSyncServiceJob.class)
                /*
                 * Set the UNIQUE tag used to identify this Job.
                 */
                    .setTag(REMINDER_JOB_TAG)
                /*
                 * Network constraints on which this Job should run. In this app, we're using the
                 * device charging constraint so that the job only executes if the device is
                 * charging.
                 *

                 * In a normal app, it might be a good idea to include a preference for this,
                 * as different users may have different preferences on when you should be
                 * syncing your application's data.
                 */
                .setConstraints(Constraint.ON_ANY_NETWORK)
                /*
                 * setLifetime sets how long this job should persist. The options are to keep the
                 * Job "forever" or to have it die the next time the device boots up.
                 */
                    .setLifetime(Lifetime.FOREVER)
                /*
                 * We want these reminders to continuously happen, so we tell this Job to recur.
                 */
                    .setRecurring(true)
                /*
                 * We want the reminders to happen every 15 minutes or so. The first argument for
                 * Trigger class's static executionWindow method is the start of the time frame
                 * when the
                 * job should be performed. The second argument is the latest point in time at
                 * which the data should be synced. Please note that this end time is not
                 * guaranteed, but is more of a guideline for FirebaseJobDispatcher to go off of.
                 */
                    .setTrigger(Trigger.executionWindow(
                            REMINDER_INTERVAL_SECONDS,
                            REMINDER_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
                /*
                 * If a Job with the tag with provided already exists, this new job will replace
                 * the old one.
                 */
                    .setReplaceCurrent(true)
                /* Once the Job is ready, call the builder's build method to return the Job */
                    .build();

            // COMPLETED (21) Use dispatcher's schedule method to schedule the job
        /* Schedule the Job with the dispatcher */
            dispatcher.schedule(constraintReminderJob);

            // COMPLETED (22) Set sInitialized to true to mark that we're done setting up the job
        /* The job has been initialized */
            sInitialized = true;

        }else
        {
            return;
        }

    }

    synchronized public static void scheduleAnnouncementRemainder(Context context) {


        // COMPLETED (17) If the job has already been initialized, return
        if (issInitializedann) return;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            // COMPLETED (18) Create a new GooglePlayDriver
            Driver driver = new GooglePlayDriver(context);
            // COMPLETED (19) Create a new FirebaseJobDispatcher with the driver
            FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

            // COMPLETED (20) Use FirebaseJobDispatcher's newJobBuilder method to build a job which:
            // - has WaterReminderFirebaseJobService as it's service
            // - has the tag REMINDER_JOB_TAG
            // - only triggers if the device is charging
            // - has the lifetime of the job as forever
            // - has the job recur
            // - occurs every 15 minutes with a window of 15 minutes. You can do this using a
            //   setTrigger, passing in a Trigger.executionWindow
            // - replaces the current job if it's already running
            // Finally, you should build the job.
        /* Create the Job to periodically create reminders to drink water */
            Job constraintReminderJob = dispatcher.newJobBuilder()
                /* The Service that will be used to write to preferences */
                    .setService(AnnouncementSync.class)
                /*
                 * Set the UNIQUE tag used to identify this Job.
                 */
                    .setTag(ANNOUNCEMENT_JOB_TAG)
                /*
                 * Network constraints on which this Job should run. In this app, we're using the
                 * device charging constraint so that the job only executes if the device is
                 * charging.
                 *

                 * In a normal app, it might be a good idea to include a preference for this,
                 * as different users may have different preferences on when you should be
                 * syncing your application's data.
                 */
                    .setConstraints(Constraint.ON_ANY_NETWORK)
                /*
                 * setLifetime sets how long this job should persist. The options are to keep the
                 * Job "forever" or to have it die the next time the device boots up.
                 */
                    .setLifetime(Lifetime.FOREVER)
                /*
                 * We want these reminders to continuously happen, so we tell this Job to recur.
                 */
                    .setRecurring(true)
                /*
                 * We want the reminders to happen every 15 minutes or so. The first argument for
                 * Trigger class's static executionWindow method is the start of the time frame
                 * when the
                 * job should be performed. The second argument is the latest point in time at
                 * which the data should be synced. Please note that this end time is not
                 * guaranteed, but is more of a guideline for FirebaseJobDispatcher to go off of.
                 */
                    .setTrigger(Trigger.executionWindow(
                            REMINDER_INTERVAL_SECONDS,
                            REMINDER_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
                /*
                 * If a Job with the tag with provided already exists, this new job will replace
                 * the old one.
                 */
                    .setReplaceCurrent(true)
                /* Once the Job is ready, call the builder's build method to return the Job */
                    .build();

            // COMPLETED (21) Use dispatcher's schedule method to schedule the job
        /* Schedule the Job with the dispatcher */
            dispatcher.schedule(constraintReminderJob);

            // COMPLETED (22) Set sInitialized to true to mark that we're done setting up the job
        /* The job has been initialized */
            issInitializedann = true;

        }else
        {
            return;
        }

    }


}
