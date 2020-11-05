package constructionEcoles;

import java.util.Scanner;
import java.util.InputMismatchException ;

/**
 * La classe Main va permettre à l'utilisateur de modéliser son agglomération en manipulant une instance d'un objet Agglomeration. A l'aide d'un menu, l'utilisateur va pouvoir :
 * 1) instancier une agglomération de n villes.
 * 2) relier les villes entre elles à l'aide de routes. L'utilisateur peut relier autant de villes qu'il le souhaite tant que celles-ci ne sont pas reliées toutes reliées deux à deux par une route. Une fois qu'il existe au moins un chemin reliant chaque couple de villes, l'utilisateur peut : 
 * 		a. retirer des écoles tant que ce retrait satisfait la contrainte d'Accessibilité*.
 * 		b. ajouter des écoles si la ville n'en a pas déjà une. (Pour le moment, la contrainte d'économie n'est pas respectée.) 
 * A chaque fois que l'utilisateur retire ou ajoute une école, on affiche la liste des villes contenant des écoles.
 * A la fin du programme, la liste des villes contenant des écoles est affichées, avec la liste des routes les reliant.
 * @author Anthony Baptista
 * @author Yann Trividic
 * @version 1.0
 */

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
		System.out.println("Creee : "+agg.toString()+"\nLes écoles sont dans les villes suivantes : ");
		agg.afficheVilleAEcole() ;
		
		
	
		
		/* On cree ici le 1er menu qui va nous afficher les choix suivants : 
		 * 1/ ajouter une route
		 * 2/ j'ai rentré toutes mes routes" 
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
			System.out.println("2 - j'ai rentré toutes mes routes");
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
					//A MODIFIER tant que "Ville a" est null, demander à nouveau une ville
					try {
						System.out.print("Entrez la première ville a relier : ");
						char a = sc.next().charAt(0) ; //retourne une ville
						System.out.print("Entrez la deuxieme ville a relier : ");
						char b = sc.next().charAt(0) ; //retourne une ville
						System.out.println(a+" "+b) ;
						agg.ajouterRoute(a, b) ;
						System.out.println("Liste des routes de l'agglomération :\n"+agg.afficherRoutes()) ;
					} catch(Exception e) {
						System.out.println(e);
					}
					break ;
				case 2 :
					System.out.println("Est-ce que toutes les villes sont bien accessibles...\n"+(agg.estConnexe()?"Oui !\n\n":"Non ! Continuez d'ajouter des routes")) ;
					System.out.println(choice) ;
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

		System.out.println(agg.toString()) ;
		
		/* On cr�e ici le 2eme menu qui va permettre � l'utilisateur de soit: 1/ajouter une ecole 2/retirer une ecole 3/ fin.
		 * 
		 * Si l'on choisit ajouter une ecole, on utilise la fonction void ajouterEcole en utilisant comme argument la ville rentr�e au clavier par l'utilisateur.
		 * 
		 * Si l'on choisit retirer une �cole, on utilise la fonction void retirerEcole en utilisant comme argument la ville rentr�e au clavier par l'utilisateur.
		 * 
		 * Si l'on choisit fin , le programme s'arrete.
		 */
		
		while (choice != 3){ 
			System.out.println("\n  - Menu 2 - ") ;
			System.out.println("1 - ajouter une ecole");
			System.out.println("2 - retirer une ecole");
			System.out.println("3 - fin");	
			System.out.print("Veuillez entrer votre choix : ");
			choice = sc.nextInt() ;
			
			switch(choice) {
				case 1 :
					System.out.println("Entrez la ville dans laquelle vous souhaitez ajouter une ecole" );
					try {
						agg.ajouterEcole(sc.next().charAt(0));
					} catch(Exception e) {
						System.out.println(e);
					}
					break ;
				case 2:
					System.out.println("Entrez la ville dans laquelle vous souhaitez retirer une ecole" );
					try {
						agg.retirerEcole(sc.next().charAt(0));
					} catch (Exception e) {
						System.out.println(e);
					}
					break ;
				case 3:
				agg.afficheVilleAEcole();
			}
			System.out.println("Bilan : \n"+agg.toString()+"\nLes écoles sont dans les villes suivantes : ") ;
			System.out.println("Liste des routes de l'agglomération :\n"+agg.afficherRoutes()) ;
			System.out.println("Sortie du programme.") ;
		}
		sc.close();
	}
}
