package pt.dbs;

import pt.taskManagement.LinkedTaskDone;
import pt.taskManagement.Task;
import pt.taskManagement.TodoManager;
import pt.taskManagement.TodoTask;

import java.sql.*;

// This class makes queries to the H2 database
public class DBManager {

    Connection conn;

    public DBManager(Connection conn) {
        shutDownHook();
        this.conn = conn;
        tableExists();
    }

    // Catches SIGINT (program shutdown) and closes the database
    private void shutDownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                closeConnection();
            }
        });
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


            String createActivity = "CREATE TABLE IF NOT EXISTS activity_inventory(" +
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

    public TodoTask returnTasks() {
        ResultSet rs = null;
        try {
            String sql = "SELECT started, id, done, name, pomodoros, notes FROM info WHERE cast(started As DATE)=CURDATE() AND done=FALSE;";
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            TodoTask t = null;
            while (rs.next()) {
                TodoTask temp = new TodoTask(rs.getTimestamp(1), rs.getString(4), rs.getInt(5), rs.getString(6));
                if (t == null)
                    t = temp;
                else
                    t.setNext(temp);
            }
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public LinkedTaskDone returnDoneTasks() {
        ResultSet rs = null;
        try {
            String sql = "SELECT started, id, done, name, pomodoros, notes FROM info WHERE cast(started As DATE)=CURDATE() AND done=TRUE;";
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            LinkedTaskDone t = null;
            while (rs.next()) {
                LinkedTaskDone temp = new LinkedTaskDone(rs.getString(4), rs.getString(6), rs.getInt(5), rs.getTimestamp(1));
                if (t == null)
                    t = temp;
                else {
                    LinkedTaskDone tempDone = t;
                    while(tempDone.getNext() != null) {
                        tempDone = (LinkedTaskDone) tempDone.getNext();
                    }
                    tempDone.setNext(temp);
                }
            }
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // Add yesterdays unfinished tasks
    public TodoTask queryYesterdaysTasks() {
        ResultSet rs = null;
        try {
            String sql;
            sql = "SELECT started, id, done, name, pomodoros, notes FROM info " +
                    "WHERE cast(started As DATE)=DATEADD('DAY', -1, CURDATE()) AND done=FALSE;";

            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            TodoTask t = null;
            while (rs.next()) {
                TodoTask temp = new TodoTask(rs.getTimestamp(1), rs.getString(4), rs.getInt(5), rs.getString(6));
                if (t == null)
                    t = temp;
                else
                    t.setNext(temp);
            }

            stmt.executeUpdate("DELETE FROM info where started=DATEADD('DAY', -1, CURDATE()) AND done=FALSE");
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // Closes the database connection
    public void closeConnection() {
        try {
            if (conn != null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void prepareStatement(Task t) throws SQLException {
        PreparedStatement pStmt = conn.prepareStatement("INSERT INTO info VALUES(null, ?, ?, ?, ?, ?, ?)");
        int id = 1;
        while(t.getNext() != null) {
            pStmt.setTimestamp(1, new java.sql.Timestamp(t.getDate().getTime()));
            pStmt.setInt(2, id);
            pStmt.setBoolean(3, t instanceof TodoTask);
            pStmt.setString(4, t.getName());
            pStmt.setInt(5, t.getPomodoros());
            pStmt.setString(6, t.getNote());
            pStmt.execute();
            t = t.getNext();
            id++;
        }
    }

    public void saveTodayTasks(boolean changed, TodoTask t, LinkedTaskDone tt) {
        try {
            if (changed) {
                Statement stmt = conn.createStatement();
                String sql = "DELETE FROM info WHERE cast(current_timestamp() As DATE)=CURDATE();";
                stmt.executeUpdate(sql);

                prepareStatement(t);
                prepareStatement(tt);

                System.out.println("Saved.");
            } else
                System.out.println("No changes made.");

            // Change changed to false
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
