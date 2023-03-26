package ZabijuSe;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

public class Pawn extends JPanel {
	
	private final int N = 3;			//pocet cipu
	private final double DEFAULT_R = 25;	//vychozi polomer hvezdy
	private double halfSize = DEFAULT_R;			//aktualni polomer
	private Path2D pawn;	// pesak k vykresleni
	private int sX;
	private int sY;
	public static final Color PIECE_WHITE = Color.WHITE;
	public static final Color PIECE_BLACK = Color.BLACK;
	private Color pieceColor;
	private int rectSize;
	private int row;
	private int column;
	private boolean isWhite;

	public Field field;

	private boolean isFocused;

	//============================ TEST ===============================
	private static int WIDTH;
	private static int HEIGHT;
	private static int PAWN_SIZE;
	private static int RADIUS;
	private static int X_CENTER;
	private static int Y_CENTER;

	//======================================== Konstruktory ========================================
	public Pawn(int sX, int sY, boolean isWhite) {
		this.sX = sX;
		this.sY = sY;
		this.isWhite = isWhite;
		this.isFocused = false;

		if (isWhite) {
			setPawnColor(PIECE_WHITE);
		} else {
			setPawnColor(PIECE_BLACK);
		}
	}

	//======================================== Funkce ========================================
	@Override
	public void paint(Graphics g) {

		super.paint(g);
		halfSize = getRectSize()/3.0;
		Graphics2D g2 = (Graphics2D)g;
		g2.translate(sX, sY);
		paintPawn(g2);
		//drawPawn(g2);
	}
	
	/** 
	 * Vytvori v pameti pravidelnou hvezdu o polomeru R s N cipy
	 * Stred hvezdy je v pocatku souradneho systemu
	 * @return vytvorenou hvezdu
	 */
	private Path2D createPawn() {

//		double delta_fi = 2*Math.PI/N;
//		pawn = new Path2D.Double();
//		pawn.moveTo(0, -size); //prvni vrchol hvezdicky (nahore)
//		for (int i = 0; i < N; i++) {
//			double x = size*Math.cos(i*delta_fi + 1.5*Math.PI);
//			double y = size*Math.sin(i*delta_fi + 1.5*Math.PI);
//			pawn.lineTo(x, y);
//
//			double x2 = 0.5*size*Math.cos(i*delta_fi + delta_fi*0.5 + 1.5*Math.PI);
//			double y2 = 0.5*size*Math.sin(i*delta_fi + delta_fi*0.5 + 1.5*Math.PI);
//			pawn.lineTo(x2, y2);
//		}
//		pawn.closePath();

		// TODO: FUNKCNI
//		pawn = new Path2D.Double();
//		pawn.moveTo(0, -halfSize/2.0);
//		pawn.lineTo(halfSize, halfSize);
//		pawn.lineTo(-halfSize, halfSize);
//		pawn.lineTo(0, -halfSize/2.0);
//		pawn.closePath();

		int headSize = (int) (halfSize/2.0);

		pawn = new Path2D.Double();
		pawn.moveTo(-halfSize, halfSize);	// levy dolni roh
		pawn.lineTo(halfSize, halfSize);	// pravy dolni roh
		pawn.lineTo(halfSize, halfSize - halfSize/3.0);	// pravy vrchol podstavy pesaka
		pawn.lineTo(0, -halfSize/2.0); // spicka tela

		pawn.lineTo(halfSize/1.5, -halfSize/1.5);
		pawn.lineTo(halfSize/1.5, -halfSize);
		pawn.lineTo(0, -halfSize); 		// spicka hlavy
		pawn.lineTo(-halfSize/1.5, -halfSize);
		pawn.lineTo(-halfSize/1.5, -halfSize/1.5);
		pawn.lineTo(0, -halfSize/2.0); // spicka tela

		pawn.lineTo(-halfSize, halfSize - halfSize/3.0);	// levy vrchol podstavy pesaka
		pawn.lineTo(-halfSize, halfSize);

		pawn.closePath();

		return pawn;
	}

	private void paintPawn(Graphics2D g2) {
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(pieceColor);
		pawn = createPawn();
		g2.fill(pawn);

		//g2.setColor(Color.WHITE);	//TODO pak pridat vetveni na baervu obrysu inverzni k barve figurky
		if (this.isWhite()) {
			g2.setColor(PIECE_BLACK);
		} else {
			g2.setColor(PIECE_WHITE);
		}
		g2.draw(pawn);
	}
	
//	private void drawPawn(Graphics2D g2) {
//		if (this.pawn == null){
//			this.pawn = createPawn();
//		}
//		g2.setColor(pieceColor);
//		g2.fill(pawn);
//	}

	/**
	 * Otestuje, zda je zobrazeny objekt hvezdy 
	 * zasazen mysi/dotykem v miste [x,y]
	 * @param x
	 * @param y
	 * @return true, pokud zasah
	 */
	public boolean isPawnHit(double x, double y) {
		//TODO: test, zda hvezda zasazena
		return (this.pawn != null &&
				this.pawn.contains(
				x - this.sX, y - this.sY));
	}

	public void moveTo(int x, int y) {
		setsX(x);
		setsY(y);
	}

	//======================================== Gettery ========================================
	public int getRectSize() {
		return rectSize;
	}
	public int getRow() {
		return row;
	}
	public int getColumn() {
		return column;
	}
	public int getsX() {
		return sX;
	}
	public int getsY() {
		return sY;
	}
	public Field getField() {
		return field;
	}

	public boolean isWhite() {
		return isWhite;
	}

	public boolean isFocused() {
		return isFocused;
	}

	//======================================== Settery ========================================
	/**
	 * Setter pro barvu hvezdy
	 * @param pieceColor pozadovana barva hvezdy
	 */
	public void setPawnColor(Color pieceColor) {
		this.pieceColor = pieceColor;
	}
	public void setRectSize(int rectSize) {
		this.rectSize = rectSize;
		this.pawn = createPawn();
		this.repaint();
	}
	public void setsX(int sX) {
		this.sX = sX;
	}
	public void setsY(int sY) {
		this.sY = sY;
	}
	public void setRow(int row) {
		this.row = row;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public void setFocused(boolean focused) {
		isFocused = focused;
	}
}
