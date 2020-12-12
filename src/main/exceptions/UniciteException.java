package main.exceptions;

/**
 * Exception lancée dans le cas où une contrainte d'unicité n'est pas respectées (deux villes identiques, deux routes, etc.)
 * @author Yann Trividic
 * @version 1.0
 */

public class UniciteException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UniciteException (String s) {
        super(s) ;
    }
}
