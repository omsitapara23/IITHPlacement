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

public class EventAdapter extends ArrayAdapter<DateEvent> {

    public EventAdapter(Context context, ArrayList<DateEvent> dateList) {
        super(context, 0, dateList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.adapter_date, parent, false);
        }
        final DateEvent currentDate = getItem(position);
        TextView dateText = (TextView) listItem.findViewById(R.id.Date);
        dateText.setText(currentDate.getmDate());

        TextView eventText = (TextView) listItem.findViewById(R.id.event);
        eventText.setText(currentDate.getmEvent());

        return listItem;
    }
}