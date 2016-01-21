package pt.Menu;

import pt.Manager;

public class SaveOperation implements Operation {
    private final Manager man;

    public SaveOperation(Manager man) {
        this.man = man;
    }

    @Override
    public void execute(String[] params) {
        man.saveTodayTasks();
    }
}
