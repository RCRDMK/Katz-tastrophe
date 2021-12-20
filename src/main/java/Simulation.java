import game.GameField;

public class Simulation extends Thread {//implements ObserverInterface {
    GameField gameField;
    SimulationController simulationController;
    volatile boolean pause;
    volatile boolean stop;

   /* @Override
    public void run() {
        gameField.addObserver(this);
        try {
            gameField.getCharacter();
        } catch (stopException e) {
        } finally {
            gameField.removeObserver(this);
            simulationController.simulationEnded();
        }
    }

    @Override
    public void update(Object object) {
        if (!Thread.isFXApplicationThread()) {
            return;
        }
        sleep(500);
        if (stop) {
            throw new stopexception;
        }
        while (pause) {
            wait();
            if (stop) {
                throw new stopexception;
            }
        }
    }*/
}


