package model.pattern;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ObservedObject {
    private final List<ObserverInterface> registeredObservers = new CopyOnWriteArrayList<>();

    public void addObserver(ObserverInterface observer) {
        registeredObservers.add(observer);
    }

    public void removeObserver(ObserverInterface observer) {
        registeredObservers.remove(observer);
    }

    public void notifyRegisteredObservers(Object object) {
        for (ObserverInterface observer : this.registeredObservers) {
            observer.update(this);
        }
    }

    public void copy(ObservedObject observedObject) {
        for (ObserverInterface observer : observedObject.registeredObservers) {
            this.addObserver(observer);
        }
    }

    public void clear() {
        registeredObservers.clear();
    }
}
