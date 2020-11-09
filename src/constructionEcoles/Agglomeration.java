package constructionEcoles;
import java.util.List;

import constructionEcoles.exceptions.*;

import java.util.ArrayList; 
import java.util.LinkedList;

/**
 * Classe qui definit une agglomeration dans le cadre du projet de construction d'ecoles.
 * @author Yann Trividic
 * @version 1.0
 */

public class Agglomeration {

	private List<Ville> villes; // le type List permet de ne pas se fermer de portes pour la suite.
	
	/**
	 * Constructeur par defaut de la classe. Il initialise le ArrayList villes avec une taille de 10
	 * @see Agglomeration(Ville...)
	 * @see Agglomeration(int)
	 */
	public Agglomeration() {
		this.villes = new ArrayList<Ville>() ;
	}
	
	/**
	 * Constructeur initialisant ses villes grace aux villes passees en arguments
	 * @param villes	varargs de villes qui seront stockees dans l'attribut villes de l'objet
	 * @see Agglomeration()
	 * @see Agglomeration(int)
	 */
	public Agglomeration(Ville...villes) {
		this.villes = new ArrayList<Ville>() ;		
		for (Ville a : villes) addVille(a) ;
	}
	
	/**
	 * Constructeur initialisant le ArrayList villes avec la taille de l'entier passe en argument tant qu'il ne depasse pas 26
	 * @param nbVilles	le nombre de villes a generer dans l'agglo
	 * @see Agglomeration(Ville...)
	 * @see Agglomeration(int)
	 */
	public Agglomeration(int nbVilles) {
		this.villes = new ArrayList<Ville>(nbVilles);
		char c ;
		for(c = 'a'; c < 'a'+nbVilles; c++) this.villes.add(new Ville(c)) ;
	}
	
	/**
	 * Methode permettant de verifier si une ville est deja dans l'agglomeration
	 * @param		a	Char correspondant a l'attribut key de la ville a verifier
	 * @exception	ExceptionVille dans le cas ou la ville n'existe pas.
	 * @return		v	la ville trouvee dans l'agglomeration si elle a ete trouvee
	 * @see getVille(Ville)
	 */
	public Ville getVille(char a) throws Exception {
		for (Ville v : villes) if (v.getKey() == a) return v ;
		throw new ExceptionVille("La ville "+a+" n'existe pas dans l'agglomeration") ;
	}
	
	/**
	 * Methode permettant de verifier si une ville est deja dans l'agglo
	 * @param	a	ville a chercher dans l'agglomeration
	 * @return	v	la ville trouvee dans l'agglomeration si elle a ete trouvee
	 * @exception	ExceptionVille	lance une exception si la ville n'est pas dans l'agglomeration
	 * @see getVille(char)
	 */
	public Ville getVille(Ville a) throws Exception {
		for (Ville v : villes) if (v.getKey() == a.getKey()) return v ;
		throw new ExceptionVille("La ville "+a.getKey()+" n'existe pas dans l'agglomeration") ;
	}
	

	//ajoute une ville dans l'agglomeration en verifiant que celle-ci n'est pas deja dans l'agglomeration
	private void addVille(Ville a){
		try {
			if(getVille(a.getKey()) == null) villes.add(a) ;
		} catch(Exception e) {
			System.out.println("La ville "+a.getKey()+" est deja dans l'agglomeration.");
		}
	}

	
	/**
	 * Methode permettant d'ajouter une route entre deux villes dans le cas ou celles-ci ne seraient pas deja reliees
	 * @param		a	premiere ville du couple de villes a relier par une route
	 * @param		b	seconde ville du couple de villes a relier par une route
	 * @exception	ExceptionVille dans le cas ou les deux villes sont identiques, si elles sont deja reliees ou si l'une d'elles n'existe pas
	 * @see			ajouterRoute(char, char)
	 */
	public void ajouterRoute(Ville a, Ville b) throws Exception {
		if(a.equals(b)) throw new ExceptionVille("Les deux villes sont identiques") ; // equals ne marche pas ?
		if(getVille(a) == null || getVille(b) == null) throw new ExceptionVille("L'une des villes n'existe pas") ;
		if(a.getVoisins().contains(b)) throw new ExceptionUnicite("Les deux villes sont deja reliees");
		a.getVoisins().add(b) ;
		b.getVoisins().add(a) ;
	}
	
	/**
	 * Surcharge de la methode ajouterRoute(Ville, Ville) avec a la place des arguments Ville deux char
	 * @see		ajouterRoute(Ville, Ville)
	 * @param		a	premiere ville du couple de villes a relier par une route
	 * @exception	ExceptionVille dans le cas ou les deux villes sont identiques, si elles sont deja reliees ou si l'une d'elles n'existe pas
	 * @param		b	seconde ville du couple de villes a relier par une route
	 */
	public void ajouterRoute(char a, char b) throws Exception {
		ajouterRoute(getVille(a), getVille(b)) ;
	}
	
	
	/**
	 * Methode permettant d'ajouter une ecole dans une ville n'en ayant pas deja et dans le cas ou l'ajout ne briserait pas la contrainte d'Economie.
	 * @param		a					la ville dans laquelle on veut ajouter une ecole
	 * @exception	ExceptionEconomie 	dans le cas ou la ville a deja une ecole (a terme, une exception sera aussi lancee si la ville est deja proche d'une ecole)
	 * @see			ajouterRoute(Ville, Ville)
	 */
	public void ajouterEcole(Ville a) throws Exception {
		ajouterEcole(a.getKey()) ;

	}
	
