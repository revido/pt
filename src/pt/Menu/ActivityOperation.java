package pt.Menu;

import pt.activityInventory.ActivityManager;

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
