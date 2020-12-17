package main.menus;

import java.util.Scanner;

import main.menus.util.Affichage;
import main.menus.util.EntreeClavier;
import main.algorithmique.testeur.Testeur;
import main.entites.Agglomeration;

public class MenuAlgorithmes {
	
	//TODO il ne serait pas très long d'implémenter la possibilité de générer un CSV bilan de tous les algos
	protected static void menuPrincipalAlgorithmes(Agglomeration agg) {
		int choix = -1 ;
		Scanner sc = new Scanner(System.in) ;
		
		do {
			Affichage.afficherMenuPrincipalAlgorithmes() ;
			choix = EntreeClavier.getEntierDansIntervalleExclu(0, 5, sc) ;
			System.out.println("Votre choix : "+choix) ;
			
			switch(choix) {
			case 0 :
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
			case 5 :
				MenuPrincipal.lancement(sc) ;
			default :
				break ;
			}
		} while(choix != 0) ;

		sc.close();
		System.out.println("\n\nFin du programme.") ;		
	}
	
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

	private static void resoudAutoAvecChoixAlgorithme(Agglomeration agg, Scanner sc) { // quel type également ici et les arguments
		int choix = -1 ;
		Affichage.afficherListeAlgorithmes() ;
		choix = EntreeClavier.getEntierDansIntervalleExclu(0, Testeur.algos.length, sc) ; //TODO vérifier qu'il n'y a pas de problème à l'exécution avec -1 0 6 etc
		System.out.println("Votre choix : "+choix) ;
		if(choix != 0) Testeur.getTestSurAlgo(agg, choix-1) ;
	}
}
