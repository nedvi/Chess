package reengineering;

import java.awt.geom.Path2D;

/**
 * Trida reprezentujici figurku kralovny
 *
 * @author Dominik Nedved, A22B0109P
 * @version 26.03.2023
 */
public class Queen extends APiece {

    //======================================== Konstruktory ========================================

    /**
     * Konstruktor
     *
     * @param sX souradnice stredove souradnice X
     * @param sY souradnice stredove souradnice Y
     * @param isWhite test na barvu figurky (bila/cerna)
     */
    public Queen(double sX, double sY, boolean isWhite) {
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
        piece.lineTo(halfSize/3.0, halfSize - halfSize/2.0); // zlom tela
        //queen.lineTo(halfSize/5.0, 0); // 2. zlom tela

        piece.lineTo(halfSize/4.0, -halfSize/4.0); // pravy horni roh tela

        piece.lineTo(halfSize/2.0, -halfSize);  // pravy horni roh hlavy
        piece.lineTo(halfSize/6.0, -halfSize/1.5);
        piece.lineTo(0, -halfSize); 		// spicka hlavy
        piece.lineTo(-halfSize/6.0, -halfSize/1.5);
        piece.lineTo(-halfSize/2.0, -halfSize); // levy horni roh hlavy

        piece.lineTo(-halfSize/4.0, -halfSize/4.0); // levy horni roh tela
        //queen.lineTo(-halfSize/5.0, 0); // 2. zlom tela
        piece.lineTo(-halfSize/3.0, halfSize - halfSize/2.0);// zlom tela
        piece.lineTo(-halfSize, halfSize - halfSize/3.0);	// levy vrchol podstavy pesaka
        piece.lineTo(-halfSize, halfSize);

        piece.closePath();

        return piece;
    }
}