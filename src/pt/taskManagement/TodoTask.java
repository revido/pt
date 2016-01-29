package pt.taskManagement;

import java.util.Date;

// Represents a a time-trackable task
public class TodoTask extends Task {
    private Date date;
    private boolean done;

    // name, note != null && name != ""
    // Creates a task. if "unplanned" is true then this task is unplanned & urgent
    public TodoTask(String name, String notes, int pomodoros) {
        super(name, notes, pomodoros);
        this.date = new Date();
    }

    // t != null
    // Creates a new task with information from task t
    public TodoTask(TodoTask t) {
        this(t.getName(), t.getNote(), t.getPomodoros());

        this.done = t.isDone();
        this.setDate(t.getDate());
    }

//    // t != null
//    // Overrides the info from this to "to" task. Only used for switching positions of two tasks
//    @Override
//    public TodoTask changeToTask(Task t) {
//        TodoTask save = new TodoTask(this);
//
//        TodoTask to = (TodoTask) t;
//
//        this.setName(to.getName());
//        this.setNote(to.getNote());
//        this.setPomodoros(to.getPomodoros());
//
//        this.done = to.isDone();
//        this.setDate(to.getDate());
//
//        return save;
//    }

    // Mark the task as finished. A finished task is not trackable.
    public void finishTask() {
        done = true;
    }

    // Returns the creation date of the task
    public Date getDate() {
        return date;
    }

    // date != null
    // Sets the new date for this task. Only used for switching positions of tasks
    public void setDate(Date date) {
        this.date = date;
    }

    // Returns true if the task is finished.
    public boolean isDone() {
        return done;
    }
}
