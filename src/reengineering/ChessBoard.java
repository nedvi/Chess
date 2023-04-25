package reengineering;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

/**
 * Trida reprezentujici sachovnici
 *
 * @author Dominik Nedved, A22B0109P
 * @version 26.03.2023
 */
public class ChessBoard extends JPanel {

    /** Velikost strany ctverce, jenz reprezentuje vizualizaci jednoho hraciho pole */
    private int rectSize;

    /** Barva "bilych" poli */
    private final Color WHITE = Color.LIGHT_GRAY;

    /** Barva "cernych" poli */
    private final Color BLACK = Color.DARK_GRAY;

    /** Pomocny test pro prvotni nastaveni rozvrzeni figurek */
    private boolean isFirstLoad;

    /** Pomocna promenna pro porovnani predchozi sirky okna s novou */
    private double lastWidth;

    /** Pomocna promenna pro porovnani predchozi vysky okna s novou */
    private double lastHeight;

    /** 2D pole pro praci s vizuialnim zobrazenim sachovnice */
    private final Rectangle[][] rectBoard = new Rectangle[8][8];

    /** 2D pole pro praci s funkcionalnim zobrazenim sachovnice */
    private final Field[][] fieldBoard = new Field[8][8];

    /** Odkaz na aktualne mysi presouvanou figurku */
    public IPiece focusedPiece;

    /** Deklarace pole pesaku */
    private final Pawn[] pawns;

    /** Deklarace pole vezi */
    private final Rook[] rooks;

    /** Deklarace pole koni */
    private final Knight[] knights;

    /** Deklarace pole strelcu */
    private final Bishop[] bishops;

    /** Deklarace pole kraloven */
    private final Queen[] queens;

    /** Deklarace pole kralu */
    private final King[] kings;

    /**
     * Konstruktor sachovnice
     */
    public ChessBoard() {
        this.setMinimumSize(new Dimension(800, 600));

        isFirstLoad = true;
        focusedPiece = null;

        // Pesaci
        pawns = new Pawn[16];
        for (int i = 0; i < 8; i++) {               // Cerni pesaci
            pawns[i] = new Pawn(0, 0, false);
        }
        for (int i = 8; i < pawns.length; i++) {    // Bili pesaci
            pawns[i] = new Pawn(0, 0, true);
        }

        // Veze
        rooks = new Rook[4];
        for (int i = 0; i < 2; i++) {               // Cerne veze
            rooks[i] = new Rook(0, 0, false);
        }
        for (int i = 2; i < rooks.length; i++) {    // Bile veze
            rooks[i] = new Rook(0, 0, true);
        }

        // Kone
        knights = new Knight[4];
        for (int i = 0; i < 2; i++) {               // Cerny kone
            knights[i] = new Knight(0, 0, false);
        }
        for (int i = 2; i < knights.length; i++) {    // Bily kone
            knights[i] = new Knight(0, 0, true);
        }

        // Strelci
        bishops = new Bishop[4];
        for (int i = 0; i < 2; i++) {               // Cerni strelci
            bishops[i] = new Bishop(0, 0, false);
        }
        for (int i = 2; i < bishops.length; i++) {    // Bili strelci
            bishops[i] = new Bishop(0, 0, true);
        }

        // Kralovny
        queens = new Queen[2];
        queens[0] = new Queen(0, 0, false);
        queens[1] = new Queen(0, 0, true);

        // Kralove
        kings = new King[2];
        kings[0] = new King(0, 0, false);
        kings[1] = new King(0, 0, true);
    }

