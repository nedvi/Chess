package reengineering;

/**
 * Trida reprezentujici jedno konkretni pole sachovnice
 *
 * @author Dominik Nedved, A22B0109P
 * @version 26.03.2023
 */
public class Field {

    /** Figurka na danem poli */
    private APiece piece = null;

    /** Radka sachovnice */
    private int row;

    /** Sloupec sachovnice */
    private int column;

    /** Kontrola obsazenosti pole */
    private boolean isUsed = false;

    /**
     * Konstruktor pole
     *
     * @param row radka sachovnice
     * @param column sloupec sachovnice
     */
    public Field(int row, int column) {
        this.row = row;
        this.column = column;
    }

    //======================================== Gettery ========================================

    /**
     * @return radek pole
     */
    public int getRow() {
        return row;
    }

    /**
     * @return sloupec pole
     */
    public int getColumn() {
        return column;
    }

    /**
     * @return figurka na poli
     */
    public APiece getPiece() {
        return piece;
    }

    /**
     * @return true pokud figurka na poli je, jinak false
     */
    public boolean isUsed() {
        return isUsed;
    }

    //======================================== Settery ========================================

    /**
     * @param row radek pole
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * @param column sloupec pole
     */
    public void setColumn(int column) {
        this.column = column;
    }

    /**
     * @param piece figurka na poli
     */
    public void setPiece(APiece piece) {
        this.piece = piece;
        isUsed = piece != null;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    @Override
    public String toString() {
        return String.format("Field: row = %d; column = %d; Piece = %s", row, column, piece);
    }
}

