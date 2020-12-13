package main.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ChargeurProprietes {
	
	static String fichier = "./src/resources/config/config.properties";
	
	public static boolean getPropriete(String propriete) {
        return Boolean.getBoolean(propriete) ;
	}
	
	public static void chargerProprietes() {
        try (InputStream input = new FileInputStream(fichier)) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            System.setProperties(prop);
            System.out.println(System.getProperty("affichageExceptions"));
            System.out.println(System.getProperty("affichageDebug"));
            System.out.println(System.getProperty("affichageLogs"));

        } catch (IOException ex) {
            System.err.println("Le fichier "+fichier+" n'a pas pu être lu correctement.") ;
        }
	}
}