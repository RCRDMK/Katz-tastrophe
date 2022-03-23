package controller;

import javafx.application.Platform;
import model.GameField;
import model.messages.SimulationHasBeenPausedMessage;
import model.messages.SimulationHasEndedMessage;
import model.messages.SimulationHasResumedMessage;
import model.messages.SimulationHasStartedMessage;
import model.pattern.ObservedObject;
import view.GameFieldPanelController;
import view.viewController.MainViewController;

/**
 * This class is responsible for handling the control over threads which are running the main methods inside the
 * chosen compiled classes.
 *
 * @since 15.01.2022
 */
public class SimulationController extends ObservedObject {
    private GameField gameField;
    private GameFieldPanelController gameFieldPanelController;
    private Simulation simulation;
    private MainViewController mainViewController;

    /**
     * The custom constructor of the class. It initiates the variables with the value from the parameter.
     *
     * @param gameFieldPanelController Controller class of the gamefield panel. From here are all classes necessary for
     *                                 depiction on the panel being controlled.
     * @since 15.01.2022
     */
    public SimulationController(GameFieldPanelController gameFieldPanelController) {
        this.gameField = gameFieldPanelController.getGameField();
        this.gameFieldPanelController = gameFieldPanelController;
    }

    /**
     * When this method is called, it informs the observers that the thread has been, through what ever reason, terminated.
     *
     * @since 15.01.2022
     */
    public void simulationEnded() {
        Platform.runLater(() -> notifyRegisteredObservers(new SimulationHasEndedMessage()));
    }

    /**
     * When this method is being called, it initiates the simulator variable and calls its run method afterwards.
     *
     * @since 15.01.2022
     */
    public void start(MainViewController mainViewController) {
        if (simulation != null && simulation.isPause()) {
            resume();
        } else {
            this.mainViewController = mainViewController;
            addObserver(mainViewController);
            simulation = new Simulation(gameFieldPanelController, this);
            simulation.setDaemon(true);
            simulation.start();
            notifyRegisteredObservers(new SimulationHasStartedMessage());
        }
    }

    /**
     * When this method is being called, it resumes the former paused thread and informs the observers about it.
     *
     * @since 15.01.2022
     */
    public void resume() {
        simulation.pause = false;
        synchronized (simulation) {
            simulation.notify();
        }
        notifyRegisteredObservers(new SimulationHasResumedMessage());
    }

    /**
     * When this method is being called, it pauses the running thread.
     *
     * @since 15.01.2022
     */
    public void pause() {
        simulation.pause = true;
        notifyRegisteredObservers(new SimulationHasBeenPausedMessage());
    }

    /**
     * When this method is being called, it terminates the thread and informs the observers about it.
     *
     * @since 15.01.2022
     */
    public void stop() {
        simulation.stop = true;
        simulation.pause = false;
        synchronized (simulation) {
            simulation.notify();
        }
        notifyRegisteredObservers(new SimulationHasEndedMessage());
    }
}


