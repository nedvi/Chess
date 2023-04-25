package The1stPhase_final;

/**
 * Trida reprezentujici jedno konkretni pole sachovnice
 *
 * @author Dominik Nedved, A22B0109P
 * @version 26.03.2023
 */
public class Field {

    /** Figurka na danem poli */
    private IPiece piece;

    /** Radka sachovnice */
    private int row;

    /** Sloupec sachovnice */
    private int column;

    /** Kontrola obsazenosti pole */
    private boolean isUsed;

    /**
     * Konstruktor pole
     *
     * @param piece figurka na danem poli
     * @param row radka sachovnice
     * @param column sloupec sachovnice
     */
    public Field(IPiece piece, int row, int column) {
        this.piece = piece;
        this.row = row;
        this.column = column;
        isUsed = piece != null;
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
    public IPiece getPiece() {
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
    public void setPiece(IPiece piece) {
        this.piece = piece;
        isUsed = piece != null;
    }

    @Override
    public String toString() {
        return String.format("The1stPhase_final.Field: row = %d; column = %d; Piece = %s", row, column, piece);
    }
}

