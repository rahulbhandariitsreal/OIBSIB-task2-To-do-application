package com.example.todoapplication.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Notes {



    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "username")
    private String note_username;

    @ColumnInfo(name = "note_heading")
    private String note_heading;

    @ColumnInfo(name = "note_details")
    private String note_details;

    @ColumnInfo(name = "time")
    private String time_creation;




    public Notes() {
    }


    public Notes(String note_username, String note_heading, String note_details, String time_creation) {
        this.note_username = note_username;
        this.note_heading = note_heading;
        this.note_details = note_details;
        this.time_creation = time_creation;
    }

    public String getNote_username() {
        return note_username;
    }

    public void setNote_username(String note_username) {
        this.note_username = note_username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNote_heading() {
        return note_heading;
    }

    public void setNote_heading(String note_heading) {
        this.note_heading = note_heading;
    }

    public String getNote_details() {
        return note_details;
    }

    public void setNote_details(String note_details) {
        this.note_details = note_details;
    }

    public String getTime_creation() {
        return time_creation;
    }

    public void setTime_creation(String time_creation) {
        this.time_creation = time_creation;
    }
}
