package controller;

import game.GameField;
import pattern.ObservedObject;

/**
 * This class is responsible for handling the control over threads which are running the main methods inside the
 * chosen compiled classes.
 *
 * @since 15.01.2022
 */
public class SimulationController extends ObservedObject {
    GameField gameField;
    GameFieldPanelController gameFieldPanelController;
    Simulation simulation;

    volatile int speed;

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
        notifyRegisteredObservers(this);
    }

    /**
     * When this method is being called, it initiates the simulator variable and calls its run method afterwards.
     *
     * @since 15.01.2022
     */
    public void start() {
        simulation = new Simulation(gameFieldPanelController, this);
        simulation.setDaemon(true);
        simulation.run();
    }

    /**
     * When this method is being called, it resumes the former paused thread and informs the observers about it.
     *
     * @since 15.01.2022
     */
    public void resume() {
        simulation.pause = false;
        simulation.notify();
    }

    /**
     * When this method is being called, it pauses the running thread.
     *
     * @since 15.01.2022
     */
    public void pause() {
        simulation.pause = true;
    }

    /**
     * When this method is being called, it terminates the thread and informs the observers about it.
     *
     * @since 15.01.2022
     */
    public void stop() {
        simulation.stop = true;
        simulation.pause = false;
        simulation.notify();
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}


