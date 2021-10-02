package com.example.todo.Adapter;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.Data.Task;
import com.example.todo.R;
import com.example.todo.Util.Utils;
import com.google.android.material.chip.Chip;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Task> allTasks;
    private onTaskClickListener onTaskClickListener;

    public RecyclerViewAdapter(List<Task> allTasks, onTaskClickListener onTaskClickListener) {
        this.allTasks = allTasks;
        this.onTaskClickListener= onTaskClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_row_adapter , parent ,false);
        return new ViewHolder(view, onTaskClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Task task = allTasks.get(position);
        ColorStateList colorStateList = new ColorStateList(new int[][]{
                new int[]{-android.R.attr.state_enabled},
                new int[]{android.R.attr.state_enabled}
        },
                new int[]{
                        Color.LTGRAY,
                        Utils.priorityColor(task)
        });

        holder.task_textview.setText(task.getTask().toString());
        holder.today_chip.setText(Utils.FormatDate(task.getDue_date()));

        holder.today_chip.setTextColor(colorStateList);
        holder.today_chip.setChipIconTint(colorStateList);
        holder.radio_button.setButtonTintList(colorStateList);
        holder.today_chip.setTextColor(colorStateList);
        holder.task_textview.setTextColor(colorStateList);


    }

    @Override
    public int getItemCount() {
        return allTasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private AppCompatRadioButton radio_button;
        private AppCompatTextView task_textview;
        private Chip today_chip;
        onTaskClickListener onTaskClickListener;

        public ViewHolder(@NonNull View itemView, onTaskClickListener onTaskClickListener) {
            super(itemView);
            radio_button = itemView.findViewById(R.id.todo_row_radio_Button);
            task_textview = itemView.findViewById(R.id.todo_row_textview);
            today_chip = itemView.findViewById(R.id.todo_row_chip);
            this.onTaskClickListener = onTaskClickListener;

            itemView.setOnClickListener(this);
            radio_button.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            int id = v.getId();
            Task task = allTasks.get(getAdapterPosition());
          //  if(id==R.id.todo_row_layout) {
            //}
            if(id==R.id.todo_row_radio_Button){
                onTaskClickListener.onRadioButtonClick(task);
            }
            else{
                onTaskClickListener.onTaskClick(getAdapterPosition());
            }

        }
    }

    public interface onTaskClickListener {
        void onTaskClick(long position);

        void onRadioButtonClick(Task task);
    }
}
