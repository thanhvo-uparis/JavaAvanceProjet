package constructionEcoles;
import java.util.List;

import constructionEcoles.exceptions.*;

import java.util.ArrayList; 
import java.util.LinkedList;

// Contraintes :	faut-il que l'utilisateur soit totalement libre et que les contraintes soient vérifiées à la fin ?
// 					ou que les contraintes soient respectées en chaque instant du programme


/**
 * Classe qui definit une agglomeration dans le cadre
 ∗ du projet de construction d'écoles.
 * @author Yann Trividic
 * @version 1.0
 */

public class Agglomeration {

	private List<Ville> villes; // le type List permet de ne pas se fermer de portes pour la suite.
	
	/**
	 * Constructeur par défaut de la classe. Il initialise le ArrayList villes avec une taille de 10
	 */
	public Agglomeration() {
		this.villes = new ArrayList<Ville>() ;
	}
	
	/**
	 * Constructeur initialisant ses villes grâce aux villes passées en arguments
	 * @param villes	varargs de villes qui seront stockées dans l'attribut villes de l'objet
	 */
	public Agglomeration(Ville...villes) {
		this.villes = new ArrayList<Ville>() ;		
		for (Ville a : villes) addVille(a) ;
	}
	
	/**
	 * Constructeur initialisant le ArrayList villes avec la taille de l'entier passé en argument tant qu'il ne dépasse pas 26
	 * @param villes	tableau de villes qui seront stockées dans l'attribut villes de l'objet
	 */
	public Agglomeration(int nb_villes) {
		this.villes = new ArrayList<Ville>(nb_villes);
		char c ;
		for(c = 'a'; c < 'a'+nb_villes; c++) this.villes.add(new Ville(c)) ;
	}
	
	/**
	 * Methode permettant de vérifier si une ville est déjà dans l'agglo
	 * @param		a	Char correspondant à l'attribut key de la ville à vérifier
	 * @exception	ExceptionVille dans le cas où la ville n'existe pas.
	 * @return		v	la ville trouvée dans l'agglo si elle a été trouvée
	 */
	public Ville hasVille(char a) throws Exception {
		for (Ville v : villes) if (v.getKey() == a) return v ;
		throw new ExceptionVille("La ville n'existe pas") ;
	}
	
	/**
	 * Methode permettant de vérifier si une ville est déjà dans l'agglo
	 * @see hasVille(char)
	 */
	public Ville hasVille(Ville a) throws Exception {
		for (Ville v : villes) if (v.getKey() == a.getKey()) return v ;
		throw new ExceptionVille("La ville n'existe pas") ;
	}
	
	/**
	 * Methode permettant d'ajouter une ville dans une agglomeration. 
	 * @param	a	ville à ajouter
	 */
	private void addVille(Ville a){
		try {
			if(hasVille(a.getKey()) == null) villes.add(a) ;
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
		//[...] à définir
		return sb.toString() ;
	}
	
	/**
	 * Methode permettant d'ajouter une route entre deux villes dans le cas où celles-ci ne seraient pas déjà reliées
	 * @param		a	première ville du couple de villes à relier par une route
	 * @param		b	seconde ville du couple de villes à relier par une route
	 * @exception	ExceptionVille dans le cas où les deux villes sont identiques, si elles sont déjà reliées ou si l'une d'elles n'existe pas
	 */
	public void ajouterRoute(Ville a, Ville b) throws Exception {
		if(a.equals(b)) throw new ExceptionVille("Les deux villes sont identiques") ; // equals ne marche pas ?
		if(hasVille(a) == null || hasVille(b) == null) throw new ExceptionVille("L'une des villes n'existe pas") ;
		if(a.getVoisins().contains(b)) throw new ExceptionUnicite("Les deux villes sont déjà reliées");
		a.getVoisins().add(b) ;
		b.getVoisins().add(a) ;
	}
	
	/**
	 * Méthode permettant d'ajouter une école à une ville en faisant passer son attribut hasEcole de false à true 
	 * dans le cas où cet ajout ne brise pas la contrainte Economique
	 * @param		a					la ville dans laquelle on essaiera d'ajouter une école
	 * @exception	ExceptionEconomie	si ajouter une école dans la ville casse la contrainte Economique
	 */
	public void ajouterEcole(Ville a) throws Exception {
		hasVille(a) ;
		if(a.getHasEcole()) throw new ExceptionEconomie("La ville a déjà une école.");
		if(a.hasEcoleVoisins()) throw new ExceptionEconomie("La ville est déjà proche d'une école.");
		a.setHasEcole(true);
	}
	
	/**
	 * Methode permettant de retirer une école à une ville en faisant passer son attribut hasEcole de true à false 
	 * dans le cas où cet ajout ne brise pas la contrainte d'Accessibilité
	 * @param		a						la ville dans laquelle on essaiera de retirer une école
	 * @exception	ExceptionAccessibilite	si enlever l'école de la ville casse la contrainte d'Accessibilité
	 */
	public void retirerEcole(Ville a) throws Exception {
		hasVille(a) ;
		if(!a.hasEcoleVoisins() && a.getHasEcole()) throw new ExceptionAccessibilite("La ville ne serait plus assez proche une école.");
		a.setHasEcole(false);
		//pour l'instant, on peut retirer des écoles dans le cas où au moins l'un des voisins a une école.
		//cela pose un problème sur la contrainte d'accessibilité si l'école retirée était la seule école accessible à certaines villes.
	}
	
	/**
	 * Methode permettant de retirer toutes les écoles des villes
	 */
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
	
	/**
	 * Methode permettant de savoir combien il y a d'écoles dans l'agglomération
	 * @return	un int correspondant au nombre de villes dans l'agglomération
	 */
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
	
	public boolean respecteAccessibilite() {
		for(Ville v : villes) if(!v.getHasEcole() && !v.hasEcoleVoisins()) return false ;
		return true ;
	}
	
	@Override
	public String toString() {
		return("Agglomération de "+villes.size()+" villes et "+nbEcoles()+" écoles.") ;
	}
}
