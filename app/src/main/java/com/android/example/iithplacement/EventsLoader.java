package com.android.example.iithplacement;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.android.example.iithplacement.Utils.FetchNotifications;

import java.util.List;

/**
 * Created by om on 8/12/17.
 */

public class EventsLoader extends AsyncTaskLoader<List<DateEvent>> {
    private String mUri;

    public EventsLoader(Context context, String uri) {
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
        List<DateEvent> datelist = FetchNotifications.fetchBooksData(mUri);
        return datelist;
    }
}