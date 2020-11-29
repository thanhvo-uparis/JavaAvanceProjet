package constructionEcoles;

import java.util.Scanner;

import outils.Algos;
import outils.LectureEcriture;

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

		// Charger une agglom�ration
		//		a) charger manuellement
		//		b) charger � partir d'un fichier (LectureEcriture.lectureDepuisFichier("string") ;)

		Scanner sc = new Scanner(System.in);
		int choice = 0; 
		boolean exit ;

		do {
			System.out.println("\n  - Charger une agglom�ration - ") ;
			System.out.println("1 - Charger manuellement");
			System.out.println("2 - Charger depuis un fichier");
			System.out.print("Veuillez entrer votre choix : ");

			switch (choice) {
			case 1 :
				aggloManuelle();
				sc.close();
				break ;
			case 2 :
				System.out.println("Rentrez le chemin vers le fichier n�cessaire � la cr�ation de l'agglom�ration");
				LectureEcriture.lectureDepuisFichier(sc.nextLine()) ;
				sc.close();
				break ;
			default :
				System.out.println("choix incorrect") ;
				break ;
			}
			exit = (choice == 1) || (choice == 2);		
		} while (!exit);
		// Autre menu :
	
		do {
			System.out.println("\n  - Menu principal - ") ;
			System.out.println("1 - R�soudre manuellement");
			System.out.println("2 - R�soudre automatiquement");
			System.out.println("3 - sauvegarder") ;
			System.out.println("4 - tester la complexit� des algorithmes") ;
			System.out.println("5 - fin") ;
			System.out.print("Veuillez entrer votre choix : ");
	
			switch(choice) {
			case 1 :
				// R�soud manuellement le probleme en faisant appel � la m�thode resoudManuelle
				resoudManuelle(agg);
	
				//je ne sais plus faire l'appel de la m�thode pour moi c'etait resoudManuelle(agg); (basique mais je bloque)
				break ;
			case 2 :
				// R�soud automatiquement le probleme en faisant appel � la m�thode resoudAuto
				resoudAuto(agg);
	
				break ;
			case 3 : 
				//Sauvegarde le fichier en utilisant la m�thode ecritureVersFichier de la classe LectureEcriture.
				System.out.println("veuillez entrer le chemin o� sauvegarder le fichier");
				LectureEcriture.ecritureVersFichier(sc.nextLine(), agg);
				break;
			case 4 : 
				//Complexit� des algorithmes 
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
	
		}while (!exit);
	}

	
//termine la boucle quand l'utilisateur choisit de fermer le programme (choix 5).





	// 1) r�soudre manuellement
	// 2) r�soudre automatiquement 
	//		a) laisser le choix de l'algorithme
	//		b) appliquer l'algorithme par d�faut
	// 3) sauvegarder
	// 4) tester la complexit� des algorithmes avec la m�thode Tester.compareAlgorithmes(Agglomeration agg)
	// 5) fin
	
	//Mettre l'ancien main dans une m�thode statique � part 
	
	
	
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

	private static void aggloManuelle(){// je ne sais pas quel type de retopur il faut utiliser 
		Scanner sc = new Scanner(System.in);
	
		int nbVilles = 0 ;
	
		while(nbVilles < 1  || nbVilles > 26) { //Tant que le nombre n'est pas compris entre 1 et 26, on demande a l'utilisateur de saisir un nombre.
			System.out.print("Entrez un nombre de villes entre 1 et 26 : ");
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
					char a = sc.next().charAt(0) ; //retourne une ville
					System.out.print("Entrez la cle de la deuxieme ville a relier : ");
					char b = sc.next().charAt(0) ; //retourne une ville
					System.out.println(a+" "+b) ;
					agg.ajouterRoute(a, b) ;
					System.out.println("Liste des routes de l'agglomeration :\n"+agg.afficherRoutes()) ;
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
	
		} while (!exit) ; //tant que la ville n'est pas connexe et que l'utilisateur ne veut pas sortir
	
		System.out.println("Fin de la premiere etape. Bilan :\n") ;
		System.out.println(agg.toString()+"Les routes sont les suivantes \n"+agg.afficherRoutes()) ;
	}



// On cree ici le 2eme menu qui va permettre a l'utilisateur de soit: 1/ajouter une ecole 2/retirer une ecole 3/fin.

	private void resoudManuelle(Agglomeration agg) {//pareil je ne sais pas quel type utiliser et si l'argument est bon
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
					agg.ajouterEcole(sc.next().charAt(0));
				} catch(Exception e) {
					System.err.println(e);
				}
				break ;
	
			case 2:
				System.out.println("Entrez la cle de la ville dans laquelle vous souhaitez retirer une ecole : " );
				try {
					agg.retirerEcole(sc.next().charAt(0));
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
		System.out.print("Liste des routes de l'agglomeration :\n"+agg.afficherRoutes()) ;
		System.out.println("Contrainte accessibilite respectee ? "+(agg.respecteAccessibilite()?"Oui.":"Non.")) ;
		System.out.println("Sortie du programme.") ;
		sc.close();
	}


	private  void resoudAuto(Agglomeration agg) {// quel type �galement ici et les arguments
	
		int choice = 0;
		boolean exit;
		do {
			System.out.println("\n Choix de l'algorithme");
			System.out.println("1- Algortihme naif");
			System.out.println("2- Algorithme 2 ");
			System.out.println("3- Algorithme optimal");
			System.out.println("Veuillez entrer votre choix");
			try {
				choice = sc.nextInt() ;
			} catch(InputMismatchException e) {
				System.err.println("Il faut rentrer un int.") ;
				sc.next();
				choice = 4 ;
			}
			switch(choice) {
			case 1 :
				System.out.println("nombre d'it�rations?");
				Algos.algorithmeApproximationNaif(sc.nextInt(), agg);// je ne sais pas si l'appel est bon au niveau des arguments.
				break;
			case 2 : 
				System.out.println("nombre de r�p�titions ? ");
				Algos.algorithmeApproximationUnPeuMoinsNaif(sc.nextInt(), agg);// je ne sais pas si l'appel est bon au niveau des arguments.
				break ;
			case 3 : 
				System.out.println("Garder les �coles d�ja construites? true or false");
				boolean garderEcolesConstruites;
				if (sc.nextLine() == "true") {
					garderEcolesConstruites = true;
				}	else garderEcolesConstruites = false;
	
	
				Algos.algorithmeFilePriorite(agg, garderEcolesConstruites);// je ne sais pas si l'appel est bon au niveau des arguments.
			}
			exit = (choice>0 )&& (choice< 4);
	
		}while (!exit);
	}
}

//Me manque encore a g�rer les exceptions que je vais avancer et la complexit� des algorithmes.