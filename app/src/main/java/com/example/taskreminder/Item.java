package com.example.taskreminder;

public class Item {
    String taskName;
    String taskStartingTime;

    public Item(String taskName, String taskStartingTime) {
        this.taskName = taskName;
        this.taskStartingTime = taskStartingTime;
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
