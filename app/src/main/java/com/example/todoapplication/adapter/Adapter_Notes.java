package com.example.todoapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapplication.R;
import com.example.todoapplication.model.Notes;
import com.example.todoapplication.model.Task_Diff_call;
import com.example.todoapplication.model.Task_Diff_call_notes;
import com.example.todoapplication.model.Task_Holder;

import java.util.ArrayList;


public class Adapter_Notes extends RecyclerView.Adapter<Adapter_Notes.viewholder> {

    private ArrayList<Notes> arrayList;
    private Context context;

    private onClickedlistener listener;


    public Adapter_Notes(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_custon_layout, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        Notes taskData = arrayList.get(position);
        holder.taskTextView_head.setText(taskData.getNote_heading());
        holder.taskTextView_detail.setText(taskData.getNote_details());
        holder.dateTextView.setText(taskData.getTime_creation());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void setListener(onClickedlistener listener) {
        this.listener = listener;
    }

    public class viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView taskTextView_head;
        private TextView taskTextView_detail;
        private TextView dateTextView;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            taskTextView_head = itemView.findViewById(R.id.taskTextView_heading);
            taskTextView_detail = itemView.findViewById(R.id.taskTextView_deatils);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(listener !=null){
                listener.oncliked(v,getAdapterPosition());
            }
        }
    }

    public void setListtask(ArrayList<Notes> newlisttask) {
//        this.listtask = listtask;
//        notifyDataSetChanged();
        final DiffUtil.DiffResult result = DiffUtil.calculateDiff
                (new Task_Diff_call_notes(arrayList, newlisttask), false);

        arrayList = newlisttask;
        result.dispatchUpdatesTo(Adapter_Notes.this);
    }

    public interface onClickedlistener{
        void oncliked(View v,int pos);
    }
}