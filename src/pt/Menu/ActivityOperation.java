package pt.Menu;

import pt.PtManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class ActivityOperation implements Operation {
    private final HashMap<String, Operation> operationList;
    private final BufferedReader in;
    private final PtManager man;

    public ActivityOperation(PtManager man, BufferedReader in) {
        this.man = man;
        operationList = new HashMap<>();
        this.in = in;
        createMenu();
    }

    @Override
    public void execute(String[] params) {
        String s;

        try {
            System.out.print("act> ");

            while ((s = in.readLine()) != null) {
                if(s.equals("exit") || s.equals("quit"))
                    break;
                if (s.equals("help")) {
                    displayAllCommands();
                } else {
                    executeCommand(new Command(s));
                }
                System.out.print("act> ");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void displayAllCommands() {
        System.out.println("Not implemented.");
    }

    // Requires valid non-null command
    // Executes a given command
    private void executeCommand(Command cmd) {
        if (operationList.containsKey(cmd.getCommand())) {
            Operation o = operationList.get(cmd.getCommand());
            o.execute(cmd.getParams());
        } else if (!cmd.getCommand().equals(""))
            System.out.println("Unknown command.");
    }
    private void createMenu() {
        operationList.put("add", new AddActOperation(man.getActMan()));
        operationList.put("rename", new ChangeNameOperation(man.getActMan()));
        operationList.put("ls", new ListOperation(man.getActMan()));
        operationList.put("rm", new RemoveOperation(man.getActMan()));
//        operationList.put("rmall", new RemoveAllOperation(man));
        operationList.put("save", new SaveOperation(man));
        operationList.put("switch", new SwitchOperation(man.getActMan()));
        operationList.put("sw", new SwitchOperation(man.getActMan()));
    }
}
