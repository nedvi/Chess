package ZabijuSe;

import java.awt.*;
import java.awt.geom.Path2D;

public interface IPiece {
    void paint(Graphics g);
    Path2D createPiece();
    void paintPiece(Graphics2D g2);
    boolean isPieceHit(double x, double y);
    void moveTo(int x, int y);
    public void setRectSize(int rectSize);
}
