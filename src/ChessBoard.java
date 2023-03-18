

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ChessBoard extends JPanel implements MouseListener {

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
    Rectangle[][] rectBoard = new Rectangle[10][10];

    /** Pole "indexu" sloupcu */
    String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h"};

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
    }

    /**
     * Vykresleni sachovnice
     *
     * @param g
     */
    public void paintChessBoard(Graphics g) {

        int RECT_SIZE = Math.min(getWidth(), getHeight()) / 12; // 1/12th of smallest dimension of window

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        int x = RECT_SIZE;
        int y = this.getHeight()/2 - (10* RECT_SIZE)/2; //
        int posun = RECT_SIZE;
        String actualLetter;

        int row0Number = 8;
        int row9Number = 8;

        for (int row = 0; row < 10; row++) {
            x = this.getWidth()/2 - (10* RECT_SIZE)/2 + RECT_SIZE;//2 * RECT_SIZE;
            if (row == 0) {     // horni rada pismen
                for (int column = 1; column < 9; column++) {
                    g2.setColor(FONT_COLOR);
                    Rectangle actualRectangle = new Rectangle(x, y, RECT_SIZE, RECT_SIZE);
                    rectBoard[0][column] = actualRectangle;
                    g2.draw(actualRectangle);

                    g.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
                    actualLetter = letters[column - 1];
                    int letterWidth = g.getFontMetrics().stringWidth(actualLetter);
                    g2.drawString(actualLetter, (x + RECT_SIZE / 2 - letterWidth / 2), (y + RECT_SIZE / 2 + FONT_SIZE / 4));

                    x += posun;
                }
            } else if (row == 9) {     // horni rada pismen

                y += RECT_SIZE;
                g2.setColor(FONT_COLOR);
                for (int column = 1; column < 9; column++) {
                    Rectangle actualRectangle = new Rectangle(x, y, RECT_SIZE, RECT_SIZE);
                    rectBoard[0][column] = actualRectangle;
                    g2.draw(actualRectangle);

                    g2.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
                    actualLetter = letters[column - 1];
                    int letterWidth = g.getFontMetrics().stringWidth(actualLetter);
                    g2.drawString(actualLetter, (x + RECT_SIZE / 2 - letterWidth / 2), (y + RECT_SIZE / 2 + FONT_SIZE / 4));

                    x += posun;
                }
            } else {
                x = this.getWidth()/2 - (10* RECT_SIZE)/2;
                y += RECT_SIZE;
                for (int column = 0; column < 10; column++) {
                    if(column==0) {                             // cisla 0. sloupce
                        g2.setColor(FONT_COLOR);
                        Rectangle actualRectangle = new Rectangle(x, y, RECT_SIZE, RECT_SIZE);
                        rectBoard[row][0] = actualRectangle;
                        g2.draw(actualRectangle);

                        g2.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
                        actualLetter = String.valueOf(row0Number);
                        int letterWidth = g.getFontMetrics().stringWidth(actualLetter);
                        g2.drawString(actualLetter, (x + RECT_SIZE / 2 - letterWidth / 2), (y + RECT_SIZE / 2 + FONT_SIZE / 4));

                        x += posun;
                        row0Number--;
                    } else if (column==9) {                     // cisla 9. sloupce
                        g2.setColor(FONT_COLOR);
                        Rectangle actualRectangle = new Rectangle(x, y, RECT_SIZE, RECT_SIZE);
                        rectBoard[row][0] = actualRectangle;
                        g2.draw(actualRectangle);

                        g2.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
                        actualLetter = String.valueOf(row9Number);
                        int letterWidth = g.getFontMetrics().stringWidth(actualLetter);
                        g2.drawString(actualLetter, (x + RECT_SIZE / 2 - letterWidth / 2), (y + RECT_SIZE / 2 + FONT_SIZE / 4));

                        x += posun;
                        row9Number--;

                    } else {                                    // hraci pole
                        Rectangle actualRectangle = new Rectangle(x, y, RECT_SIZE, RECT_SIZE);
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

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
