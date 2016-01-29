package pt;

import pt.taskManagement.TodoManager;

class Main {
    public static void main(String[] args) {
//        Pt pt = new Pt();
//        pt.runProgram();
        TodoManager man = new TodoManager();
        man.add("test1", "note1");
        man.add("test2", "note2");
        man.add("test3", "note3");
        man.add("test4", "note4");
        man.add("test5", "note5");
        man.add("test6", "note6");

        System.out.println(man.toString());
        System.out.println();

        System.out.println(man.toString());
        System.out.println();
    }
}
