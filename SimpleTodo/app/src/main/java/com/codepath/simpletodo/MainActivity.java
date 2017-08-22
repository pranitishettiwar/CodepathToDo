package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<ListItem> todoItems;
    ItemsAdapter itemsAdapter;
    ListView lvItems;
    EditText etEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FlowManager.init(new FlowConfig.Builder(this).build());

        setContentView(R.layout.activity_main);
        populateArrayItems();

        etEditText = (EditText) findViewById(R.id.etEditText);


    }

    private void deleteItemInDB(int position) {
        ToDoItem deleteInDB = new ToDoItem();

        deleteInDB.setPosition(position);
        deleteInDB.delete();

        // Query  the table to get all the items after current deleted item, and decrement their position by 1.
        //Now the item positions on UI matches the value stored in DB for all item, except we still have last item remaining
        //Delete the last item in DB, as it's the duplicate of last-1 element
        List<ToDoItem> toDoItemList = SQLite.select().from(ToDoItem.class).where(ToDoItem_Table.position.greaterThan(position)).queryList();

        for (int i = 0; i < toDoItemList.size(); i++) {
            ToDoItem currentToDoItem = toDoItemList.get(i);
            int dbPosition = currentToDoItem.getPosition();
            currentToDoItem.setPosition(dbPosition - 1);
            currentToDoItem.save();
        }

        deleteInDB.setPosition(todoItems.size());
        deleteInDB.delete();
    }

    public void populateArrayItems() {
        // Construct the data source
        todoItems = new ArrayList<>();
        readItemsFromDB();

        // Create the adapter to convert the array to views
        itemsAdapter = new ItemsAdapter(this, todoItems);

        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.lvItems);
        listView.setAdapter(itemsAdapter);
    }

    private void readItemsFromDB() {
        // Query  whole table
        List<ToDoItem> toDoItemList = SQLite.select().from(ToDoItem.class).queryList();

        for (int i = 0; i < toDoItemList.size(); i++) {
            ListItem listItem = new ListItem(toDoItemList.get(i).getName(), "09/01/2017");
            todoItems.add(i, listItem);
        }

    }

    private void writeItemToDB() {
        int numTodoItems = todoItems.size();
        ToDoItem toDoItem = new ToDoItem();
        toDoItem.setName(todoItems.get(numTodoItems - 1).toString());
        toDoItem.setPosition(numTodoItems - 1);
        toDoItem.save();
    }

    public void onAddItem(View view) {
        ListItem listItem = new ListItem(etEditText.getText().toString(), "09/01/2017");
        itemsAdapter.add(listItem);
        etEditText.setText("");
        writeItemToDB();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            ListItem item = new ListItem(data.getExtras().getString("item"),"09/01/2017");
            int item_position = data.getIntExtra("item_position", 0);

            todoItems.set(item_position, item);
            itemsAdapter.notifyDataSetChanged();
        }
    }
}
