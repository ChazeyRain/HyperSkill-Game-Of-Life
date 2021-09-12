package life;

import life.mvc.Model;

import javax.swing.*;
import java.awt.*;

public class GameOfLife extends JFrame {

    private boolean[][] cells;
    private final JLabel aliveLabel;
    private final JLabel generationLabel;
    private final JPanel petriDish;
    private Model model;
    private boolean pause = false;

    public boolean isPause() {
        return pause;
    }

    public GameOfLife() {
        super("Game of Life");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1020, 840);
        setLocationRelativeTo(null);

        petriDish = new JPanel() {
            @Override
            public void paint(Graphics g) {
                g.clearRect(0, 0, getWidth(), getHeight());
                if (cells == null) {
                    return;
                }
                for (int i = 0; i < cells.length; i++) {
                    for (int j = 0; j < cells.length; j++) {
                        if (cells[i][j]) {
                            g.setColor(Color.BLACK);
                            g.fillRect(i * 10, j * 10, 10, 10);
                        } else {
                            g.drawRect(i * 10, j * 10, 10, 10);
                        }
                    }
                }
            }
        };

        JPanel menu = new JPanel();
        menu.setPreferredSize(new Dimension(200, 100));
        menu.setLayout(new FlowLayout(FlowLayout.CENTER));

        JPanel buttons = new JPanel();
        buttons.setPreferredSize(new Dimension(200, 50));
        buttons.setLayout(new FlowLayout(FlowLayout.CENTER));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        JToggleButton startButton = new JToggleButton("->");
        JButton restartButton = new JButton("R");

        petriDish.setSize(800, 800);

        startButton.setName("PlayToggleButton");
        startButton.addActionListener( e -> {
            togglePause();
        });

        restartButton.setName("ResetButton");
        restartButton.addActionListener(e -> {
            newModel();
        });

        generationLabel = new JLabel("gen");
        generationLabel.setName("GenerationLabel");
        generationLabel.setFont(new Font("Terminal", Font.PLAIN, 15));

        aliveLabel = new JLabel("alive");
        aliveLabel.setName("AliveLabel");
        aliveLabel.setFont(new Font("Terminal", Font.PLAIN, 15));

        add(menu, BorderLayout.WEST);
        add(petriDish, BorderLayout.CENTER);

        buttons.add(startButton);
        buttons.add(restartButton);

        infoPanel.add(generationLabel);
        infoPanel.add(aliveLabel);

        menu.add(buttons, BorderLayout.NORTH);
        menu.add(infoPanel, BorderLayout.CENTER);

        buttons.add(startButton);
        buttons.add(restartButton);

        setVisible(true);

        newModel();
    }

    public void update(int generation, int alive, boolean[][] cells) {
        this.cells = cells;
        generationLabel.setText("Generation: #" + generation);
        aliveLabel.setText("Alive: " + alive);
        petriDish.repaint();
    }

    private void newModel() {
        if (model != null && model.isAlive()) {
            model.interrupt();
        }
        model = new Model(80, this);
        model.start();
    }

    private void togglePause() {
        pause = !pause;
    }

}
