import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    private final int SIZE = 10;
    private Cell[][] cells;
    private List<Ship> ships;

    public Board() {
        cells = new Cell[SIZE][SIZE];
        ships = new ArrayList<>();
        initializeCells();
        placeShips();
    }

    private void initializeCells() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
    }

    private void placeShips() {
        int[] shipSizes = {5, 4, 3, 3, 2};
        Random rand = new Random();

        for (int size : shipSizes) {
            boolean placed = false;
            while (!placed) {
                boolean horizontal = rand.nextBoolean();
                int row = rand.nextInt(SIZE);
                int col = rand.nextInt(SIZE);

                if (canPlaceShip(size, row, col, horizontal)) {
                    Ship ship = new Ship(size);
                    for (int i = 0; i < size; i++) {
                        if (horizontal) {
                            cells[row][col + i].setShip(ship);
                        } else {
                            cells[row + i][col].setShip(ship);
                        }
                    }
                    ships.add(ship);
                    placed = true;
                }
            }
        }
    }

    private boolean canPlaceShip(int size, int row, int col, boolean horizontal) {
        if (horizontal) {
            if (col + size > SIZE) return false;
            for (int i = 0; i < size; i++) {
                if (cells[row][col + i].hasShip()) return false;
            }
        } else {
            if (row + size > SIZE) return false;
            for (int i = 0; i < size; i++) {
                if (cells[row + i][col].hasShip()) return false;
            }
        }
        return true;
    }

    public Cell getCell(int row, int col) {
        return cells[row][col];
    }

    public List<Ship> getShips() {
        return ships;
    }
}
