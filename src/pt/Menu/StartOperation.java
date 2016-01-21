package pt.Menu;

import pt.Manager;

public class StartOperation implements Operation {
    private final Manager man;

    public StartOperation(Manager man) {
        this.man = man;
    }

    @Override
    public void execute(String[] params) {
        man.timer();
    }
}
