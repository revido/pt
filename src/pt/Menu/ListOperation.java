package pt.Menu;

import pt.Manager;

public class ListOperation implements Operation {
    private final Manager man;

    public ListOperation(Manager man) {
        this.man = man;
    }

    @Override
    public void execute(String[] params) {
        man.list();
    }
}
