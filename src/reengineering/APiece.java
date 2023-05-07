package reengineering;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

public abstract class APiece extends JPanel {

    /** Konstanta barvy pro bile figurky  */
    protected final static Color PIECE_WHITE = Color.WHITE;

    /** Konstanta barvy pro cerne figurky  */
    protected final static Color PIECE_BLACK = Color.BLACK;

    /** Polovina velikosti figurky */
    protected double halfSize;

    /** Vykreslovaci cesta figurky */
    protected Path2D piece;

    /** Stredova souradnice X figurky */
    private double sX;

    /** Stredova souradnice Y figurky */
    private double sY;

    /** Barva figurky */
    private Color pieceColor;

    /** Veliost figurky */
    private int pieceSize;

    /** Cislo radky figurky */
    private int row;

    /** Cislo sloupce figurky */
    private int column;

    /** Test na barvu figurky (bila/cerna) */
    private final boolean isWhite;

    /** Pole figurky */
    private Field field;

    private boolean enPassant = false;
    private boolean wasEnPassantAlready = false;

    //======================================== Konstruktory ========================================

    /**
     * Konstruktor
     *
     * @param sX souradnice stredove souradnice X
     * @param sY souradnice stredove souradnice Y
     * @param isWhite test na barvu figurky (bila/cerna)
     */
    public APiece(double sX, double sY, boolean isWhite) {
        this.sX = sX;
        this.sY = sY;
        this.isWhite = isWhite;
        if (isWhite) {
            setPieceColor(PIECE_WHITE);
        } else {
            setPieceColor(PIECE_BLACK);
        }
    }

    //======================================== Funkce ========================================

    /**
     * Vykresleni komponenty
     *
     * @param g graficky kontext
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        halfSize = this.getPieceSize()/3.0;
        Graphics2D g2 = (Graphics2D)g;
        g2.translate(sX, sY);
        paintPiece(g2);
    }

    /**
     * Vytvoreni figurky
     *
     * @return hotova cestu figurky
     */
    public abstract Path2D createPiece();

    /**
     * Vykresli figurku
     *
     * @param g2 graficky kontext
     */
    public void paintPiece(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(pieceColor);
        if (this.piece == null){
            this.piece = createPiece();
        }
        g2.fill(piece);

        if (this.isWhite()) {
            g2.setColor(PIECE_BLACK);
        } else {
            g2.setColor(PIECE_WHITE);
        }
        g2.draw(piece);
    }

    /**
     * Testuje, zda je zobrazena figurka zasazena mysi
     *
     * @param x testovana souradnice X
     * @param y testovana souradnice Y
     * @return true, pokud zasah
     */
    public boolean isPieceHit(double x, double y) {
        return (this.piece.contains(x - this.sX, y - this.sY));
    }

    /**
     * Presune figurku na konkretni souradnice
     *
     * @param x souradnice X
     * @param y souradnice Y
     */
    public void moveTo(double x, double y) {
        this.sX = x;
        this.sY = y;
    }

    public double distanceTo(double x, double y) {
        double dx = x - this.sX;
        double dy = y - this.sY;
        return Math.sqrt(dx * dx + dy * dy);
    }

    //======================================== Gettery ========================================

    /**
     * @return aktualni radka sachovnice
     */
    public int getRow() {
        return row;
    }

    /**
     * @return aktualni sloupec sachovnice
     */
    public int getColumn() {
        return column;
    }

    /**
     * @return aktualni pole sachovnice
     */
    public Field getField() {
        return field;
    }

    /**
     * @return true, pokud je figurka bila, jinak false
     */
    public boolean isWhite() {
        return isWhite;
    }

    /**
     * @return velikost figurky
     */
    public int getPieceSize() {
        return pieceSize;
    }

    /**
     * Getter pro stredovou souradnici X
     * @return stredova souradnice X
     */
    public double getsX() {
        return sX;
    }

    /**
     * Getter pro stredovou souradnici Y
     * @return stredova souradnice Y
     */
    public double getsY() {
        return sY;
    }

    /**
     * Kontrola, zda jde pesak vzit mimochodem
     *
     * @return true pokud jde vzit mimochodem (ihned po dvojtahu)
     */
    public boolean isEnPassant() {
        return enPassant;
    }

    public boolean wasEnPassantAlready() {
        return wasEnPassantAlready;
    }

    //======================================== Settery ========================================

    /**
     * Nastavi aktualni radka sachovnice
     *
     * @param row aktualni radka sachovnice
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Nastavi aktualni sloupec sachovnice
     *
     * @param column aktualni sloupec sachovnice
     */
    public void setColumn(int column) {
        this.column = column;
    }

    /**
     * Nastavi aktualni pole sachovnice
     *
     * @param field aktualni sloupec sachovnice
     */
    public void setField(Field field) {
        this.field = field;
    }

    /**
     * Nastavi velikost figurky
     *
     * @param size velikost figurky
     */
    public void setPieceSize(int size) {
        this.pieceSize = size;
        this.piece = createPiece();
    }

    /**
     * Nastavi barvu figurky
     *
     * @param pieceColor barva figurky
     */
    public void setPieceColor(Color pieceColor) {
        this.pieceColor = pieceColor;
    }

    public void setEnPassant(boolean enPassant) {
        this.enPassant = enPassant;
    }

    public void setWasEnPassantAlready(boolean wasEnPassantAlready) {
        this.wasEnPassantAlready = wasEnPassantAlready;
    }

    @Override
    public String toString() {
        return String.format("%s; %s", getClass().getSimpleName(), getField());
    }
}
