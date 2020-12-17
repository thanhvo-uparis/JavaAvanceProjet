package main.menus.util;

import main.algorithmique.testeur.Testeur;

public class Affichage {

	private static void enteteMenu() {
		System.out.println("\n\n-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-\n") ;
	}
	
	public static void afficherIntro() {
		System.out.println("Bienvenue !\n\nCette application est là pour faciliter la gestion de votre agglomération, de ses villes et de ses écoles.") ;
	}
	
	public static void afficherMenuChoixTypeChargement() {
		enteteMenu() ;
		System.out.println("\n  - Menu de chargement d'agglomérations - \n") ;
		System.out.println("1 - Charger une agglomération vous-même");
		System.out.println("2 - Charger une agglomération depuis un fichier");
		System.out.println("0 - Terminer le programme") ;
		System.out.print("Veuillez entrer votre choix : ");
	}

	public static void afficherMenuResolution() {
		enteteMenu() ;
		System.out.println("\n  - Menu de résolution des écoles - \n") ;
		System.out.println("1 - Résoudre manuellement");
		System.out.println("2 - Résoudre automatiquement");
		System.out.println("3 - Sauvegarder votre agglomération dans un fichier CA") ;
		System.out.println("0 - Terminer le programme") ;
		System.out.print("Veuillez entrer votre choix : ");
	}
	
	public static void afficherMenuAjoutRoutes() {
		enteteMenu() ;
		System.out.println("\n  - Menu d'ajout des routes - \n") ;
		System.out.println("1 - Ajouter une route");
		System.out.println("2 - J'ai rentré toutes mes routes");
		System.out.println("0 - Terminer du programme") ;
		System.out.print("Veuillez entrer votre choix : ");
	}
	
	public static void afficherDemandeConnexite() {
		System.out.println("Voulez-vous que votre agglomération soit forcément connexe ?") ;
		System.out.println("1 - Oui") ;
		System.out.println("0 - Non") ;
	}
	
	public static void afficherMenuAjoutRetraitEcoles() {
		enteteMenu() ;
		System.out.println("\n  - Menu d'ajout et de retrait d'écoles - \n") ;
		System.out.println("1 - Ajouter une ecole");
		System.out.println("2 - Retirer une ecole");
		System.out.println("3 - J'ai fini mes modifications sur les écoles");	
		System.out.println("0 - Terminer le programme");
		System.out.print("Veuillez entrer votre choix : ");
	}
	
	public static void afficherMenuPrincipalAlgorithmes() {
		enteteMenu() ;
		System.out.println("\n  - Menu Principal Algorithmes - \n") ;
		System.out.println("1 - Appliquer l'algorithme de votre choix sur votre agglomération") ;
		System.out.println("2 - Appliquer notre meilleur algorithme sur votre agglomération") ;
		System.out.println("3 - Comparer les résultats de nos différents algorithmes sur votre agglomération") ;
		System.out.println("4 - Tester nos algorithmes sur une série d'agglomérations aléatoires") ;
		System.out.println("5 - Retourner au menu de création/chargement d'agglomérations") ;
		System.out.println("0 - Terminer le programme") ;
		System.out.print("Votre choix ? ") ;
	}
	
	public static void afficherListeAlgorithmes() {
		enteteMenu() ;
		System.out.println("\n\n  - Nos algorithmes - \n") ;
		for(int i = 0 ; i < Testeur.algos.length ; i++) {
			System.out.println((i+1)+" - "+Testeur.algos[i]) ;
		}
		System.out.println("0 - Retourner au menu précédent sans exécuter d'algorithme") ;
		System.out.println("(Si vous ne savez pas quoi choisir, allez consulter la page Algos.java de notre JavaDoc)") ;
		System.out.print("Votre choix ? ") ;
	}
}
