package manager;

public class Managers {
    public static TaskManager getDefaults(HistoryManager historyManager) {
        return new InMemoryTaskManager(getDefaultHistory());
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
