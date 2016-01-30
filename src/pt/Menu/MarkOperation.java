package pt.Menu;

import pt.taskManagement.TodoManager;

public class MarkOperation implements Operation {
    private final TodoManager man;

    public MarkOperation(TodoManager man) {
        this.man = man;
    }

    @Override
    public void execute(String[] params) {
        man.mark();
    }
}
