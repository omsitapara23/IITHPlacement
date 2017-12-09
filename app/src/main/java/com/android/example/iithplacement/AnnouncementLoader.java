package com.android.example.iithplacement;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.android.example.iithplacement.Utils.FetchNotifications;

import java.util.List;

/**
 * Created by om on 9/12/17.
 */

public class AnnouncementLoader extends AsyncTaskLoader<List<DateEvent>> {
    private String mUri;

    public AnnouncementLoader(Context context, String uri) {
        super(context);
        mUri = uri;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<DateEvent> loadInBackground() {
        if (mUri == null) {
            return null;
        }
        List<DateEvent> datelist = FetchNotifications.fetchAnnouncementData(mUri);
        return datelist;
    }
}
