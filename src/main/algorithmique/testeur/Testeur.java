package main.algorithmique.testeur;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.LinkedList;

import main.algorithmique.Algos;
import main.entites.Agglomeration;

/**
 * Classe statique permettant de réaliser diverses tests sur les algorithmes, principalement pour comparer leurs performances.
 * @author Yann Trividic
 * @version 1.0
 */

public class Testeur {
	
	private static final Integer k = 100 ;	// paramètre des deux algorithmes naïfs
	
	/*
	 * Liste des noms d'algorithmes qui sera exécutée lors des tests effectués sur tous les algos de manière transversale.
	 * La valeur de leurs paramètres est spécifiée dans la méthode switchAlgo
	 * si cette liste est modifiée, bien penser à modifier la méthode switchAlgo en conséquence et le return de la méthode getTestSurAlgo
	 */
	public static final String [] algos = { "algorithmeApproximationNaif(agg, "+k+")", 
											 "algorithmeApproximationUnPeuMoinsNaif(agg, "+k+")", 
											 "algorithmeFilePriorite(agg, false)",
											 "algorithmeParSoustraction(agg, true, true)",
											 "algorithmeParSoustraction(agg, false, false)",
											 "algorithmeParSoustraction(agg, true, false)" } ;
	
	private static void switchAlgo(int algo, Agglomeration agg) {
		switch(algo) {
		case 0 :
			Algos.algorithmeApproximationNaif(agg, k) ;
			break;
		case 1 :
			Algos.algorithmeApproximationUnPeuMoinsNaif(agg, k) ;
			break;
		case 2 :
			Algos.algorithmeFilePriorite(agg, false) ;
			break ;
		case 3 :
			Algos.algorithmeParSoustraction(agg, true, true) ;
			break;
		case 4 :
			Algos.algorithmeParSoustraction(agg, false, false) ;
			break;
		case 5 :
			Algos.algorithmeParSoustraction(agg, true, false) ;
			break;
		}
	}
	
	/**
	 * Méthode retournant une liste de Rapport contenant des informations sur l'exécution des algorithmes de la liste algos
	 * @param agg Une agglomération quelconque.
	 * @see algos
	 * @return tests les rapports générés par la méthode
	 */
	public static ArrayList<Rapport> getTestComplexiteTousAlgos(Agglomeration agg) {
		ArrayList<Rapport> tests = new ArrayList<Rapport>(algos.length) ;
		for(int i = 0 ; i < algos.length ; i++) tests.add(getTestSurAlgo(agg, i)) ;
		return tests ;
	}
	
	/**
	 * Méthode permettant d'effectuer un test sur une agglomération aléatoire
	 * @param nbVilles le nombre de villes de l'agglomération aléatoire (minimum 2, maximum 500)
	 * @param algo le numéro associé à l'algorithme à exécuter (voir la méthode privée switchAlgo) ou la liste algos
	 * @see algos
	 * @return rapports contenant le bilan des différentes exécutions d'algorithmes
	 * @throws InputMismatchException dans le cas où les valeurs ne correspondraient pas à celles spécifiées ici.
	 */
	public static Rapport getTestAlgoSurAgglomerationAleatoire(int nbVilles, int algo) throws InputMismatchException {
		return getTestAlgoSurAgglomerationAleatoire(nbVilles, algo, true) ;
	}
	
	private static Rapport getTestAlgoSurAgglomerationAleatoire(int nbVilles, int algo, boolean affichage) throws InputMismatchException {
		if(nbVilles <= 2 || nbVilles >= 500) throw new InputMismatchException("Le nombre de villes minimum pour une agglomération est 2 et le maximum est 500 (pour cet algorithme).") ;
		if(algo < 0 || algo > algos.length-1) throw new InputMismatchException("Algorithme invalide") ;
		if(affichage) System.out.println("Résultat de l'algorithme "+algos[algo]+" sur une agglomération aléatoire de "+nbVilles+" villes.");
		return getTestSurAlgo(GenerateurAgglomeration.randomAggloConnexeGenerateur(nbVilles), algo, affichage) ;
	}
	
