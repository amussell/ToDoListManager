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

    public int cancelTask(int taskId) {
        return -1;
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

    public int markTaskComplete(int taskId) {

        return -1;
    }

    /**
     * Returns a list os completed tasks with a given tag
     *
     * If the given tag is null a list of all completed tasks
     * is returned
     *
     * @param tag
     * @return list of completed tasks
     */
    public List<Task> getCompletedTasks(String tag) {

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
    public int renameTask(int taskId, String newLabel) {

        return -1;
    }

    /**
     * Returns a list of all tasks with labels that
     * contain the keyword given.
     *
     * @param keyword
     * @return list of tasks
     */
    public List<Task> search(String keyword) {

        return null;
    }

    /**
     * Looks up a task and returns a task object
     *
     * returns null if there is no task with the
     * specified task id.
     * @param taskId
     * @return
     */
    public Task lookupTask(int taskId) {

        return null;
    }




}