package pt;

import pt.taskManagement.LinkedTaskDone;
import pt.taskManagement.TodoTask;

import java.util.ArrayList;

public class Table {

    public void displayTasks(TodoTask t, LinkedTaskDone d) {
        System.out.format("+------+----+----------------------------------+---------------+---------------------------+%n");
        System.out.format("| Done | ID |               Name               |     Marks     |           Notes           |%n");
        System.out.format("+------+----+----------------------------------+---------------+---------------------------+%n");
        displayTasks(t);
        System.out.format("+------+----+----------------------------------+---------------+---------------------------+%n");
        displayDoneTasks(d);
        System.out.format("+------+----+----------------------------------+---------------+---------------------------+%n");
    }

    private void displayTasks(TodoTask t) {
        String leftAlignFormat = "| %4s | %2s | %-32s | %-13s | %-25s |%n";

        int i = 1;
        while (t != null) {
            System.out.format(leftAlignFormat, "", i, t.getName(), t.getPomodoros(), t.getNote());
            t = (TodoTask) t.getNext();
            i++;
        }
    }


    private void displayDoneTasks(LinkedTaskDone t) {
        String leftAlignFormat = "| %4s | %2s | %-32s | %-13s | %-25s |%n";

        while (t != null) {
            System.out.format(leftAlignFormat, "DONE", "", t.getName(), t.getPomodoros(), t.getNote());
            t = (LinkedTaskDone) t.getNext();
        }
    }

//
//    public void addColumn(String name) {
//        columns.add(name);
//    }
//
//    public void addRow(ArrayList<String> row) {
//        content.add(row);
//    }
//
//    public String returnTable() {
////        String table = top();
////
////        String format = "|";
////        for (int i = 0; i < columns.size(); i++) {
////            format += "%" + maxColLen(i) + "s" + "|";
////        }
////
////        table += String.format(format,columns);
//
//        String format = "| %4s | %2s | %32s | ";
//    }
////
////    public String top() {
////        String top = "+";
////        for (int i = 0; i < columns.size(); i++) {
////            top += repeatChar(maxColLen(i)) + "+";
////        }
////        top += "\r\n";
////    }
////
////    private String repeatChar(int times) {
////        String s = "";
////        for (int i = 0; i < times; i++) {
////            s += "-";
////        }
////        return s;
////    }
////
////    public int maxColLen(int col) {
////        int max = 0;
////        for (int i = 0; i < content.size(); i++) {
////            int len = content.get(i).get(col).length();
////            if (len > max)
////                max = len;
////        }
//    }
}
