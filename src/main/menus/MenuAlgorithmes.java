package main.menus;

import java.util.InputMismatchException;
import java.util.Scanner;

import main.algorithmique.testeur.Testeur;
import main.entites.Agglomeration;

public class MenuAlgorithmes {
	
	//TODO il ne serait pas très long d'implémenter la possibilité de générer un CSV bilan de tous les algos
	protected static void menuPrincipalAlgorithmes(Agglomeration agg) {
		int choix = -1 ;
		Scanner sc = new Scanner(System.in) ;
		
		do {
			afficherMenuPrincipalAlgorithmes() ;
			choix = getEntierDansIntervalleExclu(0, 5, sc) ;
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
				; //TODO mettre la méthode pour rentrer dans le menu de création d'agglomération
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
		min = getEntierDansIntervalleExclu(2, 500, sc) ;
		System.out.println("min = "+min) ;
		System.out.print("Votre max ? ");
		max = getEntierDansIntervalleExclu(2, 500, sc) ;
		System.out.println("max = "+max) ;
		Testeur.getTestsAlgosSurAgglomerationAleatoire(min, max) ;
	}
	
	private static int getEntierDansIntervalleExclu(int min, int max, Scanner sc) {
		int valeur = min-1 ;
		do {
			try {
				min = sc.nextInt() ;
			} catch(InputMismatchException e) {
				System.err.println("Il faut rentrer un entier entre "+min+" et "+max+". Recommencez : ") ;
				sc.next();
				valeur = max+1 ;
			}
		} while(valeur < min || valeur > max) ;
		return valeur ;
	}

	private static void resoudAutoAvecChoixAlgorithme(Agglomeration agg, Scanner sc) { // quel type également ici et les arguments
		int choix = -1 ;
		afficherListeAlgorithmes() ;
		choix = getEntierDansIntervalleExclu(0, Testeur.algos.length, sc) ; //TODO vérifier qu'il n'y a pas de problème à l'exécution avec -1 0 6 etc
		System.out.println("Votre choix : "+choix) ;
		if(choix != 0) Testeur.getTestSurAlgo(agg, choix-1) ;
	}
	
	private static void afficherMenuPrincipalAlgorithmes() {
		System.out.println("  - Menu Principal Algorithmes - ") ;
		System.out.println("1 - Appliquer l'algorithme de votre choix sur votre agglomération") ;
		System.out.println("2 - Appliquer notre meilleur algorithme sur votre agglomération") ;
		System.out.println("3 - Comparer les résultats de nos différents algorithmes sur votre agglomération") ;
		System.out.println("4 - Tester nos algorithmes sur une série d'agglomérations aléatoires") ;
		System.out.println("5 - Retourner au menu de création/chargement d'agglomérations") ;
		System.out.println("0 - Terminer le programme") ;
		System.out.print("Votre choix ? ") ;
	}
	
	private static void afficherListeAlgorithmes() {
		System.out.println("\n\n  - Nos algorithmes - ") ;
		for(int i = 0 ; i < Testeur.algos.length ; i++) {
			System.out.println((i+1)+" - "+Testeur.algos[i]) ;
		}
		System.out.println("0 - Retourner au menu précédent sans exécuter d'algorithme") ;
		System.out.println("(Si vous ne savez pas quoi choisir, allez consulter la page Algos.java de notre JavaDoc)") ;
		System.out.print("Votre choix ? ") ;
	}
}