	/**
	 * Méthode retournant des Rapport après avoir exécutés les différents algorithmes de la liste algo sur des agglomérations aléatoires
	 * @param nbVillesMin la taille minimale des agglomérations aléatoires à tester (au minimum 2)
	 * @param nbVillesMax la taille maximale des agglomératoires aléatoires à tester (au maximum 500)
	 * @return rapports contenant les résultats des tests effectués
	 * @see algos
	 * @throws InputMismatchException dans le cas où les valeurs ne correspondraient pas à celles spécifiées ici.
	 */
	public static ArrayList<Rapport> getTestsAlgosSurAgglomerationAleatoire(int nbVillesMin, int nbVillesMax) {
		return getTestsAlgosSurAgglomerationAleatoire(nbVillesMin, nbVillesMax, null) ; //permet d'appliquer le calcul sur tous les algorithmes
	}
	
	private static ArrayList<Rapport> getTestsAlgosSurAgglomerationAleatoire(int nbVillesMin, int nbVillesMax, Integer algo) throws InputMismatchException {
		if(nbVillesMin > nbVillesMax) { // dans le cas où on aurait inversé les arguments
			int tmp = nbVillesMin ;
			nbVillesMin = nbVillesMax ;
			nbVillesMax = tmp ;
			System.out.println("Vos arguments étaient dans le mauvais ordre, on a réparé ça pour vous.") ;
		}
		
		if(nbVillesMin < 2) throw new InputMismatchException("On ne peut pas avoir d'agglomération avec moins de 2 villes.") ;
		if(nbVillesMin > 500) throw new InputMismatchException("Cette méthode n'accepte pas d'agglomérations de plus de 500 villes.") ;
		
		ArrayList<Rapport> rapports = new ArrayList<Rapport>((nbVillesMax-nbVillesMin)*algos.length) ;
		
		int affichageAvancement = 0 ;
		
		for(int nbVilles = nbVillesMin ; nbVilles <= nbVillesMax ; nbVilles++) {
			double avancement = (double) (nbVilles-nbVillesMin)/(nbVillesMax-nbVillesMin) * 100 ;
			if(avancement >= affichageAvancement) {
				System.out.println("Génération des résultats en cours... "+String.format("%.2f", avancement)+" %") ;
				affichageAvancement+=2 ;
			}
			if(algo == null) {
				Agglomeration agg = GenerateurAgglomeration.randomAggloConnexeGenerateur(nbVilles) ;
				for(int i = 0; i < algos.length ; i++) {
					rapports.add(getTestSurAlgo(agg, i, false)) ;
				}
			} else {
				rapports.add(getTestAlgoSurAgglomerationAleatoire(nbVilles, algo, false)) ;
			}
		}
		System.out.println("Calcul terminé avec succès.\n") ;
		return rapports ;
	}
	
	/**
	 * Méthode appliquant un algorithme sur une série d'agglomérations.
	 * @param algo int correspondant à l'identifiant d'un algorithme dans la liste algos
	 * @param aggs varargs d'agglomérations quelconque
	 * @return rapports contenant les rapports des tests effectués
	 * @throws InputMismatchException dans le cas où l'algorithme spécifié n'existerait pas dans algos.
	 * @see algos
	 */
	public static ArrayList<Rapport> getTestSurAlgo(int algo, Agglomeration...aggs) throws InputMismatchException {
		if(algo < 0 || algo > algos.length-1) throw new InputMismatchException("Algorithme invalide") ;
		ArrayList<Rapport> tests = new ArrayList<Rapport>() ;
		for(Agglomeration agg : aggs) tests.add(getTestSurAlgo(agg, algo)) ;
		return tests ;
	}
	
