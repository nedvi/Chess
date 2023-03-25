package ZabijuSe;

import javax.swing.*;

public abstract class APiece extends JPanel {

    private int row;
    private  int column;
    private boolean isWhite;

    public APiece(int row, int column, boolean isWhite) {
        this.row = row;
        this.column = column;
        this.isWhite = isWhite;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean isWhite() {
        return isWhite;
    }


}
