import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameUI {
    private Board board;
    private JFrame frame;
    private JButton[][] buttons;
    private JLabel missCounterLabel;
    private JLabel strikeCounterLabel;
    private JLabel totalMissCounterLabel;
    private JLabel totalHitCounterLabel;
    private int missCounter;
    private int strikeCounter;
    private int totalMissCounter;
    private int totalHitCounter;

    public GameUI(Board board) {
        this.board = board;
        buttons = new JButton[10][10];
        missCounter = 0;
        strikeCounter = 0;
        totalMissCounter = 0;
        totalHitCounter = 0;
    }

    public void createAndShowGUI() {
        frame = new JFrame("Battleship Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel boardPanel = new JPanel(new GridLayout(10, 10));
        initializeBoard(boardPanel);

        JPanel statusPanel = new JPanel(new GridLayout(1, 4));
        initializeStatusPanel(statusPanel);

        JPanel controlPanel = new JPanel();
        initializeControlPanel(controlPanel);

        mainPanel.add(boardPanel, BorderLayout.CENTER);
        mainPanel.add(statusPanel, BorderLayout.NORTH);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void initializeBoard(JPanel boardPanel) {
        for (int i = 0; i < 10; i++) {
            for  (int j = 0; j < 10; j++) {
                JButton button = new JButton("~");
                button.setBackground(Color.CYAN);
                int row = i;
                int col = j;
                button.addActionListener(e -> handleButtonClick(e, row, col));
                buttons[i][j] = button;
                boardPanel.add(button);
            }
        }
    }

    private void initializeStatusPanel(JPanel statusPanel) {
        missCounterLabel = new JLabel("Miss Counter: 0");
        strikeCounterLabel = new JLabel("Strike Counter: 0");
        totalMissCounterLabel = new JLabel("Total Misses: 0");
        totalHitCounterLabel = new JLabel("Total Hits: 0");

        statusPanel.add(missCounterLabel);
        statusPanel.add(strikeCounterLabel);
        statusPanel.add(totalMissCounterLabel);
        statusPanel.add(totalHitCounterLabel);
    }

    private void initializeControlPanel(JPanel controlPanel) {
        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.addActionListener(e -> resetGame());

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> System.exit(0));

        controlPanel.add(playAgainButton);
        controlPanel.add(quitButton);
    }

    private void handleButtonClick(ActionEvent e, int row, int col) {
        JButton button = buttons[row][col];
        Cell cell = board.getCell(row, col);

        if (!button.isEnabled()) {
            return;
        }

        button.setEnabled(false);

        if (cell.hasShip()) {
            cell.hit();
            button.setText("X");
            button.setBackground(Color.RED);
            totalHitCounter++;
            missCounter = 0;
            updateCounters();

            if (cell.getShip().isSunk()) {
                JOptionPane.showMessageDialog(frame, "You sunk a ship!");
                if (checkWin()) {
                    int result = JOptionPane.showConfirmDialog(frame, "You won! Play again?", "Victory", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        resetGame();
                    } else {
                        System.exit(0);
                    }
                }
            }
        } else {
            button.setText("M");
            button.setBackground(Color.YELLOW);
            missCounter++;
            totalMissCounter++;
            updateCounters();

            if (missCounter >= 5) {
                strikeCounter++;
                missCounter = 0;
                updateCounters();

                if (strikeCounter >= 10) {
                    int result = JOptionPane.showConfirmDialog(frame, "You lost! Play again?", "Defeat", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        resetGame();
                    } else {
                        System.exit(0);
                    }
                }
            }
        }
    }

    private void updateCounters() {
        missCounterLabel.setText("Miss Counter: " + missCounter);
        strikeCounterLabel.setText("Strike Counter: " + strikeCounter);
        totalMissCounterLabel.setText("Total Misses: " + totalMissCounter);
        totalHitCounterLabel.setText("Total Hits: " + totalHitCounter);
    }

    private boolean checkWin() {
        for (Ship ship : board.getShips()) {
            if (!ship.isSunk()) {
                return false;
            }
        }
        return true;
    }

    private void resetGame() {
        frame.dispose();
        Board newBoard = new Board();
        GameUI newGameUI = new GameUI(newBoard);
        newGameUI.createAndShowGUI();
    }
}
