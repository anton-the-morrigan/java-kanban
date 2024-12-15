package test;

import task.*;
import manager.InMemoryHistoryManager;
import manager.InMemoryTaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class InMemoryTaskManagerTest {
    InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
    InMemoryTaskManager taskManager = new InMemoryTaskManager(historyManager);


    @Test
    public void createTask() {

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

        Epic testEpic6 = new Epic("Тестовый эпик 2", "Выполнить все сабтаски тестового эпика 2", Status.NEW);
        taskManager.createTask(testEpic6);

        Subtask testSubtask7 = new Subtask("Тестовый сабтаск 7", "Выполнить тестовый сабтаск 7", Status.NEW, 6);
        taskManager.createTask(testSubtask7);

        Subtask testSubtask8 = new Subtask("Тестовый сабтаск 8", "Выполнить тестовый сабтаск 8", Status.NEW, 6);
        taskManager.createTask(testSubtask8);

        Task testTask9 = new Task("Тестовый таск 9", "Выполнить тестовый таск 9", Status.NEW);
        taskManager.createTask(testTask9);

        Task testEpic10 = new Epic("Тестовый эпик 10", "Выполнить все сабтаски тестового эпика 10", Status.NEW);
        taskManager.createTask(testEpic10);

        Assertions.assertEquals(testEpic10, InMemoryTaskManager.getTasks().get(10));
        Assertions.assertEquals(testEpic1, InMemoryTaskManager.getEpics().get(1));
        Assertions.assertEquals(testSubtask5, InMemoryTaskManager.getSubtasks().get(5));

        int taskCount = InMemoryTaskManager.getTasks().size() + InMemoryTaskManager.getEpics().size()  + InMemoryTaskManager.getSubtasks().size();
        Assertions.assertEquals(10, taskCount);
    }

    @Test
    public void deleteTask() {
        int taskCount;

        Task testTask1 = new Task("Тестовый таск 1", "Выполнить тестовый таск 1", Status.NEW);
        taskManager.createTask(testTask1);

        Epic testEpic2 = new Epic("Тестовый эпик 2", "Выполнить все сабтаски тестового эпика 2", Status.NEW);
        taskManager.createTask(testEpic2);

        Subtask testSubtask3 = new Subtask("Тестовый сабтаск 3", "Выполнить тестовый сабтаск 3", Status.NEW, 2);
        taskManager.createTask(testSubtask3);

        Subtask testSubtask4 = new Subtask("Тестовый сабтаск 4", "Выполнить тестовый сабтаск 4", Status.NEW, 2);
        taskManager.createTask(testSubtask4);

        taskManager.deleteTask(1);
        taskCount = InMemoryTaskManager.getTasks().size() + InMemoryTaskManager.getEpics().size() + InMemoryTaskManager.getSubtasks().size();
        Assertions.assertEquals(3, taskCount);

        taskManager.deleteTask(4);
        taskCount = InMemoryTaskManager.getTasks().size() + InMemoryTaskManager.getEpics().size() + InMemoryTaskManager.getSubtasks().size();
        Assertions.assertEquals(2, taskCount);

        taskManager.deleteTask(2);
        taskCount = InMemoryTaskManager.getTasks().size() + InMemoryTaskManager.getEpics().size() + InMemoryTaskManager.getSubtasks().size();
        Assertions.assertEquals(0, taskCount);


    }

    @Test
    public void updateTask() {

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

        Assertions.assertEquals(InMemoryTaskManager.getTasks().get(1), testUpdatedTask);
        Assertions.assertEquals(InMemoryTaskManager.getEpics().get(2), testUpdatedEpic);
        Assertions.assertEquals(InMemoryTaskManager.getSubtasks().get(3), testUpdatedSubtask);

    }

    @Test
    public void deleteAllTask() {
        Epic testEpic1 = new Epic("Тестовый эпик 1", "Выполнить все сабтаски тестового эпика 1", Status.NEW);
        taskManager.createTask(testEpic1);

        Subtask testSubtask2 = new Subtask("Тестовый сабтаск 2", "Выполнить тестовый сабтаск 2", Status.NEW, 1);
        taskManager.createTask(testSubtask2);

        Subtask testSubtask3 = new Subtask("Тестовый сабтаск 3", "Выполнить тестовый сабтаск 3", Status.NEW, 1);
        taskManager.createTask(testSubtask3);

        taskManager.deleteAllTasks();

        int taskCount = InMemoryTaskManager.getTasks().size() + InMemoryTaskManager.getEpics().size()  + InMemoryTaskManager.getSubtasks().size();

        Assertions.assertEquals(0, taskCount);
    }

}
