public class ToDoListManager {

    public static void main(String[] args) {
        String dbUrl = "";
        int userId;
        String password;
        int sandboxId;
        String sandboxPassword;
        int port;

        try {
            userId = Integer.parseInt(args[0]);
            password = args[1];
            sandboxId = Integer.parseInt(args[2]);
            sandboxPassword = args[3];
            port = Integer.parseInt(args[4]);

            String strSshUser = args[0];                  // SSH loging username
            String strSshPassword = args[1];                   // SSH login password
            String strSshHost = "onyx.boisestate.edu";          // hostname or ip or SSH server
            int nSshPort = 22;                                    // remote SSH host port number
            String strRemoteHost = "localhost";  // hostname or ip of your database server
            int nLocalPort = 3367;  // local port number use to bind SSH tunnel

            String strDbUser = args[2];                    // database loging username
            String strDbPassword = args[3];                    // database login password
            int nRemotePort = Integer.parseInt(args[4]); // remote port number of your database

            session = JDBCexample.doSshTunnel(strSshUser, strSshPassword, strSshHost, nSshPort, strRemoteHost, nLocalPort, nRemotePort);

        } catch(Exception e) {
            printUsage();
            System.exit(1);
        }

        TaskManager tm = new TaskManager(dbUrl);
        tm.run();

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
        /*This is one of the available choices to connect to mysql
         * If you think you know another way, you can go ahead*/

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

    public static void printUsage() {
        System.out.println("java -jar TaskManager <BroncoUserid> <BroncoPassword> <sandboxUSerID> <sandbox password> <yourportnumber>");
    }
}
