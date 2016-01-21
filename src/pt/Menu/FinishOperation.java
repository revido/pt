package pt.Menu;

import pt.Manager;

public class FinishOperation implements Operation {
    private final Manager man;

    public FinishOperation(Manager man) {
        this.man = man;
    }

    @Override
    public void execute(String[] params) {
        man.finish();
    }
}
