package com.android.example.iithplacement;

/**
 * Created by om on 7/12/17.
 */

public class DateEvent {

    private String mDate ,mEvent;

    public DateEvent(String title, String Author) {

        mDate = title;
        mEvent = Author;

    }

    public String getmDate() {
        return mDate;
    }

    public String getmEvent() {
        return mEvent;
    }

}
