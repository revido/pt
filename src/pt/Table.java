package pt;

import java.util.ArrayList;

public class Table {
    ArrayList<String> columns;
    ArrayList<ArrayList<String>> content;

    public Table() {
        columns = new ArrayList<>();
        content = new ArrayList<>();
    }

    public void addColumn(String name) {
        columns.add(name);
    }

    public void addRow(ArrayList<String> row) {
        content.add(row);
    }

    public String returnTable() {
//        String table = top();
//
//        String format = "|";
//        for (int i = 0; i < columns.size(); i++) {
//            format += "%" + maxColLen(i) + "s" + "|";
//        }
//
//        table += String.format(format,columns);

        String format = "| %4s | %2s | %32s | ";
    }
//
//    public String top() {
//        String top = "+";
//        for (int i = 0; i < columns.size(); i++) {
//            top += repeatChar(maxColLen(i)) + "+";
//        }
//        top += "\r\n";
//    }
//
//    private String repeatChar(int times) {
//        String s = "";
//        for (int i = 0; i < times; i++) {
//            s += "-";
//        }
//        return s;
//    }
//
//    public int maxColLen(int col) {
//        int max = 0;
//        for (int i = 0; i < content.size(); i++) {
//            int len = content.get(i).get(col).length();
//            if (len > max)
//                max = len;
//        }
    }
}
