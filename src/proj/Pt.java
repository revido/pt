package proj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Pt {
    private final Manager man;

    public Pt() {
        shutDownHook();
        man = new Manager();
    }

    public void runProgram() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        Menu f = new Menu(man);

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
                switch (arguments[0]) {
                    case "": break;
                    case "start": f.startMenu(in); break;
                    case "resume": f.resumeMenu(in); break;
                    case "timer":
                    case "t": f.getTimeMenu(); break;
                    case "debug": f.switchDebugMenu(); break;
                    case "add": f.addMenu(arguments); break;
                    case "ls": f.listMenu(); break;
                    case "lsh": f.listHistoryMenu(); break;
                    case "undo": f.undoMenu(); break;
                    case "save": f.saveMenu(); break;
                    case "done": f.finishMenu(); break;
                    case "mark": f.markMenu(); break;
                    case "get": f.getMenu(); break;
                    case "switch": f.switchMenu(arguments); break;
                    case "rm": f.removeMenu(arguments); break;
                    case "rmall": f.removeAllMenu(); break;
                    case "exit":
                    case "quit": f.exitMenu(); break;
                    default: System.out.println("Unknown command.");
                }
                System.out.print("pt> ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
