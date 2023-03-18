import javafx.scene.shape.Rectangle;

/**
 * Trida reprezentujici jedno konkretni pole sachovnice
 */
public class Field {

    /** souradnice X pole */
    private int x;

    /** souradnice Y pole */
    private int y;

    /** Kontrola obsazenosti pole */
    private boolean isUsed;

    /**
     * Konstruktor pole
     *
     * @param x souradnice X pole
     * @param y souradnice Y pole
     */
    public Field(int x, int y) {
        this.x = x;
        this.y = y;
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

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }
}

