/*
Create PomodoroTimer class extends Runable
in there, there is a method wchich converts total seconds to a hour format.
while this thread runs, it is poossible to poll the timer in order to show how many minutes are left. (by runing timer/t command)
it should also be possible to attach (start) and detach (spacebar) from timer

 */

package proj;

import proj.dbs.DBConnector;

import java.io.BufferedReader;
import java.sql.*;
import java.util.ArrayList;

class Manager {

    private ArrayList<Task> finishedTasks;
    private ArrayList<Task> unfinishedTasks;
    private TaskListState history;
    private boolean changed;
    private final Connection conn;

    public Manager() {
        conn = DBConnector.getConnection();
        tableExists();
        this.finishedTasks = new ArrayList<>();
        this.unfinishedTasks = new ArrayList<>();
        queryTodayTasks();
    }

    private void tableExists() {
        try {
            String create = "CREATE TABLE IF NOT EXISTS info (" +
                    " rid INT NOT NULL AUTO_INCREMENT," +
                    " started DATETIME NOT NULL," +
                    " id INT," +
                    " done TINYINT(1)," +
                    " name VARCHAR(30)," +
                    " pomodoros INT," +
                    " notes VARCHAR(255)," +
                    " PRIMARY KEY (rid)" +
                    ");";

            Statement stmt = conn.createStatement();
            stmt.executeUpdate(create);
        } catch (SQLException e) {
            e.printStackTrace();
            closeConnection();
        }
    }

    private PomodoroTimer t;
    private Thread th;

