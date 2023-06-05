package com.example.todoapplication.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.todoapplication.model.User;

import java.util.List;

@Dao
public interface User_AO {


    @Insert
    void insert_User(User user);

    @Query("Select * from User" )
    LiveData<List<User>> getall_user();

    @Query("SELECT * FROM user WHERE name LIKE :first AND"+
            " password Like :pass ")

    User getauser(String first,String pass);

    @Delete
    void delete_user(User user);



}
