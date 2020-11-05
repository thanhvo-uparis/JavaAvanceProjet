package constructionEcoles;

import java.util.Scanner;
import java.io.*;

/* La classe Main va permettre à l'utilisateur de modéliser son agglomération en manipulant une instance d'un objet Agglomeration. A l'aide d'un menu, l'utilisateur va pouvoir :
 * 1) instancier une agglomération de n villes.
 * 2) relier les villes entre elles à l'aide de routes. L'utilisateur peut relier autant de villes qu'il le souhaite tant que celles-ci ne sont pas reliées toutes reliées deux à deux par une route. Une fois qu'il existe au moins un chemin reliant chaque couple de villes, l'utilisateur peut : 
 * 		a. retirer des écoles tant que ce retrait satisfait la contrainte d'Accessibilité*.
 * 		b. ajouter des écoles si la ville n'en a pas déjà une. (Pour le moment, la contrainte d'économie n'est pas respectée.) 
 * A chaque fois que l'utilisateur retire ou ajoute une école, on affiche la liste des villes contenant des écoles.
 * A la fin du programme, la liste des villes contenant des écoles est affichées, avec la liste des routes les reliant.
 */

public class Main {

	//On demande � l'utilisateur un nombre de villes qui servira � construire l'agglom�ration. Ce nombre doit etre compris entre 1 et 26 auquel cas on affiche une erreur.//
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Entrechoice un nombre de villes ");
		Agglomeration agg = new Agglomeration(sc.nextInt());
		//Il faut que tu ajoutes ici une sécurité dans le cas où l'utilateur rentre une valeur erronée
		
		
		/* On cr�e ici le 1er menu qui va nous afficher les choix suivants : 1/ajouter une route ou 2/fin.
		 
		   La boucle while permet de verifier que l'agglomeration est connexe, c'est-�-dire qu'elle v�rifie que toute les villes sont r�li�esd par au minimium une route.Tant que ce n'est pas le cas on continue 
		   de  demander � l'utilisateur d'ajouter des routes.
		  
		   Si on choisit ajouter une route, on utilise la fonction void ajouterRoute en prenant en argument les deux villes rentr�es au clavier.
		 
		   Si on choisit fin, une v�rification est faite pour s'assurer que toutes villes sont reli�es. On utilise pour cela la fonction boolean estConnexe.
		   Si ce n'est pas le cas, on affiche un message d'erreur � l'utilisateur afin qu'il continue. 
		*/
		break;
		
		int choice = 0;
		BufferedReader syl1 = new BufferedReader (new InputStreamReader(System.in));
		choice = Integer.parseInt(syl1.readLine());
	
		do {
			System.out.println("1 - ajouter une route");
			System.out.println("2 - j'ai rentré toutes mes routes");
			System.out.println("3 - fin du programme") ;
			System.out.println("Veuillechoice entrer votre choix");
			switch(choice) {
				case 1:
					//A MODIFIER tant que "Ville a" est null, demander à nouveau une ville
					System.out.println("Entrechoice-la première ville a relier : ");
					Ville a = agg.hasVille(sc.next().charAt(0)) ; //retourne une ville ou retourne null si la ville n'a pas été trouvée
					
					//Faire de même pour la seconde ville, Ville b ;
					Ville b = agg.hasVille(sc.next().charAt(0)) ; //retourne une ville ou retourne null si la ville n'a pas été trouvée
					try {
						agg.ajouterRoute(a, b);
					} catch(Exception e) {
						System.out.println(e) ;
					}
				case 2:
				case 3:
					System.out.println("Fin du programme.") ;
					sc.close();
					System.exit(0) ;
			}
		} while (!agg.estConnexe() && (choice != 2)) ; //ce serait bien qu'ici l'utilisateur sache si la ville est connexe ou non.
		
		System.out.println("Toutes vos villes sont accessibles.") ;
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
			System.out.println("1-ajouter une ecole");
			System.out.println("2-retirer une ecole");
			System.out.println("3-fin");	
			System.out.println("Veuillechoice entrer votre choix");
			
			switch(choice){ //Je te laisse t'occuper de calquer ce qui a été dans le premier menu
			
			case 1:
				agg.ajouterEcole(nextVille);
				agg.afficheVilleAEcole();
			case 2:
				agg.retirerEcole(nextVille);
				agg.afficheVilleAEcole();
			case 3:
				
				agg.afficheVilleAEcole();
				System.out.println("fin du programme");
			}
		}
		sc.close();
	}
}
