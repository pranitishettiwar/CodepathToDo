package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    EditText etEditItem;
    private int item_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        etEditItem = (EditText) findViewById(R.id.etEditItem);

        String item = getIntent().getStringExtra("item");
        item_position = getIntent().getIntExtra("item_position", 1);
        etEditItem.setText(item);
        etEditItem.setSelection(etEditItem.getText().length());
    }

    public void editItem(View view) {
        //Sending back the data to MainActivity
        Intent i = new Intent();
        i.putExtra("item", etEditItem.getText().toString());
        i.putExtra("item_position", item_position);
        setResult(RESULT_OK, i);
        this.finish();
    }

}
