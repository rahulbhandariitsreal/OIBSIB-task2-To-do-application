package com.example.todoapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todoapplication.database.RoomDatabse;
import com.example.todoapplication.databinding.ActivityMainBinding;
import com.example.todoapplication.model.User;
import com.example.todoapplication.viewmodel.Viewmodel_task;

public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;
    private User user;
    private Viewmodel_task viewmodel_task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        retrievedata();



     viewmodel_task=new ViewModelProvider(this).get(Viewmodel_task.class);






        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginsetup();
            }
        });

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrationsetup();
            }
        });




    }

    private void retrievedata() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String name_stored= sharedPreferences.getString("user_name", "");

        if(!TextUtils.isEmpty(name_stored)){
            startActivity(new Intent(MainActivity.this,Home_Activity.class));
            finish();
        }

    }

    private void registrationsetup() {

// Inflate the custom layout
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.registration_layout, null);

// Find views within the custom layout
        Button positiveButton = dialogView.findViewById(R.id.btnRegister);
        EditText nametext=dialogView.findViewById(R.id.etName);
        EditText password=dialogView.findViewById(R.id.etPassword);
        EditText cpassword=dialogView.findViewById(R.id.etConfirmPassword);



        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=nametext.getText().toString();
                String pass=password.getText().toString();
                String cpass=cpassword.getText().toString();

                if(TextUtils.isEmpty(name) || !pass.equals(cpass) ){
                    Toast.makeText(MainActivity.this, "Invalid Fields", Toast.LENGTH_SHORT).show();
                }else{
                    User user1=new User(name,pass);
                    viewmodel_task.adduser(user1);
                    saveduserinsharedpre(user1);
                    startActivity(new Intent(MainActivity.this,Home_Activity.class));
                    finish();

                }

            }
        });
// Create an instance of the AlertDialog.Builder class
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

// Set the inflated layout as the view for the AlertDialog.Builder
        builder.setView(dialogView);

// Customize the dialog further
        builder.setTitle("Registration")
                .setMessage("Welcome to Registration");


// Set the negative button and its click listener
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle negative button click
                // Do something when the user clicks the Cancel button
                dialog.dismiss();
            }
        });

// Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();




    }

    private void saveduserinsharedpre(User user1) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_name",user1.getName());
        editor.apply();
    }

    private void loginsetup() {
// Create an instance of the AlertDialog.Builder class
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.login_layout, null);

// Find views within the custom layout
        Button positiveButton = dialogView.findViewById(R.id.btnLogin);
        EditText nametext=dialogView.findViewById(R.id.etUsername);
        EditText password=dialogView.findViewById(R.id.etPassword);

        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=nametext.getText().toString();
                String pass=password.getText().toString();

                if(TextUtils.isEmpty(name) ){
                    Toast.makeText(MainActivity.this, "Invalid Fields", Toast.LENGTH_SHORT).show();
                }else{
                    User user1=new User(name,pass);
                boolean result=    viewmodel_task.getauser(user1.getName(),user1.getPassword());
                if(result){
                    saveduserinsharedpre(user1);
                    startActivity(new Intent(MainActivity.this,Home_Activity.class));
                    finish();
                }else{
                    Toast.makeText(MainActivity.this, "Wrong Credentials", Toast.LENGTH_SHORT).show();
                }


                }

            }
        });


// Set the inflated layout as the view for the AlertDialog.Builder
        builder.setView(dialogView);

// Customize the dialog further
        builder.setTitle("Login")
                .setMessage("Please Enter Details");


// Set the negative button and its click listener
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle negative button click
                // Do something when the user clicks the Cancel button
                dialog.dismiss();
            }
        });

// Create and show the dialog
        // Inflate the custom layout
        AlertDialog dialog = builder.create();
        dialog.show();







    }


}