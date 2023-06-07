package com.example.todoapplication.fragments;

import static com.example.todoapplication.Home_Activity.name_owner;

import android.annotation.SuppressLint;
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
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.todoapplication.R;
import com.example.todoapplication.adapter.Adapter_Notes;
import com.example.todoapplication.model.Notes;
import com.example.todoapplication.model.Task_Holder;
import com.example.todoapplication.viewmodel.Viewmodel_task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Notes_Fragment extends Fragment implements Adapter_Notes.onClickedlistener {


    private Viewmodel_task viewmodel_task;
    private RecyclerView recyclerView;
    private ArrayList<Notes> arrayList;

    private Adapter_Notes adapter_notes;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_, container, false);

        viewmodel_task=new ViewModelProvider(getActivity()).get(Viewmodel_task.class);
        arrayList=new ArrayList<>();
        adapter_notes=new Adapter_Notes(getContext());
        recyclerView=view.findViewById(R.id.notesrecycler);
        viewmodel_task.getallnotes().observe(getViewLifecycleOwner(), new Observer<List<Notes>>() {
            @Override
            public void onChanged(List<Notes> notes) {
                arrayList.clear();
                for(Notes note_now:notes)
                {
                    if(name_owner.equals(note_now.getNote_username()))
                    arrayList.add(note_now);
                }
                setrecyclerview();
            }
        });




        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                Notes deltask = arrayList.get(viewHolder.getAdapterPosition());
                viewmodel_task.delete_note(deltask);
            }
        }).attachToRecyclerView(recyclerView);
    }

    private void setrecyclerview() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter_notes);
        adapter_notes.setListtask(arrayList);
        adapter_notes.setListener(this::oncliked);
    }

    @Override
    public void oncliked(View v, int pos) {
        Notes edit_note=new Notes();

        Notes note_holder = arrayList.get(pos);


        LayoutInflater inflater = LayoutInflater.from(getContext());

        // Inflate the custom layout for the dialog
        View dialogView = inflater.inflate(R.layout.addd_task, null);

        AlertDialog.Builder alterdialogue = new AlertDialog.Builder(getContext());
        alterdialogue.setTitle("Update Note");
        alterdialogue.setMessage("Enter Details");

        alterdialogue.setView(dialogView);

        EditText taskheading,taskdetail;
        Button dateButton;
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
        ToggleButton toggleButton=dialogView.findViewById(R.id.toggleButton);
        toggleButton.setVisibility(View.GONE);


        taskdetail.setText(note_holder.getNote_details());
        taskheading.setText(note_holder.getNote_heading());
        dateButton.setText(note_holder.getTime_creation());

        dateButton.setEnabled(false);






        AlertDialog dialog = alterdialogue.create();

        // Set button click listener for updating
        Button saveButton = dialogView.findViewById(R.id.saveButton);
        saveButton.setText("Update");
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String heading = taskheading.getText().toString().trim();
                String detail = taskdetail.getText().toString().trim();
                edit_note.setTime_creation(note_holder.getTime_creation());
                edit_note.setNote_heading(heading);
                edit_note.setNote_username(name_owner);
                edit_note.setNote_details(detail);
                edit_note.setId(note_holder.getId());
                viewmodel_task.update_notes(edit_note);
                Toast.makeText(getContext(), "Update successfully", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();



    }
}