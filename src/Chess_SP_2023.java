import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.TimerTask;

/**
 * Spousteci trida semsestralni prace z predmetu KIV/UPG -> Sachy
 *
 * @author Dominik Nedved, A22B0109P
 * @version 07.05.2023
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

//						ActionListener timerAction = new ActionListener() {
//							public void actionPerformed(ActionEvent evt) {
//								if(chessBoard.isMoving) {
//									chessBoard.repaint();
//									System.out.println("Akce provedena");
//								}
//
//							}
//						};
//						Timer timer = new Timer(10, timerAction);
//						timer.setRepeats(true);
//						timer.start();




//						chessBoard.repaint();

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

		chessBoard.addComponentListener(new ComponentAdapter() {
			/**
			 * Zajistuje kontrolu resize okna a rozliseni.
			 * Jedna zajimavost - viz dokumentace
			 *
			 * @param e the event to be processed
			 */
			public void componentResized(ComponentEvent e) {
				chessBoard.repaint();
			}


		});

		java.util.Timer tm = new java.util.Timer();
		tm.schedule(new TimerTask() {
			@Override
			public void run() {
				chessBoard.repaint();
			}
		}, 0, 10);	// hodnoty v [ms] -> 50x za sekundu je naplanovano volani run()

		okno.add(chessBoard); // prida komponentu
		okno.pack(); // udela resize okna dle komponent

		okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		okno.setLocationRelativeTo(null); // vycentrovat na obrazovce
		okno.setVisible(true);
	}
}
