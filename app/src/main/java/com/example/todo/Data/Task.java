package com.example.todo.Data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "task_table")
public class Task {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "task_id")
    private long id;

    private String task;

    @ColumnInfo(name = "priority")
    private Priority priority;

    @ColumnInfo(name = "Due_Date")
    private Date due_date;

    @ColumnInfo(name = "created_at")
    private Date date_created;

    private boolean isDone;

    public Task(){}
    public Task(String  task, Priority priority, Date due_date, Date date_created, boolean isDone) {
        this.task = task;
        this.priority = priority;
        this.due_date = due_date;
        this.date_created = date_created;
        this.isDone = isDone;

    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", task=" + task +
                ", due_date=" + due_date +
                ", date_created=" + date_created +
                ", isDone=" + isDone +
                ", priority=" + priority +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String  getTask() {
        return task;
    }

    public void setTask(String  task) {
        this.task = task;
    }

    public Date getDue_date() {
        return due_date;
    }

    public void setDue_date(Date due_date) {
        this.due_date = due_date;
    }

    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}
