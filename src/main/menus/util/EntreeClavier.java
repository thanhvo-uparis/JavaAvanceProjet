package main.menus.util;

import java.util.InputMismatchException;
import java.util.Scanner;

public class EntreeClavier {
	
	public static int getEntierDansIntervalleExclu(int min, int max, Scanner sc) {
		int valeur = min-1 ;
		int count = 0 ;
		//System.out.println("min = "+min+" max = "+max) ;
		do {
			if(count != 0) System.out.print("Réessayez :") ;
			try {
				valeur = sc.nextInt() ;
			} catch(InputMismatchException e) {
				System.err.print("Il faut rentrer un entier entre "+min+" et "+max+". ") ;
				sc.next();
				valeur = max+1 ;
			}
			count ++ ;
			if(count == 10) {
				System.err.print("Nombre d'essais maximum autorisés dépassé. Sortie du programme");
				sc.close();
				System.exit(-1);
			}
		} while(valeur < min || valeur > max) ;
		return valeur ;
	}

}
