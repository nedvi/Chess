import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.pdf.PDFDocument;
import org.jfree.pdf.PDFGraphics2D;
import org.jfree.pdf.PDFHints;
import org.jfree.pdf.Page;
import org.jfree.svg.SVGGraphics2D;
import org.jfree.svg.SVGUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.TimerTask;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

	private static JFrame frame;
	private static ChessBoard chessBoard;
	private static JPanel graphPanel;

	private static XYSeries player1Series;
	private static XYSeries player2Series;

	/**
	 * Spousteci metoda programu
	 *
	 * @param args parametry prikazove radky
	 */
	public static void main(String[] args) {
		frame = new JFrame();
		frame.setTitle(MAIN_TITLE_STR);
		frame.setSize(800, 600);
		frame.setMinimumSize(new Dimension(800, 600));
		frame.setLayout(new BorderLayout());

		chessBoard = new ChessBoard();

		JMenuBar menubar = new JMenuBar();

		JMenu game = new JMenu("Hra");
		menubar.add(game);

		JMenuItem restart = new JMenuItem("Restart");
		game.add(restart);
		restart.addActionListener(event -> {
			chessBoard.setFirstLoad(true);
			chessBoard.init();
		});

		JMenuItem draw = new JMenuItem("Remiza");
		game.add(draw);
		draw.addActionListener(event -> {
			chessBoard.setDraw(true);
		});

		//=============================== Graf menu ===============================
		JMenu content = new JMenu("Zobrazit");
		menubar.add(content);

		JMenuItem showGraph = new JMenuItem("Graf");
		content.add(showGraph);
		showGraph.addActionListener(event -> getGraph());

		JMenuItem showChessBoard = new JMenuItem("Sachovnici");
		content.add(showChessBoard);
		showChessBoard.addActionListener(event -> getChessBoard());

		//=============================== Export menu ===============================
		JMenu exportMenu = new JMenu("Export");
		menubar.add(exportMenu);

		JMenuItem pngExportMI = new JMenuItem("PNG");
		exportMenu.add(pngExportMI);

		JMenuItem pdfExportMI = new JMenuItem("PDF");
		exportMenu.add(pdfExportMI);

		JMenuItem svgExportMI = new JMenuItem("SVG");
		exportMenu.add(svgExportMI);



		pngExportMI.addActionListener(event -> exportToPng(frame));
		pdfExportMI.addActionListener(event -> exportToPdf(frame));
		svgExportMI.addActionListener(event -> exportToSvg(frame));

		frame.add(menubar, BorderLayout.NORTH);


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

	private static void getChessBoard() {
		if (graphPanel != null) {
			graphPanel.setVisible(false);
			frame.remove(graphPanel);
			chessBoard.setVisible(true);
			frame.add(chessBoard); // prida komponentu
		}
	}

	private static void getGraph() {
		if (chessBoard != null) {
			chessBoard.setVisible(false);
			frame.remove(chessBoard);
			if (graphPanel == null) {
				graphPanel = getTimeSeriesChartExample();
				graphPanel.addComponentListener(new ComponentAdapter() {
					/**
					 * Zajistuje kontrolu resize okna a rozliseni.
					 *
					 * @param e the event to be processed
					 */
					public void componentResized(ComponentEvent e) {
						graphPanel.repaint();
					}
				});
			}
			updateData(chessBoard.getWhitePlayTimes(), chessBoard.getBlackPlayTimes());
			graphPanel.setVisible(true);
			frame.add(graphPanel);
	}
	}

	private static void exportToPng(JFrame frame) {
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

	private static void exportToPdf(JFrame frame) {
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

	private static void exportToSvg(JFrame frame) {
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

	public static JPanel getTimeSeriesChartExample() {
		// Vytvoření seznamu pro hráče 1 a hráče 2 s předanými hodnotami
		List<Double> player1Data = chessBoard.getWhitePlayTimes();
		List<Double> player2Data = chessBoard.getBlackPlayTimes();

		// Inicializace datové sady
		XYSeriesCollection dataset = new XYSeriesCollection();

		// Inicializace série pro hráče 1 a přidání hodnot
		player1Series = new XYSeries("Hráč 1 (bílý)");
		for (int i = 0; i < player1Data.size(); i++) {
			player1Series.add(i + 1, player1Data.get(i));
		}
		dataset.addSeries(player1Series);

		// Inicializace série pro hráče 2 a přidání hodnot
		player2Series = new XYSeries("Hráč 2 (černý)");
		for (int i = 0; i < player2Data.size(); i++) {
			player2Series.add(i + 1, player2Data.get(i));
		}
		dataset.addSeries(player2Series);

		// Vytvoření grafu
		JFreeChart chart = ChartFactory.createXYLineChart(
				"Graf s časy odehraných tahů hráčů",
				"Pořadí tahu",
				"Uplynulý čas tahu (s)",
				dataset,
				PlotOrientation.VERTICAL,
				true,
				true,
				false
		);

		// Pro celociselnou oxu X
		XYPlot plot = (XYPlot) chart.getPlot();
		NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
		xAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		// Nastavení zvýraznění extrémů
		XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
		renderer.setDefaultShapesVisible(true); //setBaseShapesVisible(true);


		// Nastavení stylu grafu
		chart.getXYPlot().setBackgroundPaint(Color.WHITE);
		chart.getXYPlot().setDomainGridlinePaint(Color.BLACK);
		chart.getXYPlot().setRangeGridlinePaint(Color.BLACK);
		chart.getXYPlot().setDomainCrosshairVisible(true);
		chart.getXYPlot().setRangeCrosshairVisible(true);

		// Vytvoření panelu pro graf
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 300));

		// Vytvoření hlavního okna aplikace
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(chartPanel, BorderLayout.CENTER);

		return panel;
	}

	public static void updateData(List<Double> player1Data, List<Double> player2Data) {
		// Aktualizace datových sad
		player1Series.clear();
		for (int i = 0; i < player1Data.size(); i++) {
			player1Series.add(i + 1, player1Data.get(i));
		}

		player2Series.clear();
		for (int i = 0; i < player2Data.size(); i++) {
			player2Series.add(i + 1, player2Data.get(i));
		}
	}

}
