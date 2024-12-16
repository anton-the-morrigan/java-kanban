package manager;

import java.util.Set;
import java.util.LinkedHashSet;

public class InMemoryHistoryManager implements HistoryManager {

    public static Set<Integer> history = new LinkedHashSet<>();

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
        history.add(id);
    }

    @Override
    public void deleteFromHistory(int id) {
        if (history.contains(id)) {
            history.removeIf(item -> item.equals(id));
        }
    }

    public static Set<Integer> getHistoryArray() {
        return history;
    }
}
