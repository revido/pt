package pt.Menu;

import pt.Manager;

public class SwitchOperation implements Operation {
    private final Manager man;

    public SwitchOperation(Manager man) {
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
