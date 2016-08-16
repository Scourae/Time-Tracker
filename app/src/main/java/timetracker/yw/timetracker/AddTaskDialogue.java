package timetracker.yw.timetracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AddTaskDialogue extends DialogFragment {

    private String mAddTask = "Add Task";
    private String mEditTask = "Edit Task";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        Bundle instance = getArguments();
        boolean isEdit = false;
        String titleString = "";
        String descriptionString = "";
        int position = 0;
        if (instance != null) {
            titleString = instance.getString("name");
            descriptionString = instance.getString("description");
            position = instance.getInt("position");
            isEdit = true;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.add_task_dialog, null);
        builder.setView(view);
        builder.setTitle(mAddTask);
        if (isEdit) {
            builder.setTitle(mEditTask);
            EditText titleView = (EditText) view.findViewById(R.id.add_task_title);
            titleView.setText(titleString, TextView.BufferType.EDITABLE);
            EditText descriptionView = (EditText) view.findViewById(R.id.add_task_description);
            descriptionView.setText(descriptionString, TextView.BufferType.EDITABLE);
        }
        final boolean isEditToInner = isEdit;
        final int positionToInner = position;
        builder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                EditText titleView = (EditText) view.findViewById(R.id.add_task_title);
                String titleString = titleView.getText().toString();
                EditText descriptionView = (EditText) view.findViewById(R.id.add_task_description);
                String descriptionString = descriptionView.getText().toString();

                MainActivity currentActivity = (MainActivity) getActivity();
                if (!isEditToInner) {
                    currentActivity.getToDoFragment().addTask(titleString, descriptionString);
                }
                else {
                    currentActivity.getToDoFragment().editTask(titleString, descriptionString, positionToInner);
                }
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        return builder.create();
    }
}
