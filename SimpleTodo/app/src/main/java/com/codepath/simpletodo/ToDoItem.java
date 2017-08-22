package com.codepath.simpletodo;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by praniti on 8/20/17.
 */

//Table name: ToDoItem
@Table(database = ToDoDatabase.class)
public class ToDoItem extends BaseModel {
    @Column
    @PrimaryKey
    private int position;

    @Column
    private String name;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

