package pt;

import pt.config.Config;
import pt.dbs.DBConnector;
import pt.dbs.DBManager;
import pt.taskManagement.TodoManager;
import pt.taskManagement.TodoTask;

import java.sql.*;

public class PtManager {

    private final Connection conn;
    private final Config conf;
    private final DBManager dbMan;
    private Thread running;
    private Pomodoro pp;
    private TodoManager todoMan;

    public PtManager(Config conf) {
        this.conf = conf;
        Debugger.debug = conf.getDebug();
        Debugger.log("Initializing PtManager");

        conn = DBConnector.getConnection();
        dbMan = new DBManager(conn);

        todoMan = new TodoManager();
        queryTasks();
    }

    public boolean isChanged() {
        return todoMan.isChanged();
    }

    public void saveTasks() {
        dbMan.saveTodayTasks(todoMan.isChanged(), (TodoTask) todoMan.getTask(), todoMan.getDone());
    }

    // Queries the unfinished tasks from yesterday and todays tasks
    private void queryTasks() {
        todoMan.add(dbMan.queryYesterdaysTasks());

        TodoTask t = dbMan.returnTasks();
        if (t != null)
            todoMan.add(t);

        todoMan.addDone(dbMan.returnDoneTasks());
        todoMan.setChanged(false);
    }


    private void sendToBack() {
        if (pp.isAlive()) {
            Debugger.log("Sending Thread to background.");
            pp.setContinuous(false);
            running = new Thread(pp);
            running.start();
        }
    }

    public void timer() {
        try {
            if (running != null && running.isAlive()) {
                // timer hasn't goten to 0
                pp.setContinuous(true);
                running.join();
                sendToBack();
            } else {
                // timer start + after once finished
                pp = new Pomodoro(todoMan.isLongBreak(), (TodoTask) todoMan.getTask(), conf);
                pp.setOnWork(true);
                pp.setContinuous(true);
                running = new Thread(pp);
                running.start();
                running.join();
                sendToBack();
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void showTime() {
        if (pp == null) {
            System.out.println("No timer");
            return;
        }
        System.out.println(pp.printTimeSingle());
    }

    public TodoManager getTodoMan() {
        return todoMan;
    }
}
