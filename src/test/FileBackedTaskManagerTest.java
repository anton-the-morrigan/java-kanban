package test;

import exception.IntersectionException;
import task.*;
import manager.InMemoryHistoryManager;
import manager.FileBackedTaskManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static manager.InMemoryTaskManager.prioritizedTasks;


class FileBackedTaskManagerTest {
    InMemoryHistoryManager historyManager = new InMemoryHistoryManager();



    @Test
    public void createTask() throws IOException {

        File file = Files.createTempFile("tasks", ".csv").toFile();
        file.deleteOnExit();
        FileBackedTaskManager taskManager = new FileBackedTaskManager(historyManager, file);

        Epic testEpic1 = new Epic("Тестовый эпик 1", "Выполнить все сабтаски тестового эпика 1", Status.NEW);
        taskManager.createTask(testEpic1);

        Subtask testSubtask2 = new Subtask("Тестовый сабтаск 2", "Выполнить тестовый сабтаск 2", Status.NEW, 1);
        taskManager.createTask(testSubtask2);

        Subtask testSubtask3 = new Subtask("Тестовый сабтаск 3", "Выполнить тестовый сабтаск 3", Status.NEW, 1);
        taskManager.createTask(testSubtask3);

        Subtask testSubtask4 = new Subtask("Тестовый сабтаск 4", "Выполнить тестовый сабтаск 4", Status.NEW, 1);
        taskManager.createTask(testSubtask4);

        Subtask testSubtask5 = new Subtask("Тестовый сабтаск 5", "Выполнить тестовый сабтаск 5", Status.NEW, 1);
        taskManager.createTask(testSubtask5);

        Epic testEpic6 = new Epic("Тестовый эпик 6", "Выполнить все сабтаски тестового эпика 6", Status.NEW);
        taskManager.createTask(testEpic6);

        Subtask testSubtask7 = new Subtask("Тестовый сабтаск 7", "Выполнить тестовый сабтаск 7", Status.NEW, 6);
        taskManager.createTask(testSubtask7);

        Subtask testSubtask8 = new Subtask("Тестовый сабтаск 8", "Выполнить тестовый сабтаск 8", Status.NEW, 6);
        taskManager.createTask(testSubtask8);

        Task testTask9 = new Task("Тестовый таск 9", "Выполнить тестовый таск 9", Status.NEW);
        taskManager.createTask(testTask9);

        Task testEpic10 = new Epic("Тестовый эпик 10", "Выполнить все сабтаски тестового эпика 10", Status.NEW);
        taskManager.createTask(testEpic10);

        Assertions.assertEquals(testEpic10, FileBackedTaskManager.getTasks().get(10));
        Assertions.assertEquals(testEpic1, FileBackedTaskManager.getEpics().get(1));
        Assertions.assertEquals(testSubtask5, FileBackedTaskManager.getSubtasks().get(5));

        int taskCount = FileBackedTaskManager.getTasks().size() + FileBackedTaskManager.getEpics().size()  + FileBackedTaskManager.getSubtasks().size();
        Assertions.assertEquals(10, taskCount);
    }

    @Test
    public void deleteTask() throws IOException {
        int taskCount;

        File file = Files.createTempFile("tasks", ".csv").toFile();
        file.deleteOnExit();
        FileBackedTaskManager taskManager = new FileBackedTaskManager(historyManager, file);

        Task testTask1 = new Task("Тестовый таск 1", "Выполнить тестовый таск 1", Status.NEW);
        taskManager.createTask(testTask1);

        Epic testEpic2 = new Epic("Тестовый эпик 2", "Выполнить все сабтаски тестового эпика 2", Status.NEW);
        taskManager.createTask(testEpic2);

        Subtask testSubtask3 = new Subtask("Тестовый сабтаск 3", "Выполнить тестовый сабтаск 3", Status.NEW, 2);
        taskManager.createTask(testSubtask3);

        Subtask testSubtask4 = new Subtask("Тестовый сабтаск 4", "Выполнить тестовый сабтаск 4", Status.NEW, 2);
        taskManager.createTask(testSubtask4);

        taskManager.deleteTask(1);
        taskCount = FileBackedTaskManager.getTasks().size() + FileBackedTaskManager.getEpics().size() + FileBackedTaskManager.getSubtasks().size();
        Assertions.assertEquals(3, taskCount);

        taskManager.deleteTask(4);
        taskCount = FileBackedTaskManager.getTasks().size() + FileBackedTaskManager.getEpics().size() + FileBackedTaskManager.getSubtasks().size();
        Assertions.assertEquals(2, taskCount);

        taskManager.deleteTask(2);
        taskCount = FileBackedTaskManager.getTasks().size() + FileBackedTaskManager.getEpics().size() + FileBackedTaskManager.getSubtasks().size();
        Assertions.assertEquals(0, taskCount);


    }

