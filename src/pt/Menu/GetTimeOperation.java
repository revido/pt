package pt.Menu;

import pt.Manager;

public class GetTimeOperation implements Operation {
    private final Manager man;

    public GetTimeOperation(Manager man) {
        this.man = man;
    }

    @Override
    public void execute(String[] params) {
        man.showTime();
    }
}
