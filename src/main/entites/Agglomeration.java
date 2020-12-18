package main.entites;
import java.util.*;
import main.exceptions.AccessibiliteException;
import main.exceptions.EconomieException;
import main.exceptions.UniciteException;
import main.exceptions.VilleException;

/**
 * Classe qui definit une agglomeration dans le cadre du projet de construction d'ecoles.
 * @author Yann Trividic
 * @version 1.1
 */

public class Agglomeration{

	private List<Ville> villes; // le type List permet de ne pas se fermer de portes pour la suite.
	
	/**
	 * Constructeur par defaut de la classe. Il initialise le ArrayList villes avec une taille de 10
	 * @see Agglomeration(Ville...)
	 * @see Agglomeration(int)
	 */
	public Agglomeration() {
		this.villes = new ArrayList<Ville>();
	}
	
	/**
	 * Constructeur initialisant ses villes grace aux villes passees en arguments
	 * @param villes	varargs de villes qui seront stockees dans l'attribut villes de l'objet
	 * @see Agglomeration()
	 * @see Agglomeration(int)
	 */
	  public Agglomeration(Ville [] villes) {
            this.villes = new ArrayList<Ville>();
            for (Ville v : villes) this.villes.add(v);
       }

	  /**
	   * Constructeur initialisant ses villes grace aux villes passees en arguments
	   * @param villes	varargs de villes qui seront stockees dans l'attribut villes de l'objet
	   * @see Agglomeration()
	   * @see Agglomeration(int)
	   */
	  public Agglomeration(ArrayList<String> noms) {
		  this.villes = new ArrayList<Ville>();
		  for (String n : noms) this.villes.add(new Ville(n));
	  }  

	/**
	 * Constructeur initialisant le ArrayList villes avec la taille de l'entier passe en argument tant qu'il ne depasse pas 26
	 * @param nbVilles	le nombre de villes a generer dans l'agglo
	 * @see Agglomeration(Ville...)
	 * @see Agglomeration(int)
	 */
	public Agglomeration(int nbVilles) {
		this.villes = new ArrayList<Ville>(nbVilles);
		char c = 'A';
		if(nbVilles < 2) {
			throw new IllegalArgumentException("Le nombre de villes est invalide. Il doit être compris entre 2 et 26 000") ;
		} else if(nbVilles < 27) {
			for(c = 'A'; c < 'A'+nbVilles; c++) this.villes.add(new Ville(c));
		} else if(nbVilles <= 26*10) {
			for(int i = 0 ; i < nbVilles ; i++) this.villes.add(new Ville((char) ('A'+i%26)+""+String.format("%01d", i/26)));
		} else if(nbVilles <= 26*100) { 
			for(int i = 0 ; i < nbVilles ; i++) this.villes.add(new Ville((char) ('A'+i%26)+String.format("%02d", i/26)));
		} else if(nbVilles <= 26*1000) { 
			for(int i = 0 ; i < nbVilles ; i++) this.villes.add(new Ville((char) ('A'+i%26)+String.format("%03d", i/26)));
		}
	}
	
	/**
	 * Methode permettant de verifier si une ville est deja dans l'agglomeration
	 * @param		a	Char correspondant a l'attribut key de la ville a verifier
	 * @exception	VilleException dans le cas ou la ville n'existe pas.
	 * @return		v	la ville trouvee dans l'agglomeration si elle a ete trouvee
	 * @see getVille(Ville)
	 */
	public Ville getVille(String a) throws VilleException {
		for (Ville v : villes) if (v.getKey().equals(a)) return v;
		throw new VilleException("La ville "+a+" n'existe pas dans l'agglomeration");
	}
	
