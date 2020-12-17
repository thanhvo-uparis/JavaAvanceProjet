package main.menus;

import java.util.InputMismatchException;
import java.util.Scanner;

import main.algorithmique.Algos;
import main.entites.Agglomeration;
import main.io.LectureEcriture;
import main.io.ChargeurProprietes;

public class MenuInitial {
	
	public static void lancement() {
		MenuInitial.menuCreationAgglo();
		MenuInitial.menuPrincipal(agg); //PROBLEME DE VARIABLE ENCORE MAL DECLAR2 JE SUPPOSE A APPARU DEPUIS QUE J4AI REGLE LES AUTRES PROBLEMES DE LA VARIABLE AGG
	}
		
	public static void menuCreationAgglo() {
		// Charger une agglomération
				//		a) charger manuellement
				//		b) charger à partir d'un fichier (LectureEcriture.lectureDepuisFichier("string") ;)
		Scanner sc = new Scanner(System.in);
		int choice = 0; 
		boolean exit ;

		do {
			System.out.println("\n  - Charger une agglomàration - ") ;
			System.out.println("1 - Charger manuellement");
			System.out.println("2 - Charger depuis un fichier");
			System.out.print("Veuillez entrer votre choix : ");

			switch (choice) {
			case 1 :
				//Charge une agglomàration de facon manuelle grâce à la fonction aggloManuelle()
				aggloManuelle();
				sc.close();
				break ;
			case 2 :
				//Charge l'agglomàration depuis un fichier grâce à la fonction LectureDepuisFichier de la classe LectureEcriture
				System.out.println("Rentrez le chemin vers le fichier nécessaire à la cràation de l'agglomération");
				LectureEcriture.lectureDepuisFichier(sc.nextLine()) ;
				sc.close();
				break ;
			default :
				//affiche choix incorrect si le choix rentrà n'est pas 1 ou 2 
				System.out.println("choix incorrect") ;
				break ;
			}
			exit = (choice == 1) || (choice == 2);		
		} while (!exit);
		
	}
	//Crée le menuPrincipal qui prend comme argument l'agglomération agg et qui nous propose 4 choix
	private void menuPrincipal(Agglomeration agg) {//LIé AU PROBLEME DE VARIABLE LIGNE 15 
		Scanner sc = new Scanner(System.in);
		int choice=0;
		boolean exit;
		do {
			System.out.println("\n  - Menu principal - ") ;
			System.out.println("1 - Résoudre manuellement");
			System.out.println("2 - Résoudre automatiquement");
			System.out.println("3 - sauvegarder") ;
			System.out.println("4 - fin") ;
			System.out.print("Veuillez entrer votre choix : ");
	
			switch(choice) {
			case 1 :
				// Résoud manuellement le probleme en faisant appel à la méthode resoudManuelle qui prend comme paramètre la variable agg
				resoudManuelle(agg);
	
				break ;
			case 2 :
				// Résoud automatiquement le probleme en faisant appel à la méthode resoudAuto
				MenuAlgorithmes.menuPrincipalAlgorithmes(agg);
	
				break ;
			case 3 : 
				//Sauvegarde le fichier en utilisant la méthode ecritureVersFichier de la classe LectureEcriture.
				System.out.println("veuillez entrer le chemin où sauvegarder le fichier");
				LectureEcriture.ecritureVersFichier(sc.nextLine(), agg);
				break;
			case 4 : 
	
				// Fermeture du programme
				System.out.println("Fin du programme.");
				sc.close();
				break ;
			default :
				//affiche "choix incorrect" en cas de mauvais choix
				System.out.println("Choix incorrect.");
				break ;
			}
			exit = (choice != 4) ;
			sc.close();
		}while (!exit);
	}
	
//termine la boucle quand l'utilisateur choisit de fermer le programme (choix 4).
	
	
	
	//Méthode
	public static Agglomeration aggloManuelle(){
		Scanner sc = new Scanner(System.in);
	
		int nbVilles = 0 ;
	
		
			System.out.print("Entrez un nombre de villes ");
			try {
				nbVilles = sc.nextInt(); 
			} catch(InputMismatchException e) {
				System.err.println("Il faut rentrer un int.") ;
				sc.next() ;
				nbVilles = 0 ;
			}// Demande à l'utilisateur de rentrer un nombre de ville qu'on va attribuer à la variable nbvilles
		
	
			//Crée une agglomération en prenant comme paramètre nbVilles
		Agglomeration agg = new Agglomeration(nbVilles);
		System.out.println("Creee : "+agg.toString()+"\nLes ecoles sont dans les villes suivantes : ");
		agg.afficheVilleAEcole() ;
		int choice = 0; 
		boolean exit ;
	
		//Crée un menu qui permet à l'utilisateur de créer son agglomération entièrement
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
				//Permet à l'utilisateur d'ajouter une route entre 2 villes en lui demandant de relier deux villes via leurs clés. Affiche ensuite la liste des routes de l'agglomération.
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
				
				//Vérifie que l'agglomération est bien connexe. Le cas échéant, on demande à l'utilisateur de continuer à ajouter des routes
				System.out.println("Est-ce que toutes les villes sont bien accessibles...\n"+(agg.estConnexe()?"Oui !\n":"Non ! Continuez d'ajouter des routes")) ;
				break ;
			case 3 :
				//quitte le menu
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
		

		
		System.out.println("Fin de la premiere etape. Bilan :\n") ;
		System.out.println(agg.toString()+"Les routes sont les suivantes \n") ;//affiche les routes à la fin de la première étape
		agg.afficheRoutes() ;
		return agg;
	}
	
	//Méthode qui permet de résoudre manuellement le problème en prenant en paramètre l'agglomération agg via un menu a 3 choix.
	private void resoudManuelle(Agglomeration agg) {
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
				
				//Permet d'ajouter une école . Il n'est posssible d'en ajouter une que si il n'y a pas déjà une école dans la ville en question.
				System.out.println("Entrez la cle de la ville dans laquelle vous souhaitez ajouter une ecole" );
				try {
					agg.ajouterEcole(sc.nextLine());
				} catch(Exception e) {
					System.err.println(e);
				}
				break ;
	
			case 2:
				
				//Permet de retirer une école . Il n'est possbile de la retirer que si la ville possède une école.
				System.out.println("Entrez la cle de la ville dans laquelle vous souhaitez retirer une ecole : " );
				try {
					agg.retirerEcole(sc.nextLine());
				} catch (Exception e) {
					System.err.println(e);
				}
				break ;
	
			case 3:
				System.out.println("Fin");
				
				break ;
	
			default : 
				System.err.println("Choix incorrect.") ;
				break ;
			}
			System.out.println("Les ecoles sont dans les villes suivantes : ") ;
			agg.afficheVilleAEcole();//Affiche les villes qui possèdent des écoles.
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
