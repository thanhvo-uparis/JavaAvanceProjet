package main.algorithmique;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

import main.entites.Agglomeration;
import main.entites.Ville;
import main.exceptions.VilleException;
import main.io.ChargeurProprietes;

/**
 * Classe statique contenant plusieurs algorithmes permettant de minimiser plus ou moins bien le nombre d'écoles d'une agglomération.
 * @author Yann Trividic
 * @version 1.1
 */

//TODO vérifier qu'on ne peut pas créer d'Agglomération de taille inférieure à 2
//TODO Changer le franglais
public class Algos {

	/**
	 * Méthode statique permettant de retirer un certain nombre d'écoles à l'agglomération passée en argument.
	 * Cet algorithme est très naïf et très améliorable.
	 * @see		Agglomeration algorithmeApproximationUnPeuMoinsNaif(int, Agglomeration)
	 * @param 	k	nombre d'itérations de l'algorithme.
	 * @param	agg	l'agglomération dont on cherche à réduire le nombre d'écoles. 
	 * @return	la même agglomération que celle passée en argument, mais avec une configuration d'écoles différentes
	 */
	public static Agglomeration algorithmeApproximationNaif(Agglomeration agg, int k) {
		ArrayList<Ville> villes = (ArrayList<Ville>) agg.getVilles() ;
		for(int i = 0 ; i < k ; i++) {
			Ville v = villes.get((int) (Math.random()*villes.size())) ;
			try {
				if(v.getAEcole()) {
					agg.retirerEcole(v);
					//System.out.println("Retrait") ;
				} else {
					agg.ajouterEcole(v) ;
					//System.out.println("Ajout") ;
				}
			} catch(Exception e) {
				//System.out.println(e) ;
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
	public static Agglomeration algorithmeApproximationUnPeuMoinsNaif(Agglomeration agg, int k) {
		ArrayList<Ville> villes = (ArrayList<Ville>) agg.getVilles() ;
		int scoreCourant = agg.getNbEcoles();
		for(int i = 0 ; i < k ; i++) {
			Ville v = villes.get((int) (Math.random()*villes.size())) ;
			try {
				if(v.getAEcole()) {
					agg.retirerEcole(v);
				} else agg.ajouterEcole(v) ;
			} catch(Exception e) {
				//System.out.println(e) ;
			}
			if(agg.getNbEcoles() < scoreCourant) {
				i = 0 ;
				scoreCourant = agg.getNbEcoles() ;
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
	 * Cet algorithme est efficace lorsque le nombre de villes est relativement faible. Il permet parfois d'obtenir de meilleurs résultats que algorithmeParSoustraction()
	 * La complexité de cet algorithme est correcte mais les résultats sont de plus en plus mauvais plus le nombre de villes est grand.
	 * @param agg						l'agglomération dont on cherche à réduire le nombre d'écoles. Le graphe qui la représente peut-être non-connexe et orienté.
	 * @param garderEcolesConstruites	booléen permettant de préciser si on souhaite garder ou non les écoles déjà présentes dans l'agglomération
	 * @see algorithmeParSoustraction(Agglomeration, boolean, boolean)
	 * @return							l'agglomération donnée en argument avec un nombre d'école minimisé
	 */
	
	public static Agglomeration algorithmeFilePriorite(Agglomeration agg, boolean garderEcolesConstruites) {
		
		if(!garderEcolesConstruites) agg.clearEcole(); 	// on enlève toutes les écoles présentes. L'algorithme fonctionne si on décide de garder les écoles
														// déjà construites. Le nombre d'écoles final risque cependant d'être supérieur.
				
		ArrayList<Ville> villes = (ArrayList<Ville>) agg.getVilles();
				
		// On utilise une PriorityQueue dont le critère de comparaison est le nombre de villes qui bénéficieraient de l'ajout d'une ville dans la ville courante
		PriorityQueue<Ville> p = new PriorityQueue<Ville>(villes.size(), Comparator.comparing(Ville::beneficeSiAjoutEcole).reversed());
		// Pour randomiser les tests, si les algorithmes des PriorityQueue sont totalement déterministes,
		// on peut modifier beneficeSiAjoutEcole en ajoutant un bruit aléatoire à la valeur retournée (voir le commentaire dans la méthode citée)
		
		for(Ville v : villes) { 											// cette boucle met une école dans les villes qui devront forcément contenir une école
			ArrayList<Ville> voisins = v.getVoisins();
			if(voisins.size() == 0) {
				v.setHasEcole(true);
			} else if(voisins.size() == 1 || voisins.size() == 0) { 		// c'est-à-dire les villes qui n'ont qu'un voisin et qui n'ont pas déjà d'école construite
				if(!v.getAEcole()) voisins.get(0).setHasEcole(true);		// le 2e if sert dans le cas d'une agglomération de deux villes 
			} else if (!p.contains(v)){										// ou qu'on a choisi de garder les écoles déjà construites
				p.add(v); 													// si elles ont plus d'un voisin, on les ajoute dans la file de priorité
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
													// ... on défile et on ajoute une école dans la ville défilée si elle n'en a pas déjà une. Sinon, on ne fait rien.
			v.setHasEcole(true);
			if(!p.isEmpty()) p.add(p.remove());		// permet d'actualiser la file de priorité selon les nouveaux résultats du comparateur, 
													// c'est çe qui est vraiment coûteux en temps de calcul dans l'algorithme donc on le fait uniquement quand la file doit

		}											// être actualisée. La complexité est apparemment améliorable avec un tas de Fibonacci
													// voir https://stackoverflow.com/questions/1871253/updating-java-priorityqueue-when-its-elements-change-priority
		return agg;							
	}	
	
	/**
	 * Algorithme permettant de trouver de bonnes approximations de la répartition optimale d'écoles dans une agglomération. Permet l'utilisation
	 * d'une approche récursive pour extraire une liste de configurations possibles lorsque l'algorithme ne trouve pas directement une configuration qui lui
	 * semble optimale. L'algorithme ne prend pas en compte les écoles pré-existantes dans l'agglomération.
	 * Cet algorithme a une complexité calcul exponentielle dans les pire cas. Dans les cas moyens, la complexité est acceptable.
	 * Le score de cet algorithme (nombre d'écoles par villes) est en moyenne meilleur que l'algorithmeFilePriorite le nombre de villes est supérieur à 30 mais peut-être moins bon
	 * dans de très rares cas.
	 * @param agg Agglomération connexe ou non. Si cette agglomération possède des écoles, celles-ci seront retirées dès le début de l'exécution de la méthode.
	 * @param estDynamique Booléen permettant de choisir entre une approche dynamique permettant potentiellement d'extraire différentes configurations, et une approche directe qui ne trouvera qu'une seule
	 * configuration possiblement (celle-ci sera possiblement moins bonne qu'avec l'approche dynamique)
	 * @param utiliseFilePriorite Booléen permettant de choisir si l'utilisateur veut utiliser une file de priorité ou non pour déterminer l'ordre dans lequel
	 * seront remplies avec des écoles les villes voisines de villes de degré 1. Permet d'améliorer significativement la complexité calcul et de la limiter dans les pire cas. 
	 * Le score de l'algorithme peut varier selon la valeur de ce paramètre. Il sera en moyenne meilleur si utiliseFilePriorite est à true
	 * @return Une agglomération avec une configuration d'écoles proche de la configuration optimale
	 * @see algorithmeFilePriorite(Agglomeration, boolean)
	 */
	public static Agglomeration algorithmeParSoustraction(Agglomeration agg, boolean estDynamique, boolean utiliseFilePriorite) {
		return algorithmeParSoustraction(agg, estDynamique, null, null, true, utiliseFilePriorite) ;
	}

	private static Agglomeration algorithmeParSoustraction(Agglomeration agg, boolean estDynamique, 
														   ListeAdjacence laRecursivite,
														   ArrayList<ArrayList<String>> meilleuresRepartitionsRecursivite, 
														   boolean premiereIteration, boolean utiliseFilePriorite) {

		ListeAdjacence la ;
		ArrayList<ArrayList<String>> meilleuresRepartitions ; // pour stocker les meilleures répartitions
		
		// Initialisation lors de la première entrée dans l'algorithme
		if(premiereIteration == true) {
			agg.clearEcole() ; 												// On vide toutes les écoles présentes dans l'agglomération
			la = new ListeAdjacence(agg.getVilles()) ; 						// On instancie une liste d'adjacence à partir de l'agglomération
			meilleuresRepartitions = new ArrayList<ArrayList<String>>() ;
		} else {															// dans le cas où on est déjà dans la récursion, on prend simplement les arguments passés en paramètres par la fonction déjà en cours d'exécution
			la = new ListeAdjacence(laRecursivite) ;
			meilleuresRepartitions = meilleuresRepartitionsRecursivite ;
		}
				
		// 1ère étape : on ajoute des écoles dans les villes ayant un seul voisin dans une file
		LinkedList<String> file = new LinkedList<String>() ;
		PriorityQueue<Ville> pqVoisinsDegreUn = new PriorityQueue<Ville>(agg.getVilles().size(), Comparator.comparing(Ville::getNbVoisins).reversed()) ;
		
		if(utiliseFilePriorite) {
			la.voisinsDegreUn(agg, pqVoisinsDegreUn) ; // enfile tous les voisins des villes de degré 1
		} else {
			la.voisinsDegreUn(file) ;
		}
		
		// 2e étape : Tant qu'on peut, on va essayer d'ajouter des écoles dans les villes qui doivent de toute façon en accueillir.
		do {
			// 2.a) dans les villes voisines des villes de degré 1
			if(utiliseFilePriorite) { // selon l'algorithme qu'on a choisi, on peut utiliser ou non une file de priorité pour remplir cette tâche
				remplirVillesDegreUn(agg, la, pqVoisinsDegreUn) ;
			} else remplirVillesDegreUn(agg, la, file) ;
			
			// 2.b) s'il y a des villes orphelines, c'est-à-dire des villes qui se retrouvent sans voisin, on leur ajoute automatiquement une école
			remplirVillesDegreZero(agg, la, file) ;
			
			// Il est possible que cette opération ait généré des villes auxquelles il faudra de toute façon rajouter des écoles,
			// on vérifie ça en réappellant la méthode voisinsDegreUn()
			if(utiliseFilePriorite) {
				la.voisinsDegreUn(agg, pqVoisinsDegreUn) ;
			} else la.voisinsDegreUn(file) ;
		} while(!pqVoisinsDegreUn.isEmpty() && !agg.respecteAccessibilite()) ;

		// 3e étape : S'il n'y a aucun point d'entrée dans l'algorithme, c'est-à-dire aucune ville de degré 1, alors il va falloir explorer récursivement le graphe pour trouver une bonne répartition d'écoles
		if(!agg.respecteAccessibilite()) {
			if(estDynamique) { 		// dans le cas où on souhaite explorer récursivement le graphe pour trouver la meilleure configuration
				meilleuresRepartitions = remplirVillesSansDegreUn(agg, la, meilleuresRepartitions, true, utiliseFilePriorite) ;
			} else { 				// dans le cas où on souhaite rapidement trouver une bonne approximation de la configuration optimale
				meilleuresRepartitions = remplirVillesSansDegreUn(agg, la, meilleuresRepartitions, false, utiliseFilePriorite) ;
			}
		}
		
		// 4e étape : Si on a remonté toute la recursivité, alors on clot l'algorithme de la sorte
		if(premiereIteration == true) {
			if(meilleuresRepartitions.size() > 1) { // tri des répartitions possibles quand il y en a plusieurs
				for(ArrayList<String> al : meilleuresRepartitions) Collections.sort(al); 		// par ordre alphabétique
				meilleuresRepartitions.sort(Comparator.comparing(ArrayList<String>::size)); 	// puis par taille
			}
			if(!meilleuresRepartitions.isEmpty()) { // pas la meilleure manière de faire... A travailler
				agg.clearEcole();
				try {
					agg.ajouterEcole(meilleuresRepartitions.get(0)); // étant donné que la liste a été triée, c'est toujours la meilleure répartition qui est donnée
				} catch (Exception e) { if(ChargeurProprietes.getPropriete("affichageExceptions")) System.out.println("meilleuresRepartitions est vide.") ;}
			}
		}
		return agg ;
	}

	private static void remplirVillesDegreUn(Agglomeration agg, ListeAdjacence la, LinkedList<String> file) {
		while(!agg.respecteAccessibilite() && !file.isEmpty()) {
			String c = file.poll() ;
			try {
				try { //si la ville ville voisine n'a pas d'école, alors on en met une, sinon non
					boolean voisinDegreUnAEcole = agg.getVille(la.get(c).get(0)).getAEcole() ;
					if(!voisinDegreUnAEcole) agg.getVille(c).setHasEcole(true);
				} catch (IndexOutOfBoundsException e) { //cette exception est levée uniquement dans le cas où la ville n'aurait plus de voisin
					agg.getVille(c).setHasEcole(true);	//si la ville n'a plus aucun voisin, alors on lui ajoute une école
				}
			} catch(VilleException e) {
				if(ChargeurProprietes.getPropriete("affichageExceptions")) System.err.println("La ville "+c+" n'a pas pu être accédée.") ;
			} catch (NullPointerException e) {
				if(ChargeurProprietes.getPropriete("affichageExceptions")) System.err.println("La ville que vous essayez d'accéder n'est plus dans la liste d'adjacence");
			} 
			
			if(la.containsKey(c)) { //on retire la ville et ses voisins de la liste d'adjacence
				la.removeVilleEtVoisins(c); 
			}
		}
	}
	
	// Variante de la méthode remplirVillesDegreUn() avec une file de priorité
	private static void remplirVillesDegreUn(Agglomeration agg, ListeAdjacence la, PriorityQueue<Ville> p) {
		while(!agg.respecteAccessibilite() && !p.isEmpty()) {
			String c = p.poll().getKey() ;
			try {
				try { //si la ville ville voisine n'a pas d'école, alors on en met une, sinon non
					boolean voisinDegreUnAEcole = agg.getVille(la.get(c).get(0)).getAEcole() ;
					if(!voisinDegreUnAEcole) agg.getVille(c).setHasEcole(true);
				} catch (IndexOutOfBoundsException e) { //cette exception est levée uniquement dans le cas où la ville n'aurait plus de voisin
					agg.getVille(c).setHasEcole(true);	//si la ville n'a plus aucun voisin, alors on lui ajoute une école
				}
			} catch(VilleException e) {
				if(ChargeurProprietes.getPropriete("affichageExceptions")) System.err.println("La ville "+c+" n'a pas pu être accédée.") ;
			} catch (NullPointerException e) {
				if(ChargeurProprietes.getPropriete("affichageExceptions")) System.err.println("La ville que vous essayez d'accéder n'est plus dans la liste d'adjacence");
			} 
			if(la.containsKey(c)) { //on retire la ville et ses voisins de la liste d'adjacence
				la.removeVilleEtVoisins(c); 
			}
		}
	}
	
	private static void remplirVillesDegreZero(Agglomeration agg, ListeAdjacence la, LinkedList<String> file) {
		la.degreZero(file) ; // enfile toutes les files de degré 0 dans file
		while(!agg.respecteAccessibilite() && !file.isEmpty()) {
			String cDegreZero = file.poll() ;
			try {
				agg.getVille(cDegreZero).setHasEcole(true);
			} catch (VilleException e) {
				if(ChargeurProprietes.getPropriete("affichageExceptions")) System.err.println("La ville "+cDegreZero+" n'a pas pu être accédée.") ;
			}
			la.remove(cDegreZero); // on vide la liste d'adjacence des villes orphelines
		}
	}
	
	private static ArrayList<ArrayList<String>> remplirVillesSansDegreUn(Agglomeration agg, ListeAdjacence la, ArrayList<ArrayList<String>> meilleuresRepartitions, boolean estDynamique, boolean utiliseFilePriorite) {

		ArrayList<String> villesAEcoles = agg.getVillesAEcole() ; // on stocke l'état des écoles de l'agglomération dans une variable
		ArrayList<String> listePlusHautsDegres ;
		
		if(estDynamique) { 	// On met dans un ArrayList toutes les villes ayant le plus haut sommet
			listePlusHautsDegres = la.plusHautsDegres() ;
		}
		else { 				// Dans le cas où on ne veut pas faire de programmation dynamique, on va juste faire comme s'il n'y avait qu'une seule ville de plus haut sommet
			listePlusHautsDegres = new ArrayList<String>() ;
			listePlusHautsDegres.add(la.plusHautDegre()) ;
		}
		
		for(String c : listePlusHautsDegres) { // on regarde quelles configurations seraient possibles pour chaque possibilités et on les stocke dans meilleuresRepartitions
			try { agg.getVille(c).setHasEcole(true); } catch (VilleException e) { 
				if(ChargeurProprietes.getPropriete("affichageExceptions")) System.err.println("La ville "+c+" n'a pas pu être accédée.") ; 
			}
			
			ListeAdjacence recursionLA = new ListeAdjacence(la) ; // crée une shallow copy de la liste d'adjacence courante pour permettre de faire des manipulations dessus
			recursionLA.removeVilleEtVoisins(c) ;
	
			algorithmeParSoustraction(agg, true, recursionLA, meilleuresRepartitions, false, utiliseFilePriorite) ; //on entre dans la récursivité avec la shallow copy
	
			if(agg.respecteAccessibilite() && !meilleuresRepartitions.contains(agg.getVillesAEcole())) { // quand cette étape est finie, on a forcément une configuration respectant la contrainte d'accessibilité, on la conserve dans meilleuresRepartitions
				meilleuresRepartitions.add(agg.getVillesAEcole()); // on ajoute la composition trouvée dans meilleuresRepartitions sans les doublons
			}
			
			agg.clearEcole(villesAEcoles); // on enleve toutes les écoles sauf celles dans villesAEcoles pour reprendre au stade où on en était avant cette itération de l'algorithme
		}
		return meilleuresRepartitions ;
	}
}