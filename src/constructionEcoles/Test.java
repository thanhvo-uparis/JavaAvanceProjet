package constructionEcoles ;
public class Test {
	public static void main(String[] args) {
		
		Agglomeration agg = new Agglomeration(5) ;
		
		System.out.println("Création d'une agglomeration de 5 villes : " + agg.toString());
		
		Ville a ;
		Ville b ;

		String s = "abcdef" ;
		char c ;
		
		//ajout de 10 routes aléatoires 
		do {
			for(int i = 0 ; i < 10 ; i++) {
				try {
					a = agg.hasVille(s.charAt((int) Math.round(Math.random()*5))) ;
					b = agg.hasVille(s.charAt((int) Math.round(Math.random()*5))) ;
					System.out.println("Ville 1 à relier : " +a.toString()) ;
					System.out.println("Ville 2 à relier : " +b.toString()) ;
					agg.ajouterRoute(a,b) ;
				} catch(Exception e) {
					System.out.println(e) ;
				}
				System.out.println(agg.afficherRoutes()) ;
			}
		} while(!agg.estConnexe()) ;
		
		for(int i = 0 ; i < 10 ; i++) {
			try {
				c = s.charAt((int) Math.round(Math.random()*5)) ;
				System.out.println("Char : "+c) ;
				a = agg.hasVille(c) ;
				System.out.println("Ville : " +a.toString()) ;
				System.out.println(i%2); 
				if(i%2 == 0) agg.retirerEcole(a);
				if(i%2 == 1) agg.ajouterEcole(a);
			} catch(Exception e) {
				System.out.println(e) ;
			}
			try {
			} catch(Exception e) {
				System.out.println(e) ;
			}
			System.out.println(agg.toString()) ;
			System.out.println(agg.afficherRoutes()) ;
		}
		
		System.out.println("Fin du programme");
	}
}
