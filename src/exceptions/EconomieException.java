package exceptions;

/**
 * Exception lancée dans le cas où la contrainte Economie n'est pas respectée
 * @author Yann Trividic
 * @version 1.0
 */

public class EconomieException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EconomieException (String s) {
        super(s) ;
    }
}
