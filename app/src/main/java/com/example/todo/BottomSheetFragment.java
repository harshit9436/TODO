package com.example.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.todo.Data.Priority;
import com.example.todo.Data.Task;
import com.example.todo.Model.TaskViewModel;
import com.example.todo.Util.Utils;
import com.google.android.material.chip.Chip;

import java.util.Calendar;
import java.util.Date;

public class BottomSheetFragment extends AppCompatActivity {
    private ImageButton setDueDate_button;
    private ImageButton SetPriority_button;
    private ImageButton saveTask_button;
    private EditText enter_task;
    private CalendarView calendarView;
    private RadioButton highPriority_button;
    private RadioButton mediumPriority_button;
    private RadioButton lowPriority_button;
    private Group calendar_group;
    private RadioGroup radioGroup;
    private Date due_Date = Calendar.getInstance().getTime();
    Calendar calendar = Calendar.getInstance();
    private  int chip_count=0;
    private TaskViewModel taskViewModel;
    private String originalTask;
    private boolean allow_edit=false;
    private Priority priority = Priority.LOW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet_fragment);
        enter_task = findViewById(R.id.enter_task_textview);
        saveTask_button = findViewById(R.id.savetask_button);
        setDueDate_button = findViewById(R.id.setDueDate_button);
        SetPriority_button = findViewById(R.id.setPriority_button);
        calendarView = findViewById(R.id.calendar_view);
        highPriority_button = findViewById(R.id.radioButton_high);
        mediumPriority_button = findViewById(R.id.radioButton_med);
        lowPriority_button = findViewById(R.id.radioButton_low);
        calendar_group = findViewById(R.id.dueDate_group);
        radioGroup = findViewById(R.id.radioGroup_priority);


        taskViewModel = new ViewModelProvider
                .AndroidViewModelFactory(BottomSheetFragment.this.getApplication())
                .create(TaskViewModel.class);

        Chip today_chip = findViewById(R.id.Today_chip);
        today_chip.setOnClickListener(v -> {
             chip_count = 1;
             setDue_date(chip_count);
        });
        Chip tomorrow_chip = findViewById(R.id.tomorrow_chip);
        tomorrow_chip.setOnClickListener(v -> {
            chip_count =2 ;
            setDue_date(chip_count);
        });
        Chip nextWeek_chip = findViewById(R.id.nextWeek_chip);
        nextWeek_chip.setOnClickListener(v -> {
            chip_count = 3;
            setDue_date(chip_count);
        });


        if(getIntent().hasExtra("task_id_intent")){
            allow_edit=true;
            long task_id = getIntent().getLongExtra("task_id_intent",0) ;
            Log.d("TAG", getIntent().toString() );
            taskViewModel.getTask(task_id).observe(this, task -> {
                if(task!=null) {
                    originalTask = task.getTask().toString();
                    enter_task.setText(originalTask);
                    setDue_date(chip_count);
                    due_Date = task.getDue_date();
                    Log.d("date", "onCreate: "+ Utils.FormatDate(due_Date));
                }
                else {
                    Toast.makeText(BottomSheetFragment.this , "something is wrong" , Toast.LENGTH_SHORT).show();
                }
            });
        }


        saveTask_button.setOnClickListener(v -> {
        String task  = enter_task.getText().toString().trim();
            Log.d("onupdate", "onUPDATEtry: " + task);
        if(allow_edit){
            if(!TextUtils.isEmpty(task)&& due_Date!=null){
                Task updated_task = new Task();
                updated_task.setId(getIntent().getLongExtra("task_id_intent",0));
                updated_task.setTask(task);
                updated_task.setDate_created(Calendar.getInstance().getTime());
                updated_task.setDone(false);
                updated_task.setDue_date(due_Date);
                updated_task.setPriority(priority);
                TaskViewModel.update(updated_task);
                Log.d("onupdate", "onUPDATEtry: " + "its going fine");
                finish();
            }
        }else {
            if (!TextUtils.isEmpty(task) && due_Date != null) {
                Task mytask = new Task(task, priority, due_Date, Calendar.getInstance().getTime(), false);
                TaskViewModel.insert(mytask);
                finish();
            } else {
                Toast.makeText(BottomSheetFragment.this, "Enter all fields", Toast.LENGTH_SHORT).show();
            }
        }
        });

        setDueDate_button.setOnClickListener(v -> {
            calendar_group.setVisibility(
                    (calendar_group.getVisibility()== View.GONE) ? View.VISIBLE : View.GONE
            );

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),0);
        });

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            calendar.clear();
            calendar.set(year,month,dayOfMonth);
            due_Date =calendar.getTime();

        });

        SetPriority_button.setOnClickListener(v -> {
                   radioGroup.setVisibility(
                           radioGroup.getVisibility()==View.GONE ? View.VISIBLE : View.GONE
                   );
                   radioGroup.setOnCheckedChangeListener((group, checkedId) -> {

                       if(radioGroup.getVisibility()==View.VISIBLE){
                           if(checkedId == R.id.radioButton_high){
                               priority = Priority.HIGH;
                           }
                           else if(checkedId == R.id.radioButton_med){
                               priority = Priority.MEDIUM;
                           }
                           else if(checkedId == R.id.radioButton_low){
                               priority = Priority.LOW;
                           }else{
                               priority = Priority.LOW;
                           }
                       }

                   });

        });


    }

    private void setDue_date(int chip_count) {
        if(chip_count==1){
            calendar.add(Calendar.DAY_OF_YEAR,0);
            due_Date = calendar.getTime();

        }else if(chip_count==2){
            calendar.add(Calendar.DAY_OF_YEAR,1);
            due_Date = calendar.getTime();

        } else if (chip_count==3){
            calendar.add(Calendar.DAY_OF_YEAR ,7);
            due_Date = calendar.getTime();

        }
    }

}