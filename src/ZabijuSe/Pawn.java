package ZabijuSe;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

public class Pawn extends JPanel {
	
	private final int N = 3;			//pocet cipu
	private final double DEFAULT_R = 25;	//vychozi polomer hvezdy
	private double R = DEFAULT_R;			//aktualni polomer
	private Path2D pawn;	// pesak k vykresleni
	private int sX;
	private int sY;
	private Color starColor = Color.BLACK;		// defaultne oranzova
	private int rectSize;
	private int row;
	private int column;
	protected boolean isWhite;

	public Field field;

	private int posun = 0;

	private boolean isFocused;

	//======================================== Konstruktory ========================================
	public Pawn(int sX, int sY, boolean isWhite) {
		this.sX = sX;
		this.sY = sY;
		this.isWhite = isWhite;
		this.isFocused = false;
	}

	//======================================== Funkce ========================================
	@Override
	public void paint(Graphics g) {

		super.paint(g);
		R = getRectSize()/2.0;
		Graphics2D g2 = (Graphics2D)g;
		g2.translate(sX + posun, sY);
		drawPawn(g2);
	}
	
	/** 
	 * Vytvori v pameti pravidelnou hvezdu o polomeru R s N cipy
	 * Stred hvezdy je v pocatku souradneho systemu
	 * @return vytvorenou hvezdu
	 */
	private Path2D createPawn() {

		double delta_fi = 2*Math.PI/N;
		pawn = new Path2D.Double();
		pawn.moveTo(0, -R); //prvni vrchol hvezdicky (nahore)
		for (int i = 0; i < N; i++) {
			double x = R*Math.cos(i*delta_fi + 1.5*Math.PI);
			double y = R*Math.sin(i*delta_fi + 1.5*Math.PI);
			pawn.lineTo(x, y);
			
			double x2 = 0.5*R*Math.cos(i*delta_fi + delta_fi*0.5 + 1.5*Math.PI);
			double y2 = 0.5*R*Math.sin(i*delta_fi + delta_fi*0.5 + 1.5*Math.PI);
			pawn.lineTo(x2, y2);
		}
		pawn.closePath();
		return pawn;
	}
	
	private void drawPawn(Graphics2D g2) {
		if (this.pawn == null){
			this.pawn = createPawn();
		}
		g2.setColor(starColor);
		g2.fill(pawn);
	}

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

	public int getPosun() {
		return posun;
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

	public boolean isFocused() {
		return isFocused;
	}

	//======================================== Settery ========================================
	/**
	 * Setter pro barvu hvezdy
	 * @param starColor pozadovana barva hvezdy
	 */
	public void setPawnColor(Color starColor) {
		this.starColor = starColor;
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

	public void setPosun(int posun) {
		this.posun = posun;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public void setFocused(boolean focused) {
		isFocused = focused;
	}
}
