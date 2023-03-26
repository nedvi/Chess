package CleanBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class ChessBoard extends JPanel {

    private int rectSize;

    /** Barva "bilych" poli */
    private final Color WHITE = Color.LIGHT_GRAY;

    /** Barva "cernych" poli */
    private final Color BLACK = Color.DARK_GRAY;

    /** Pomocna hodnota k zajisteni stridani barev v hracim poli */
    private boolean isWhite = true;

    private boolean firstInRowIsWhite = true;

    private boolean isFirstLoad = true;

    private double lastWidth = getWidth();
    private double lastHeight = getHeight();

    private Rectangle[][] rectBoard = new Rectangle[8][8];
    private Field[][] board = new Field[8][8];

    public Pawn[] pawns;

    public Rook rook1;

    /**
     * Konstruktor
     */
    public ChessBoard() {
        //this.setPreferredSize(new Dimension(800, 600));
        this.setMinimumSize(new Dimension(800, 600));
        pawns = new Pawn[2];  // TODO pak upravit
        for (int i = 0; i < pawns.length; i++) {
            pawns[i] = new Pawn(0, 0, false);
        }
        rook1 = new Rook(100+getRectSize(), 100+getRectSize(), false);
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
//        if(isFirstLoad) {
//            firstLoad();
//        }
        pawns[1].setsX(300);
        paintPawn(g);
        //rook1.paint(g);
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
                Field actualField = new Field(row, column);
                board[row][column] = actualField;
                //System.out.println(actualField.toString());
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

        for (int i = 0; i < pawns.length; i++) {
            pawns[i].setRectSize(getRectSize());
            if (lastWidth != this.getWidth() || lastHeight != this.getHeight()) {
                updatePiecesLocations(pawns[i]);
                lastWidth = this.getWidth();
                lastHeight = this.getHeight();
            }
            pawns[i].paint(g2);
        }
        rook1.setRectSize(getRectSize());
        rook1.paint(g2);
    }

    public void updatePiecesLocations(Pawn pawn) {
        pawn.moveTo(
                (int) rectBoard[pawn.getRow()][pawn.getColumn()].getX() + getRectSize()/2,
                (int) rectBoard[pawn.getRow()][pawn.getColumn()].getY() + getRectSize()/2
        );
        pawn.setRectSize(getRectSize());
    }

    public void mouseDragged(MouseEvent e, Pawn focusedPawn) {

        if (focusedPawn != null) {
            focusedPawn.setPawnColor(Color.RED);

            focusedPawn.moveTo(e.getX(), e.getY());
        } else {
            focusedPawn.setPawnColor(Color.BLACK);
            //this.pawn.repaint();
        }
    }

    public void mouseReleased(MouseEvent e, Pawn focusedPawn) {
        Rectangle focusedField;
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                focusedField = rectBoard[row][column];
                if (focusedField.contains(e.getX(), e.getY())) {
                    System.out.printf("Focused field: row = %d; column = %d [Mouse - X = %d; Y = %d]\n",
                            row, column, e.getX(), e.getY());
                    focusedPawn.moveTo((int) focusedField.getX() + getRectSize() / 2, (int) focusedField.getY() + getRectSize() / 2);
                    focusedPawn.setRow(row);
                    focusedPawn.setColumn(column);
                    System.out.println("Pesak1 pozice: x = " + pawns[0].getsX() + "y = " + pawns[0].getsY());
                    System.out.println("Pesak2 pozice: x = " + pawns[1].getsX() + "y = " + pawns[1].getsY());
                }
            }
            focusedPawn.setPawnColor(Color.BLACK);
            updatePiecesLocations(focusedPawn);
        }
    }

    public Pawn getFocusedPiece(int x, int y) {
        for (int i = 0; i < pawns.length; i++) {
            if (pawns[i].isPawnHit(x, y)) {
                //System.out.println("Hit pawn: " + i);
                return pawns[i];
            }
        }
        return null;
    }
    public void firstLoad() {
        for (int i = 1; i < pawns.length; i++) {
            Pawn actualPawn = pawns[i];
            actualPawn.setRow(i);
            actualPawn.setColumn(i);
            actualPawn.moveTo(
                    board[actualPawn.getRow()][actualPawn.getColumn()].getX() + getRectSize() / 2,
                    board[actualPawn.getRow()][actualPawn.getColumn()].getY() + getRectSize() / 2
            );
            isFirstLoad = false;
        }
    }



    //======================================== Gettery ========================================
    public int getRectSize() {
        return rectSize;
    }
    public Rectangle[][] getRectBoard() {
        return rectBoard;
    }
    //======================================== Settery ========================================
    public void setRectSize(int rectSize) {
        this.rectSize = rectSize;
    }
}