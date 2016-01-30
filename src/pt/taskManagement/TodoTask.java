package pt.taskManagement;

import java.sql.Timestamp;
import java.util.Date;

// Represents a a time-trackable task
public class TodoTask extends Task {

    // name, note != null && name != ""
    // Creates a task. if "unplanned" is true then this task is unplanned & urgent
    public TodoTask(String name, String notes, int pomodoros) {
        super(name, notes, pomodoros);
    }

    // t != null
    // Creates a new task with information from task t
    public TodoTask(TodoTask t) {
        this(t.getName(), t.getNote(), t.getPomodoros());
    }

    // Import task from Database
    public TodoTask(Date date, String name, int pomodoros, String note) {
        super(name, note, pomodoros);
    }

    // Adds another pomodoro, meaning that the timer finished
    public void addMark() {
        setPomodoros(getPomodoros() + 1);
    }
}
