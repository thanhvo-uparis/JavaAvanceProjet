package constructionEcoles.exceptions;

/**
 * Exception lancée dans le cas où la ville ou l'identifiant passé en argument n'existe pas.
 * @author Yann Trividic
 * @version 1.0
 */

public class ExceptionVille extends Exception {
	public ExceptionVille (String s) {
        super(s) ;
    }
}