    /**
     * Ziska konkretni figurku pouzivanou mysi podle souradnic
     *
     * @param x souradnice X
     * @param y souradnice Y
     * @return mysi zamerena figurka nebo null
     */
    public IPiece getFocusedPiece(int x, int y) {
        if (focusedPiece == null) {
            for (Pawn pawn : pawns) {
                if (pawn.isPieceHit(x, y)) {
                    focusedPiece = pawn;
                    return focusedPiece;
                }
            }
            for (Rook rook : rooks) {
                if (rook.isPieceHit(x, y)) {
                    focusedPiece = rook;
                    return focusedPiece;
                }
            }
            for (Knight knight : knights) {
                if (knight.isPieceHit(x, y)) {
                    focusedPiece = knight;
                    return focusedPiece;
                }
            }
            for (Bishop bishop : bishops) {
                if (bishop.isPieceHit(x, y)) {
                    focusedPiece = bishop;
                    return focusedPiece;
                }
            }
            for (Queen queen : queens) {
                if (queen.isPieceHit(x, y)) {
                    focusedPiece = queen;
                    return focusedPiece;
                }
            }
            for (King king : kings) {
                if (king.isPieceHit(x, y)) {
                    focusedPiece = king;
                    return focusedPiece;
                }
            }

        } else {
            return focusedPiece;
        }
        return null;
    }

    /**
     * Zajistuje spravne rozvrzeni figurek pri prvotnim nacteni
     */
    public void firstLoad() {
        // Pesaci
        int pawnColumn = 0;
        for (int i = 0; i < pawns.length; i++) {
            Pawn actualPawn = pawns[i];
            if (i < 8) {
                actualPawn.setRow(1);
                actualPawn.setColumn(i);

            } else {
                actualPawn.setRow(6);
                actualPawn.setColumn(pawnColumn);
                pawnColumn++;
            }
            actualPawn.setField(fieldBoard[actualPawn.getRow()][actualPawn.getColumn()]);
            actualPawn.getField().setPiece(actualPawn);
            actualPawn.moveTo(
                    (int) (rectBoard[actualPawn.getRow()][actualPawn.getColumn()].getX() + this.getRectSize() / 2),
                    (int) (rectBoard[actualPawn.getRow()][actualPawn.getColumn()].getY() + this.getRectSize() / 2)
            );
        }

        // Veze
        int rookColumn = 0;
        for (int i = 0; i < rooks.length; i++) {
            Rook actualRook = rooks[i];
            if (i<2) {
                actualRook.setRow(0);
                actualRook.setColumn(i * 7);
                actualRook.setField(fieldBoard[actualRook.getRow()][actualRook.getColumn()]);
            } else {
                actualRook.setRow(7);
                actualRook.setColumn(rookColumn);
                actualRook.setField(fieldBoard[actualRook.getRow()][actualRook.getColumn()]);
                rookColumn += 7;
            }
            actualRook.moveTo(
                    (int) (rectBoard[actualRook.getRow()][actualRook.getColumn()].getX() + this.getRectSize() / 2),
                    (int) (rectBoard[actualRook.getRow()][actualRook.getColumn()].getY() + this.getRectSize() / 2)
            );
        }

        // Kone
        for (int i = 0; i < knights.length; i++) {
            Knight actualKnight = knights[i];
            if (i<2) {
                actualKnight.setRow(0);
                if(i==0) {
                    actualKnight.setColumn(1);
                } else {
                    actualKnight.setColumn(6);
                }
            } else {
                actualKnight.setRow(7);
                if(i==2) {
                    actualKnight.setColumn(1);
                } else {
                    actualKnight.setColumn(6);
                }
            }
            actualKnight.setField(fieldBoard[actualKnight.getRow()][actualKnight.getColumn()]);
            actualKnight.moveTo(
                    (int) (rectBoard[actualKnight.getRow()][actualKnight.getColumn()].getX() + this.getRectSize() / 2),
                    (int) (rectBoard[actualKnight.getRow()][actualKnight.getColumn()].getY() + this.getRectSize() / 2)
            );
        }

        // Strelci
        for (int i = 0; i < bishops.length; i++) {
            Bishop actualBishop = bishops[i];
            if (i<2) {
                actualBishop.setRow(0);
                if(i==0) {
                    actualBishop.setColumn(2);
                } else {
                    actualBishop.setColumn(5);
                }
            } else {
                actualBishop.setRow(7);
                if(i==2) {
                    actualBishop.setColumn(2);
                } else {
                    actualBishop.setColumn(5);
                }
            }
            actualBishop.setField(fieldBoard[actualBishop.getRow()][actualBishop.getColumn()]);
            actualBishop.moveTo(
                    (int) (rectBoard[actualBishop.getRow()][actualBishop.getColumn()].getX() + this.getRectSize() / 2),
                    (int) (rectBoard[actualBishop.getRow()][actualBishop.getColumn()].getY() + this.getRectSize() / 2)
            );
        }

        // Kralovny
        for (int i = 0; i < queens.length; i++) {
            Queen actualQueen = queens[i];
                actualQueen.setRow(i*7);
                actualQueen.setColumn(3);
                actualQueen.setField(fieldBoard[actualQueen.getRow()][actualQueen.getColumn()]);
            actualQueen.moveTo(
                    (int) (rectBoard[actualQueen.getRow()][actualQueen.getColumn()].getX() + this.getRectSize() / 2),
                    (int) (rectBoard[actualQueen.getRow()][actualQueen.getColumn()].getY() + this.getRectSize() / 2)
            );
        }

        // Kralove
        for (int i = 0; i < kings.length; i++) {
            King actualKing = kings[i];
            actualKing.setRow(i*7);
            actualKing.setColumn(4);
            actualKing.setField(fieldBoard[actualKing.getRow()][actualKing.getColumn()]);
            actualKing.moveTo(
                    (int) (rectBoard[actualKing.getRow()][actualKing.getColumn()].getX() + this.getRectSize() / 2),
                    (int) (rectBoard[actualKing.getRow()][actualKing.getColumn()].getY() + this.getRectSize() / 2)
            );
        }
        isFirstLoad = false;
    }

