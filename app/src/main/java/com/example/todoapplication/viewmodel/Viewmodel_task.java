package com.example.todoapplication.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import com.example.todoapplication.database.RoomDatabse;
import com.example.todoapplication.model.Task_Holder;
import com.example.todoapplication.model.User;
import com.example.todoapplication.repository.Repository_Task_User;

import java.util.List;

public class Viewmodel_task extends AndroidViewModel {

    private  Repository_Task_User repository_task_user;
    private LiveData<List<Task_Holder>> task_holderLiveData;
    private RoomDatabse db;

    public Viewmodel_task(@NonNull Application application) {
        super(application);
      db = Room.databaseBuilder(application,
                RoomDatabse.class, "database-name").build();
        repository_task_user=new Repository_Task_User(db);
    }

    public LiveData<List<Task_Holder>> getalltask(){
     task_holderLiveData=   repository_task_user.getalltask();
     return task_holderLiveData;
    }

    public void update_task(Task_Holder task_holder){
        repository_task_user.udpate_task(task_holder);
    }
    public void delete_task(Task_Holder task_holder){
        repository_task_user.delete_task(task_holder);
    }

    public void adduser(User user){
        repository_task_user.adduser(user);
    }



    public boolean getauser(String name,String pass){
        User user=repository_task_user.get_a_user(name,pass);
        if (user == null)
            return false;
        else
            return true;
    }




}
