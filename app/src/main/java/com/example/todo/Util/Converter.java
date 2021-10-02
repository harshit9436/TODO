package com.example.todo.Util;

import androidx.room.TypeConverter;

import com.example.todo.Data.Priority;

import java.util.Date;

public class Converter {
    @TypeConverter
    public static Date fromTimeStamp(Long value){
    return value==null ? null : new Date(value);
    }

    @TypeConverter
    public static Long fromTimeStamp(Date date){
        return date==null ? null : date.getTime();
    }

    @TypeConverter
    public static String fromPriority(Priority priority){
        return priority==null ? null : priority.name();
    }

    @TypeConverter
    public static Priority toPriority(String priority){
        return priority==null ? null : Priority.valueOf(priority);
    }
}
