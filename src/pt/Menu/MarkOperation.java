package pt.Menu;

import pt.Manager;

public class MarkOperation implements Operation {
    private final Manager man;

    public MarkOperation(Manager man) {
        this.man = man;
    }

    @Override
    public void execute(String[] params) {
        man.mark();
    }
}
