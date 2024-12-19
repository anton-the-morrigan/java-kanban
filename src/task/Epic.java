package task;

import java.time.Duration;
import java.util.ArrayList;

public class Epic extends Task {

    public ArrayList<Integer> subtaskIds;

    public Epic(String name, String description, Status status) {
        super(name, description, status);
        this.duration = Duration.ZERO;
        this.startTime = null;
        this.endTime = null;
    }
}
