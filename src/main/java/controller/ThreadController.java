package controller;

import game.GameCharacter;
import game.GameField;
import game.GameFieldPanel;

import static java.lang.Thread.sleep;

public class ThreadController {

    private GameFieldPanelController gameFieldPanelController;
    private GameFieldPanel gameFieldPanel;
    private GameField gameField;
    private GameCharacter character;
    boolean isRunning = false;

    public ThreadController(GameFieldPanelController gfpc) {
        gameFieldPanelController = gfpc;
        gameFieldPanel = gfpc.getGameFieldPanel();
        gameField = gfpc.getGameField();
        character = gfpc.getCharacter();
    }

    public void start() {
        Runnable runnable = this::run;
        new Thread(runnable).start();
    }

    public void stop() {
        isRunning = false;
    }

    private void run() {
        if (!isRunning) {
            isRunning = true;
        }

        while (isRunning) {
            try {
                character.moveUp();
                sleep(500);
            } catch (Throwable e) {
                System.out.println("Fehler");
                isRunning = false;
            }
        }
    }
}
