import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * A class used to interact with the DataBase and keep an
 * interactive shell session running while the program is in use
 */
public class TaskManager {
    String prompt = "TaskManager>";
    Database db;
    boolean running = true;

    public TaskManager(Credentials credentials) {
        db = new Database(credentials);
    }

    /**
     * Stays in a loop taking user input to
     * process database transactions
     */
    public void run() {
        Scanner in = new Scanner(System.in);
        while(running) { //Will stop when "exit" is entered
            System.out.print(prompt);
            String line = in.nextLine();
            parseLine(line);
        }
        db.close();
    }

    /**
     *
     * @param line
     */
    private void parseLine(String line) {
        Scanner scan = new Scanner(line);
        if(!scan.hasNext()) return; //Nothing to do
        String command = scan.next();
        String restOfLine = scan.hasNext() ? scan.nextLine() : "";
        if(command.equals("add")) add(restOfLine);
        else if(command.equals("active")) listActive(restOfLine);
        else if(command.equals("due")) due(restOfLine);
        else if(command.equals("tag")) addTag(restOfLine);
        else if(command.equals("finish")) finish(restOfLine);
        else if(command.equals("cancel")) cancel(restOfLine);
        else if(command.equals("show")) show(restOfLine);
        else if(command.equals("completed")) showCompleted(restOfLine);
        else if(command.equals("overdue")) showOverdue(restOfLine);
        else if(command.equals("rename")) rename(restOfLine);
        else if(command.equals("search")) search(restOfLine);
        else if(command.equals("exit")) running = false;
        else printUsage();
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

    /**
     * Prints a usage message
     */
    public void printUsage(){
        System.out.println("Invalid command. Read the instructions.");
    }

    private void listActive(String line) {
        Scanner scan = new Scanner(line);
        String tag = null;
        if(scan.hasNext()) tag = scan.next();
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

    private void due(String line) {
        Scanner scan = new Scanner(line);
        String usage = "Usage: due <task id> <date yyyy-MM-dd>\ndue today\ndue soon";
        try {
            String next = scan.next();
            if(next.equals("today")) {
                dueToday();
            } else if (next.equals("soon")) {
                dueSoon();
            } else {
                int taskId = 0;
                Date dueDate = null;
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                taskId = Integer.parseInt(next);
                dueDate = df.parse(scan.next());
                setDue(taskId,dueDate);
            }

        } catch (Exception e) {
            System.out.println(usage);
        }
        scan.close();
    }

    private void dueToday() {
        List<Task> tasksDueToday = db.getTasksDueToday();
        if(tasksDueToday.size() == 0) {
            System.out.println("There are no tasks due today");
        } else {
            for (Task task : tasksDueToday) {
                System.out.println(task);
            }
        }
    }

    private void dueSoon() {
        List<Task> tasksDueSoon = db.getTasksDueSoon();
        if(tasksDueSoon.size() == 0) {
            System.out.println("There are no tasks due soon");
        } else {
            for (Task task : tasksDueSoon) {
                System.out.println(task);
            }
        }
    }

    private void setDue(int taskId, Date date) {
        int successCode = db.setDueDate(taskId,date);
        if(successCode == -1) {
            System.out.println("Failed to set due date");
        } else {
            System.out.println("Set due date for task " + taskId + " to " + date);
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
        Scanner scan = new Scanner(line);
        String usage = "finish <task id>";
        try {
            taskId = Integer.parseInt(scan.next());
            int successCode = db.markTaskComplete(taskId);
            if(successCode == -1) {
                System.out.println("Could not mark task as finished");
            } else {
                System.out.println("Task " + taskId + " marked as finished");
            }
        } catch (Exception e) {
            System.out.println(usage);
        }
    }

    private void cancel(String line) {
        int taskId = 0;
        String usage = "cancel <task id>";
        Scanner scan = new Scanner(line);
        try {
            taskId = Integer.parseInt(scan.next());
            int successCode = db.cancelTask(taskId);
            if(successCode == -1) {
                System.out.println("Could not mark task as canceled");
            } else {
                System.out.println("Task " + taskId + " marked as canceled");
            }
        } catch (Exception e) {
            System.out.println(usage);
        }
    }

    private void show(String line) {
        int taskId = 0;
        String usage = "show <task id>";
        Scanner scan = new Scanner(line);
        try {
            taskId = Integer.parseInt(scan.next());
            Task task = db.lookupTask(taskId);
            if(task == null) {
                System.out.println("No task with that id");
            } else {
                System.out.println(task.toString());
            }
        } catch (Exception e) {
            System.out.println(usage);
        }
    }

    private void showCompleted(String line) {
        Scanner scan = new Scanner(line);
        String usage = "completed <tag>";
        String tag = null;
        if(scan.hasNext()) tag = scan.next();
        List<Task> completedTasks = db.getCompletedTasks(tag);
        if(completedTasks.size() == 0) {
            System.out.println("No completed tasks with that tag");
        } else {
            for(Task task : completedTasks) {
                System.out.println(task);
            }
        }
    }

    private void showOverdue(String line) {
        List<Task> overdueTasks = db.getOverdueTasks();
        if(overdueTasks.size() == 0) {
            System.out.println("No overdue tasks");
        } else {
            for (Task task : overdueTasks) {
                System.out.println(task);
            }
        }
    }

    private void rename(String line) {
        Scanner scan = new Scanner(line);
        String usage = "rename <task id> <new label>";
        try {
            int taskId = Integer.parseInt(scan.next());
            String newLabel = scan.nextLine().trim();
            if(newLabel.length() == 0) {
                System.out.println(usage);
                return;
            }
            int successCode = db.renameTask(taskId,newLabel);
            if(successCode == -1) {
                System.out.println("Could not rename task");
            } else {
                System.out.println("Renamed task to " + newLabel);
            }

        } catch(Exception e) {
            System.out.println(usage);
        }
    }

    private void search(String line) {
        String usage = "search <keyword>";
        String keyword = line.trim();
        if(keyword.length() == 0) {
            System.out.println(usage);
            return;
        }
        List<Task> tasks = db.search(keyword);
        if(tasks.size() == 0) {
            System.out.println("No results");
            return;
        }
        for(Task task: tasks) {
            System.out.println(task);
        }
    }
}
