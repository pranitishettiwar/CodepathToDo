package com.codepath.simpletodo;

import java.util.ArrayList;

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

//    public static ArrayList<ListItem> getListItem() {
//        ArrayList<ListItem> lists = new ArrayList<ListItem>();
//        lists.add(new ListItem("Milk","7/12/2017"));
//        return lists;
//    }

}
