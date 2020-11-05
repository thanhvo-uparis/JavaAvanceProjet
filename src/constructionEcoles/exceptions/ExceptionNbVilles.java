package constructionEcoles.exceptions;

/**
 * Exception lancée dans le cas où le nombre de villes n'est pas valide
 * @author Yann Trividic
 * @version 1.0
 */

public class ExceptionNbVilles extends Exception {
	public ExceptionNbVilles (String s) {
        super(s) ;
    }
}