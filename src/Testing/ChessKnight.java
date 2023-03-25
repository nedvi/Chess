package Testing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.*;
import javax.swing.*;

public class ChessKnight extends JPanel implements MouseListener {
    private int x, y;

    public ChessKnight() {
        x = 50;
        y = 50;
        addMouseListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // enable anti-aliasing for smoother lines
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // set stroke width for all shapes
        g2.setStroke(new BasicStroke(2));

        // draw horse head
        Ellipse2D.Double head = new Ellipse2D.Double(x, y, 50, 50);
        g2.setPaint(new Color(181, 136, 99)); // set head color
        g2.fill(head); // fill head with color
        g2.setPaint(Color.BLACK); // reset paint color
        g2.draw(head);

        // draw horse body
        Rectangle2D.Double body = new Rectangle2D.Double(x + 10, y + 50, 30, 30);
        g2.setPaint(new Color(181, 136, 99)); // set body color
        g2.fill(body); // fill body with color
        g2.setPaint(Color.BLACK); // reset paint color
        g2.draw(body);

        // draw horse legs
        Line2D.Double frontLeg1 = new Line2D.Double(x + 10, y + 80, x + 5, y + 100);
        Line2D.Double frontLeg2 = new Line2D.Double(x + 40, y + 80, x + 45, y + 100);
        Line2D.Double backLeg1 = new Line2D.Double(x + 10, y + 100, x + 5, y + 120);
        Line2D.Double backLeg2 = new Line2D.Double(x + 40, y + 100, x + 45, y + 120);
        g2.draw(frontLeg1);
        g2.draw(frontLeg2);
        g2.draw(backLeg1);
        g2.draw(backLeg2);

        // draw horse hooves
        g2.setPaint(new Color(181, 136, 99)); // set hoof color
        g2.fill(new Ellipse2D.Double(x + 2, y + 118, 12, 12)); // front left
        g2.fill(new Ellipse2D.Double(x + 33, y + 118, 12, 12)); // front right
        g2.fill(new Ellipse2D.Double(x + 2, y + 138, 12, 12)); // back left
        g2.fill(new Ellipse2D.Double(x + 33, y + 138, 12, 12)); // back right
        g2.setPaint(Color.BLACK); // reset paint color

        // draw horse mane
        Arc2D.Double mane = new Arc2D.Double(x + 12, y + 10, 25, 35, 30, 120, Arc2D.OPEN);
        GeneralPath path = new GeneralPath(mane);
        path.lineTo(x + 24, y + 5);
        path.lineTo(x + 36, y + 5);
        path.append(new Arc2D.Double(x + 23, y + 10, 25, 35, -60, 120, Arc2D.OPEN), true);
        g2.draw(path);
        g2.draw(mane);

        // draw horse eye
        Ellipse2D.Double eye = new Ellipse2D.Double(x + 30, y + 15, 10, 10);
        g2.setPaint(Color.WHITE); // set eye color
        g2.fill(eye); // fill eye with color
        g2.setPaint(Color.BLACK); // reset paint color
        g2.draw(eye);

        // draw horse nostrils
        g2.draw(new Ellipse2D.Double(x + 15, y + 30, 5, 5));
        g2.draw(new Ellipse2D.Double(x + 30, y + 30, 5, 5));

        // draw horse ears
        g2.draw(new Line2D.Double(x + 22, y + 10, x + 10, y + 25));
        g2.draw(new Line2D.Double(x + 28, y + 10, x + 40, y + 25));

        // draw chess board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Rectangle2D.Double square = new Rectangle2D.Double(i * 50, j * 50, 50, 50);
                if ((i + j) % 2 == 0) {
                    g2.setPaint(new Color(255, 206, 158));
                } else {
                    g2.setPaint(new Color(209, 139, 71));
                }
                g2.fill(square);
                g2.setPaint(Color.BLACK);
                g2.draw(square);
            }
        }
    }

    public void mouseClicked(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        repaint();
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Chess Knight");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        ChessKnight knight = new ChessKnight();
        frame.add(knight);
        frame.setVisible(true);
    }
}