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
		
		int choix = -1 ;//Initialise la variable choix à -1

		Affichage.afficherMenuPrincipalAlgorithmes() ;//fait appel à la méthode afficherMenuPrincipalAlgorithme de la classe Affichage.
		choix = EntreeClavier.getEntierDansIntervalleExclu(0, 6, sc) ;//fait appel à la méthode getEntierDansIntervalleExclu de la classe EntreeClavier avec comme paramètres 0, 6 et sc.
		System.out.println("Votre choix : "+choix) ;// affiche le choix de l'utilisateur

		switch(choix) {
		case 0 :
			// Fermeture du programme
			System.out.println("Fin du programme.");
			sc.close();
			System.exit(1);
			break ;
		case 1 :
			resoudAutoAvecChoixAlgorithme(agg, sc) ;//fait appel à la méthode resoudAutoAvecChoixAlgorithme avec comme paramètres agg et sc.
			break ;
		case 2 :
			Testeur.resolutionAgglomerationAvecBascule(agg) ;//fait appel à la méthode resolutionAgglomerationAvecBascule de la classe Testeur avec comme parmaètre agg.
			break ;
		case 3 :
			Testeur.compareAlgorithmes(agg) ;//fait appel à la méthode compareAlgorithmes de la classe Testeur avec comme paramètre agg.
			break ;
		case 4 :
			resoudAutoAvecAgglomerationsAleatoires(sc) ;//fait appel à la méthode resoudAutoAvecAgglomerationsAleatoires avec comme paramètre sc.
			break ;
		case 5 : 
			//Sauvegarde le fichier en utilisant la méthode ecritureVersFichier de la classe LectureEcriture.
			sc.nextLine();
			System.out.println("Veuillez entrer le chemin absolu où vous souhaitez sauvegarder votre fichier : ");
			LectureEcriture.ecritureVersFichier(sc.nextLine(), agg);//fait appel à la méthode ecritureVersFichier de la classe LectureEcriture avec comme paramètres sc et agg.
			menuPrincipalAlgorithmes(agg, sc) ;//fait appel à la méthode menuPrincipalAlgorithmes avec comme paramètres agg et sc 
			break;
		case 6 :
			MenuPrincipal.lancement(sc, false) ;//fait appel à la méthode lancement de la classe MenuPrincipal avec comme arguments sc et false.
		default :
			System.out.println("Choix incorrect.");
			break ;
		}
		
		menuPrincipalAlgorithmes(agg, sc) ;//fait appel à la méthode menuPrincipalAlgorithmes avec comme paramètres agg et sc 
	}
		/**
		 * Méthode qui permet de résoudre le problème en prenant une agglomération aléatoire afin de tester les algorithmes.
		 * 
		 * @param sc qui est une entrée clavier pour lire le nombre de villes de l'agglomération à générer
		 */
	private static void resoudAutoAvecAgglomerationsAleatoires(Scanner sc) {
		
		int min = -1, max = -1 ;//initialise les variables min et max à -1.
		System.out.println("Vous devez choisir la taille minimale des agglomérations à générer (>1) et la taille maximale des agglomérations à générer (<=500)") ;
		System.out.println("Attention : Plus le nombre choisi est grand, plus le temps d'exécution de la méthode sera long.") ;
		System.out.print("Votre min ? ");
		min = EntreeClavier.getEntierDansIntervalleExclu(2, 500, sc) ;//fait appel à la méthode getEntierDansIntervalleExclu de la classe EntreeClavier avec comme paramètres 2, 500 et sc .
		System.out.println("min = "+min) ;//affiche la variable min
		System.out.print("Votre max ? ");
		max = EntreeClavier.getEntierDansIntervalleExclu(2, 500, sc) ;//fait appel à la méthode getEntierDansIntervalleExclu de la classe EntreeClavier avec comme paramètres 2, 500 et sc .
		System.out.println("max = "+max) ;//affiche la variable max
		Testeur.getTestsAlgosSurAgglomerationAleatoire(min, max) ;//fait appel à la méthode getTestsAlgosSurAgglomerationAleatoire de la classe Testeur avec comme paramètres min et max .
	}
	/**
	 * permet de résoudre le problème en laissant le choix de l'algorithme à l'utilisateur
	 * 
	 * @param agg qui est l'agglomération chargée/ crée par l'utilisateur
	 * @param sc qui est une entrée clavier qui permet de prendre le choix de l'algorithme de l'utilisateur
	 */
	private static void resoudAutoAvecChoixAlgorithme(Agglomeration agg, Scanner sc) { // quel type également ici et les arguments
		int choix = -1 ;//initialise la variable choix a -1
		Affichage.afficherListeAlgorithmes() ;//fait appel à la méthode afficherListeAlgorithmes de la classe Affichage
		choix = EntreeClavier.getEntierDansIntervalleExclu(0, Testeur.algos.length, sc) ; //fait appel à la méthode getEntierDansIntervalleExclu de la classe EntreeClavier avec comme paramètres 0, Testeur.algos.length et sc .
		//TODO vérifier qu'il n'y a pas de problème à l'exécution avec -1 0 6 etc
		System.out.println("Votre choix : "+choix) ;//affiche la variable choix
		if(choix != 0) Testeur.getTestSurAlgo(agg, choix-1) ;//si choix est  différent de 0, fait appel à la méthode getTestSurAlgo de la classe Testeur avec comme paramètres agg et choix-1.
	}
}
