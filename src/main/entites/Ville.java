package main.entites;
import java.util.ArrayList;

/**
 * Classe qui definit une ville dans le cadre du projet de construction d'ecoles.
 * @author thanhcongvo
 * @version 1.0
 */

public class Ville {
	
	//Attributs
	/** hasEcole: indique si la ville contient une ecole ou non. @return: Oui (par defaut): 1,  Non: 0
	 * voisins: contenant toutes les villes reliees à l'instance de Ville appelant la methode.
	 * @param	key: un identifiant unique de chaque ville.
	 */
	private Boolean hasEcole;
	private ArrayList<Ville> voisins;
	private String key; 
	
	
	//Constructeur
	/** Un constructeur de la classe Ville permettant d'initialiser une Ville avec ses voisins et une ecole ou non
	 *  @param	key			un identifiant unique de chaque ville.
	 *  @param	voisins 	un ArrayList contenant les voisins de la ville a instancier
	 *  @param	hasEcole	booleen pour renseigner si la ville a une ecole ou non
	 */
	public Ville(Boolean hasEcole, ArrayList<Ville> voisins, char key){
		this.hasEcole = hasEcole;	//pour l'instant, toutes les villes doivent avoir des ecoles à l'initialisation. 
		this.voisins = voisins;		//on ne doit pas avoir de constructeurs qui puissent permettre autre chose
		this.key= key+"";				//On veut que la cle soit une lettre entre A et Z, il faut s'assurer de ca
	}
	
	/** Un constructeur de la classe Ville permettant d'initialiser une Ville avec sa key
	 * @param	key			un identifiant unique de chaque ville.
	 */
	public Ville(char key){
		this.hasEcole = true;	//pour l'instant, toutes les villes doivent avoir des ecoles a l'initialisation. 
		this.voisins = new ArrayList<Ville>(0);		//on ne doit pas avoir de constructeurs qui puissent permettre autre chose
		this.key = key+"";				//On veut que la cle soit une lettre entre A et Z, il faut s'assurer de ca
	}
	
	public Ville(String key) {
		this.hasEcole = true;	//pour l'instant, toutes les villes doivent avoir des ecoles a l'initialisation. 
		this.voisins = new ArrayList<Ville>(0);		//on ne doit pas avoir de constructeurs qui puissent permettre autre chose
		this.key = key ;
	}
	
	
	//Methodes
	/** La methode getHasEcole() permet d'acceder a la valeur de variable
	 *  d'instance privee hasEcole.
	 *  @return	hasEcole
	 */
	public boolean getAEcole() {
		return hasEcole;
	}
	
	
	/** La methode setHasEcole() permet de modifier la valeur
	 *  de variable d'instance privee hasEcole par une valeur d'un parametre dans cette methode.	 
	 *  @param	hasEcole	true pour si on veut ajouter une ecole, false si on veut en retirer une
	 */
	public void setHasEcole(boolean hasEcole) {
		this.hasEcole = hasEcole;
	}
	
	
	/** La methode getKey() permet d'acceder a la valeur de variable
	 *  d'instance privee key.
	 *  @return	key			un identifiant unique de chaque ville.
	 */
	public String getKey() {
		return key;
	}
	
	
	/** Methode permettant de savoir si au moins un voisin de la ville instanciee possede une ecole ou non.
	 * @return un booleen si l'une des villes voisines possede une ecole ou non.
	 */
	public boolean hasEcoleVoisins() {
		boolean b = false ;
		  for(int i=0; i< voisins.size(); i++) {
			 if (voisins.get(i).getAEcole()) b = true;  
		  }
		 return b;
	}
	
	//retourne le nombre d'ecoles accessibles a la ville en dehors de celle-ci
	private int nbEcolesVoisins() {
		int nbEcoles = 0 ;
		  for(int i=0; i< voisins.size(); i++) {
			 if (voisins.get(i).getAEcole()) nbEcoles++ ;  
		  }
		 return nbEcoles;
	}
	
	/** Methode permettant de savoir combien d'écoles sont accessibles depuis cette ville.
	 * @return un int correspondant au nombre 
	 */
	public int getNbEcolesAccessibles() {
		return nbEcolesVoisins()+(hasEcole?1:0) ;
	}

	/** Methode retournant un string contenant les voisins de la ville instanciee 
	 * @return un int de la valeur du nombre d'ecoles accessibles depuis cette ville
	 */
	public String afficherVoisins() {
		StringBuilder sb = new StringBuilder() ;
		sb.append("[ ") ;
		for(Ville v : voisins) sb.append(v.getKey()+" ") ;
		return sb.append("]").toString() ;
	}
	
	/** La methode getVoisins() permet d'acceder a la valeur de variable
	 *  d'instance privee voisins.
	 *  @return	voisins	les voisins de la ville instanciee
	 */
	public ArrayList<Ville> getVoisins() {
		return voisins;
	}
	
	public int getNbVoisins() {
		return voisins.size() ;
	}
	
	public boolean aAccesEcole() {
		return getAEcole() || hasEcoleVoisins() ;
	}
	
	public int beneficeSiAjoutEcole() {
		int score = (aAccesEcole()?0:1) ;
		for(Ville v : getVoisins()) if(!v.aAccesEcole()) score++ ;
		//score += (Math.random()-0.5)*0.01 ;	// si on veut randomiser, la fonction doit retourner un double
		//System.out.println(this.key+" = "+score) ;
		return score ;		
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder() ;
		sb.append(key+ " - Ecole : "+(hasEcole?"Oui":"Non")+" - Voisins : "+afficherVoisins()) ;
		return sb.toString() ;
	}
}
