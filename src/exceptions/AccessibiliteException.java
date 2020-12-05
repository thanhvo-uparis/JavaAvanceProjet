package exceptions;

/**
 * Exception lancée dans le cas où la contrainte d'Accessibilité n'est pas respectée
 * @author Yann Trividic
 * @version 1.0
 */

public class AccessibiliteException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccessibiliteException (String s) {
        super(s) ;
    }
}
