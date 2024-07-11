import task.*;

public class Main {

    public static void main(String[] args) {

        Manager manager = new Manager();

        System.out.println("Проверка метода createTask()");

        System.out.println("Добавление эпика");
        Epic testEpic1 = new Epic("Забота", "Позаботиться о кошке", Status.NEW);
        manager.createTask(testEpic1);
        System.out.println("Эпик добавлен");

        System.out.println("Добавление подзадач");
        Subtask testSubtask2 = new Subtask("Мяукнуть", "Мяукнуть на кошку", Status.NEW, 1);
        manager.createTask(testSubtask2);
        Subtask testSubtask3 = new Subtask("Муркнуть", "Муркнуть на кошку", Status.NEW, 1);
        manager.createTask(testSubtask3);
        Subtask testSubtask4 = new Subtask("Погладить", "Погладить кошку по головушке", Status.NEW, 1);
        manager.createTask(testSubtask4);
        Subtask testSubtask5 = new Subtask("Почесать", "Почесать кошку за ушком", Status.NEW, 1);
        manager.createTask(testSubtask5);
        System.out.println("Подзадачи добавлены");

        System.out.println("Добавление второго эпика");
        Epic testEpic6 = new Epic("Выжить", "Пережить сегодняшний день", Status.NEW);
        manager.createTask(testEpic6);
        System.out.println("Второй эпик добавлен");

        System.out.println("Добавление подзадач второго эпика");
        Subtask testSubtask7 = new Subtask("Поесть", "Поесть сегодня еду", Status.NEW, 6);
        manager.createTask(testSubtask7);
        Subtask testSubtask8 = new Subtask("Поспать", "Поспать сегодня достаточное время", Status.NEW, 6);
        manager.createTask(testSubtask8);
        System.out.println("Подзадачи второго эпика добавлены");

        System.out.println("Добавление задач");
        Task testTask9 = new Task("Прогуляться", "Сходить погулять в парк", Status.NEW);
        manager.createTask(testTask9);
        Task testTask10 = new Task("Отдохнуть", "Поиграть в какую-нибудь видеоигру", Status.NEW);
        manager.createTask(testTask10);
        System.out.println("Задачи добавлены");

        System.out.println("Метод createTask() проверен");



        System.out.println("Проверка метода findTask()");

        manager.findTask(1);
        manager.findTask(3);
        manager.findTask(5);
        manager.findTask(7);
        manager.findTask(9);

        System.out.println("Метод findTask() проверен");



        System.out.println("Проверка метода showAllTasks()");

        manager.showAllTasks();

        System.out.println("Метод showAllTasks() проверен");



        System.out.println("Проверка метода deleteTask()");

        manager.deleteTask(7);
        manager.deleteTask(8);
        manager.showAllTasks();

        manager.deleteTask(6);
        manager.showAllTasks();

        manager.deleteTask(10);
        manager.showAllTasks();

        System.out.println("Метод deleteTask() проверен");



        System.out.println("Проверка метода updateTask()");

        Task testUpdatedTask = new Task("Не сдохнуть", "Сдать спринт 4", Status.DONE);
        Epic testUpdatedEpic = new Epic("Досуг", "Провести время с кошкой", Status.NEW);
        Subtask testUpdatedSubtask = new Subtask("Поиграть", "Поиграть с кошкой", Status.DONE, 1);
        manager.updateTask(9, testUpdatedTask);
        manager.updateTask(1, testUpdatedEpic);
        manager.updateTask(3, testUpdatedSubtask);
        manager.showAllTasks();

        System.out.println("Метод updateTask() проверен");



        System.out.println("Проверка метода deleteAllTask()");

        manager.deleteAllTasks();
        manager.showAllTasks();

        System.out.println("Метода deleteAllTask() проверен");
    }
}