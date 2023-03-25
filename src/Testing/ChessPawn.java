package Testing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ChessPawn extends JPanel {
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    private static final int PAWN_SIZE = 60;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Set rendering hints for antialiasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Create a Path2D shape for the pawn
        Path2D pawnShape = new Path2D.Double();
        int x = (WIDTH - PAWN_SIZE) / 2;
        int y = (HEIGHT - PAWN_SIZE) / 2;
        int topHeight = PAWN_SIZE / 4;
        int bottomHeight = PAWN_SIZE - topHeight;
        int midWidth = PAWN_SIZE / 2;
        int sideWidth = (PAWN_SIZE - midWidth) / 2;
        int bottomWidth = PAWN_SIZE - 2 * sideWidth;

        // Draw the top half of the pawn
        pawnShape.moveTo(x, y + topHeight);
        pawnShape.lineTo(x + sideWidth, y);
        pawnShape.lineTo(x + midWidth, y + topHeight);
        pawnShape.lineTo(x + midWidth, y);
        pawnShape.lineTo(x + midWidth + sideWidth, y);
        pawnShape.lineTo(x + bottomWidth, y + topHeight);
        pawnShape.lineTo(x + sideWidth, y + topHeight);
        pawnShape.closePath();

        // Draw the bottom half of the pawn
        pawnShape.moveTo(x + sideWidth, y + topHeight);
        pawnShape.lineTo(x + sideWidth, y + bottomHeight);
        pawnShape.lineTo(x + bottomWidth, y + bottomHeight);
        pawnShape.lineTo(x + bottomWidth, y + topHeight);
        pawnShape.closePath();

        // Fill the pawn shape with black color
        g2d.setColor(Color.BLACK);
        g2d.fill(pawnShape);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Chess Pawn");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        ChessPawn chessPawn = new ChessPawn();
        frame.add(chessPawn);
        frame.setVisible(true);
    }
}