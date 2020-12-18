package main.menus.util;

import java.io.File;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import main.entites.Agglomeration;

public class EntreeClavier {
	
	public static int nbEssaisMax = 10 ; 
	
	public static int getEntierDansIntervalleInclu(int min, int max, Scanner sc) {
		int valeur = min-1 ;
		int compteur = 0 ;
		//System.out.println("min = "+min+" max = "+max) ;
		do {
			if(compteur != 0) System.err.print("Votre nombre doit être compris entre "+min+" et "+max+". Réessayez : ") ;
			try {
				valeur = sc.nextInt() ;
			} catch(InputMismatchException e) {
				System.err.print("Il faut rentrer un entier. ") ;
				sc.next();
				valeur = max+1 ;
			}
			compteur ++ ;
			if(compteur == nbEssaisMax) {
				System.err.println("Nombre d'essais maximum autorisés dépassé. Sortie du programme");
				FonctionnalitesCommunes.finProgramme(sc);
			}
		} while(valeur < min || valeur > max) ;
		System.out.print("Votre choix : "+valeur+"\n");
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
			if(compteur == nbEssaisMax) {
				System.err.println("Nombre d'essais maximum autorisés dépassé. Sortie du programme");
				FonctionnalitesCommunes.finProgramme(sc);
			}
    	} while(chemin == "") ;
   	 return chemin ;
    }
    
    public static boolean dossierExiste(String chemin) {
    	File f = new File(chemin) ;
    	if(f.exists() && f.isDirectory()) return true ;
    	return false ;
    }
    
    public static String getDossierValide(Scanner sc) {
    	sc.nextLine() ;
    	String chemin ;
    	int compteur = 0 ;
    	do {
    		if (compteur != 0) System.err.print("Dossier invalide. Réessayez : ") ;
    		chemin = sc.nextLine() ;
    		compteur ++ ;
			if(compteur == nbEssaisMax) {
				System.err.println("Nombre d'essais maximum autorisés dépassé. Sortie du programme");
				FonctionnalitesCommunes.finProgramme(sc);
			}
    	} while(!dossierExiste(chemin)) ;
    	if(chemin.endsWith("/")) chemin = chemin.substring(0,chemin.length()-1) ;
    	return chemin ;
    }
    
    public static String getNomFichierEcritureValide(Scanner sc) {
    	String dossier = getDossierValide(sc) ;
    	System.out.print("Veuillez entrer le nom de votre fichier : ") ;
    	return dossier+"/"+sc.nextLine().replace(" ", ""); //prend en compte les espaces, mais ne fait pas grand chose d'autre... à améliorer
    }
    
    public static String ajouterExtension(String nomFichier, String extension) {
    	String [] decoupe = nomFichier.split("\\.") ;
    	if(!decoupe[decoupe.length-1].toLowerCase().equals(extension)) nomFichier += "."+extension.toLowerCase() ;
    	return nomFichier ;
    }
    
    public static String getNomFichierEcritureValideAvecExtension(Scanner sc, String extension) {
    	return ajouterExtension(getNomFichierEcritureValide(sc), extension) ;
    }
    
	public static Agglomeration nomsVillesAuClavier(Scanner sc, int nbVilles){
		sc.nextLine();
		System.out.print("\n");
		String nom ;
		boolean contient ;
		ArrayList<String> noms = new ArrayList<String>() ;
		for(int i = 0 ; i < nbVilles ; i++) {
			do {
				System.out.print("Nom de la ville #"+(i+1)+" : ") ;
				nom = sc.nextLine() ;
				if((contient = noms.contains(nom)) == true) System.err.println("Vous avez déjà proposé la ville "+nom+". Réessayez.\n") ; 
			} while(contient) ;
			noms.add(nom) ;
		}
		return new Agglomeration(noms) ;
	}
}
