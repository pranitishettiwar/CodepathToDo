package com.codepath.simpletodo;

/**
 * Created by praniti on 8/21/17.
 */

public class ListItem {
    private String todoName;
    private String todoDate;

    public ListItem(String todoName, String todoDate) {
        this.todoName = todoName;
        this.todoDate = todoDate;
    }

    //retrieve todo name
    public String getTodoName() {
        return todoName;
    }

    //retrieve todo due date
    public String getTodoDate() {
        return todoDate;
    }

    // set todo name
    public void setTodoName(String name) {
        todoName = name;
    }

    // set todo name
    public void setTodoDate(String date) {
        todoDate = date;
    }

}
