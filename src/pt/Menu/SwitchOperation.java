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
            man.switchPos(Integer.parseInt(params[1]), Integer.parseInt(params[2]));
        } catch (Exception e) {
            System.err.println("No or incorrect values specified.");
        }
    }
}
