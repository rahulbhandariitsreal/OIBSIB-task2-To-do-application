package com.example.todoapplication.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.todoapplication.model.Task_Holder;

import java.util.List;

@Dao
public interface Task_DAO {


    @Query("SELECT * FROM Task_Holder")
   LiveData<List<Task_Holder>> getAll_Task();

    @Insert
    void insert_Task(Task_Holder users);

    @Update
    void updatetask(Task_Holder task_holder);

    @Delete
    void delete_Task(Task_Holder user);


}
