import java.util.ArrayList;
import java.util.Date;

public class Task {
    private String label;
    private int id;
    private Date createDate;
    private Date dueDate;
    private int status; /*0: In Progress 1: Complete 2: Canceled*/
    private ArrayList<String> tags;

    public Task(String label) {
        this.label = label;
        tags = new ArrayList<>();
    }

    public void addTag(String tag) {
        tags.add(tag);
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString() {
        String dueDateString = "";
        String tagsString = "";
        if(dueDate != null) {
            dueDateString = dueDate.toString();
        }
        if(this.tags.size() > 0) {
            tagsString = "Tags: ";
            for(String tag : tags) {
                tagsString += tag + " ";
            }
        }
        return "ID: "+id + " " + "Label: "+label + " " + "DUE: "+dueDateString + " " + "CREATED: "+createDate + " " + "TAG: "+tagsString;
    }
}