	/**
	 * Méthode appliquant un algorithme sur une agglomération.
	 * @param algo int correspondant à l'identifiant d'un algorithme dans la liste algos
	 * @param agg Agglomération quelconque
	 * @return Rapport contenant le bilan du test effectué
	 * @throws InputMismatchException dans le cas où l'algorithme spécifié n'existerait pas dans algos.
	 * @see algos
	 */
	public static Rapport getTestSurAlgo(Agglomeration agg, int algo) {
		 return getTestSurAlgo(agg, algo, true) ;
	}
	
	private static Rapport getTestSurAlgo(Agglomeration agg, int algo, boolean affichage) throws InputMismatchException {
		if(algo < 0 || algo > algos.length-1) throw new InputMismatchException("Algorithme invalide") ;
		long tempsDebut = System.nanoTime();
		switchAlgo(algo, agg) ;
		long tempsFin = System.nanoTime();
		
		double tempsEcoule = tempsFin - tempsDebut;
		//System.out.println("Temps d'exécution en nanosecondes :  " + tempsEcoule);
		//System.out.println("Temps d'exécution en millisecondes : " + tempsEcoule / 1000000);
		
		if(affichage) affichageBilanAlgo(agg, algo) ;
		return new Rapport(algos[algo], //nom de l'algo
				(algo==0||algo==1)?k:null, //valeur de k
				(algo==3||algo==5)?Boolean.valueOf(true):Boolean.valueOf(false), //estDynamique
				(algo==2||algo==3)?Boolean.valueOf(true):Boolean.valueOf(false),
				agg, 
				tempsEcoule/1000000) ;
	}
		
	/**
	 * Méthode affichant et comparant les résultats obtenus par les différents algorithmes de la liste algos
	 * @param agg Une agglomération quelconque
	 * @return rapports contenant les rapports des tests effectués
	 * @see algos
	 */
	public static ArrayList<Rapport> compareAlgorithmes(Agglomeration agg) {
		System.out.println("\n\n-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-\n") ;
		System.out.println("\n\n\n\t **** Comparatif des résultats obtenus par nos algorithmes **** \n") ;
		ArrayList<Rapport> tests = getTestComplexiteTousAlgos(agg) ;
		
		String nomAlgoMeilleurTemps = "";
		double meilleurTemps = Double.MAX_VALUE ;
		LinkedList<String> nomsAlgoMeilleursScore = new LinkedList<String>();
		double meilleurScore = Double.MAX_VALUE ;
		
		System.out.println("-----------------------------\n\n --> Rapports :") ;
		for(Rapport t : tests) {
			System.out.println(t.toString()) ;
			if(t.getTemps() < meilleurTemps) {
				meilleurTemps = t.getTemps() ;
				nomAlgoMeilleurTemps = t.getNomAlgo() ;
			}
			if(t.getScore() < meilleurScore) {
				meilleurScore = t.getScore() ;
				nomsAlgoMeilleursScore.clear(); ;
				nomsAlgoMeilleursScore.add(t.getNomAlgo()) ;
			} else if(t.getScore() == meilleurScore) {
				nomsAlgoMeilleursScore.add(t.getNomAlgo()) ;
			}
		}
		
		System.out.println("\nLe meilleur temps d'exécution a été obtenu par "+nomAlgoMeilleurTemps+" en "+String.format("%.3f", meilleurTemps)+" ms.") ;
		System.out.print("Le meilleur score (ratio nbEcoles/nbVilles) est de "+String.format("%.3f", meilleurScore)+" et a été obtenu par ") ;
		if(nomsAlgoMeilleursScore.size() > 1) {
			System.out.print(":\n") ;
			for(String s : nomsAlgoMeilleursScore) System.out.println(s) ;
		} else {
			System.out.println(nomsAlgoMeilleursScore.get(0)+".\n\n*\n") ;
		}
		
		return tests ;
	}
	