    @Test
    public void updateTask() throws IOException {

        File file = Files.createTempFile("tasks", ".csv").toFile();
        file.deleteOnExit();
        FileBackedTaskManager taskManager = new FileBackedTaskManager(historyManager, file);

        Task testTask1 = new Task("Тестовый таск 1", "Выполнить тестовый таск 1", Status.DONE);
        taskManager.createTask(testTask1);

        Epic testEpic2 = new Epic("Тестовый эпик 2", "Выполнить все сабтаски тестового эпика 2", Status.NEW);
        taskManager.createTask(testEpic2);

        Subtask testSubtask3 = new Subtask("Тестовый сабтаск 3", "Выполнить тестовый сабтаск 3", Status.NEW, 2);
        taskManager.createTask(testSubtask3);

        Task testUpdatedTask = new Task("Обновлённый тестовый таск 1", "Выполнить обновлённый тестовый таск 1", Status.NEW);
        Epic testUpdatedEpic = new Epic("Обновлённый тестовый эпик 2", "Выполнить все сабтаски обновлённого тестового эпика 2", Status.NEW);
        Subtask testUpdatedSubtask = new Subtask("Обновлённый тестовый сабтаск 3", "Выполнить обновлённый тестовый сабтаск 3", Status.DONE, 2);

        taskManager.updateTask(1, testUpdatedTask);
        taskManager.updateTask(2, testUpdatedEpic);
        taskManager.updateTask(3, testUpdatedSubtask);

        Assertions.assertEquals(FileBackedTaskManager.getTasks().get(1), testUpdatedTask);
        Assertions.assertEquals(FileBackedTaskManager.getEpics().get(2), testUpdatedEpic);
        Assertions.assertEquals(FileBackedTaskManager.getSubtasks().get(3), testUpdatedSubtask);

    }

    @Test
    public void deleteAllTask() throws IOException {

        File file = Files.createTempFile("tasks", ".csv").toFile();
        file.deleteOnExit();
        FileBackedTaskManager taskManager = new FileBackedTaskManager(historyManager, file);

        Epic testEpic1 = new Epic("Тестовый эпик 1", "Выполнить все сабтаски тестового эпика 1", Status.NEW);
        taskManager.createTask(testEpic1);

        Subtask testSubtask2 = new Subtask("Тестовый сабтаск 2", "Выполнить тестовый сабтаск 2", Status.NEW, 1);
        taskManager.createTask(testSubtask2);

        Subtask testSubtask3 = new Subtask("Тестовый сабтаск 3", "Выполнить тестовый сабтаск 3", Status.NEW, 1);
        taskManager.createTask(testSubtask3);

        taskManager.deleteAllTasks();

        int taskCount = FileBackedTaskManager.getTasks().size() + FileBackedTaskManager.getEpics().size()  + FileBackedTaskManager.getSubtasks().size();

        Assertions.assertEquals(0, taskCount);
    }

    @Test
    public void testIntersectionException() throws IOException  {

        File file = Files.createTempFile("tasks", ".csv").toFile();
        file.deleteOnExit();
        FileBackedTaskManager taskManager = new FileBackedTaskManager(historyManager, file);

        boolean thrown = false;
        try {
            Task testTask1 = new Task("Тестовый таск 1", "Выполнить тестовый таск 1", Status.NEW, Duration.ofMinutes(30), LocalDateTime.of(2024, 12, 19, 17, 30));
            taskManager.createTask(testTask1);

            Task testTask2 = new Task("Тестовый таск 2", "Выполнить тестовый таск 2", Status.NEW, Duration.ofMinutes(90), LocalDateTime.of(2024, 12, 19, 18, 30));
            taskManager.createTask(testTask2);

            Task testTask3 = new Task("Тестовый таск 3", "Выполнить тестовый таск 3", Status.NEW, Duration.ofMinutes(30), LocalDateTime.of(2024, 12, 19, 19, 1));
            taskManager.createTask(testTask3);
        } catch (IntersectionException e) {
            thrown = true;
        }

        Assertions.assertTrue(thrown);

    }

    @Test
    public void testElementsInPrioritizedTasks()  throws IOException  {

        File file = Files.createTempFile("tasks", ".csv").toFile();
        file.deleteOnExit();
        FileBackedTaskManager taskManager = new FileBackedTaskManager(historyManager, file);

        Task testTask1 = new Task("Тестовый таск 1", "Выполнить тестовый таск 1", Status.NEW, Duration.ofMinutes(30), LocalDateTime.of(2024,12,19,17,30));
        taskManager.createTask(testTask1);

        Task testTask2 = new Task("Тестовый таск 2", "Выполнить тестовый таск 2", Status.NEW, Duration.ofMinutes(30), LocalDateTime.of(2024,12,19,18,30));
        taskManager.createTask(testTask2);

        Task testTask3 = new Task("Тестовый таск 3", "Выполнить тестовый таск 3", Status.NEW, Duration.ofMinutes(30), LocalDateTime.of(2024,12,19,19,1));
        taskManager.createTask(testTask3);



        Epic testEpic4 = new Epic("Тестовый эпик 4", "Выполнить все сабтаски тестового эпика 4", Status.NEW);
        taskManager.createTask(testEpic4);

        Subtask testSubtask5 = new Subtask("Тестовый сабтаск 5", "Выполнить тестовый сабтаск 5", Status.NEW, 4, Duration.ofMinutes(30), LocalDateTime.of(2024,12,17,17,30));
        taskManager.createTask(testSubtask5);

        Subtask testSubtask6 = new Subtask("Тестовый сабтаск 6", "Выполнить тестовый сабтаск 6", Status.NEW, 4, Duration.ofMinutes(30), LocalDateTime.of(2024,12,15,18,1));
        taskManager.createTask(testSubtask6);

        Task testTask7 = new Task("Тестовый таск 7", "Выполнить тестовый таск 7", Status.NEW);
        taskManager.createTask(testTask7);

        taskManager.getPrioritizedTasks();

        List<Integer> prioritizedTasksList = new ArrayList<>(prioritizedTasks);

        List<Integer> testList = new ArrayList<>();
        testList.add(6);
        testList.add(5);
        testList.add(1);
        testList.add(2);
        testList.add(3);

        Assertions.assertEquals(prioritizedTasksList, testList);
    }
}
