package pt;

import pt.Menu.*;
import pt.activityInventory.ActivityManager;
import pt.config.ConfigManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

class Pt {
    private final Manager man;
    private final ActivityManager actMan;
    private final ConfigManager confMan;
    private final HashMap<String, Operation> operationList;

    // Initialization of Pt
    public Pt() {
        shutDownHook();

        confMan = new ConfigManager();
        confMan.load();
        actMan = new ActivityManager();
        man = new Manager(confMan.getConfig(), actMan);

        operationList = new HashMap<>();
        createMenu();
    }

    // Runs the program menu
    public void runProgram() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
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
        man.closeConnection();
    }

    private void displayAllCommands() {
        for (Map.Entry<String, Operation> entry : operationList.entrySet()) {
            System.out.println(entry.getKey());
        }
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
        operationList.put("add", new AddOperation(man));
        operationList.put("activity", new ActivityOperation());
        operationList.put("act", new ActivityOperation(man));
        operationList.put("done", new FinishOperation(man));
        operationList.put("exit", new ExitOperation(man));
        operationList.put("get", new GetTaskOperation(man));
        operationList.put("ls", new ListOperation(man));
        operationList.put("lsh", new ListHistoryOperation(man));
        operationList.put("mark", new MarkOperation(man));
        operationList.put("quit", new ExitOperation(man));
        operationList.put("resume", new StartOperation(man));
        operationList.put("rm", new RemoveOperation(man));
        operationList.put("rmall", new RemoveAllOperation(man));
        operationList.put("save", new SaveOperation(man));
        operationList.put("start", new StartOperation(man));
        operationList.put("switch", new SwitchOperation(man));
        operationList.put("t", new GetTimeOperation(man));
        operationList.put("timer", new GetTimeOperation(man));
        operationList.put("undoChanges", new UndoOperation(man));
    }

    // Catches SIGINT (program shutdown) and closes the database
    private void shutDownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                if (man != null)
                    man.closeConnection();
            }
        });
    }

}
