package pt.Menu;

import pt.PtManager;

public class SaveOperation implements Operation {
    private final PtManager man;

    public SaveOperation(PtManager man) {
        this.man = man;
    }

    @Override
    public void execute(String[] params) {
        man.saveTasks();
    }
}
