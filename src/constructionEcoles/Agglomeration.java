package constructionEcoles;
import java.util.List;

import constructionEcoles.exceptions.*;

import java.util.ArrayList; 
import java.util.LinkedList;

/**
 * Classe qui definit une agglomération dans le cadre du projet de construction d'écoles.
 * @author Yann Trividic
 * @version 1.0
 */

public class Agglomeration {

	private List<Ville> villes; // le type List permet de ne pas se fermer de portes pour la suite.
	
	/**
	 * Constructeur par défaut de la classe. Il initialise le ArrayList villes avec une taille de 10
	 * @see Agglomeration(Ville...)
	 * @see Agglomeration(int)
	 */
	public Agglomeration() {
		this.villes = new ArrayList<Ville>() ;
	}
	
	/**
	 * Constructeur initialisant ses villes grâce aux villes passées en arguments
	 * @param villes	varargs de villes qui seront stockées dans l'attribut villes de l'objet
	 * @see Agglomeration()
	 * @see Agglomeration(int)
	 */
	public Agglomeration(Ville...villes) {
		this.villes = new ArrayList<Ville>() ;		
		for (Ville a : villes) addVille(a) ;
	}
	
	/**
	 * Constructeur initialisant le ArrayList villes avec la taille de l'entier passé en argument tant qu'il ne dépasse pas 26
	 * @param nbVilles	le nombre de villes à générer dans l'agglo
	 * @see Agglomeration(Ville...)
	 * @see Agglomeration(int)
	 */
	public Agglomeration(int nbVilles) {
		this.villes = new ArrayList<Ville>(nbVilles);
		char c ;
		for(c = 'a'; c < 'a'+nbVilles; c++) this.villes.add(new Ville(c)) ;
	}
	
	/**
	 * Methode permettant de vérifier si une ville est déjà dans l'agglo
	 * @param		a	Char correspondant à l'attribut key de la ville à vérifier
	 * @exception	ExceptionVille dans le cas où la ville n'existe pas.
	 * @return		v	la ville trouvée dans l'agglo si elle a été trouvée
	 * @see getVille(Ville)
	 */
	public Ville getVille(char a) throws Exception {
		for (Ville v : villes) if (v.getKey() == a) return v ;
		throw new ExceptionVille("La ville "+a+" n'existe pas dans l'agglomération") ;
	}
	
	/**
	 * Methode permettant de vérifier si une ville est déjà dans l'agglo
	 * @param	a	ville à chercher dans l'agglomération
	 * @return	v	la ville trouvée dans l'agglo si elle a été trouvée
	 * @exception	ExceptionVille	lance une exception si la ville n'est pas dans l'agglomération
	 * @see getVille(char)
	 */
	public Ville getVille(Ville a) throws Exception {
		for (Ville v : villes) if (v.getKey() == a.getKey()) return v ;
		throw new ExceptionVille("La ville "+a.getKey()+" n'existe pas dans l'agglomération") ;
	}
	

	//ajoute une ville dans l'agglomération en vérifiant que celle-ci n'est pas déjà dans l'agglo
	private void addVille(Ville a){
		try {
			if(getVille(a.getKey()) == null) villes.add(a) ;
		} catch(Exception e) {
			System.out.println("La ville "+a.getKey()+" est déjà dans l'agglomeration.");
		}
	}
	
	/**
	 * Méthode implémentant l'algorithme principal de la classe permettant une répartition optimisée des 
	 * écoles dans l'agglomération.
	 * @return	un string contenant la liste des villes dans lesquelles seront construites des écoles de manière 
	 * 		 	à ce que les contraintes économiques et d'accessibilité soient respectées en limitant les coûts
	 */
	public String placerEcoles() {
		StringBuilder sb = new StringBuilder() ;
		//Première idée :
		//
		//	Dans un premier temps, placer des écoles dans toutes les villes qui devront forcément en accueillir.
		//	Cela concerne toutes les villes voisines de villes de degré 1 (c'est-à-dire les villes accessibles uniquement via une route)
		//	
		//	Dans un second temps, tant que la contrainte d'accessibilité n'est pas remplie : 
		//		On cherche la ville pour laquelle l'ajout d'une école permettrait au plus grand nombre de ville d'accéder à une école
		//		Ajout d'une école dans la ville trouvée
		//	
		//	Question : 	est-ce qu'il existe des cas où le résultat obtenu ne serait pas optimal ?
		//				dans le cas où on a le choix entre x villes à l'étape 2, comment choisir une ville en particulier ?
		return sb.toString() ;
	}
	
