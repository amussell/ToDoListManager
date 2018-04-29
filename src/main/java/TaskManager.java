import java.util.Scanner;

public class TaskManager {
    String prompt = "TaskManager>";
    Database db;

    public TaskManager(String url) {
        db = new Database(url);
    }

    public void run() {
        Scanner in = new Scanner(System.in);
        while(true) { //Will stop when "exit" is entered and parseLine calls System.exit()
            System.out.print(prompt);
            String line = in.nextLine();
            parseLine(line);
        }

    }

    private void parseLine(String line) {
        Scanner scan = new Scanner(line);
        if(!scan.hasNext()) return; //Nothing to do
        String command = scan.next();
        if(command.equals("add")) add(scan.nextLine());
        if(command.equals("active")) listActive(scan.nextLine());
        if(command.equals("due")) setDue(scan.nextLine());
        if(command.equals("tag")) addTag(scan.nextLine());
        if(command.equals("finish")) finish(scan.nextLine());
        if(command.equals("cancel")) cancel(scan.nextLine());
        if(command.equals("show")) show(scan.nextLine());
        if(command.equals("completed")) complete(scan.nextLine());
        if(command.equals("overdue")) showOverdue(scan.nextLine());
        if(command.equals("rename")) rename(scan.nextLine());
        if(command.equals("search")) search(scan.nextLine());
        if(command.equals("exit")) System.exit(1);
    }

    private void add(String line) {
        Task task = new Task(line);
        int taskId = db.createTask(task);
        task.setId(taskId);
        if(taskId == -1) {
            System.out.println("Task could not be created");
        } else {
            System.out.println("Task id is: " + taskId);
        }
    }

    private void listActive(String line) {

    }

    private void setDue(String line) {

    }

    private void addTag(String line) {

    }

    private void finish(String line) {

    }

    private void cancel(String line) {

    }

    private void show(String line) {

    }

    private void complete(String line) {

    }

    private void showOverdue(String line) {

    }

    private void rename(String line) {

    }

    private void search(String line) {

    }
}
