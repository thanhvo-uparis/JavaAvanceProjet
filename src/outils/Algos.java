package outils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

import constructionEcoles.Agglomeration;
import constructionEcoles.Ville;
import constructionEcoles.exceptions.ExceptionVille;




/**
 * Classe donnant l'accès à plusieurs algorithmes permettant de minimiser plus ou moins bien le nombre d'écoles d'une agglomération.
 * @author Yann Trividic
 * @version 1.0
 */

// TODO On en arrive au point où le manque de visualisation commence à se faire sentir, 
// pour installer JavaFX, voir https://www.eclipse.org/forums/index.php/t/1097505/

// TODO Etant donné que fractionner la classe Agglo en deux implique de changer la visibilité de certaines méthodes
// peut-être qu'il serait mieux que la classe Main soit dans un package complètement à part (afin que les protected ait du sens).
// ou alors mettre la classe Algos dans le package outils et mettre toutes les méthodes d'Agglo en public

public class Algos {
	
	/**
	 * Méthode statique permettant de retirer un certain nombre d'écoles à l'agglomération passée en argument.
	 * Cet algorithme est très naïf et très améliorable.
	 * @see		Agglomeration algorithmeApproximationUnPeuMoinsNaif(int, Agglomeration)
	 * @param 	k	nombre d'itérations de l'algorithme.
	 * @param	agg	l'agglomération dont on cherche à réduire le nombre d'écoles. 
	 * @return	la même agglomération que celle passée en argument, mais avec une configuration d'écoles différentes
	 */
	public static Agglomeration algorithmeApproximationNaif(int k, Agglomeration agg) {
		ArrayList<Ville> villes = (ArrayList<Ville>) agg.getVilles() ;
		for(int i = 0 ; i < k ; i++) {
			Ville v = villes.get((int) Math.random()*villes.size()) ;
			try {
				if(v.getHasEcole()) {
					agg.retirerEcole(v);
				} else agg.ajouterEcole(v) ;
			} catch(Exception e) {
				System.out.println(e) ;
			}
		} 
		return agg;
	}
	
