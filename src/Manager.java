import java.util.ArrayList;
import java.util.HashMap;

public class Manager {

    int nextId = 1;
    HashMap<Integer, Task> tasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();
    HashMap<Integer, Subtask> subtasks = new HashMap<>();

    public void showAllTasks() {
        if (!tasks.isEmpty()) {
            System.out.println("Задачи");
            for (Task task : tasks.values()) {
                System.out.println(task);
            }
        } else {
            System.out.println("Задачи отсутствуют");
        }
        if (!epics.isEmpty()) {
            for (Epic epic : epics.values()) {
                System.out.println("Эпик");
                System.out.println(epic);
                if (!epic.subtaskIds.isEmpty()) {
                    System.out.println("Подзадачи эпика:");
                    for (Integer subtaskId : epic.subtaskIds) {
                        System.out.println(subtasks.get(subtaskId));
                    }
                } else {
                    System.out.println("Подзадачи эпика отсутствуют");
                }
            }
        } else {
            System.out.println("Эпики отсутствуют");
        }
    }

    public void createTask(Task task) {
        task.id = nextId;
        nextId++;
        tasks.put(task.id, task);
    }

    public void createTask(Epic epic) {
        epic.id = nextId;
        epic.subtaskIds = new ArrayList<>();
        nextId++;
        epics.put(epic.id, epic);
    }

    public void createTask(Subtask subtask) {
        if (epics.containsKey(subtask.epicId)) {
            subtask.id = nextId;
            nextId++;
            subtasks.put(subtask.id, subtask);
            epics.get(subtask.epicId).subtaskIds.add(subtask.id);
            refreshEpics();
        } else {
            System.out.println("Эпик не найден");
        }
    }

    public void updateTask(int id, Task task) {
        task.id = id;
        tasks.put(task.id, task);
    }

    public void updateTask(int id, Epic epic) {
        epic.id = id;
        epic.subtaskIds = epics.get(id).subtaskIds;
        epics.put(epic.id, epic);
    }

    public void updateTask(int id, Subtask subtask) {
        subtask.id = id;
        subtasks.put(subtask.id, subtask);
        refreshEpics();
    }

    public void findTask(int id) {
        if (tasks.containsKey(id)) {
            System.out.println(tasks.get(id));
        } else if (epics.containsKey(id)) {
            System.out.println(epics.get(id));
        } else if (subtasks.containsKey(id)) {
            System.out.println(subtasks.get(id));
        } else {
            System.out.println("Задача с данным ID не найдена");
        }
    }

    public void deleteTask(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
            System.out.println("Задача удалена");
        } else if (epics.containsKey(id)) {
            for (Integer subtaskId : epics.get(id).subtaskIds) {
                subtasks.remove(subtaskId);
            }
            epics.get(id).subtaskIds.clear();
            epics.remove(id);
            System.out.println("Эпик удалён");
        } else if (subtasks.containsKey(id)) {
            epics.get(subtasks.get(id).epicId).subtaskIds.removeIf(subtaskId -> subtaskId.equals(id));
            subtasks.remove(id);
            System.out.println("Подзадача удалена");
            refreshEpics();
        } else {
            System.out.println("Задача с данным ID не найдена");
        }
    }

    public void deleteAllTasks() {
        tasks.clear();
        epics.clear();
        subtasks.clear();
        nextId = 1;
    }

    public void refreshEpics() {
        for (Epic epic : epics.values()) {
            if (!epic.subtaskIds.isEmpty()) {
                int newSubtasks = 0;
                int doneSubtasks = 0;
                for (Integer subtaskId : epic.subtaskIds) {
                    if (subtasks.get(subtaskId).status.equals(Status.NEW)) {
                        newSubtasks++;
                    } else if (subtasks.get(subtaskId).status.equals(Status.DONE)) {
                        doneSubtasks++;
                    }
                }
                if (newSubtasks != 0 && doneSubtasks == 0) {
                    epic.status = Status.NEW;
                } else if (newSubtasks == 0 && doneSubtasks != 0) {
                    epic.status = Status.DONE;
                } else {
                    epic.status = Status.IN_PROGRESS;
                }
            } else {
                epic.status = Status.NEW;
            }
        }
    }

}