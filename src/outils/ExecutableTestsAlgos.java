package outils;

import constructionEcoles.Agglomeration;

public class ExecutableTestsAlgos {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Agglomeration agg = new Agglomeration(22) ;
		try {
			agg.ajouterRoute("A", "B", "C", "E") ;
			agg.ajouterRoute("B", "D") ;
			agg.ajouterRoute("C", "D", "E") ;
			agg.ajouterRoute("D", "E", "F", "G") ;
			agg.ajouterRoute("E", "I") ;
			agg.ajouterRoute("F", "J") ;
			agg.ajouterRoute("G", "I") ;
			agg.ajouterRoute("H", "I", "M") ;
			agg.ajouterRoute("I", "M", "N") ;
			agg.ajouterRoute("J", "N", "P", "O") ;
			agg.ajouterRoute("K", "L", "O") ;
			agg.ajouterRoute("L", "O", "Q") ;
			agg.ajouterRoute("M", "R") ;
			agg.ajouterRoute("N", "R") ;
			agg.ajouterRoute("O", "P", "Q") ;
			agg.ajouterRoute("P", "S") ;
			agg.ajouterRoute("Q", "S") ;
			agg.ajouterRoute("R", "S", "T", "U") ;
			agg.ajouterRoute("S", "U") ;
			agg.ajouterRoute("T", "V") ;
			agg.ajouterRoute("U","V") ;
		} catch(Exception e){
			
		}
		agg.afficheBilan();
		//agg.ajouterRoute("V") ;
		//agg.ajouterRoute(V) ;
		System.out.println("Entrée dans le Tester : ") ;
		System.out.println(Tester.testComplexiteAlgo(26, 3, agg).toString()) ;
		//System.out.println(Tester.testComplexiteTousAlgos(10).toString()) ;
		//Tester.testComplexiteTousAlgos(25) ;
	}
}
