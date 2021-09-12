package life.mvc;

import life.GameOfLife;

import java.util.Random;

public class Model extends Thread {

    private boolean[][] petriDish;
    private int size;
    private GameOfLife window;
    private int genN = 0;
    private int alive = 0;
    private boolean paused = false;

    private Random randomizer;

    public void togglePause() {
        paused = !paused;
    }

    private Model (int size, int seed) {
        randomizer = new Random(seed);
        petriDish = new boolean[size][size];
        this.size = size;
    }

    public Model (int size, GameOfLife window) {
        randomizer = new Random();
        petriDish = new boolean[size][size];
        this.size = size;
        this.window = window;
    }

    private void generateDish() {
        alive = 0;
        genN = 0;
        boolean isCell;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                isCell = randomizer.nextBoolean();
                petriDish[i][j] = isCell;
                alive += isCell ? 1 : 0;
            }
        }
    }

    private void nextGeneration() {
        boolean[][] newPetriDish = new boolean[size][size];
        genN++;
        alive = 0;
        boolean isCell;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                isCell = checkCell(i, j);
                newPetriDish[i][j] = isCell;
                alive += isCell ? 1 : 0;
            }
        }
        petriDish = newPetriDish;
    }

    private boolean checkCell(int x, int y) {
        int count = 0;
        int newX;
        int newY;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }

                newX = x + i == -1 ? size - 1
                        : x + i == size ? 0 : x + i;

                newY = y + j == -1 ? size - 1
                        : y + j == size ? 0 : y + j;

                count += petriDish[newX][newY] ? 1 : 0;
            }
        }
        return (petriDish[x][y] && count == 2) || count == 3;
    }

    private void jumpToGeneration(int generationNumber) {
        for (int i = 0; i < generationNumber; i++) {
            nextGeneration();
        }
    }

    private void sendDishToView() {
        window.update(genN, alive, petriDish);
    }

    @Override
    public void run() {
        generateDish();
        sendDishToView();
        while (true) {
            if (window.isPause()) {
                nextGeneration();
                sendDishToView();
            }
            try {
               Thread.sleep(30l);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
