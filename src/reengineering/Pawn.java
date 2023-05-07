package reengineering;

import java.awt.geom.Path2D;

/**
 * Trida reprezentujici figurku pesaka
 *
 * @author Dominik Nedved, A22B0109P
 * @version 07.05.2023
 */
public class Pawn extends APiece {

	//======================================== Konstruktory ========================================

	/**
	 * Konstruktor
	 *
	 * @param sX souradnice stredove souradnice X
	 * @param sY souradnice stredove souradnice Y
	 * @param isWhite test na barvu figurky (bila/cerna)
	 */
	public Pawn(double sX, double sY, boolean isWhite) {
		super(sX, sY, isWhite);
	}

	//======================================== Funkce ========================================

	/**
	 * Vytvoreni figurky
	 *
	 * @return hotova cestu figurky
	 */
	@Override
	public Path2D createPiece() {
		piece = new Path2D.Double();
		piece.moveTo(-halfSize, halfSize);	// levy dolni roh
		piece.lineTo(halfSize, halfSize);	// pravy dolni roh
		piece.lineTo(halfSize, halfSize - halfSize/3.0);	// pravy vrchol podstavy pesaka
		piece.lineTo(halfSize/2.0, halfSize - halfSize/2.0); // zlom tela

		piece.lineTo(halfSize/4.0, -halfSize/4.0); // pravy horni roh tela
		piece.lineTo(halfSize/3.0, -halfSize/3.0);
		piece.lineTo(halfSize/2.0, -halfSize/2.0);

		piece.lineTo(halfSize/5.0, -halfSize);
		piece.lineTo(-halfSize/5.0, -halfSize);

		piece.lineTo(-halfSize/2.0, -halfSize/2.0);
		piece.lineTo(-halfSize/3.0, -halfSize/3.0);
		piece.lineTo(-halfSize/4.0, -halfSize/4.0); // levy horni roh tela

		piece.lineTo(-halfSize/2.0, halfSize - halfSize/2.0);// zlom tela
		piece.lineTo(-halfSize, halfSize - halfSize/3.0);	// levy vrchol podstavy pesaka
		piece.lineTo(-halfSize, halfSize);

		piece.closePath();

		return piece;
	}
}
