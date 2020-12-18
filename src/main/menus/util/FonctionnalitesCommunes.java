package main.menus.util;

import java.util.Scanner;

import main.entites.Agglomeration;
import main.io.LectureEcriture;

public class FonctionnalitesCommunes {
	public static void sauvegarderAgglomerationFichier(Agglomeration agg, Scanner sc) {
		System.out.print("Veuillez entrer le chemin absolu du dossier où vous souhaitez sauvegarder votre fichier : ");
		String chemin = EntreeClavier.getNomFichierEcritureValideAvecExtension(sc, "ca") ;
		LectureEcriture.ecritureVersFichier(chemin, agg);
	}
	
	public static void finProgramme(Scanner sc) {
		System.out.println("Fin du programme.");
		sc.close();
		System.exit(1);
	}
}
