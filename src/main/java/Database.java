import java.sql.*;
import java.util.List;


/**
 *Sqlite database Class
 */
public class Database {

    Connection conn = null;
    String url = "";

    public Database(String url) {
        this.url = url;
        connect();
    }

    /**
     * Connect to a sample database
     */
    public void connect() {
        try {
            // create a connection to the database
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates all required tables with the proper schema for the database
     *
     * returns true if setup was successful, false if setup failed.
     */
    public boolean setUp() {
        String createUsers = "CREATE TABLE users (uuid text, name string, date date, passHash varChar(512), realName string, ipAddress string, primary key (uuid));";
        String createTimeStamp = "CREATE TABLE timeStamp (stamp integer, primary key (stamp));";
        String insertFirstTimeStamp = "Insert into timeStamp values (0);";
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(createUsers);
            stmt.execute(createTimeStamp);
            stmt.execute(insertFirstTimeStamp);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Closes database handler
     */
    public void close() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTask() {

    }

    public void cancelTask() {

    }

    public List<Task> getActiveTasks() {

        return null;
    }

    /**
     * Associates a given task with a given tag
     */
    public void tagTask() {

    }

    /**
     * Sets the due date for a task with a given task id.
     */
    public void setDueDate() {

    }

    public void markTaskComplete() {

    }

    public List<Task> getCompletedTasks() {

        return null;
    }

    public List<Task> getOverdueTasks() {

        return null;
    }

    /**
     * Gets a list of tasks that are due in the next three days
     *
     * @return tasks that are due soon
     */
    public List<Task> getTasksDueSoon() {

        return null;
    }

    public List<Task> getTasksDueToday() {

        return null;
    }

    /**
     * Renames a tasks label
     */
    public void renameTask() {

    }




}