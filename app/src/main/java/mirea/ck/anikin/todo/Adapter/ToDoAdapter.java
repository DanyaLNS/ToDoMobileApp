package mirea.ck.anikin.todo.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mirea.ck.anikin.todo.MainActivity;
import mirea.ck.anikin.todo.Model.ToDoModel;
import mirea.ck.anikin.todo.R;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {
    private List<ToDoModel> toDoList;
    private MainActivity mainActivity;

    public ToDoAdapter(MainActivity mainActivity) {
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
        ToDoModel item = toDoList.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(toBoolean(item.getStatus()));
    }

    public int getItemCount() {
        return toDoList.size();
    }

    private boolean toBoolean(int num) {
        return num != 0;
    }

    public void setTasks(List<ToDoModel> toDoList){
        this.toDoList = toDoList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox task;

        ViewHolder(View view) {
            super(view);
            task = view.findViewById(R.id.todoCheckBox);
        }
    }
}
