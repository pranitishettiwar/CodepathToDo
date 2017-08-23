package com.codepath.simpletodo;

/**
 * Created by praniti on 8/21/17.
 */

public class ListItem {
    private String todoname;
    private String tododate;

    public ListItem(String todoname, String tododate) {
        this.todoname = todoname;
        this.tododate = tododate;
    }

    //retrieve todo name
    public String getToDoName() {
        return todoname;
    }

    //retrieve todo due date
    public String getToDoDate() {
        return tododate;
    }

    // set todo name
    public void setToDoName(String name) {
        todoname = name;
    }

    // set todo name
    public void setToDoDate(String date) {
        tododate = date;
    }

}
