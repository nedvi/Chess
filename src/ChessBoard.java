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
 * @version 07.05.2023
 */
public class ChessBoard extends JPanel {

    public boolean isMoving;
    public int startX;
    public int startY;
    public int targetX;
    public int targetY;

    public int castlingRookStartX;
    public int castlingRookStartY;
    public int castlingRookTargetX;
    public int castlingRookTargetY;

    private int moveStep;

    private boolean mouseWasReleased = false;

    private long millisecStart;
    private boolean isNewAnimation = true;

    private Field lastMovedFrom;


    private Field lastMovedTo;


    private List<Field> validMoves;

    /** Pole, pro ktere by byl dany kral v sachu (seznam se pouziva pouze pri validaci pohybu krale) */
    private List<Field> checkFields;

    private boolean whitesTurn = true;

    private boolean mouseMovedFunctionOn = true;

    private APiece castlingRook = null;

    private List<Double> whitePlayTimes;
    private List<Double> blackPlayTimes;

    private long playTimeStart;
    private long playTimeEnd;

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
    public final Field[][] fieldBoard = new Field[8][8];

    /** Odkaz na aktualne mysi presouvanou figurku */
    public APiece focusedPiece;

    /** Deklarace pole pesaku */
    private List<Pawn> pawns;

    /** Deklarace pole vezi */
    private List<Rook> rooks;

    /** Deklarace pole koni */
    private List<Knight> knights;

    /** Deklarace pole strelcu */
    private List<Bishop> bishops;

    /** Deklarace pole kraloven */
    private List<Queen> queens;

    /** Deklarace pole kralu */
    private List<King> kings;

    /**
     * Deklarace pole eliminiovanych
     */
    private List<APiece> eliminatedPieces;

