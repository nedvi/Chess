import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

/**
 * Trida reprezentujici figurku krale
 *
 * @author Dominik Nedved, A22B0109P
 * @version 26.03.2023
 */
public class King extends JPanel implements IPiece {

    /** Polovina velikosti figurky */
    private double halfSize;

    /** Vykreslovaci cesta figurky */
    private Path2D king;

    /** Stredova souradnice X figurky */
    private int sX;

    /** Stredova souradnice Y figurky */
    private int sY;

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

    /** Test na to, zda je figurka stale ve hre */
    private boolean isOut;

    //======================================== Konstruktory ========================================

    /**
     * Konstruktor
     *
     * @param sX souradnice stredove souradnice X
     * @param sY souradnice stredove souradnice Y
     * @param isWhite test na barvu figurky (bila/cerna)
     */
    public King(int sX, int sY, boolean isWhite) {
        this.sX = sX;
        this.sY = sY;
        this.isWhite = isWhite;
        this.isOut = false;
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
    @Override
    public Path2D createPiece() {
        king = new Path2D.Double();
        king.moveTo(-halfSize, halfSize);	// levy dolni roh
        king.lineTo(halfSize, halfSize);	// pravy dolni roh
        king.lineTo(halfSize, halfSize - halfSize/3.0);	// pravy vrchol podstavy pesaka
        king.lineTo(halfSize/3.0, halfSize - halfSize/2.0); // zlom tela

        king.lineTo(halfSize/4.0, -halfSize/10.0); // pravy horni roh tela
        king.lineTo(halfSize/2.0, -halfSize/4.0);  // pravy horni roh hlavy

        king.lineTo(halfSize/8.0, -halfSize/3.0); 		// prava spicka hlavy

        king.lineTo(halfSize/8.0, -halfSize/1.8); // prava strana krize
        king.lineTo(halfSize/3.0, -halfSize/1.8); // prava strana krize
        king.lineTo(halfSize/3.0, -halfSize/1.25); // prava strana krize
        king.lineTo(halfSize/8.0, -halfSize/1.25); // prava strana krize

        king.lineTo(halfSize/8.0, -halfSize); 		// prava spicka hlavy
        //king.lineTo(0, -halfSize); 		// spicka hlavy
        king.lineTo(-halfSize/8.0, -halfSize); 		// leva spicka hlavy

        king.lineTo(-halfSize/8.0, -halfSize/1.25); // leva strana krize
        king.lineTo(-halfSize/3.0, -halfSize/1.25); // leva strana krize
        king.lineTo(-halfSize/3.0, -halfSize/1.8); // leva strana krize
        king.lineTo(-halfSize/8.0, -halfSize/1.8); // leva strana krize

        king.lineTo(-halfSize/8.0, -halfSize/3.0); 		// leva spicka hlavy

        king.lineTo(-halfSize/2.0, -halfSize/4.0); // levy horni roh hlavy
        king.lineTo(-halfSize/4.0, -halfSize/10.0); // levy horni roh tela
        //queen.lineTo(-halfSize/5.0, 0); // 2. zlom tela
        king.lineTo(-halfSize/3.0, halfSize - halfSize/2.0);// zlom tela
        king.lineTo(-halfSize, halfSize - halfSize/3.0);	// levy vrchol podstavy pesaka
        king.lineTo(-halfSize, halfSize);

        king.closePath();

        return king;
    }

    /**
     * Vykresli figurku
     *
     * @param g2 graficky kontext
     */
    @Override
    public void paintPiece(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(pieceColor);
        if (this.king == null){
            this.king = createPiece();
        }
        g2.fill(king);

        if (this.isWhite()) {
            g2.setColor(PIECE_BLACK);
        } else {
            g2.setColor(PIECE_WHITE);
        }
        g2.draw(king);
    }

    /**
     * Testuje, zda je zobrazeny objekt hvezdy zasazen mysi
     *
     * @param x testovana souradnice X
     * @param y testovana souradnice Y
     * @return true, pokud zasah
     */
    @Override
    public boolean isPieceHit(double x, double y) {
        return (this.king != null && this.king.contains(x - this.sX, y - this.sY));
    }

    /**
     * Presune figurku na konkretni souradnice
     *
     * @param x souradnice X
     * @param y souradnice Y
     */
    public void moveTo(int x, int y) {
        this.sX = x;
        this.sY = y;
    }

    //======================================== Gettery ========================================

    /**
     * @return aktualni radka sachovnice
     */
    @Override
    public int getRow() {
        return row;
    }

    /**
     * @return aktualni sloupec sachovnice
     */
    @Override
    public int getColumn() {
        return column;
    }

    /**
     * @return aktualni pole sachovnice
     */
    @Override
    public Field getField() {
        return field;
    }

    /**
     * @return true, pokud je figurka bila, jinak false
     */
    @Override
    public boolean isWhite() {
        return isWhite;
    }

    /**
     * @return true, pokud figurka jiz byla vyhozena jinou figurkou, jinak false
     */
    @Override
    public boolean isOut() {
        return isOut;
    }

    /**
     * @return velikost figurky
     */
    @Override
    public int getPieceSize() {
        return pieceSize;
    }

    //======================================== Settery ========================================

    /**
     * Nastavi aktualni radka sachovnice
     *
     * @param row aktualni radka sachovnice
     */
    @Override
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Nastavi aktualni sloupec sachovnice
     *
     * @param column aktualni sloupec sachovnice
     */
    @Override
    public void setColumn(int column) {
        this.column = column;
    }

    /**
     * Nastavi aktualni pole sachovnice
     *
     * @param field aktualni sloupec sachovnice
     */
    @Override
    public void setField(Field field) {
        this.field = field;
    }

    /**
     * @param isOut true, pokud figurka jiz byla vyhozena jinou figurkou, jinak false
     */
    @Override
    public void setOut(boolean isOut) {
        this.isOut = isOut;
    }

    /**
     * Nastavi velikost figurky
     *
     * @param size velikost figurky
     */
    @Override
    public void setPieceSize(int size) {
        this.pieceSize = size;
        this.king = createPiece();
    }

    /**
     * Nastavi barvu figurky
     *
     * @param pieceColor barva figurky
     */
    @Override
    public void setPieceColor(Color pieceColor) {
        this.pieceColor = pieceColor;
    }
}