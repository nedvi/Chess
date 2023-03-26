import java.awt.*;
import java.awt.geom.Path2D;

public interface IPiece {
    void paint(Graphics g);
    Path2D createPiece();
    void paintPiece(Graphics2D g2);
    boolean isPieceHit(double x, double y);
    void moveTo(int x, int y);
    void setRectSize(int rectSize);
    int getRow();
    int getColumn();
    void setPieceColor(Color pieceColor);
    void setRow(int row);
    void setColumn(int column);
    boolean isWhite();
    boolean isOut();
    void setOut(boolean isOut);
    Field getField();
    void setField(Field field);
}
