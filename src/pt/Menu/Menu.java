package pt.Menu;

import pt.PtManager;
import pt.config.ConfigManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Menu {
    private final HashMap<String, Operation> operationList;
    private final PtManager man;
    private final BufferedReader in;

    public Menu(ConfigManager confMan) {
        man = new PtManager(confMan.getConfig());
        operationList = new HashMap<>();
        in = new BufferedReader(new InputStreamReader(System.in));

        createMenu();
    }

    // Runs the program menu
    public void runProgram() {
        String s;

        try {
            System.out.print("pt> ");

            while ((s = in.readLine()) != null) {
                if (s.equals("help")) {
                    displayAllCommands();
                } else {
                    executeCommand(new Command(s));
                }
                System.out.print("pt> ");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
/*
    // doesnt work on java 1.7
    private void displayAllCommands() {
        for (HashMap.Entry<String, Operation> entry : operationList.entrySet()) {
            System.out.println(entry.getKey());
        }
    }
    */

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

    // Creates a HashMap of menu items with their operations
    private void createMenu() {
        operationList.put("add", new AddOperation(man.getTodoMan()));
//        operationList.put("activity", new ActivityOperation(actMan));
//        operationList.put("act", new ActivityOperation(actMan));
        operationList.put("done", new FinishOperation(man.getTodoMan()));
        operationList.put("exit", new ExitOperation(man));
//        operationList.put("get", new GetTaskOperation(man.getTodoMan()));
        operationList.put("ls", new ListOperation(man.getTodoMan()));
//        operationList.put("lsh", new ListHistoryOperation(man));
        operationList.put("mark", new MarkOperation(man.getTodoMan()));
        operationList.put("quit", new ExitOperation(man));
        operationList.put("resume", new StartOperation(man));
        operationList.put("rm", new RemoveOperation(man.getTodoMan()));
//        operationList.put("rmall", new RemoveAllOperation(man));
        operationList.put("save", new SaveOperation(man));
        operationList.put("start", new StartOperation(man));
        operationList.put("switch", new SwitchOperation(man.getTodoMan()));
        operationList.put("sw", new SwitchOperation(man.getTodoMan()));
        operationList.put("t", new GetTimeOperation(man));
        operationList.put("timer", new GetTimeOperation(man));
//        operationList.put("undoChanges", new UndoOperation(man));
    }
}
