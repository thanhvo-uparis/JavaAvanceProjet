package constructionEcoles ;
public class Test {
	public static void main(String[] args) {
		
		int nbLettres = 10 ; //doit être compris entre 1 et 25 pour mener à bien les tests
		int nbRetraitsAjouts = 150 ; //nombre d'opérations à effectuer sur l'agglomération
		
		Agglomeration agg = new Agglomeration(nbLettres) ; //initialisation de l'agglo
		
		System.out.println("Création d'une agglomeration de "+nbLettres+" villes : " + agg.toString());
		
		char a ;
		char b ;

		// Création d'un string dans lequel on viendra piocher aléatoirement une lettre
		StringBuilder sb = new StringBuilder() ;
		for(char c = 'a'; c < 'a'+nbLettres+1; c++) sb.append(c) ; //le "+1" sert à ajouter une lettre qui ne sera pas dans les villes, pour forcer les erreurs
		String s = sb.toString() ;
		
		// Ajout de routes aléatoires jusqu'à ce que l'agglomération soit connexe
		do {
			try {
				a = s.charAt((int) Math.round(Math.random()*nbLettres)) ; //extrait un caractère aléatoire de s puis extrait la ville correspondante à ce caractère dans agg
				b = s.charAt((int) Math.round(Math.random()*nbLettres)) ;
				System.out.println("Ville 1 à relier : " +a) ;
				System.out.println("Ville 2 à relier : " +b) ;
				agg.ajouterRoute(a,b) ; 	
				System.out.println("Villes après exécution de ajouterRoute()") ;
				System.out.println(agg.getVille(a).toString()) ;
				System.out.println(agg.getVille(b).toString()) ;
			} catch(Exception e) {
				System.out.println(e) ;
			}
			System.out.println(agg.afficherRoutes()) ;
		} while(!agg.estConnexe()) ;
		
		System.out.println("L'agglomération est connexe.") ;
		
		//On lance nbRetraitsAjouts méthodes de test sur l'agglo connexe tout juste générée.
		for(int i = 0 ; i < nbRetraitsAjouts ; i++) {
			try {
				char c ;
				c = s.charAt((int) Math.round(Math.random()*nbLettres)) ; //extrait un caractère aléatoire du string s
				System.out.println("\nChar : "+c) ;
				Ville v = agg.getVille(c) ;
				System.out.println("Ville : " +v.toString()) ;
				System.out.println(i%2); 
				if(i%2 == 0) agg.retirerEcole(v); //quand i est pair, on retire une école
				if(i%2 == 1) agg.ajouterEcole(v); //quand i est impair, on ajoute une école
			} catch(Exception e) {
				System.out.println(e) ;
			}
			System.out.println(agg.toString()) ;
			agg.afficheVilleAEcole();
		}
		System.out.println(agg.afficherRoutes()) ;
		System.out.println("Contrainte accessibilité respectée ? "+(agg.respecteAccessibilite()?"Oui.":"Non.")) ;
		System.out.println("Fin du test.");
	}
}