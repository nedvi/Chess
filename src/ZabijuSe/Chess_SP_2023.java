package ZabijuSe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Chess_SP_2023 {
		private static final String MAIN_TITLE_STR = "UPG - Chess";

		public static void main(String[] args) {
			JFrame okno = new JFrame();
			okno.setTitle(MAIN_TITLE_STR);
			okno.setSize(800, 600);
			okno.setMinimumSize(new Dimension(800, 600));

			ChessBoard chessBoard = new ChessBoard();

			chessBoard.addMouseMotionListener(new MouseMotionListener() {
				@Override
				public void mouseDragged(MouseEvent e) {
					int mouseX = e.getX();
					int mouseY = e.getY();

					if (chessBoard.contains(mouseX, mouseY)) {
						Pawn focusedPawn = chessBoard.getFocusedPiece(mouseX, mouseY);
						if (focusedPawn != null) {
							chessBoard.mouseDragged(e, focusedPawn);
							focusedPawn.repaint();
							chessBoard.repaint();
						}
					}
				}

				@Override
				public void mouseMoved(MouseEvent e) {

				};
			});

			chessBoard.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {

				}

				@Override
				public void mousePressed(MouseEvent e) {

				}

				@Override
				public void mouseReleased(MouseEvent e) {
					int mouseX = e.getX();
					int mouseY = e.getY();

					if (chessBoard.contains(mouseX, mouseY)) {
						Pawn focusedPawn = chessBoard.getFocusedPiece(mouseX, mouseY);
						if (focusedPawn != null) {
							chessBoard.mouseReleased(e, focusedPawn);
							chessBoard.repaint();
						}
					}
				}

				@Override
				public void mouseEntered(MouseEvent e) {

				}

				@Override
				public void mouseExited(MouseEvent e) {

				}
			});

			okno.add(chessBoard); //prida komponentu
			okno.pack(); //udela resize okna dle komponent

			okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			okno.setLocationRelativeTo(null); //vycentrovat na obrazovce
			okno.setVisible(true);


		}
}
