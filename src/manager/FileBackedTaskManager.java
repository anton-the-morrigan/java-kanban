package manager;

import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class FileBackedTaskManager extends InMemoryTaskManager {

    File file;


    public FileBackedTaskManager(HistoryManager historyManager, File file) {
        super(historyManager);
        this.file = file;
        loadFromFile(file);
    }


    @Override
    public void showAllTasks() {
        super.showAllTasks();
    }

    @Override
    public void createTask(Task task) {
        super.createTask(task);
        saveToFile();
    }

    @Override
    public void createTask(Epic epic) {
        super.createTask(epic);
        saveToFile();
    }

    @Override
    public void createTask(Subtask subtask) {
        super.createTask(subtask);
        saveToFile();
    }

    @Override
    public void updateTask(int id, Task task) {
        super.updateTask(id, task);
        saveToFile();
    }

    @Override
    public void updateTask(int id, Epic epic) {
        super.updateTask(id, epic);
        saveToFile();
    }

    @Override
    public void updateTask(int id, Subtask subtask) {
        super.updateTask(id, subtask);
        saveToFile();
    }

    @Override
    public void findTask(int id) {
        super.findTask(id);
    }

    @Override
    public void deleteTask(int id) {
        super.deleteTask(id);
        saveToFile();
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        saveToFile();
    }

    @Override
    public void refreshEpics() {
        super.refreshEpics();
    }

    public void saveToFile() {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("id,type,name,status,description,epic").append(System.lineSeparator());

            for (Task task : InMemoryTaskManager.tasks.values()) {
                sb.append(task.id).append(",").append("TASK").append(",").append(task.name).append(",").append(task.status).append(",").append(task.description).append("\n");
            }

            for (Epic epic : InMemoryTaskManager.epics.values()) {
                sb.append(epic.id).append(",").append("EPIC").append(",").append(epic.name).append(",").append(epic.status).append(",").append(epic.description).append("\n");
            }

            for (Subtask subtask : InMemoryTaskManager.subtasks.values()) {
                sb.append(subtask.id).append(",").append("SUBTASK").append(",").append(subtask.name).append(",").append(subtask.status).append(",").append(subtask.description).append(",").append(subtask.epicId).append("\n");
            }

            Files.writeString(file.toPath(), sb.toString());
        } catch (IOException e) {
            System.out.println("Произошла ошибка при сохранении файла");
        }
    }

    public void loadFromFile(File file) {
        if (file.exists()) {
            try (BufferedReader br = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8)) {
                while (br.ready()) {
                    String line = br.readLine();
                    if (!line.isEmpty()) {
                        String[] list = line.split(",");
                        if (list[1].equals("TASK")) {
                            Task task = new Task(list[2], list[3], Status.valueOf(list[4]));
                            task.id = Integer.parseInt(list[0]);
                            tasks.put(task.id, task);
                            nextId++;
                        } else if (list[1].equals("EPIC")) {
                            Epic epic = new Epic(list[2], list[3], Status.valueOf(list[4]));
                            epic.id = Integer.parseInt(list[0]);
                            tasks.put(epic.id, epic);
                            nextId++;
                        } else if (list[1].equals("SUBTASK")) {
                            Subtask subtask = new Subtask(list[2], list[3], Status.valueOf(list[4]), Integer.parseInt(list[5]));
                            subtask.id = Integer.parseInt(list[0]);
                            subtasks.put(subtask.id, subtask);
                            nextId++;
                        }
                    } else {
                        break;
                    }
                }

            } catch (IOException e) {
                System.out.println("Произошла ошибка при загрузке файла");
            }
        }
    }
}
