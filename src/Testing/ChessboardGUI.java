package Testing;

import javax.swing.*;
import java.awt.*;

public class ChessboardGUI extends JFrame {
    private JPanel chessBoardPanel;

    public ChessboardGUI() {
        super("Chessboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);

        chessBoardPanel = new JPanel(new GridLayout(8, 8));
        chessBoardPanel.setSize(600, 600);

        boolean white = true;
        for (int i = 0; i < 64; i++) {
            JPanel squarePanel = new JPanel(new BorderLayout());
            squarePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            if (white)
                squarePanel.setBackground(Color.WHITE);
            else
                squarePanel.setBackground(Color.GRAY);

            chessBoardPanel.add(squarePanel);

            if ((i + 1) % 8 != 0)
                white = !white;
        }

        getContentPane().add(chessBoardPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new ChessboardGUI();
    }
}