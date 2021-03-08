package com.google.android.logview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LogView3Adapter extends ArrayAdapter<Log> {

    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss.SSS");

    public LogView3Adapter(Context context, int resource, List<Log> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log log = getItem(position);
        View view;
        LogViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.layout_logview3_item, parent, false);
            viewHolder = new LogViewHolder();
            viewHolder.logText = view.findViewById(R.id.tvLogText);
            viewHolder.logDate = view.findViewById(R.id.tvLogDate);
            viewHolder.logType = view.findViewById(R.id.tvLogType);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (LogViewHolder) view.getTag();
        }

        if (log.logType == LogType.DBG) {
            setLabelTextColor("#271FE3", viewHolder.logText, viewHolder.logType, viewHolder.logDate);
        } else if (log.logType == LogType.IFO) {
            setLabelTextColor("#4D4D4D", viewHolder.logText, viewHolder.logType, viewHolder.logDate);
        } else if (log.logType == LogType.WRN) {
            setLabelTextColor("#D85800", viewHolder.logText, viewHolder.logType, viewHolder.logDate);
        } else if (log.logType == LogType.ERR) {
            setLabelTextColor("#FF0000", viewHolder.logText, viewHolder.logType, viewHolder.logDate);
        } else if (log.logType == LogType.OK) {
            setLabelTextColor("#1F860A", viewHolder.logText, viewHolder.logType, viewHolder.logDate);
        }

        viewHolder.logText.setText(log.logText);
        viewHolder.logType.setText(String.format("%-3s", log.logType.name()));
        viewHolder.logDate.setText(formatLogDate(log.logTime));
        return view;
    }

    private void setLabelTextColor(TextView textView, String color) {
        textView.setTextColor(Color.parseColor(color));
    }

    private void setLabelTextColor(String color, TextView... textViews) {
        for (TextView textview : textViews
        ) {
            setLabelTextColor(textview, color);
        }
    }

    private String formatLogDate(long date) {
        return simpleDateFormat.format(new Date(date));
    }
}
