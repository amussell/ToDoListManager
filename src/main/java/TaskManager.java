import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
        Scanner scan = new Scanner(line);
        String tag = null;
        if(!scan.hasNext()) tag = scan.next();
        scan.close();

        List<Task> tasks;
        tasks = db.getActiveTasks(tag);

        if(tasks.size() == 0) {
            System.out.println("No active tasks");
        } else {
            for (Task task : tasks) {
                System.out.println(task);
            }
        }
    }

    private void setDue(String line) {
        Scanner scan = new Scanner(line);
        int taskId = 0;
        Date dueDate = null;
        try {
            SimpleDateFormat df = new SimpleDateFormat();
            taskId = Integer.parseInt(scan.next());
            dueDate = df.parse(scan.next());
        } catch (Exception e) {
            e.printStackTrace(); //DELETE AND ADD USAGE STATEMENT
            return;
        }
        scan.close();

        int successCode = db.setDueDate(taskId,dueDate);
        if(successCode == -1) {
            System.out.println("Failed to set due date");
        } else {
            System.out.println("Set due date for task " + taskId + " to " + dueDate);
        }
    }

    private void addTag(String line) {
        Scanner scan = new Scanner(line);
        String usage = "Usage: tag <taskId> tags...";
        int taskId;
        ArrayList<String> tags = new ArrayList();
        try {
            taskId = Integer.parseInt(scan.next());
            while(scan.hasNext()) {
                tags.add(scan.next());
            }
            if(tags.size() == 0) {
                System.out.println(usage);
                return;
            } else {
                db.tagTask(taskId,tags);
            }
        } catch(Exception e) {
            System.out.println(usage);
        }
    }

    private void finish(String line) {
        int taskId = 0;
        String usage = "finish <task id>";
        try {
            taskId = Integer.parseInt(line);

        } catch (Exception e) {
            System.out.println(usage);
        }
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
