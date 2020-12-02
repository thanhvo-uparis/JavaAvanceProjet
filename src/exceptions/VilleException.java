package exceptions;

/**
 * Exception lancée dans le cas où la ville ou l'identifiant passé en argument n'existe pas.
 * @author Yann Trividic
 * @version 1.0
 */

public class VilleException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VilleException (String s) {
        super(s) ;
    }
}