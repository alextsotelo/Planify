package com.example.calendarapp; // Update this to match your app's package name

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class List extends AppCompatActivity {

    private Button buttonBackToCalendar; // Back to Calendar button
    private Button buttonAddTask; // Add Task button
    private ListView listViewTasks; // ListView for tasks
    private ArrayList<Task> tasks; // List to hold tasks
    private TaskAdapter adapter; // Adapter for ListView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Initialize views
        buttonBackToCalendar = findViewById(R.id.buttonBackToCalendar); // Back to Calendar button
        buttonAddTask = findViewById(R.id.buttonAddTask); // Add Task button
        listViewTasks = findViewById(R.id.listViewTasks); // ListView for tasks

        // Set up the back button listener
        buttonBackToCalendar.setOnClickListener(v -> finish()); // This closes the current activity

        // Initialize task list and adapter
        tasks = new ArrayList<>();
        adapter = new TaskAdapter(this, tasks);
        listViewTasks.setAdapter(adapter);

        // Set up button click listener for adding tasks
        buttonAddTask.setOnClickListener(v -> showAddTaskDialog());
    }

    // Method to show dialog for adding a new task
    private void showAddTaskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Task");

        // Set up the input
        final EditText input = new EditText(this);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Save", (dialog, which) -> {
            String taskText = input.getText().toString();
            if (!taskText.isEmpty()) {
                tasks.add(new Task(taskText, false)); // Add a new task
                adapter.notifyDataSetChanged(); // Update the ListView
            } else {
                Toast.makeText(List.this, "Task cannot be empty!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    // Task class to hold task data
    private class Task {
        String taskText; // Text of the task
        boolean isChecked; // Whether the task is checked

        Task(String taskText, boolean isChecked) {
            this.taskText = taskText;
            this.isChecked = isChecked;
        }
    }

    // Custom adapter for the ListView
    private class TaskAdapter extends ArrayAdapter<Task> {
        public TaskAdapter(@NonNull List context, ArrayList<Task> tasks) {
            super(context, 0, tasks);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            // Get the data item for this position
            Task task = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            }

            // Lookup view for data population
            CheckBox checkBoxTask = convertView.findViewById(R.id.checkBoxTask);
            TextView textViewTask = convertView.findViewById(R.id.textViewTask);
            Button buttonDelete = convertView.findViewById(R.id.buttonDelete);

            // Populate the data into the template view using the data object
            textViewTask.setText(task.taskText);
            checkBoxTask.setChecked(task.isChecked);

            // Update the task when the checkbox is clicked
            checkBoxTask.setOnCheckedChangeListener((buttonView, isChecked) -> {
                task.isChecked = isChecked; // Update the task's checked status
            });

            // Set up the delete button click listener
            buttonDelete.setOnClickListener(v -> {
                tasks.remove(position); // Remove the task from the list
                notifyDataSetChanged(); // Update the ListView
                Toast.makeText(List.this, "Task deleted", Toast.LENGTH_SHORT).show();
            });

            return convertView;
        }
    }
}
