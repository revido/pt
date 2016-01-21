package pt.Menu;

import pt.Manager;

public class RemoveOperation implements Operation {
    private final Manager man;

    public RemoveOperation(Manager man) {
        this.man = man;
    }

    @Override
    public void execute(String[] params) {
        try {
            this.man.remove(Integer.parseInt(params[1]));
        } catch (Exception e) {
            System.err.println("No or incorrect values specified.");
        }
    }
}
