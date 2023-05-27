import org.jfree.pdf.PDFDocument;
import org.jfree.pdf.PDFGraphics2D;
import org.jfree.pdf.PDFHints;
import org.jfree.pdf.Page;
import org.jfree.svg.SVGGraphics2D;
import org.jfree.svg.SVGUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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

	private static int pngScreenshotCount = 0;
	private static int pdfScreenshotCount = 0;
	private static int svgScreenshotCount = 0;

	/**
	 * Spousteci metoda programu
	 *
	 * @param args parametry prikazove radky
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setTitle(MAIN_TITLE_STR);
		frame.setSize(800, 600);
		frame.setMinimumSize(new Dimension(800, 600));
		frame.setLayout(new BorderLayout());

		JMenuBar menubar = new JMenuBar();

		JMenu graf = new JMenu("Graf");
		menubar.add(graf);

		JMenu exportMenu = new JMenu("Export");
		menubar.add(exportMenu);

		JMenuItem pngExportMI = new JMenuItem("PNG");
		exportMenu.add(pngExportMI);

		JMenuItem pdfExportMI = new JMenuItem("PDF");
		exportMenu.add(pdfExportMI);

		JMenuItem svgExportMI = new JMenuItem("SVG");
		exportMenu.add(svgExportMI);

		pngExportMI.addMouseListener(exportToPng(frame));
		pdfExportMI.addMouseListener(exportToPdf(frame));
		svgExportMI.addMouseListener(exportToSvg(frame));

		frame.add(menubar, BorderLayout.NORTH);

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
		}, 0, 10);

		frame.add(chessBoard); // prida komponentu
		frame.pack(); // udela resize okna dle komponent

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null); // vycentrovat na obrazovce
		frame.setVisible(true);
	}

	private static MouseListener exportToPng(JFrame frame) {
		return new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}
			@Override
			public void mousePressed(MouseEvent e) {

			}


			@Override
			public void mouseReleased(MouseEvent e) {
				frame.getGraphics();
				Container c = frame.getContentPane();
				BufferedImage im = new BufferedImage(c.getWidth(), c.getHeight(), BufferedImage.TYPE_INT_ARGB);
				c.paint(im.getGraphics());

				try {
					String fileName = String.format("Screenshot_%d.png", pngScreenshotCount);
					ImageIO.write(im, "PNG", new File(fileName));
					System.out.println("Export to PNG successful");
					pngScreenshotCount++;
				} catch (IOException ex) {
					throw new RuntimeException(ex);
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {

			}
		};
	}

	private static MouseListener exportToPdf(JFrame frame) {
		return new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				PDFDocument pdf = new PDFDocument();
				Container c = frame.getContentPane();
				Page page = pdf.createPage(new Rectangle(c.getWidth(), c.getHeight()));
				PDFGraphics2D g2 = page.getGraphics2D();
				g2.setRenderingHint(PDFHints.KEY_DRAW_STRING_TYPE, PDFHints.VALUE_DRAW_STRING_TYPE_VECTOR);
				frame.getContentPane().paint(g2);
				String fileName = String.format("Screenshot_%d.pdf", pdfScreenshotCount);
				pdf.writeToFile(new File(fileName));
				System.out.println("Export to PDF successful");
				pdfScreenshotCount++;
			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}
		};
	}

	private static MouseListener exportToSvg(JFrame frame) {
		return new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}
			@Override
			public void mousePressed(MouseEvent e) {

			}


			@Override
			public void mouseReleased(MouseEvent e) {
				Container c = frame.getContentPane();
				SVGGraphics2D g2 = new SVGGraphics2D(c.getWidth(), c.getHeight());
				c.paint(g2);
				String fileName = String.format("Screenshot_%d.svg",svgScreenshotCount);
				File f = new File(fileName);
				try {
					SVGUtils.writeToSVG(f, g2.getSVGElement());
					System.out.println("Export to SVG successful");
					svgScreenshotCount++;

				} catch (IOException ex) {
					System.err.println(ex);
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {

			}
		};
	}
}
