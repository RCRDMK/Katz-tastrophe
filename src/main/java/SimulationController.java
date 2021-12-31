/*
import game.GameField;
import javafx.application.Platform;

public class SimulationController {
    GameField gameField;
    Simulation simulation = new Simulation(gameField);
    volatile int speed;

    public SimulationController(GameField gameField) {
        this.gameField = gameField;
    }

    public void simulationEnded() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    public void start() {
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

*/
