package manager;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {

    static ArrayList<Integer> history = new ArrayList<>();
    int recentItemsSize = 10;

    @Override
    public void getHistory() {
        for (int id : history) {
            if (InMemoryTaskManager.getTasks().containsKey(id)) {
                System.out.println(InMemoryTaskManager.getTasks().get(id));
            } else if (InMemoryTaskManager.getEpics().containsKey(id)) {
                System.out.println(InMemoryTaskManager.getEpics().get(id));
            } else {
                System.out.println(InMemoryTaskManager.getSubtasks().get(id));
            }
        }
    }

    @Override
    public void addToHistory(int id) {
        if (history.size() < recentItemsSize) {
            history.add(id);
        } else {
            history.removeFirst();
            history.add(id);
        }
    }

    @Override
    public void deleteFromHistory(int id) {
        if (history.contains(id)) {
            history.remove(Integer.valueOf(id));
        }
    }

    public static ArrayList<Integer> getHistoryArray() {
        return history;
    }
}
