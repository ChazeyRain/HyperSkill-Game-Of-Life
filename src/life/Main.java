package life;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        try {
            //Controller.getInitialisation();

            Runnable window = new Runnable() {
                @Override
                public void run() {
                    new GameOfLife();
                }
            };

            SwingUtilities.invokeAndWait(window);

        } catch (Exception e) {
            System.out.print(e.getMessage());
            e.printStackTrace();
        }
    }
}
