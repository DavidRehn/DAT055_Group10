package src;

import java.util.ArrayList;
import java.util.List;

public class Observable {
    private final List<Observer> observers = new ArrayList<>();
    private boolean changed = false;

    public synchronized void addObserver(Observer observer) {
        if (observer == null) {
            throw new NullPointerException("observer is null");
        }
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public synchronized void setChanged() {
        changed = true;
    }

    public void notifyObservers(clientModel model) {
        Observer[] observerSnapshot;
        synchronized (this) {
            if (!changed) {
                return;
            }
            observerSnapshot = observers.toArray(new Observer[0]);
            changed = false;
        }

        for (Observer observer : observerSnapshot) {
            observer.update(model);
        }
    }
}
