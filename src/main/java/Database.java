import java.sql.*;
import com.jcraft.jsch.*; //SSH connection

import java.util.*;
import java.util.Date;


/**
 *Database Class
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
            int nLocalPort = 5005;                                       // local port number use to bind SSH tunnel

            //Creates an SSH tunnel
            session = doSshTunnel(strSshUser, strSshPassword, strSshHost, nSshPort, strRemoteHost, nLocalPort, nRemotePort);

            // create a connection to the database
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:"+nLocalPort+"/Tasks?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=false&useLegacyDatetimeCode=false&serverTimezone=UTC", strDbUser, strDbPassword);
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
        session.setPortForwardingL(nLocalPort, strRemoteHost, nRemotePort);
        session.connect();
        return session;
    }


    /**
     * Closes database handler
     */
    public void close() {
        try {
            if (conn != null) {
                conn.close();
                session.disconnect();
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
        PreparedStatement stmt = null;
        try {
            conn.setAutoCommit(false); //Begin transaction
            stmt =
                    conn.prepareStatement(
                            "INSERT INTO task (label,create_date,status) VALUES (?,NOW(),?);",
                            Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1,task.getLabel());
            stmt.setInt(2,0); //0 status is in progress
            stmt.execute();
            conn.commit();
            conn.setAutoCommit(true);
            ResultSet rs = stmt.getGeneratedKeys();

            if(rs.next()) {
                int id = rs.getInt(1);
                return id;
            } else {
                return -1;
            }
        } catch(SQLException e) {
            rollBack(e);
            return -1;
        } finally {
            resetToDefaultDB(stmt);
        }
    }

    /**
     * Cancels a task by setting its status in the database to 2.
     *
     * Returns 1 if it successfully canceled the task, and -1 if it
     * does not successfully cancel the task
     *
     * @param taskId
     * @return success code
     */
    public int cancelTask(int taskId) {
        PreparedStatement stmt = null;
        PreparedStatement stmt2 = null;
        try {
            conn.setAutoCommit(false); //Begin transaction
            stmt =
                    conn.prepareStatement("DELETE FROM tagged_task WHERE task_id = ?;");
            stmt2 =
                    conn.prepareStatement("DELETE FROM task WHERE id = ?;");
            stmt.setInt(1,taskId);
            stmt2.setInt(1,taskId);
            stmt.execute();
            int rowsDeleted = stmt2.executeUpdate();
            conn.commit();
            conn.setAutoCommit(true);
            if(rowsDeleted == 0) return -1;
            return 1;
        } catch(SQLException e) {
            rollBack(e);
            return -1;
        } finally{
            resetToDefaultDB(stmt);
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
            PreparedStatement stmt;

            if(tag != null) {
                stmt = conn.prepareStatement("SELECT * FROM task JOIN tagged_task ON id = task_id " +
                                "WHERE tag_name = ? AND status = 0;");
                stmt.setString(1,tag);
            } else {
                stmt = conn.prepareStatement("SELECT * FROM task WHERE status = 0;");
            }
            ResultSet rs = stmt.executeQuery();
            ArrayList<Task> tasks = new ArrayList();
            while(rs.next()) {
                Task task = new Task(rs.getString("label"));
                task.setId(rs.getInt("id"));
                task.setDueDate(rs.getDate("due_date",Calendar.getInstance(TimeZone.getTimeZone("MST"))));
                task.setCreateDate(rs.getDate("create_date",Calendar.getInstance(TimeZone.getTimeZone("MST"))));
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
        PreparedStatement stmt = null;
        try {
            conn.setAutoCommit(false); //Begin transaction

            for(String tag : tags) {
                try {
                    stmt = conn.prepareStatement("INSERT IGNORE INTO tag (name) VALUES (?);");
                    stmt.setString(1, tag);
                    stmt.execute();
                    stmt = conn.prepareStatement("INSERT INTO tagged_task (task_id,tag_name) VALUES (?,?);");
                    stmt.setInt(1, taskId);
                    stmt.setString(2,tag);
                    stmt.execute();
                } catch (SQLException e) {
                    /*The tag must have already existed*/
                }
            }
            conn.commit();
        } catch(SQLException e) {
            rollBack(e);
        } finally{
            resetToDefaultDB(stmt);
        }
    }

    /**
     * Sets the due date for a task with a given task id.
     *
     * returns 1 if successful and -1 if not
     */
    public int setDueDate(int taskId, Date dueDate) {
        PreparedStatement stmt = null;
        try {
            conn.setAutoCommit(false); //Begin transaction
            stmt =
                    conn.prepareStatement("UPDATE task SET due_date = ? WHERE id = ?;");
            stmt.setDate(1,new java.sql.Date(dueDate.getTime())); // 2 status is canceled
            stmt.setInt(2,taskId);
            stmt.execute();
            conn.commit();
            return 1;
        } catch(SQLException e) {
            rollBack(e);
            return -1;
        } finally{
            resetToDefaultDB(stmt);
        }
    }

    public int markTaskComplete(int taskId) {
        PreparedStatement stmt = null;
        try {
            conn.setAutoCommit(false); //Begin transaction
            stmt =
                    conn.prepareStatement("UPDATE task SET status = ? WHERE id = ?;");
            stmt.setInt(1,1); // 1 status is complete
            stmt.setInt(2,taskId);
            int numUpdatedRows = stmt.executeUpdate();
            conn.commit();
            if(numUpdatedRows == 0) return -1;
            return 1;
        } catch(SQLException e) {
            rollBack(e);
            return -1;
        } finally {
            resetToDefaultDB(stmt);
        }
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
        try {
            PreparedStatement stmt;

            if(tag != null) {
                stmt = conn.prepareStatement("SELECT * FROM task JOIN tagged_task ON id = task_id " +
                        "WHERE tag_name = ? AND status = 1;");
                stmt.setString(1,tag);
            } else {
                stmt = conn.prepareStatement("SELECT * FROM task WHERE status = 1;");
            }
            ResultSet rs = stmt.executeQuery();
            ArrayList<Task> tasks = new ArrayList();
            while(rs.next()) {
                Task task = new Task(rs.getString("label"));
                task.setId(rs.getInt("id"));
                task.setDueDate(new Date(rs.getDate("due_date",Calendar.getInstance(TimeZone.getTimeZone("MST"))).getTime()));
                task.setCreateDate(rs.getDate("create_date",Calendar.getInstance(TimeZone.getTimeZone("MST"))));
                tasks.add(task);
            }
            return tasks;

        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Task> getOverdueTasks() {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("SELECT * FROM task WHERE due_date < NOW() AND status = 0;");

            ResultSet rs = stmt.executeQuery();
            ArrayList<Task> tasks = new ArrayList();
            while(rs.next()) {
                Task task = new Task(rs.getString("label"));
                task.setId(rs.getInt("id"));
                task.setDueDate(rs.getDate("due_date",Calendar.getInstance(TimeZone.getTimeZone("MST"))));
                task.setCreateDate(rs.getDate("create_date",Calendar.getInstance(TimeZone.getTimeZone("MST"))));
                tasks.add(task);
            }
            return tasks;

        } catch(SQLException e) {
            return null;
        }

    }

    /**
     * Gets a list of tasks that are due in the next three days
     *
     * @return tasks that are due soon
     */
    public List<Task> getTasksDueSoon() {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("SELECT * FROM task " +
                                             "WHERE due_date < DATE_ADD(NOW(), INTERVAL 3 DAY) AND status = 0;");

            ResultSet rs = stmt.executeQuery();
            ArrayList<Task> tasks = new ArrayList();
            while(rs.next()) {
                Task task = new Task(rs.getString("label"));
                task.setId(rs.getInt("id"));
                task.setDueDate(rs.getDate("due_date",Calendar.getInstance(TimeZone.getTimeZone("MST"))));
                task.setCreateDate(rs.getDate("create_date",Calendar.getInstance(TimeZone.getTimeZone("MST"))));
                tasks.add(task);
            }
            return tasks;

        } catch(SQLException e) {
            return null;
        }
    }

    public List<Task> getTasksDueToday() {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("SELECT * FROM task WHERE YEAR(due_date) = YEAR(NOW()) AND MONTH(due_date) = MONTH(NOW()) AND DAY(due_date) = DAY(NOW()) and status = 0;");

            ResultSet rs = stmt.executeQuery();
            ArrayList<Task> tasks = new ArrayList();
            while(rs.next()) {
                Task task = new Task(rs.getString("label"));
                task.setId(rs.getInt("id"));
                task.setDueDate(rs.getDate("due_date",Calendar.getInstance(TimeZone.getTimeZone("MST"))));
                task.setCreateDate(rs.getDate("create_date",Calendar.getInstance(TimeZone.getTimeZone("MST"))));
                tasks.add(task);
            }
            return tasks;

        } catch(SQLException e) {
            return null;
        }
    }

    /**
     * Renames a tasks label
     */
    public int renameTask(int taskId, String newLabel) {
        PreparedStatement stmt = null;
        try {
            conn.setAutoCommit(false); //Begin transaction
            stmt =
                    conn.prepareStatement("UPDATE task SET label = ? WHERE id = ?;");
            stmt.setString(1,newLabel); // 1 status is complete
            stmt.setInt(2,taskId);
            stmt.execute();
            conn.commit();
            return 1;
        } catch(SQLException e) {
            rollBack(e);
            return -1;
        } finally {
            resetToDefaultDB(stmt);
        }
    }

    /**
     * Returns a list of all tasks with labels that
     * contain the keyword given.
     *
     * @param keyword
     * @return list of tasks
     */
    public List<Task> search(String keyword) {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("SELECT * FROM task WHERE label LIKE ?;");
            stmt.setString(1,"%" + keyword + "%");

            ResultSet rs = stmt.executeQuery();
            ArrayList<Task> tasks = new ArrayList();
            while(rs.next()) {
                Task task = new Task(rs.getString("label"));
                task.setId(rs.getInt("id"));
                task.setDueDate(rs.getDate("due_date",Calendar.getInstance(TimeZone.getTimeZone("MST"))));
                task.setCreateDate(rs.getDate("create_date",Calendar.getInstance(TimeZone.getTimeZone("MST"))));
                tasks.add(task);
            }
            return tasks;

        } catch(SQLException e) {
            return null;
        }
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
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("SELECT * FROM task WHERE id = ?;");
            stmt.setInt(1,taskId);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                Task task = new Task(rs.getString("label"));
                task.setId(rs.getInt("id"));
                task.setDueDate(rs.getDate("due_date",Calendar.getInstance(TimeZone.getTimeZone("MST"))));
                task.setCreateDate(rs.getDate("create_date",Calendar.getInstance(TimeZone.getTimeZone("MST"))));

                stmt = conn.prepareStatement("SELECT * FROM tagged_task WHERE task_id = ?;");
                stmt.setInt(1,taskId);
                rs = stmt.executeQuery();
                while(rs.next()) {
                    String tag = rs.getString("tag_name");
                    task.addTag(tag);
                } return task;
            } else {
                return null;
            }

        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Rolls back a transaction if an exception is caught.
     * @param e
     */
    private void rollBack(Exception e){
        e.printStackTrace();
        if (conn != null) {
            try {
                System.err.print("Transaction is being rolled back");
                conn.rollback();
            } catch(SQLException e2) {
                System.out.println("ERROR IN ROLLING BACK DB");
                e.printStackTrace();
            }
        }
    }

    /**
     * Resets the DB back to default
     */
    private void resetToDefaultDB(Statement stmt){
        try{
            if (stmt != null) {
                stmt.close();
            }
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("ERROR IN RESET TO DEFAULT DB");
        }

    }

}