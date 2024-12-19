package task;

import java.time.Duration;
import java.time.LocalDateTime;

public class Task {

    public int id;
    public String name;
    public String description;
    public Status status;

    public Duration duration;
    public LocalDateTime startTime;
    public LocalDateTime endTime;

    public Task(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.duration = Duration.ZERO;
        this.startTime = null;
        this.endTime = null;
    }

    public Task(String name, String description, Status status, Duration duration, LocalDateTime startTime) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.duration = duration;
        this.startTime = startTime;
        if (startTime != null) {
            if (duration == null) {
                throw new IllegalArgumentException("При указании startTime, нужно указать duration");
            }
            this.endTime = startTime.plus(duration);
        }
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
