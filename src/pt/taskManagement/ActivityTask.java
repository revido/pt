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

    public ActivityTask(ActivityTask t) {
        this(t.getName(), t.getNote(), t.getPomodoros(), t.getUnplanned());
    }

//    // t != null
//    // Overrides the info from this to "to" task. Only used for switching positions of two tasks
//    @Override
//    public ActivityTask changeToTask(Task t) {
//        ActivityTask save = this;
//
//        ActivityTask to = (ActivityTask) t;
//
//        this.setName(to.getName());
//        this.setNote(to.getNote());
//        this.setPomodoros(to.getPomodoros());
//
//        this.setUnplanned(to.getUnplanned());
//
//        return save;
//    }

    // Returns true if this particulat task is an unplanned & urgent task
    public boolean getUnplanned() { return unplanned; }

    // Reset the unplanned & urgent option
    public void setUnplanned(boolean unplanned) { this.unplanned = unplanned; }
}
