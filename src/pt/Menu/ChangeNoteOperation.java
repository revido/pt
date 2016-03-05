package pt.Menu;

import pt.taskManagement.TodoManager;

public class ChangeNoteOperation implements Operation {
    private final TodoManager man;

    public ChangeNoteOperation(TodoManager man ) {
        this.man = man;
    }

    @Override
    public void execute(String[] params) {
        try {
            man.switchPos(Integer.parseInt(params[0]), Integer.parseInt(params[1]));
            int id = Integer.parseInt(params[0]);
            String note = params[1];
            man.changeNote(id,note);
        } catch (Exception e) {
            System.err.println("No or incorrect values specified.");
        }
    }
}
