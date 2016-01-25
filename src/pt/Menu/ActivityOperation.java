package pt.Menu;

import pt.Manager;
import pt.TaskListState;
import pt.activityInventory.ActivityManager;

import java.io.BufferedReader;

public class ActivityOperation implements Operation {
    BufferedReader in;
    TaskListState state;

    public ActivityOperation(BufferedReader in, TaskListState state) {
        this.in = in;
    }

    @Override
    public void execute(String[] params) {
        ActivityManager actMan = new ActivityManager();
        actMan.run(state);
    }
}
