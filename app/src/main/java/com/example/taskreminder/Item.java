package com.example.taskreminder;

import android.widget.CheckBox;

public class Item {
    String taskName;
    String taskCategory;
    String taskStartingDate;
    String taskStartingTime;

    public Item(String taskName, String taskStartingTime, String taskCategory, String taskStartingDate) {
        this.taskName = taskName;
        this.taskCategory = taskCategory;
        this.taskStartingTime = taskStartingTime;
        this.taskStartingDate = taskStartingDate;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskStartingTime() {
        return taskStartingTime;
    }

    public void setTaskStartingTime(String taskStartingTime) {
        this.taskStartingTime = taskStartingTime;
    }
}
