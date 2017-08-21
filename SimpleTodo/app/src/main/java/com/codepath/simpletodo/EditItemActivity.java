package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

public class EditItemActivity extends AppCompatActivity {

    EditText etEditItem;
    private int item_position;
    private String item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FlowManager.init(new FlowConfig.Builder(this).build());
        setContentView(R.layout.activity_edit_item);
        etEditItem = (EditText) findViewById(R.id.etEditItem);

        item = getIntent().getStringExtra("item");
        item_position = getIntent().getIntExtra("item_position", 1);
        etEditItem.setText(item);
        etEditItem.setSelection(etEditItem.getText().length());

    }

    private void updateItemInDB() {
        ToDoItem itemInDB = new ToDoItem();

        //Saving the Edited value in DB
        itemInDB.setName(etEditItem.getText().toString());
        itemInDB.setPosition(item_position);
        itemInDB.save();
    }

    public void editItem(View view) {
        updateItemInDB();
        //Sending back the data to MainActivity
        Intent i = new Intent();
        i.putExtra("item", etEditItem.getText().toString());
        i.putExtra("item_position", item_position);
        setResult(RESULT_OK, i);
        this.finish();
    }

}
