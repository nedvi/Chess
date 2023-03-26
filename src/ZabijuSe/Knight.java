package ZabijuSe;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

public class Knight extends JPanel implements IPiece {
    private double halfSize;
    private Path2D knight;	// kun k vykresleni
    private int sX;
    private int sY;
    public static final Color PIECE_WHITE = Color.WHITE;
    public static final Color PIECE_BLACK = Color.BLACK;
    private Color pieceColor;
    private int rectSize;
    private int row;
    private int column;
    private boolean isWhite;
    public Field field;

    boolean isOut;
    @Override
    public boolean isOut() {
        return isOut;
    }
    @Override
    public void setOut(boolean isOut) {
        this.isOut = isOut;
    }
    //======================================== Konstruktory ========================================
    public Knight(int sX, int sY, boolean isWhite) {
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
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        halfSize = getRectSize()/3.0;
        Graphics2D g2 = (Graphics2D)g;
        g2.translate(sX, sY);
        paintPiece(g2);
    }

    /**
     * Vytvori v pameti pravidelnou hvezdu o polomeru R s N cipy
     * Stred hvezdy je v pocatku souradneho systemu
     * @return vytvorenou hvezdu
     */
    @Override
    public Path2D createPiece() {
        knight = new Path2D.Double();
        knight.moveTo(-halfSize, halfSize);	// levy dolni roh
        knight.lineTo(halfSize, halfSize);	// pravy dolni roh
        knight.lineTo(halfSize, halfSize - halfSize/3.0);	// pravy vrchol podstavy
        knight.lineTo(halfSize/2.0, halfSize - halfSize/1.5); // "zlom" na zadech
        knight.lineTo(halfSize, -halfSize/2.0); // pravy roh vrcholu "tela"
        knight.lineTo(halfSize/2.0, -halfSize);
        knight.lineTo(halfSize/2.0, -halfSize/1.3);
        knight.lineTo(halfSize/6.0, -halfSize/1.2);

        knight.lineTo(-halfSize/1.3, -halfSize/2.0); // horni spicka nosu kone
        knight.lineTo(-halfSize/1.5, -halfSize/8.0); // dolni spicka nosu kone
        knight.lineTo(-halfSize/5.0, -halfSize/6.0); // krk kone zleva

        knight.lineTo(-halfSize/3.0, halfSize - halfSize/1.5);// "zlom" na hrudi

        knight.lineTo(-halfSize, halfSize - halfSize/3.0);	// levy vrchol podstavy
        knight.lineTo(-halfSize, halfSize);

        knight.closePath();

        return knight;
    }

    @Override
    public void paintPiece(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(pieceColor);
        if (this.knight == null){
            this.knight = createPiece();
        }
        g2.fill(knight);

        if (this.isWhite()) {
            g2.setColor(PIECE_BLACK);
        } else {
            g2.setColor(PIECE_WHITE);
        }
        g2.draw(knight);
    }

    /**
     * Otestuje, zda je zobrazeny objekt hvezdy
     * zasazen mysi/dotykem v miste [x,y]
     * @param x
     * @param y
     * @return true, pokud zasah
     */
    @Override
    public boolean isPieceHit(double x, double y) {
        //TODO: test, zda hvezda zasazena
        return (this.knight != null &&
                this.knight.contains(
                        x - this.sX, y - this.sY));
    }

    public void moveTo(int x, int y) {
        setsX(x);
        setsY(y);
    }

    //======================================== Gettery ========================================
    public int getRectSize() {
        return rectSize;
    }
    @Override
    public int getRow() {
        return row;
    }
    @Override
    public int getColumn() {
        return column;
    }
    public int getsX() {
        return sX;
    }
    public int getsY() {
        return sY;
    }
    public Field getField() {
        return field;
    }

    public boolean isWhite() {
        return isWhite;
    }

    //======================================== Settery ========================================
    /**
     * Setter pro barvu hvezdy
     * @param pieceColor pozadovana barva hvezdy
     */
    @Override
    public void setPieceColor(Color pieceColor) {
        this.pieceColor = pieceColor;
    }
    @Override
    public void setRectSize(int rectSize) {
        this.rectSize = rectSize;
        this.knight = createPiece();
        this.repaint();
    }
    public void setsX(int sX) {
        this.sX = sX;
    }
    public void setsY(int sY) {
        this.sY = sY;
    }
    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setField(Field field) {
        this.field = field;
    }
}