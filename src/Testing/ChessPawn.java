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
    private static final int RADIUS = PAWN_SIZE / 2;
    private static final int X_CENTER = WIDTH / 2;
    private static final int Y_CENTER = HEIGHT / 2;
    private static final int Y_OFFSET = 5;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Set rendering hints for antialiasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Define the pawn shape using a Path2D object
        Path2D.Double pawnShape = new Path2D.Double();
        pawnShape.moveTo(X_CENTER, Y_CENTER + RADIUS);
        pawnShape.lineTo(X_CENTER + RADIUS, Y_CENTER + RADIUS);
        pawnShape.curveTo(X_CENTER + RADIUS * 0.5, Y_CENTER - RADIUS * 0.8, X_CENTER - RADIUS * 0.5, Y_CENTER - RADIUS * 0.8, X_CENTER - RADIUS, Y_CENTER + RADIUS);
        pawnShape.closePath();

        // Draw the pawn body as a filled shape
        g2d.setColor(Color.WHITE);
        g2d.fill(pawnShape);

        // Draw the pawn outline
        g2d.setColor(Color.BLACK);
        g2d.draw(pawnShape);

        // Draw the pawn head as a circle
        int headSize = PAWN_SIZE / 3;
        int headX = X_CENTER - headSize / 2;
        int headY = Y_CENTER - RADIUS;
        g2d.fillOval(headX, headY, headSize, headSize);
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