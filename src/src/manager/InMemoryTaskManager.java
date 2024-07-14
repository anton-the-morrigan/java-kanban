package src.manager;

import src.task.Epic;
import src.task.Status;
import src.task.Subtask;
import src.task.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {

    HistoryManager historyManager;

    int nextId = 1;
    public static HashMap<Integer, Task> tasks;
    public static HashMap<Integer, Epic> epics;
    public static HashMap<Integer, Subtask> subtasks;

    public InMemoryTaskManager(HistoryManager historyManager) {
        this.historyManager = historyManager;
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subtasks = new HashMap<>();
    }

    @Override
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

    @Override
    public void createTask(Task task) {
        task.id = nextId;
        nextId++;
        tasks.put(task.id, task);
    }

    @Override
    public void createTask(Epic epic) {
        epic.id = nextId;
        epic.subtaskIds = new ArrayList<>();
        nextId++;
        epics.put(epic.id, epic);
    }

    @Override
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

    @Override
    public void updateTask(int id, Task task) {
        task.id = id;
        tasks.put(task.id, task);
    }

    @Override
    public void updateTask(int id, Epic epic) {
        epic.id = id;
        epic.subtaskIds = epics.get(id).subtaskIds;
        epics.put(epic.id, epic);
    }

    @Override
    public void updateTask(int id, Subtask subtask) {
        subtask.id = id;
        subtasks.put(subtask.id, subtask);
        refreshEpics();
    }

    @Override
    public void findTask(int id) { //добавить addToHistory
        if (tasks.containsKey(id)) {
            System.out.println(tasks.get(id));
            historyManager.addToHistory(id);
        } else if (epics.containsKey(id)) {
            System.out.println(epics.get(id));
            historyManager.addToHistory(id);
        } else if (subtasks.containsKey(id)) {
            System.out.println(subtasks.get(id));
            historyManager.addToHistory(id);
        } else {
            System.out.println("Задача с данным ID не найдена");
        }
    }

    @Override
    public void deleteTask(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
            System.out.println("Задача удалена");
            historyManager.deleteFromHistory(id);
        } else if (epics.containsKey(id)) {
            for (Integer subtaskId : epics.get(id).subtaskIds) {
                subtasks.remove(subtaskId);
            }
            epics.get(id).subtaskIds.clear();
            epics.remove(id);
            System.out.println("Эпик удалён");
            historyManager.deleteFromHistory(id);
        } else if (subtasks.containsKey(id)) {
            epics.get(subtasks.get(id).epicId).subtaskIds.removeIf(subtaskId -> subtaskId.equals(id));
            subtasks.remove(id);
            System.out.println("Подзадача удалена");
            refreshEpics();
            historyManager.deleteFromHistory(id);
        } else {
            System.out.println("Задача с данным ID не найдена");
        }
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
        epics.clear();
        subtasks.clear();
        InMemoryHistoryManager.getHistoryArray().clear();
        nextId = 1;
    }

    @Override
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

    public static HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    public static HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    public static HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

}