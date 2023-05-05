package reengineering;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
    public APiece focusedPiece;

    /** Deklarace pole pesaku */
    private final List<Pawn> pawns;

    /** Deklarace pole vezi */
    private final List<Rook> rooks;

    /** Deklarace pole koni */
    private final List<Knight> knights;

    /** Deklarace pole strelcu */
    private final List<Bishop> bishops;

    /** Deklarace pole kraloven */
    private final List<Queen> queens;

    /** Deklarace pole kralu */
    private final List<King> kings;

    private APiece lastMovedPiece;

    private final List<APiece> eliminatedPieces;

    /**
     * Konstruktor sachovnice
     */
    public ChessBoard() {
        this.setMinimumSize(new Dimension(800, 600));

        isFirstLoad = true;
        focusedPiece = null;

        // Pesaci
        pawns = new ArrayList<>();
        for (int i = 0; i < 8; i++) {               // Cerni pesaci
            pawns.add(new Pawn(0, 0, false));
        }
        for (int i = 8; i < 16; i++) {    // Bili pesaci
            pawns.add(new Pawn(0, 0, true));
        }

        // Veze
        rooks = new ArrayList<>();
        for (int i = 0; i < 2; i++) {               // Cerne veze
            rooks.add(new Rook(0, 0, false));
        }
        for (int i = 2; i < 4; i++) {    // Bile veze
            rooks.add(new Rook(0, 0, true));
        }

        // Kone
        knights = new ArrayList<>();
        for (int i = 0; i < 2; i++) {               // Cerny kone
            knights.add(new Knight(0, 0, false));
        }
        for (int i = 2; i < 4; i++) {    // Bily kone
            knights.add(new Knight(0, 0, true));
        }

        // Strelci
        bishops = new ArrayList<>();
        for (int i = 0; i < 2; i++) {               // Cerni strelci
            bishops.add(new Bishop(0, 0, false));
        }
        for (int i = 2; i < 4; i++) {    // Bili strelci
            bishops.add(new Bishop(0, 0, true));
        }

        // Kralovny
        queens = new ArrayList<>();
        queens.add(new Queen(0, 0, false));
        queens.add(new Queen(0, 0, true));

        // Kralove
        kings = new ArrayList<>();
        kings.add(new King(0, 0, false));
        kings.add(new King(0, 0, true));

        eliminatedPieces = new ArrayList<>();
    }

    /**
     * Ziska konkretni figurku pouzivanou mysi podle souradnic
     *
     * @param x souradnice X
     * @param y souradnice Y
     * @return mysi zamerena figurka nebo null
     */
    public APiece getFocusedPiece(int x, int y) {
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
        for (int i = 0; i < pawns.size(); i++) {
            Pawn actualPawn = pawns.get(i);
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
            actualPawn.getField().setUsed(true);
            actualPawn.moveTo(
                    (int) (rectBoard[actualPawn.getRow()][actualPawn.getColumn()].getX() + this.getRectSize() / 2),
                    (int) (rectBoard[actualPawn.getRow()][actualPawn.getColumn()].getY() + this.getRectSize() / 2)
            );
            actualPawn.repaint();
        }

        // Veze
        int rookColumn = 0;
        for (int i = 0; i < rooks.size(); i++) {
            Rook actualRook = rooks.get(i);
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
        for (int i = 0; i < knights.size(); i++) {
            Knight actualKnight = knights.get(i);
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
            actualKnight.getField().setUsed(true);
            actualKnight.moveTo(
                    (int) (rectBoard[actualKnight.getRow()][actualKnight.getColumn()].getX() + this.getRectSize() / 2),
                    (int) (rectBoard[actualKnight.getRow()][actualKnight.getColumn()].getY() + this.getRectSize() / 2)
            );
        }

        // Strelci
        for (int i = 0; i < bishops.size(); i++) {
            Bishop actualBishop = bishops.get(i);
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
        for (int i = 0; i < queens.size(); i++) {
            Queen actualQueen = queens.get(i);
                actualQueen.setRow(i*7);
                actualQueen.setColumn(3);
                actualQueen.setField(fieldBoard[actualQueen.getRow()][actualQueen.getColumn()]);
            actualQueen.moveTo(
                    (int) (rectBoard[actualQueen.getRow()][actualQueen.getColumn()].getX() + this.getRectSize() / 2),
                    (int) (rectBoard[actualQueen.getRow()][actualQueen.getColumn()].getY() + this.getRectSize() / 2)
            );
        }

        // Kralove
        for (int i = 0; i < kings.size(); i++) {
            King actualKing = kings.get(i);
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
    public void paintPiece(Graphics g, APiece piece) {
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
    public void updatePiecesLocations(APiece piece) {
        piece.moveTo(
                (int) rectBoard[piece.getRow()][piece.getColumn()].getX() + getRectSize()/2,
                (int) rectBoard[piece.getRow()][piece.getColumn()].getY() + getRectSize()/2
        );
        piece.setPieceSize(getRectSize());
    }

    /**
     * Metoda pro kontrolu, zda nebyla nektera z figurek eliminovana.
     * Pokud se ukaze, ze ano, figurka bude odebrana z hraciho pole a presune se do seznamu vyrazenych.
     * Zaroven je zde osetrena situace, kdy by se figurka snazila vyhodit figurku ze svych rad :).
     *
     * @param piece posledni zahrana figurka
     * @param oldFocusedPieceRow uchovani indexu rady pro pripad nevalidniho tahu
     * @param oldFocusedPieceColumn uchovani indexu sloupce pro pripad nevalidniho tahu
     */
    public void eliminate(APiece piece, int oldFocusedPieceRow, int oldFocusedPieceColumn) {
        double sX = piece.getsX();
        double sY = piece.getsY();

        boolean isWhitePiece = piece.isWhite();

        Iterator<Pawn> pawnIterator = pawns.iterator();
        while (pawnIterator.hasNext()) {
            Pawn pawn = pawnIterator.next();

            if (pawn.isPieceHit(sX, sY) && !pawn.equals(piece)) {   // pokud je zasazen jiny pesak a zaroven neni roven sam sobe
                if (pawn.isWhite() != isWhitePiece) {
                    eliminatedPieces.add(pawn);
                    pawnIterator.remove();
                    System.out.println(pawn.getClass().getSimpleName() + " ELIMINATED");
                } else {
                    piece.setRow(oldFocusedPieceRow);
                    piece.setColumn(oldFocusedPieceColumn);
                    updatePiecesLocations(piece);
                    System.out.format("Invalid move, %s moved back to the last valid position.\n", pawn.getClass().getSimpleName());
                }
            }
        }

        Iterator<Rook> rookIterator = rooks.iterator();
        while (rookIterator.hasNext()) {
            Rook rook = rookIterator.next();

            if (rook.isPieceHit(sX, sY) && !rook.equals(piece)) {   // pokud je zasazen jiny pesak a zaroven neni roven sam sobe
                if (rook.isWhite() != isWhitePiece) {
                    eliminatedPieces.add(rook);
                    rookIterator.remove();
                    System.out.println(rook.getClass().getSimpleName() + " ELIMINATED");
                } else {
                    piece.setRow(oldFocusedPieceRow);
                    piece.setColumn(oldFocusedPieceColumn);
                    updatePiecesLocations(piece);
                    System.out.format("Invalid move, %s moved back to the last valid position.\n", rook.getClass().getSimpleName());
                }
            }
        }

        Iterator<Knight> knightIterator = knights.iterator();
        while (knightIterator.hasNext()) {
            Knight knight = knightIterator.next();

            if (knight.isPieceHit(sX, sY) && !knight.equals(piece)) {   // pokud je zasazen jiny pesak a zaroven neni roven sam sobe
                if (knight.isWhite() != isWhitePiece) {
                    eliminatedPieces.add(knight);
                    knightIterator.remove();
                    System.out.println(knight.getClass().getSimpleName() + " ELIMINATED");
                } else {
                    piece.setRow(oldFocusedPieceRow);
                    piece.setColumn(oldFocusedPieceColumn);
                    updatePiecesLocations(piece);
                    System.out.format("Invalid move, %s moved back to the last valid position.\n", knight.getClass().getSimpleName());
                }
            }
        }

        Iterator<Bishop> bishopIterator = bishops.iterator();
        while (bishopIterator.hasNext()) {
            Bishop bishop = bishopIterator.next();

            if (bishop.isPieceHit(sX, sY) && !bishop.equals(piece)) {   // pokud je zasazen jiny pesak a zaroven neni roven sam sobe
                if (bishop.isWhite() != isWhitePiece) {
                    eliminatedPieces.add(bishop);
                    bishopIterator.remove();
                    System.out.println(bishop.getClass().getSimpleName() + " ELIMINATED");
                } else {
                    piece.setRow(oldFocusedPieceRow);
                    piece.setColumn(oldFocusedPieceColumn);
                    updatePiecesLocations(piece);
                    System.out.format("Invalid move, %s moved back to the last valid position.\n", bishop.getClass().getSimpleName());
                }
            }
        }

        Iterator<Queen> queenIterator = queens.iterator();
        while (queenIterator.hasNext()) {
            Queen queen = queenIterator.next();

            if (queen.isPieceHit(sX, sY) && !queen.equals(piece)) {   // pokud je zasazen jiny pesak a zaroven neni roven sam sobe
                if (queen.isWhite() != isWhitePiece) {
                    eliminatedPieces.add(queen);
                    queenIterator.remove();
                    System.out.println(queen.getClass().getSimpleName() + " ELIMINATED");
                } else {
                    piece.setRow(oldFocusedPieceRow);
                    piece.setColumn(oldFocusedPieceColumn);
                    updatePiecesLocations(piece);
                    System.out.format("Invalid move, %s moved back to the last valid position.\n", queen.getClass().getSimpleName());
                }
            }
        }

        Iterator<King> kingIterator = kings.iterator();
        while (kingIterator.hasNext()) {
            King king = kingIterator.next();

            if (king.isPieceHit(sX, sY) && !king.equals(piece)) {   // pokud je zasazen jiny pesak a zaroven neni roven sam sobe
                if (king.isWhite() != isWhitePiece) {
                    eliminatedPieces.add(king);

                    kingIterator.remove();
                    System.out.println(king.getClass().getSimpleName() + " ELIMINATED");
                    gameOver(king);
                } else {
                    piece.setRow(oldFocusedPieceRow);
                    piece.setColumn(oldFocusedPieceColumn);
                    updatePiecesLocations(piece);
                    System.out.format("Invalid move, %s moved back to the last valid position.\n", king.getClass().getSimpleName());
                }
            }
        }
    }

    private void gameOver(King eliminatedKing) {
        StringBuilder gameOverSB = new StringBuilder();

        if (eliminatedKing.isWhite()) {
            gameOverSB.append("Hra skončila! Vítěz: Černý");
        } else {
            gameOverSB.append("Hra skončila! Vítěz: Bílý");
        }

        JOptionPane gameOverDialog = new JOptionPane(gameOverSB);
        gameOverDialog.setVisible(true);



        JButton newGameBTN = new JButton("New game");
        gameOverDialog.add(newGameBTN);

        JButton exitBTN = new JButton("Exit");
        gameOverDialog.add(exitBTN);


        exitBTN.addActionListener(event -> System.exit(0));
        JDialog dialog = gameOverDialog.createDialog(null, "Konec hry");
        newGameBTN.addActionListener(event -> {
            firstLoad();
            dialog.setVisible(false);
        });
        dialog.setVisible(true);

    }

    /**
     * Metoda pro MouseMotionListener, ktera zajistuje zmenu barvy figurky pri tahnuti mysi a spravne aktualni souradnice
     *
     * @param e mouse event
     * @param focusedPiece zamerena figurka
     */
    public void mouseDragged(MouseEvent e, APiece focusedPiece) {
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
    public void mouseReleased(MouseEvent e, APiece focusedPiece) {

        int oldFocusedPieceRow = 1000;
        int oldFocusedPieceColumn = 1000;


        Rectangle focusedRectangle;
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                focusedRectangle = rectBoard[row][column];
                if (focusedRectangle.contains(e.getX(), e.getY())) {

                    oldFocusedPieceRow = focusedPiece.getRow();
                    oldFocusedPieceColumn = focusedPiece.getColumn();

                    focusedPiece.moveTo((int) focusedRectangle.getX() + getRectSize() / 2, (int) focusedRectangle.getY() + getRectSize() / 2);
                    focusedPiece.setRow(row);
                    focusedPiece.setColumn(column);
                    focusedPiece.setField(fieldBoard[row][column]);

                    //TODO wtf to co je tenhle if blok LMAO
                    if(focusedPiece.getField().isUsed()) {
                        System.out.println(focusedPiece.getField().getPiece() + " is out!");
                        focusedPiece.getField().getPiece().setOut(true);

                        focusedPiece.getField().setPiece(focusedPiece);     // nastaveni figurky do noveho fieldu
                    }
                    focusedPiece.getField().setUsed(true);
                    System.out.println(" to field [row: " + focusedPiece.getField().getRow() + "; column: " + focusedPiece.getField().getColumn() + "; isUsed:" + focusedPiece.getField().isUsed() + "]");

                }
            }
            if (focusedPiece.isWhite()) {
                focusedPiece.setPieceColor(Pawn.PIECE_WHITE);
            } else {
                focusedPiece.setPieceColor(Pawn.PIECE_BLACK);
            }
            updatePiecesLocations(focusedPiece);
            lastMovedPiece = focusedPiece;

        }
        eliminate(lastMovedPiece, oldFocusedPieceRow, oldFocusedPieceColumn);
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
