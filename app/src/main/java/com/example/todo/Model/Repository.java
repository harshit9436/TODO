package com.example.todo.Model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.todo.Data.Task;
import com.example.todo.Data.TaskDao;
import com.example.todo.Util.TaskRoomDatabase;

import java.util.List;

public class Repository {
    private static LiveData<List<Task>> allTasks;
    private static TaskDao taskDao;

    public Repository(Application application) {
       TaskRoomDatabase taskRoomDatabase = TaskRoomDatabase.getDatabase(application);
        taskDao = taskRoomDatabase.taskDao();
        allTasks = taskDao.getAllTask();
    }
    public static LiveData<List<Task>> getAllTasks(){
        return allTasks;
    }

    public static LiveData<Task> getTask(long id){
        return taskDao.getTask(id);
    }

    public void insert(Task task){
        TaskRoomDatabase.databaseWriteExecutor.execute(()->
            taskDao.insert(task));
    }
    public void deleteAll(){
        TaskRoomDatabase.databaseWriteExecutor.execute(taskDao::deleteAll);
    }
    public void delete(Task task){
        TaskRoomDatabase.databaseWriteExecutor.execute(()->
                taskDao.delete(task));
    }

    public void update(Task task){
        TaskRoomDatabase.databaseWriteExecutor.execute(()->
                taskDao.update(task));
    }

}
