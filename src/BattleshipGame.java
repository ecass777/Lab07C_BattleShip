import javax.swing.*;

public class BattleshipGame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Board board = new Board();
            GameUI gameUI = new GameUI(board);
            gameUI.createAndShowGUI();
        });
    }
}
