package com.example.todoapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import com.example.todoapplication.databinding.ActivityHomeBinding;
import com.example.todoapplication.model.User;

public class Home_Activity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.logout.setOnClickListener(new View.OnClickListener() {
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