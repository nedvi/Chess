package reengineering;

public class Move {
    public static boolean pawnMove(APiece pawn, int wantedRow, int wantedCol) {
        APiece enPassantPieceLeft = null;
        APiece enPassantPieceRight = null;
        if (pawn.getColumn() == 0) {
            enPassantPieceRight = ChessBoard.fieldBoard[pawn.getRow()][pawn.getColumn() + 1].getPiece();
        } else if (pawn.getColumn() == 7) {
            enPassantPieceLeft = ChessBoard.fieldBoard[pawn.getRow()][pawn.getColumn() - 1].getPiece();
        } else {
            enPassantPieceLeft = ChessBoard.fieldBoard[pawn.getRow()][pawn.getColumn() - 1].getPiece();
            enPassantPieceRight = ChessBoard.fieldBoard[pawn.getRow()][pawn.getColumn() + 1].getPiece();
        }
        if (pawn.isWhite()) {
            //TODO: Pro bile pesce
            //TODO: Naimplementovat brani mimochodem


            if ((enPassantPieceLeft != null && enPassantPieceLeft.isEnPassant() && !enPassantPieceLeft.isWhite() && enPassantPieceLeft.getColumn() != pawn.getColumn()) || (enPassantPieceRight != null && enPassantPieceRight.isEnPassant() && !enPassantPieceRight.isWhite() && enPassantPieceRight.getColumn() != pawn.getColumn())) {
                System.out.println("Prosla ta zpicena en passant podminka? - bily vyhazuje cernou");
                return true;
            }
            if (wantedRow == pawn.getRow() - 1 && ((wantedCol == pawn.getColumn() - 1 ) || (wantedCol == pawn.getColumn() + 1)) && ChessBoard.fieldBoard[wantedRow][wantedCol].getPiece() != null) {
                if (wantedRow == 0) {
                    ChessBoard.promotion = true;
                    System.out.println("Promotion = true");
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
                    System.out.println("Promotion = true");
                }
                return true;
            }

        } else {
            //TODO: Pro cerne pesce

            if ((enPassantPieceLeft != null && enPassantPieceLeft.isEnPassant() && enPassantPieceLeft.isWhite() && enPassantPieceLeft.getColumn() != pawn.getColumn()) || (enPassantPieceRight != null && enPassantPieceRight.isEnPassant() && enPassantPieceRight.isWhite() && enPassantPieceRight.getColumn() != pawn.getColumn())) {
                System.out.println("Prosla ta zpicena podminka? - cernej vyhazuje bilou");
                return true;
            }
            if (wantedRow == pawn.getRow() + 1 && ((wantedCol == pawn.getColumn() - 1 ) || (wantedCol == pawn.getColumn() + 1)) && ChessBoard.fieldBoard[wantedRow][wantedCol].getPiece() != null) {
                if (wantedRow == 7) {
                    ChessBoard.promotion = true;
                    System.out.println("Promotion = true");
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
                if (wantedRow == 0) {
                    ChessBoard.promotion = true;
                    System.out.println("Promotion = true");
                }
                return true;
            }
        }
        return false;
    }

    public static boolean rookMove(APiece rook, int wantedRow, int wantedCol) {
        if (rook.isWhite()) {
            //TODO: Pro bile veze
            //TODO: Naimplementovat rosadu
            if (rook.getRow() == wantedRow || rook.getColumn() == wantedCol) {  // zaklad

                int sumOfSteps = 0;

                int tempRow = rook.getRow();
                int[] rowIndexesInWayArray = null;

                int tempCol = rook.getColumn();
                int[] colIndexesInWayArray = null;

                // pro pohyb nahoru a dolu
                if(rook.getColumn() == wantedCol) {
                    // pro pohyb bilych nahoru
                    if (wantedRow - tempRow < 0) {
                        sumOfSteps = Math.abs(wantedRow - tempRow);
                        System.out.println("Sum of steps nahoru: " + sumOfSteps);
                        rowIndexesInWayArray = new int[sumOfSteps];
                        tempRow--;
                        for (int i = 1; i <= sumOfSteps; i++) {
                            rowIndexesInWayArray[i - 1] = tempRow--;
                            System.out.println("Rook index " + (i - 1) + ": " + rowIndexesInWayArray[i - 1]);
                        }
                    } else if (wantedRow - tempRow > 0) {   // pro pohyb bilych dolu
                        sumOfSteps = Math.abs(wantedRow - tempRow);
                        System.out.println("Sum of steps dolu: " + sumOfSteps);
                        rowIndexesInWayArray = new int[sumOfSteps];
                        tempRow++;
                        for (int i = 1; i <= sumOfSteps; i++) {
                            rowIndexesInWayArray[i - 1] = tempRow++;
                            System.out.println("Rook index " + (i - 1) + ": " + rowIndexesInWayArray[i - 1]);
                        }
                    }

                    for (int i = 0; i < sumOfSteps; i++) {
                        APiece pieceInWay = ChessBoard.fieldBoard[rowIndexesInWayArray[i]][wantedCol].getPiece();
                        if (pieceInWay != null) {
                            if(i == sumOfSteps-1) {
                                return !pieceInWay.isWhite();
                            }
                            return false;
                        }
                    }
                } else {    // Pro pohyb doleva a doprava
                    // pro pohyb bilych doleva
                    if (wantedCol - tempCol < 0) {
                        sumOfSteps = Math.abs(wantedCol - tempCol);
                        System.out.println("Sum of steps doleva: " + sumOfSteps);
                        colIndexesInWayArray = new int[sumOfSteps];
                        tempCol--;
                        for (int i = 1; i <= sumOfSteps; i++) {
                            colIndexesInWayArray[i - 1] = tempCol--;
                            System.out.println("Rook index " + (i - 1) + ": " + colIndexesInWayArray[i - 1]);
                        }
                    } else if (wantedCol - tempCol > 0) {   // pro pohyb bilych doprava
                        sumOfSteps = Math.abs(wantedCol - tempCol);
                        System.out.println("Sum of steps doprava: " + sumOfSteps);
                        colIndexesInWayArray = new int[sumOfSteps];
                        tempCol++;
                        for (int i = 1; i <= sumOfSteps; i++) {
                            colIndexesInWayArray[i - 1] = tempCol++;
                            System.out.println("Rook index " + (i - 1) + ": " + colIndexesInWayArray[i - 1]);
                        }
                    }

                    for (int i = 0; i < sumOfSteps; i++) {
                        APiece pieceInWay = ChessBoard.fieldBoard[wantedRow][colIndexesInWayArray[i]].getPiece();
                        if (pieceInWay != null) {
                            if (i == sumOfSteps - 1) {
                                return !pieceInWay.isWhite();
                            }
                            return false;
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    public static boolean bishopMove(APiece bishop, int wantedRow, int wantedCol) {
        int tempRow = bishop.getRow();
        int tempCol = bishop.getColumn();

        int[] rowIndexesInWayArray = null;
        int[] colIndexesInWayArray = null;

        int sumOfRowSteps = Math.abs(wantedRow - tempRow);
        int sumOfColSteps = Math.abs(wantedCol - tempCol);

        //TODO: Pro bile strelce
        if (bishop.isWhite()) {

            if (tempRow != wantedRow && tempCol != wantedCol && sumOfRowSteps == sumOfColSteps) {  // zaklad
                System.out.println("Sum of Bishop steps : " + sumOfRowSteps);
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
                            System.out.println("Bishop - row index " + (i - 1) + ": " + rowIndexesInWayArray[i - 1] + "; col index " + (i - 1) + ": " + colIndexesInWayArray[i - 1]);
                        }
                    } else if (directionHorizontal > 0) {   // doprava
                        tempCol++;
                        for (int i = 1; i <= sumOfRowSteps; i++) {
                            rowIndexesInWayArray[i - 1] = tempRow--;
                            colIndexesInWayArray[i - 1] = tempCol++;
                            System.out.println("Bishop - row index " + (i - 1) + ": " + rowIndexesInWayArray[i - 1] + "; col index " + (i - 1) + ": " + colIndexesInWayArray[i - 1]);
                        }
                    }
                } else if (directionVertical > 0) {     // dolu
                    tempRow++;
                    if (directionHorizontal < 0) {  // doleva
                        tempCol--;
                        for (int i = 1; i <= sumOfRowSteps; i++) {
                            rowIndexesInWayArray[i - 1] = tempRow++;
                            colIndexesInWayArray[i - 1] = tempCol--;
                            System.out.println("Bishop - row index " + (i - 1) + ": " + rowIndexesInWayArray[i - 1] + "; col index " + (i - 1) + ": " + colIndexesInWayArray[i - 1]);
                        }
                    } else if (directionHorizontal > 0) {   // doprava
                        tempCol++;
                        for (int i = 1; i <= sumOfRowSteps; i++) {
                            rowIndexesInWayArray[i - 1] = tempRow++;
                            colIndexesInWayArray[i - 1] = tempCol++;
                            System.out.println("Bishop - row index " + (i - 1) + ": " + rowIndexesInWayArray[i - 1] + "; col index " + (i - 1) + ": " + colIndexesInWayArray[i - 1]);
                        }
                    }
                }

                for (int i = 0; i < sumOfRowSteps; i++) {
                    APiece pieceInWay = ChessBoard.fieldBoard[rowIndexesInWayArray[i]][colIndexesInWayArray[i]].getPiece();
                    if (pieceInWay != null) {
                        if (i == sumOfRowSteps-1) {
                            return !pieceInWay.isWhite();
                        }
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public static boolean knightMove(APiece knight, int wantedRow, int wantedCol) {
        int tempRow = knight.getRow();
        int tempCol = knight.getColumn();

        //TODO: Pro bile kone
        if (knight.isWhite()) {
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
                    return !pieceInWay.isWhite();
                }
                return true;
            }
        }
        return false;
    }

    public static boolean queenMove(APiece knight, int wantedRow, int wantedCol) {
        if (rookMove(knight, wantedRow, wantedCol)) {
            return true;
        } else return bishopMove(knight, wantedRow, wantedCol);
    }

    public static boolean kingMove(APiece king, int wantedRow, int wantedCol) {
        int tempRow = king.getRow();
        int tempCol = king.getColumn();
        if (king.isWhite()) {
            boolean passed = false;
            if (wantedRow == tempRow - 1 || wantedRow == tempRow || wantedRow == tempRow + 1) {
                if (wantedCol == tempCol - 1 || wantedCol == tempCol || wantedCol == tempCol + 1) {
                    passed = true;
                }
            }
            if (passed) {
                APiece pieceInWay = ChessBoard.fieldBoard[wantedRow][wantedCol].getPiece();
                if (pieceInWay != null) {
                    return !pieceInWay.isWhite();
                }
                return true;
            }
        }
        return false;
    }
}