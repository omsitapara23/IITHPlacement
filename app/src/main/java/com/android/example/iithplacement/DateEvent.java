package com.android.example.iithplacement;

/**
 * Created by om on 7/12/17.
 */

public class DateEvent {

    private String mDate ,mEvent, mAuthority, mPostDate;

    public DateEvent(String date, String event) {

        mDate = date;
        mEvent = event;

    }

    public DateEvent(String date, String event, String authority, String postDate){
        mDate = date;
        mEvent = event;
        mAuthority = authority;
        mPostDate = postDate;

    }

    public String getmDate() {
        return mDate;
    }

    public String getmEvent() {
        return mEvent;
    }

    public String getmAuthority() {return  mAuthority;}

    public String getmPostDate() {return  mPostDate;}

}
