package reengineering;

import javax.swing.*;
import java.awt.*;

public class StoneAnimation extends JPanel {
    private Point startPosition;
    private Point endPosition;
    private int step = 0;

    public StoneAnimation(Point start, Point end) {
        startPosition = start;
        endPosition = end;
        Timer timer = new Timer(50, (e) -> {
            if (step < 10) {
                step++;
                repaint();
            }
        });
        timer.setRepeats(true);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = (int) ((endPosition.x - startPosition.x) * step / 10.0 + startPosition.x);
        int y = (int) ((endPosition.y - startPosition.y) * step / 10.0 + startPosition.y);
        g.fillOval(x, y, 20, 20);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Stone Animation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        Point start = new Point(50, 50);
        Point end = new Point(250, 250);
        StoneAnimation animation = new StoneAnimation(start, end);
        frame.add(animation);
        frame.setVisible(true);
    }
}