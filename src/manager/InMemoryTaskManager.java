package manager;

import exception.IntersectionException;
import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    HistoryManager historyManager;

    int nextId = 1;
    public static HashMap<Integer, Task> tasks;
    public static HashMap<Integer, Epic> epics;
    public static HashMap<Integer, Subtask> subtasks;

    public static Set<Integer> prioritizedTasks = new TreeSet<>(new Comparator<>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            if (tasks.containsKey(o1)) {
                if (tasks.containsKey(o2)) {
                    return tasks.get(o1).startTime.compareTo(tasks.get(o2).startTime);
                } else if (subtasks.containsKey(o2)) {
                    return tasks.get(o1).startTime.compareTo(subtasks.get(o2).startTime);
                }

            } else if (subtasks.containsKey(o1)) {
                if (tasks.containsKey(o2)) {
                    return subtasks.get(o1).startTime.compareTo(tasks.get(o2).startTime);
                } else if (subtasks.containsKey(o2)) {
                    return subtasks.get(o1).startTime.compareTo(subtasks.get(o2).startTime);
                }
            }
            return 0;
        }
    });


    public InMemoryTaskManager(HistoryManager historyManager) {
        this.historyManager = historyManager;
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subtasks = new HashMap<>();
    }

    public void getPrioritizedTasks() {
        for (Integer prioritizedTaskId : prioritizedTasks) {
            if (tasks.containsKey(prioritizedTaskId)) {
                System.out.println(tasks.get(prioritizedTaskId));
            } else if (subtasks.containsKey(prioritizedTaskId)) {
                System.out.println(subtasks.get(prioritizedTaskId));
            }
        }
    }

    private boolean isTaskSuitableForPriority(Task task, Task previousTask, Task nextTask) {
        if (previousTask == null && (task.startTime.isAfter(nextTask.startTime) || task.endTime.isAfter(nextTask.endTime))) {
            return false;
        }
        if (nextTask == null && (task.startTime.isBefore(previousTask.startTime) || task.endTime.isBefore(previousTask.endTime))) {
            return false;
        }

        if (previousTask != null && task.startTime.isBefore(previousTask.startTime)) {
            return false;
        }
        if (nextTask != null && task.endTime.isAfter(nextTask.startTime)) {
            return false;
        }

        return true;
    }

    private static Task getAnyTaskById(Integer id) {
        if(tasks.containsKey(id)) {
            return tasks.get(id);
        }
        if(subtasks.containsKey(id)) {
            return subtasks.get(id);
        }
        if(epics.containsKey(id)) {
            return epics.get(id);
        }
        return null;
    }

    public void addPrioritizedTasks(Task newTask) {
        if (newTask.startTime == null || newTask.endTime == null) {
            return;
        }

        List<Integer> prioritizedTasksList = new ArrayList<>(prioritizedTasks);

        if (prioritizedTasksList.isEmpty()) {
            prioritizedTasks.add(newTask.id);
            return;
        }

        for (int i = -1; i < prioritizedTasksList.size(); i++) {
            Task previousTask = i < 0 ? null : getAnyTaskById(prioritizedTasksList.get(i));
            Task nextTask = i + 1 > prioritizedTasksList.size() - 1 ? null : getAnyTaskById(prioritizedTasksList.get(i + 1));

            boolean isSuitable = isTaskSuitableForPriority(
                    newTask,
                    previousTask,
                    nextTask
            );
            if(isSuitable) {
                prioritizedTasks.add(newTask.id);
                return;
            }
        }
        throw new IntersectionException("Время выполнения данной задачи пересекается со временем уже существующей.");
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
        addPrioritizedTasks(task);
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

            addPrioritizedTasks(subtask);

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
        addPrioritizedTasks(task);
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
        addPrioritizedTasks(subtask);
        refreshEpics();
    }

    @Override
    public void findTask(int id) {
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
            prioritizedTasks.remove(id);
        } else if (epics.containsKey(id)) {
            for (Integer subtaskId : epics.get(id).subtaskIds) {
                subtasks.remove(subtaskId);
                prioritizedTasks.remove(subtaskId);
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
            prioritizedTasks.remove(id);
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
        prioritizedTasks.clear();
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


                    if (subtasks.get(subtaskId).duration != null) {
                        epic.duration = epic.duration.plus(subtasks.get(subtaskId).duration);
                    }

                    if (epic.startTime == null || (subtasks.get(subtaskId).startTime != null && subtasks.get(subtaskId).startTime.isBefore(epic.startTime))) {
                        epic.startTime = subtasks.get(subtaskId).startTime;
                    }

                    if (epic.endTime == null || (subtasks.get(subtaskId).endTime != null && subtasks.get(subtaskId).endTime.isAfter(epic.endTime))) {
                        epic.endTime = subtasks.get(subtaskId).endTime;
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