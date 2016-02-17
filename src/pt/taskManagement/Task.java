package pt.taskManagement;

import java.util.Date;

// Provides basic information for sub-tasks.
// Tasks are linked to each other which also acts as a prioritized completion list
public abstract class Task {

    private int pomodoros;
    private String name;
    private String note;
    private final Date date;

    private Task next;

    // name, note != null && name != ""
    // Creates a task. if "unplanned" is true then this task is unplanned & urgent
    Task(String name, String notes, int pomodoros) {
        this.name = name;
        this.note = notes;
        this.pomodoros = pomodoros;
        this.date = new Date();
    }

    public Task(Task t) {
        this.name = t.getName();
        this.note = t.getNote();
        this.pomodoros = t.getPomodoros();
        this.next = t.getNext();
        this.date = t.getDate();
    }

    Task(String name, String note, int pomodoros, Date date) {
        this.name = name;
        this.note = note;
        this.pomodoros = pomodoros;
        this.date = date;
    }

    // Returns the name of the task
    public String getName() {
        return name;
    }

    // name != null && name != "" && name.length() <= 100
    // Renames the task
    public void setName(String name) {
        this.name = name;
    }

    // Returns the additional info for this task
    public String getNote() {
        return note;
    }

    // note != null && note.length() <= 255
    // Rewrites the note field of the task
    public void setNote(String note) {
        this.note = note;
    }

    // Returns the ammount of pomodoros needed to complete the task
    public int getPomodoros() {
        return pomodoros;
    }

    // pomodoros >= 0
    // Sets the pomodoros needed/completed. Used only for switching tasks
    public void setPomodoros(int pomodoros) {
        this.pomodoros = pomodoros;
    }

    // Returns the next linked task
    public Task getNext() {
        return next;
    }

    // Sets the next task that needs to be completed.
    public void setNext(Task next) {
        this.next = next;
    }

    @Override
    public String toString() {
        String s = name;
        if (next != null)
            return name + next.toString();

        return s;
    }

    public Date getDate() {
        return date;
    }
}
