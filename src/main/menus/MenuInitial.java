package main.menus;

import java.util.InputMismatchException;
import java.util.Scanner;

import main.algorithmique.Algos;
import main.entites.Agglomeration;
import main.io.LectureEcriture;
import main.io.ChargeurProprietes;

public class MenuInitial {
	
	public static void lancement() {
		ChargeurProprietes.chargerProprietes();
		MenuInitial.menuCreationAgglo();
		MenuInitial.menuPrincipal(); //
	}
		
	public static Agglomeration menuCreationAgglo() {
		// Charger une agglomération
				//		a) charger manuellement
				//		b) charger é partir d'un fichier (LectureEcriture.lectureDepuisFichier("string") ;)
		Scanner sc = new Scanner(System.in);
		int choice = 0; 
		boolean exit ;

		do {
			System.out.println("\n  - Charger une agglomération - ") ;
			System.out.println("1 - Charger manuellement");
			System.out.println("2 - Charger depuis un fichier");
			System.out.print("Veuillez entrer votre choix : ");

			switch (choice) {
			case 1 :
				aggloManuelle();
				sc.close();
				break ;
			case 2 :
				System.out.println("Rentrez le chemin vers le fichier nécessaire é la création de l'agglomération");
				LectureEcriture.lectureDepuisFichier(sc.nextLine()) ;
				sc.close();
				break ;
			default :
				System.out.println("choix incorrect") ;
				break ;
			}
			exit = (choice == 1) || (choice == 2);		
		} while (!exit);
		return agg;
	}
	
	private static void menuPrincipal() {
		Scanner sc = new Scanner(System.in);
		int choice=0;
		boolean exit;
		do {
			System.out.println("\n  - Menu principal - ") ;
			System.out.println("1 - Résoudre manuellement");
			System.out.println("2 - Résoudre automatiquement");
			System.out.println("3 - sauvegarder") ;
			System.out.println("4 - tester la complexité des algorithmes") ;
			System.out.println("5 - fin") ;
			System.out.print("Veuillez entrer votre choix : ");
	
			switch(choice) {
			case 1 :
				// Résoud manuellement le probleme en faisant appel é la méthode resoudManuelle
				resoudManuelle(agg);//Régler le probleme de la variable agg
	
				//je ne sais plus faire l'appel de la méthode pour moi c'etait resoudManuelle(agg); (basique mais je bloque)
				break ;
			case 2 :
				// Résoud automatiquement le probleme en faisant appel é la méthode resoudAuto
				MenuAlgorithmes.resoudAuto(agg);
	
				break ;
			case 3 : 
				//Sauvegarde le fichier en utilisant la méthode ecritureVersFichier de la classe LectureEcriture.
				System.out.println("veuillez entrer le chemin oé sauvegarder le fichier");
				LectureEcriture.ecritureVersFichier(sc.nextLine(), agg);
				break;
			case 4 : 
				//Complexité des algorithmes 
				break;
			case 5 : 
	
				// Fermeture du programme
				System.out.println("Fin du programme.");
				sc.close();
				break ;
			default :
				System.out.println("Choix incorrect.");
				break ;
			}
			exit = (choice != 5) ;
			sc.close();
		}while (!exit);
	}
	
//termine la boucle quand l'utilisateur choisit de fermer le programme (choix 5).
	
