package constructionEcoles.exceptions;

/**
 * Exception lancée dans le cas où la contrainte Economie n'est pas respectée
 * @author Yann Trividic
 * @version 1.0
 */

public class ExceptionEconomie extends Exception {
	public ExceptionEconomie (String s) {
        super(s) ;
    }
}
