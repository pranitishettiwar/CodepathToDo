package com.codepath.simpletodo;

/**
 * Created by praniti on 8/21/17.
 */

public class ListItem {
    private String todoName;
    private String todoDate;
    private String todoPriority;

    public ListItem(String todoName, String todoDate, String todoPriority) {
        this.todoName = todoName;
        this.todoDate = todoDate;
        this.todoPriority = todoPriority;
    }

    //retrieve todo name
    public String getTodoName() {
        return todoName;
    }

    //retrieve todo due date
    public String getTodoDate() {
        return todoDate;
    }

    //retrieve todo priority
    public String getPriority() {
        return todoPriority;
    }

    // set todo name
    public void setTodoName(String name) {
        todoName = name;
    }

    // set todo date
    public void setTodoDate(String date) {
        todoDate = date;
    }

    // set todo priority
    public void setTodoPriority(String priority) {
        todoPriority = priority;
    }
}
