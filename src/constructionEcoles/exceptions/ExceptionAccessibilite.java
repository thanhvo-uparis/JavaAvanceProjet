package constructionEcoles.exceptions;

/**
 * Exception lancée dans le cas où la contrainte d'Accessibilité n'est pas respectée
 * @author Yann Trividic
 * @version 1.0
 */

public class ExceptionAccessibilite extends Exception {
	public ExceptionAccessibilite (String s) {
        super(s) ;
    }
}
