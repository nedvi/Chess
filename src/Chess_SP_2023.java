import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Chess_SP_2023 {

		// konstanty titulku okna
		private static final String MAIN_TITLE_STR = "UPG - Chess";
		// private static final Image TITLE_ICO = new Image("PATH/TO/ICON");

		public static void main(String[] args) {
			JFrame okno = new JFrame();
			okno.setTitle(MAIN_TITLE_STR);
			okno.setSize(800, 600);
			okno.setMinimumSize(new Dimension(800, 600));

			ChessBoard chessBoard = new ChessBoard();

			chessBoard.addMouseMotionListener(new MouseMotionListener() {
				@Override
				public void mouseDragged(MouseEvent e) {

					if (chessBoard.contains(e.getX(), e.getY())) {
						chessBoard.mouseMoved(e, e.getX(), e.getY());
					}
					chessBoard.repaint();
				}

				@Override
				public void mouseMoved(MouseEvent e) {



					//System.out.printf("Mouse - X = %d; Y = %d;\n", e.getX(), e.getY());

//					for (int row = 1; row < 9; row++) {
//						for (int column = 1; column < 9; column++) {
//							Rectangle focusedField = chessBoard.getRectBoard()[row][column];
//
//
//							if (focusedField.contains(e.getX(), e.getY())) {
//								System.out.printf("Focused field: row = %d; column = %d [Mouse - X = %d; Y = %d;\n",
//										row, column, e.getX(), e.getY());
//
//								chessBoard.mouseMoved(e, (int) focusedField.getX(), (int) focusedField.getY());
//								chessBoard.repaint();
//							}
//						}
//					}
					//Rectangle focusedField = chessBoard.getRectBoard()[1][2];

				};
			});
			okno.add(chessBoard); //prida komponentu
			okno.pack(); //udela resize okna dle komponent

			okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			okno.setLocationRelativeTo(null); //vycentrovat na obrazovce
			okno.setVisible(true);


		}
}