	/**
	 * Methode permettant d'ajouter une route entre deux villes dans le cas où celles-ci ne seraient pas déjà reliées
	 * @param		a	première ville du couple de villes à relier par une route
	 * @param		b	seconde ville du couple de villes à relier par une route
	 * @exception	ExceptionVille dans le cas où les deux villes sont identiques, si elles sont déjà reliées ou si l'une d'elles n'existe pas
	 * @see			ajouterRoute(char, char)
	 */
	public void ajouterRoute(Ville a, Ville b) throws Exception {
		if(a.equals(b)) throw new ExceptionVille("Les deux villes sont identiques") ; // equals ne marche pas ?
		if(getVille(a) == null || getVille(b) == null) throw new ExceptionVille("L'une des villes n'existe pas") ;
		if(a.getVoisins().contains(b)) throw new ExceptionUnicite("Les deux villes sont déjà reliées");
		a.getVoisins().add(b) ;
		b.getVoisins().add(a) ;
	}
	
	/**
	 * Surcharge de la méthode ajouterRoute(Ville, Ville) avec à la place des arguments Ville deux char
	 * @see		ajouterRoute(Ville, Ville)
	 * @param		a	première ville du couple de villes à relier par une route
	 * @exception	ExceptionVille dans le cas où les deux villes sont identiques, si elles sont déjà reliées ou si l'une d'elles n'existe pas
	 * @param		b	seconde ville du couple de villes à relier par une route
	 */
	public void ajouterRoute(char a, char b) throws Exception {
		ajouterRoute(getVille(a), getVille(b)) ;
	}
	
	
	/**
	 * Methode permettant d'ajouter une école dans une ville n'en ayant pas déjà et dans le cas où l'ajout ne briserait pas la contrainte d'Economie.
	 * @param		a					la ville dans laquelle on veut ajouter une école
	 * @exception	ExceptionEconomie 	dans le cas où la ville a déjà une école (à terme, une exception sera aussi lancée si la ville est déjà proche d'une école)
	 * @see			ajouterRoute(Ville, Ville)
	 */
	public void ajouterEcole(Ville a) throws Exception {
		ajouterEcole(a.getKey()) ;

	}
	
	/**
	 * Surcharge de la méthode ajouterEcole(Ville) avec à la place de l'argument Ville un char
	 * @param		c					la ville dans laquelle on veut ajouter une école
	 * @exception	ExceptionEconomie 	dans le cas où la ville a déjà une école (à terme, une exception sera aussi lancée si la ville est déjà proche d'une école)
	 * @see		ajouterEcole(Ville)
	 */
	public void ajouterEcole(char c) throws Exception {
		Ville a = getVille(c) ;
		if(a.getHasEcole()) throw new ExceptionEconomie("La ville a déjà une école.");
		//if(a.hasEcoleVoisins()) throw new ExceptionEconomie("La ville est déjà proche d'une école."); //	décommenter cette ligne pour que la contrainte d'Economie
		a.setHasEcole(true);																			//	soit satisfaite en chaque instant de l'exécution du programme
	}
	
	
	/**
	 * Methode permettant de retirer une école à une ville en faisant passer son attribut hasEcole de true à false 
	 * dans le cas où cet ajout ne brise pas la contrainte d'Accessibilité
	 * @param		a						la ville dans laquelle on essaiera de retirer une école
	 * @exception	ExceptionAccessibilite	si enlever l'école de la ville casse la contrainte d'Accessibilité
	 * @see			retirerEcole(char)
	 */
	public void retirerEcole(Ville a) throws Exception {
		retirerEcole(a.getKey()) ;
	}
	
