
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class TaskManager {
    String prompt = "TaskManager>";
    Database db;
    boolean running = true;

    public TaskManager(Credentials credentials) {
        db = new Database(credentials);
    }

    public void run() {
        Scanner in = new Scanner(System.in);
        while(running) { //Will stop when "exit" is entered
            System.out.print(prompt);
            String line = in.nextLine();
            parseLine(line);
        }
        db.close();
    }

    private void parseLine(String line) {
        Scanner scan = new Scanner(line);
        if(!scan.hasNext()) return; //Nothing to do
        String command = scan.next();
        if(command.equals("add")) add(scan.nextLine());
        if(command.equals("active")) listActive(scan.nextLine());
        if(command.equals("due")) due(scan.nextLine());
        if(command.equals("tag")) addTag(scan.nextLine());
        if(command.equals("finish")) finish(scan.nextLine());
        if(command.equals("cancel")) cancel(scan.nextLine());
        if(command.equals("show")) show(scan.nextLine());
        if(command.equals("completed")) showCompleted(scan.nextLine());
        if(command.equals("overdue")) showOverdue(scan.nextLine());
        if(command.equals("rename")) rename(scan.nextLine());
        if(command.equals("search")) search(scan.nextLine());
        if(command.equals("exit")) running = false;
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

    private void due(String line) {
        Scanner scan = new Scanner(line);
        String usage = "Usage: due <date>\ndue today\ndue soon";
        try {
            String next = scan.next();
            if(next.equals("today")) {
                dueToday();
            } else if (next.equals("soon")) {
                dueSoon();
            } else {
                int taskId = 0;
                Date dueDate = null;
                SimpleDateFormat df = new SimpleDateFormat();
                taskId = Integer.parseInt(scan.next());
                dueDate = df.parse(next);
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
        String usage = "finish <task id>";
        try {
            taskId = Integer.parseInt(line);
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
        try {
            taskId = Integer.parseInt(line);
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
        try {
            taskId = Integer.parseInt(line);
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
