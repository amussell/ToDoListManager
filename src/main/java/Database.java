import java.sql.*;
import com.jcraft.jsch.*; //SSH connection
import java.util.Date;
import java.util.List;


/**
 *Sqlite database Class
 */
public class Database {

    Connection conn = null;
    Session session = null;
    Credentials credentials = null;

    public Database(Credentials credentials) {
        this.credentials = credentials;
        connect();
    }

    /**
     * Connect to a sample database
     */
    public void connect() {
        try {
            String strSshUser = credentials.getSshId();                  // SSH login username
            String strSshPassword = credentials.getSshPassword();        // SSH login password
            String strDbUser = credentials.getSandboxId();               // database loging username
            String strDbPassword = credentials.getSandboxPassword();     // database login password
            int nRemotePort = credentials.getDbPort();                   // remote port number of your database

            String strSshHost = "onyx.boisestate.edu";                   // hostname or ip or SSH server
            int nSshPort = 22;                                           // remote SSH host port number
            String strRemoteHost = "localhost";                          // hostname or ip of your database server
            int nLocalPort = 3367;                                       // local port number use to bind SSH tunnel

            //Creates an SSH tunnel
            session = doSshTunnel(strSshUser, strSshPassword, strSshHost, nSshPort, strRemoteHost, nLocalPort, nRemotePort);

            // create a connection to the database
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:"+nLocalPort, strDbUser, strDbPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates an SSH tunnel to be able to connect to the host where the database is located
     * @param strSshUser
     * @param strSshPassword
     * @param strSshHost
     * @param nSshPort
     * @param strRemoteHost
     * @param nLocalPort
     * @param nRemotePort
     * @return
     * @throws JSchException
     */
    private static Session doSshTunnel( String strSshUser, String strSshPassword, String strSshHost, int nSshPort, String strRemoteHost, int nLocalPort, int nRemotePort ) throws JSchException
    {
        final JSch jsch = new JSch();
        java.util.Properties configuration = new java.util.Properties();
        configuration.put("StrictHostKeyChecking", "no");

        Session session = jsch.getSession( strSshUser, strSshHost, 22 );
        session.setPassword( strSshPassword );

        session.setConfig(configuration);
        session.connect();
        session.setPortForwardingL(nLocalPort, strRemoteHost, nRemotePort);
        return session;
    }

    /**
     * Creates all required tables with the proper schema for the database
     *
     * returns true if setup was successful, false if setup failed.
     */
    public boolean setUp() {
        String createUsers = "CREATE TABLE task (id ;";
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