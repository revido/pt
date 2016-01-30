package pt.Menu;

import pt.taskManagement.TodoManager;

public class RemoveOperation implements Operation {
    private final TodoManager man;

    public RemoveOperation(TodoManager man) {
        this.man = man;
    }

    @Override
    public void execute(String[] params) {
        try {
            this.man.remove(Integer.parseInt(params[0]));
        } catch (Exception e) {
            System.err.println("No or incorrect values specified.");
        }
    }
}
