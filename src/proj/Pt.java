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
                    case "": System.out.print("pt> ");break;
                    case "start": f.startMenu(in); break;
                    case "timer":
                    case "t": f.getTime(); System.out.print("pt> ");break;
                    case "add": f.addMenu(arguments); System.out.print("pt> ");break;
                    case "ls": f.listMenu(); System.out.print("pt> ");break;
                    case "lsh": f.listHistoryMenu(); System.out.print("pt> ");break;
                    case "undo": f.undoMenu(); System.out.print("pt> ");break;
                    case "save": f.saveMenu(); System.out.print("pt> ");break;
                    case "done": f.finishMenu(); System.out.print("pt> ");break;
                    case "mark": f.markMenu(); System.out.print("pt> ");break;
                    case "get": f.getMenu(); System.out.print("pt> ");break;
                    case "switch": f.switchMenu(arguments); System.out.print("pt> ");break;
                    case "rm": f.removeMenu(arguments); System.out.print("pt> ");break;
                    case "rmall": f.removeAllMenu(); System.out.print("pt> ");break;
                    case "exit":
                    case "quit": f.exitMenu(); break;
                    default: System.out.println("Unknown command.");
                }
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
