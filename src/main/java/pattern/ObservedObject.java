package pattern;

import java.util.ArrayList;
import java.util.List;

public class ObservedObject {
    private final List<ObserverInterface> registeredObservers = new ArrayList<>();

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
}
