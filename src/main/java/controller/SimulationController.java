package controller;

import game.GameField;
import pattern.ObservedObject;

public class SimulationController extends ObservedObject {
    GameField gameField;
    GameFieldPanelController gameFieldPanelController;
    Simulation simulation;

    volatile int speed;

    public SimulationController(GameFieldPanelController gameFieldPanelController) {
        this.gameField = gameFieldPanelController.getGameField();
        this.gameFieldPanelController = gameFieldPanelController;
    }

    public void simulationEnded() {
        notifyRegisteredObservers(this);
    }

    public void start() {
        simulation = new Simulation(gameFieldPanelController, this);
        simulation.setDaemon(true);
        simulation.start();
    }

    public void resume() {
        simulation.pause = false;
        simulation.notify();
    }

    public void pause() {
        simulation.pause = true;
    }

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


