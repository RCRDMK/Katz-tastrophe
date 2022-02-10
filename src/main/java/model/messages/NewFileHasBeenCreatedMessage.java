package model.messages;

import model.pattern.ObservedObject;
import viewController.MainViewController;

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
