package com.codepath.simpletodo;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;


/**
 * Created by praniti on 8/19/17.
 */

public class EditItemDialogFragment extends DialogFragment {

    private EditText mEditText;
    private Button mbtn;
    private int item_position;

    public interface EditItemDialogListener {
        void onFinishEditDialog(String inputText, int position);
    }

    public EditItemDialogFragment() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_item, container, false);

        // Set title for this dialog
        getDialog().setTitle("Edit Item");

        //Get field from view
        mEditText = (EditText) view.findViewById(R.id.etEditItem);
        mbtn = (Button) view.findViewById(R.id.btnSave);

        //Unpack data from Bundle
        Bundle bundle = getArguments();
        String item = bundle.getString("item");
        item_position = bundle.getInt("item_position", 1);
        mEditText.setText(item);
        mEditText.setSelection(mEditText.getText().length());

        // Show soft keyboard automatically and request focus to field
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateItemInDB();

                // Return input text back to activity through the implemented listener
                EditItemDialogListener listener = (EditItemDialogListener) getActivity();
                listener.onFinishEditDialog(mEditText.getText().toString(), item_position);

                //Close the dialog and return back to the parent activity
                dismiss();
            }
        });

        return view;
    }

    private void updateItemInDB() {
        ToDoItem itemInDB = new ToDoItem();

        //Saving the Edited value in DB
        itemInDB.setName(mEditText.getText().toString());
        itemInDB.setPosition(item_position);
        itemInDB.save();
    }


}



