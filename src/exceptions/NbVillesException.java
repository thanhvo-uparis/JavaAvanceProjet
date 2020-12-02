package exceptions;

/**
 * Exception lancée dans le cas où le nombre de villes n'est pas valide
 * @author Yann Trividic
 * @version 1.0
 */

public class NbVillesException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NbVillesException (String s) {
        super(s) ;
    }
}