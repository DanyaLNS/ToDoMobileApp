package mirea.ck.anikin.todo.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mirea.ck.anikin.todo.AddNewTask;
import mirea.ck.anikin.todo.MainActivity;
import mirea.ck.anikin.todo.model.ToDoModel;
import mirea.ck.anikin.todo.R;
import mirea.ck.anikin.todo.utils.DatabaseHandler;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {
    private List<ToDoModel> toDoList;
    private MainActivity mainActivity;
    private DatabaseHandler db;

    public ToDoAdapter(DatabaseHandler db, MainActivity mainActivity) {
        this.db = db;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        db.openDatabase();
        ToDoModel item = toDoList.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(toBoolean(item.getStatus()));
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    db.updateStatus(item.getId(), 1);
                } else {
                    db.updateStatus(item.getId(), 0);
                }
            }
        });
    }

    public int getItemCount() {
        return toDoList.size();
    }

    private boolean toBoolean(int num) {
        return num != 0;
    }

    public void setTasks(List<ToDoModel> toDoList) {
        this.toDoList = toDoList;
//        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        ToDoModel item = toDoList.get(position);
        db.deleteTask(item.getId());
        toDoList.remove(position);
        notifyItemRemoved(position);
    }

    public Context getContext() {
        return mainActivity;
    }

    public void editItem(int position) {
        ToDoModel item = toDoList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("task", item.getTask());
        AddNewTask fragment = new AddNewTask();
        fragment.setArguments(bundle);
        fragment.show(mainActivity.getSupportFragmentManager(), AddNewTask.TAG);

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox task;

        ViewHolder(View view) {
            super(view);
            task = view.findViewById(R.id.todoCheckBox);
        }
    }
}
