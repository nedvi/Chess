package Testing;

import javax.swing.*;
import java.awt.*;

public class ChessBoard0 extends JFrame {

    private JPanel chessBoardPanel;
    private JPanel[][] squares = new JPanel[8][8];
    private JLabel[][] figures = new JLabel[8][8];

    public ChessBoard0() {
        setTitle("Chess Board");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chessBoardPanel = new JPanel(new GridLayout(8, 8));
        chessBoardPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        getContentPane().add(chessBoardPanel);

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                squares[row][col] = new JPanel(new BorderLayout());
                chessBoardPanel.add(squares[row][col]);

                Color color = (row + col) % 2 == 0 ? Color.WHITE : Color.GRAY;
                squares[row][col].setBackground(color);

                if (row == 1) {
                    figures[row][col] = new JLabel(new ImageIcon("pawn_black.png"));
                    squares[row][col].add(figures[row][col]);
                } else if (row == 6) {
                    figures[row][col] = new JLabel(new ImageIcon("pawn_white.png"));
                    squares[row][col].add(figures[row][col]);
                } else if (row == 0 || row == 7) {
                    String type = "";
                    switch (col) {
                        case 0:
                        case 7:
                            type = "rook";
                            break;
                        case 1:
                        case 6:
                            type = "knight";
                            break;
                        case 2:
                        case 5:
                            type = "bishop";
                            break;
                        case 3:
                            type = "queen";
                            break;
                        case 4:
                            type = "king";
                            break;
                    }
                    figures[row][col] = new JLabel(new ImageIcon(type + (row == 0 ? "_black.png" : "_white.png")));
                    squares[row][col].add(figures[row][col]);
                }
            }
        }

        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        new ChessBoard0();
    }
}