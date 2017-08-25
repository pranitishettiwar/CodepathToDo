package com.codepath.simpletodo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements EditItemDialogFragment.EditItemDialogListener {

    ArrayList<ListItem> todoItems;
    ItemsAdapter itemsAdapter;
    EditText mEditText;
    String currentDate;
    TodoItemsDbHelper todoItemsDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FlowManager.init(new FlowConfig.Builder(this).build());

        setContentView(R.layout.activity_main);
        populateArrayItems();

        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        Date dateobj = new Date();
        currentDate = df.format(dateobj);

        mEditText = (EditText) findViewById(R.id.etEditText);
    }

    // Called by ItemsAdapter
    public void todoItemLongClicked(int position) {
        //Delete selected item from DB
        todoItemsDbHelper.deleteItemInDB(position, todoItems);
    }

    //Sending data to EditItemActivity
    public void sendData(int position) {
        {
            EditItemDialogFragment dialogFragment = new EditItemDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putString("item", todoItems.get(position).getTodoName().toString());
            bundle.putString("itemDate", todoItems.get(position).getTodoDate().toString());
            bundle.putInt("itemPosition", position);

            dialogFragment.setArguments(bundle);
            dialogFragment.show(getFragmentManager(), "fragment_edit_item");
        }
    }

    public void populateArrayItems() {
        // Construct the data source
        todoItems = new ArrayList<>();

        //Read all the items from the DB
        todoItemsDbHelper = new TodoItemsDbHelper();
        todoItemsDbHelper.readItemsFromDB(todoItems);

        // Create the adapter to convert the array to views
        itemsAdapter = new ItemsAdapter(this, todoItems);

        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.lvItems);
        listView.setAdapter(itemsAdapter);
    }


    public void onAddItem(View view) {
        ListItem listItem = new ListItem(mEditText.getText().toString(), "added on " + currentDate);
        Toast.makeText(this, "Item Added:" + " " + mEditText.getText().toString(), Toast.LENGTH_SHORT).show();
        itemsAdapter.add(listItem);
        mEditText.setText("");

        //Write newly added item in DB
        todoItemsDbHelper.writeItemToDB(todoItems);
    }

    @Override
    public void onFinishEditDialog(String itemText, int itemPosition) {
        todoItems.get(itemPosition).setTodoName(itemText);
        itemsAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Item Updated:" + " " + itemText, Toast.LENGTH_SHORT).show();
    }

}
