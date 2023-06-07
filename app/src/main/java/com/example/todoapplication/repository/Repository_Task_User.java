package com.example.todoapplication.repository;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.todoapplication.dao.Note_Dao;
import com.example.todoapplication.dao.Task_DAO;
import com.example.todoapplication.dao.User_AO;
import com.example.todoapplication.database.RoomDatabse;
import com.example.todoapplication.model.Notes;
import com.example.todoapplication.model.Task_Holder;
import com.example.todoapplication.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository_Task_User {

    User_AO userDao;
    Task_DAO task_dao;

    Note_Dao note_dao;
    private User users;
    public LiveData<List<Task_Holder>> tasks_live;
    public LiveData<List<Notes>> notes_live;


    public Repository_Task_User(RoomDatabse db) {
        userDao = db.userDao();
        task_dao = db.taskDao();
        note_dao = db.note_dao();
    }

    public LiveData<List<Task_Holder>> getalltask() {
        tasks_live = task_dao.getAll_Task();
        return tasks_live;
    }

    public void adduser(User user) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                userDao.insert_User(user);
            }
        });
    }

    public void add_task(Task_Holder task_holder) {

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                task_dao.insert_Task(task_holder);
            }
        });

    }

    public void udpate_task(Task_Holder task_holder) {

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                task_dao.updatetask(task_holder);
            }
        });

    }

    public void delete_task(Task_Holder task_holder) {

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                task_dao.delete_Task(task_holder);
            }
        });

    }

    public User get_a_user(String name, String pass) {

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                users = userDao.getauser(name, pass);
            }
        });
        return users;
    }

    //notes
    public LiveData<List<Notes>> getallnotes() {
        notes_live = note_dao.getAll_notes();
        return notes_live;
    }

    public void addnote(Notes notes) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                note_dao.insertnote(notes);
            }
        });
    }


    public void udpate_note(Notes notes) {

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                note_dao.updatenote(notes);
            }
        });

    }

    public void delete_note(Notes notes) {

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                note_dao.delete_note(notes);
            }
        });

    }

}
