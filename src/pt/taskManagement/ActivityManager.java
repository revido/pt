package pt.taskManagement;

import pt.Table;

public class ActivityManager extends Manager {
// --Commented out by Inspection START (3/6/16 12:29 AM):
//    // name, notes != null && !name.equals("")
//    // Adds a Task to the end of the list
//    public void add(String name, String notes, int effort, boolean unplanned) {
//        ActivityTask temp = new ActivityTask(name, notes, effort, unplanned);
//        super.add(temp);
//    }
// --Commented out by Inspection STOP (3/6/16 12:29 AM)

// --Commented out by Inspection START (3/6/16 12:29 AM):
//    public void setUnplanned(int id, boolean unplanned) {
//        ActivityTask t = (ActivityTask) getTaskFromId(id);
//
//        t.setUnplanned(unplanned);
//        super.setChanged(false);
//    }
// --Commented out by Inspection STOP (3/6/16 12:29 AM)

    @Override
    public void displayTasks() {
        Table t = new Table();
        t.displayActivities(head);
    }
}
