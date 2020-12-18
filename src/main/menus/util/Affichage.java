package main.menus.util;

import main.algorithmique.testeur.Testeur;

public class Affichage {

	private static void enteteMenu() {
		System.out.println("\n\n-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-\n") ;
	}
	
	private static void politesse() {
		System.out.print("Veuillez entrer votre choix : ");
	}
	
	public static void afficherIntro() {
		System.out.println("Bienvenue !\n\nCette application est là pour faciliter la gestion de votre agglomération, de ses villes et de ses écoles.") ;
		System.out.println("Programme développé par Anthony Baptista, Thanh-Vo Cong et Yann Trividic dans le cadre du cours IF05X030 de l'Université de Paris.") ;
	}
	
	public static void afficherMenuChoixTypeChargement() {
		enteteMenu() ;
		System.out.println("\n  - Menu de chargement d'agglomérations - \n") ;
		System.out.println("1 - Charger une agglomération vous-même");
		System.out.println("2 - Charger une agglomération depuis un fichier");
		System.out.println("3 - Générer aléatoirement une agglomération connexe");
		System.out.println("0 - Quitter le programme") ;
		politesse() ;
	}

	public static void afficherMenuResolution() {
		enteteMenu() ;
		System.out.println("\n  - Menu de résolution des écoles - \n") ;
		System.out.println("1 - Résoudre manuellement la position de vos écoles");
		System.out.println("2 - Résoudre automatiquement la position de vos écoles");
		System.out.println("3 - Sauvegarder votre agglomération dans un fichier CA") ;
		System.out.println("4 - Retourner au menu de chargement d'agglomérations") ;
		System.out.println("0 - Quitter le programme") ;
		politesse() ;
	}
	
	public static void afficherMenuAjoutRoutes() {
		enteteMenu() ;
		System.out.println("\n  - Menu d'ajout des routes - \n") ;
		System.out.println("1 - Ajouter une route");
		System.out.println("2 - J'ai rentré toutes mes routes");
		System.out.println("0 - Quitter le programme") ;
		politesse() ;
	}
	
	public static void afficherDemandeConnexite() {
		System.out.println("\nVoulez-vous que votre agglomération soit forcément connexe ?") ;
		System.out.println("1 - Oui") ;
		System.out.println("0 - Non") ;
		politesse() ;
	}
	
	public static void afficherDemandeNomsAuClavier() {
		System.out.println("\nVoulez-vous que vous rentrer vous-même le nom de vos villes ?") ;
		System.out.println("1 - Oui") ;
		System.out.println("0 - Non") ;
		politesse() ;
	}
	
	public static void afficherMenuAjoutRetraitEcoles() {
		enteteMenu() ;
		System.out.println("\n  - Menu d'ajout et de retrait d'écoles - \n") ;
		System.out.println("1 - Ajouter une école");
		System.out.println("2 - Retirer une école");
		System.out.println("3 - J'ai fini mes modifications sur les écoles");	
		System.out.println("0 - Quitter le programme");
		politesse() ;
	}
	
	public static void afficherMenuPrincipalAlgorithmes() {
		enteteMenu() ;
		System.out.println("\n  - Menu Principal Algorithmes - \n") ;
		System.out.println("1 - Appliquer l'algorithme de votre choix sur votre agglomération") ;
		System.out.println("2 - Appliquer notre meilleur algorithme sur votre agglomération") ;
		System.out.println("3 - Comparer les résultats de nos différents algorithmes sur votre agglomération") ;
		System.out.println("4 - Tester nos algorithmes sur une série d'agglomérations aléatoires (les résultats seront enregistrés dans un fichier CSV)") ;
		System.out.println("5 - Sauvegarder votre agglomération dans un fichier CA") ;
		System.out.println("6 - Retourner au menu de chargement d'agglomérations") ;
		System.out.println("0 - Quitter le programme") ;
		politesse() ;
	}
	
	public static void afficherListeAlgorithmes() {
		enteteMenu() ;
		System.out.println("\n\n  - Nos algorithmes - \n") ;
		for(int i = 0 ; i < Testeur.algos.length ; i++) {
			System.out.println((i+1)+" - "+Testeur.algos[i]) ;
		}
		System.out.println("0 - Retourner au menu précédent sans exécuter d'algorithme") ;
		System.out.println("(Si vous ne savez pas quoi choisir, allez consulter la page Algos.java de notre JavaDoc)") ;
		politesse() ;
	}
}