	/**
	 * Surcharge de la methode ajouterEcole(Ville) avec a la place de l'argument Ville un char
	 * @param		c					la ville dans laquelle on veut ajouter une ecole
	 * @exception	ExceptionEconomie 	dans le cas ou la ville a deja une ecole (a terme, une exception sera aussi lancee si la ville est deja proche d'une ecole)
	 * @see		ajouterEcole(Ville)
	 */
	public void ajouterEcole(char c) throws Exception {
		Ville a = getVille(c) ;
		if(a.getHasEcole()) throw new ExceptionEconomie("La ville a deja une ecole.");
		//if(a.hasEcoleVoisins()) throw new ExceptionEconomie("La ville est deja proche d'une ecole."); //	decommenter cette ligne pour que la contrainte d'Economie
		a.setHasEcole(true);																			//	soit satisfaite en chaque instant de l'execution du programme
	}
	
	
	/**
	 * Methode permettant de retirer une ecole a une ville en faisant passer son attribut hasEcole de true a false 
	 * dans le cas ou cet ajout ne brise pas la contrainte d'Accessibilite
	 * @param		a						la ville dans laquelle on essaiera de retirer une ecole
	 * @exception	ExceptionAccessibilite	si enlever l'ecole de la ville casse la contrainte d'Accessibilite
	 * @see			retirerEcole(char)
	 */
	public void retirerEcole(Ville a) throws Exception {
		retirerEcole(a.getKey()) ;
	}
	
	/**
	 * Surcharge de la methode retirerEcole(Ville) avec a la place de l'argument Ville un char
	 * @param		c						la ville dans laquelle on essaiera de retirer une ecole
	 * @see		retirerEcole(Ville)
	 * @exception	ExceptionAccessibilite	si enlever l'ecole de la ville casse la contrainte d'Accessibilite
	 */
	public void retirerEcole(char c) throws Exception {
		Ville a = getVille(c) ;
		boolean accessibiliteVoisins = true ;
		if(!a.hasEcoleVoisins() && a.getHasEcole()) throw new ExceptionAccessibilite("La ville "+a.getKey()+" ne serait plus assez proche une ecole.");
		for(Ville voisin : a.getVoisins()) if(voisin.getNbEcolesAccessibles() == 1 && !voisin.getHasEcole()) accessibiliteVoisins = false ;
		if(!accessibiliteVoisins) throw new ExceptionAccessibilite("L'ecole de la ville "+a.getKey()+" est l'unique ecole accessible pour au moins une de ses villes voisines.");
		a.setHasEcole(false);
	}
	
	
	//permet de supprimer toutes les ecoles de l'agglomeration
	private void clearEcole() {
		for(Ville a : villes) a.setHasEcole(false);
	}
	
	/**
	 * Methode permettant de savoir s'il existe au moins un chemin a travers l'agglomeration reliant chaque couple de villes.
	 * Il s'agit d'une implementation d'un parcours BFS.
	 * @return	un booleen prenant pour valeur true si l'agglomeration est connexe et false sinon
	 */
	public boolean estConnexe() {
		LinkedList<Ville> file = new LinkedList<Ville>() ;	//file qui servira a stocker les villes restant a parcourir
		ArrayList<Character> marques = new ArrayList<Character>(0); //villes deja parcourues, contiendra les identifiants de chaque villes
		
		file.add(villes.get(0)) ; //enfile le premier element de villes dans la file
		marques.add(villes.get(0).getKey()) ; //la premiere ville est visitee
		
		while(!file.isEmpty()) { //tant que la file n'est pas vide
			Ville v = file.pollFirst() ; //on defile le dernier element v de la file
			for(Ville a : v.getVoisins()) { //pour tous les voisins de v
				if(!marques.contains(a.getKey())) { //on regarde si la ville a un identifiant deja visite
					marques.add(a.getKey()) ; // si elle ne l'est pas, on la marque comme visite
					file.offer(a) ; //et on l'enfile dans la file
				}
			}
		}
		
		if(marques.size() != villes.size()) return false ; //si tous les elements de l'agglo n'ont pas ete visites, on retourne faux
		return true ; //sinon on retourne vrai
	}
	
	/**
	 * Methode permettant d'afficher la liste des villes possedant des ecoles dans l'agglomeration.
	 */
	public void afficheVilleAEcole() {
		StringBuilder sb = new StringBuilder() ;
		for(Ville a : villes) {
			if(a.getHasEcole()) sb.append(a.getKey()+" ") ;
		}
		System.out.println(sb) ;
	}
	

