package test;

import src.task.*;
import src.manager.InMemoryHistoryManager;
import src.manager.InMemoryTaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class InMemoryHistoryManagerTest {
    InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
    InMemoryTaskManager taskManager = new InMemoryTaskManager(historyManager);

    @Test
    void getHistory() {
        Task testTask1 = new Task("Прогуляться", "Сходить погулять в парк", Status.NEW);
        taskManager.createTask(testTask1);

        Task testTask2 = new Task("Отдохнуть", "Поиграть в какую-нибудь видеоигру", Status.NEW);
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

        Assertions.assertEquals(2, historyManager.history.size());

        taskManager.deleteTask(2);
        Assertions.assertEquals(1, historyManager.history.size());

        taskManager.deleteAllTasks();
        Assertions.assertEquals(0, historyManager.history.size());
    }
}