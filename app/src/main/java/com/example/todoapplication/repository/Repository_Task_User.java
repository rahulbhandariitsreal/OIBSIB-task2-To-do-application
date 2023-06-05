package com.example.todoapplication.repository;



import androidx.lifecycle.LiveData;

import com.example.todoapplication.dao.Task_DAO;
import com.example.todoapplication.dao.User_AO;
import com.example.todoapplication.database.RoomDatabse;
import com.example.todoapplication.model.Task_Holder;
import com.example.todoapplication.model.User;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository_Task_User {

    User_AO userDao ;
    Task_DAO task_dao;
    private   User users;

    public Repository_Task_User(RoomDatabse db) {
        userDao=db.userDao();
        task_dao=db.taskDao();

    }

    public LiveData<List<Task_Holder>> getalltask(){
       LiveData< List<Task_Holder> > tasks = task_dao.getAll_Task();
        return  tasks;
    }

    public void adduser(User user){
        ExecutorService executorService=Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {

                userDao.insert_User(user);
            }
        });
    }

    public void udpate_task(Task_Holder task_holder){

        ExecutorService executorService= Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                task_dao.updatetask(task_holder);
            }
        });

    }
    public void delete_task(Task_Holder task_holder){

        ExecutorService executorService= Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                task_dao.delete_Task(task_holder);
            }
        });

    }

    public User get_a_user(String name,String pass){

        ExecutorService executorService= Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                 users=userDao.getauser(name,pass);
            }
        });
        return  users;
    }

}