    /**
     * Inicializacni metoda pro novou hru
     */
    private void init() {
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

        // Inicializace instanci poli
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Field actualField = new Field(i, j);
                fieldBoard[i][j] = actualField;
            }
        }

        whitePlayTimes = new ArrayList<>();
        blackPlayTimes = new ArrayList<>();

        playTimeStart = System.currentTimeMillis();

        checkFields = null;
    }

    /**
     * Konstruktor sachovnice
     */
    public ChessBoard() {
        this.setMinimumSize(new Dimension(800, 600));
        isFirstLoad = true;
        focusedPiece = null;
        int timerDelay = 25;

        // Osetreni repaintu pri actionPerformed.
        // Volani repaint() kdekoliv ve tride totiz jen dava pozadavek
        // na co nejdrivejsi prekresleni v aktualnim vlakne,
        // coz se muze projevit podstatne pozdeji (nebo vubec xd)
//        new Timer(timerDelay, e -> repaint()).start();
        init();

    }

    /**
     * Ziska konkretni figurku pouzivanou mysi podle souradnic
     *
     * @param x souradnice X
     * @param y souradnice Y
     * @return mysi zamerena figurka nebo null
     */
    public APiece getFocusedPiece(int x, int y) {
        List<APiece> allPieces = getAllPieces();

        if (focusedPiece != null) {
            return focusedPiece;
        }
        for (APiece piece : allPieces) {
            if (piece.isPieceHit(x, y)) {

                focusedPiece = piece;
                return focusedPiece;
            }
        }
        return null;
    }

    /**
     * Vytvoreni jednoho seznamu vsech figurek na sachovnici
     *
     * @return seznam vsech figurek na sachovnici
     */
    public List<APiece> getAllPieces() {
        List<APiece> allPieces = new ArrayList<>();

        allPieces.addAll(pawns);
        allPieces.addAll(rooks);
        allPieces.addAll(knights);
        allPieces.addAll(bishops);
        allPieces.addAll(queens);
        allPieces.addAll(kings);

        return allPieces;
    }

    private void animateMove() {
        if (isNewAnimation) {
            millisecStart = System.currentTimeMillis();
            isNewAnimation = false;
            validMoves = null;
        }

        final int MAX_SUM_OF_STEPS = 33;

        if (moveStep <= MAX_SUM_OF_STEPS) {
            int currentX = startX + (targetX - startX) * moveStep / MAX_SUM_OF_STEPS;
            int currentY = startY + (targetY - startY) * moveStep / MAX_SUM_OF_STEPS;

            int currentCastlingRookX = castlingRookStartX + (castlingRookTargetX - castlingRookStartX) * moveStep / MAX_SUM_OF_STEPS;
            int currentCastlingRookY = castlingRookStartY + (castlingRookTargetY - castlingRookStartY) * moveStep / MAX_SUM_OF_STEPS;

//            System.out.println("MoveStep: " + moveStep + "; StartX: " + startX + "; StartY: " + startY + ";targetX: " + targetX + "; targetY: " + targetY +"; CurrentX: " + currentX + "; CurrentY: " + currentY);

            for (APiece piece : getAllPieces()) {
                if (piece == focusedPiece) {
                    piece.moveTo(currentX, currentY);
                    if (castlingRook != null) {
                        castlingRook.moveTo(currentCastlingRookX, currentCastlingRookY);
                    }
                }
            }

            moveStep++;
        } else {
            isMoving = false;
            moveStep = 0;
            this.focusedPiece = null;   // obstara odebrani focusu po pusteni mysi
            long millisecEnd = System.currentTimeMillis();
//            System.out.println("Presun trval " + (millisecEnd - millisecStart) + " ms.");

            playTimeEnd = System.currentTimeMillis();

            long lastPlayTime = (playTimeEnd - playTimeStart);

            double lastPlayTimeInSecond = (double) lastPlayTime / 1000;

            if (whitesTurn) {
//                if (whitePlayTimes.size() == 0) {
//                    whitePlayTimes.add(lastPlayTime);
//                }
                blackPlayTimes.add(lastPlayTimeInSecond);
                System.out.println("Tah cerneho trval " + lastPlayTime + " ms. Tedy " + lastPlayTimeInSecond + " s.");
            } else {
                whitePlayTimes.add(lastPlayTimeInSecond);
                System.out.println("Tah bileho trval " + lastPlayTime + " ms. Tedy " + lastPlayTimeInSecond + " s.");
            }

            playTimeStart = System.currentTimeMillis();
            isNewAnimation = true;
            mouseMovedFunctionOn = true;
            validMoves = null;
            castlingRook = null;
            checkFields = null;
        }
    }

    /**
     * Vykresleni celkoveho panelu
     *
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        paintChessBoard(g);

        if (!isFirstLoad && mouseWasReleased && isMoving) {
            animateMove();
        }
        if (lastWidth != this.getWidth() || lastHeight != this.getHeight()) {    // Aktualizace pozic pri zmene velikosti okna
            // Aktualizace pozic
            for (APiece piece : getAllPieces()) {
                updatePiecesLocations(piece);
                paintPiece(g, piece);
            }
            lastWidth = this.getWidth();
            lastHeight = this.getHeight();
        } else {
            if(isFirstLoad) {       // Test na prvotni nacteni
                // Pesaci
                lastMovedFrom = null;
                lastMovedTo = null;
                validMoves = null;
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
                    fieldUpdate(actualPawn);
                    actualPawn.moveTo(
                            (int) (rectBoard[actualPawn.getRow()][actualPawn.getColumn()].getX() + this.getRectSize() / 2),
                            (int) (rectBoard[actualPawn.getRow()][actualPawn.getColumn()].getY() + this.getRectSize() / 2)
                    );
                    paintPiece(g, actualPawn);
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
                    fieldUpdate(actualRook);
                    actualRook.moveTo(
                            (int) (rectBoard[actualRook.getRow()][actualRook.getColumn()].getX() + this.getRectSize() / 2),
                            (int) (rectBoard[actualRook.getRow()][actualRook.getColumn()].getY() + this.getRectSize() / 2)
                    );
                    paintPiece(g, actualRook);
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
                    fieldUpdate(actualKnight);
                    actualKnight.moveTo(
                            (int) (rectBoard[actualKnight.getRow()][actualKnight.getColumn()].getX() + this.getRectSize() / 2),
                            (int) (rectBoard[actualKnight.getRow()][actualKnight.getColumn()].getY() + this.getRectSize() / 2)
                    );
                    paintPiece(g, actualKnight);
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
                    fieldUpdate(actualBishop);
                    actualBishop.moveTo(
                            (int) (rectBoard[actualBishop.getRow()][actualBishop.getColumn()].getX() + this.getRectSize() / 2),
                            (int) (rectBoard[actualBishop.getRow()][actualBishop.getColumn()].getY() + this.getRectSize() / 2)
                    );
                    paintPiece(g, actualBishop);
                }

                // Kralovny
                for (int i = 0; i < queens.size(); i++) {
                    Queen actualQueen = queens.get(i);
                    actualQueen.setRow(i*7);
                    actualQueen.setColumn(3);

                    fieldUpdate(actualQueen);
                    actualQueen.moveTo(
                            (int) (rectBoard[actualQueen.getRow()][actualQueen.getColumn()].getX() + this.getRectSize() / 2),
                            (int) (rectBoard[actualQueen.getRow()][actualQueen.getColumn()].getY() + this.getRectSize() / 2)
                    );
                    paintPiece(g, actualQueen);
                }

                // Kralove
                for (int i = 0; i < kings.size(); i++) {
                    King actualKing = kings.get(i);
                    actualKing.setRow(i*7);
                    actualKing.setColumn(4);

                    fieldUpdate(actualKing);
                    actualKing.moveTo(
                            (int) (rectBoard[actualKing.getRow()][actualKing.getColumn()].getX() + this.getRectSize() / 2),
                            (int) (rectBoard[actualKing.getRow()][actualKing.getColumn()].getY() + this.getRectSize() / 2)
                    );
                    paintPiece(g, actualKing);
                }
                whitesTurn = true;
                isFirstLoad = false;
            }
            // Pesaci
            for (Pawn pawn : pawns) {
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



            int XdistanceBetweenEliminatedWhites = rectSize;
            int YdistanceBetweenEliminatedWhites = 0;
            int XdistanceBetweenEliminatedBlacks = rectSize;
            int YdistanceBetweenEliminatedBlacks = 0;

            int sumOfWhiteEliminated = 0;
            int sumOfBlackEliminated = 0;


            if (eliminatedPieces != null) {
                for (APiece piece : eliminatedPieces) {
                    if (piece.isWhite() ) {
                        if (sumOfWhiteEliminated == 4) {
                            XdistanceBetweenEliminatedWhites = rectSize;
                            YdistanceBetweenEliminatedWhites += rectSize/1.5;
                            sumOfWhiteEliminated = 0;
                        }
                        piece.moveTo(
                                rectBoard[0][7].getX() + rectSize/3.0 + XdistanceBetweenEliminatedWhites,
                                rectBoard[0][7].getY() + rectSize/2.0 + YdistanceBetweenEliminatedWhites);
                        XdistanceBetweenEliminatedWhites += rectSize/3.0;
                        paintEliminatedPiece(g, piece);


                        sumOfWhiteEliminated += 1;
                    } else {
                        if (sumOfBlackEliminated == 4) {
                            XdistanceBetweenEliminatedBlacks = rectSize;
                            YdistanceBetweenEliminatedBlacks += rectSize/1.5;
                            sumOfBlackEliminated = 0;
                        }
                        piece.moveTo(
                                rectBoard[7][7].getX() + rectSize/3.0 + XdistanceBetweenEliminatedBlacks,
                                rectBoard[7][7].getY() + rectSize/2.0 - YdistanceBetweenEliminatedBlacks);
                        XdistanceBetweenEliminatedBlacks += rectSize/3.0;
                        paintEliminatedPiece(g, piece);


                        sumOfBlackEliminated += 1;
                    }
                }
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

        int alpha = 127; // 50% transparent
        Color transparentOrange = new Color(255, 128, 0, alpha);
        Color transparentGreen = new Color(16, 223, 33, alpha);

        for (int row = 0; row < 8; row++) {
            x = this.getWidth()/2 - (8* rectSize)/2;
            y += rectSize;
            for (int column = 0; column < 8; column++) {   // hraci pole
                Rectangle actualRectangle = new Rectangle(x, y, rectSize, rectSize);
                rectBoard[row][column] = actualRectangle;


                if ((row % 2 == 0) == (column % 2 == 0)) {
                    g2.setColor(WHITE);
                    g2.fill(actualRectangle);
                } else {
                    g2.setColor(BLACK);
                    g2.fill(actualRectangle);
                }

                // Zvyrazneni poslednoho tahu
                if (lastMovedFrom != null && lastMovedTo != null) {

                    if (row == lastMovedFrom.getRow() && column == lastMovedFrom.getColumn()) {
                        g2.setColor(transparentOrange);
                        g2.fill(actualRectangle);
                    } else if (row == lastMovedTo.getRow() && column == lastMovedTo.getColumn()) {
                        g2.setColor(transparentOrange);
                        g2.fill(actualRectangle);
                    }
                }

                // Zvyrazneni moznych tahu
                if (validMoves != null) {
                    for (Field validMove : validMoves) {
                        if (row == validMove.getRow() && column == validMove.getColumn()) {
                            g2.setColor(transparentGreen);
                            g2.fill(actualRectangle);
                        }
                    }
                }

                x += posun;
            }
        }

//        // Zvyrazneni poslednoho tahu
//        if (lastMovedFrom != null && lastMovedTo != null) {
//
//
////            lastMovedFrom.setSize(rectSize,rectSize);
////            lastMovedTo.setSize(rectSize,rectSize);
//
//            int alpha = 127; // 50% transparent
//            Color transparentOrange = new Color(255, 128, 0, alpha);
//            g2.setColor(transparentOrange);
//
//            y = this.getHeight()/2 - (10* rectSize)/2; //
//
//            for (int row = 0; row < 8; row++) {
//                x = this.getWidth()/2 - (8* rectSize)/2;
//                y += rectSize;
//                for (int column = 0; column < 8; column++) {
//                    if (rectBoard[row][column].equals(lastMovedFrom)) {
//                        g2.fill(lastMovedFrom);
//                    } else if (rectBoard[row][column].equals(lastMovedTo)) {
//                        g2.fill(lastMovedTo);
//                    }
//                    x += posun;
//                }
//            }
//        }


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
     * Vykresleni konkretni eliminovanou figurky
     *
     * @param g graficky kontext
     * @param piece konkretni kreslena figurka
     */
    public void paintEliminatedPiece(Graphics g, APiece piece) {
        Graphics2D g2 = (Graphics2D) g;
        AffineTransform old = g2.getTransform();
        piece.setPieceSize((int) (this.getRectSize()/1.25));
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
                (int) rectBoard[piece.getRow()][piece.getColumn()].getX() + getRectSize()/2.0,
                (int) rectBoard[piece.getRow()][piece.getColumn()].getY() + getRectSize()/2.0
        );
        piece.setPieceSize(getRectSize());
    }

    /**
     * Metoda pro kontrolu, zda nebyla nektera z figurek eliminovana.
     * Pokud se ukaze, ze ano, figurka bude odebrana z hraciho pole a presune se do seznamu vyrazenych.
     * Zaroven je zde osetrena situace, kdy by se figurka snazila vyhodit figurku ze svych rad :).
     * V metode je osetrena i druha cast brani mimochodem (prvni je v Move.pawnMove())
     *
     * @param piece posledni zahrana figurka
     * @param oldFocusedPieceRow uchovani indexu rady pro pripad nevalidniho tahu
     * @param oldFocusedPieceColumn uchovani indexu sloupce pro pripad nevalidniho tahu
     */
    public void eliminate(APiece piece, int oldFocusedPieceRow, int oldFocusedPieceColumn) {
        double sX = piece.getsX();
        double sY = piece.getsY();
        boolean isWhitePiece = piece.isWhite();

        List<APiece> allPieces = new ArrayList<>();
        allPieces.addAll(pawns);
        allPieces.addAll(rooks);
        allPieces.addAll(knights);
        allPieces.addAll(bishops);
        allPieces.addAll(queens);

        boolean enPassantDone = false;

        APiece enPassantPiece = null;
        APiece enPassantPieceLeft;
        APiece enPassantPieceRight;

        if (piece.getClass().getSimpleName().equals("Pawn")) {
            //======================================================= Brani mimochodem bilym pesakem =======================================================
            if (piece.isWhite()) {
                if (oldFocusedPieceColumn == 0
                        && fieldBoard[oldFocusedPieceRow][oldFocusedPieceColumn + 1].getPiece() != null
                        && fieldBoard[oldFocusedPieceRow][oldFocusedPieceColumn + 1].getPiece().isEnPassant()
                        && fieldBoard[oldFocusedPieceRow][oldFocusedPieceColumn + 1].getPiece().getRow() == 3) {   // brani mimochodem doprava bez sloupce 0
                    enPassantPieceRight = fieldBoard[oldFocusedPieceRow][oldFocusedPieceColumn + 1].getPiece();
                    enPassantPiece = enPassantPieceRight;
                } else if (oldFocusedPieceColumn == 7
                        && fieldBoard[oldFocusedPieceRow][oldFocusedPieceColumn - 1].getPiece() != null
                        && fieldBoard[oldFocusedPieceRow][oldFocusedPieceColumn - 1].getPiece().isEnPassant()
                        && fieldBoard[oldFocusedPieceRow][oldFocusedPieceColumn - 1].getPiece().getRow() == 3) {    // brani mimochodem doleva bez sloupce 7
                    enPassantPieceLeft = fieldBoard[oldFocusedPieceRow][oldFocusedPieceColumn - 1].getPiece();
                    enPassantPiece = enPassantPieceLeft;
                } else if (oldFocusedPieceColumn != 7
                        && fieldBoard[oldFocusedPieceRow][oldFocusedPieceColumn + 1].getPiece() != null
                        && fieldBoard[oldFocusedPieceRow][oldFocusedPieceColumn + 1].getPiece().isEnPassant()
                        && fieldBoard[oldFocusedPieceRow][oldFocusedPieceColumn + 1].getPiece().getRow() == 3) {    // brani mimochodem doprava
                    enPassantPieceRight = fieldBoard[oldFocusedPieceRow][oldFocusedPieceColumn + 1].getPiece();
                    enPassantPiece = enPassantPieceRight;
                } else if (oldFocusedPieceColumn != 0
                        && fieldBoard[oldFocusedPieceRow][oldFocusedPieceColumn - 1].getPiece() != null
                        && fieldBoard[oldFocusedPieceRow][oldFocusedPieceColumn - 1].getPiece().isEnPassant()
                        && fieldBoard[oldFocusedPieceRow][oldFocusedPieceColumn - 1].getPiece().getRow() == 3) {    // brani mimochodem doleva
                    enPassantPieceLeft = fieldBoard[oldFocusedPieceRow][oldFocusedPieceColumn - 1].getPiece();
                    enPassantPiece = enPassantPieceLeft;
                } else {
                    enPassantPiece = null;
                }
            }
            //======================================================= Brani mimochodem cernym pesakem =======================================================
            else {
                if (oldFocusedPieceColumn == 0
                        && fieldBoard[oldFocusedPieceRow][oldFocusedPieceColumn + 1].getPiece() != null
                        && fieldBoard[oldFocusedPieceRow][oldFocusedPieceColumn + 1].getPiece().isEnPassant()
                        && fieldBoard[oldFocusedPieceRow][oldFocusedPieceColumn + 1].getPiece().getRow() == 4) {   // brani mimochodem doprava bez sloupce 0
                    enPassantPieceRight = fieldBoard[oldFocusedPieceRow][oldFocusedPieceColumn + 1].getPiece();
                    enPassantPiece = enPassantPieceRight;
                } else if (oldFocusedPieceColumn == 7
                        && fieldBoard[oldFocusedPieceRow][oldFocusedPieceColumn - 1].getPiece() != null
                        && fieldBoard[oldFocusedPieceRow][oldFocusedPieceColumn - 1].getPiece().isEnPassant()
                        && fieldBoard[oldFocusedPieceRow][oldFocusedPieceColumn - 1].getPiece().getRow() == 4) {    // brani mimochodem doleva bez sloupce 7
                    enPassantPieceLeft = fieldBoard[oldFocusedPieceRow][oldFocusedPieceColumn - 1].getPiece();
                    enPassantPiece = enPassantPieceLeft;
                } else if (oldFocusedPieceColumn != 7
                        && fieldBoard[oldFocusedPieceRow][oldFocusedPieceColumn + 1].getPiece() != null
                        && fieldBoard[oldFocusedPieceRow][oldFocusedPieceColumn + 1].getPiece().isEnPassant()
                        && fieldBoard[oldFocusedPieceRow][oldFocusedPieceColumn + 1].getPiece().getRow() == 4) {    // brani mimochodem doprava
                    enPassantPieceRight = fieldBoard[oldFocusedPieceRow][oldFocusedPieceColumn + 1].getPiece();
                    enPassantPiece = enPassantPieceRight;
                } else if (oldFocusedPieceColumn != 0
                        && fieldBoard[oldFocusedPieceRow][oldFocusedPieceColumn - 1].getPiece() != null
                        && fieldBoard[oldFocusedPieceRow][oldFocusedPieceColumn - 1].getPiece().isEnPassant()
                        && fieldBoard[oldFocusedPieceRow][oldFocusedPieceColumn - 1].getPiece().getRow() == 4) {    // brani mimochodem doleva
                    enPassantPieceLeft = fieldBoard[oldFocusedPieceRow][oldFocusedPieceColumn - 1].getPiece();
                    enPassantPiece = enPassantPieceLeft;
                } else {
                    enPassantPiece = null;
                }
            }
        }

        if (enPassantPiece != null) {
            if (enPassantPiece.isEnPassant()) {
                if (enPassantPiece.isWhite() != piece.isWhite() && enPassantPiece.getColumn() == piece.getColumn()) {
                    eliminatedPieces.add(enPassantPiece);
                    enPassantPiece.getField().setPiece(null);
                    pawns.remove(enPassantPiece);
                    enPassantDone = true;
                }
            }
        }

        for (Pawn pawn : pawns) {
            if (pawn.isEnPassant()) {
                if (!pawn.wasEnPassantAlready()) {
                    pawn.setWasEnPassantAlready(true);
                } else if (pawn.wasEnPassantAlready()) {
                    pawn.setEnPassant(false);
                }
            }
        }

        for (APiece otherPiece : allPieces) {
            if (otherPiece.isPieceHit(sX, sY) && !otherPiece.equals(piece)) {
                if (otherPiece.isWhite() != isWhitePiece) {
                    eliminatedPieces.add(otherPiece);
                    if (pawns.contains(otherPiece) ) {
                        pawns.remove(otherPiece);
                    } else if (rooks.contains(otherPiece)) {
                        rooks.remove(otherPiece);
                    } else if (knights.contains(otherPiece)) {
                        knights.remove(otherPiece);
                    } else if (bishops.contains(otherPiece)) {
                        bishops.remove(otherPiece);
                    } else if (queens.contains(otherPiece)) {
                        queens.remove(otherPiece);
                    }
                    //System.out.println(otherPiece.getClass().getSimpleName() + " ELIMINATED");
                } else {
                    piece.setRow(oldFocusedPieceRow);
                    piece.setColumn(oldFocusedPieceColumn);
                    updatePiecesLocations(piece);
                }
            } else if (enPassantDone) {
                enPassantDone = false;
            }
        }

        Iterator<King> kingIterator = kings.iterator();
        while (kingIterator.hasNext()) {
            King king = kingIterator.next();

            if (king.isPieceHit(sX, sY) && !king.equals(piece)) {   // pokud je zasazen jiny pesak a zaroven neni roven sam sobe
                if (king.isWhite() != isWhitePiece) {
                    eliminatedPieces.add(king);

                    kingIterator.remove();
                    //System.out.println(king.getClass().getSimpleName() + " ELIMINATED");
                    gameOver(king);
                } else {
                    piece.setRow(oldFocusedPieceRow);
                    piece.setColumn(oldFocusedPieceColumn);
                    updatePiecesLocations(piece);
                }
            }
        }

    }

    /**
     * Metoda pro konec hry
     * Zobrazi dialogove okno s vysledkem hry + informace o hre
     *
     * @param eliminatedKing vyrazeny kral
     */
    private void gameOver(King eliminatedKing) {
        StringBuilder gameOverSB = new StringBuilder();

        if (isDraw()) {
            gameOverSB.append("Hra skoncila remizou!");
        } else if (eliminatedKing.isWhite()) {
            gameOverSB.append("Hra skoncila! Vitez: Cerny");
        } else {
            gameOverSB.append("Hra skoncila! Vitez: Bily");
        }

        JOptionPane gameOverPane = new JOptionPane(gameOverSB, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
        gameOverPane.setVisible(true);

        JButton newGameBTN = new JButton("Nova hra");
        gameOverPane.add(newGameBTN);

        JButton exitBTN = new JButton("Ukoncit program");
        gameOverPane.add(exitBTN);

        exitBTN.addActionListener(event -> System.exit(0));
        JDialog dialog = gameOverPane.createDialog(null, "Konec hry");
        newGameBTN.addActionListener(event -> {
            isFirstLoad = true;
            init();
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
        mouseMovedFunctionOn = false;
        if ((focusedPiece.isWhite() && whitesTurn) || (!focusedPiece.isWhite() && !whitesTurn)) {
            focusedPiece.setPieceColor(Color.RED);
            focusedPiece.moveTo(e.getX(), e.getY());
        } else {
            this.focusedPiece = null;
        }

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
        if ((focusedPiece.isWhite() && whitesTurn) || (!focusedPiece.isWhite() && !whitesTurn)) {

            int oldFocusedPieceRow = 0;
            int oldFocusedPieceColumn = 0;


            Rectangle focusedRectangle;

            for (int row = 0; row < 8; row++) {
                for (int column = 0; column < 8; column++) {

                    focusedRectangle = rectBoard[row][column];


                    if (e.getX() < rectBoard[0][0].getX() || e.getX() > (rectBoard[7][7].getX() + rectSize)) {   // osetreni pripadu, kdy by figurka byla "pustena" mimo sachovnici - delalo to blbosti, jako zachovani focusu u te posledni figurky misto aktualni
                        this.focusedPiece = null;   // obstara odebrani focusu po pusteni mysi
                    } else if (focusedRectangle.contains(e.getX(), e.getY())) {

                        oldFocusedPieceRow = focusedPiece.getRow();
                        oldFocusedPieceColumn = focusedPiece.getColumn();

                        startX = (int) rectBoard[oldFocusedPieceRow][oldFocusedPieceColumn].getX() + rectSize / 2;
                        startY = (int) rectBoard[oldFocusedPieceRow][oldFocusedPieceColumn].getY() + rectSize / 2;

                        if (focusedPiece.getClass().getSimpleName().equals("King") && Move.kingMove(focusedPiece, row, column, this, true)) {
                            validMove(focusedPiece, focusedRectangle, row, column, oldFocusedPieceRow, oldFocusedPieceColumn);
                            mouseWasReleased = true;
                            break;
//                            System.out.println("KING valid move passed (Row: " + row + "; Col: " + column);
                        } else if (getValidMoves(focusedPiece).contains(fieldBoard[row][column])) {
                            getValidMovesAndMakeOne(focusedPiece);
                            validMove(focusedPiece, focusedRectangle, row, column, oldFocusedPieceRow, oldFocusedPieceColumn);
//                            System.out.println("Valid move passed (Row: " + row + "; Col: " + column);
                            mouseWasReleased = true;
                        } else {
                            this.focusedPiece = null;   // obstara odebrani focusu po pusteni mysi
                            validMoves = null;
                        }
                    } else {
                        validMoves = null;
                        getValidMoves(focusedPiece);     // Tady se pouze ukazuji mozne pohyby, pohyb se ale neprovadi
//                        System.out.println("Oznacena figurka na pozici: Row - " + focusedPiece.getRow() + ", Col - " + focusedPiece.getColumn());
                    }
                }
                if (focusedPiece.isWhite()) {
                    focusedPiece.setPieceColor(Pawn.PIECE_WHITE);

                } else {
                    focusedPiece.setPieceColor(Pawn.PIECE_BLACK);
                }
                updatePiecesLocations(focusedPiece);
            }

        } else {
            this.focusedPiece = null;
        }
    }

    // pokud neni v sachu - true
    public List<Field> getCheckFields(APiece king) {

        checkFields = new ArrayList<>();
        if (king.isWhite()) {
            for (APiece piece : getAllPieces()) {
                if (!piece.isWhite() && !piece.getClass().getSimpleName().equals("King)")) {
                    getValidMoves(piece);
                    checkFields.addAll(validMoves);
                }
            }
        } else {
            for (APiece piece : getAllPieces()) {
                if (piece.isWhite() && !piece.getClass().getSimpleName().equals("King)")) {
                    getValidMoves(piece);
                    checkFields.addAll(validMoves);
                }
            }
        }
        return checkFields;
    }

    public List<Field> getValidMoves(APiece piece) {
        validMoves = new ArrayList<>();

        switch (piece.getClass().getSimpleName()) {
            case "Pawn" -> {
                for (int row = 0; row < 8; row++) {
                    for (int col = 0; col < 8; col++) {
                        if (Move.pawnMove(piece, row, col, this, false)) {
                            validMoves.add(fieldBoard[row][col]);
                        }
                    }
                }
                return validMoves;
            }
            case "Rook" -> {
                for (int row = 0; row < 8; row++) {
                    for (int col = 0; col < 8; col++) {
                        if (Move.rookMove(piece, row, col, this)) {
                            validMoves.add(fieldBoard[row][col]);
                        }
                    }
                }
                return validMoves;
            }
            case "Knight" -> {
                for (int row = 0; row < 8; row++) {
                    for (int col = 0; col < 8; col++) {
                        if (Move.knightMove(piece, row, col, this)) {
                            validMoves.add(fieldBoard[row][col]);
                        }
                    }
                }
                return validMoves;
            }
            case "Bishop" -> {
                for (int row = 0; row < 8; row++) {
                    for (int col = 0; col < 8; col++) {
                        if (Move.bishopMove(piece, row, col, this)) {
                            validMoves.add(fieldBoard[row][col]);
                        }
                    }
                }
                return validMoves;
            }
            case "Queen" -> {
                for (int row = 0; row < 8; row++) {
                    for (int col = 0; col < 8; col++) {
                        if (Move.queenMove(piece, row, col, this)) {
                            validMoves.add(fieldBoard[row][col]);
                        }
                    }
                }
                return validMoves;
            }
            case "King" -> {
                for (int row = 0; row < 8; row++) {
                    for (int col = 0; col < 8; col++) {
                        if (Move.kingMove(piece, row, col, this, false)) {
                            validMoves.add(fieldBoard[row][col]);
                        }
                    }
                }
                return validMoves;
            }
        }
        return validMoves;
    }

    public void getValidMovesAndMakeOne(APiece piece) {
        validMoves = new ArrayList<>();

        switch (piece.getClass().getSimpleName()) {
            case "Pawn" -> {
                for (int row = 0; row < 8; row++) {
                    for (int col = 0; col < 8; col++) {
                        if (Move.pawnMove(piece, row, col, this, true)) {
                            validMoves.add(fieldBoard[row][col]);
                        }
                    }
                }
            }
            case "Rook" -> {
                for (int row = 0; row < 8; row++) {
                    for (int col = 0; col < 8; col++) {
                        if (Move.rookMove(piece, row, col, this)) {
                            validMoves.add(fieldBoard[row][col]);
                        }
                    }
                }
            }
            case "Knight" -> {
                for (int row = 0; row < 8; row++) {
                    for (int col = 0; col < 8; col++) {
                        if (Move.knightMove(piece, row, col, this)) {
                            validMoves.add(fieldBoard[row][col]);
                        }
                    }
                }
            }
            case "Bishop" -> {
                for (int row = 0; row < 8; row++) {
                    for (int col = 0; col < 8; col++) {
                        if (Move.bishopMove(piece, row, col, this)) {
                            validMoves.add(fieldBoard[row][col]);
                        }
                    }
                }
            }
            case "Queen" -> {
                for (int row = 0; row < 8; row++) {
                    for (int col = 0; col < 8; col++) {
                        if (Move.queenMove(piece, row, col, this)) {
                            validMoves.add(fieldBoard[row][col]);
                        }
                    }
                }
            }
            case "King" -> {
                for (int row = 0; row < 8; row++) {
                    for (int col = 0; col < 8; col++) {
                        if (Move.kingMove(piece, row, col, this, true)) {
                            validMoves.add(fieldBoard[row][col]);
                        }
                    }
                }
            }
        }
    }

    /**
     * Provede jiz zvalidovany pohyb a korektne upravi vlastnosti figurek a poli, pripadne provede eliminaci jine figurky
     *
     * @param focusedPiece figurka, se kterou byl proveden pohyb
     * @param focusedRectangle ctverec na sachovnici, na kterou je figurka presouvana
     * @param row novy index radky
     * @param column novy index sloupce
     * @param oldFocusedPieceRow stary index radky
     * @param oldFocusedPieceColumn stary index sloupce
     */
    public void validMove(APiece focusedPiece, Rectangle focusedRectangle, int row, int column, int  oldFocusedPieceRow, int oldFocusedPieceColumn) {

        validMoves = null;
        focusedPiece.moveTo(
                focusedRectangle.getX() + getRectSize() / 2.0,
                focusedRectangle.getY() + getRectSize() / 2.0);
        focusedPiece.setRow(row);
        focusedPiece.setColumn(column);
        focusedPiece.setField(fieldBoard[row][column]);

        targetX = (int) focusedPiece.getsX();
        targetY = (int) focusedPiece.getsY();
        moveStep = 1;
        isMoving = true;

        fieldBoard[oldFocusedPieceRow][oldFocusedPieceColumn].setPiece(null);   // nastaveni stare bunky na prazdnou
        fieldUpdate(focusedPiece);
        focusedPiece.setMovedAlready(true);
        eliminate(focusedPiece, oldFocusedPieceRow, oldFocusedPieceColumn);

        lastMovedFrom = fieldBoard[oldFocusedPieceRow][oldFocusedPieceColumn];
        lastMovedTo = fieldBoard[focusedPiece.getRow()][focusedPiece.getColumn()];



        //========================================== Rosada doprava ==========================================
        if (focusedPiece.isCastling()) {
            if (castlingRook.getColumn() == 0) {

                castlingRookStartX = (int) (getRectBoard()[oldFocusedPieceRow][0].getX() + getRectSize() / 2.0);
                castlingRookStartY = (int) (getRectBoard()[oldFocusedPieceRow][0].getY() + getRectSize() / 2.0);

                castlingRook.moveTo(
                        getRectBoard()[oldFocusedPieceRow][3].getX() + getRectSize() / 2.0,
                        getRectBoard()[oldFocusedPieceRow][3].getY() + getRectSize() / 2.0);

                castlingRookTargetX = (int) (getRectBoard()[oldFocusedPieceRow][3].getX() + getRectSize() / 2.0);
                castlingRookTargetY = (int) (getRectBoard()[oldFocusedPieceRow][3].getY() + getRectSize() / 2.0);

                castlingRook.setRow(oldFocusedPieceRow);
                castlingRook.setColumn(3);
                castlingRook.setField(fieldBoard[oldFocusedPieceRow][3]);
                fieldBoard[castlingRook.getRow()][0].setPiece(null);   // nastaveni stare bunky na prazdnou
                fieldUpdate(castlingRook);
                castlingRook.setMovedAlready(true);
                focusedPiece.setCastling(false);
            } else if (castlingRook.getColumn() == 7) {

                castlingRookStartX = (int) (getRectBoard()[oldFocusedPieceRow][7].getX() + getRectSize() / 2.0);
                castlingRookStartY = (int) (getRectBoard()[oldFocusedPieceRow][7].getY() + getRectSize() / 2.0);

                castlingRook.moveTo(
                        getRectBoard()[oldFocusedPieceRow][5].getX() + getRectSize() / 2.0,
                        getRectBoard()[oldFocusedPieceRow][5].getY() + getRectSize() / 2.0);

                castlingRookTargetX = (int) (getRectBoard()[oldFocusedPieceRow][5].getX() + getRectSize() / 2.0);
                castlingRookTargetY = (int) (getRectBoard()[oldFocusedPieceRow][5].getY() + getRectSize() / 2.0);

                castlingRook.setRow(oldFocusedPieceRow);
                castlingRook.setColumn(5);
                castlingRook.setField(fieldBoard[oldFocusedPieceRow][5]);
                fieldBoard[castlingRook.getRow()][7].setPiece(null);   // nastaveni stare bunky na prazdnou
                fieldUpdate(castlingRook);
                castlingRook.setMovedAlready(true);
                focusedPiece.setCastling(false);
            }

        }
        if (isDraw()) {
            gameOver(null);
        }

        if (focusedPiece.isPromoted()) {
            whitesTurn = !whitesTurn;
            promotion(focusedPiece);
        } else {
            whitesTurn = !whitesTurn;
        }
    }

    /**
     * Testovaci metoda na remizu
     *
     * @return true, pokud je remiza, jinak false
     */
    private boolean isDraw() {
        // 2 kralove
        if (getAllPieces().size() == 2 && kings.size() == 2) {
            return true;
        } else if (getAllPieces().size() == 3
                && kings.size() == 2
                && (knights.size() == 1 || bishops.size() == 1)) {  // 2 kralove + strelec/jezdec
            return true;
        }
        return false;
    }

    /**
     * Aktualizuje info o poli u figurky a info o figurce u pole
     *
     * @param piece aktualizovana figurka
     */
    public void fieldUpdate(APiece piece) {
        piece.setField(fieldBoard[piece.getRow()][piece.getColumn()]);
        fieldBoard[piece.getRow()][piece.getColumn()].setPiece(piece);
        updatePiecesLocations(piece);
    }

    /**
     * Provede promenu pesce v kralovnu
     *
     * @param pawn promeneny pesec
     */
    private void promotion(APiece pawn) {
        int pawnRow = pawn.getRow();
        int pawnCol = pawn.getColumn();
        double pawnSx = pawn.getsX();
        double pawnSy = pawn.getsY();
        boolean pawnIsWhite = pawn.isWhite();

        Iterator<Pawn> pawnIterator = pawns.iterator();
        while (pawnIterator.hasNext()) {
            Pawn pawnTemp = pawnIterator.next();
            if (pawnTemp.equals(pawn)) {
                pawnIterator.remove();
            }
        }

        StringBuilder promotionSB = new StringBuilder();

        if (pawnIsWhite) {
            promotionSB.append("Promena bileho pesaka. Vyberte si figurku:");
        } else {
            promotionSB.append("Promena cerneho pesaka. Vyberte si figurku:");
        }

        JOptionPane gameOverPane = new JOptionPane(promotionSB, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
        gameOverPane.setVisible(true);

        JButton queenBTN = new JButton("Kralovna");
        gameOverPane.add(queenBTN);

        JButton rookBTN = new JButton("Vez");
        gameOverPane.add(rookBTN);

        JButton knightBTN = new JButton("Kun");
        gameOverPane.add(knightBTN);

        JButton bishopBTN = new JButton("Strelec");
        gameOverPane.add(bishopBTN);

        JDialog dialog = gameOverPane.createDialog(null, "Promena");
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);   // Disable tlacitka X, aby nedoslo na situaci, kdy hrac zavre nabidku promeny bez vyberu figurky
        queenBTN.addActionListener(event -> {
            Queen queen = new Queen(pawnSx, pawnSy, pawnIsWhite);
            queen.setRow(pawnRow);
            queen.setColumn(pawnCol);
            queens.add(queen);
            fieldUpdate(queen);
            dialog.setVisible(false);
        });


        rookBTN.addActionListener(event -> {
            Rook rook = new Rook(pawnSx, pawnSy, pawnIsWhite);
            rook.setRow(pawnRow);
            rook.setColumn(pawnCol);
            rooks.add(rook);
            fieldUpdate(rook);
            dialog.setVisible(false);
        });

        knightBTN.addActionListener(event -> {
            Knight knight = new Knight(pawnSx, pawnSy, pawnIsWhite);
            knight.setRow(pawnRow);
            knight.setColumn(pawnCol);
            knights.add(knight);
            fieldUpdate(knight);
            dialog.setVisible(false);
        });

        bishopBTN.addActionListener(event -> {
            Bishop bishop = new Bishop(pawnSx, pawnSy, pawnIsWhite);
            bishop.setRow(pawnRow);
            bishop.setColumn(pawnCol);
            bishops.add(bishop);
            fieldUpdate(bishop);
            dialog.setVisible(false);
        });


        dialog.setVisible(true);
    }

    //======================================== Gettery ========================================

    /**
     * @return aktualni stranu jednoho ctverce sachovnice
     */
    public int getRectSize() {
        return rectSize;
    }

    public Rectangle[][] getRectBoard() {
        return rectBoard;
    }

    public Field[][] getFieldBoard() {
        return fieldBoard;
    }

    public APiece getCastlingRook() {
        return castlingRook;
    }

    public void setCastlingRook(APiece castlingRook) {
        this.castlingRook = castlingRook;
    }

    public List<Double> getWhitePlayTimes() {
        return whitePlayTimes;
    }

    public List<Double> getBlackPlayTimes() {
        return blackPlayTimes;
    }
}