import java.awt.geom.Path2D;

/**
 * Trida reprezentujici figurku veze
 *
 * @author Dominik Nedved, A22B0109P
 * @version 07.05.2023
 */
public class Rook extends APiece {

    //======================================== Konstruktory ========================================

    /**
     * Konstruktor
     *
     * @param sX souradnice stredove souradnice X
     * @param sY souradnice stredove souradnice Y
     * @param isWhite test na barvu figurky (bila/cerna)
     */
    public Rook(double sX, double sY, boolean isWhite) {
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
        piece.lineTo(halfSize, halfSize - halfSize/3.0);	// pravy vrchol podstavy
        piece.lineTo(halfSize/2.0, -halfSize/2.0); // pravy roh vrcholu "tela"

        piece.lineTo(halfSize/1.5, -halfSize/1.5);
        piece.lineTo(halfSize/1.5, -halfSize);

        piece.lineTo(halfSize/3.0, -halfSize);
        piece.lineTo(halfSize/3.0, -halfSize/1.3);   // prava hradba dolu
        piece.lineTo(halfSize/6.0, -halfSize/1.3);   // prava hradba doleva
        piece.lineTo(halfSize/6.0, -halfSize);          // prostredni hradba nahoru
        piece.lineTo(-halfSize/6.0, -halfSize);          // prostredni hradba doleva
        piece.lineTo(-halfSize/6.0, -halfSize/1.3);   // prostredni hradba dolu
        piece.lineTo(-halfSize/3.0, -halfSize/1.3);   // leva hradba dolni levy roh
        piece.lineTo(-halfSize/3.0, -halfSize);          // leva hradba nahoru

        piece.lineTo(-halfSize/1.5, -halfSize);
        piece.lineTo(-halfSize/1.5, -halfSize/1.5);
        piece.lineTo(-halfSize/2.0, -halfSize/2.0); // levy roh vrcholu "tela"

        piece.lineTo(-halfSize, halfSize - halfSize/3.0);	// levy vrchol podstavy
        piece.lineTo(-halfSize, halfSize);

        piece.closePath();

        return piece;
    }
}