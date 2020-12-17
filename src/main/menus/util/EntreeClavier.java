package main.menus.util;

import java.io.File;

import java.util.InputMismatchException;
import java.util.Scanner;

public class EntreeClavier {
	
	public static int getEntierDansIntervalleExclu(int min, int max, Scanner sc) {
		int valeur = min-1 ;
		int compteur = 0 ;
		//System.out.println("min = "+min+" max = "+max) ;
		do {
			if(compteur != 0) System.out.print("Votre nombre doit être compris entre "+min+" et "+max+". Réessayez : ") ;
			try {
				valeur = sc.nextInt() ;
			} catch(InputMismatchException e) {
				System.err.print("Il faut rentrer un entier. ") ;
				sc.next();
				valeur = max+1 ;
			}
			compteur ++ ;
			if(compteur == 10) {
				System.err.print("Nombre d'essais maximum autorisés dépassé. Sortie du programme");
				sc.close();
				System.exit(-1);
			}
		} while(valeur < min || valeur > max) ;
		System.out.print("\n");
		return valeur ;
	}

    public static String getCheminValideCA(Scanner sc) {
    	sc.nextLine();
    	String chemin ;
    	int compteur = 0 ;
    	do {
    		chemin = sc.nextLine() ;
    		//System.out.println(chemin) ;
	    	if(!new File(chemin).exists()) {
	    		chemin = "" ;
	    		System.err.print("Le chemin que vous avez rentré n'existe pas. Recommencez : ") ;
	    	} else {
		    	String [] decoupe = chemin.split("\\.") ;
		    	//System.out.println(decoupe[decoupe.length-1]) ;
		    	if(!decoupe[decoupe.length-1].toLowerCase().equals("ca")) {
		    		chemin = "" ;
		    		System.err.print("Le fichier que vous avez indiqué n'est pas un fichier CA. Recommencez : ") ;
		    	}
	    	}
			compteur ++ ;
			if(compteur == 10) {
				System.err.print("Nombre d'essais maximum autorisés dépassé. Sortie du programme");
				sc.close();
				System.exit(-1);
			}
    	} while(chemin == "") ;
   	 return chemin ;
    }
}
