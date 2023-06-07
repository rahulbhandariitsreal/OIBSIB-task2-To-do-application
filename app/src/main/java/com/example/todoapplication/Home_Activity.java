package com.example.todoapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.todoapplication.adapter.ViewPgagerAdapter;
import com.example.todoapplication.databinding.ActivityHomeBinding;
import com.example.todoapplication.fragments.Notes_Fragment;
import com.example.todoapplication.fragments.Task_Fragment;
import com.example.todoapplication.model.Notes;
import com.example.todoapplication.model.Task_Holder;
import com.example.todoapplication.viewmodel.Viewmodel_task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Calendar;

public class Home_Activity extends AppCompatActivity {

    private ActivityHomeBinding binding_h;

    private Notes notes;

    private int SAVING_COUNTER=1;
    //0 -> notes
    //1 -> task;
    private EditText taskheading, taskdetail;
    private Button dateButton;
    private   Task_Holder task_holder;
    private Calendar calendar;
    public  static String name_owner;

    private Viewmodel_task viewmodel_task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding_h=ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding_h.getRoot());

        viewmodel_task=new ViewModelProvider(this).get(Viewmodel_task.class);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        name_owner= sharedPreferences.getString("user_name", "");

        ViewPgagerAdapter adapter = new ViewPgagerAdapter(Home_Activity.this);
     binding_h.viewpager2.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tablayout);
//        new TabLayoutMediator(tabLayout, viewPager2,
//                (tab, position) -> tab.setText("OBJECT " + (position + 1))
//        ).attach();

        TabLayoutMediator tabLayoutMediator=new TabLayoutMediator(tabLayout,binding_h.viewpager2,(tab, position) -> {
            if(position == 0){
                tab.setText("Event");
            }
            else if(position==1){
                tab.setText("Notes");

            }
        });

        tabLayoutMediator.attach();
        notes=new Notes();


        task_holder=new Task_Holder();
        binding_h.addtask.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingInflatedId")
            @Override
            public void onClick(View v) {
                // Create a LayoutInflater instance
                LayoutInflater inflater = LayoutInflater.from(getApplication());

                // Inflate the custom layout for the dialog
                View dialogView = inflater.inflate(R.layout.addd_task, null);

                AlertDialog.Builder alterdialogue=new AlertDialog.Builder(Home_Activity.this);
                alterdialogue.setTitle("Add task and note");
                alterdialogue.setMessage("Enter Details");

                alterdialogue.setView(dialogView);

                ToggleButton toggleButton=dialogView.findViewById(R.id.toggleButton);
                // Initialize views
                taskheading = dialogView.findViewById(R.id.task_heading);
                taskdetail = dialogView.findViewById(R.id.taskdetails);
                dateButton = dialogView.findViewById(R.id.dateButton);
                toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        // Handle the toggle button state change here
                        if (isChecked) {
                            // Toggle button is on
                            //note
                            //0
                            Calendar calendar = Calendar.getInstance();
                            int year = calendar.get(Calendar.YEAR);
                            int month = calendar.get(Calendar.MONTH);
                            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                            String selectedDate = year + "-" + (month+1) + "-" + dayOfMonth;

                            notes.setTime_creation(selectedDate);
                            SAVING_COUNTER=0;

                            dateButton.setEnabled(false);
                            taskheading.setHint("Enter note heading");
                            taskdetail.setHint("Enter note details");
                            dateButton.setText(selectedDate);
                            task_holder.setTask_date(selectedDate);
                        } else {
                            // Toggle button is off
                            //task
                            dateButton.setEnabled(true);
                            SAVING_COUNTER=1;
                            taskheading.setHint("Enter task heading");
                            taskdetail.setHint("Enter task details");
                            dateButton.setText("select date");

                        }
                    }
                });
                alterdialogue.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                   dialog.dismiss();
                    }
                });



                // Initialize calendar
                calendar = Calendar.getInstance();

                // Set button click listener for date selection
                dateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDatePicker();
                    }
                });

                AlertDialog dialog=alterdialogue.create();

                // Set button click listener for saving
                Button saveButton = dialogView.findViewById(R.id.saveButton);
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String heading = taskheading.getText().toString().trim();
                        String detail = taskdetail.getText().toString().trim();
                        String date = dateButton.getText().toString();

                        if(TextUtils.isEmpty(heading) || TextUtils.isEmpty(detail) || TextUtils.isEmpty(date)){
                            Toast.makeText(Home_Activity.this, "Empty fields", Toast.LENGTH_SHORT).show();

                        }else if(SAVING_COUNTER==1){
                            task_holder.setTask(heading);
                            task_holder.setTask_details(detail);
                            task_holder.setTask_date(date);
                            task_holder.setUsername(name_owner);
                            viewmodel_task.addd_task(task_holder);

                        }else if(SAVING_COUNTER==0){
                            //note
                            //0
                            Calendar calendar = Calendar.getInstance();
                            int year = calendar.get(Calendar.YEAR);
                            int month = calendar.get(Calendar.MONTH);
                            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                            String selectedDate = year + "-" + (month+1) + "-" + dayOfMonth;
                            notes.setNote_username(name_owner);
                            notes.setTime_creation(selectedDate);
                            notes.setNote_heading(heading);
                            notes.setNote_details(detail);
                            viewmodel_task.addd_note(notes);
                        }
                        Toast.makeText(Home_Activity.this, "Added successfully", Toast.LENGTH_SHORT).show();
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
                        Home_Activity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                                // Do something with the selected date
                                String selectedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDayOfMonth;
                                task_holder.setTask_date(selectedDate);
                                task_holder.setUsername(name_owner);
                                // You can display the selected date in a TextView or EditText
                                dateButton.setText(selectedDate);
                            }
                        },
                        year, month, dayOfMonth);

                // Show the date picker dialog
                datePickerDialog.show();

            }
        });


        binding_h.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Home_Activity.this);
                builder.setTitle("Logout");
                builder.setMessage("Are you sure you want to logout?");

// Set the positive button and its click listener
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Perform logout operation
                        // Call the logout method or clear session data
                        saveduserinsharedpre();
                        startActivity(new Intent(Home_Activity.this,MainActivity.class));
                        finish();
                    }
                });

// Set the negative button and its click listener
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing, dismiss the dialog
                        dialog.dismiss();
                    }
                });
                builder.show();

// Create and show the dialog
                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });


    }

    private void saveduserinsharedpre() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_name","");
        editor.apply();
    }



}