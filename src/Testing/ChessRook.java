package Testing;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class ChessRook extends JPanel {

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // draw rook body
        g2.setColor(Color.WHITE);
        Rectangle2D.Double body = new Rectangle2D.Double(25, 50, 50, 100);
        g2.fill(body);
        g2.setColor(Color.BLACK);
        g2.draw(body);

        // draw rook head
        g2.setColor(Color.WHITE);
        Polygon head = new Polygon();
        head.addPoint(30, 40);
        head.addPoint(70, 40);
        head.addPoint(70, 50);
        head.addPoint(60, 50);
        head.addPoint(60, 60);
        head.addPoint(50, 60);
        head.addPoint(50, 50);
        head.addPoint(30, 50);
        g2.fill(head);
        g2.setColor(Color.BLACK);
        g2.draw(head);

        // draw rook details
        g2.setColor(Color.BLACK);
        Rectangle2D.Double details = new Rectangle2D.Double(30, 110, 40, 10);
        g2.fill(details);
        g2.draw(details);
        Line2D.Double line1 = new Line2D.Double(25, 50, 75, 50);
        g2.draw(line1);
        Line2D.Double line2 = new Line2D.Double(25, 150, 75, 150);
        g2.draw(line2);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.add(new ChessRook());
        frame.setSize(150, 200);
        frame.setVisible(true);
    }
}