package main.menus.util;

import java.util.InputMismatchException;
import java.util.Scanner;

public class EntreeClavier {
	
	public static int getEntierDansIntervalleExclu(int min, int max, Scanner sc) {
		int valeur = min-1 ;
		do {
			try {
				min = sc.nextInt() ;
			} catch(InputMismatchException e) {
				System.err.print("Il faut rentrer un entier entre "+min+" et "+max+". Recommencez : ") ;
				sc.next();
				valeur = max+1 ;
			}
		} while(valeur < min || valeur > max) ;
		return valeur ;
	}

}
