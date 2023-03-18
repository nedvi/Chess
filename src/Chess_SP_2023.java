import javax.swing.*;
import java.awt.*;

public class Chess_SP_2023 {

		// konstanty titulku okna
		private static final String MAIN_TITLE_STR = "UPG - Chess";
		// private static final Image TITLE_ICO = new Image("PATH/TO/ICON");

		public static void main(String[] args) {
			JFrame okno = new JFrame();
			okno.setTitle(MAIN_TITLE_STR);
			okno.setSize(800, 600);
			okno.setMinimumSize(new Dimension(800, 600));

			okno.add(new ChessBoard()); //prida komponentu
			okno.pack(); //udela resize okna dle komponent

			okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			okno.setLocationRelativeTo(null); //vycentrovat na obrazovce
			okno.setVisible(true);
		}

	}
