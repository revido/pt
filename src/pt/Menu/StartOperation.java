package pt.Menu;

import pt.PtManager;

public class StartOperation implements Operation {
    private final PtManager man;

    public StartOperation(PtManager man) {
        this.man = man;
    }

    @Override
    public void execute(String[] params) {
        man.timer();
    }
}
