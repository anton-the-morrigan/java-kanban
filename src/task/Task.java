package task;

public class Task {

    public int id;
    public String name;
    public String description;
    public Status status;

    public Task(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    @Override
    public String toString() {
        return "name='" + name + "', description='" + description + "', status='" + status + "'";
    }

    @Override
    public int hashCode() {
        return id;
    }

}