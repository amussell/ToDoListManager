import java.sql.*;
import java.util.ArrayList;
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
        try {
            conn.setAutoCommit(false); //Begin transaction
            PreparedStatement stmt =
                    conn.prepareStatement(
                            "INSERT INTO task (label,create_date,status) VALUES (?,NOW(),?);",
                            Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1,task.getLabel());
            stmt.setInt(2,0); //0 status is in progress
            stmt.execute();
            int id = stmt.getGeneratedKeys().getInt(1);
            conn.commit();
            conn.setAutoCommit(true);
            return id;
        } catch(SQLException e) {
            return -1;
        }
    }

    /**
     * Cancels a task by setting its status in the database to 2.
     *
     * Returns 1 if it succesfully canceled the task, and -1 if it
     * does not successfully cancel the task
     *
     * @param taskId
     * @return success code
     */
    public int cancelTask(int taskId) {
        try {
            conn.setAutoCommit(false); //Begin transaction
            PreparedStatement stmt =
                    conn.prepareStatement("UPDATE task SET status = ? WHERE id = ?;");
            stmt.setInt(1,2); // 2 status is canceled
            stmt.setInt(2,taskId);
            stmt.execute();
            conn.commit();
            conn.setAutoCommit(true);
            return 1;
        } catch(SQLException e) {
            return -1;
        }
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
        try {
            conn.setAutoCommit(false); //Begin transaction

            PreparedStatement stmt;

            if(tag != null) {
                stmt = conn.prepareStatement("SELECT * FROM task JOIN tagged_task ON id = task_id " +
                                "WHERE name = ? AND status = 0;");
                stmt.setString(1,tag);
            } else {
                stmt = conn.prepareStatement("SELECT * FROM task WHERE status = 0;");
            }
            ResultSet rs = stmt.executeQuery();
            conn.commit();
            conn.setAutoCommit(true);
            ArrayList<Task> tasks = new ArrayList();
            while(rs.next()) {
                Task task = new Task(rs.getString("label"));
                task.setId(rs.getInt("id"));
                task.setDueDate(rs.getDate("due_date"));
                task.setCreateDate(rs.getDate("create_date"));
                tasks.add(task);
            }
            return tasks;

        } catch(SQLException e) {
            return null;
        }
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