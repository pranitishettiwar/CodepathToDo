package com.codepath.simpletodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by praniti on 8/21/17.
 */

public class ItemsAdapter extends ArrayAdapter<ListItem> {
    public ItemsAdapter(Context context, ArrayList<ListItem> listItem) {
        super(context, 0, listItem);
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
        // Return the completed view to render on screen
        return convertView;
    }

}
