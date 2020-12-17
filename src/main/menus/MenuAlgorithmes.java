package main.menus;

import java.util.Scanner;

import main.menus.util.Affichage;
import main.menus.util.EntreeClavier;
import main.algorithmique.testeur.Testeur;
import main.entites.Agglomeration;
import main.io.LectureEcriture;

public class MenuAlgorithmes {
	
	//TODO il ne serait pas très long d'implémenter la possibilité de générer un CSV bilan de tous les algos

	/**
	 * méthode qui permet de créer le menu principal des algorithmes afin de laisser choisir à l'utilisateur une résolution auto avec un choix d'algorithme, des testeurs, resolution avec une agglomération aléatoire
	 * @param agg qui est l'agglomération crée ou chargée par l'utilisateur.
	 */
	protected static void menuPrincipalAlgorithmes(Agglomeration agg, Scanner sc) {
		
		int choix = -1 ;

		Affichage.afficherMenuPrincipalAlgorithmes() ;
		choix = EntreeClavier.getEntierDansIntervalleExclu(0, 6, sc) ;
		System.out.println("Votre choix : "+choix) ;

		switch(choix) {
		case 0 :
			// Fermeture du programme
			System.out.println("Fin du programme.");
			sc.close();
			System.exit(1);
			break ;
		case 1 :
			resoudAutoAvecChoixAlgorithme(agg, sc) ;
			break ;
		case 2 :
			Testeur.resolutionAgglomerationAvecBascule(agg) ;
			break ;
		case 3 :
			Testeur.compareAlgorithmes(agg) ;
			break ;
		case 4 :
			resoudAutoAvecAgglomerationsAleatoires(sc) ;
			break ;
		case 5 : 
			//Sauvegarde le fichier en utilisant la méthode ecritureVersFichier de la classe LectureEcriture.
			sc.nextLine();
			System.out.println("Veuillez entrer le chemin absolu où vous souhaitez sauvegarder votre fichier : ");
			LectureEcriture.ecritureVersFichier(sc.nextLine(), agg);
			menuPrincipalAlgorithmes(agg, sc) ;
			break;
		case 6 :
			MenuPrincipal.lancement(sc, false) ;
		default :
			System.out.println("Choix incorrect.");
			break ;
		}
		
		menuPrincipalAlgorithmes(agg, sc) ;
	}
		/**
		 * Méthode qui permet de résoudre le problème en prenant une agglomération aléatoire afin de tester les algorithmes.
		 * 
		 * @param sc qui est une entrée clavier pour lire le nombre de villes de l'agglomération à générer
		 */
	private static void resoudAutoAvecAgglomerationsAleatoires(Scanner sc) {
		
		int min = -1, max = -1 ;
		System.out.println("Vous devez choisir la taille minimale des agglomérations à générer (>1) et la taille maximale des agglomérations à générer (<=500)") ;
		System.out.println("Attention : Plus le nombre choisi est grand, plus le temps d'exécution de la méthode sera long.") ;
		System.out.print("Votre min ? ");
		min = EntreeClavier.getEntierDansIntervalleExclu(2, 500, sc) ;
		System.out.println("min = "+min) ;
		System.out.print("Votre max ? ");
		max = EntreeClavier.getEntierDansIntervalleExclu(2, 500, sc) ;
		System.out.println("max = "+max) ;
		Testeur.getTestsAlgosSurAgglomerationAleatoire(min, max) ;
	}
	/**
	 * permet de résoudre le problème en laissant le choix de l'algorithme à l'utilisateur
	 * 
	 * @param agg qui est l'agglomération chargée/ crée par l'utilisateur
	 * @param sc qui est une entrée clavier qui permet de prendre le choix de l'algorithme de l'utilisateur
	 */
	private static void resoudAutoAvecChoixAlgorithme(Agglomeration agg, Scanner sc) { // quel type également ici et les arguments
		int choix = -1 ;
		Affichage.afficherListeAlgorithmes() ;
		choix = EntreeClavier.getEntierDansIntervalleExclu(0, Testeur.algos.length, sc) ; //TODO vérifier qu'il n'y a pas de problème à l'exécution avec -1 0 6 etc
		System.out.println("Votre choix : "+choix) ;
		if(choix != 0) Testeur.getTestSurAlgo(agg, choix-1) ;
	}
}
