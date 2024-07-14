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

        Task testTask10 = new Task("Отдохнуть", "Поиграть в какую-нибудь видеоигру", Status.NEW);
        taskManager.createTask(testTask10);

        int taskCount = taskManager.getTasks().size() + taskManager.getEpics().size()  + taskManager.getSubtasks().size();
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
        taskCount = taskManager.getTasks().size() + taskManager.getEpics().size() + taskManager.getSubtasks().size();
        Assertions.assertEquals(3, taskCount);

        taskManager.deleteTask(4);
        taskCount = taskManager.getTasks().size() + taskManager.getEpics().size() + taskManager.getSubtasks().size();
        Assertions.assertEquals(2, taskCount);

        taskManager.deleteTask(2);
        taskCount = taskManager.getTasks().size() + taskManager.getEpics().size() + taskManager.getSubtasks().size();
        Assertions.assertEquals(0, taskCount);


    }

    @Test
    public void updateTask() {

        Task testTask1 = new Task("Не сдохнуть", "Сдать спринт 4", Status.DONE);
        taskManager.createTask(testTask1);

        Epic testEpic2 = new Epic("Забота", "Позаботиться о кошке", Status.NEW);
        taskManager.createTask(testEpic2);

        Subtask testSubtask3 = new Subtask("Мяукнуть", "Мяукнуть на кошку", Status.NEW, 2);
        taskManager.createTask(testSubtask3);

        Task testUpdatedTask = new Task("Не сдохнуть", "Сдать спринт 5", Status.NEW);
        Epic testUpdatedEpic = new Epic("Досуг", "Провести время с кошкой", Status.NEW);
        Subtask testUpdatedSubtask = new Subtask("Поиграть", "Поиграть с кошкой", Status.DONE, 2);

        taskManager.updateTask(1, testUpdatedTask);
        taskManager.updateTask(2, testUpdatedEpic);
        taskManager.updateTask(3, testUpdatedSubtask);

        Assertions.assertEquals(taskManager.getTasks().get(1), testUpdatedTask);
        Assertions.assertEquals(taskManager.getEpics().get(2), testUpdatedEpic);
        Assertions.assertEquals(taskManager.getSubtasks().get(3), testUpdatedSubtask);

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

        int taskCount = taskManager.getTasks().size() + taskManager.getEpics().size()  + taskManager.getSubtasks().size();

        Assertions.assertEquals(0, taskCount);
    }

}