package outils;

import constructionEcoles.Agglomeration;

public class ExecutableTestsAlgos {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
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
		//System.out.println("Entrée dans le Tester : ") ;
		//System.out.println(Tester.testComplexiteAlgo(300, 3).toString()) ;
		System.out.println(Tester.testComplexiteTousAlgos(500).toString()) ;
		//Tester.testComplexiteTousAlgos(25) ;
	}
}
