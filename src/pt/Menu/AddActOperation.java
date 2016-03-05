package pt.Menu;

import pt.taskManagement.Manager;

import java.util.Scanner;

public class AddActOperation implements Operation {
    private final Manager man;

    public AddActOperation(Manager man) {
        this.man = man;
    }

    @Override
    public void execute(String[] params) {
        if (params.length == 1) {
            man.add(params[0], "", -1);
        } else {
            Scanner scanner = new Scanner(System.in);
            String s;

            System.out.print("Name: ");
            String name = scanner.nextLine();


            System.out.print("Effort [1]: ");
            s = scanner.nextLine();
            int effort = s.equals("") ? 1 : Integer.parseInt(s);

            System.out.print("Note [empty]: ");
            String note = scanner.nextLine();

            System.out.print("Position [last]: ");
            s = scanner.nextLine();
            int pos = s.equals("") ? -1 : Integer.parseInt(s);

            man.addAct(name, note, effort, pos);
        }
    }

}
