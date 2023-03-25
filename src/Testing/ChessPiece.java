package Testing;

public abstract class ChessPiece {
    protected int row;
    protected int col;
    protected boolean isWhite;

    public ChessPiece(int row, int col, boolean isWhite) {
        this.row = row;
        this.col = col;
        this.isWhite = isWhite;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public abstract boolean isValidMove(int toRow, int toCol, ChessPiece[][] board);

    public abstract String getSymbol();
}