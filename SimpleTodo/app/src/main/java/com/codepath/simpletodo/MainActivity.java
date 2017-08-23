package com.codepath.simpletodo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements EditItemDialogFragment.EditItemDialogListener {

    ArrayList<ListItem> todoItems;
    ItemsAdapter itemsAdapter;
    EditText etEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FlowManager.init(new FlowConfig.Builder(this).build());

        setContentView(R.layout.activity_main);
        populateArrayItems();

        etEditText = (EditText) findViewById(R.id.etEditText);
    }

    // Called by ItemsAdapter
    public void todoItemLongClicked(int position) {
        deleteItemInDB(position);
    }

    //Sending data to EditItemActivity
    public void sendData(int position) {
        {
            EditItemDialogFragment dialogFragment = new EditItemDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putString("item", todoItems.get(position).getToDoName().toString());
            bundle.putInt("item_position", position);

            dialogFragment.setArguments(bundle);
            dialogFragment.show(getFragmentManager(), "fragment_edit_item");
        }
    }

    private void deleteItemInDB(int position) {
        // Delete the current item
        ToDoItem deleteInDB = new ToDoItem();
        deleteInDB.setPosition(position);
        deleteInDB.delete();

        // Query the table to get all the items after current deleted item, and decrement their position by 1.
        //Now the item positions on UI matches the value stored in DB for all items, except we still have last item remaining
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
        toDoItem.setName(todoItems.get(numTodoItems - 1).getToDoName().toString());
        toDoItem.setPosition(numTodoItems - 1);
        toDoItem.save();
    }

    public void onAddItem(View view) {
        ListItem listItem = new ListItem(etEditText.getText().toString(), "09/01/2017");
        Toast.makeText(this, "Item updated:" + " " + etEditText.getText().toString(), Toast.LENGTH_SHORT).show();
        itemsAdapter.add(listItem);
        etEditText.setText("");
        writeItemToDB();
    }

    @Override
    public void onFinishEditDialog(String item_text, int item_position) {
        todoItems.get(item_position).setToDoName(item_text);
        itemsAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Item updated:" + " " + item_text, Toast.LENGTH_SHORT).show();
    }

}
