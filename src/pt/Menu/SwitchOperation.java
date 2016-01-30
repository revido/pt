package pt.Menu;

import pt.PtManager;
import pt.taskManagement.TodoManager;

public class SwitchOperation implements Operation {
    private final TodoManager man;

    public SwitchOperation(TodoManager man ) {
        this.man = man;
    }

    @Override
    public void execute(String[] params) {
        try {
            man.switchPos(Integer.parseInt(params[0]), Integer.parseInt(params[1]));
        } catch (Exception e) {
            System.err.println("No or incorrect values specified.");
        }
    }
}