    public void startPomodoro(BufferedReader in) {
        try {
            if (!this.unfinishedTasks.isEmpty()) {
                if (t != null) {
                    if (t.getMaxSeconds() > 0) {
                        KeyListener listener = new KeyListener(t, in);
                        new Thread(listener).start();
                        t.setContinuous(true);
                        th.join();
                        if (t.getMaxSeconds() > 0) {
                            th = new Thread(t);
                            th.start();
                        }
                        return;
                    }
                    if (isLongBreak())
                        t = new PomodoroTimer(getCurrentTask(), true);
                    else
                        t = new PomodoroTimer(getCurrentTask(), false);
                } else
                    t = new PomodoroTimer(getCurrentTask(), false);

                KeyListener listener = new KeyListener(t, in);
                new Thread(listener).start();
                th = new Thread(t);
                t.setContinuous(true);
                th.start();
                th.join();
                if (t.getMaxSeconds() > 0) {
                    th = new Thread(t);
                    th.start();
                }
            } else
                System.out.println("No task to work on.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean isLongBreak() {
        int count = 0;
        for (Task t : finishedTasks) {
            count += t.getMarks();
        }
        for (Task t : unfinishedTasks) {
            count += t.getMarks();
        }
        count++;

        return count % 4 == 0 && count > 0;
    }

/*
    public void startPomodoro() {
        if (!this.unfinishedTasks.isEmpty()) {
                while (true) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                    String s;
                    System.out.print("Ready? [Y/N]> ");
                    if ((s = in.readLine()).isEmpty()) {
                        System.out.println("abort.");
                    } else if (s.equals("y") || s.equals("Y")) {
                        countDown("Work", 1500);
                        this.mark();
                        sendMsg("Take a break!");
                        if (isLongBreak())
                            countDown("Long Break", 900);
                        else
                            countDown("Break", 300);

                        sendMsg("Break is over!");
                    } else
                        break;
                }
        } else
            System.out.println("There is no task to work on.");
    }
*/

    private void addToHistory() {
        TaskListState temp = new TaskListState(new ArrayList<>(this.finishedTasks), new ArrayList<>(this.unfinishedTasks));
        if (history == null) {
            history = temp;
        } else {
            temp.next = history;
            history = temp;
        }
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
                if (t.isFinished())
                    this.finishedTasks.add(t);
                else
                    this.unfinishedTasks.add(t);
            }
            addToHistory();
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
    // Removes Task with specified ID from the unfinished tasks list
    // and updates the following IDs
    public void remove(int id) {
        for (int i = 0; i < unfinishedTasks.size(); i++) {
            Task t = unfinishedTasks.get(i);
            if (t.getId() == id) {
                unfinishedTasks.remove(i);
                if (i < unfinishedTasks.size()) {
                    unfinishedTasks.get(i).setId(unfinishedTasks.get(i).getId() - 1);
                }
            }
        }
        this.changed = true;
    }

    public void removeAllTasks() {
        this.changed = true;
        this.unfinishedTasks.clear();
        this.finishedTasks.clear();
        addToHistory();
    }

    public boolean isChanged() {
        return changed;
    }

    private void prepareStatement(ArrayList<Task> list) throws SQLException {
        PreparedStatement pStmt = conn.prepareStatement("INSERT INTO info VALUES(null, ?, ?, ?, ?, ?, ?)");
        for (Task t : list) {
            pStmt.setTimestamp(1, new java.sql.Timestamp(t.getDate().getTime()));
            pStmt.setInt(2, t.getId());
            pStmt.setBoolean(3, t.isFinished());
            pStmt.setString(4, t.getName());
            pStmt.setInt(5, t.getMarks());
            pStmt.setString(6, t.getNotes());
            pStmt.execute();
        }
    }

    void saveTodayTasks() {
        try {
            if (changed) {
                Statement stmt = conn.createStatement();
                String sql = "DELETE FROM info WHERE cast(current_timestamp() As DATE)=CURDATE();";
                stmt.executeUpdate(sql);

                prepareStatement(unfinishedTasks);
                prepareStatement(finishedTasks);

                System.out.println("Saved.");
            } else
                System.out.println("No changes made.");
            changed = false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(String name, String notes) {
        this.unfinishedTasks.add(new Task(unfinishedTasks.size() + 1, name, notes));
        this.changed = true;

        addToHistory();
    }

    public void mark() {
        this.unfinishedTasks.get(0).addMark();
        this.changed = true;

        addToHistory();
    }

    public void finish() {
        unfinishedTasks.get(0).finish();
        finishedTasks.add(unfinishedTasks.get(0));
        unfinishedTasks.remove(0);

        for (Task t : unfinishedTasks) {
            t.setId(t.getId() - 1);
        }
        this.changed = true;

        addToHistory();
    }

    public void showCurrentTask() {
        System.out.println(this.unfinishedTasks.get(0).getName());
    }

    private Task getCurrentTask() {
        return this.unfinishedTasks.get(0);
    }


    public void list() {
        System.out.format("+------+----+----------------------------------+---------------+---------------------------+%n");
        System.out.format("| Done | ID |               Name               |     Marks     |           Notes           |%n");
        System.out.format("+------+----+----------------------------------+---------------+---------------------------+%n");
        list(this.unfinishedTasks);
        System.out.format("+------+----+----------------------------------+---------------+---------------------------+%n");
        list(this.finishedTasks);
        System.out.format("+------+----+----------------------------------+---------------+---------------------------+%n");
    }

    private void list(ArrayList<Task> ts) {
        String leftAlignFormat = "| %4s | %2s | %-32s | %-13s | %-25s |%n";
        for (Task t : ts) {
            String done = (t.isFinished()) ? "DONE" : "";
            String id = t.getId() == -1 ? "" : Integer.toString(t.getId());
            System.out.format(leftAlignFormat, done, id, t.getName(), returnMarks(t.getMarks()), t.getNotes());
        }
    }

    private String returnMarks(int marks) {
        String mark = "";
        for (int i = 0; i < marks; i++) {
            mark += "X ";
        }
        return mark;
    }

    public void switchPos(int i, int i1) {
        int index1 = i - 1;
        int index2 = i1 - 1;

        Task t1 = this.unfinishedTasks.get(index1);
        Task t2 = this.unfinishedTasks.get(index2);

        String tempName = t1.getName();
        t1.setName(t2.getName());
        t2.setName(tempName);
        this.changed = true;

        addToHistory();
    }

    public void undoChanges() {
        if (history.next != null) {
            this.finishedTasks = history.next.getFinished();
            this.unfinishedTasks = history.next.getUnfinished();

            this.history = history.next;
        } else
            System.out.println("Already at the latest change.");
    }

    public void listHistory() {
        TaskListState temp = history;
        while (temp != null) {
            list(temp.getUnfinished());
            System.out.println("----------------------");
            temp = temp.next;
        }
    }

    // Closes he database connection
    public void closeConnection() {
        try {
            if (conn != null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showTime() {
        if (t == null) {
            System.out.println("No timer");
            return;
        }
        System.out.println(t.showTime());
    }

    class TaskListState {
        private final ArrayList<Task> finishedTasks;
        private final ArrayList<Task> unfinishedTasks;

        public TaskListState next;

        public TaskListState(ArrayList<Task> finishedTasks, ArrayList<Task> unfinishedTasks) {
            this.finishedTasks = finishedTasks;
            this.unfinishedTasks = unfinishedTasks;
            this.next = null;
        }

        ArrayList<Task> getFinished() {
            return this.finishedTasks;
        }

        ArrayList<Task> getUnfinished() {
            return this.unfinishedTasks;
        }
    }

}
