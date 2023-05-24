import java.awt.geom.Path2D;

/**
 * Trida reprezentujici figurku kone
 *
 * @author Dominik Nedved, A22B0109P
 * @version 07.05.2023
 */
public class Knight extends APiece {

    //======================================== Konstruktory ========================================

    /**
     * Konstruktor
     *
     * @param sX souradnice stredove souradnice X
     * @param sY souradnice stredove souradnice Y
     * @param isWhite test na barvu figurky (bila/cerna)
     */
    public Knight(double sX, double sY, boolean isWhite) {
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
        piece.lineTo(halfSize/2.0, halfSize - halfSize/1.5); // "zlom" na zadech
        piece.lineTo(halfSize, -halfSize/2.0); // pravy roh vrcholu "tela"
        piece.lineTo(halfSize/2.0, -halfSize);
        piece.lineTo(halfSize/2.0, -halfSize/1.3);
        piece.lineTo(halfSize/6.0, -halfSize/1.2);

        piece.lineTo(-halfSize/1.3, -halfSize/2.0); // horni spicka nosu kone
        piece.lineTo(-halfSize/1.5, -halfSize/8.0); // dolni spicka nosu kone
        piece.lineTo(-halfSize/5.0, -halfSize/6.0); // krk kone zleva

        piece.lineTo(-halfSize/3.0, halfSize - halfSize/1.5);// "zlom" na hrudi

        piece.lineTo(-halfSize, halfSize - halfSize/3.0);	// levy vrchol podstavy
        piece.lineTo(-halfSize, halfSize);

        piece.closePath();

        return piece;
    }
}