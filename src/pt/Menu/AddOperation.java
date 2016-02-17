package pt.Menu;

import pt.taskManagement.TodoManager;

import java.util.Scanner;

public class AddOperation implements Operation {
    private final TodoManager man;

    public AddOperation(TodoManager man) {
        this.man = man;
    }

    @Override
    public void execute(String[] params) {
        if (params.length == 1) {
            man.add(params[0], "", -1);
        } else {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Name: ");
            String name = scanner.nextLine();

            System.out.print("Position [last]: ");
            String s = scanner.nextLine();
            int pos = s.equals("") ? -1 : Integer.parseInt(s);

            System.out.print("Note [empty]: ");
            String note = scanner.nextLine();

            man.add(name, note, pos);
        }
    }

}
