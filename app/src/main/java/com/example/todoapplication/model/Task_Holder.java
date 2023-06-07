package com.example.todoapplication.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Task_Holder {

    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "username")
    String username;


    @ColumnInfo(name = "task")
    String task;

    @ColumnInfo(name = "date")
    String task_date;

    @ColumnInfo(name = "task_detail")
    String task_details;

    public Task_Holder() {
    }

    public Task_Holder(String username, String task, String task_date, String task_details) {
        this.username = username;
        this.task = task;
        this.task_date = task_date;
        this.task_details = task_details;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getTask_date() {
        return task_date;
    }

    public void setTask_date(String task_date) {
        this.task_date = task_date;
    }

    public String getTask_details() {
        return task_details;
    }

    public void setTask_details(String task_details) {
        this.task_details = task_details;
    }
}
