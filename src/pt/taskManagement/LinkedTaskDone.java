package pt.taskManagement;

import java.util.Date;

// Represents a a time-trackable task
public class LinkedTaskDone extends Task {
    private Date date;

    // name, note != null && name != ""
    // Creates a task. if "unplanned" is true then this task is unplanned & urgent
    public LinkedTaskDone(String name, String notes, int pomodoros, Date date) {
        super(name, notes, pomodoros);
        this.date = date;
    }

    public LinkedTaskDone(TodoTask head) {
        this(head.getName(), head.getNote(), head.getPomodoros(), head.getDate());
    }
}
