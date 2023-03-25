package CleanBoard;

/**
 * Trida reprezentujici jedno konkretni pole sachovnice
 */
public class Field {

    /** souradnice X pole */
    private int x;

    /** souradnice Y pole */
    private int y;

    private int row;

    private int column;

    /** Kontrola obsazenosti pole */
    private boolean isUsed;

    /**
     * Konstruktor pole
     *
     * @param x souradnice X pole
     * @param y souradnice Y pole
     */
    public Field(int x, int y, int row, int column) {
        this.x = x;
        this.y = y;
        this.row = row;
        this.column = column;
        isUsed = false;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    @Override
    public String toString() {
        return String.format("Field: x = %d; y = %d; row = %d; column = %d;", x, y, row, column, isUsed);
    }
}