	public static Agglomeration aggloManuelle(){// je ne sais pas quel type de retour il faut utiliser 
		Scanner sc = new Scanner(System.in);
	
		int nbVilles = 0 ;
	
		while(nbVilles < 1  || nbVilles > 26) { //Tant que le nombre n'est pas compris entre 1 et 26, on demande a l'utilisateur de saisir un nombre.
			//TODO faire en sorte que la contrainte entre 1 et 26 saute
			System.out.print("Entrez un nombre de villes ");
			try {
				nbVilles = sc.nextInt(); 
			} catch(InputMismatchException e) {
				System.err.println("Il faut rentrer un int.") ;
				sc.next() ;
				nbVilles = 0 ;
			}
		}
	
		Agglomeration agg = new Agglomeration(nbVilles);
		System.out.println("Creee : "+agg.toString()+"\nLes ecoles sont dans les villes suivantes : ");
		agg.afficheVilleAEcole() ;
		int choice = 0; 
		boolean exit ;
	
		do {
			System.out.println("\n  - Menu 1 - ") ;
			System.out.println("1 - ajouter une route");
			System.out.println("2 - j'ai rentre toutes mes routes");
			System.out.println("3 - fin du programme\n") ;
			System.out.print("Veuillez entrer votre choix : ");
			try {
				choice = sc.nextInt() ;
			} catch(InputMismatchException e) {
				System.err.println("Il faut rentrer un int.") ;
				sc.next();
				choice = 4 ;
			}
			switch(choice) {
			case 1 :
				//A MODIFIER tant que "Ville a" est null, demander a nouveau une ville
				try {
					System.out.print("\nEntrez la cle de la premiere ville a relier : ");
					String villeA = sc.nextLine() ; //retourne une ville
					System.out.print("Entrez la cle de la deuxieme ville a relier : ");
					String villeB = sc.nextLine() ; //retourne une ville
					System.out.println(villeA+" "+villeB) ;
					agg.ajouterRoute(villeA, villeB) ;
					System.out.println("Liste des routes de l'agglomeration :\n") ;
					agg.afficheRoutes();
				} catch(Exception e) {
					System.err.println(e);
				}
				break ;
			case 2 :
				System.out.println("Est-ce que toutes les villes sont bien accessibles...\n"+(agg.estConnexe()?"Oui !\n":"Non ! Continuez d'ajouter des routes")) ;
				break ;
			case 3 :
				System.out.println("Fin du programme.") ;
				sc.close();
				System.exit(0) ;
				break ;
			default : 
				System.out.println("Choix incorrect.") ;
				break ;
			}
			exit = (choice == 2) && agg.estConnexe() ;
	
		} while (!exit) ;
		return agg;//tant que la ville n'est pas connexe et que l'utilisateur ne veut pas sortir
	
		
		/* On cree ici le 1er menu qui va nous afficher les choix suivants : 
		 * 1/ ajouter une route
		 * 2/ j'ai rentre toutes mes routes" 
		 * 3/ fin
		
				   La boucle while permet de verifier que l'agglomeration est connexe, c'est-a-dire qu'elle verifie que toute les villes sont reliees par au minimum une route.
				   Tant que ce n'est pas le cas on continue de  demander a l'utilisateur d'ajouter des routes.
		
				   Si on choisit ajouter une route, on utilise la fonction void ajouterRoute en prenant en argument les deux villes rentrees au clavier.
		
				   Si on choisit 2, une verification est faite pour s'assurer que toutes villes sont reliees.
				   Si ce n'est pas le cas, on affiche un message d'erreur a l'utilisateur afin qu'il continue. 
		 */

		
		System.out.println("Fin de la premiere etape. Bilan :\n") ;
		System.out.println(agg.toString()+"Les routes sont les suivantes \n") ;//regler la fonction afficheroutes
		agg.afficheRoutes() ;
	}
	
	private Agglomeration resoudManuelle(agg) {//pareil je ne sais pas quel type utiliser et si l'argument est bon
		int choice = 0;
		Scanner sc = new Scanner(System.in) ;
		while (choice != 3){ 
			System.out.println("\n  - Menu 2 - ") ;
			System.out.println("1 - ajouter une ecole");
			System.out.println("2 - retirer une ecole");
			System.out.println("3 - fin");	
			System.out.print("Veuillez entrer votre choix : ");
	
			try {
				choice = sc.nextInt() ;
			} catch(InputMismatchException e) {
				System.err.println("Il faut rentrer un int.") ;
				sc.next();
				choice = 4 ;
			}
	
			switch(choice) {
			case 1 :
				System.out.println("Entrez la cle de la ville dans laquelle vous souhaitez ajouter une ecole" );
				try {
					agg.ajouterEcole(sc.nextLine());
				} catch(Exception e) {
					System.err.println(e);
				}
				break ;
	
			case 2:
				System.out.println("Entrez la cle de la ville dans laquelle vous souhaitez retirer une ecole : " );
				try {
					agg.retirerEcole(sc.nextLine());
				} catch (Exception e) {
					System.err.println(e);
				}
				break ;
	
			case 3:
				
				break ;
	
			default : 
				System.err.println("Choix incorrect.") ;
				break ;
			}
			System.out.println("Les ecoles sont dans les villes suivantes : ") ;
			agg.afficheVilleAEcole();
		}
		System.out.println("\nBilan : \n"+agg.toString()+"\nLes ecoles sont dans les villes suivantes : ") ;
		agg.afficheVilleAEcole();
		System.out.print("Liste des routes de l'agglomeration :\n") ;
		agg.afficheRoutes();
		System.out.println("Contrainte accessibilite respectee ? "+(agg.respecteAccessibilite()?"Oui.":"Non.")) ;
		System.out.println("Sortie du programme.") ;
		sc.close();
	}

}
