package reengineering;

import java.awt.*;
import java.awt.geom.Path2D;

/**
 * Rozhrani reprezentujici zaklad kazde figurky.
 * V pozdejsi verzi bude nahrazeno abstraktni tridou.
 *
 * @author Dominik Nedved, A22B0109P
 * @version 26.03.2023
 */
public interface IPiece {

    /** Konstanta barvy pro bile figurky  */
    Color PIECE_WHITE = Color.WHITE;

    /** Konstanta barvy pro cerne figurky  */
    Color PIECE_BLACK = Color.BLACK;

    /**
     * Vykresleni komponenty
     *
     * @param g graficky kontext
     */
    void paint(Graphics g);

    /**
     * Vytvoreni figurky
     *
     * @return hotova cestu figurky
     */
    Path2D createPiece();

    /**
     * Vykresli figurku
     *
     * @param g2 graficky kontext
     */
    void paintPiece(Graphics2D g2);

    /**
     * Testuje, zda je zobrazeny objekt hvezdy zasazen mysi
     *
     * @param x testovana souradnice X
     * @param y testovana souradnice Y
     * @return true, pokud zasah
     */
    boolean isPieceHit(double x, double y);

    /**
     * Presune figurku na konkretni souradnice
     *
     * @param x souradnice X
     * @param y souradnice Y
     */
    void moveTo(int x, int y);

    //======================================== Gettery ========================================

    /**
     * @return aktualni radka sachovnice
     */
    int getRow();

    /**
     * @return aktualni sloupec sachovnice
     */
    int getColumn();

    /**
     * @return aktualni pole sachovnice
     */
    Field getField();

    /**
     * @return true, pokud je figurka bila, jinak false
     */
    boolean isWhite();

    /**
     * @return true, pokud figurka jiz byla vyhozena jinou figurkou, jinak false
     */
    boolean isOut();

    /**
     * @return velikost figurky
     */
    int getPieceSize();

    //======================================== Settery ========================================

    /**
     * Nastavi aktualni radka sachovnice
     *
     * @param row aktualni radka sachovnice
     */
    void setRow(int row);

    /**
     * Nastavi aktualni sloupec sachovnice
     *
     * @param column aktualni sloupec sachovnice
     */
    void setColumn(int column);

    /**
     * Nastavi aktualni pole sachovnice
     *
     * @param field aktualni sloupec sachovnice
     */
    void setField(Field field);

    /**
     * @param isOut true, pokud figurka jiz byla vyhozena jinou figurkou, jinak false
     */
    void setOut(boolean isOut);

    /**
     * Nastavi velikost figurky
     *
     * @param size velikost figurky
     */
    void setPieceSize(int size);

    /**
     * Nastavi barvu figurky
     *
     * @param pieceColor barva figurky
     */
    void setPieceColor(Color pieceColor);
}