	/**
	 * Methode permettant de verifier si une ville est deja dans l'agglo
	 * @param	a	ville a chercher dans l'agglomeration
	 * @return	v	la ville trouvee dans l'agglomeration si elle a ete trouvee
	 * @exception	VilleException	lance une exception si la ville n'est pas dans l'agglomeration
	 * @see getVille(String)
	 */
	public Ville getVille(Ville a) throws Exception {
		for (Ville v : villes) if (v.getKey() == a.getKey()) return v;
		throw new VilleException("La ville "+a.getKey()+" n'existe pas dans l'agglomeration");
	}
	

	//ajoute une ville dans l'agglomeration en verifiant que celle-ci n'est pas deja dans l'agglomeration
	@SuppressWarnings("unused")
	private void addVille(Ville a){
		try {
			if(getVille(a.getKey()) == null) villes.add(a);
		} catch(Exception e) {
			System.out.println("La ville "+a.getKey()+" est deja dans l'agglomeration.");
		}
	}

	/**
	 * Getter pour l'attribut villes
	 * @return la liste de villes associées à l'agglomération appelant la méthode
	 */
	public List<Ville> getVilles() {
		return villes;
	}
	
	/**
	 * Methode permettant d'ajouter une route entre deux villes dans le cas ou celles-ci ne seraient pas deja reliees
	 * @param		a	premiere ville du couple de villes a relier par une route
	 * @param		b	seconde ville du couple de villes a relier par une route
	 * @exception	VilleException dans le cas ou les deux villes sont identiques, si elles sont deja reliees ou si l'une d'elles n'existe pas
	 * @see			ajouterRoute(String, String)
	 */
	public void ajouterRoute(Ville a, Ville b) throws Exception {
		if(a.equals(b)) throw new VilleException("Les deux villes sont identiques"); // equals ne marche pas ?
		if(getVille(a) == null || getVille(b) == null) throw new VilleException("L'une des villes n'existe pas");
		if(a.getVoisins().contains(b)) throw new UniciteException("Les deux villes sont deja reliees");
		a.getVoisins().add(b);
		b.getVoisins().add(a);
	}
	
	/**
	 * Surcharge de la methode ajouterRoute(Ville, Ville) avec a la place des arguments Ville deux String
	 * @see		ajouterRoute(Ville, Ville)
	 * @param		a	premiere ville du couple de villes a relier par une route
	 * @exception	VilleException dans le cas ou les deux villes sont identiques, si elles sont deja reliees ou si l'une d'elles n'existe pas
	 * @param		b	seconde ville du couple de villes a relier par une route
	 */
	public void ajouterRoute(String a, String b) throws Exception {
		ajouterRoute(getVille(a), getVille(b));
	}
	
	public void ajouterRoute(String a, String...s) throws Exception {
		for(String ville : s) ajouterRoute(getVille(a), getVille(ville));
	}
		
	/**
	 * Methode permettant d'ajouter une ecole dans une ville n'en ayant pas deja et dans le cas ou l'ajout ne briserait pas la contrainte d'Economie.
	 * @param		a					la ville dans laquelle on veut ajouter une ecole
	 * @exception	EconomieException 	dans le cas ou la ville a deja une ecole (a terme, une exception sera aussi lancee si la ville est deja proche d'une ecole)
	 * @see			ajouterRoute(Ville, Ville)
	 */
	public void ajouterEcole(Ville a) throws Exception {
		ajouterEcole(a.getKey());
	}
	
	/**
	 * Surcharge de la methode ajouterEcole(Ville) avec a la place de l'argument Ville un String
	 * @param		c					la ville dans laquelle on veut ajouter une ecole
	 * @exception	EconomieException 	dans le cas ou la ville a deja une ecole (a terme, une exception sera aussi lancee si la ville est deja proche d'une ecole)
	 * @see		ajouterEcole(Ville)
	 */
	public void ajouterEcole(String c) throws Exception {
		Ville a = getVille(c);
		if(a.getAEcole()) throw new EconomieException("La ville a déjà une école.");
		//if(a.hasEcoleVoisins()) throw new ExceptionEconomie("La ville est deja proche d'une ecole."); //	decommenter cette ligne pour que la contrainte d'Economie
		a.setHasEcole(true);																			//	soit satisfaite en chaque instant de l'execution du programme
	}
	
