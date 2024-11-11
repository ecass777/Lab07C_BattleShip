public class Cell {
    private int row;
    private int col;
    private Ship ship;
    private boolean isHit;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.ship = null;
        this.isHit = false;
    }

    public boolean hasShip() {
        return ship != null;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public Ship getShip() {
        return ship;
    }

    public boolean isHit() {
        return isHit;
    }

    public void hit() {
        isHit = true;
        if (ship != null) {
            ship.hit();
        }
    }
}