	/**
	 * Méthode permettant d'obtenir une résolution optimale ou quasi-optimale de la disposition des écoles dans l'agglomération.
	 * Etant donné que algorithmeFilePriorite performe parfois mieux avec un petit nombre de villes, on l'utilise ici en parallèle 
	 * d'algorithmeParSoustraction. Quand le nombre de villes est grand, on privilégiera algorithmeParSoustraction.
	 * Il est cependant à noter que algorithmeParSoustraction avec sa file de priorité ne donnera pas à chaque fois le résultat optimal.
	 * Cet algorithme favorise la vitesse de calcul. La méthode compareAlgorithmes vous donnera le meilleur résultat de nos algorithmes à coup sûr.
	 * @param agg Agglomération quelconque
	 * @see compareAlgorithmes(Agglomeration)
	 * @return Rapport de l'exécution de l'algorithme choisi
	 */
	public static Rapport resolutionAgglomerationAvecBascule(Agglomeration agg) {
		System.out.println("\n\n-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-\n") ;
		System.out.println("\n\n\n\t **** Choix automatique de l'algorithme et résolution **** \n") ;
		System.out.print(" --> ") ;
		if(agg.getVilles().size() < 20) {
			Rapport [] coupleRapport = new Rapport[2] ;
			coupleRapport[0] = getTestSurAlgo(agg, 2, false) ;
			ArrayList<String> meilleureRepartition = agg.getVillesAEcole() ;
			coupleRapport[1] = getTestSurAlgo(agg, 3, false) ;
			//System.out.println(coupleRapport[0]) ;
			//System.out.println(coupleRapport[1]) ;
			if(meilleureRepartition.size() > agg.getVillesAEcole().size()) {
				System.out.println("Algorithme utilisé : "+algos[3]+"\n") ;
				agg.afficheBilan();
				return coupleRapport[1] ;
			} else {
				agg.clearEcole();
				try {
					agg.ajouterEcole(meilleureRepartition);
				} catch (Exception e) {
					System.err.println(e) ;
				}
				System.out.println("Algorithme utilisé : "+algos[2]+"\n") ;
				agg.afficheBilan();
				return coupleRapport[0] ;
			}
		} else {
			System.out.println("Algorithme utilisé : "+algos[3]+"\n") ;
			return getTestSurAlgo(agg, 3) ;
		}
	}
	
	
	/**
	 * Méthode écrivant dans un fichier CSV les résultats passés en arguments
	 * @param rapports une liste de rapports quelconque
	 * @param chemin Chemin valide
	 */
	public static void ecrireRapportsDansCSV(ArrayList<Rapport> rapports, String chemin) {
        try (FileWriter fileWriter = new FileWriter(chemin)) {
        	fileWriter.append(Rapport.enteteCSV()+"\n") ;
    		for(Rapport t : rapports) fileWriter.append(t.formatCSV()+"\n") ;
        } catch (FileNotFoundException e) {
            System.err.println("Le chemin suivant est invalide : " + chemin);
        } catch (IOException e) {
            System.err.println("Le chemin suivant est invalide : " + chemin);
        }
        System.out.println("Votre fichier CSV a été généré à l'adresse suivante : "+chemin) ;
	}
	
	
	/**
	 * Méthode affichant le bilan d'une agglomération suite à l'exécution d'un algorithme sur cette dernière
	 * @param agg Agglomération quelconque 
	 * @param algo int correspondant à un algorithme de la liste algos
	 * @see algos
	 * @throws InputMismatchException dans le cas où l'algorithme spécifié n'existerait pas dans algos.
	 */
	public static void affichageBilanAlgo(Agglomeration agg, int algo) throws InputMismatchException {
		System.out.println("\n --> Bilan au sortir de "+algos[algo]+" :\n");
		agg.afficheBilan() ;
		System.out.println("\n-----------------------------") ;
	}
}
	/*
	//Main de test
	public static void main(String[] args) {
		resources.ChargeurProprietes.chargerProprietes(false);
		
		Agglomeration agg1 = new Agglomeration(22) ;
		try {
			agg1.ajouterRoute("A", "B", "C", "E") ;
			agg1.ajouterRoute("B", "D") ;
			agg1.ajouterRoute("C", "D", "E") ;
			agg1.ajouterRoute("D", "E", "F", "G") ;
			agg1.ajouterRoute("E", "I") ;
			agg1.ajouterRoute("F", "J") ;
			agg1.ajouterRoute("G", "I") ;
			agg1.ajouterRoute("H", "I", "M") ;
			agg1.ajouterRoute("I", "M", "N") ;
			agg1.ajouterRoute("J", "N", "P", "O") ;
			agg1.ajouterRoute("K", "L", "O") ;
			agg1.ajouterRoute("L", "O", "Q") ;
			agg1.ajouterRoute("M", "R") ;
			agg1.ajouterRoute("N", "R") ;
			agg1.ajouterRoute("O", "P", "Q") ;
			agg1.ajouterRoute("P", "S") ;
			agg1.ajouterRoute("Q", "S") ;
			agg1.ajouterRoute("R", "S", "T", "U") ;
			agg1.ajouterRoute("S", "U") ;
			agg1.ajouterRoute("T", "V") ;
			agg1.ajouterRoute("U","V") ;
		} catch(Exception e){}
		
		Agglomeration agg2 = new Agglomeration(12) ;
		try {
			agg2.ajouterRoute("A", "C") ;
			agg2.ajouterRoute("B", "C") ;
			agg2.ajouterRoute("C", "F") ;
			agg2.ajouterRoute("D", "E") ;
			agg2.ajouterRoute("E", "F", "G") ;
			agg2.ajouterRoute("F", "G", "H") ;
			agg2.ajouterRoute("G") ;
			agg2.ajouterRoute("H", "J", "K") ;
			agg2.ajouterRoute("I", "J") ;
			agg2.ajouterRoute("J") ;
			agg2.ajouterRoute("K") ;
		} catch(Exception e){}
		
		Agglomeration agg3 = new Agglomeration(5) ;
		try {
			agg3.ajouterRoute("A", "B", "C", "D") ;
			agg3.ajouterRoute("B", "C", "E") ;
			agg3.ajouterRoute("C", "D") ;
			agg3.ajouterRoute("D", "E") ;
		} catch(Exception e){}
	
		//agg1.afficheBilan();
		//agg.ajouterRoute("V") ;
		//agg.ajouterRoute(V) ;
		//System.out.println("Entrée dans le Tester : ") 
		
		//Scanner sc = new Scanner(System.in) ;
		//compareAlgorithmes(agg3) ;
		//compareAlgorithmes(agg1) ;
		
		//Agglomeration agg = GenerateurAgglomeration.randomAggloConnexeGenerateur(2) ;
		//agg.afficheBilan();
		
		//System.out.println(getTestSurAlgo(agg1, 2, true)) ;
		//System.out.println(Testeur.getTestAlgoSurAgglomerationAleatoire(45, 2)) ;
		
		//getTestAlgoSurAgglomerationAleatoire(4, 3) ;
		
		//System.out.println(resolutionAgglomerationAvecBascule(GenerateurAgglomeration.randomAggloConnexeGenerateur(10))) ;
		
		//String chemin = "./src/resources/rapports.csv" ;
		//ArrayList<Rapport> testsAleatoiresSurAlgos = getTestsAlgosSurAgglomerationAleatoire(2, 400) ;

		//ecrireRapportsDansCSV(testsAleatoiresSurAlgos, chemin) ;
        

		System.out.println("\n-----------------------------------------------\n\n\n\t\t****** Tests sur algorithmeFilePriorite avec agglomérations connues ******\n\n") ;
		ArrayList<RapportTest> testsSurAlgoAvecAggloDefinie = getTestSurAlgo(2, agg1, agg2, agg3) ;
		System.out.println("\n-----------------------------------------------\n\nVotre fichier CSV :\n\n"+RapportTest.enteteCSV()) ;
		for(RapportTest t : testsSurAlgoAvecAggloDefinie) System.out.println(t.formatCSV()) ;

		//System.out.println(Tester.testComplexiteTousAlgos(500).toString()) ;
		//Tester.testComplexiteTousAlgos(25) ;
*/
