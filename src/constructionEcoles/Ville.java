package constructionEcoles;
import java.util.ArrayList;

/**
 * Classe qui définit une ville dans le cadre du projet de construction d'écoles.
 * @author thanhcongvo
 * @version 1.0
 */

public class Ville {
	
	//Attributs
	/** hasEcole: indique si la ville contient une école ou non. @return: Oui (par défaut): 1,  Non: 0
	 * voisins: contenant toutes les villes reliées à l'instance de Ville appelant la méthode.
	 * @param	key: un identifiant unique de chaque ville.
	 */
	private Boolean hasEcole;
	private ArrayList<Ville> voisins;
	private char key; 
	
	
	//Constructeur
	/** Un constructeur de la classe Ville permettant d'initialiser une Ville avec ses voisins et une école ou non
	 *  @param	key			un identifiant unique de chaque ville.
	 *  @param	voisins 	un ArrayList contenant les voisins de la ville à instancier
	 *  @param	hasEcole	booléen pour renseigner si la ville a une école ou non
	 */
	public Ville(Boolean hasEcole, ArrayList<Ville> voisins, char key){
		this.hasEcole = hasEcole;	//pour l'instant, toutes les villes doivent avoir des écoles à l'initialisation. 
		this.voisins = voisins;		//on ne doit pas avoir de constructeurs qui puissent permettre autre chose
		this.key= key;				//On veut que la clé soit une lettre entre A et Z, il faut s'assurer de ça
	}
	
	/** Un constructeur de la classe Ville permettant d'initialiser une Ville avec sa key
	 * @param	key			un identifiant unique de chaque ville.
	 */
	public Ville(char key){
		this.hasEcole = true;	//pour l'instant, toutes les villes doivent avoir des écoles à l'initialisation. 
		this.voisins = new ArrayList<Ville>(0);		//on ne doit pas avoir de constructeurs qui puissent permettre autre chose
		this.key= key;				//On veut que la clé soit une lettre entre A et Z, il faut s'assurer de ça
	}
	
	
	//Methodes
	/** La méthode getHasEcole() permet d'accéder à la valeur de variable
	 *  d'instance privée hasEcole.
	 *  @return	hasEcole
	 */
	public boolean getHasEcole() {
		  return hasEcole;
	}
	
	
	/** La méthode setHasEcole() permet de modifier la valeur
	 *  de variable d'instance privée hasEcole par une valeur d'un paramètre dans cette méthode.	 
	 *  @param	hasEcole	true pour si on veut ajouter une école, false si on veut en retirer une
	 */
	public void setHasEcole(boolean hasEcole) {
		 this.hasEcole = hasEcole;
	}
	
	
	/** La méthode getKey() permet d'accéder à la valeur de variable
	 *  d'instance privée key.
	 *  @return	key			un identifiant unique de chaque ville.
	 */
	public char getKey() {
		return key;
	}
	
	
	/** Methode permettant de savoir si au moins un voisin de la ville instanciée possède une école ou non.
	 * @return un booléen si l'une des villes voisines possède une école ou non.
	 */
	public boolean hasEcoleVoisins() {
		boolean b = false ;
		  for(int i=0; i< voisins.size(); i++) {
			 if (voisins.get(i).getHasEcole()) b = true;  
		  }
		 return b;
	}
	
	//retourne le nombre d'écoles accessibles à la ville en dehors de celle-ci
	private int nbEcolesVoisins() {
		int nbEcoles = 0 ;
		  for(int i=0; i< voisins.size(); i++) {
			 if (voisins.get(i).getHasEcole()) nbEcoles++ ;  
		  }
		 return nbEcoles;
	}
	
	/** Methode permettant de savoir combien d'écoles sont accessibles depuis cette ville.
	 * @return un int correspondant au nombre 
	 */
	public int getNbEcolesAccessibles() {
		return nbEcolesVoisins()+(hasEcole?1:0) ;
	}

	/** Methode retournant un string contenant les voisins de la ville instanciée 
	 * @return un int de la valeur du nombre d'écoles accessibles depuis cette ville
	 */
	public String afficherVoisins() {
		StringBuilder sb = new StringBuilder() ;
		sb.append("[ ") ;
		for(Ville v : voisins) sb.append(v.getKey()+" ") ;
		return sb.append("]").toString() ;
	}
	
	/** La méthode getVoisins() permet d'accéder à la valeur de variable
	 *  d'instance privée voisins.
	 *  @return	voisins	les voisins de la ville instanciée
	 */
	public ArrayList<Ville> getVoisins() {
		return voisins;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder() ;
		sb.append(key+ " - Ecole : "+(hasEcole?"Oui":"Non")+" - Voisins : "+afficherVoisins()) ;
		return sb.toString() ;
	}
}
