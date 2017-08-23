package com.codepath.simpletodo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements EditItemDialogFragment.EditItemDialogListener {

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


<<<<<<< HEAD
=======
        //Remove item from list when user long tapped
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                todoItems.remove(position);
                aToDoAdapter.notifyDataSetChanged();
                deleteItemInDB(position);
                return true;
            }
        });

        //Update item from list when user tapped on the item
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                sendData(position);
            }

        });
>>>>>>> 5c587688368f6ec6b15cc1019e9150e8523f208c
    }

    //Sending data to EditItemActivity
    private void sendData(int position) {
        {
            EditItemDialogFragment dialogFragment = new EditItemDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putString("item", todoItems.get(position).toString());
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
<<<<<<< HEAD

        // Create the adapter to convert the array to views
        itemsAdapter = new ItemsAdapter(this, todoItems);

        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.lvItems);
        listView.setAdapter(itemsAdapter);
=======
        aToDoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView textView = (TextView) view.findViewById(android.R.id.text1);

                textView.setTextColor(Color.WHITE);
                textView.setBackgroundColor(Color.DKGRAY);

                return view;
            }
        };
>>>>>>> 5c587688368f6ec6b15cc1019e9150e8523f208c
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
<<<<<<< HEAD
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            ListItem item = new ListItem(data.getExtras().getString("item"),"09/01/2017");
            int item_position = data.getIntExtra("item_position", 0);

            todoItems.set(item_position, item);
            itemsAdapter.notifyDataSetChanged();
        }
=======
    public void onFinishEditDialog(String item_text, int item_position) {
        todoItems.set(item_position, item_text);
        aToDoAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Item updated:" + " " + item_text, Toast.LENGTH_SHORT).show();
>>>>>>> 5c587688368f6ec6b15cc1019e9150e8523f208c
    }

}
