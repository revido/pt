package proj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Menu {
    private final Manager man;

    public Menu(Manager man) {
        this.man = man;
    }

    public void addMenu(String[] params) {
        String s = "";
        String n = "";
        Boolean notes = false;
        for (int i = 1; i < params.length; i++) {
            if (params[i].equals("-n")) {
                notes = true;
                continue;
            }

            if (notes) {
                n += params[i] + " ";
            } else
                s += params[i] + " ";
        }
        s = s.trim();
        n = n.trim();

        man.add(s, n);
    }

    public void finishMenu() {
        this.man.finish();
    }

    public void exitMenu() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            if (man.isChanged()) {
                System.out.print("Save changes?\n[Y/N]> ");
                String ans = in.readLine();
                if (ans.equals("Y") || ans.equals("y")) {
                    man.saveTodayTasks();
                }
            }
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getMenu() {
        this.man.getCurrentTask();
    }

    public void listMenu() {
        this.man.list();
    }

    public void markMenu() {
        this.man.mark();
    }

    public void removeMenu(String[] params) {
        this.man.remove(Integer.parseInt(params[1]));
    }

    public void removeAllMenu() {
        this.man.removeAllTasks();
    }

    public void listHistoryMenu() {
        this.man.listHistory();
    }

    public void saveMenu() {
        man.saveTodayTasks();
    }

    public void startMenu() {
        man.startPomodoro();
    }

    public void switchMenu(String[] params) {
        man.switchPos(Integer.parseInt(params[1]), Integer.parseInt(params[2]));
    }

    public void undoMenu() {
        man.undoChanges();
    }
}
