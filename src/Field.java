/**
 * Trida reprezentujici jedno konkretni pole sachovnice
 *
 * @author Dominik Nedved, A22B0109P
 * @version 07.05.2023
 */
public class Field {

    /** Figurka na danem poli */
    private APiece piece = null;

    /** Radka sachovnice */
    private final int row;

    /** Sloupec sachovnice */
    private final int column;

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
     * @param piece figurka na poli
     */
    public void setPiece(APiece piece) {
        this.piece = piece;
        isUsed = piece != null;
    }

    /**
     * Modifikovany toString
     *
     * @return toString
     */
    @Override
    public String toString() {
        return String.format("Field: row = %d; column = %d; Piece = %s", row, column, piece);
    }
}

