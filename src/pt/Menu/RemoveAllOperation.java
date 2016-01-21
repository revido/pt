package pt.Menu;

import pt.Manager;

public class RemoveAllOperation implements Operation {
    private final Manager man;

    public RemoveAllOperation(Manager man) {
        this.man = man;
    }

    @Override
    public void execute(String[] params) {
        man.removeAllTasks();
    }
}
