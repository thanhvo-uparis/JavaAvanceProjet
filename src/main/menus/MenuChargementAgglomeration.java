package main.menus;

import java.util.Scanner;

import main.entites.Agglomeration;
import main.io.LectureEcriture;
import main.menus.util.*;

public class MenuChargementAgglomeration {

	public static Agglomeration choixTypeChargement(Scanner sc) {

		Affichage.afficherMenuChoixTypeChargement() ;
		int choix = EntreeClavier.getEntierDansIntervalleExclu(0, 2, sc) ;
		Agglomeration agg = null ;
		
		switch (choix) {
		case 1 :
			//Charge une agglomàration de facon manuelle grâce à la fonction aggloManuelle()
			agg = ajoutRoutesAuClavier(sc);
			break ;
		case 2 :
			//Charge l'agglomàration depuis un fichier grâce à la fonction LectureDepuisFichier de la classe LectureEcriture
			System.out.print("Rentrez le chemin (absolu) vers le fichier CA nécessaire à la création de l'agglomération : ");
			agg = LectureEcriture.lectureDepuisFichier(EntreeClavier.getCheminValideCA(sc)) ;
			break ;
		case 0 : 
			// Fermeture du programme
			System.out.println("Fin du programme.");
			sc.close();
			System.exit(1);
			break ;
		default :
			//affiche choix incorrect si le choix rentrà n'est pas 1 ou 2 
			System.out.println("Erreur lors du choix de votre type de chargement.") ;
			agg = null ;
			break ;
		}
		return agg ;
	}
	
	public static void menuResolutionProbleme(Agglomeration agg, Scanner sc) {

		Affichage.afficherMenuResolution() ;
		int choix = EntreeClavier.getEntierDansIntervalleExclu(0, 4, sc) ;

		switch(choix) {
		case 1 :
			// Résoud manuellement le probleme en faisant appel à la méthode resoudManuelle qui prend comme paramètre la variable agg
			ajoutEtRetraitEcolesAuClavier(agg, sc);
			break ;
		case 2 :
			// Résoud automatiquement le probleme en faisant appel à la méthode resoudAuto
			MenuAlgorithmes.menuPrincipalAlgorithmes(agg);
			break ;
		case 3 : 
			//Sauvegarde le fichier en utilisant la méthode ecritureVersFichier de la classe LectureEcriture.
			sc.nextLine();
			System.out.println("Veuillez entrer le chemin absolu où sauvegarder votre fichier");
			LectureEcriture.ecritureVersFichier(sc.nextLine(), agg);
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
	
	public static Agglomeration ajoutRoutesAuClavier(Scanner sc){

		System.out.print("Vous avez choisi de rentrer votre agglomération au clavier.\n"
				+ "Attention, plus ce nombre est grand et plus il vous prendra du temps de créer cette agglomération.\n"
				+ "Entrez le nombre de villes de votre agglomération : ");
		int nbVilles = EntreeClavier.getEntierDansIntervalleExclu(2, 150, sc) ;

		//Crée une agglomération en prenant comme paramètre nbVilles
		Agglomeration agg = new Agglomeration(nbVilles);
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
					System.err.println(e);
				}
				break ;
			case 2 :
				//Vérifie que l'agglomération est bien connexe. Le cas échéant, on demande à l'utilisateur de continuer à ajouter des routes
				System.out.println("Est-ce que toutes les villes sont bien accessibles...\n"
									+ (agg.estConnexe()?"Oui !\n":"Non ! "
									+ (connexiteNecessaire?"Continuez d'ajouter des routes":""))) ;
				break ;
				
			case 3 :
				//quitte le menu
				System.out.println("Fin du programme.") ;
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
		return agg;
	}

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
