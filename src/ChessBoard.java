

import CleanBoard.Pawn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class ChessBoard extends JPanel {

    private int rectSize;

    public int getRectSize() {
        return rectSize;
    }

    public void setRectSize(int rectSize) {
        this.rectSize = rectSize;
    }

    /** Barva "bilych" poli */
    private final Color WHITE = Color.LIGHT_GRAY;

    /** Barva "cernych" poli */
    private final Color BLACK = Color.DARK_GRAY;

    /** Barva fontu */
    private final Color FONT_COLOR = Color.BLACK;

    /** Velikost fontu */
    private final int FONT_SIZE = 20;

    /** Pomocna hodnota k zajisteni stridani barev v hracim poli */
    private boolean isWhite = true;

    Field[][] board = new Field[8][8];
    private Rectangle[][] rectBoard = new Rectangle[10][10];

    /** Pole "indexu" sloupcu */
    String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h"};

    public Pawn pawn = new Pawn(1, 2, false);

    /**
     * Konstruktor
     */
    public ChessBoard() {
        //this.setPreferredSize(new Dimension(800, 600));
        this.setMinimumSize(new Dimension(800, 600));

    }

    /**
     * Vykresleni objektu
     *
     * @param g the <code>Graphics</code> context in which to paint
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        paintChessBoard(g);
        pawn.paint(g);
    }

    /**
     * Vykresleni sachovnice
     *
     * @param g
     */
    public void paintChessBoard(Graphics g) {

        //rectSize = Math.min(getWidth(), getHeight()) / 12; // 1/12th of smallest dimension of window
        setRectSize(Math.min(getWidth(), getHeight()) / 12);
        pawn.setRectSize(getRectSize());
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        int x = rectSize;
        int y = this.getHeight()/2 - (10* rectSize)/2; //

        int posun = rectSize;

        String actualLetter;

        int row0Number = 8;
        int row9Number = 8;

        for (int row = 0; row < 10; row++) {
            x = this.getWidth()/2 - (10* rectSize)/2 + rectSize;//2 * rectSize;
            if (row == 0) {     // horni rada pismen
                for (int column = 1; column < 9; column++) {
                    g2.setColor(FONT_COLOR);
                    Rectangle actualRectangle = new Rectangle(x, y, rectSize, rectSize);
                    rectBoard[0][column] = actualRectangle;
                    g2.draw(actualRectangle);

                    g.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
                    actualLetter = letters[column - 1];
                    int letterWidth = g.getFontMetrics().stringWidth(actualLetter);
                    g2.drawString(actualLetter, (x + rectSize / 2 - letterWidth / 2), (y + rectSize / 2 + FONT_SIZE / 4));

                    x += posun;
                }
            } else if (row == 9) {     // horni rada pismen

                y += rectSize;
                g2.setColor(FONT_COLOR);
                for (int column = 1; column < 9; column++) {
                    Rectangle actualRectangle = new Rectangle(x, y, rectSize, rectSize);
                    rectBoard[0][column] = actualRectangle;
                    g2.draw(actualRectangle);

                    g2.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
                    actualLetter = letters[column - 1];
                    int letterWidth = g.getFontMetrics().stringWidth(actualLetter);
                    g2.drawString(actualLetter, (x + rectSize / 2 - letterWidth / 2), (y + rectSize / 2 + FONT_SIZE / 4));

                    x += posun;
                }
            } else {
                x = this.getWidth()/2 - (10* rectSize)/2;
                y += rectSize;
                for (int column = 0; column < 10; column++) {
                    if(column==0) {                             // cisla 0. sloupce
                        g2.setColor(FONT_COLOR);
                        Rectangle actualRectangle = new Rectangle(x, y, rectSize, rectSize);
                        rectBoard[row][0] = actualRectangle;
                        g2.draw(actualRectangle);

                        g2.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
                        actualLetter = String.valueOf(row0Number);
                        int letterWidth = g.getFontMetrics().stringWidth(actualLetter);
                        g2.drawString(actualLetter, (x + rectSize / 2 - letterWidth / 2), (y + rectSize / 2 + FONT_SIZE / 4));

                        x += posun;
                        row0Number--;
                    } else if (column==9) {                     // cisla 9. sloupce
                        g2.setColor(FONT_COLOR);
                        Rectangle actualRectangle = new Rectangle(x, y, rectSize, rectSize);
                        rectBoard[row][0] = actualRectangle;
                        g2.draw(actualRectangle);

                        g2.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
                        actualLetter = String.valueOf(row9Number);
                        int letterWidth = g.getFontMetrics().stringWidth(actualLetter);
                        g2.drawString(actualLetter, (x + rectSize / 2 - letterWidth / 2), (y + rectSize / 2 + FONT_SIZE / 4));

                        x += posun;
                        row9Number--;

                    } else {                                    // hraci pole
                        Rectangle actualRectangle = new Rectangle(x, y, rectSize, rectSize);
                        rectBoard[row][column] = actualRectangle;

                        if (isWhite) {
                            g2.setColor(WHITE);
                            isWhite = false;
                        } else {
                            g2.setColor(BLACK);
                            isWhite = true;
                        }
                        g2.fill(actualRectangle);
                        x += posun;
                    }
                }
                if (isWhite) {
                    isWhite = false;
                } else {
                    isWhite = true;
                }
            }
        }

    }
    public void mouseDragged(MouseEvent e) {
       // e.getX()
    }

    public void mouseMoved(MouseEvent e, int x, int y) {
        if (this.pawn.isStarHit(e.getX(), e.getY())) {
            this.pawn.setStarColor(Color.BLACK);
            this.pawn.moveTo(x, y);
            this.pawn.repaint();
        } else {
            this.pawn.setStarColor(Color.ORANGE);
            this.pawn.repaint();
        }
    }

    public Rectangle[][] getRectBoard() {
        return rectBoard;
    }

    public void setRectBoard(Rectangle[][] rectBoard) {
        this.rectBoard = rectBoard;
    }
}
