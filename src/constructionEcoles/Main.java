package constructionEcoles;

import java.util.Scanner;
import java.util.InputMismatchException ;

/**
 * La classe Main va permettre a l'utilisateur de modeliser son agglomeration en manipulant une instance d'un objet Agglomeration. A l'aide d'un menu, l'utilisateur va pouvoir :
 * 1) instancier une agglomeration de n villes.
 * 2) relier les villes entre elles a l'aide de routes. L'utilisateur peut relier autant de villes qu'il le souhaite tant que celles-ci ne sont pas reliees toutes reliees deux a deux par une route. Une fois qu'il existe au moins un chemin reliant chaque couple de villes, l'utilisateur peut : 
 * 		a. retirer des ecoles tant que ce retrait satisfait la contrainte d'Accessibilite*.
 * 		b. ajouter des ecoles si la ville n'en a pas deja une. (Pour le moment, la contrainte d'economie n'est pas respectee.) 
 * A chaque fois que l'utilisateur retire ou ajoute une ecole, on affiche la liste des villes contenant des ecoles.
 * A la fin du programme, la liste des villes contenant des ecoles est affichees, avec la liste des routes les reliant.
 * @author Anthony Baptista
 * @author Yann Trividic
 * @version 1.0
 */

//TEST COMMIT PAZRTIE 2///

public class Main {
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		int nbVilles = 0 ;
		
		while(nbVilles < 1  || nbVilles > 26) { //Tant que le nombre n'est pas compris entre 1 et 26, on demande a l'utilisateur de saisir un nombre.
			System.out.print("Entrez un nombre de villes entre 1 et 26 : ");
			try {
				nbVilles = sc.nextInt(); 
			} catch(InputMismatchException e) {
				System.out.println("Il faut rentrer un int.") ;
				sc.next() ;
				nbVilles = 0 ;
			}
		}
		
		Agglomeration agg = new Agglomeration(nbVilles);
		System.out.println("Creee : "+agg.toString()+"\nLes ecoles sont dans les villes suivantes : ");
		agg.afficheVilleAEcole() ;
		
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
				System.out.println("Il faut rentrer un int.") ;
				sc.next();
				choice = 4 ;
			}
			switch(choice) {
				case 1 :
					//A MODIFIER tant que "Ville a" est null, demander a nouveau une ville
					try {
						System.out.print("\nEntrez la cle de la premiere ville a relier : ");
						char a = sc.next().charAt(0) ; //retourne une ville
						System.out.print("Entrez la cle de la deuxieme ville a relier : ");
						char b = sc.next().charAt(0) ; //retourne une ville
						System.out.println(a+" "+b) ;
						agg.ajouterRoute(a, b) ;
						System.out.println("Liste des routes de l'agglomeration :\n"+agg.afficherRoutes()) ;
					} catch(Exception e) {
						System.out.println(e);
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
			
		} while (!exit) ; //tant que la ville n'est pas connexe et que l'utilisateur ne veut pas sortir

		System.out.println("Fin de la premiere etape. Bilan :\n") ;
		System.out.println(agg.toString()+"Les routes sont les suivantes \n"+agg.afficherRoutes()) ;
		
		// On cree ici le 2eme menu qui va permettre a l'utilisateur de soit: 1/ajouter une ecole 2/retirer une ecole 3/fin.
		while (choice != 3){ 
			System.out.println("\n  - Menu 2 - ") ;
			System.out.println("1 - ajouter une ecole");
			System.out.println("2 - retirer une ecole");
			System.out.println("3 - fin");	
			System.out.print("Veuillez entrer votre choix : ");
			
			try {
				choice = sc.nextInt() ;
			} catch(InputMismatchException e) {
				System.out.println("Il faut rentrer un int.") ;
				sc.next();
				choice = 4 ;
			}
			
			switch(choice) {
				case 1 :
					System.out.println("Entrez la cle de la ville dans laquelle vous souhaitez ajouter une ecole" );
					try {
						agg.ajouterEcole(sc.next().charAt(0));
					} catch(Exception e) {
						System.out.println(e);
					}
					break ;
					
				case 2:
					System.out.println("Entrez la cle de la ville dans laquelle vous souhaitez retirer une ecole : " );
					try {
						agg.retirerEcole(sc.next().charAt(0));
					} catch (Exception e) {
						System.out.println(e);
					}
					break ;
					
				case 3:
					break ;
					
				default : 
					System.out.println("Choix incorrect.") ;
					break ;
			}
			System.out.println("Les ecoles sont dans les villes suivantes : ") ;
			agg.afficheVilleAEcole();
		}
		System.out.println("\nBilan : \n"+agg.toString()+"\nLes ecoles sont dans les villes suivantes : ") ;
		agg.afficheVilleAEcole();
		System.out.print("Liste des routes de l'agglomeration :\n"+agg.afficherRoutes()) ;
		System.out.println("Contrainte accessibilite respectee ? "+(agg.respecteAccessibilite()?"Oui.":"Non.")) ;
		System.out.println("Sortie du programme.") ;
		sc.close();
	}
}
