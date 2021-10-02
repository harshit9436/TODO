package com.example.todo.Model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.todo.Data.Task;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private final LiveData<List<Task>> allTasks;
    private static Repository repository;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allTasks = Repository.getAllTasks();
    }

    public LiveData<List<Task>> getAllTasks(){return allTasks;}
    public LiveData<Task> getTask(long id){
        return Repository.getTask(id);
    }
    public static void insert(Task task){
        repository.insert(task);
    }
    public static void delete(Task task){
        repository.delete(task);
    }
    public static void deleteAll(){
        repository.deleteAll();
    }
    public static void update(Task task){
        repository.update(task);
    }

}
