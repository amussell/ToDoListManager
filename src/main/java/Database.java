import java.sql.*;
import java.util.Date;
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

    /**
     * Creates a new task in the database and returns the
     * id of the task
     *
     * returns -1 if database query failed to create task
     * @param task
     * @return
     */
    public int createTask(Task task) {
        return -1;
    }

    public void cancelTask() {

    }

    /**
     * Gets a list of active tasks associated with the specified
     * tag. If no tag is specified (null) then it returns a list
     * of all active tasks
     *
     * @param tag
     * @return list of tasks
     */
    public List<Task> getActiveTasks(String tag) {

        return null;
    }

    /**
     * Associates a given task with a given tag
     */
    public void tagTask(int taskId, List<String> tags) {

    }

    /**
     * Sets the due date for a task with a given task id.
     *
     * returns 1 if successful and -1 if not
     */
    public int setDueDate(int taskId, Date dueDate) {

        return -1;
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