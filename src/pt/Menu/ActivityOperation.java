package pt.Menu;

import pt.TaskListState;
import pt.activityInventory.ActivityManager;

import java.io.BufferedReader;

public class ActivityOperation implements Operation {
    ActivityManager actMan;

    public ActivityOperation(ActivityManager actMan) {
        this.actMan = actMan;
    }

    @Override
    public void execute(String[] params) {
        actMan.run();
    }
}
