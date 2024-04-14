import java.awt.geom.Path2D;

/**
 * Trida reprezentujici figurku krale
 *
 * @author Dominik Nedved
 * @version 07.05.2023
 */
public class King extends APiece {

    //======================================== Konstruktory ========================================

    /**
     * Konstruktor
     *
     * @param sX souradnice stredove souradnice X
     * @param sY souradnice stredove souradnice Y
     * @param isWhite test na barvu figurky (bila/cerna)
     */
    public King(double sX, double sY, boolean isWhite) {
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

        piece.lineTo(halfSize/4.0, -halfSize/10.0); // pravy horni roh tela
        piece.lineTo(halfSize/2.0, -halfSize/4.0);  // pravy horni roh hlavy

        piece.lineTo(halfSize/8.0, -halfSize/3.0); 		// prava spicka hlavy

        piece.lineTo(halfSize/8.0, -halfSize/1.8); // prava strana krize
        piece.lineTo(halfSize/3.0, -halfSize/1.8); // prava strana krize
        piece.lineTo(halfSize/3.0, -halfSize/1.25); // prava strana krize
        piece.lineTo(halfSize/8.0, -halfSize/1.25); // prava strana krize

        piece.lineTo(halfSize/8.0, -halfSize); 		// prava spicka hlavy
        //king.lineTo(0, -halfSize); 		// spicka hlavy
        piece.lineTo(-halfSize/8.0, -halfSize); 		// leva spicka hlavy

        piece.lineTo(-halfSize/8.0, -halfSize/1.25); // leva strana krize
        piece.lineTo(-halfSize/3.0, -halfSize/1.25); // leva strana krize
        piece.lineTo(-halfSize/3.0, -halfSize/1.8); // leva strana krize
        piece.lineTo(-halfSize/8.0, -halfSize/1.8); // leva strana krize

        piece.lineTo(-halfSize/8.0, -halfSize/3.0); 		// leva spicka hlavy

        piece.lineTo(-halfSize/2.0, -halfSize/4.0); // levy horni roh hlavy
        piece.lineTo(-halfSize/4.0, -halfSize/10.0); // levy horni roh tela
        //queen.lineTo(-halfSize/5.0, 0); // 2. zlom tela
        piece.lineTo(-halfSize/3.0, halfSize - halfSize/2.0);// zlom tela
        piece.lineTo(-halfSize, halfSize - halfSize/3.0);	// levy vrchol podstavy pesaka
        piece.lineTo(-halfSize, halfSize);

        piece.closePath();

        return piece;
    }
}