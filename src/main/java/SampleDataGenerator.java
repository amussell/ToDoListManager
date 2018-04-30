import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class SampleDataGenerator {
    public static void main(String[] args) {
        tasks();

    }
    private static void tags() {
        File file = new File("C:\\Users\\Alex Mussell\\Documents\\EXAMPLE-DATA-TAGS.csv");
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Scanner s = new Scanner(file);
            s.nextLine();
            while(s.hasNextLine()) {
                String line = s.nextLine();
                if(line.trim().length() == 0) {
                    continue;
                }
                Scanner ls = new Scanner(line);
                ls.useDelimiter(",");
                String tag = ls.next();
                String sql = "INSERT INTO tags (name) VALUES ('" + tag + "');";
                System.out.println(sql);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private static void taggedTasks() {
        File file = new File("C:\\Users\\Alex Mussell\\Documents\\EXAMPLE-DATA-TAGGED-TASKS.csv");
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Scanner s = new Scanner(file);
            s.nextLine();
            while(s.hasNextLine()) {
                String line = s.nextLine();
                if(line.trim().length() == 0) {
                    continue;
                }
                Scanner ls = new Scanner(line);
                ls.useDelimiter(",");
                int taskid = Integer.parseInt(ls.next());
                String tag = ls.next();
                String sql = "INSERT INTO tagged_task (task_id,tag_name) VALUES (" + taskid + ",'" + tag + "');";
                System.out.println(sql);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void tasks() {
        File file = new File("C:\\Users\\Alex Mussell\\Documents\\EXAMPLE-DATA-TASKS.csv");
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Scanner s = new Scanner(file);
            s.nextLine();
            while(s.hasNextLine()) {
                String line = s.nextLine();
                if(line.trim().length() == 0) {
                    continue;
                }
                Scanner ls = new Scanner(line);
                ls.useDelimiter(",");
                int id = Integer.parseInt(ls.next());
                String label = ls.next();
                String create_date = ls.next();
                String due_date = ls.next();
                int status = Integer.parseInt(ls.next());
                String sql;
                if(due_date.length() != 0) {
                    sql = "INSERT INTO task (id,label,create_date,due_date,status) VALUES (" + id + ",'" + label + "','" + create_date + "','" + due_date + "'," + status + ");";
                } else {
                    sql = "INSERT INTO task (id,label,create_date,status) VALUES (" + id + ",'" + label + "','" + create_date + "'," + status + ");";
                }
                System.out.println(sql);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
