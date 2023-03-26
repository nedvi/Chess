import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

public class Pawn extends JPanel implements IPiece {
	private double halfSize;
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
	boolean isOut;
	@Override
	public boolean isOut() {
		return isOut;
	}
	@Override
	public void setOut(boolean isOut) {
		this.isOut = isOut;
	}
	//======================================== Konstruktory ========================================
	public Pawn(int sX, int sY, boolean isWhite) {
		this.sX = sX;
		this.sY = sY;
		this.isWhite = isWhite;
		this.isOut = false;
		if (isWhite) {
			setPieceColor(PIECE_WHITE);
		} else {
			setPieceColor(PIECE_BLACK);
		}
	}

	//======================================== Funkce ========================================
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		halfSize = getRectSize()/3.0;
		Graphics2D g2 = (Graphics2D)g;
		g2.translate(sX, sY);
		paintPiece(g2);
	}
	
	/** 
	 * Vytvori v pameti pravidelnou hvezdu o polomeru R s N cipy
	 * Stred hvezdy je v pocatku souradneho systemu
	 * @return vytvorenou hvezdu
	 */
	@Override
	public Path2D createPiece() {
		pawn = new Path2D.Double();
		pawn.moveTo(-halfSize, halfSize);	// levy dolni roh
		pawn.lineTo(halfSize, halfSize);	// pravy dolni roh
		pawn.lineTo(halfSize, halfSize - halfSize/3.0);	// pravy vrchol podstavy pesaka
		pawn.lineTo(halfSize/2.0, halfSize - halfSize/2.0); // zlom tela

		pawn.lineTo(halfSize/4.0, -halfSize/4.0); // pravy horni roh tela
		pawn.lineTo(halfSize/3.0, -halfSize/3.0);
		pawn.lineTo(halfSize/2.0, -halfSize/2.0);

		pawn.lineTo(halfSize/5.0, -halfSize);
		//pawn.lineTo(0, -halfSize); 		// spicka hlavy
		pawn.lineTo(-halfSize/5.0, -halfSize);

		pawn.lineTo(-halfSize/2.0, -halfSize/2.0);
		pawn.lineTo(-halfSize/3.0, -halfSize/3.0);
		pawn.lineTo(-halfSize/4.0, -halfSize/4.0); // levy horni roh tela

		pawn.lineTo(-halfSize/2.0, halfSize - halfSize/2.0);// zlom tela
		pawn.lineTo(-halfSize, halfSize - halfSize/3.0);	// levy vrchol podstavy pesaka
		pawn.lineTo(-halfSize, halfSize);

		pawn.closePath();

		return pawn;
	}

	@Override
	public void paintPiece(Graphics2D g2) {
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(pieceColor);
		if (this.pawn == null){
			this.pawn = createPiece();
		}
		g2.fill(pawn);

		if (this.isWhite()) {
			g2.setColor(PIECE_BLACK);
		} else {
			g2.setColor(PIECE_WHITE);
		}
		g2.draw(pawn);
	}

	/**
	 * Otestuje, zda je zobrazeny objekt hvezdy 
	 * zasazen mysi/dotykem v miste [x,y]
	 * @param x
	 * @param y
	 * @return true, pokud zasah
	 */
	@Override
	public boolean isPieceHit(double x, double y) {
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
	@Override
	public int getRow() {
		return row;
	}
	@Override
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


	//======================================== Settery ========================================
	/**
	 * Setter pro barvu hvezdy
	 * @param pieceColor pozadovana barva hvezdy
	 */
	@Override
	public void setPieceColor(Color pieceColor) {
		this.pieceColor = pieceColor;
	}
	@Override
	public void setRectSize(int rectSize) {
		this.rectSize = rectSize;
		this.pawn = createPiece();
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
}
