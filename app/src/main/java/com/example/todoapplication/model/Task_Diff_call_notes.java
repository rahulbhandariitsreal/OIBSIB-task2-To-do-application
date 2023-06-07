package com.example.todoapplication.model;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import java.util.ArrayList;

public class Task_Diff_call_notes extends DiffUtil.Callback {

    ArrayList<Notes> oldTaskList;
    ArrayList<Notes> newTaskList;

    public Task_Diff_call_notes(ArrayList<Notes> oldTaskList, ArrayList<Notes> newTaskList)  {
        this.oldTaskList = oldTaskList;
        this.newTaskList = newTaskList;

    }

    @Override
    public int getOldListSize() {
        return oldTaskList==null?0:oldTaskList.size();
    }

    @Override
    public int getNewListSize() {
        return newTaskList==null?0:newTaskList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return newTaskList.get(newItemPosition).getId()==oldTaskList.get(oldItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return newTaskList.get(newItemPosition).equals(oldTaskList.get(oldItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}

