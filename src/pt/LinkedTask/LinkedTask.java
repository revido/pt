package pt.LinkedTask;

import java.util.Date;

// Represents a a time-trackable task
public class LinkedTask extends Task{
    private Date date;
    private boolean done;

    // name, note != null && name != ""
    // Creates a task. if "unplanned" is true then this task is unplanned & urgent
    public LinkedTask(String name, String notes) {
        super(name,notes);
        this.date = new Date();
    }

    // Mark the task as finished. A finished task is not trackable.
    public void finishTask() { done = true; }

    // Returns the creation date of the task
    public Date getDate() { return date; }

    // Returns true if the task is finished.
    public boolean isDone() { return done; }
}
