package controller;

import javafx.application.Platform;
import model.GameField;
import model.exceptions.StoppedException;
import model.pattern.ObserverInterface;

/**
 * This class is responsible running the main method inside the compiled classes as a thread.
 *
 * @since 15.01.2022
 */

public class Simulation extends Thread implements ObserverInterface {
    GameField gameField;
    GameFieldPanelController gameFieldPanelController;
    SimulationController simulationController;
    volatile boolean pause;
    volatile boolean stop;

    /**
     * The custom constructor of the class.
     * <p>
     * It initiates the variables with the values given by the parameter.
     *
     * @param gameFieldPanelController Controller class of the gamefield panel. From here are all classes necessary for
     *                                 depiction on the panel being controlled.
     * @param simulationController     Controller class for the simulation. Responsible for handling every request for
     *                                 the threads.
     * @since 15.01.2022
     */
    public Simulation(GameFieldPanelController gameFieldPanelController, SimulationController simulationController) {
        this.gameFieldPanelController = gameFieldPanelController;
        gameField = gameFieldPanelController.getGameField();
        this.simulationController = simulationController;
    }

    /**
     * Responsible for actually running the main method inside the currently chosen compiled class.
     *
     * @since 15.01.2022
     */
    @Override
    public void run() {
        gameField.addObserver(this);
        try {
            gameFieldPanelController.getCharacter().main();
        } catch (StoppedException e) {
            e.printStackTrace();
        } finally {
            gameField.removeObserver(this);
            simulationController.simulationEnded();
        }
    }

    @Override
    public void update(Object object) {
        try {
            if (Platform.isFxApplicationThread()) {
                return;
            }
            sleep(250);
            if (stop) {
                throw new StoppedException();
            }
            while (pause) {
                synchronized (this) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (stop) {
                    throw new StoppedException();
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }

    }
}



