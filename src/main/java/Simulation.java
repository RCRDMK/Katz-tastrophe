/*
import game.GameField;
import javafx.application.Platform;
import pattern.ObserverInterface;

public class Simulation extends Thread implements ObserverInterface {
    GameField gameField;
    SimulationController simulationController;
    volatile boolean pause;
    volatile boolean stop;

    public Simulation(GameField gameField) {
        this.gameField = gameField;
    }

    @Override
    public void run() {
        gameField.addObserver(this);
        try {
            gameField.getCharacter();
        } */
/*catch (InterruptedException e) {
            e.printStackTrace();
        }*//*
 finally {
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
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ;
        if (stop) {
            throw new stopexception;
        }
        while (pause) {
            wait();
            if (stop) {
                throw new stopexception;
            }
        }
    }
}


*/
