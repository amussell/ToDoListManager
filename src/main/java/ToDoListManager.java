import java.io.Console;

/**
 * Driving class for our Task Manager.
 * Parses the command line args and starts a database session
 */
public class ToDoListManager {
    public static void main(String[] args) {
        //Credential information
        String sshId;
        String sshPassword;
        String sandboxId;
        String sandboxPassword;
        int dbPort;

        Credentials credentials = null; //Credentials used for SSH and the DataBase
        try {
            sshId = args[0];
            if(sshId.equals("c")){ //Will read in credentials from the command line if 'c' is used as an argument
                credentials = readCredentialsFromConsole();
            } else{
                sshPassword = args[1];
                sandboxId = args[2];
                sandboxPassword = args[3];
                dbPort = Integer.parseInt(args[4]);
                credentials = new Credentials(sshId, sshPassword, sandboxId, sandboxPassword, dbPort);
            }
        } catch(Exception e) {
            printUsage();
            System.exit(1);
        }

        TaskManager tm = new TaskManager(credentials);
        tm.run();
    }

    /**
     * Reads in credentials from the console
     * @return
     */
    private static Credentials readCredentialsFromConsole(){
        Credentials credentials = null;
        try{
            Console console = System.console();
            String sshUser = console.readLine("SSH Username: ");
            String sshPassword = new String(console.readPassword("SSH Password: "));
            String dbPassword = new String(console.readPassword("DB Password: "));
            String dbPort = console.readLine("DB Port: ");
            credentials = new Credentials(sshUser, sshPassword, "msandbox", dbPassword,Integer.parseInt(dbPort));
        } catch (Exception e){
            System.out.println("Unable to get a Console. Exiting program.");
            System.exit(0);
        }
            return credentials;
    }

    public static void printUsage() {
        System.out.println("java -jar TaskManager <BroncoUserid> <BroncoPassword> <sandboxUSerID> <sandbox password> <yourportnumber>");
    }
}
