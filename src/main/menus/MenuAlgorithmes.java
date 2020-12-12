package main.menus;

import java.util.InputMismatchException;
import java.util.Scanner;

import main.algorithmique.Algos;
import main.entites.Agglomeration;

public class MenuAlgorithmes {
	protected static void resoudAuto(Agglomeration agg) {// quel type également ici et les arguments
		
		Scanner sc = new Scanner(System.in) ;
		int choice = 0;
		boolean exit;
		do {
			System.out.println("\n Choix de l'algorithme");
			System.out.println("1- Algortihme naif");
			System.out.println("2- Algorithme 2 ");
			System.out.println("3- Algorithme optimal(file prioritaire)");
			System.out.println("4- Algorithme par soustraction");
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
				System.out.println("nombre d'itérations?");
				Algos.algorithmeApproximationNaif(agg, sc.nextInt());// je ne sais pas si l'appel est bon au niveau des arguments.
				break;
			case 2 : 
				System.out.println("nombre de répétitions ? ");
				Algos.algorithmeApproximationUnPeuMoinsNaif(agg, sc.nextInt());// je ne sais pas si l'appel est bon au niveau des arguments.
				break ;
			case 3 : 
				System.out.println("Garder les écoles déja construites? true or false");
				boolean garderEcolesConstruites;
				if (sc.nextLine() == "true") {
					garderEcolesConstruites = true;
				}	else garderEcolesConstruites = false;
	
	
				Algos.algorithmeFilePriorite(agg, garderEcolesConstruites);// je ne sais pas si l'appel est bon au niveau des arguments.
			case 4 :
				// je dois encore faire l'appel a l'algo 4 
				break;
			default :
				System.err.println("Choix incorrect.") ;
				break;
			}
			exit = (choice>0 ) && (choice< 4);
			sc.close();
		} while (!exit);
	}
}
