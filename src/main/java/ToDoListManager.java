public class ToDoListManager {

    public static void main(String[] args) {
        String dbUrl = "";
        String sshId;
        String sshPassword;
        String sandboxId;
        String sandboxPassword;
        int dbPort;
        Credentials credentials = null; //Credentials used for SSH and the DataBase

        try {
            sshId = args[0];
            sshPassword = args[1];
            sandboxId = args[2];
            sandboxPassword = args[3];
            dbPort = Integer.parseInt(args[4]);

            credentials = new Credentials(sshId, sshPassword, sandboxId, sandboxPassword, dbPort);
        } catch(Exception e) {
            printUsage();
            System.exit(1);
        }

        TaskManager tm = new TaskManager(credentials);
        tm.run();
    }

    public static void printUsage() {
        System.out.println("java -jar TaskManager <BroncoUserid> <BroncoPassword> <sandboxUSerID> <sandbox password> <yourportnumber>");
    }
}
