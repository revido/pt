package pt.activityInventory;

import pt.Menu.*;
import pt.TaskListState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ActivityManager {
    public void run(TaskListState state) {
        String s;
        ActivityState act = new ActivityState(state);
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.print("pt.act> ");
            while ((s = in.readLine()) != null) {

                if(s.equals("quit")) {
                    break;
                }
                else if(!s.equals("")) {
                    switch (s) {
                        case "quit":
                            break;
                        case "add":
                            AddOperation op = new AddOperation(act);
                            op.execute();
                            break;
                        case "ls":
                            act.displayCurrentTasks();
                            break;
                        case "switch":
                            act.switchPos(1, 2);
                            break;
                        case "remove":
                            act.remove(1);
                            break;
                        case "import":
                            act.importToPt();
                        default:
                            System.out.println("Unknown command.");
                            break;
                    }
                }
                System.out.print("pt.act> ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
