package pt.LinkedTask;

// Represents a task in the Activity Inventory. These taska are not trackable.
// Their purpose is to schedule todays tasks and to estimate their effort of completion.
public class LinkedActivity extends Task{
    private boolean unplanned;

    // name, note != null && name != ""
    // Creates a task. if "unplanned" is true then this task is unplanned & urgent
    public LinkedActivity(String name, String notes, boolean unplanned) {
        super(name, notes);
        this.setUnplanned(unplanned);
    }

    // Returns true if this particulat task is an unplanned & urgent task
    public boolean getUnplanned() { return unplanned; }

    // Reset the unplanned & urgent option
    public void setUnplanned(boolean unplanned) { this.unplanned = unplanned; }
}
