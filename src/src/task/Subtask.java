package src.task;

public class Subtask extends Task {

    public int epicId;

    public Subtask(String name, String description, Status status, int epicId) {
        super(name, description, status);
        this.epicId = epicId;
    }
}