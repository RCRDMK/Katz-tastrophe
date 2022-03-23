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

package model.messages;

import model.pattern.ObservedObject;
import view.viewController.MainViewController;

/**
 * This message informs the MainViewController whenever a new file has been created by the user.
 *
 * @since 09.02.2022
 */

public class NewFileHasBeenCreatedMessage extends ObservedObject {

    String newFileName;

    /**
     * The custom constructor of the class. It adds the given MainViewController as an observer, initializes the
     * newFileName String with the given String and then sends this messages to the registered observers.
     *
     * @param fileName           Name of the newly created file
     * @param mainViewController Instance of the mainViewController in which a new file has been created
     * @since 09.02.2022
     */
    public NewFileHasBeenCreatedMessage(String fileName, MainViewController mainViewController) {
        addObserver(mainViewController);
        newFileName = fileName;
        notifyRegisteredObservers(this);
    }

    public String getNewFileName() {
        return newFileName;
    }
}
