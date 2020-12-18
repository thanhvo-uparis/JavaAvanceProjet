package main.menus.util;

import java.util.Scanner;

import main.entites.Agglomeration;
import main.io.EntreeClavier;
import main.io.LectureEcriture;

/**
 * Classe utilitaire contenant des méthodes devant être accessible depuis les différents menus
 * @author Yann Trividic
 */
public class FonctionnalitesCommunes {
	public static void sauvegarderAgglomerationFichier(Agglomeration agg, Scanner sc) {
		System.out.print("Veuillez entrer le chemin absolu du dossier où vous souhaitez sauvegarder votre fichier : ");
		String chemin = EntreeClavier.getNomFichierEcritureValideAvecExtension(sc, "ca") ;
		LectureEcriture.ecritureVersFichier(chemin, agg);
	}
	
	public static void finProgramme(Scanner sc) {
		sc.close();
		System.out.println("\n\nFin du programme.");
		System.exit(1);
	}
}
