package pt.Menu;

import pt.PtManager;

public class GetTimeOperation implements Operation {
    private final PtManager man;

    public GetTimeOperation(PtManager man) {
        this.man = man;
    }

    @Override
    public void execute(String[] params) {
        man.showTime();
    }
}
