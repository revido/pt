package pt.Menu;

import pt.PtManager;
import pt.taskManagement.TodoManager;

public class ListOperation implements Operation {
    private final TodoManager man;

    public ListOperation(TodoManager man) {
        this.man = man;
    }

    @Override
    public void execute(String[] params) {
        man.displayTasks();
    }
}
