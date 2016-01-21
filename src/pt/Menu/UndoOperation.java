package pt.Menu;

import pt.Manager;

public class UndoOperation implements Operation {
    private final Manager man;

    public UndoOperation(Manager man) {
        this.man = man;
    }

    @Override
    public void execute(String[] params) {
        man.undoChanges();
    }
}
