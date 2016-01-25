package pt.activityInventory;

import java.util.Date;
import java.util.Scanner;

public class AddOperation {
    ActivityState state;

    public AddOperation(ActivityState sate) {
        this.state = sate;
    }

    public void execute() {
        Scanner scanner = new Scanner(System.in);
        String name;
        boolean unplanned;
        //time
        int effort = -1;
        ActivityUnplanned unplanned1 = null;

        System.out.print("Unplanned [no]:");
        String s = scanner.nextLine();
        if (s.equals("yes") || s.equals("y")) {

            unplanned1 = new ActivityUnplanned(true, new Date());
        } else if (s.equals("") || s.equals("n") || s.equals("no")) {
            unplanned = false;
        }
        System.out.print("Name: ");
        name = scanner.nextLine();

//        boolean correctEffort = true;
//        while (correctEffort) {
//            System.out.print("Effort: ");
//            if (scanner.hasNextInt()) {
//                effort = scanner.nextInt();
//                correctEffort = false;
//            } else {
//                System.out.println("Please use a number.");
//                correctEffort = true;
//            }
//        }
        System.out.print("Effort: ");
        effort = scanner.nextInt();

        state.add(unplanned1, name, effort);
    }
}
