package com.example.todoapplication.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.todoapplication.dao.Task_DAO;
import com.example.todoapplication.dao.User_AO;
import com.example.todoapplication.model.Task_Holder;
import com.example.todoapplication.model.User;


@Database(entities = {User.class, Task_Holder.class}, version = 1)
public abstract class RoomDatabse extends RoomDatabase {

    public abstract User_AO userDao();
    public abstract Task_DAO taskDao();

}
