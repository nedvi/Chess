package CleanBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;

public class Pawn extends JPanel {
	
	private final int N = 3;			//pocet cipu
	private final double DEFAULT_R = 25;	//vychozi polomer hvezdy
	private double R = DEFAULT_R;			//aktualni polomer
	private Path2D star;	//hvezda k vykresleni

	private int posunPoX;

	private int sX;
	private int sY;

	private Color starColor = Color.BLACK;		// defaultne oranzova

	private int rectSize;
		
	//========= TESTING =========
	protected int row;
	protected int col;
	protected boolean isWhite;

	public Pawn(int x, int y, boolean isWhite) {
		this.sX = x;
		this.sY = y;
		this.isWhite = isWhite;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public boolean isWhite() {
		return isWhite;
	}

	//========= END TESTING =========

	@Override
	public void paint(Graphics g) {
		R = getRectSize()/2.0;
		super.paint(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.translate(sX, sY);
		drawStar(g2);
	}
	
	/** 
	 * Vytvori v pameti pravidelnou hvezdu o polomeru R s N cipy
	 * Stred hvezdy je v pocatku souradneho systemu
	 * @return vytvorenou hvezdu
	 */
	private Path2D createStar() {

		double delta_fi = 2*Math.PI/N;
		Path2D star = new Path2D.Double();
		star.moveTo(0, -R); //prvni vrchol hvezdicky (nahore)
		for (int i = 0; i < N; i++) {
			double x = R*Math.cos(i*delta_fi + 1.5*Math.PI);
			double y = R*Math.sin(i*delta_fi + 1.5*Math.PI);
			star.lineTo(x, y);
			
			double x2 = 0.5*R*Math.cos(i*delta_fi + delta_fi*0.5 + 1.5*Math.PI);
			double y2 = 0.5*R*Math.sin(i*delta_fi + delta_fi*0.5 + 1.5*Math.PI);
			star.lineTo(x2, y2);
		}
		star.closePath();
		return star;
	}
	
	private void drawStar(Graphics2D g2) {
		if (this.star == null){
			this.star = createStar();
		}
		
		g2.setColor(starColor);
		g2.fill(star);
	}

	/**
	 * Otestuje, zda je zobrazeny objekt hvezdy 
	 * zasazen mysi/dotykem v miste [x,y]
	 * @param x
	 * @param y
	 * @return true, pokud zasah
	 */
	public boolean isStarHit(double x, double y) {
		//TODO: test, zda hvezda zasazena
		return (this.star != null &&
				this.star.contains(
				x - sX, y - sY));
	}

	/**
	 * Setter pro barvu hvezdy
	 * @param starColor pozadovana barva hvezdy
	 */
	public void setStarColor(Color starColor) {
		this.starColor = starColor;
	}

	public void setPosunPoX(int posunPoX) {
		this.posunPoX += posunPoX;
	}

	public void setsX(int sX) {
		this.sX = sX;
	}

	public void setsY(int sY) {
		this.sY = sY;
	}

	public int getRectSize() {
		return rectSize;
	}

	public void moveTo(int x, int y) {
		setsX(x);
		setsY(y);
	}

	public void setRectSize(int rectSize) {
		this.rectSize = rectSize;
		this.star = createStar();
		this.repaint();
	}
}
