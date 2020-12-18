package main.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Classe permettant de gérer les propriétés systèmes relative au projet
 * @author Yann Trividic
 * @version 1.0
 */

public class ChargeurProprietes {
	
	static String fichier = "resources/config/config.properties";
	
	/**
	 * Retourne la valeur associée à une propriété passée en argument
	 * @param propriete String associé au nom de la propriété
	 * @return la valeur booleenne associée à la propriété
	 */
	public static Boolean getPropriete(String propriete) {
		Boolean prop ;
		try {
			prop = Boolean.getBoolean(propriete) ;
		} catch (IllegalArgumentException e) {
			System.err.println("La propriété "+propriete+" n'existe pas.") ;
			return null ;
		}
        return prop ;
	}
	
	/**
	 * Méthode chargeant les propriétés du fichier ./src/resources/config/config.properties
	 * @param affichage Permet d'afficher ou non les propriétés systèmes chargées
	 */
	public static void chargerProprietes(boolean affichage) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream input = classLoader.getResourceAsStream(fichier)) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            System.setProperties(prop);
            if(affichage) {
                System.out.println("affichageExceptions="+System.getProperty("affichageExceptions"));
                System.out.println("affichageDebug="+System.getProperty("affichageDebug"));
                System.out.println("affichageLogs="+System.getProperty("affichageLogs"));
            }

        } catch (IOException ex) {
            System.err.println("Le fichier "+fichier+" n'a pas pu être lu correctement.") ;
            chargerProprietesParDefaut() ;
        } catch (NullPointerException e) {
            System.err.println("Le fichier "+fichier+" n'a pas pu être accédé (peut-être que vous n'avez pas une machine sous UNIX ?)") ;
            chargerProprietesParDefaut() ;
        }
	}
	
	private static void chargerProprietesParDefaut() {

        System.setProperty("affichageDebug", "false") ;
        System.setProperty("affichageDebug", "false") ;
        System.setProperty("affichageDebug", "false") ;

        System.out.println("Les propriétés systèmes ont pris pour valeur par défaut : ") ;
        System.out.println("affichageExceptions="+System.getProperty("affichageExceptions"));
        System.out.println("affichageDebug="+System.getProperty("affichageDebug"));
        System.out.println("affichageLogs="+System.getProperty("affichageLogs"));
	}
}