/*
 * Katz-tastrophe - a miniature programming learn environment
 * Copyright (C) 2022 RCRDMK
 *
 * This program (Katz-tastrophe) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program (Katz-tastrophe) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. See LICENSE File in the main directory. If not, see <https://www.gnu.org/licenses/>.
 */

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
            observer.update(object);
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
