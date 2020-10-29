package constructionEcoles;
import java.util.ArrayList; 
import java.util.LinkedList;

/**
 * Classe qui definit une agglomeration dans le cadre
 ∗ du projet de construction décoles.
 * @author Yann Trividic
 * @version 1.0
 */

public class Agglomeration {

	private ArrayList<Ville> villes;
	
	/**
	 * Constructeur par défaut de la classe. Il initialise le ArrayList villes avec une taille de 10
	 */
	public Agglomeration() {
		this.villes = new ArrayList<Ville>() ;
	}
	
	/**
	 * Constructeur initialisant ses villes grâce aux villes passées en arguments
	 * @param villes	varargs de villes qui seront stockées dans l'attribut villes de l'objet
	 * 					lance une exception s'il existe deux villes identiques dans le varargs
	 */
	public Agglomeration(Ville...villes) throws Exception {
		this.villes = new ArrayList<Ville>() ;		
		for (Ville a : villes) {
			if(!a.hasEcole) a.setHasEcole(true) ;
			if(!villes.contains(a) { //ce constructeur suppose que l'objet ville implémente bien la méthode equals
				villes.add(a);	
			} else {
				throw new Exception("Deux villes passées en arguments portent le même identifiant.");
			}
		}
	}
	
	/**
	 * Constructeur initialisant le ArrayList villes avec la taille de l'entier passé en argument tant qu'il ne dépasse pas 26
	 * @param villes	tableau de villes qui seront stockées dans l'attribut villes de l'objet
	 * 					lance une exception si l'argument est < 1 ou > 26
	 */
	public Agglomeration(int nb_villes) throws Exception {
		if(nb_villes < 1 || nb_villes > 26) throw new Exception("Nombre de villes invalide.");
		this.villes = new ArrayList<Ville>(nb_villes);
	}
	
	/**
	 * Méthode implémentant l'algorithme principal de la classe permettant une répartition optimisée des 
	 * écoles dans l'agglomération.
	 * @return	un string contenant la liste des villes dans lesquelles seront construites des écoles de manière 
	 * 		 	à ce que les contraintes économiques et d'accessibilité soient respectées en limitant les coûts
	 */
	public String placerEcoles() {
		StringBuilder sb = new StringBuilder() ;
		//[...] 
		return sb.toString() ;
	}
	
	/**
	 * Methode permettant d'ajouter une route entre deux villes dans le cas où celles-ci ne seraient pas déjà reliées
	 * @param	a	première ville du couple de villes à relier par une route
	 * @param	b	seconde ville du couple de villes à relier par une route
	 */
	public void ajouterRoute(Ville a, Ville b) throws Exception {
		if(a.voisins.contains(b)) throw new Exception("Les deux villes sont déjà reliées");
		a.voisins.add(b) ;
		b.voisins.add(a) ;
	}
	
	/**
	 * Méthode permettant d'ajouter une école à une ville en faisant passer son attribut hasEcole de false à true 
	 * dans le cas où si cet ajout ne brise pas la contrainte d'Economie
	 * @param	a	la ville dans laquelle on essaiera d'ajouter une école
	 * 				lance une exception si la ville a déjà une école
	 */
	public void ajouterEcole(Ville a) throws Exception {
		if(a.getHasEcole()) throw new Exception("La ville a déjà une école.");
		if(a.hasEcoleVoisins()) throw new Exception("La ville est déjà proche d'une école.");
		a.setHasEcole(true);
	}
	
	/**
	 * Methode permettant de retirer une école à une ville en faisant passer son attribut hasEcole de true à false 
	 * dans le cas où si cet ajout ne brise pas la contrainte d'Accessibilité
	 * @param	a	la ville dans laquelle on essaiera d'ajouter une école
	 * 				lance une exception si la ville n'a déjà pas d'école
	 */
	public void retirerEcole(Ville a) throws Exception {
		if(!a.getHasEcole()) throw new Exception("La ville n'a déjà pas d'école.");
		if(!a.hasEcoleVoisins()) throw new Exception("La ville ne sera plus procheune école.");
		a.setHasEcole(false);
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
			for(Ville a : v.voisins) { //pour tous les voisins de v
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
			if(a.hasEcole()) sb.add(a.getKey()+" ") ;
		}
		System.out.println(sb) ;
	}
	
	/**
	 * Methode permettant de savoir combien il y a d'écoles dans l'agglomération
	 * @return	un int correspondant au nombre de villes dans l'agglomération
	 */
	public int nbEcoles() {
		int c = 0 ;
		for(Ville a : villes) {
			if(a.hasEcole()) c++ ;
		}
		return c ;
	}
	
	@Override
	public String toString() {
		return("Agglomération de "+villes.size()+" villes et "+nbEcoles()+" écoles.") ;
	}
}
