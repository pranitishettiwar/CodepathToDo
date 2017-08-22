package com.codepath.simpletodo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by praniti on 8/21/17.
 */

public class ItemsAdapter extends ArrayAdapter<ListItem> implements View.OnClickListener, View.OnLongClickListener {
    public ItemsAdapter(Context context, ArrayList<ListItem> listItem) {
        super(context, 0, listItem);
    }

    ArrayList<ListItem> todoItems;
    ListView lvItems;

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

        tvToDoName.setOnClickListener(this);
        tvToDoName.setOnLongClickListener(this);
        return convertView;

    }

    @Override
    public boolean onLongClick(View view) {
        int position1 = (Integer) view.getTag();
        todoItems.remove(position1);
        //aToDoAdapter.notifyDataSetChanged();
        deleteItemInDB(position1);
        return true;
    }

    @Override
    public void onClick(View view) {
        //int position1 = (Integer) view.getTag();
        Intent i = new Intent(getContext(), EditItemActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        view.getContext().startActivity(i);

        //Intent i = view.getContext().startActivity(new Intent(activity, EditItemActivity.class));
        //i.putExtra("item", todoItems.get(position1).toString());
        //i.putExtra("item_position", position1);
        //startActivityForResult(i, 1);
    }


    private void deleteItemInDB(int position1) {
        ToDoItem deleteInDB = new ToDoItem();

        deleteInDB.setPosition(position1);
        deleteInDB.delete();

        // Query  the table to get all the items after current deleted item, and decrement their position by 1.
        //Now the item positions on UI matches the value stored in DB for all item, except we still have last item remaining
        //Delete the last item in DB, as it's the duplicate of last-1 element
        List<ToDoItem> toDoItemList = SQLite.select().from(ToDoItem.class).where(ToDoItem_Table.position.greaterThan(position1)).queryList();

        for (int i = 0; i < toDoItemList.size(); i++) {
            ToDoItem currentToDoItem = toDoItemList.get(i);
            int dbPosition = currentToDoItem.getPosition();
            currentToDoItem.setPosition(dbPosition - 1);
            currentToDoItem.save();
        }

        deleteInDB.setPosition(todoItems.size());
        deleteInDB.delete();
    }
}
