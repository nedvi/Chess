package reengineering;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Spousteci trida semsestralni prace z predmetu KIV/UPG -> Sachy
 *
 * @author Dominik Nedved, A22B0109P
 * @version 26.03.2023
 */
public class Chess_SP_2023 {

	/** Konstanta titulku okna */
	private static final String MAIN_TITLE_STR = "UPG - Chess SP, Dominik Nedved A22B0109P";

	/**
	 * Spousteci metoda programu
	 *
	 * @param args parametry prikazove radky
	 */
	public static void main(String[] args) {
		JFrame okno = new JFrame();
		okno.setTitle(MAIN_TITLE_STR);
		okno.setSize(800, 600);
		okno.setMinimumSize(new Dimension(800, 600));

		ChessBoard chessBoard = new ChessBoard();

		chessBoard.addMouseMotionListener(new MouseMotionListener() {

			/**
			 * Zajisteni funkci pri posunu mysi
			 *
			 * @param e the event to be processed
			 */
			@Override
			public void mouseDragged(MouseEvent e) {
				int mouseX = e.getX();
				int mouseY = e.getY();

				if (chessBoard.contains(mouseX, mouseY)) {
					APiece piece = chessBoard.getFocusedPiece(mouseX, mouseY);

					if (piece != null) {
						chessBoard.mouseDragged(e, piece);
						//piece.repaint();
						chessBoard.repaint();
					}
				}
			}

			@Override
			public void mouseMoved(MouseEvent e) {

			}
		});

		chessBoard.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			/**
			 * Zajisteni funkci pri pusteni tlacitka mysi
			 *
			 * @param e the event to be processed
			 */
			@Override
			public void mouseReleased(MouseEvent e) {
				int mouseX = e.getX();
				int mouseY = e.getY();

				if (chessBoard.contains(mouseX, mouseY)) {
					APiece piece = chessBoard.getFocusedPiece(mouseX, mouseY);
					if (piece != null) {
						chessBoard.mouseReleased(e, piece);
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

		okno.add(chessBoard); // prida komponentu
		okno.pack(); // udela resize okna dle komponent

		okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		okno.setLocationRelativeTo(null); // vycentrovat na obrazovce
		okno.setVisible(true);
	}
}