	/**
	 * Surcharge de la méthode retirerEcole(Ville) avec à la place de l'argument Ville un char
	 * @param		c						la ville dans laquelle on essaiera de retirer une école
	 * @see		retirerEcole(Ville)
	 * @exception	ExceptionAccessibilite	si enlever l'école de la ville casse la contrainte d'Accessibilité
	 */
	public void retirerEcole(char c) throws Exception {
		Ville a = getVille(c) ;
		boolean accessibiliteVoisins = true ;
		if(!a.hasEcoleVoisins() && a.getHasEcole()) throw new ExceptionAccessibilite("La ville "+a.getKey()+" ne serait plus assez proche une école.");
		for(Ville voisin : a.getVoisins()) if(voisin.getNbEcolesAccessibles() == 1 && !voisin.getHasEcole()) accessibiliteVoisins = false ;
		if(!accessibiliteVoisins) throw new ExceptionAccessibilite("L'école de la ville "+a.getKey()+" est l'unique école accessible pour au moins une de ses villes voisines.");
		a.setHasEcole(false);
	}
	
	
	//permet de supprimer toutes les écoles de l'agglomération
	private void clearEcole() {
		for(Ville a : villes) a.setHasEcole(false);
	}
	
	/**
	 * Methode permettant de savoir s'il existe au moins un chemin à travers l'agglomération reliant chaque couple de villes.
	 * Il s'agit d'une implémentation d'un parcours BFS.
	 * @return	un booléen prenant pour valeur true si l'agglomération est connexe et false sinon
	 */
	public boolean estConnexe() {
		LinkedList<Ville> file = new LinkedList<Ville>() ;	//file qui servira à stocker les villes restant à parcourir
		ArrayList<Character> marques = new ArrayList<Character>(0); //villes déjà parcourues, contiendra les identifiants de chaque villes
		
		file.add(villes.get(0)) ; //enfile le premier élément de villes dans la file
		marques.add(villes.get(0).getKey()) ; //la première ville est visitée
		
		while(!file.isEmpty()) { //tant que la file n'est pas vide
			Ville v = file.pollFirst() ; //on défile le dernier élément v de la file
			for(Ville a : v.getVoisins()) { //pour tous les voisins de v
				if(!marques.contains(a.getKey())) { //on regarde si la ville a un identifiant déjà visité
					marques.add(a.getKey()) ; // si elle ne l'est pas, on la marque comme visitée
					file.offer(a) ; //et on l'enfile dans la file
				}
			}
		}
		
		if(marques.size() != villes.size()) return false ; //si tous les éléments de l'agglo n'ont pas été visités, on retourne faux
		return true ; //sinon on retourne vrai
	}
	
	/**
	 * Methode permettant d'afficher la liste des villes possédant des écoles dans l'agglomération.
	 */
	public void afficheVilleAEcole() {
		StringBuilder sb = new StringBuilder() ;
		for(Ville a : villes) {
			if(a.getHasEcole()) sb.append(a.getKey()+" ") ;
		}
		System.out.println(sb) ;
	}
	

	//retourne le nombre d'écoles de l'agglomération
	private int nbEcoles() {
		int c = 0 ;
		for(Ville a : villes) {
			if(a.getHasEcole()) c++ ;
		}
		return c ;
	}
	
	/**
	 * Methode permettant d'afficher toutes les routes de l'agglomération. Il n'y a pas de doublons. 
	 * Par exemple, si [a, b] apparait, alors [b, a] n'apparaitra pas.
	 * @return	un string composé d'autant de lignes qu'il y a de routes.
	 */
	public String afficherRoutes() {
		StringBuilder sb = new StringBuilder() ;
		for(Ville ville : villes) {
			for(Ville voisin : ville.getVoisins()) {
				if(voisin.getKey()>ville.getKey()) sb.append("["+ville.getKey()+", "+voisin.getKey()+"]\n") ;
			}
		}
		return sb.toString() ;
	}
	
	/**
	 * Methode vérifiant si toutes les villes de l'agglomération ont bien accès à une école 
	 * @return	un booléen avec la valeur true si la contrainte d'accessibilité est respectée et false sinon.
	 */
	public boolean respecteAccessibilite() {
		for(Ville v : villes) if(!v.getHasEcole() && !v.hasEcoleVoisins()) return false ;
		return true ;
	}
	
	@Override
	public String toString() {
		return("Agglomération de "+villes.size()+" villes et "+nbEcoles()+" écoles.") ;
	}
}
