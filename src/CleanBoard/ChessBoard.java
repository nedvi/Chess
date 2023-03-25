package CleanBoard;

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

    private boolean firstInRowIsWhite = true;

    private boolean isFirstLoad = true;
    private boolean isFirstLoad2 = true;

    private double lastWidth = getWidth();
    private double lastHeight = getHeight();

    private Rectangle[][] rectBoard = new Rectangle[8][8];
    private Field[][] board = new Field[8][8];

    public Pawn pawn1;
    public Pawn pawn2;

    /**
     * Konstruktor
     */
    public ChessBoard() {
        //this.setPreferredSize(new Dimension(800, 600));
        this.setMinimumSize(new Dimension(800, 600));
        pawn1 = new Pawn(0, 0, false);
        pawn2 = new Pawn(0, 0, false);
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
        //pawn.paint(g);
        paintPawn(g);
        paintPawn2(g);
    }

    /**
     * Vykresleni sachovnice
     *
     * @param g
     */
    public void paintChessBoard(Graphics g) {
        setRectSize(Math.min(getWidth(), getHeight()) / 8);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        int x;
        int y = this.getHeight()/2 - (10* rectSize)/2; //
        int posun = rectSize;

        for (int row = 0; row < 8; row++) {
            x = this.getWidth()/2 - (8* rectSize)/2;
            y += rectSize;
            for (int column = 0; column < 8; column++) {   // hraci pole

                Rectangle actualRectangle = new Rectangle(x, y, rectSize, rectSize);
                rectBoard[row][column] = actualRectangle;
                Field actualField = new Field(x, y, row, column);
                board[row][column] = actualField;
                System.out.println(actualField.toString());
                if ((row % 2 == 0) == (column % 2 == 0))
                    g2.setColor(BLACK);
                else
                    g2.setColor(WHITE);

                g2.fill(actualRectangle);
                x += posun;
            }
        }
        if (firstInRowIsWhite) {
            isWhite = false;
            firstInRowIsWhite = false;
        } else {
            isWhite = true;
            firstInRowIsWhite = true;
        }
    }

    public void paintPawn(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        pawn1.setRectSize(getRectSize());
        if (isFirstLoad) {
            pawn1.moveTo(board[1][1].getX() + getRectSize()/2, board[1][1].getY() + getRectSize()/2);
            pawn1.setRow(1);
            pawn1.setCol(1);
            isFirstLoad = false;
        }
        if (lastWidth != this.getWidth() || lastHeight != this.getHeight()) {
            updatePiecesLocations();
            lastWidth = this.getWidth();
            lastHeight = this.getHeight();
        }
        pawn1.paint(g2);
    }

    public void paintPawn2(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        pawn2.setRectSize(getRectSize());
        if (isFirstLoad2) {
            pawn2.moveTo(board[7-1][7-1].getX() + getRectSize()/2, board[7-1][7-1].getY() + getRectSize()/2);
            pawn2.setRow(7-1);
            pawn2.setCol(7-1);
            isFirstLoad2 = false;
        }
        if (lastWidth != this.getWidth() || lastHeight != this.getHeight()) {
            updatePiecesLocations();
            lastWidth = this.getWidth();
            lastHeight = this.getHeight();
        }
        pawn2.paint(g2);
    }

    public void mouseDragged(MouseEvent e) {
        if (this.pawn1.isStarHit(e.getX(), e.getY())) {
            this.pawn1.setStarColor(Color.RED);

            this.pawn1.moveTo(e.getX(), e.getY());
            this.pawn1.repaint();

        } else {
            this.pawn1.setStarColor(Color.BLACK);
            //this.pawn.repaint();
        }
    }

    public void updatePiecesLocations() {
        pawn1.moveTo((int) rectBoard[pawn1.row][pawn1.col].getX() + getRectSize()/2, (int) rectBoard[pawn1.row][pawn1.col].getY() + getRectSize()/2);
        pawn1.setRectSize(getRectSize());
    }

    public void mouseReleased(MouseEvent e) {
        if (this.pawn1.isStarHit(e.getX(), e.getY())) {
            Rectangle focusedField = null;
            for (int row = 0; row < 8; row++) {
                for (int column = 0; column < 8; column++) {
                    focusedField = this.getRectBoard()[row][column];

                    if (focusedField.contains(e.getX(), e.getY())) {
                        System.out.printf("Focused field: row = %d; column = %d [Mouse - X = %d; Y = %d;\n",
                                row, column, e.getX(), e.getY());
//                        this.pawn.moveTo((int) focusedField.getX(), (int) focusedField.getX());
                        this.pawn1.moveTo((int) focusedField.getX() + getRectSize() / 2, (int) focusedField.getY() + getRectSize() / 2);
                        this.pawn1.setRow(row);
                        this.pawn1.setCol(column);
                        //this.pawn.repaint();

                    }
                }
            }
            this.pawn1.setStarColor(Color.BLACK);
        }
        updatePiecesLocations();
    }

    public Rectangle[][] getRectBoard() {
        return rectBoard;
    }

    public void firstLoad() {
        //TODO prvotni nacteni
    }
}
