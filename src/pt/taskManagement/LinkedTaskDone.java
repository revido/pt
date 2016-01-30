package pt.taskManagement;

import java.sql.Timestamp;
import java.util.Date;

// Represents a a time-trackable task
public class LinkedTaskDone extends Task {
    private Date date;

    // name, note != null && name != ""
    // Creates a finished task
    public LinkedTaskDone(String name, String notes, int pomodoros, Date date) {
        super(name, notes, pomodoros);
        this.date = date;
    }

    public LinkedTaskDone(TodoTask head) {
        this(head.getName(), head.getNote(), head.getPomodoros(), head.getDate());
    }

    // Returns the creation date of the task (not the finish date)
    public Date getDate() {
        return date;
    }
}