	/**
	 * Surcharge de la méthode ajouterEcole avec un ArrayList à la place de la ville passée en argument
	 * @see ajouterEcole(Ville)
	 * @param cles Les clés associées aux villes dans lesquelles on veut ajouter des écoles
	 * @throws Exception dans le cas ou la ville a deja une ecole (a terme, une exception sera aussi lancee si la ville est deja proche d'une ecole)
	 */
	public void ajouterEcole(ArrayList<String> cles) throws Exception {
		for(String cle : cles) ajouterEcole(cle) ;
	}
	
	
	/**
	 * Methode permettant de retirer une ecole a une ville en faisant passer son attribut hasEcole de true a false 
	 * dans le cas ou cet ajout ne brise pas la contrainte d'Accessibilite
	 * @param		a						la ville dans laquelle on essaiera de retirer une ecole
	 * @exception	AccessibiliteException	si enlever l'ecole de la ville casse la contrainte d'Accessibilite
	 * @see			retirerEcole(String)
	 */
	public void retirerEcole(Ville a) throws Exception {
		retirerEcole(a.getKey());
	}
	
	/**
	 * Surcharge de la methode retirerEcole(Ville) avec a la place de l'argument Ville un String
	 * @param		c						la ville dans laquelle on essaiera de retirer une ecole
	 * @see		retirerEcole(Ville)
	 * @exception	AccessibiliteException	si enlever l'ecole de la ville casse la contrainte d'Accessibilite
	 */
	public void retirerEcole(String c) throws Exception {
		Ville a = getVille(c);
		boolean accessibiliteVoisins = true;
		if(!a.hasEcoleVoisins() && a.getAEcole()) throw new AccessibiliteException("La ville "+a.getKey()+" ne serait plus assez proche une école.");
		for(Ville voisin : a.getVoisins()) if(voisin.getNbEcolesAccessibles() == 1 && !voisin.getAEcole()) accessibiliteVoisins = false;
		if(!accessibiliteVoisins) throw new AccessibiliteException("L'école de la ville "+a.getKey()+" est l'unique école accessible pour au moins une de ses villes voisines.");
		a.setHasEcole(false);
	}
	
	
	/**
	 * Surcharge de la méthode clearEcole permettant de conserver les écoles de certaines villes
	 * @param conserverEcoles ArrayList contenant les clés des villes dont il faut conserver les écoles
	 * @see clearEcole()
	 */
	public void clearEcole(ArrayList<String> conserverEcoles) {
		if(conserverEcoles != null) {
			for(Ville v : villes) if(!conserverEcoles.contains(v.getKey())) v.setHasEcole(false);
		} else {
			for(Ville v : villes) v.setHasEcole(false);
		}
	}
	
	/**
	 * Méthode permettant de retirer les écoles pouvant exister dans les villes de l'agglomération appelant la méthode
	 */
	public void clearEcole() {
		clearEcole(null);
	}

	/**
	 * Methode permettant de savoir s'il existe au moins un chemin a travers l'agglomeration reliant chaque couple de villes.
	 * Il s'agit d'une implementation d'un parcours BFS.
	 * @return	un booleen prenant pour valeur true si l'agglomeration est connexe et false sinon
	 */
	public boolean estConnexe() {
		LinkedList<Ville> file = new LinkedList<Ville>();	//file qui servira a stocker les villes restant a parcourir
		ArrayList<String> marques = new ArrayList<String>(0); //villes deja parcourues, contiendra les identifiants de chaque villes
		
		file.add(villes.get(0)); //enfile le premier element de villes dans la file
		marques.add(villes.get(0).getKey()); //la premiere ville est visitee
		
		while(!file.isEmpty()) { //tant que la file n'est pas vide
			Ville v = file.pollFirst(); //on defile le dernier element v de la file
			for(Ville a : v.getVoisins()) { //pour tous les voisins de v
				if(!marques.contains(a.getKey())) { //on regarde si la ville a un identifiant deja visite
					marques.add(a.getKey()); // si elle ne l'est pas, on la marque comme visite
					file.offer(a); //et on l'enfile dans la file
				}
			}
		}
		
		if(marques.size() != villes.size()) return false; //si tous les elements de l'agglo n'ont pas ete visites, on retourne faux
		return true; //sinon on retourne vrai
	}
	

