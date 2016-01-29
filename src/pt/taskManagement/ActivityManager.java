package pt.taskManagement;

public class ActivityManager extends Manager {
    // name, notes != null && !name.equals("")
    // Adds a Task to the end of the list
    public void add(String name, String notes, int pomodoros, boolean unplanned) {
        ActivityTask temp = new ActivityTask(name, notes, pomodoros, unplanned);
        super.add(temp);
    }

    public void setUnplanned(int id, boolean unplanned) {
        ActivityTask t = (ActivityTask) getTaskFromId(id);

        t.setUnplanned(unplanned);
    }
}
