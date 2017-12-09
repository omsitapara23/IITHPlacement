package com.android.example.iithplacement;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.example.iithplacement.DateEvent;

import java.util.ArrayList;

public class AnnouncementAdapter extends ArrayAdapter<DateEvent> {

    public AnnouncementAdapter(Context context, ArrayList<DateEvent> dateList) {
        super(context, 0, dateList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.adapter_announcement, parent, false);
        }
        final DateEvent currentDate = getItem(position);
        TextView dateText = (TextView) listItem.findViewById(R.id.Date);
        String date = currentDate.getmDate();
        String[] seperatedDate = date.split("T");
        dateText.setText("Date : " + seperatedDate[0]);

        TextView eventText = (TextView) listItem.findViewById(R.id.event);
        eventText.setText("Event : " +currentDate.getmEvent());

        TextView postText = (TextView) listItem.findViewById(R.id.postdate);
        String postDate = currentDate.getmPostDate();
        String[] postDatesep = postDate.split("T");
        postText.setText("Posted on : " +postDatesep[0]);

        TextView authorityText = (TextView) listItem.findViewById(R.id.authority);
        authorityText.setText("Posted by : " +currentDate.getmAuthority());

        return listItem;
    }
}