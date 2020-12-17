package main.exceptions;

/**
 * Exception lancée dans le cas où un fichier CA est corrompu
 * @author Yann Trividic
 * @version 1.0
 */
public class FichierAgglomerationSyntaxeException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FichierAgglomerationSyntaxeException (String s) {
        super(s) ;
    }
}