package Testing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Chessboard extends JFrame implements MouseListener, MouseMotionListener {

    private JPanel board;
    private JLabel chessPiece;
    private int xAdjustment;
    private int yAdjustment;

    public Chessboard() {
        super("Chessboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        board = new JPanel(new GridLayout(8, 8));
        board.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        for (int i = 0; i < 64; i++) {
            JPanel square = new JPanel(new BorderLayout());
            board.add(square);
            int row = (i / 8) % 2;
            if (row == 0)
                square.setBackground(i % 2 == 0 ? Color.WHITE : Color.BLACK);
            else
                square.setBackground(i % 2 == 0 ? Color.BLACK : Color.WHITE);
        }
        add(board);

        // Add chess pieces
        addChessPiece("rook.png", 0);
        addChessPiece("src/Testing/knight.png", 1);
        addChessPiece("bishop.png", 2);
        addChessPiece("queen.png", 3);
        addChessPiece("king.png", 4);
        addChessPiece("bishop.png", 5);
        addChessPiece("knight.png", 6);
        addChessPiece("rook.png", 7);
        for (int i = 8; i < 16; i++) {
            addChessPiece("pawn.png", i);
        }

        board.addMouseListener(this);
        board.addMouseMotionListener(this);
        setVisible(true);
    }

    private void addChessPiece(String filename, int index) {
        JLabel piece = new JLabel(new ImageIcon(filename));
        JPanel square = (JPanel) board.getComponent(index);
        square.add(piece);
    }

    public void mousePressed(MouseEvent e) {
        chessPiece = null;
        Component c = board.findComponentAt(e.getX(), e.getY());
        if (c instanceof JPanel)
            return;
        Point parentLocation = c.getParent().getLocation();
        xAdjustment = parentLocation.x - e.getX();
        yAdjustment = parentLocation.y - e.getY();
        chessPiece = (JLabel) c;
        chessPiece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);
        chessPiece.setSize(chessPiece.getWidth(), chessPiece.getHeight());
        board.add(chessPiece, JLayeredPane.DRAG_LAYER);
        board.repaint();
    }

    public void mouseDragged(MouseEvent me) {
        if (chessPiece == null)
            return;
        chessPiece.setLocation(me.getX() + xAdjustment, me.getY() + yAdjustment);
    }

    public void mouseReleased(MouseEvent e) {
        if (chessPiece == null)
            return;
        chessPiece.setVisible(false);
        Component c = board.findComponentAt(e.getX(), e.getY());
        if (c instanceof JLabel) {
            Container parent = c.getParent();
            parent.remove(0);
            parent.add(chessPiece);
        } else {
            Container parent = (Container) c;
            parent.add(chessPiece);
        }
        chessPiece.setVisible(true);
        board.repaint();
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public static void main(String[] args) {
        new Chessboard();
    }
}
