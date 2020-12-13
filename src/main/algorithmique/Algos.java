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
 * Classe donnant l'accès à plusieurs algorithmes permettant de minimiser plus ou moins bien le nombre d'écoles d'une agglomération.
 * @author Yann Trividic
 * @version 1.0
 */

// TODO Changer le franglais
// TODO alléger les commentaires quand il y en a trop, ajouter quand il y en a pas assez
// TODO proposer à l'utilisateur les meilleures combinaisons possibles


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
				if(v.getHasEcole()) {
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
		System.out.println("Entrée dans algorithmeApproximationUnPeuMoinsNaif") ;
		ArrayList<Ville> villes = (ArrayList<Ville>) agg.getVilles() ;
		int scoreCourant = agg.nbEcoles();
		for(int i = 0 ; i < k ; i++) {
			Ville v = villes.get((int) (Math.random()*villes.size())) ;
			try {
				if(v.getHasEcole()) {
					agg.retirerEcole(v);
				} else agg.ajouterEcole(v) ;
			} catch(Exception e) {
				//System.out.println(e) ;
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
		System.out.println("Entrée dans algorithmeFilePriorite") ;
		// TODO tester la validité de cet algorithme, sa complexité et vérifier empiriquement s'il permet de trouver le résultat optimal à chaque fois
		// ^^^^^ à faire dans la classe Tester
		
		if(!garderEcolesConstruites) agg.clearEcole(); 	// on enlève toutes les écoles présentes. L'algorithme fonctionne si on décide de garder les écoles
		//System.out.println(agg.nbEcoles()) ;			// déjà construites. Le nombre d'écoles final risque cependant d'être supérieur.
		
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
		//agg.afficheBilan();
		//System.out.println(p.size()) ;
		
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
	
	
	
	
	
	
	// INITIALISATION DE LA RECURSIVITE
	public static Agglomeration algorithmeParSoustraction(Agglomeration agg, boolean estDynamique) {
		return algorithmeParSoustraction(agg, estDynamique, null, null, true) ;
	}

	private static Agglomeration algorithmeParSoustraction(Agglomeration agg, boolean estDynamique, ListeAdjacence laRecursivite, ArrayList<ArrayList<String>> meilleuresRepartitionsRecursivite, boolean premiereIteration) {
				
			/* Structure générale de l'algorithme algoPS() :
			 * 
			 * Paramètres :
			 * 		Strate : la profondeur de l'algorithme initialisé à 0 
			 * 		L : une liste d'adjacence qui évoluera au fur et à mesure de l'algorithme
			 * 		agg : une agglomération sans école
			 * 
			 * Variables :
			 * 		Soit F une file vide
			 * 		Soit Configs une liste vide
			 * 
			 * Initialisation : 
			 * 		on appelle algoPS(strate = 0, L = vide)
			 * 
			 * Déroulement de l'algorithme :
			 * 
			 * Si strate = 0 
			 * 		L est construite à partir d'agg
			 * 			  
			 * Tant que la liste d'adjacence n'est pas vide
			 * 		Tant qu'il y a des sommets de degré 1 dans L
			 * 			Ajouter toutes les villes de degré 1 dans F avec leurs voisins
			 * 			Ajouter une école à tous les voisins des villes de degré 1 sauf si leur voisin a déjà une école
			 * 		 
			 *		 	Tant que F n'est pas vide
			 *				défiler F dans V
			 *				pour chaque entrée de L, retirer V et ses voisins de l'entrée
			 *				retirer V de L
			 *
			 *		Ajouter une école dans chaque ville de degré 0
			 *		Retirer toutes les écoles de degré 0 de L
			 * 
			 * 		Si la contrainte d'accessibilité n'est pas respectée dans Agg
			 * 			enfiler tous les sommets de plus haut degré dans FplusHaut
			 * 			listeEcoles = liste des écoles d'agg
			 * 			Copier l'état courant de L dans une shallowCopy
			 * 			Tant que FplusHaut n'est pas vide
			 * 				défiler FplusHaut dans u	
			 * 				enlever U et tous ses voisins de la shallowCopy
			 * 				algoPS(strate+1, shallowCopy)
			 * 				enfiler les écoles d'agg dans Configs
			 * 				retirer toutes les écoles d'agg qui ne sont pas dans listeEcoles
			 * 			
			 */

		//if(ChargeurProprietes.getPropriete("affichageDebug")) System.out.println("\n\n\n-----------------------------------------------------------------------------------------------\n\n\n") ;
		
		System.out.println("Entrée dans algorithmeParSoustraction") ;
		
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
		
		/*
		System.out.println("premiereIteration = "+premiereIteration) ;
		System.out.print("Affichage meilleure répartition INITIALISATION: ") ;
		for(ArrayList<String> ss : meilleuresRepartitions) {
			for(String s : ss) System.out.print(" "+s);
			System.out.print("\n") ;
		}
		System.out.println("\n") ;
		
		if(ChargeurProprietes.getPropriete("affichageExceptions")) System.out.println("Initialisation : "+la.toString()) ;
		*/	
				
		// 1ère étape : on ajoute des écoles dans les villes ayant un seul voisin dans une file
		LinkedList<String> file = new LinkedList<String>() ;
		la.voisinsDegreUn(file) ; // enfile tous les voisins des villes de degré 1
		
		// 2e étape : Tant qu'on peut, on va essayer d'ajouter des écoles dans les villes qui doivent de toute façon en accueillir.
		do {
			// 2.a) on met des écoles dans chacune des villes de la file, tout en les retirant de la liste d'adjacence avec leurs voisines.
			//System.out.println("Entrée dans la fonction remplirEcolesVillesDegreUN()") ;
			remplirVillesDegreUn(agg, la, file) ;
			System.out.println(la.toString());
			//System.out.println("Sortie de la fonction remplirEcolesVillesDegreUn() (strate = "+strateRecursivite+") : ") ;
			//System.out.println(la.toString()) ; // Après un premier nettoyage
				
			// 2.b) s'il y a des villes orphelines, c'est-à-dire des villes qui se retrouvent sans voisin, on leur ajoute automatiquement une école
			//System.out.println("Entrée dans la fonction remplirEcolesVillesDegreZERO()") ;
			remplirVillesDegreZero(agg, la, file) ;
			//System.out.println("Affichage de l'état de la file après sortie du while des voisins de degré 0 (strate = "+strateRecursivite+") :") ;
			//System.out.println(la.toString()) ; // Après un premier nettoyage
			la.voisinsDegreUn(file) ; // Il est possible que cette opération ait généré des villes auxquelles il faudra de toute façon rajouter des écoles.
			for(String s : file) {
				System.out.print(s+" ") ;
			}
			System.out.print("\n") ;
			agg.afficheBilan();
		} while(!file.isEmpty() && !agg.respecteAccessibilite()) ;

		// 3e étape : S'il n'y a aucun point d'entrée dans l'algorithme, c'est-à-dire aucune ville de degré 1, alors il va falloir explorer récursivement le graphe pour trouver une bonne répartition d'écoles
		if(!agg.respecteAccessibilite()) { //&& !la.isEmpty()) { //TODO vérifier si l'algo reste valide sans la deuxième condition
			if(estDynamique) { // dans le cas où on souhaite explorer récursivement le graphe pour trouver la meilleure configuration
				//System.out.println("Entrée dans la fonction remplirVillesDynamiquement() ("+strateRecursivite+")") ;
				meilleuresRepartitions = remplirVillesSansDegreUn(agg, la, meilleuresRepartitions, true) ;
				//System.out.println("Sortie de la fonction remplirVillesDynamiquement() ("+strateRecursivite+")") ;
			} else { // dans le cas où on souhaite rapidement trouver une bonne approximation de la configuration optimale
				meilleuresRepartitions = remplirVillesSansDegreUn(agg, la, meilleuresRepartitions, false) ;
			}
			/*
			if(ChargeurProprietes.getPropriete("affichageExceptions")) { //ce bloc sert à afficher l'état de meilleures répartitions

				System.out.print("Affichage meilleure répartition : ") ;
				for(ArrayList<String> ss : meilleuresRepartitions) {
					for(String s : ss) System.out.print(" "+s);
					System.out.print("\n") ;
				}
				System.out.print("\n") ;
			}*/
		}
		
		if(premiereIteration == true) {// Cette partie ne s'exécute uniquement quand l'algorithme est en train de conclure
			System.out.println("BILAN FINAL") ;
			if(!meilleuresRepartitions.isEmpty()) {
				agg.clearEcole();
				try {
					agg.ajouterEcole(meilleuresRepartitions.get(0));	// Par défaut, on met la meilleure configuration dans l'agglo
					agg.afficheBilan();
				} catch (Exception e) { System.out.println(e) ;}
			}
			if(meilleuresRepartitions.size() > 1) {
				for(ArrayList<String> al : meilleuresRepartitions) Collections.sort(al); 				// Chacune des configurations est triée par ordre alphabétique
				meilleuresRepartitions.sort(Comparator.comparing(ArrayList<String>::size).reversed()); 	// Puis par taille
				System.out.println("Voici certaines des meilleures configurations possibles pour construire vos écoles :") ;
				for(ArrayList<String> ss : meilleuresRepartitions) {
					for(String s : ss) System.out.print(s+" ");
					System.out.print("\n") ;
				}
			}
		}
			
		return agg ;
	}

	private static LinkedList<String> remplirVillesDegreUn(Agglomeration agg, ListeAdjacence la, LinkedList<String> file) {
		System.out.println("Entrée dans remplirVillesDegreUn") ;
		while(!agg.respecteAccessibilite() && !file.isEmpty()) {
			String c = file.poll() ;
			try {
				try { //si la ville ville voisine n'a pas d'école, alors on en met une, sinon non
					boolean voisinDegreUnAEcole = agg.getVille(la.get(c).get(0)).getHasEcole() ;
					if(!voisinDegreUnAEcole) agg.getVille(c).setHasEcole(true);
				} catch (IndexOutOfBoundsException e) { //cette exception est levée uniquement dans le cas où la ville n'aurait plus de voisin
					agg.getVille(c).setHasEcole(true); //si la ville n'a plus aucun voisin, alors on lui ajoute une école
				}
			} catch(VilleException e) {
				//System.err.println("La ville "+c+" n'a pas pu être accédée.") ;
			} catch (NullPointerException e) {
				//if(ChargeurProprietes.getPropriete("affichageExceptions")) System.out.println("La ville que vous essayez d'accéder n'est plus dans la liste d'adjacence");
			} 
			
			//TODO il y a de la marge pour optimiser ça 
			if(la.containsKey(c)) {
				//if(ChargeurProprietes.getPropriete("affichageExceptions")) System.out.println("Entrée removeVilleEtVoisins pour "+c);
				la.removeVilleEtVoisins(c); 
				//if(ChargeurProprietes.getPropriete("affichageExceptions")) System.out.println(la.toString()) ;
			}
		}
		return file ;
	}
	
	private static void remplirVillesDegreZero(Agglomeration agg, ListeAdjacence la, LinkedList<String> file) {
		System.out.println("Entrée dans remplirVillesDegreZero") ;
		la.degreZero(file) ; // enfile toutes les files de degré 0 dans file
		//if(ChargeurProprietes.getPropriete("affichageExceptions")) System.out.println("Sortie du while des voisins de degré 0") ;
		while(!agg.respecteAccessibilite() && !file.isEmpty()) {
			String cDegreZero = file.poll() ;
			try {
				agg.getVille(cDegreZero).setHasEcole(true);
			} catch (VilleException e) {
				if(ChargeurProprietes.getPropriete("affichageExceptions")) System.err.println("La ville "+cDegreZero+" n'a pas pu être accédée.") ;
			}
			la.remove(cDegreZero); // on vide la HashMap des villes orphelines
		}
	}
	
	private static ArrayList<ArrayList<String>> remplirVillesSansDegreUn(Agglomeration agg, ListeAdjacence la, ArrayList<ArrayList<String>> meilleuresRepartitions, boolean estDynamique) {
		System.out.println("Entrée dans remplirVillesSansDegreUn") ;

		ArrayList<String> villesAEcoles = agg.getVillesAEcole() ;
		ArrayList<String> listePlusHautsDegres ;
		
		if(estDynamique) { 	// On met dans un ArrayList toutes les villes ayant le plus haut sommet
			listePlusHautsDegres = la.plusHautsDegres() ;
		}
		else { 				// Dans le cas où on ne veut pas faire de programmation dynamique, on va juste faire comme s'il n'y avait qu'une seule ville de plus haut sommet
			listePlusHautsDegres = new ArrayList<String>() ;
			listePlusHautsDegres.add(la.plusHautDegre()) ;
		}
		
		//il y a un bug à cet endroit : on ne peut pas sortir du loop car il reprend à chaque fois à 0 lorsqu'il remonte de la récursivité
		//l'algorithme fonctionne donc pour descendre au fond de la récursivité mais pas pour remonter.
		//System.out.println("Entrée dans la boucle de listePlusHautsDegres ("+strateRecursivite+") : ") ;
		for(String c : listePlusHautsDegres) {
			/*
			System.out.print("listePlusHautsDegres : ") ;
			for(String s : listePlusHautsDegres) System.out.print(s+" ") ;
			System.out.print("\n");
			System.out.println("Sommet courant = "+c) ;
			*/
			try { agg.getVille(c).setHasEcole(true); } catch (VilleException e) {}	//TODO	
			ListeAdjacence recursionLA = new ListeAdjacence(la) ; //crée une shallow copy de la liste d'adjacence courante pour permettre de faire des manipulations dessus
			
			/*
			if(ChargeurProprietes.getPropriete("affichageExceptions")) {
				System.out.println("Affichage de recursionLA avant removeVilleEtVoisins("+c+") : \n"+recursionLA.toString()) ;
				//System.out.println("Affichage de la avant removeVilleEtVoisins("+c+") : \n"+la.toString()) ;
			}*/
		
			recursionLA.removeVilleEtVoisins(c) ; //le c est une copie ?
			
			/*
			if(ChargeurProprietes.getPropriete("affichageExceptions")) {
				System.out.println("Affichage de recursionLA après removeVilleEtVoisins("+c+") : \n"+recursionLA.toString()) ;
				//System.out.println("Affichage de la avant removeVilleEtVoisins("+c+") : \n"+la.toString()) ;
			}
			
			if(ChargeurProprietes.getPropriete("affichageExceptions")) {
				//System.out.println("Affichage de recursionLA avant récursivité : \n"+recursionLA.toString()) ;
				//System.out.println("Affichage de la avant récursivité  : \n"+la.toString()) ;
				System.out.println("Entrée dans algorithmeParSoustraction de strate "+(strateRecursivite+1)) ;
			}
			
			System.out.println("Villes à écoles avant avoir clearEcoles() :") ;
			agg.afficheVilleAEcole();
			System.out.println(agg.getVillesAEcole().size()+ " "+ meilleuresRepartitions.size()) ;
			*/
			algorithmeParSoustraction(agg, true, recursionLA, meilleuresRepartitions, false) ; //on entre dans la récursivité avec la shallow copy
	
			if(agg.respecteAccessibilite() && !meilleuresRepartitions.contains(agg.getVillesAEcole())) { // quand cette étape est finie, on a forcément une configuration respectant la contrainte d'accessibilité, on la conserve dans meilleuresRepartitions
				meilleuresRepartitions.add(agg.getVillesAEcole()); // on ajoute la composition trouvée dans meilleuresRepartitions sans les doublons
			}
			/*
			System.out.print("Affichage meilleure répartition : ") ;
			for(ArrayList<String> ss : meilleuresRepartitions) {
				for(String s : ss) System.out.print(" "+s);
				System.out.print("\n") ;
			}
			System.out.println("\n") ;
			*/
			agg.clearEcole(villesAEcoles); // on enleve toutes les écoles sauf celles dans villesAEcoles pour reprendre au stade où on en était avant cette itération de l'algorithme
			
			//System.out.println("Villes à écoles après avoir clearEcoles() :") ;
			//agg.afficheVilleAEcole();
		}
		//System.out.println("Sortie de la boucle de listePlusHautsDegres ("+strateRecursivite+") : ") ;
		
		return meilleuresRepartitions ;
	}
}




/*

private static void remplirVillesDynamiquement(Agglomeration agg, ListeAdjacence la, int strateRecursivite) {
	if(ChargeurProprietes.getPropriete("affichageExceptions")) {
		System.out.println("Entrée condition estDynamique : strateRecursivite = "+strateRecursivite) ;
		System.out.println("Ecoles lors de l'entrée :") ;
		agg.afficheVilleAEcole();
	}

	ArrayList<ArrayList<String>> meilleuresRepartitions = new ArrayList<ArrayList<String>>() ;
	ArrayList<String> villesAEcoles = agg.getVillesAEcole() ;
	ArrayList<String> listePlusHautsDegres = la.plusHautsDegres() ;
	
	//il y a un bug à cet endroit : on ne peut pas sortir du loop car il reprend à chaque fois à 0 lorsqu'il remonte de la récursivité
	//l'algorithme fonctionne donc pour descendre au fond de la récursivité mais pas pour remonter.
	for(String c : listePlusHautsDegres) {
		
		if(ChargeurProprietes.getPropriete("affichageExceptions")) {
			System.out.print("listePlusHautsDegres : ") ;
			for(String s : listePlusHautsDegres) System.out.print(s+" ") ;
			System.out.print("\n");
		}
		try {
			if(ChargeurProprietes.getPropriete("affichageExceptions")) System.out.println("Ajout d'école dans la ville "+c) ;
			agg.getVille(c).setHasEcole(true);
			
		} catch (VilleException e) {
			if(ChargeurProprietes.getPropriete("affichageExceptions")) System.err.println("La ville "+c+" n'a pas pu être accédée.") ;
		}		
		ListeAdjacence recursionLA = new ListeAdjacence(la) ;
		//if(affichageDebug) System.out.println("recursionLA == la ? "+(recursionLA==la?"Oui":"Non")) ; // objets identiques ou non (même adresse)
		//if(affichageDebug) System.out.println("recursionLA.equals(la) ? "+(recursionLA.equals(la)?"Oui":"Non")) ; //valeurs égales ou non

		if(ChargeurProprietes.getPropriete("affichageExceptions")) {
			System.out.println("Affichage de recursionLA avant removeVilleEtVoisins("+c+") : \n"+recursionLA.toString()) ;
			//System.out.println("Affichage de la avant removeVilleEtVoisins("+c+") : \n"+la.toString()) ;
		}
		
		recursionLA.removeVilleEtVoisins(c) ; //le c est une copie ?
		if(ChargeurProprietes.getPropriete("affichageExceptions")) {
			//System.out.println("Affichage de recursionLA avant récursivité : \n"+recursionLA.toString()) ;
			//System.out.println("Affichage de la avant récursivité  : \n"+la.toString()) ;
			System.out.println("Entrée dans algorithmeParSoustraction de strate "+(strateRecursivite+1)) ;
		}
		algorithmeParSoustraction(agg, true, strateRecursivite+1, recursionLA) ; // point d'entrée de la récursivité
		if(!meilleuresRepartitions.contains(agg.getVillesAEcole())) meilleuresRepartitions.add(agg.getVillesAEcole()); // on ajoute la composition trouvée dans meilleuresRepartitions sans les doublons
		
		if(ChargeurProprietes.getPropriete("affichageExceptions")) { //ce bloc sert à afficher l'état de meilleures répartitions
			int compteur = 1 ;
			System.out.println("Meilleures répartitions :") ;
			for(ArrayList<String> repart : meilleuresRepartitions) {
				System.out.print("#"+compteur+" :");
				for(String r : repart) System.out.print(" "+r);
				compteur++ ;
				System.out.print("\n") ;
			}
		}
		
		agg.clearEcole(villesAEcoles); // on enleve toutes les écoles sauf celles dans villesAEcoles
		if(ChargeurProprietes.getPropriete("affichageExceptions")) {
			System.out.println("Villes à écoles après avoir clearEcoles() :") ;
			agg.afficheVilleAEcole();
			System.out.println("Affichage de la à la fin d'une itération de la boucle for des plus hauts degrés : "+la.toString()) ;
		}
	}
	
	meilleuresRepartitions.sort(Comparator.comparing(ArrayList<String>::size).reversed()); //on trie les répartitions
	
	// Dans le cas où on a remonté toutes les strates
	if(strateRecursivite == 0) {
		// Affichage de toutes les combinaisons optimales possibles par ordre alphabétique
		int numPossibilite = 1 ;
		for(ArrayList<String> villesEnPlus : meilleuresRepartitions) {
			villesAEcoles.addAll(villesEnPlus) ;
			Collections.sort(villesAEcoles);
			System.out.println("Possibilité #"+numPossibilite+" : ") ;
			for(String c : villesAEcoles) {
				System.out.print(c+" ");
			}
			System.out.println("\n") ;
		}
		
		// Ajout final de la meilleure combinaison à agg
		try {
			agg.ajouterEcole(meilleuresRepartitions.get(0));
		} catch (Exception e) {
			if(ChargeurProprietes.getPropriete("affichageExceptions")) System.err.println("L'une des villes n'a pas pu être accédée dans l'ajout d'écoles final.") ;
		}	
	}
}

*/


//proposer des contraintes 


















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
