package test;

import src.task.*;
import src.manager.InMemoryHistoryManager;
import src.manager.InMemoryTaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class InMemoryTaskManagerTest {
    InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
    InMemoryTaskManager taskManager = new InMemoryTaskManager(historyManager);


    @Test
    public void createTask() {

        Epic testEpic1 = new Epic("Забота", "Позаботиться о кошке", Status.NEW);
        taskManager.createTask(testEpic1);

        Subtask testSubtask2 = new Subtask("Мяукнуть", "Мяукнуть на кошку", Status.NEW, 1);
        taskManager.createTask(testSubtask2);

        Subtask testSubtask3 = new Subtask("Муркнуть", "Муркнуть на кошку", Status.NEW, 1);
        taskManager.createTask(testSubtask3);

        Subtask testSubtask4 = new Subtask("Погладить", "Погладить кошку по головушке", Status.NEW, 1);
        taskManager.createTask(testSubtask4);

        Subtask testSubtask5 = new Subtask("Почесать", "Почесать кошку за ушком", Status.NEW, 1);
        taskManager.createTask(testSubtask5);

        Epic testEpic6 = new Epic("Выжить", "Пережить сегодняшний день", Status.NEW);
        taskManager.createTask(testEpic6);

        Subtask testSubtask7 = new Subtask("Поесть", "Поесть сегодня еду", Status.NEW, 6);
        taskManager.createTask(testSubtask7);

        Subtask testSubtask8 = new Subtask("Поспать", "Поспать сегодня достаточное время", Status.NEW, 6);
        taskManager.createTask(testSubtask8);

        Task testTask9 = new Task("Прогуляться", "Сходить погулять в парк", Status.NEW);
        taskManager.createTask(testTask9);

        Task testEpic10 = new Epic("Отдохнуть", "Найти способ отдохнуть", Status.NEW);
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

        Task testTask1 = new Task("Отдохнуть", "Поиграть в какую-нибудь видеоигру", Status.NEW);
        taskManager.createTask(testTask1);

        Epic testEpic2 = new Epic("Выжить", "Пережить сегодняшний день", Status.NEW);
        taskManager.createTask(testEpic2);

        Subtask testSubtask3 = new Subtask("Поесть", "Поесть сегодня еду", Status.NEW, 2);
        taskManager.createTask(testSubtask3);

        Subtask testSubtask4 = new Subtask("Поспать", "Поспать сегодня достаточное время", Status.NEW, 2);
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

        Task testTask1 = new Task("Не сдохнуть", "Сдать спринт n", Status.DONE);
        taskManager.createTask(testTask1);

        Epic testEpic2 = new Epic("Забота", "Позаботиться о кошке", Status.NEW);
        taskManager.createTask(testEpic2);

        Subtask testSubtask3 = new Subtask("Мяукнуть", "Мяукнуть на кошку", Status.NEW, 2);
        taskManager.createTask(testSubtask3);

        Task testUpdatedTask = new Task("Не сдохнуть", "Сдать спринт n+1", Status.NEW);
        Epic testUpdatedEpic = new Epic("Досуг", "Провести время с кошкой", Status.NEW);
        Subtask testUpdatedSubtask = new Subtask("Поиграть", "Поиграть с кошкой", Status.DONE, 2);

        taskManager.updateTask(1, testUpdatedTask);
        taskManager.updateTask(2, testUpdatedEpic);
        taskManager.updateTask(3, testUpdatedSubtask);

        Assertions.assertEquals(InMemoryTaskManager.getTasks().get(1), testUpdatedTask);
        Assertions.assertEquals(InMemoryTaskManager.getEpics().get(2), testUpdatedEpic);
        Assertions.assertEquals(InMemoryTaskManager.getSubtasks().get(3), testUpdatedSubtask);

    }

    @Test
    public void deleteAllTask() {
        Epic testEpic1 = new Epic("Выжить", "Пережить сегодняшний день", Status.NEW);
        taskManager.createTask(testEpic1);

        Subtask testSubtask2 = new Subtask("Поесть", "Поесть сегодня еду", Status.NEW, 1);
        taskManager.createTask(testSubtask2);

        Subtask testSubtask3 = new Subtask("Поспать", "Поспать сегодня достаточное время", Status.NEW, 1);
        taskManager.createTask(testSubtask3);

        taskManager.deleteAllTasks();

        int taskCount = InMemoryTaskManager.getTasks().size() + InMemoryTaskManager.getEpics().size()  + InMemoryTaskManager.getSubtasks().size();

        Assertions.assertEquals(0, taskCount);
    }

}
