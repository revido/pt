package pt;

import pt.activityInventory.ActivityManager;
import pt.config.Config;
import pt.dbs.DBConnector;

import java.sql.*;
import java.util.ArrayList;

public class Manager {

    private final TaskListState tasks;
    private final Connection conn;
    private final Config conf;
    private Thread running;
    private Pomodoro pp;

    public Manager(Config conf) {
        this.conf = conf;
        Debugger.debug = conf.getDebug();
        Debugger.log("Initializing pt.Manager.");
        conn = DBConnector.getConnection();
        tableExists();

        tasks = new TaskListState();
        queryYesterdaysTasks();
        queryTodayTasks();
    }

    private void tableExists() {
        try {
            Statement stmt = conn.createStatement();

            String createInfo = "CREATE TABLE IF NOT EXISTS info (" +
                    " rid INT NOT NULL AUTO_INCREMENT," +
                    " started DATETIME NOT NULL," +
                    " id INT," +
                    " done TINYINT(1)," +
                    " name VARCHAR(100)," +
                    " pomodoros INT," +
                    " notes VARCHAR(255)," +
                    " PRIMARY KEY (rid)" +
                    ");";


            String createActivity= "CREATE TABLE IF NOT EXISTS activity_inventory(" +
                    " rid INT NOT NULL AUTO_INCREMENT," +
                    " name VARCHAR(100)," +
                    " effort INT," +
                    " until DATETIME," +
                    " PRIMARY KEY (rid)" +
                    ");";

            stmt.executeUpdate(createInfo);
            stmt.executeUpdate(createActivity);

        } catch (SQLException e) {
            e.printStackTrace();
            closeConnection();
        }
    }

    // Add yesterdays unfinished tasks
    private void queryYesterdaysTasks() {
        ResultSet rs = null;
        try {
            String sql = "SELECT started, id, done, name, pomodoros, notes FROM info " +
                    "WHERE cast(started As DATE)=DATEADD('DAY', -1, CURDATE());";
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            TaskListState yesterday = new TaskListState();
            while (rs.next()) {
                Task t = new Task(rs.getTimestamp(1), rs.getInt(2), rs.getBoolean(3),
                        rs.getString(4), rs.getInt(5), rs.getString(6));

                yesterday.add(t);
            }

            for (Task t : yesterday.getCurrentState().getUnfinished()) {
                tasks.add(t.getName(), t.getNotes(), t.getPomodoros());
            }
            stmt.executeUpdate("DELETE FROM info where started=DATEADD('DAY', -1, CURDATE()) AND done=FALSE");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
                pp = new Pomodoro(isLongBreak(), getCurrentTask(), conf);
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


    private void addToHistory() {
        tasks.saveToHistory();
    }

    private void queryTodayTasks() {
        ResultSet rs = null;
        try {
            String sql = "SELECT started, id, done, name, pomodoros, notes FROM info WHERE cast(started As DATE)=CURDATE();";
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Task t = new Task(rs.getTimestamp(1), rs.getInt(2), rs.getBoolean(3),
                        rs.getString(4), rs.getInt(5), rs.getString(6));

                tasks.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // int id: ID of task to remove
    // Removes pt.Task with specified ID from the unfinished tasks displayTasks
    // and updates the following IDs
    public void remove(int id) {
        tasks.remove(id);
    }

    public boolean isChanged() {
        return tasks.isChanged();
    }

    private void prepareStatement(ArrayList<Task> list) throws SQLException {
        PreparedStatement pStmt = conn.prepareStatement("INSERT INTO info VALUES(null, ?, ?, ?, ?, ?, ?)");
        for (Task t : list) {
            pStmt.setTimestamp(1, new java.sql.Timestamp(t.getDate().getTime()));
            pStmt.setInt(2, t.getId());
            pStmt.setBoolean(3, t.isDone());
            pStmt.setString(4, t.getName());
            pStmt.setInt(5, t.getPomodoros());
            pStmt.setString(6, t.getNotes());
            pStmt.execute();
        }
    }

    private boolean isLongBreak() {
        int count = 0;
        for (Task t : tasks.getCurrentState().getUnfinished()) {
            count += t.getPomodoros();
        }
        for (Task t : tasks.getCurrentState().getFinished()) {
            count += t.getPomodoros();
        }
        count++;

        return count % 4 == 0 && count > 0;
    }

    public void saveTodayTasks() {
        try {
            if (tasks.isChanged()) {
                Statement stmt = conn.createStatement();
                String sql = "DELETE FROM info WHERE cast(current_timestamp() As DATE)=CURDATE();";
                stmt.executeUpdate(sql);

                prepareStatement(tasks.getCurrentState().getUnfinished());
                prepareStatement(tasks.getCurrentState().getFinished());

                System.out.println("Saved.");
            } else
                System.out.println("No changes made.");

            tasks.saved();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(String name, String notes) {
        tasks.add(name, notes);
    }

    public void mark() {
        addToHistory();
        getCurrentTask().addMark();
        tasks.change();
    }

    public void finish() {
        addToHistory();
        getCurrentTask().finish();
        tasks.add(getCurrentTask());
        tasks.remove(getCurrentTask());

        for (Task t : tasks.getCurrentState().getUnfinished()) {
            t.setId(t.getId() - 1);
        }
        tasks.change();
    }

    public void showCurrentTask() {
        System.out.println(getCurrentTask().getName());
    }

    private Task getCurrentTask() {
        return tasks.getCurrentState().getUnfinished().get(0);
    }

    public void removeAllTasks() {
        tasks.removeAllTasks();
    }

    public void switchPos(int i, int i1) {
        tasks.switchPos(i, i1);
    }

    public void undoChanges() {
        tasks.undoChanges();
    }

    public void listTasks() {
        tasks.displayCurrentTasks();
    }

    public void listHistory() {
        tasks.displayHistory();
    }

    public TaskListState getState() {
        return tasks;
    }

    public void showTime() {
        if (pp == null) {
            System.out.println("No timer");
            return;
        }
        System.out.println(pp.printTimeSingle());
    }

    // Closes the database connection
    public void closeConnection() {
        try {
            if (conn != null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
