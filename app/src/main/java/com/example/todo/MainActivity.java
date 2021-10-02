package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

import com.example.todo.Adapter.RecyclerViewAdapter;
import com.example.todo.Data.Priority;
import com.example.todo.Data.Task;
import com.example.todo.Model.TaskViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.onTaskClickListener {
    private TaskViewModel taskViewModel;
    private FloatingActionButton addTask_fab;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
   // BottomSheetFragment bottomSheetFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




        taskViewModel = new ViewModelProvider
                              .AndroidViewModelFactory(MainActivity.this.getApplication())
                              .create(TaskViewModel.class);


        addTask_fab = findViewById(R.id.add_task_fab);
        addTask_fab.setOnClickListener(v -> {
//            Task task = new Task("study", Priority.HIGH , Calendar.getInstance().getTime() , Calendar.getInstance().getTime(),false);
//            TaskViewModel.insert(task);
            Intent intent = new Intent(MainActivity.this , BottomSheetFragment.class);
            startActivity(intent);

        });

        taskViewModel.getAllTasks().observe(this, tasks -> {
        recyclerViewAdapter = new RecyclerViewAdapter(tasks , this );
        recyclerView.setAdapter(recyclerViewAdapter);
            });

    }

    @Override
    public void onTaskClick(long position) {
       Task task = taskViewModel.getAllTasks().getValue().get((int) position);
        Log.d("TEST_INTENT", "onTaskClick: " + position);
       Intent intent = new Intent(MainActivity.this , BottomSheetFragment.class);
       intent.putExtra("task_id_intent", task.getId());
       startActivity(intent);
    }

    @Override
    public void onRadioButtonClick(Task task) {
        TaskViewModel.delete(task);
        recyclerViewAdapter.notifyDataSetChanged();
    }


}