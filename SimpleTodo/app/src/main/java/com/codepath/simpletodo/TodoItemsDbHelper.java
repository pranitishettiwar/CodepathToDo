package com.codepath.simpletodo;

import android.widget.EditText;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by praniti on 8/24/17.
 */

public class TodoItemsDbHelper {

    public void deleteItemInDB(int position, ArrayList<ListItem> todoItems) {
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

    public void updateItemInDB(EditText mEditText, int itemPosition, String itemDate, String itemPriority) {
        ToDoItem itemInDB = new ToDoItem();

        //Saving the Edited value in DB
        itemInDB.setName(mEditText.getText().toString());
        itemInDB.setPosition(itemPosition);
        itemInDB.setDate(itemDate);
        itemInDB.setPriority(itemPriority);
        itemInDB.save();
    }

    public void readItemsFromDB(ArrayList<ListItem> todoItems) {
        // Query  whole table
        List<ToDoItem> toDoItemList = SQLite.select().from(ToDoItem.class).queryList();

        for (int i = 0; i < toDoItemList.size(); i++) {
            ListItem listItem = new ListItem(toDoItemList.get(i).getName(), toDoItemList.get(i).getDate(), toDoItemList.get(i).getPriority());
            todoItems.add(i, listItem);
        }

    }

    public void writeItemToDB(ArrayList<ListItem> todoItems) {
        int numTodoItems = todoItems.size();
        ToDoItem toDoItem = new ToDoItem();
        toDoItem.setName(todoItems.get(numTodoItems - 1).getTodoName().toString());
        toDoItem.setPosition(numTodoItems - 1);
        toDoItem.setDate(todoItems.get(numTodoItems - 1).getTodoDate().toString());
        toDoItem.save();
        toDoItem.setPriority(todoItems.get(numTodoItems - 1).getPriority().toString());
        toDoItem.save();
    }
}
