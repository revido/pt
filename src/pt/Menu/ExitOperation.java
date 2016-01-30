package pt.Menu;

import pt.PtManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ExitOperation implements Operation {
    private final PtManager man;

    public ExitOperation(PtManager man) {
        this.man = man;
    }

    @Override
    public void execute(String[] params) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            if (man.isChanged()) {
                System.out.print("Save changes?\n[Y/N]> ");
                String ans = in.readLine();
                if (ans.equals("Y") || ans.equals("y")) {
                    man.saveTasks();
                }
            }
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
