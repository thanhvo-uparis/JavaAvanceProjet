package outils;
import java.util.ArrayList;

import constructionEcoles.Agglomeration;
import constructionEcoles.Ville; 

public class Tester {
	// cette classe va contenir les méthodes qui permettront de tester les algos. Il faut y mettre le contenu de la classe Test
	
	private static final int k = 100 ;	// il s'agit du k de l'énoncé
	private static final int nbOperations = 0 ; // pour générer des écoles aléatoirements
	private static final boolean affichageExtendedLogs = false ; //affichage ou non de tous les logs
	private static final boolean affichageExceptions = false ; 

	
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
			Algos.algorithmeParSoustraction(agg, false) ;
			break;
		}
	}
	
	private static Agglomeration randomOperationsSurAgglo(Agglomeration agg, int nbOperations) {
						
		System.out.println("L'agglomeration est connexe.") ;
		
		//On lance nbOperations methodes de test sur l'agglo connexe tout juste generee.
		for(int i = 0 ; i < nbOperations ; i++) {
			try {
				String c = agg.getVilles().get((int) Math.round(Math.random()*(agg.getVilles().size()-1))).getKey() ;
				System.out.println(c) ;
				//Thread.sleep(1000);
				if(affichageExtendedLogs) System.out.println("\nChar : "+c) ;
				Ville v = agg.getVille(c) ;
				if(affichageExtendedLogs) System.out.println("Ville : " +v.toString()) ;
				if(affichageExtendedLogs) System.out.println(i%2); 
				if(i%2 == 0) agg.retirerEcole(v); //quand i est pair, on retire une ecole
				if(i%2 == 1) agg.ajouterEcole(v); //quand i est impair, on ajoute une ecole
			} catch(Exception e) {
				if(affichageExceptions) System.out.println(e) ;
			}
			if(affichageExtendedLogs) {
				System.out.println(agg.toString()) ;
				agg.afficheVilleAEcole();
			}
		}
		if(affichageExtendedLogs) {
			agg.afficheRoutes() ;
			System.out.println("Contrainte accessibilite respectee ? "+(agg.respecteAccessibilite()?"Oui.":"Non.")) ;
			System.out.println("Fin du test.");
		}
		
		return agg ;
	}
	
	private static Agglomeration randomAggloConnexeGenerateur(int nbVilles) {
		return randomAggloConnexeGenerateur(nbVilles, nbOperations) ;
	}
	
	private static Agglomeration randomAggloConnexeGenerateur(int nbVilles, int nbOperations) {
		Agglomeration agg = new Agglomeration(nbVilles) ;
		agg.afficheBilan();
		/*
		 * try { Thread.sleep(5000); } catch (InterruptedException e1) { // TODO
		 * Auto-generated catch block e1.printStackTrace(); }
		 */
		
		
		if(affichageExtendedLogs) System.out.println("Creation d'une agglomeration de "+nbVilles+" villes : " + agg.toString());
		
		String a ;
		String b ;
		
		// Ajout de routes aleatoires jusqu'a ce que l'agglomeration soit connexe
		do {
			try {
				a = agg.getVilles().get((int) Math.round(Math.random()*(agg.getVilles().size()-1))).getKey() ; //extrait un caractere aleatoire de s puis extrait la ville correspondante a ce caractere dans agg
				b = agg.getVilles().get((int) Math.round(Math.random()*(agg.getVilles().size()-1))).getKey() ;
				if(affichageExtendedLogs){
					System.out.println("Ville 1 a relier : " +a) ;
					System.out.println("Ville 2 a relier : " +b) ;
				}
				agg.ajouterRoute(a,b) ; 	
				if(affichageExtendedLogs) {
					System.out.println("Villes apres execution de ajouterRoute()") ;
					System.out.println(agg.getVille(a).toString()) ;
					System.out.println(agg.getVille(b).toString()) ;
				}
			} catch(Exception e) {
				if(affichageExceptions) System.out.println(e) ;
			}
			if(affichageExtendedLogs) agg.afficheRoutes() ;
		} while(!agg.estConnexe()) ;
		
		if(nbOperations > 0) agg = randomOperationsSurAgglo(agg, nbOperations) ;
		
		return agg ;
	}
	
	//pour tester la complexité d'un algo en particulier
	// voir https://www.techiedelight.com/measure-elapsed-time-execution-time-java/
	public static ArrayList<Test> testComplexiteTousAlgos(int nbVilles) {
		
		ArrayList<Test> tests = new ArrayList<Test>() ;
				
		for(int i = 0 ; i < algos.length ; i++) tests.add(testComplexiteAlgo(nbVilles, i)) ;
		return tests ;
	}

	public static Test testComplexiteAlgo(int nbVilles, int algo) {
		return  testComplexiteAlgo(nbVilles, algo, null) ;
	}
	
	public static Test testComplexiteAlgo(int nbVilles, int algo, Agglomeration agg) {

		Test t = new Test() ;

		if(agg == null) {
			agg = randomAggloConnexeGenerateur(nbVilles) ;
			System.out.println("Agglomération générée : ") ;
			agg.afficheBilan() ;
		}
		//agg.afficheVilleAEcole();
		long startTime = System.nanoTime();
					
		switchAlgo(algo, agg) ;
		long endTime = System.nanoTime();
		// get difference of two nanoTime values
		long timeElapsed = endTime - startTime;
		System.out.println("Temps d'exécution en nanosecondes :  " + timeElapsed);
		System.out.println("Temps d'exécution en millisecondes : " + timeElapsed / 1000000);
		
		t.put(nbVilles, new BilanIterationAlgo(agg.nbEcoles(), timeElapsed)) ;

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
		
	@Deprecated
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
