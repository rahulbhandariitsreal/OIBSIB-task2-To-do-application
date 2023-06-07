package com.example.todoapplication.fragments;

import static com.example.todoapplication.Home_Activity.name_owner;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.todoapplication.Home_Activity;
import com.example.todoapplication.R;
import com.example.todoapplication.adapter.Adapter_Task;
import com.example.todoapplication.model.Task_Holder;
import com.example.todoapplication.viewmodel.Viewmodel_task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;


public class Task_Fragment extends Fragment implements Adapter_Task.onClickedlistener {


    private Viewmodel_task viewmodel_task;

    EditText taskheading, taskdetail;
    Button dateButton;

    private Task_Holder edit_task;

    private ArrayList<Task_Holder> holderArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private Adapter_Task adapter_task;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_task_, container, false);
        viewmodel_task = new ViewModelProvider(getActivity()).get(Viewmodel_task.class);
        recyclerView = view.findViewById(R.id.taskrecycler);

        viewmodel_task.getalltask().observe(getViewLifecycleOwner(), new Observer<List<Task_Holder>>() {
            @Override
            public void onChanged(List<Task_Holder> task_holders) {
                holderArrayList.clear();
                Log.v("TAG", name_owner + " " + task_holders.size());
                for(Task_Holder task_holder:task_holders)
                {
                    if(name_owner.equals(task_holder.getUsername()))
                    holderArrayList.add(task_holder);
                }
                setrecyclerview();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                Task_Holder deltask = holderArrayList.get(viewHolder.getAdapterPosition());
                viewmodel_task.delete_task(deltask);
            }
        }).attachToRecyclerView(recyclerView);

    }

    private void setrecyclerview() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter_task = new Adapter_Task(getContext());
        recyclerView.setAdapter(adapter_task);
        adapter_task.setListtask(holderArrayList);
        adapter_task.setListener(this::oncliked);
    }


    @Override
    public void oncliked(View v, int pos) {
        edit_task = new Task_Holder();

        Task_Holder task_holder = holderArrayList.get(pos);

        Toast.makeText(getContext(), "" + task_holder.getTask(), Toast.LENGTH_SHORT).show();
        LayoutInflater inflater = LayoutInflater.from(getContext());

        // Inflate the custom layout for the dialog
        View dialogView = inflater.inflate(R.layout.addd_task, null);

        AlertDialog.Builder alterdialogue = new AlertDialog.Builder(getContext());
        alterdialogue.setTitle("Update task");
        alterdialogue.setMessage("Enter Details");

        alterdialogue.setView(dialogView);

        alterdialogue.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // Initialize views
        taskheading = dialogView.findViewById(R.id.task_heading);
        taskdetail = dialogView.findViewById(R.id.taskdetails);
        dateButton = dialogView.findViewById(R.id.dateButton);

        taskdetail.setText(task_holder.getTask());
        taskheading.setText(task_holder.getTask_details());
        dateButton.setText(task_holder.getTask_date());

        ToggleButton toggleButton=dialogView.findViewById(R.id.toggleButton);
        toggleButton.setVisibility(View.GONE);


        Calendar calendar;
        // Initialize calendar
        calendar = Calendar.getInstance();

        // Set button click listener for date selection
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        AlertDialog dialog = alterdialogue.create();

        // Set button click listener for saving
        Button saveButton = dialogView.findViewById(R.id.saveButton);
        saveButton.setText("Update");
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String heading = taskheading.getText().toString().trim();
                String detail = taskdetail.getText().toString().trim();
                String date = dateButton.getText().toString();

                edit_task.setTask(heading);
                edit_task.setTask_details(detail);
                edit_task.setTask_date(date);
                edit_task.setUsername(name_owner);
                edit_task.setId(task_holder.getId());
                viewmodel_task.update_task(edit_task);
                Toast.makeText(getContext(), "Update successfully", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();


    }

    private void showDatePicker() {
        // Create a Calendar instance to get the current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a DatePickerDialog and set the current date as default
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                        // Do something with the selected date
                        String selectedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDayOfMonth;
                        edit_task.setTask_date(selectedDate);
                        edit_task.setUsername(name_owner);
                        // You can display the selected date in a TextView or EditText
                        dateButton.setText(selectedDate);
                    }
                },
                year, month, dayOfMonth);

        // Show the date picker dialog
        datePickerDialog.show();

    }

}