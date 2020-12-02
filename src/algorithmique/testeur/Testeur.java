package algorithmique.testeur;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.LinkedList;

import algorithmique.Algos;
import constructionEcoles.Agglomeration;

public class Testeur {
	
	//TODO vérifier qu'on ne peut pas créer d'Agglomération de taille inférieure à 2
	// cette classe va contenir les méthodes qui permettront de tester les algos. Il faut y mettre le contenu de la classe Test
	
	private static final Integer k = 100 ;	// il s'agit du k de l'énoncé
	private static final Boolean estDynamique = false ;
	
	// liste des noms des méthodes, si cette liste est modifiée, bien penser à modifier la méthode switchAlgo en conséquence.
	private static final String [] algos = { "algorithmeApproximationNaif", 
											 "algorithmeApproximationUnPeuMoinsNaif", 
											 "algorithmeFilePriorite",
											 "algorithmeParSoustraction" } ;
	
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
			Algos.algorithmeParSoustraction(agg, estDynamique) ;
			break;
		}
	}
	
	//pour tester la complexité d'un algo en particulier
	// voir https://www.techiedelight.com/measure-elapsed-time-execution-time-java/
	public static ArrayList<RapportTest> testComplexiteTousAlgos(Agglomeration agg) {
		ArrayList<RapportTest> tests = new ArrayList<RapportTest>(algos.length) ;
		for(int i = 0 ; i < algos.length ; i++) tests.add(getTestSurAlgo(agg, i)) ;
		return tests ;
	}
	
	public static RapportTest getTestAlgoSurAgglomerationAleatoire(int nbVilles, int algo) throws InputMismatchException {
		if(nbVilles < 2) throw new InputMismatchException("Le nombre de villes minimum pour une agglomération est 2.") ;
		System.out.println("Résultat de l'algorithme "+algos[algo]+" sur une agglomération aléatoire de "+nbVilles+" villes.");
		return getTestSurAlgo(GenerateurAgglomeration.randomAggloConnexeGenerateur(nbVilles), algo) ;
	}
	
	public static RapportTest getTestSurAlgo(Agglomeration agg, int algo) {
		long tempsDebut = System.nanoTime();
		switchAlgo(algo, agg) ;
		long tempsFin = System.nanoTime();
		
		double tempsEcoule = tempsFin - tempsDebut;
		//System.out.println("Temps d'exécution en nanosecondes :  " + tempsEcoule);
		//System.out.println("Temps d'exécution en millisecondes : " + tempsEcoule / 1000000);
		
		affichageBilanAlgo(agg, algo) ;
		return new RapportTest(algos[algo], (algo==0||algo==1)?k:null, (algo==3)?estDynamique:null, agg.getVilles().size(), agg.nbEcoles(), tempsEcoule/1000000) ;
	}
		
	
	public static ArrayList<RapportTest> compareAlgorithmes(Agglomeration agg) {
		
		System.out.println("\t **** Comparatif des résultats obtenus par nos algorithmes **** \n") ;
		ArrayList<RapportTest> tests = testComplexiteTousAlgos(agg) ;
		
		String nomAlgoMeilleurTemps = "";
		double meilleurTemps = Double.MAX_VALUE ;
		LinkedList<String> nomsAlgoMeilleursScore = new LinkedList<String>();
		double meilleurScore = Double.MAX_VALUE ;
		
		System.out.println("-----------------------------\n\nRapports :") ;
		for(RapportTest t : tests) {
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
	
	
	public static void affichageBilanAlgo(Agglomeration agg, int algo) {
		System.out.println("\n - Bilan au sortir de "+algos[algo]+"() :");
		agg.afficheBilan() ;
		System.out.println("\n-----------------------------") ;
	}
	
	//Main de test
	public static void main(String[] args) {
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
		Testeur.compareAlgorithmes(agg1) ;
		System.out.println(Testeur.getTestAlgoSurAgglomerationAleatoire(45, 2)) ;
		//System.out.println(Tester.testComplexiteTousAlgos(500).toString()) ;
		//Tester.testComplexiteTousAlgos(25) ;
	}
}
