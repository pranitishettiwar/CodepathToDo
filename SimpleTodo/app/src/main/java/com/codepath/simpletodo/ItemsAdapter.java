package com.codepath.simpletodo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by praniti on 8/21/17.
 */

public class ItemsAdapter extends ArrayAdapter<ListItem> implements View.OnClickListener, View.OnLongClickListener {

    MainActivity activity;

    public ItemsAdapter(Context context, ArrayList<ListItem> listItem) {
        super(context, 0, listItem);
        activity = (MainActivity) context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        ListItem listItem = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        // Lookup view for data population
        TextView tvToDoName = (TextView) convertView.findViewById(R.id.tvToDoName);
        TextView tvToDoDate = (TextView) convertView.findViewById(R.id.tvToDoDate);

        // Populate the data into the template view using the data object
        tvToDoName.setText(listItem.getToDoName());
        tvToDoDate.setText(listItem.getToDoDate());

        //Call Click Listeners on the current row
        convertView.setOnLongClickListener(this);
        convertView.setOnClickListener(this);

        // Set view background color and text color
        convertView.setBackgroundColor(Color.DKGRAY);
        tvToDoName.setTextColor(Color.WHITE);
        tvToDoDate.setTextColor(Color.WHITE);

        convertView.setTag(position);

        return convertView;
    }

    @Override
    public boolean onLongClick(View view) {
        int position = (Integer) view.getTag();
        // Also remove the current item
        remove(getItem(position));
        activity.todoItemLongClicked(position);
        return true;
    }

    @Override
    public void onClick(View view) {
        int position = (Integer) view.getTag();
        activity.sendData(position);
    }

}
