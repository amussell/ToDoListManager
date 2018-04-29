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
        } catch(Exception e) {
            printUsage();
            System.exit(1);
        }

        TaskManager tm = new TaskManager(dbUrl);
        tm.run();

    }


    public static void printUsage() {
        System.out.println("java -jar TaskManager <BroncoUserid> <BroncoPassword> <sandboxUSerID> <sandbox password> <yourportnumber>");
    }
}
