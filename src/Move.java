/**
 * Trida reprezentujici validator pohybu figurek
 *
 * @author Dominik Nedved, A22B0109P
 * @version 07.05.2023
 */
public class Move {

    /**
     * Osetruje validni pohyb pesaka, a to vcetne brani mimochodem a promeny za kralovnu
     *
     * @param pawn pesak, se kterym chce hrac pohybovat
     * @param wantedRow pozadovany index radky
     * @param wantedCol pozadovany index sloupce
     * @return true, pokud je pohyb validni, jinak false
     */
    public static boolean pawnMove(APiece pawn, int wantedRow, int wantedCol) {
        APiece enPassantPieceLeft = null;
        APiece enPassantPieceRight = null;

        if (pawn.getColumn() == 0) {
            enPassantPieceRight = ChessBoard.fieldBoard[pawn.getRow()][pawn.getColumn() + 1].getPiece();
        } else if (pawn.getColumn() == 7) {
            enPassantPieceLeft = ChessBoard.fieldBoard[pawn.getRow()][pawn.getColumn() - 1].getPiece();
        } else {
            enPassantPieceRight = ChessBoard.fieldBoard[pawn.getRow()][pawn.getColumn() + 1].getPiece();
            enPassantPieceLeft = ChessBoard.fieldBoard[pawn.getRow()][pawn.getColumn() - 1].getPiece();

        }

        if (pawn.isWhite()) {       // Pro bile pesce
            if ((enPassantPieceLeft != null && enPassantPieceLeft.isEnPassant() && !enPassantPieceLeft.isWhite() && enPassantPieceLeft.getColumn() != pawn.getColumn())
                    || (enPassantPieceRight != null && enPassantPieceRight.isEnPassant() && !enPassantPieceRight.isWhite() && enPassantPieceRight.getColumn() != pawn.getColumn())) {
                return true;
            }
            if (wantedRow == pawn.getRow() - 1 && ((wantedCol == pawn.getColumn() - 1 ) || (wantedCol == pawn.getColumn() + 1)) && ChessBoard.fieldBoard[wantedRow][wantedCol].getPiece() != null) {
                if (wantedRow == 0) {
                    ChessBoard.promotion = true;
                }
                return true;
            }
            if (pawn.getRow() == 6) {   // Kdyz je pesec na sve startovaci pozici
                if (wantedRow < pawn.getRow() && wantedRow >= pawn.getRow() - 2 && pawn.getColumn() == wantedCol && ChessBoard.fieldBoard[wantedRow][wantedCol].getPiece() == null) {
                    if (wantedRow == pawn.getRow() - 2) {
                        pawn.setEnPassant(true);
                    }
                    return true;
                }
            } else if (wantedRow < pawn.getRow() && wantedRow == pawn.getRow() - 1 && pawn.getColumn() == wantedCol && ChessBoard.fieldBoard[wantedRow][wantedCol].getPiece() == null) {
                if (wantedRow == 0) {
                    ChessBoard.promotion = true;
                }
                return true;
            }
        } else {        // Pro cerne pesce
            if ((enPassantPieceLeft != null && enPassantPieceLeft.isEnPassant() && enPassantPieceLeft.isWhite() && enPassantPieceLeft.getColumn() != pawn.getColumn()) || (enPassantPieceRight != null && enPassantPieceRight.isEnPassant() && enPassantPieceRight.isWhite() && enPassantPieceRight.getColumn() != pawn.getColumn())) {
                return true;
            }
            if (wantedRow == pawn.getRow() + 1 && ((wantedCol == pawn.getColumn() - 1 ) || (wantedCol == pawn.getColumn() + 1)) && ChessBoard.fieldBoard[wantedRow][wantedCol].getPiece() != null) {
                if (wantedRow == 7) {
                    ChessBoard.promotion = true;
                }
                return true;
            }
            if (pawn.getRow() == 1) {   // Kdyz je pesec na sve startovaci pozici
                if (wantedRow > pawn.getRow() && wantedRow <= pawn.getRow() + 2 && pawn.getColumn() == wantedCol && ChessBoard.fieldBoard[wantedRow][wantedCol].getPiece() == null) {
                    if (wantedRow == pawn.getRow() + 2) {
                        pawn.setEnPassant(true);
                    }
                    return true;
                }
            } else if (wantedRow > pawn.getRow() && wantedRow == pawn.getRow() + 1 && pawn.getColumn() == wantedCol && ChessBoard.fieldBoard[wantedRow][wantedCol].getPiece() == null) {
                if (wantedRow == 7) {
                    ChessBoard.promotion = true;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Osetruje validni pohyb veze
     *
     * @param rook vez, se kterou chce hrac pohybovat
     * @param wantedRow pozadovany index radky
     * @param wantedCol pozadovany index sloupce
     * @return true, pokud je pohyb validni, jinak false
     */
    public static boolean rookMove(APiece rook, int wantedRow, int wantedCol) {
        if (rook.getRow() == wantedRow || rook.getColumn() == wantedCol) {  // zaklad
            int sumOfSteps = 0;
            int tempRow = rook.getRow();
            int[] rowIndexesInWayArray = null;
            int tempCol = rook.getColumn();
            int[] colIndexesInWayArray = null;

            if (tempRow == wantedRow && tempCol == wantedCol) {
                return false;
            }

            // pro pohyb nahoru a dolu
            if(rook.getColumn() == wantedCol) {
                if (wantedRow - tempRow < 0) {
                    sumOfSteps = Math.abs(wantedRow - tempRow);
                    rowIndexesInWayArray = new int[sumOfSteps];
                    tempRow--;
                    for (int i = 1; i <= sumOfSteps; i++) {
                        rowIndexesInWayArray[i - 1] = tempRow--;
                    }
                } else if (wantedRow - tempRow > 0) {   // pro pohyb bilych dolu
                    sumOfSteps = Math.abs(wantedRow - tempRow);
                    rowIndexesInWayArray = new int[sumOfSteps];
                    tempRow++;
                    for (int i = 1; i <= sumOfSteps; i++) {
                        rowIndexesInWayArray[i - 1] = tempRow++;
                    }
                }

                for (int i = 0; i < sumOfSteps; i++) {
                    APiece pieceInWay = ChessBoard.fieldBoard[rowIndexesInWayArray[i]][wantedCol].getPiece();
                    if (pieceInWay != null) {
                        if (i == sumOfSteps-1) {
                            return rook.isWhite() != pieceInWay.isWhite();
                        }
                        return false;
                    }
                }
            } else {    // Pro pohyb doleva a doprava
                if (wantedCol - tempCol < 0) {
                    sumOfSteps = Math.abs(wantedCol - tempCol);
                    colIndexesInWayArray = new int[sumOfSteps];
                    tempCol--;
                    for (int i = 1; i <= sumOfSteps; i++) {
                        colIndexesInWayArray[i - 1] = tempCol--;
                    }
                } else if (wantedCol - tempCol > 0) {   // pro pohyb bilych doprava
                    sumOfSteps = Math.abs(wantedCol - tempCol);
                    colIndexesInWayArray = new int[sumOfSteps];
                    tempCol++;
                    for (int i = 1; i <= sumOfSteps; i++) {
                        colIndexesInWayArray[i - 1] = tempCol++;
                    }
                }

                for (int i = 0; i < sumOfSteps; i++) {
                    APiece pieceInWay = ChessBoard.fieldBoard[wantedRow][colIndexesInWayArray[i]].getPiece();
                    if (pieceInWay != null) {
                        if (i == sumOfSteps - 1) {
                            return rook.isWhite() != pieceInWay.isWhite();
                        }
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Osetruje validni pohyb strelce
     *
     * @param bishop strelec, se kterym chce hrac pohybovat
     * @param wantedRow pozadovany index radky
     * @param wantedCol pozadovany index sloupce
     * @return true, pokud je pohyb validni, jinak false
     */
    public static boolean bishopMove(APiece bishop, int wantedRow, int wantedCol) {
        int tempRow = bishop.getRow();
        int tempCol = bishop.getColumn();

        int[] rowIndexesInWayArray;
        int[] colIndexesInWayArray;

        int sumOfRowSteps = Math.abs(wantedRow - tempRow);
        int sumOfColSteps = Math.abs(wantedCol - tempCol);
        if (tempRow != wantedRow && tempCol != wantedCol && sumOfRowSteps == sumOfColSteps) {  // zaklad
            rowIndexesInWayArray = new int[sumOfRowSteps];
            colIndexesInWayArray = new int[sumOfColSteps];

            int directionVertical = wantedRow - tempRow;
            int directionHorizontal = wantedCol - tempCol;

            if (directionVertical < 0) {     // nahoru
                tempRow--;
                if (directionHorizontal < 0) {  // doleva
                    tempCol--;
                    for (int i = 1; i <= sumOfRowSteps; i++) {
                        rowIndexesInWayArray[i - 1] = tempRow--;
                        colIndexesInWayArray[i - 1] = tempCol--;
                    }
                } else {   // doprava
                    tempCol++;
                    for (int i = 1; i <= sumOfRowSteps; i++) {
                        rowIndexesInWayArray[i - 1] = tempRow--;
                        colIndexesInWayArray[i - 1] = tempCol++;
                    }
                }
            } else {     // dolu
                tempRow++;
                if (directionHorizontal < 0) {  // doleva
                    tempCol--;
                    for (int i = 1; i <= sumOfRowSteps; i++) {
                        rowIndexesInWayArray[i - 1] = tempRow++;
                        colIndexesInWayArray[i - 1] = tempCol--;
                    }
                } else {   // doprava
                    tempCol++;
                    for (int i = 1; i <= sumOfRowSteps; i++) {
                        rowIndexesInWayArray[i - 1] = tempRow++;
                        colIndexesInWayArray[i - 1] = tempCol++;
                    }
                }
            }

            for (int i = 0; i < sumOfRowSteps; i++) {
                APiece pieceInWay = ChessBoard.fieldBoard[rowIndexesInWayArray[i]][colIndexesInWayArray[i]].getPiece();
                if (pieceInWay != null) {
                    if (i == sumOfRowSteps-1) {
                        return bishop.isWhite() != pieceInWay.isWhite();
                    }
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Osetruje validni pohyb kone
     *
     * @param knight kun, se kterym chce hrac pohybovat
     * @param wantedRow pozadovany index radky
     * @param wantedCol pozadovany index sloupce
     * @return true, pokud je pohyb validni, jinak false
     */
    public static boolean knightMove(APiece knight, int wantedRow, int wantedCol) {
        int tempRow = knight.getRow();
        int tempCol = knight.getColumn();

        boolean passed = false;

        // kratsi kroky u row, delsi u col
        if ((wantedRow == tempRow + 1 && wantedCol == tempCol + 2) || (wantedRow == tempRow + 1 && wantedCol == tempCol - 2) || (wantedRow == tempRow - 1 && wantedCol == tempCol + 2) || (wantedRow == tempRow - 1 && wantedCol == tempCol - 2)) {
            passed = true;
        }
        // delsi kroky u row, kratsi u col
        else if ((wantedRow == tempRow + 2 && wantedCol == tempCol + 1) || (wantedRow == tempRow + 2 && wantedCol == tempCol - 1) || (wantedRow == tempRow - 2 && wantedCol == tempCol + 1) || (wantedRow == tempRow - 2 && wantedCol == tempCol - 1)) {
            passed = true;
        }
        if (passed) {
            APiece pieceInWay = ChessBoard.fieldBoard[wantedRow][wantedCol].getPiece();
            if (pieceInWay != null) {
                return knight.isWhite() != pieceInWay.isWhite();
            }
            return true;
        }
        return false;
    }

    /**
     * Osetruje validni pohyb kralovny
     *
     * @param queen vez, se kterou chce hrac pohybovat
     * @param wantedRow pozadovany index radky
     * @param wantedCol pozadovany index sloupce
     * @return true, pokud je pohyb validni, jinak false
     */
    public static boolean queenMove(APiece queen, int wantedRow, int wantedCol) {
        if (rookMove(queen, wantedRow, wantedCol)) {
            return true;
        } else return bishopMove(queen, wantedRow, wantedCol);
    }

    /**
     * Osetruje validni pohyb krale
     *
     * @param king vez, se kterou chce hrac pohybovat
     * @param wantedRow pozadovany index radky
     * @param wantedCol pozadovany index sloupce
     * @return true, pokud je pohyb validni, jinak false
     */
    public static boolean kingMove(APiece king, int wantedRow, int wantedCol) {
        int tempRow = king.getRow();
        int tempCol = king.getColumn();
        boolean passed = false;
        if (wantedRow == tempRow - 1 || wantedRow == tempRow || wantedRow == tempRow + 1) {
            if (wantedCol == tempCol - 1 || wantedCol == tempCol || wantedCol == tempCol + 1) {
                passed = true;
            }
        }
        if (passed) {
            APiece pieceInWay = ChessBoard.fieldBoard[wantedRow][wantedCol].getPiece();
            if (pieceInWay != null) {
                return king.isWhite() != pieceInWay.isWhite();
            }
            return true;
        }
        return false;
    }
}