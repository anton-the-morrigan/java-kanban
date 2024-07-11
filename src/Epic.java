import java.util.ArrayList;

public class Epic extends Task {

    ArrayList<Integer> subtaskIds;

    public Epic (String name, String description, Status status) {
        super(name, description, status);
    }
}