	//retourne le nombre d'ecoles de l'agglomeration
	private int nbEcoles() {
		int c = 0 ;
		for(Ville a : villes) {
			if(a.getHasEcole()) c++ ;
		}
		return c ;
	}
	
	/**
	 * Methode permettant d'afficher toutes les routes de l'agglomeration. Il n'y a pas de doublons. 
	 * Par exemple, si [a, b] apparait, alors [b, a] n'apparaitra pas.
	 * @return	un string compose d'autant de lignes qu'il y a de routes.
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
	 * Methode verifiant si toutes les villes de l'agglomeration ont bien acces a une ecole 
	 * @return	un booleen avec la valeur true si la contrainte d'accessibilite est respecte et false sinon.
	 */
	public boolean respecteAccessibilite() {
		for(Ville v : villes) if(!v.getHasEcole() && !v.hasEcoleVoisins()) return false ;
		return true ;
	}
	
	@Override
	public String toString() {
		return("Agglomeration de "+villes.size()+" villes et "+nbEcoles()+" ecoles.") ;
	}
	
	public int algorithmeApproximationNaif() {
		int nbEcoles = nbEcoles() ;
		// TODO implementer l'algo du pdf
		return nbEcoles ;
	}
	
	public int algorithmeApproximationUnPeuMoinsNaif() {
		int nbEcoles = nbEcoles() ;
		// TODO implementer l'algo du pdf
		return nbEcoles ;
	}
	
	
	/**
	 * Methode implementant l'algorithme principal de la classe permettant une repartition optimisee des 
	 * ecoles dans l'agglomeration.
	 * @return	un string contenant la liste des villes dans lesquelles seront construites des ecoles de maniere 
	 * 		 	a ce que les contraintes economiques et d'accessibilite soient respectees en limitant les couts
	 */
	public String placerEcoles() {
		StringBuilder sb = new StringBuilder() ;
		// TODO Reflechir sur cet algo, qui est celui cense etre celui qui sera plus optimise que ceux proposes dans le pdf
		
		/*
		
		Premiere idee (algorithme glouton) :
		
				faut-il trier les villes au préalable ? Selon quel critère ? degré ? le bénéfice que l'ajout d'une école impliquerait ?

				Dans un premier temps, parcourir toutes les villes et placer des ecoles dans les villes qui devront forcément en accueillir.
				Cela concerne toutes les villes voisines de villes de degre 1 (c'est-a-dire les villes accessibles uniquement via une route)
				ajouter les villes de degré 2 qui n'ont pas d'école dans une liste. les retirer de cette liste si on leur met une école au passage
				
				Dans un second temps, tant que la contrainte d'accessibilite n'est pas remplie : 
				On cherche la ville pour laquelle l'ajout d'une ecole permettrait au plus grand nombre de villes d'acceder a une ecole
				Ajout d'une ecole dans la ville trouvee, retrait de cette école de la liste
				
				Question : 	est-ce qu'il existe des cas ou le resultat obtenu ne serait pas optimal ?
				dans le cas ou on a le choix entre x villes a l'etape 2, comment choisir une ville en particulier ? Vérifier si ce choix influe empiriquement
			
				faire attention à ne pas faire plein de loops pour rien. Faire attention à limiter la complexité
				rechercher dans des bouquins, optimisation, contraintes 
				tester la complexité de l'algorithme
				vérifier si le résultat est optimal
		
		
		Seconde idée :
		
				Representer le graphe sous la forme d'une matrice d'adjacence
				La solution de la répartition des écoles se trouverait en résolvant un système de type MX = (ai) avec a_i * x_i >= 1 et la somme des x_i est minimale
				avec M la matrice d'adjacence, X un vecteur contenant des 0 ou des 1 (école ou pas école).
				Le fait que tous les termes de MX soient non nuls signifie que la contrainte d'accessibilité est respectée.
				Le fait que la somme des ai est minimale signifie que la contrainte d'économie est respectée.
				
				Question : comment minimiser X ?
				
				
				Alternativement, utiliser la fonction quadratique associée à M puis résoudre le système obtenu ?
		
		
		Troisieme idée :
			
				Travailler avec une forme "minimale" et bipartite du graphe. C'est-à-dire que le graphe serait divisé en deux sous-graphes O et N tels que 
				O représente l'ensemble des sommets possédant une école et N l'ensemble des sommets ne possédant pas d'écoles.
				O doit contenir le moins d'éléments possible, et N le plus d'éléments possible : contrainte d'économie
				le graphe est bipartite : contrainte d'accessibilité
				
				Question : comment minimiser le graphe bipartite ? 
				D'après https://stackoverflow.com/questions/20107645/minimizing-number-of-crossings-in-a-bipartite-graph,
				ce genre de problème est NP-difficile
		
		*/
		
		return sb.toString() ;
	}
}
