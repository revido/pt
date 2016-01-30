package pt.Menu;

import pt.PtManager;
import pt.taskManagement.TodoManager;

public class FinishOperation implements Operation {
    private final TodoManager man;

    public FinishOperation(TodoManager man) {
        this.man = man;
    }

    @Override
    public void execute(String[] params) {
        man.finish();
    }
}
