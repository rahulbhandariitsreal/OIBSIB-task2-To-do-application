package com.example.todoapplication.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.todoapplication.model.Notes;

import java.util.List;
@Dao
public interface Note_Dao {
    @Query("SELECT * from notes")
    LiveData<List<Notes>> getAll_notes();


    @Insert
    void insertnote(Notes users);

    @Update
    void updatenote(Notes task_holder);


    @Delete
    void delete_note(Notes user);


}