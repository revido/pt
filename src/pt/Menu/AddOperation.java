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
            if (params.length == 0) {
                System.err.println("No or incorrect values specified.");
            } else {
                String s = "";
                String n = "";
                Boolean notes = false;
                for (String param : params) {
                    if (param.equals("-n")) {
                        notes = true;
                        continue;
                    }

                    if (notes) {
                        n += param + " ";
                    } else
                        s += param + " ";
                }
                s = s.trim();
                n = n.trim();

                if(s.length() > 100) {
                    System.out.println("Please use a name that is less than 100 characters long.");
                    return;
                }

                man.add(s, n);
            }
        } catch (Exception e) {
            System.err.println("No or incorrect values specified.");
        }
    }
}
