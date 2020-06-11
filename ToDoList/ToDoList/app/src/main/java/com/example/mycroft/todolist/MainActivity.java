package com.example.mycroft.todolist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DbHalper dbHalper;
    ArrayAdapter<String > mAdapter;
    ListView lstTask;
    Button add_btn;
    EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHalper = new DbHalper(this);
        lstTask = (ListView)findViewById(R.id.listview);
        add_btn = (Button) findViewById(R.id.add_btn);
        edit = (EditText)findViewById(R.id.editText);

        loadTaskList();

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String task =edit.getText().toString().trim();
                dbHalper.insertNewTask(task);
                edit.setText("");
                loadTaskList();
            }
        });
    }

    public void deleteTask( View view){
        View parent = (View) view.getParent();
        TextView taskView = (TextView) parent.findViewById(R.id.task_title);
        String task = taskView.getText().toString();
        dbHalper.deleteTask(task);
        loadTaskList();
    }

    public void updateTask(View view){
        View parent = (View) view.getParent();
        TextView taskView = (TextView) parent.findViewById(R.id.task_title);
        final String task = taskView.getText().toString();

        final EditText update_edit =new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Edit")
                .setView(update_edit)
                .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dbHalper.updateTask(task, update_edit.getText().toString().trim());
                        loadTaskList();
                    }
                })
                .setNegativeButton("Cancel",null)
                .create();
        dialog.show();
    }

    public void loadTaskList(){
        ArrayList<String> taskList = dbHalper.getTaskList();
        if (mAdapter == null){
            mAdapter = new ArrayAdapter<String>(this,R.layout.row,R.id.task_title,taskList);
            lstTask.setAdapter(mAdapter);
        }
        else{
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }
    }
}