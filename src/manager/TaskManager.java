package manager;

import task.Epic;
import task.Subtask;
import task.Task;

public interface TaskManager {
    void showAllTasks();

    void createTask(Task task);

    void createTask(Epic epic);

    void createTask(Subtask subtask);

    void updateTask(int id, Task task);

    void updateTask(int id, Epic epic);

    void updateTask(int id, Subtask subtask);

    void findTask(int id);

    void deleteTask(int id);

    void deleteAllTasks();

    void refreshEpics();

}