	/**
	 * Méthode statique permettant de retirer un certain nombre d'écoles à l'agglomération passée en argument.
	 * Cet algorithme est moins naïf que l'autre, mais toujours améliorable.
	 * @see		Agglomeration algorithmeApproximationNaif(int, Agglomeration)
	 * @param 	k	nombre d'itérations de l'algorithme.
	 * @param 	agg	l'agglomération dont on cherche à réduire le nombre d'écoles. 
	 * @return	la même agglomération que celle passée en argument, mais potentiellement avec beaucoup moins d'écoles
	 */
	public static Agglomeration algorithmeApproximationUnPeuMoinsNaif(int k, Agglomeration agg) {
		ArrayList<Ville> villes = (ArrayList<Ville>) agg.getVilles() ;
		int scoreCourant = agg.nbEcoles();
		for(int i = 0 ; i < k ; i++) {
			Ville v = villes.get((int) Math.random()*villes.size()) ;
			try {
				if(v.getHasEcole()) {
					agg.retirerEcole(v);
				} else agg.ajouterEcole(v) ;
			} catch(Exception e) {
				System.out.println(e) ;
			}
			if(agg.nbEcoles() < scoreCourant) {
				i = 0 ;
				scoreCourant = agg.nbEcoles() ;
			}
		} 
		return agg;
	}

	
	/**
	 * Methode de la classe permettant de minimiser le mieux le nombre d'écoles d'une agglomération donnée avec des écoles déjà construites ou non.
	 * Dans un premier temps, on va parcourir toutes les villes et placer des ecoles dans les villes qui devront forcément en accueillir.
	 * Cela concerne toutes les villes voisines de villes de degre 1 (c'est-a-dire les villes accessibles uniquement via une seule route).
	 * Ce faisant, on ajoute dans une file de priorité les villes de degré supérieur à 1. Une fois ce premier parcours effectué, on vérifie
	 * que les villes contenues dans la file n'ont pas d'écoles. Si elles en ont, on les enlève.
	 * Dans un second temps, tant que la contrainte d'Accessibilité n'est pas remplie, on extrait de la file de priorité la ville pour laquelle la 
	 * construction d'une école bénéficierait le plus à l'agglomération, puis on actualise la file.
	 * Au sortir de cet étape, le nombre d'écoles de l'agglomération est relativement proche du résultat optimal, voire est le résultat optimal.
	 * @param agg						l'agglomération dont on cherche à réduire le nombre d'écoles. Le graphe qui la représente peut-être non-connexe et orienté.
	 * @param garderEcolesConstruites	booléen permettant de préciser si on souhaite garder ou non les écoles déjà présentes dans l'agglomération
	 * @return							l'agglomération donnée en argument avec un nombre d'école minimisé
	 */
	public static Agglomeration algorithmeFilePriorite(Agglomeration agg, boolean garderEcolesConstruites) {
		// TODO tester la validité de cet algorithme, sa complexité et vérifier empiriquement s'il permet de trouver le résultat optimal à chaque fois
		// ^^^^^ à faire dans la classe Tester
		
		if(!garderEcolesConstruites) agg.clearEcole(); 	// on enlève toutes les écoles présentes. L'algorithme fonctionne si on décide de garder les écoles
														// déjà construites. Le nombre d'écoles final risque cependant d'être supérieur.
		
		ArrayList<Ville> villes = (ArrayList<Ville>) agg.getVilles();
				
		// On utilise une PriorityQueue dont le critère de comparaison est le nombre de villes qui bénéficieraient de l'ajout d'une ville dans la ville courante
		PriorityQueue<Ville> p = new PriorityQueue<Ville>(villes.size(), Comparator.comparing(Ville::beneficeSiAjoutEcole).reversed());
		// Pour randomiser les tests, si les algorithmes des PriorityQueue sont totalement déterministes,
		// on peut modifier beneficeSiAjoutEcole en ajoutant un bruit aléatoire à la valeur retournée (voir le commentaire dans la méthode citée)
		
		for(Ville v : villes) { 							// cette boucle met une école dans les villes qui devront forcément contenir une école
			ArrayList<Ville> voisins;
			if((voisins = v.getVoisins()).size() == 1) { 	// c'est-à-dire les villes qui n'ont qu'un voisin et qui n'ont pas déjà d'école construite
				if(!v.getHasEcole()) voisins.get(0).setHasEcole(true);		// le 2e if sert dans le cas d'une agglomération de deux villes 
			} else {														// ou qu'on a choisi de garder les écoles déjà construites
				p.add(v); 									// si elles ont plus d'un voisin, on les ajoute dans la file de priorité
			}
		}
		
		// Arrivés à ce point, soit on décide d'enlever toutes les villes ayant déjà des écoles construites avec cette ligne :
		// p.removeIf((v) -> v.getHasEcole());
		// soit on décide de garder les villes avec des écoles construites dans la file. La file aura donc plus d'éléments
		// et les opérations pour réévaluer les ordres de priorité seront plus couteuses.
		// Selon la forme du graphe, les deux choix peuvent être valides. S'il y a beaucoup de villes de degré 1,
		// alors cela vaudrait le coup de retirer toutes les villes à écoles, sinon, cela ne vaut pas le coup.
		// Pour l'instant, on va supposer que le nombre de villes de degré 1 est négligeable par rapport au nombre de villes total de l'agglo.
		// On pourrait potentiellement améliorer la complexité en calculant le ratio nbVillesDeg1/nbVilles marquant la limite entre l'efficacite de
		// la première méthode et l'efficacité de la seconde.
		
		while(!agg.respecteAccessibilite()) {		// La file ne sera jamais vide alors que la contrainte d'accessibilité n'est pas respectée ;
			Ville v = p.poll();						// tant que la contrainte d'accessibilité n'est pas respectée ...
			if(!v.getHasEcole()) {					// ... on défile et on ajoute une école dans la ville défilée si elle n'en a pas déjà une. Sinon, on ne fait rien.
				v.setHasEcole(true);
				if(!p.isEmpty()) p.add(p.remove());	// permet d'actualiser la file de priorité selon les nouveaux résultats du comparateur, 
			} 										// c'est çe qui est vraiment coûteux en temps de calcul dans l'algorithme donc on le fait uniquement quand la file doit
		}											// être actualisée. La complexité est apparemment améliorable avec un tas de Fibonacci
													// voir https://stackoverflow.com/questions/1871253/updating-java-priorityqueue-when-its-elements-change-priority
		return agg;									
	}
	
