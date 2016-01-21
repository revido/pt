package pt.Menu;

import pt.Manager;

public class ListHistoryOperation implements Operation {
    private final Manager man;

    public ListHistoryOperation(Manager man) {
        this.man = man;
    }

    @Override
    public void execute(String[] params) {
        man.listHistory();
    }
}
