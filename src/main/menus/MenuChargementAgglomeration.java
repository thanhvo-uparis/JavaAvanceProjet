package main.menus;

import java.util.Scanner;

import main.entites.Agglomeration;
import main.io.LectureEcriture;
import main.menus.util.*;

public class MenuChargementAgglomeration {
	/**
	 * Méthode qui permet à l'utilisateur de choisir le type de chargement de l'agglomération(manuelle/depuis un fichier)
	 * 
	 * @param sc entrée clavier qui permet de prendre le choix de l'utilisateur
	 * @return l'agglomération agg 
	 */

	public static Agglomeration choixTypeChargement(Scanner sc) {

		Affichage.afficherMenuChoixTypeChargement() ;//fait appel à la méthode afficherMenuTypeChargement de la classe Affichage 
		int choix = EntreeClavier.getEntierDansIntervalleExclu(0, 3, sc) ;/*fait appel à la méthode getEntierDansIntervalleExclu de la classe EntreeClavier afin de s'assurer que l'utilisateur
		choisisse un nombre entre 1 et 3 .*/
		Agglomeration agg = null ;//affecte la valeur null à une nouvelle agglomération agg
		int nbVilles; //décalaration de la variable entière nbVilles.
		
		switch (choix) {
		case 1 :
			System.out.print("Vous avez choisi de rentrer votre agglomération au clavier.\n"
					+ "Attention, plus ce nombre est grand et plus il vous prendra du temps de créer cette agglomération.\n"
					+ "Entrez le nombre de villes de votre agglomération : ") ;
			nbVilles = EntreeClavier.getEntierDansIntervalleExclu(2, 150, sc) ;/*fait appel à la méthode getEntierDansIntervalleExclu de la classe EntreeClavier afin de s'assurer
			que l'utilisateur choisisse un nombre entre 2 et 150 .(nombre de villes dans l'agglomération).*/
			
			Affichage.afficherDemandeNomsAuClavier();
			boolean rentreNomsAuClavier = (EntreeClavier.getEntierDansIntervalleExclu(0, 1, sc) == 1)?true:false ;//Demande à l'utilisateur si il veut rentrer lui-même les noms au clavier.
			
			System.out.print("Entrez le nombre de villes de votre agglomération : ");

			agg = (rentreNomsAuClavier)? EntreeClavier.nomsVillesAuClavier(sc, nbVilles):new Agglomeration(nbVilles) ;
					
			ajoutRoutesAuClavier(sc, agg) ;//fait appel à la méthode ajoutRoutesAuClavier qui permet à l'utilisateur de rentrer les routes reliant les villes au clavier.
			break ;
		case 2 :
			//Charge l'agglomération depuis un fichier grâce à la fonction LectureDepuisFichier de la classe LectureEcriture
			System.out.print("Rentrez le chemin (absolu) vers le fichier CA nécessaire à la création de l'agglomération : ");
			String chemin = EntreeClavier.getCheminValideCA(sc) ;//fait appel à la méthode getCheminValideCA de la classe LectureEcriture qui prend comme paramètre sc et permet de vérifier que le chemin de l'utilisateur est un fichier valide.
			agg = LectureEcriture.lectureDepuisFichier(chemin) ;//fait appel à la méthode lectureDepuisFichier de la classe LectureEcriture avec comme paramètre chemin qui mène au fichier à lire.
			if(agg == null) {
				System.out.println("Retour au menu de chargement d'agglomération.") ;//Si l'agglomération est nulle, on retourne au menu principal.
				MenuPrincipal.lancement(sc, false);
			}
			break ;
		case 3 :
			System.out.print("Entrez le nombre de villes de votre agglomération : ");
			nbVilles = EntreeClavier.getEntierDansIntervalleExclu(2, 500, sc) ;/*fait appel à la méthode getEntierDansIntervalleExclu de la classe EntreeClavier afin de s'assurer
			que l'utilisateur choisisse un nombre entre 2 et 500 .(nombre de villes dans l'agglomération).*/
			agg = main.algorithmique.testeur.GenerateurAgglomeration.randomAggloConnexeGenerateur(nbVilles) ;//permet de créer une agglomération aléatoire.
			agg.afficheBilan();// affiche l'agglomération.
			break ;
		case 0 : 
			// Fermeture du programme
			System.out.println("Fin du programme.");
			sc.close();//libération du scanner.
			System.exit(1);
			break ;
		default :
			//affiche choix incorrect si le choix rentré n'est pas 1 ou 2 
			System.out.println("Erreur lors du choix de votre type de chargement.") ;
			agg = null ;
			break ;
		}
		return agg ;
	}
	/**
	 * méthode qui permet de créer le menu de résolution de problème: choisir entre résoudre le problème manuellement/résoudre automatiquement/sauvegarder le fichier/ fermer le programme.
	 * @param agg qui correspond à l'agglomération chargée/crée par l'uitilisateur 
	 * @param sc qui permet de prendre le choix de l'utilisateur
	 */
	public static void menuResolutionProbleme(Agglomeration agg, Scanner sc) {

		Affichage.afficherMenuResolution() ;
		int choix = EntreeClavier.getEntierDansIntervalleExclu(0, 4, sc) ;

		switch(choix) {
		case 1 :
			// Résoud manuellement le probleme en faisant appel à la méthode resoudManuelle qui prend comme paramètres agg et sc
			ajoutEtRetraitEcolesAuClavier(agg, sc);
			break ;
		case 2 :
			// Résoud automatiquement le probleme en faisant appel à la méthode resoudAuto qui prend comme paramètres agg et sc
			MenuAlgorithmes.menuPrincipalAlgorithmes(agg, sc);
			break ;
		case 3 : 
			//Sauvegarde le fichier en utilisant la méthode ecritureVersFichier de la classe LectureEcriture qui prend comme paramètres agg et sc. La variable sc prendra comme valeur le chemin où sauvegarder le fichier.
			sc.nextLine();
			System.out.println("Veuillez entrer le chemin absolu où vous souhaitez sauvegarder votre fichier : ");
			LectureEcriture.ecritureVersFichier(sc.nextLine(), agg);
			menuResolutionProbleme(agg, sc) ;
			break;
		case 0 : 
			// Fermeture du programme
			System.out.println("Fin du programme.");
			sc.close();
			System.exit(1);
			break ;
		default :
			System.out.println("Choix incorrect.");
			break ;
		}
	}
	
	
	/**
	 * Méthode qui permet d'ajouter une route au clavier (dans le cas d'une création manuelle de l'agglomération
	 * @param sc qui permet de prendre les entrées clavier de l'utilisateur (notamment les villes à relier par une route
	 * @return l'agglomération crée agg
	 */
	public static void ajoutRoutesAuClavier(Scanner sc, Agglomeration agg){

		agg.afficheBilan();
		Affichage.afficherDemandeConnexite() ;
		
		boolean connexiteNecessaire = (EntreeClavier.getEntierDansIntervalleExclu(0, 1, sc) == 1)?true:false ;
		int choice = -1 ; 
		boolean exit ;
		
		do {
			Affichage.afficherMenuAjoutRoutes() ;
			choice = EntreeClavier.getEntierDansIntervalleExclu(0, 2, sc) ;
			switch(choice) {
			case 1 :
				sc.nextLine() ;
				//Permet à l'utilisateur d'ajouter une route entre 2 villes en lui demandant de relier deux villes via leurs clés. Affiche ensuite la liste des routes de l'agglomération.
				try {
					System.out.print("\nEntrez la clé de la première ville à relier : ");
					String villeA = sc.nextLine() ; //retourne une ville
					System.out.print("Entrez la clé de la seconde ville à relier : ");
					String villeB = sc.nextLine() ; //retourne une ville
					System.out.println(villeA+" "+villeB) ;
					agg.ajouterRoute(villeA, villeB) ;
					System.out.println("Liste des routes de l'agglomération :\n") ;
					agg.afficheRoutes();
				} catch(Exception e) {
					System.err.println(e.getMessage());
				}
				break ;
			case 2 :
				//Vérifie que l'agglomération est bien connexe. Le cas échéant, on demande à l'utilisateur de continuer à ajouter des routes
				System.out.println("Est-ce que toutes les villes sont bien accessibles...\n"
									+ (agg.estConnexe()?"Oui !\n":("Non ! "
									+ (connexiteNecessaire?"Continuez d'ajouter des routes.":"Mais c'est comme vous-voulez...")))) ;
				break ;
			case 0 :
				//quitte le menu
				System.out.println("Toutes vos routes ont été rentrées, votre agglomération est la suivante : ") ;
				sc.close();
				System.exit(1) ;
				break ;
				
			default : 
				System.err.println("Erreur lors du choix du menu d'ajout de routes.") ;
				break ;
			}
			exit = (choice == 2) && (agg.estConnexe() || !connexiteNecessaire);

		} while (!exit) ;

		System.out.println("Votre agglomération a bien été créée.") ;
		agg.afficheBilan();
	}
	/**
	 * méthode qui permet à l'utilisateur d'ajouter ou retirer les écoles dans les villes.
	 * @param agg qui correspond à l'agglomération chargée/crée
	 * @param sc qui permet de prendre les entrée clavier de l'utilisateur(ville dans laquelle ajouter/retirer une école)
	 * @return l'agglomération crée agg
	 */
	private static Agglomeration ajoutEtRetraitEcolesAuClavier(Agglomeration agg, Scanner sc) {
		int choix = 0;
		
		while (choix != 3){ 
			Affichage.afficherMenuAjoutRetraitEcoles() ;
			choix = EntreeClavier.getEntierDansIntervalleExclu(0, 3, sc) ;
			
			switch(choix) {
			case 1 :
				//Permet d'ajouter une école . Il n'est posssible d'en ajouter une que si il n'y a pas déjà une école dans la ville en question.
				System.out.print("Entrez la clé de la ville dans laquelle vous souhaitez ajouter une école" );
				try {
					agg.ajouterEcole(sc.nextLine());
				} catch(Exception e) {
					System.err.println(e);
				}
				break ;

			case 2:
				//Permet de retirer une école . Il n'est possbile de la retirer que si la ville possède une école.
				System.out.print("Entrez la clé de la ville dans laquelle vous souhaitez retirer une école : " );
				try {
					agg.retirerEcole(sc.nextLine());
				} catch (Exception e) {
					System.err.println(e);
				}
				break ;

			case 3:
				System.out.println("Ajout des écoles terminé.");
				break ;

			default : 
				System.err.println("Choix incorrect.") ;
				break ;
			}
			System.out.println("Les écoles sont dans les villes suivantes : ") ;
			agg.afficheVilleAEcole();//Affiche les villes qui possèdent des écoles.
		}
		agg.afficheBilan();
		return agg ;
	}
}
