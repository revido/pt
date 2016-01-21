package pt.Menu;

import pt.Manager;

public class AddOperation implements Operation {
    private final Manager man;

    public AddOperation(Manager man) {
        this.man = man;
    }

    @Override
    public void execute(String[] params) {
         try {
            if (params.length <= 1) {
                System.err.println("No or incorrect values specified.");
            } else {
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
        } catch (Exception e) {
            System.err.println("No or incorrect values specified.");
        }
    }
}
