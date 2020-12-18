package main.menus;

import java.util.Scanner;

import main.menus.util.Affichage;
import main.menus.util.FonctionnalitesCommunes;
import main.algorithmique.testeur.Testeur;
import main.entites.Agglomeration;
import main.io.EntreeClavier;

/**
 * Menu de gestion des algorithmes
 * @author Anthony Baptista
 * @author Yann Trividic
 */
public class MenuAlgorithmes {
	
	//TODO il ne serait pas très long d'implémenter la possibilité de générer un CSV bilan de tous les algos

	/**
	 * Méthode qui permet de créer le menu principal des algorithmes afin de laisser choisir à l'utilisateur une résolution auto avec un choix d'algorithme, 
	 * des testeurs, resolution avec une agglomération aléatoire
	 * @param agg qui est l'agglomération crée ou chargée par l'utilisateur.
	 */
	protected static void menuPrincipalAlgorithmes(Agglomeration agg, Scanner sc) {
		
		Affichage.afficherMenuPrincipalAlgorithmes() ;// affichage des choix possibles
		int choix = EntreeClavier.getEntierDansIntervalleInclu(0, 6, sc) ; // retourne le choix de l'utilisateur par rapport aux possibilités enoncées
		
		switch(choix) {
		case 0 :
			FonctionnalitesCommunes.finProgramme(sc);
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
			FonctionnalitesCommunes.sauvegarderAgglomerationFichier(agg, sc);
			break;
		case 6 :
			MenuChargementAgglomeration.choixTypeChargement(sc) ;
		default :
			System.out.println("Choix incorrect.");
			break ;
		}
		
		menuPrincipalAlgorithmes(agg, sc) ;//fait appel à la méthode menuPrincipalAlgorithmes avec comme paramètres agg et sc 
	}
	
	/**
	 * Méthode permettant de résoudre le problème en prenant une série d'agglomérations aléatoires afin de tester les algorithmes.
	 * Enregistre les rapports dans un fichier rapports.csv dans le dossier du choix de l'utilisateur.
	 * @param sc qui est une entrée clavier pour lire le nombre de villes de l'agglomération à générer
	 */
	private static void resoudAutoAvecAgglomerationsAleatoires(Scanner sc) {
		System.out.println("Vous devez choisir la taille minimale des agglomérations à générer (>1) et la taille maximale des agglomérations à générer (<=500)") ;
		System.out.println("Attention : Plus le nombre choisi est grand, plus le temps d'exécution de la méthode sera long.") ;
		System.out.print("Votre min ? ");
		int min = EntreeClavier.getEntierDansIntervalleInclu(2, 500, sc) ; // donne une valeur cohérente à min
		System.out.print("Votre max ? ");
		int max = EntreeClavier.getEntierDansIntervalleInclu(2, 500, sc) ; // et à max
		
		System.out.print("Veuillez rentrer le chemin absolu du dossier dans lequel vous souhaitez enregistrer vos rapports : ") ;
		String chemin = EntreeClavier.getNomFichierEcritureValideAvecExtension(sc, "csv") ;
		
		Testeur.ecrireRapportsDansCSV(Testeur.getTestsAlgosSurAgglomerationAleatoire(min, max), chemin) ; 	// l'ordre de min et max n'est pas important pour cette 
	}
	
	/**
	 * permet de résoudre le problème en laissant le choix de l'algorithme à l'utilisateur
	 * @param agg qui est l'agglomération chargée/ crée par l'utilisateur
	 * @param sc qui est une entrée clavier qui permet de prendre le choix de l'algorithme de l'utilisateur
	 */
	private static void resoudAutoAvecChoixAlgorithme(Agglomeration agg, Scanner sc) {
		Affichage.afficherListeAlgorithmes() ;
		int choix = EntreeClavier.getEntierDansIntervalleInclu(0, Testeur.algos.length, sc) ; // retourne le numéro associé à l'algo choisi
		if(choix != 0) Testeur.getTestSurAlgo(agg, choix-1) ; // lance l'algorithme choisi sur l'agglomération chargée 
	}
}
