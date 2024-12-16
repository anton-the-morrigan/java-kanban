package test;

import task.*;
import manager.InMemoryHistoryManager;
import manager.InMemoryTaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class InMemoryHistoryManagerTest {
    InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
    InMemoryTaskManager taskManager = new InMemoryTaskManager(historyManager);

    @Test
    void getHistory() {
        Task testTask1 = new Task("Тестовый таск 1", "Выполнить тестовый таск 1", Status.NEW);
        taskManager.createTask(testTask1);

        Task testTask2 = new Task("Тестовый таск 2", "Выполнить тестовый таск 2", Status.NEW);
        taskManager.createTask(testTask2);

        taskManager.findTask(1);
        taskManager.findTask(2);
        taskManager.findTask(1);
        taskManager.findTask(2);
        taskManager.findTask(1);
        taskManager.findTask(2);
        taskManager.findTask(1);
        taskManager.findTask(2);
        taskManager.findTask(1);
        taskManager.findTask(2);
        taskManager.findTask(1);
        taskManager.findTask(2);
        taskManager.findTask(1);
        taskManager.findTask(2);

        Assertions.assertEquals(2, InMemoryHistoryManager.history.size());

        taskManager.deleteTask(2);
        Assertions.assertEquals(1, InMemoryHistoryManager.history.size());

        taskManager.deleteAllTasks();
        Assertions.assertEquals(0, InMemoryHistoryManager.history.size());
    }
}
