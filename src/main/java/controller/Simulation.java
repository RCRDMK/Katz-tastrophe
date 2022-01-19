package controller;

import game.GameField;
import game.exceptions.StoppedException;
import javafx.application.Platform;
import pattern.ObserverInterface;

public class Simulation extends Thread implements ObserverInterface {
    GameField gameField;
    GameFieldPanelController gameFieldPanelController;
    SimulationController simulationController;
    volatile boolean pause;
    volatile boolean stop;

    public Simulation(GameFieldPanelController gameFieldPanelController, SimulationController simulationController) {
        this.gameFieldPanelController = gameFieldPanelController;
        gameField = gameFieldPanelController.getGameField();
        this.simulationController = simulationController;
    }

    @Override
    public void run() {
        gameField.addObserver(this);
        try {
            gameFieldPanelController.getCharacter().main();
            FileController fileController = new FileController();

        } catch (StoppedException e) {
            e.printStackTrace();
        } finally {
            gameField.removeObserver(this);
            simulationController.simulationEnded();
        }
    }

    @Override
    public void update(Object object) {
        if (!Platform.isFxApplicationThread()) {
            return;
        }
        try {
            sleep(500);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        ;
        if (stop) {
            throw new StoppedException();
        }
        while (pause) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (stop) {
                throw new StoppedException();
            }
        }
    }
}



