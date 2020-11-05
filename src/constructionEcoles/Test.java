package constructionEcoles ;

/**
 * La classe Test permet de tester les differents cas de figure pouvant se presenter a l'execution du programme. Elle permet de passer en argument le nombre de Ville constituant l'Agglomeration a tester, et le nombre d'operations "n" a effectuer sur celle-ci.
 * Le programme relie aleatoirement des Ville jusqu'a ce que l'agglomeration soit connexe.
 * Puis execute "n" operations sur ces villes (ajout d'ecoles, retraits d'ecoles).
 * Toutes les operations sont affichees pour permettre de comprendre l'execution du programme pas a pas.
 * @author Anthony Baptista
 * @author Yann Trividic
 * @version 1.0
 */

public class Test {
	
	/**
	 * @param args	Le premier argument est un int correspondant au nombre de villes que l'on veut generer. Le second est un int correspondant au nombre d'operations
	 * a effectuer sur l'agglomeration.
	 */
	public static void main(String[] args) {

		int nbVilles = Integer.parseInt(args[0]) ; //doit etre compris entre 1 et 25 pour mener a bien les tests
		int nbOperations = Integer.parseInt(args[1]) ; //nombre d'operations a effectuer sur l'agglomeration
		
		Agglomeration agg = new Agglomeration(nbVilles) ; //initialisation de l'agglo
		
		System.out.println("Creation d'une agglomeration de "+nbVilles+" villes : " + agg.toString());
		
		char a ;
		char b ;

		// Creation d'un string dans lequel on viendra piocher aleatoirement une lettre
		StringBuilder sb = new StringBuilder() ;
		for(char c = 'a'; c < 'a'+nbVilles+1; c++) sb.append(c) ; //le "+1" sert a ajouter une lettre qui ne sera pas dans les villes, pour forcer les erreurs
		String s = sb.toString() ;
		
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
		
		System.out.println("L'agglomeration est connexe.") ;
		
		//On lance nbOperations methodes de test sur l'agglo connexe tout juste generee.
		for(int i = 0 ; i < nbOperations ; i++) {
			try {
				char c ;
				c = s.charAt((int) Math.round(Math.random()*nbVilles)) ; //extrait un caractere aleatoire du string s
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
	}
}