	public static Agglomeration algorithmeParSoustraction(Agglomeration agg) {
		
			/* Structure générale de l'algorithme :
			 * 
			 * Agg une agglomération
			 * Soit L la liste d'adjacence de l'agglomération
			 * Soit F une file vide
			 * 
			 * Tant que la liste d'adjacence n'est pas totalement constituée de 0
			 * 		Tant qu'il y a des sommets de degré 1 dans L
			 * 			Ajouter toutes les villes de degré 1 dans F avec leurs voisins
			 * 			Ajouter une école à tous les voisins des villes de degré 1	
			 * 		 
			 *		 	Tant que F n'est pas vide
			 *				défiler F dans V
			 *				pour chaque entrée de L, retirer V et ses voisins de l'entrée
			 *				retirer V de L
			 *
			 *			Ajouter une école dans chaque ville de degré 0
			 * 
			 * 		Si la contrainte n'est pas totalement respectée dans Agg
			 * 			Soit u la ville de plus haut degré
			 * 			Ajouter une école dans u
			 * 			pour chaque colonne de L mettre la u-ième ligne à 0
			 */
		
		agg.clearEcole(); // la limitation de cette algorithme est qu'il ne fonctionne 
						  // que dans le cas où il n'y a aucune école dans l'agglomération au départ
		
		// La liste d'adjacence est une shallow copy de l'objet villes. 
		// Elle sert à ne pas s'encombrer des villes ayant déjà accès à des écoles au fur et à mesure qu'on en ajoute
		// Elle permet d'optimiser la complexité de l'algo et d'en améliorer sa clarté.
		ListeAdjacence la = new ListeAdjacence(agg.getVilles()) ;		
		System.out.println("Initialisation : "+la.toString()) ;
																		
		while(!la.isEmpty()) {											
			// Cette file servira dans un premier temps à stocker les villes voisines des ville de degré 1
			// puis elle accueillera les villes de degré 0
			LinkedList<Character> file = new LinkedList<Character>() ;
			la.voisinsDegreUn(file) ; // enfile tous les voisins des villes de degré 1
			
			for(int i = 0 ; i < file.size() ; i++) {
				try {
					agg.getVille(file.get(i)).setHasEcole(true);
				} catch (ExceptionVille e) {
					System.err.println("La ville "+file.get(i)+" n'a pas pu être accédée.") ;
				}
			}
			
			while(!file.isEmpty()) la.removeVilleEtVoisins(file.poll()); 	// maintenant que toutes les écoles ont bien été ajoutées
																			// on peut vider la HashMap proprement
			
			// Cette partie n'est pas foncièrement nécessaire mais elle permet de gagner en temps de calcul
			// Si on la commente, le résultat serait identique mais on ferait globalement plus de tests pour
			// finir l'exécution de l'algorithme avec des plusHautDegre finissant par retourner des villes de degré 0.
			la.degreZero(file) ; // enfile toutes les files de degré 0 dans file
			while(!file.isEmpty()) {
				Character degreZero = file.poll() ;
				try {
					agg.getVille(degreZero).setHasEcole(true);
				} catch (ExceptionVille e) {
					System.err.println("La ville "+degreZero+" n'a pas pu être accédée.") ;
				}
				la.remove(degreZero); // on vide la HashMap des villes isolées
			}
			System.out.println(la.toString()) ; // Après un premier nettoyage
			
			// Dans le cas où il n'y a aucune ville de degré 1, il faut trouver une ville qui permettrait de débloquer
			// la situation tout en répondant à la contrainte d'économie. La ville qui répond à ces exigences est 
			// la ville de plus haut degré
			if(!la.isEmpty()) {
				Character u = la.plusHautDegre() ;
				try {
					agg.getVille(u).setHasEcole(true);
				} catch (ExceptionVille e) {
					System.err.println("La ville "+u+" n'a pas pu être accédée.") ;
				}
				la.removeVilleEtVoisins(u) ;
			}
			System.out.println("Fin itération while : "+la.toString()) ; // Après avoir potentiellement retiré 
		}
		return agg ;
	}
}


/*

Autre idées d'algos :

Résoudre un système d'équations linéaires sous contrainte :

		Representer le graphe sous la forme d'une liste d'adjacence
		La solution de la répartition des écoles se trouverait en résolvant un système de type MX = (ai) avec a_i * x_i >= 1 et la somme des x_i est minimale
		avec M la liste d'adjacence, X un vecteur contenant des 0 ou des 1 (école ou pas école).
		Le fait que tous les termes de MX soient non nuls signifie que la contrainte d'accessibilité est respectée.
		Le fait que la somme des a_i est minimale signifie que la contrainte d'économie est respectée.
		
		Question : comment minimiser X ?
		
		Alternativement, utiliser la fonction quadratique associée à M puis résoudre le système obtenu ?


Travailler avec une forme "minimale" et bipartite du graphe :
	
		C'est-à-dire que le graphe serait divisé en deux sous-graphes O et N tels que 
		O représente l'ensemble des sommets possédant une école et N l'ensemble des sommets ne possédant pas d'écoles.
		O doit contenir le moins d'éléments possible, et N le plus d'éléments possible : contrainte d'économie
		le graphe est bipartite : contrainte d'accessibilité
		
		Question : comment minimiser le graphe bipartite ? 
		D'après https://stackoverflow.com/questions/20107645/minimizing-number-of-crossings-in-a-bipartite-graph,
		ce genre de problème est NP-difficile
*/
