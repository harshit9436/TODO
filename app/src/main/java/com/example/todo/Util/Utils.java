package com.example.todo.Util;

import android.graphics.Color;

import com.example.todo.Data.Priority;
import com.example.todo.Data.Task;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static String FormatDate(Date date){
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        simpleDateFormat.applyPattern("EEE MM d");
        return simpleDateFormat.format(date);
    }

    public static int priorityColor(Task task) {
        int color;
        if(task.getPriority()== Priority.HIGH){
            color = Color.argb(200,236,11,11);
        } else if(task.getPriority()==Priority.MEDIUM){
            color =Color.argb(250,244,204,28);
        } else if(task.getPriority()==Priority.LOW){
            color = Color.argb(250,59,228,65);
        }
        else {
            color = Color.GRAY;
        }
        return color;
    }
}
