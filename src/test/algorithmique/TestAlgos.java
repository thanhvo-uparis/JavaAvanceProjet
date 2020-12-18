package test.algorithmique;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;

import main.algorithmique.Algos;
import main.entites.Agglomeration;

public class TestAlgos {

	private static Agglomeration agg1 ;
	private static Agglomeration agg2 ;
	private static Agglomeration agg3 ;
	
	@Before
	public void init() {
		
		agg1 = new Agglomeration(22) ;
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
		} catch(Exception e){ System.err.println(e) ; }
		
		agg2 = new Agglomeration(12) ;
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
		} catch(Exception e){ System.err.println(e) ; }
		
		agg3 = new Agglomeration(5) ;
		try {
			agg3.ajouterRoute("A", "B", "C", "D") ;
			agg3.ajouterRoute("B", "C", "E") ;
			agg3.ajouterRoute("C", "D") ;
			agg3.ajouterRoute("D", "E") ;
		} catch(Exception e){ System.err.println(e) ; }
	}
	
	@Test
	public void testScoreAlgoFilePrioAgg1() {
		Algos.algorithmeFilePriorite(agg1, false) ;
		assertEquals(6, agg1.getNbEcoles()) ;
	}
	
	@Test
	public void testScoreAlgoFilePrioAgg2() {
		Algos.algorithmeFilePriorite(agg2, false) ;
		assertEquals(5, agg2.getNbEcoles()) ;
	}
	
	@Test
	public void testScoreAlgoFilePrioAgg3() {
		Algos.algorithmeFilePriorite(agg3, false) ;
		assertEquals(2, agg3.getNbEcoles()) ;
	}
	
	@Test
	public void testScoreAlgoParSoustraction1() {
		Algos.algorithmeParSoustraction(agg1, true, true) ;
		assertEquals(6, agg1.getNbEcoles()) ;
	}
	
	@Test
	public void testScoreAlgoParSoustraction2() {
		Algos.algorithmeParSoustraction(agg2, true, true) ;
		assertEquals(5, agg2.getNbEcoles()) ;
	}	
	
	@Test
	public void testScoreAlgoParSoustraction3() {
		Algos.algorithmeParSoustraction(agg3, true, true) ;
		assertEquals(2, agg3.getNbEcoles()) ;
	}
	
	/* Test à implémenter : 
	 * 		//agg1.afficheBilan();
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
}