	/**
	 * Methode permettant d'afficher la liste des villes possedant des ecoles dans l'agglomeration.
	 * @param vueEtendue Booléen permettant de choisir entre deux modes d'affichage possible
	 * @param tailleLigne Le nombre de villes à afficher par lignes
	 */
	public void afficheVilleAEcole(boolean vueEtendue, int tailleLigne) {

		int compteur = 0 ;
		int size = villes.get(0).getKey().length() ;
		if(vueEtendue) {
			int tmp = 0 ;
			while(compteur < villes.size()) {
				for(tmp = compteur ; tmp%tailleLigne != tailleLigne-1 && tmp%tailleLigne+compteur < villes.size() ; tmp++) {
					//System.out.print(villes.size()) ;
					System.out.print(villes.get(tmp).getKey()+" ");
				}
				if(tmp < villes.size()) System.out.print(villes.get(tmp).getKey()+" ");
				//System.out.println(compteur) ;
				System.out.print("\n");
				for(tmp = compteur ; tmp%tailleLigne != tailleLigne-1 && tmp%tailleLigne+compteur < villes.size() ; tmp++) {
					if(villes.get(tmp).getAEcole()) {
						System.out.print(new String(new char[size]).replace("\0", "E")+" ");
					} else {
						System.out.print(new String(new char[size]).replace("\0", "-")+" ");
					}
				}
				if(tmp < villes.size()) {
					if(villes.get(tmp-1).getAEcole()) {
						System.out.print(new String(new char[size]).replace("\0", "E")+" ");
					} else {
						System.out.print(new String(new char[size]).replace("\0", "-")+" ");
					}
				}
				System.out.println("\n");
				compteur += tmp%tailleLigne+1 ;
				//System.out.println(compteur) ;
			}
				
		} else {
			for(Ville a : villes) {
			if (!vueEtendue) if(a.getAEcole()) System.out.print(a.getKey()+" ");
			compteur++ ;
			if(compteur%tailleLigne-1 == 0) System.out.print("\n") ;
			}
		}
		System.out.print("\n");
	}
	
	/**
	 * Surchage de la méthode afficheVilleAEcole avec des valeurs de paramètres par défaut
	 * @see afficheVilleAEcole(boolean, int)
	 */
	public void afficheVilleAEcole() {
		if(getNbEcoles() != 0) {
			afficheVilleAEcole(true, 40) ;
		} else {
			System.out.println("Aucune école construite.") ;
		}		
	}
	
	/**
	 * Methode permettant d'afficher la liste des villes avec un certain nombre de villes par lignes
	 * @param tailleLigne
	 */
	public void afficheVilles(int tailleLigne) {
		int compteur = 0 ;
		for(Ville a : villes) {
			System.out.print(a.getKey()+" ");
			compteur++ ;
			if(compteur%tailleLigne == 0) System.out.print("\n");
		}
		System.out.print("\n");
	}
	
	/**
	 * Méthode retournant la liste des clés des villes contenant des écoles
	 * @return Un ArrayList contenant les clés des villes avec des écoles
	 */
	public ArrayList<String> getVillesAEcole() {
		ArrayList<String> villesAEcole = new ArrayList<String>(0) ;
		for(Ville a : villes) {
			if(a.getAEcole()) villesAEcole.add(a.getKey()) ;
		}
		return villesAEcole ;
	}

