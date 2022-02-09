package model.messages;

import pattern.ObservedObject;
import viewController.MainViewController;

public class NewFileHasBeenCreatedMessage extends ObservedObject {

    String newFileName;

    public NewFileHasBeenCreatedMessage(String fileName) {
        MainViewController mvc = new MainViewController();
        addObserver(mvc);
        newFileName = fileName;
        notifyRegisteredObservers(this);
    }

    public String getNewFileName() {
        return newFileName;
    }
}
