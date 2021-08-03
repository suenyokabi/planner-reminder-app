package com.example.alarmreminder.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.alarmreminder.TaskActivity;
import com.example.alarmreminder.data.DatabaseHelper;
import com.example.alarmreminder.Model.ToDoModel;
import com.example.alarmreminder.AddNewTask;
import com.example.alarmreminder.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> {
    private List<ToDoModel> mList;
    private TaskActivity activity;
    private DatabaseHelper sqLiteManager;
    public ToDoAdapter(DatabaseHelper sqLiteManager, TaskActivity activity){
        this.activity=activity;
        this.sqLiteManager=sqLiteManager;
    }

    @NonNull

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout , parent , false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoAdapter.MyViewHolder holder, int position) {
        final ToDoModel item = mList.get(position);
        holder.mCheckBox.setText(item.getTask());
        holder.mCheckBox.setChecked(toBoolean(item.getStatus()));
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    sqLiteManager.updateStatus(item.getId() , 1);
                }else
                    sqLiteManager.updateStatus(item.getId() , 0);
            }
        });

    }

    private boolean toBoolean(int num) {
        return num!=0;
    }
    public Context getContext(){
        return activity;
    }
    public void setTasks(List<ToDoModel> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }
    public void deletTask(int position){
        ToDoModel item = mList.get(position);
        sqLiteManager.deleteTask(item.getId());
        mList.remove(position);
        notifyItemRemoved(position);
    }
    public void editItem(int position){
        ToDoModel item = mList.get(position);

        Bundle bundle = new Bundle();
        bundle.putInt("id" , item.getId());
        bundle.putString("task" , item.getTask());

        AddNewTask task = new AddNewTask();
        task.setArguments(bundle);
        task.show(activity.getSupportFragmentManager() , task.getTag());


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
CheckBox mCheckBox;
        public MyViewHolder(@NonNull  View itemView) {
            super(itemView);
            mCheckBox=itemView.findViewById(R.id.mcheckbox);
        }
    }
}