	/**
	 * Getter pour le nombre d'écoles dans l'agglomération
	 * @return int le nombre d'écoles de l'agglomération
	 */
	public int getNbEcoles() {
		int compteur = 0;
		for(Ville a : villes) {
			if(a.getAEcole()) compteur++;
		}
		return compteur;
	}
	
	/**
	 * Methode permettant d'afficher toutes les routes de l'agglomeration. Il n'y a pas de doublons. 
	 * Par exemple, si [a, b] apparait, alors [b, a] n'apparaitra pas.
	 * @return	un string compose d'autant de lignes qu'il y a de routes.
	 */
	public void afficheRoutes(boolean enListe) {
		for(Ville ville : villes) {
			if(!enListe) System.out.print(ville.getKey()+" : ");
			ArrayList<Ville> voisins = ville.getVoisins() ;
			if(voisins.isEmpty()) {
				System.out.print("Aucun voisin") ;
			} else {
				for(Ville voisin : ville.getVoisins()) {
					if(enListe)	if(voisin.getKey().compareTo(ville.getKey()) > 0) System.out.print("["+ville.getKey()+", "+voisin.getKey()+"] ");
					if(!enListe) System.out.print(voisin.getKey()+" ");
				}
			}
			System.out.print("\n");	
		}
	}
	
	/**
	 * Surcharge de la méthode afficheRoutes avec paramètre par défaut à false 
	 * @see afficheRoutes(boolean)
	 */
	public void afficheRoutes() {
		afficheRoutes(false) ;
	}
	
	/**
	 * Surcharge de la méthode afficheBilan avec paramètre par défaut à false
	 * @see afficheBilan(boolean)
	 */
	public void afficheBilan() {
		afficheBilan(true) ;
	}
	
	/**
	 * Méthode permettant d'afficher une vue d'ensemble de l'état de l'agglomération (écoles, villes, routes, connexité, contrainte d'accessibilité...)
	 * @param condense booléen permettant de changer le mode d'affichage
	 */
	public void afficheBilan(boolean condense) {
		System.out.print("Agglomération "+(estConnexe()?"":"non ")+ "connexe de "+villes.size()+" et "+getNbEcoles()
						+ " écoles ("+String.format("%.2f", (double) (getNbEcoles())/villes.size()*100)+" %)");
		System.out.println("\nContrainte d'accessibilité : "+(respecteAccessibilite()?"Respectée":"Non respectée")) ;
		if(condense) {
			System.out.println("Représentation du graphe (Ecole [\"*\":oui, \" \":non] - Ville : Voisins) :") ;
			for(Ville v : villes) {
				System.out.print(v.getAEcole()?" * ":"   ") ;
				System.out.print(v.getKey()+" : ") ;
				Collections.sort(v.getVoisins(), Comparator.comparing(Ville::getKey)) ;
				ArrayList<Ville> voisins = v.getVoisins() ;
				if(voisins.isEmpty()) {
					System.out.print("Aucun voisin.");
				}
				else for(Ville voisin : voisins) System.out.print(voisin.getKey()+" ");
				System.out.print("\n");
			}
		} else {
			System.out.println("\nVilles : ") ;
			afficheVilles(40) ;
			System.out.println("\nVoisins : ");
			afficheRoutes() ;
			System.out.println("\nEcoles : ") ;
			afficheVilleAEcole() ;
		}
		System.out.print("\n");
	}
	
	/**
	 * Methode verifiant si toutes les villes de l'agglomeration ont bien acces a une ecole 
	 * @return	un booleen avec la valeur true si la contrainte d'accessibilite est respecte et false sinon.
	 */
	public boolean respecteAccessibilite() {
		for(Ville v : villes) if(!v.aAccesEcole()) return false;
		return true;
	}
	
	@Override
	public String toString() {
		return("Agglomeration de "+villes.size()+" villes et "+getNbEcoles()+" ecoles.");
	}
}
