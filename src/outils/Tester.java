package outils;
import java.util.ArrayList;

import constructionEcoles.Agglomeration;
import constructionEcoles.Ville; 

public class Tester {
	// cette classe va contenir les méthodes qui permettront de tester les algos. Il faut y mettre le contenu de la classe Test
	
	private static final int k = 100 ;	// il s'agit du k de l'énoncé
	
	// liste des noms des méthodes, si cette liste est modifiée, bien penser à modifier la méthode switchAlgo en conséquence.
	private static final String [] algos = { "algorithmeApproximationNaif", 
											 "algorithmeApproximationUnPeuMoinsNaif", 
											 "algorithmeFilePriorite",
											 "algorithmeParSoustraction" } ;
	
	private static void switchAlgo(int algo, Agglomeration agg) {
		switch(algo) {
		case 0 :
			Algos.algorithmeApproximationNaif(k, agg) ;
			break;
		case 1 :
			Algos.algorithmeApproximationUnPeuMoinsNaif(k, agg) ;
			break;
		case 2 :
			Algos.algorithmeFilePriorite(agg, false) ;
			break ;
		case 3 :
			Algos.algorithmeParSoustraction(agg) ;
			break;
		}
	}
	
	private static Agglomeration randomOperationsSurAgglo(Agglomeration agg, int nbOperations) {
				
		String s = alphabetGen(agg.getVilles().size()) ;
		
		System.out.println("L'agglomeration est connexe.") ;
		
		//On lance nbOperations methodes de test sur l'agglo connexe tout juste generee.
		for(int i = 0 ; i < nbOperations ; i++) {
			try {
				char c ;
				c = s.charAt((int) Math.round(Math.random()*agg.getVilles().size())) ; //extrait un caractere aleatoire du string s
				System.out.println("\nChar : "+c) ;
				Ville v = agg.getVille(c) ;
				System.out.println("Ville : " +v.toString()) ;
				System.out.println(i%2); 
				if(i%2 == 0) agg.retirerEcole(v); //quand i est pair, on retire une ecole
				if(i%2 == 1) agg.ajouterEcole(v); //quand i est impair, on ajoute une ecole
			} catch(Exception e) {
				System.out.println(e) ;
			}
			System.out.println(agg.toString()) ;
			agg.afficheVilleAEcole();
		}
		System.out.println(agg.afficherRoutes()) ;
		System.out.println("Contrainte accessibilite respectee ? "+(agg.respecteAccessibilite()?"Oui.":"Non.")) ;
		System.out.println("Fin du test.");
		
		return agg ;
	}
	
	private static Agglomeration randomAggloConnexeGenerateur(int nbVilles) {
		return randomAggloConnexeGenerateur(nbVilles, 0) ;
	}
	
	private static Agglomeration randomAggloConnexeGenerateur(int nbVilles, int nbOperations) {
		Agglomeration agg = new Agglomeration(nbVilles) ;
				
		System.out.println("Creation d'une agglomeration de "+nbVilles+" villes : " + agg.toString());
		
		char a ;
		char b ;
		String s = alphabetGen(nbVilles) ;
		
		// Ajout de routes aleatoires jusqu'a ce que l'agglomeration soit connexe
		do {
			try {
				a = s.charAt((int) Math.round(Math.random()*nbVilles)) ; //extrait un caractere aleatoire de s puis extrait la ville correspondante a ce caractere dans agg
				b = s.charAt((int) Math.round(Math.random()*nbVilles)) ;
				System.out.println("Ville 1 a relier : " +a) ;
				System.out.println("Ville 2 a relier : " +b) ;
				agg.ajouterRoute(a,b) ; 	
				System.out.println("Villes apres execution de ajouterRoute()") ;
				System.out.println(agg.getVille(a).toString()) ;
				System.out.println(agg.getVille(b).toString()) ;
			} catch(Exception e) {
				System.out.println(e) ;
			}
			System.out.println(agg.afficherRoutes()) ;
		} while(!agg.estConnexe()) ;
		
		if(nbOperations > 0) agg = randomOperationsSurAgglo(agg, nbOperations) ;
		
		return agg ;
	}
	
	//pour tester la complexité d'un algo en particulier
	// voir https://www.techiedelight.com/measure-elapsed-time-execution-time-java/
	private static ArrayList<Test> testComplexiteTousAlgos(int nbVillesMax) {
		
		ArrayList<Test> tests = new ArrayList<Test>() ;
				
		for(int i = 0 ; i < algos.length ; i++) tests.add(testComplexiteAlgo(nbVillesMax, i)) ;
		return tests ;
	}

	private static Test testComplexiteAlgo(int nbVillesMax, int algo) {

		Test t = new Test() ;
		
		for(int j = 2 ; j < nbVillesMax ; j++) {
			Agglomeration agg = randomAggloConnexeGenerateur(j) ;
			long startTime = System.nanoTime();
						
			switchAlgo(algo, agg) ;
	
			long endTime = System.nanoTime();
	
			// get difference of two nanoTime values
			long timeElapsed = endTime - startTime;
			System.out.println("Execution time in nanoseconds  : " + timeElapsed);
			System.out.println("Execution time in milliseconds : " + timeElapsed / 1000000);
			
			t.put(j, new BilanIterationAlgo(agg.nbEcoles(), timeElapsed / 1000000)) ;

		}
		return t ;
	}
	
	private static Test testComplexiteAlgo(Agglomeration agg, int algo) {

		Test t = new Test() ;
		
		long startTime = System.nanoTime();
			
		switchAlgo(algo, agg) ;
		
		long endTime = System.nanoTime();
		// get difference of two nanoTime values
		long timeElapsed = endTime - startTime;
		System.out.println("Execution time in nanoseconds  : " + timeElapsed);
		System.out.println("Execution time in milliseconds : " + timeElapsed / 1000000);
		
		return t ;
	}
		
	private static String alphabetGen(int n) {
		StringBuilder sb = new StringBuilder() ;
		for(char c = 'a'; c < 'a'+n; c++) sb.append(c) ; //le "+1" sert a ajouter une lettre qui ne sera pas dans les villes, pour forcer les erreurs
		return sb.toString() ;
	}
	
	public static void compareAlgorithmes(int nbVillesMax) {
		ArrayList<Test> tests = testComplexiteTousAlgos(nbVillesMax) ;

		int c = 0 ;
		for(int i = 2 ; i < nbVillesMax ; i++) {
			System.out.println("\nnbVille = "+i+"+ : ");
			for(Test t : tests) {
				System.out.println("Algo "+algos[i]+" : "+t.toString(i)) ;
				c++ ;
			}
		}
		
		c = 0 ;
		for(Test t : tests) {
			System.out.println("Algo "+c+" : getEcolePerVilles() = "+t.getEcolePerVilles()+", getAvgTime() = "+t.getAvgTime()) ;
			c++ ;
		}
	}
	
	public static void compareAlgorithmes(Agglomeration agg) {
		for(int i = 0 ; i < algos.length ; i++) {
			Test t = testComplexiteAlgo(agg, i) ;
			System.out.println("Algo "+algos[i]+" : "+t.toString(i)) ;
		}
	}
}
