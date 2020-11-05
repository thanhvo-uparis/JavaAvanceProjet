package constructionEcoles.exceptions;

/**
 * Exception lancée dans le cas où une contrainte d'unicité n'est pas respectées (deux villes identiques, deux routes, etc.)
 * @author Yann Trividic
 * @version 1.0
 */

public class ExceptionUnicite extends Exception {
	public ExceptionUnicite (String s) {
        super(s) ;
    }
}
