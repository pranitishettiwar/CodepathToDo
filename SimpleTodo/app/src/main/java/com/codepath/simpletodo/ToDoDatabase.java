package com.codepath.simpletodo;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by praniti on 8/20/17.
 */

@Database(name = ToDoDatabase.NAME, version = ToDoDatabase.VERSION)
public class ToDoDatabase {
    public static final String NAME = "ToDoDatabase";

    public static final int VERSION = 1;


}
