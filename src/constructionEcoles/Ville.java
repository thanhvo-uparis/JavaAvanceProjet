package constructionEcoles;
import java.util.ArrayList;

/**
 * Classe qui définit une ville dans le cadre
 ∗ du projet de construction d'écoles.
 * @author thanhcongvo
 * @version 1.0
 */

public class Ville {
	
	//Attributs
	/** hasEcole: indique si la ville contient une école ou non. @return: Oui (par défaut): 1,  Non: 0
	 * voisins: contenant toutes les villes reliées à l'instance de Ville appelant la méthode.
	 * key: un identifiant unique de chaque ville.
	 */
	private Boolean hasEcole;
	private ArrayList<Ville> voisins;
	private String key; 
	
	
	//Constructeur
	/** Un constructeur de la classe Ville.
	 * 
	 */
	public Ville(Boolean hasEcole, ArrayList<Ville> voisins, String key){
		this.hasEcole = hasEcole;	//pour l'instant, toutes les villes doivent avoir des écoles à l'initialisation. 
		this.voisins = voisins;		//on ne doit pas avoir de constructeurs qui puissent permettre autre chose
		this.key= key;				//On veut que la clé soit une lettre entre A et Z, il faut s'assurer de ça
	}
	
	
	//Methodes
	/** La méthode getHasEcole() permet d'accéder à la valeur de variable
	 *  d'instance privée hasEcole.
	 */
	public boolean getHasEcole() {
		  return hasEcole;
	}
	
	
	/** La méthode setHasEcole() permet de modifier la valeur
	 *  de variable d'instance privée hasEcole par une valeur d'un paramètre dans cette méthode.	 
	 */
	public void setHasEcole(boolean hasEcole) {
		 this.hasEcole = hasEcole;
	}
	
	
	/** La méthode getKey() permet d'accéder à la valeur de variable
	 *  d'instance privée key.
	 */
	public String getKey() {
		return key;
	}
	
	
	/** La méthode hasEcoleVoisins() 
	 * @return un booléen si l'une des villes voisines qui existe une école ou non.
	 */
	public boolean hasEcoleVoisins() {
		  for(int i=0; i< voisins.size(); i++) {
			 if (voisins.get(i).getHasEcole()) return true;  
		  }
		 return false;
	}


	public ArrayList<Ville> getVoisins() {
		// TODO Auto-generated method stub
		return voisins;
	}
}
