package pt.taskManagement;

// Represents a task in the Activity Inventory. These taska are not trackable.
// Their purpose is to schedule todays tasks and to estimate their effort of completion.
public class ActivityTask extends Task{
    private boolean unplanned;

    // name, note != null && name != "" && pomodoros >= 0
    // Creates a task. if "unplanned" is true then this task is unplanned & urgent
    public ActivityTask(String name, String notes, int pomodoros, boolean unplanned) {
        super(name, notes, pomodoros);
        this.setUnplanned(unplanned);
    }

// --Commented out by Inspection START (3/6/16 12:30 AM):
//    public ActivityTask(ActivityTask t) {
//        this(t.getName(), t.getNote(), t.getPomodoros(), t.getUnplanned());
//    }
// --Commented out by Inspection STOP (3/6/16 12:30 AM)

    // Returns true if this particulat task is an unplanned & urgent task
    private boolean getUnplanned() { return unplanned; }

    // Reset the unplanned & urgent option
    public void setUnplanned(boolean unplanned) { this.unplanned = unplanned; }
}