    /**
     * Vykresleni komponenty
     *
     * @param g graficky kontext
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        paintChessBoard(g);

        if (lastWidth != this.getWidth() || lastHeight != this.getHeight()) {    // Aktualizace pozic pri zmene velikosti okna
            // Pesaci
            for (Pawn pawn : pawns) {
                updatePiecesLocations(pawn);
                paintPiece(g, pawn);
            }
            // Veze
            for (Rook rook : rooks) {
                updatePiecesLocations(rook);
                paintPiece(g, rook);
            }
            // Kone
            for (Knight knight : knights) {
                updatePiecesLocations(knight);
                paintPiece(g, knight);
            }
            // Strelci
            for (Bishop bishop : bishops) {
                updatePiecesLocations(bishop);
                paintPiece(g, bishop);
            }
            // Kralovny
            for (Queen queen : queens) {
                updatePiecesLocations(queen);
                paintPiece(g, queen);
            }
            // Kralove
            for (King king : kings) {
                updatePiecesLocations(king);
                paintPiece(g, king);
            }
            lastWidth = this.getWidth();
            lastHeight = this.getHeight();
        } else {
            if(isFirstLoad) {       // Test na prvotni nacteni
                firstLoad();
                isFirstLoad = false;
            }
            // Pesaci
            for (Pawn pawn : pawns) {
                if (!pawn.isOut())
                    paintPiece(g, pawn);
            }
            // Veze
            for (Rook rook : rooks) {
                paintPiece(g, rook);
            }
            // Kone
            for (Knight knight : knights) {
                paintPiece(g, knight);
            }
            // Strelci
            for (Bishop bishop : bishops) {
                paintPiece(g, bishop);
            }
            // Kralovny
            for (Queen queen : queens) {
                paintPiece(g, queen);
            }
            // Kralove
            for (King king : kings) {
                paintPiece(g, king);
            }
        }
    }

    /**
     * Vykresleni sachovnice
     *
     * @param g graficky kontext
     */
    public void paintChessBoard(Graphics g) {
        rectSize = Math.min(this.getWidth(), this.getHeight()) / 8;
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
                Field actualField = new Field(null, row, column);
                fieldBoard[row][column] = actualField;
                if ((row % 2 == 0) == (column % 2 == 0))
                    g2.setColor(WHITE);
                else
                    g2.setColor(BLACK);

                g2.fill(actualRectangle);
                x += posun;
            }
        }
    }

    /**
     * Vykresleni konkretni figurky
     *
     * @param g graficky kontext
     * @param piece konkretni kreslena figurka
     */
    public void paintPiece(Graphics g, IPiece piece) {
        Graphics2D g2 = (Graphics2D) g;
        AffineTransform old = g2.getTransform();
        piece.setPieceSize(this.getRectSize());

        piece.paint(g2);
        g2.setTransform(old);
    }

    /**
     * Zajistuje spravne vykresleni figurek (jak pozic, tak velikosti)
     * podle aktualni velikosti jednoho ctverce sachovnice, ktery je zavisly na veliksoti
     *
     * @param piece aktualizovana figurka
     */
    public void updatePiecesLocations(IPiece piece) {
        piece.moveTo(
                (int) rectBoard[piece.getRow()][piece.getColumn()].getX() + getRectSize()/2,
                (int) rectBoard[piece.getRow()][piece.getColumn()].getY() + getRectSize()/2
        );
        piece.setPieceSize(getRectSize());
    }

    /**
     * Metoda pro MouseMotionListener, ktera zajistuje zmenu barvy figurky pri tahnuti mysi a spravne aktualni souradnice
     *
     * @param e mouse event
     * @param focusedPiece zamerena figurka
     */
    public void mouseDragged(MouseEvent e, IPiece focusedPiece) {
            focusedPiece.setPieceColor(Color.RED);
            focusedPiece.moveTo(e.getX(), e.getY());
    }

    /**
     * Metoda pro MouseListener, ktera po pusteni mysi zajistuje spravne vykresleni doprostred pole,
     * do ktereho byla figurka presunuta.
     * V budoucnu tu bude zajisteno i "vyhazovani" figurek
     *
     * @param e mouse event
     * @param focusedPiece zamerena figurka
     */
    public void mouseReleased(MouseEvent e, IPiece focusedPiece) {
        Rectangle focusedRectangle;
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                focusedRectangle = rectBoard[row][column];
                if (focusedRectangle.contains(e.getX(), e.getY())) {
                    //System.out.print("Focused piece moved from field [row: " + focusedPiece.getField().getRow() + "; column: " + focusedPiece.getField().getColumn() + "]");
                    focusedPiece.moveTo((int) focusedRectangle.getX() + getRectSize() / 2, (int) focusedRectangle.getY() + getRectSize() / 2);
                    focusedPiece.setRow(row);
                    focusedPiece.setColumn(column);
                    focusedPiece.setField(fieldBoard[row][column]);
                    if(focusedPiece.getField().isUsed()) {
                        System.out.println(focusedPiece.getField().getPiece() + " is out!");
                        focusedPiece.getField().getPiece().setOut(true);

                        focusedPiece.getField().setPiece(focusedPiece);     // nastaveni figurky do noveho fieldu

                    }
                    //System.out.println(" to field [row: " + focusedPiece.getField().getRow() + "; column: " + focusedPiece.getField().getColumn() + "]");

                }
            }
            if (focusedPiece.isWhite()) {
                focusedPiece.setPieceColor(Pawn.PIECE_WHITE);
            } else {
                focusedPiece.setPieceColor(Pawn.PIECE_BLACK);
            }
            updatePiecesLocations(focusedPiece);
        }
        this.focusedPiece = null;   // obstara odebrani focusu po pusteni mysi
    }

    //======================================== Gettery ========================================

    /**
     * @return aktualni stranu jednoho ctverce sachovnice
     */
    public int getRectSize() {
        return rectSize;
    }
}
