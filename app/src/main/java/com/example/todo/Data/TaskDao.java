package com.example.todo.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    void insert(Task task);

    @Query("DELETE FROM task_table")
    void deleteAll();

    @Delete
    void delete(Task task);

    @Update
    void update(Task task);

    @Query("SELECT * FROM task_table")
    LiveData<List<Task>> getAllTask();

    @Query("SELECT * FROM task_table WHERE task_table.task_id==:id")
    LiveData<Task> getTask(long id);


}
