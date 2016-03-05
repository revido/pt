package pt.Menu;

import pt.taskManagement.Manager;

public class ChangeNameOperation implements Operation {
    private final Manager man;

    public ChangeNameOperation(Manager man ) {
        this.man = man;
    }

    @Override
    public void execute(String[] params) {
        try {
            man.switchPos(Integer.parseInt(params[0]), Integer.parseInt(params[1]));
            int id = Integer.parseInt(params[0]);
            String name = params[1];
            man.changeName(id,name);
        } catch (Exception e) {
            System.err.println("No or incorrect values specified.");
        }
    }
}
