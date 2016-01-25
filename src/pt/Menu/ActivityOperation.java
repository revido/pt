package pt.Menu;

import pt.Manager;

public class ActivityOperation implements Operation {
    private final Manager man;

    public ActivityOperation(Manager man) {
        this.man = man;
    }

    @Override
    public void execute(String[] params) {
        man.launchActivityEditor();
    }
}
