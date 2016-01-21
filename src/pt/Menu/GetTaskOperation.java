package pt.Menu;

import pt.Manager;

public class GetTaskOperation implements Operation {
    private final Manager man;

    public GetTaskOperation(Manager man) {
        this.man = man;
    }

    @Override
    public void execute(String[] params) {
        man.showCurrentTask();
    }
}
