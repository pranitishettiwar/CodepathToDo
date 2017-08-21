package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> todoItems;
    ArrayAdapter<String> aToDoAdapter;
    ListView lvItems;
    EditText etEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FlowManager.init(new FlowConfig.Builder(this).build());

        setContentView(R.layout.activity_main);
        populateArrayItems();

        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(aToDoAdapter);
        etEditText = (EditText) findViewById(R.id.etEditText);

        //Remove item from list when user long tapped
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                todoItems.remove(position);
                aToDoAdapter.notifyDataSetChanged();
                deleteItem(position);
                return true;
            }
        });

        //Update item from list when user tapped on the item
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Sending data to EditItemActivity
                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                i.putExtra("item", todoItems.get(position).toString());
                i.putExtra("item_position", position);
                startActivityForResult(i, 1);
            }
        });
    }

    private void deleteItem(int position) {
        ToDoItem deleteInDB = new ToDoItem();

        deleteInDB.setPosition(position);
        deleteInDB.delete();

        //Decrement DB item position by 1
        // Query  the table to get all the items after current deleted item
        List<ToDoItem> toDoItemList = SQLite.select().from(ToDoItem.class).where(ToDoItem_Table.position.greaterThan(position)).queryList();

        for (int i = 0; i < toDoItemList.size(); i++) {
            ToDoItem currentToDoItem = toDoItemList.get(i);
            int dbPosition = currentToDoItem.getPosition();

            currentToDoItem.setPosition(dbPosition - 1);
            currentToDoItem.save();
        }

        //Delete the last item in DB
        deleteInDB.setPosition(todoItems.size());
        deleteInDB.delete();
    }

    public void populateArrayItems() {
        readItems();
        aToDoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);
    }

    private void readItems() {
        todoItems = new ArrayList<String>();

        // Query  whole table
        List<ToDoItem> toDoItemList = SQLite.select().from(ToDoItem.class).queryList();

        for (int i = 0; i < toDoItemList.size(); i++) {
            todoItems.add(i, toDoItemList.get(i).getName());
        }

    }

    private void writeItem() {
        int numTodoItems = todoItems.size();
        ToDoItem toDoItem = new ToDoItem();
        toDoItem.setName(todoItems.get(numTodoItems - 1).toString());
        toDoItem.setPosition(numTodoItems - 1);
        toDoItem.save();
    }

    public void onAddItem(View view) {
        aToDoAdapter.add(etEditText.getText().toString());
        etEditText.setText("");
        writeItem();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String item = data.getExtras().getString("item");
            int item_position = data.getIntExtra("item_position", 0);

            todoItems.set(item_position, item);
            aToDoAdapter.notifyDataSetChanged();
        }
    }
}
