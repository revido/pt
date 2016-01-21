package pt;

import pt.Menu.*;
import pt.config.ConfigManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

class Pt {
    private final Manager man;
    private final ConfigManager confMan;

    public Pt() {
        shutDownHook();
        confMan = new ConfigManager();
        confMan.load();
        man = new Manager(confMan.getConfig());
        operationList = new HashMap<>();
        createMenu();
    }

    private final HashMap<String, Operation> operationList;

    public void runProgram() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        Operation o;
        String s;

        System.out.print("pt> ");
        try {
            while (true) {
                s = in.readLine();
                if (s == null) {
                    System.out.println("NULL");
                    man.closeConnection();
                    return;
                }

                String[] arguments = s.split(" ");
                if (operationList.containsKey(arguments[0])) {
                    o = operationList.get(arguments[0]);
                    o.execute(arguments);
                } else if (!arguments[0].equals(""))
                    System.out.println("Unknown command.");

                System.out.print("pt> ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createMenu() {
        operationList.put("start", new StartOperation(man));
        operationList.put("resume", new StartOperation(man));
        operationList.put("timer", new GetTimeOperation(man));
        operationList.put("t", new GetTimeOperation(man));
        operationList.put("add", new AddOperation(man));
        operationList.put("ls", new ListOperation(man));
        operationList.put("lsh", new ListHistoryOperation(man));
        operationList.put("undo", new UndoOperation(man));
        operationList.put("save", new SaveOperation(man));
        operationList.put("done", new FinishOperation(man));
        operationList.put("mark", new MarkOperation(man));
        operationList.put("get", new GetTaskOperation(man));
        operationList.put("switch", new SwitchOperation(man));
        operationList.put("rm", new RemoveOperation(man));
        operationList.put("rmall", new RemoveAllOperation(man));
        operationList.put("exit", new ExitOperation(man));
        operationList.put("quit", new ExitOperation(man));
    }

    //Used for SIGINT (CTRL + C)
    private void shutDownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                man.closeConnection();
            }
        });
    }

}
