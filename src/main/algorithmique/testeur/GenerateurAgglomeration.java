package main.algorithmique.testeur;

import main.entites.Agglomeration;
import main.entites.Ville;
import resources.ChargeurProprietes;


/**
 * Classe statique permettant de générer de différentes manières des agglomérations aléatoires. 
 * @author Yann Trividic
 * @version 1.0
 */
public class GenerateurAgglomeration {
	
	private static final int nbOperations = 0 ; // pour générer des écoles aléatoirements
	
	protected static Agglomeration randomOperationsSurAgglo(Agglomeration agg, int nbOperations) {
		System.out.println("L'agglomeration est connexe.") ;
		
		//On lance nbOperations methodes de test sur l'agglo connexe tout juste generee.
		for(int i = 0 ; i < nbOperations ; i++) {
			try {
				String c = agg.getVilles().get((int) Math.round(Math.random()*(agg.getVilles().size()-1))).getKey() ;
				System.out.println(c) ;
				//Thread.sleep(1000);
				if(ChargeurProprietes.getPropriete("affichageLogs")) System.out.println("\nChar : "+c) ;
				Ville v = agg.getVille(c) ;
				if(ChargeurProprietes.getPropriete("affichageLogs")) System.out.println("Ville : " +v.toString()) ;
				if(ChargeurProprietes.getPropriete("affichageLogs")) System.out.println(i%2); 
				if(i%2 == 0) agg.retirerEcole(v); //quand i est pair, on retire une ecole
				if(i%2 == 1) agg.ajouterEcole(v); //quand i est impair, on ajoute une ecole
			} catch(Exception e) {
				if(ChargeurProprietes.getPropriete("affichageExceptions")) System.out.println(e) ;
			}
			if(ChargeurProprietes.getPropriete("affichageLogs")) {
				System.out.println(agg.toString()) ;
				agg.afficheVilleAEcole();
			}
		}
		if(ChargeurProprietes.getPropriete("affichageLogs")) {
			agg.afficheRoutes() ;
			System.out.println("Contrainte accessibilite respectee ? "+(agg.respecteAccessibilite()?"Oui.":"Non.")) ;
			System.out.println("Fin du test.");
		}
		return agg ;
	}
	
	protected static Agglomeration randomAggloConnexeGenerateur(int nbVilles) {
		return randomAggloConnexeGenerateur(nbVilles, nbOperations) ;
	}
	
	protected static Agglomeration randomAggloConnexeGenerateur(int nbVilles, int nbOperations) {
		Agglomeration agg = new Agglomeration(nbVilles) ;
		if(ChargeurProprietes.getPropriete("affichageLogs")) System.out.println("Creation d'une agglomeration de "+nbVilles+" villes : " + agg.toString());
		
		String a ;
		String b ;
		
		// Ajout de routes aleatoires jusqu'a ce que l'agglomeration soit connexe
		do {
			try {
				a = agg.getVilles().get((int) Math.round(Math.random()*(agg.getVilles().size()-1))).getKey() ; //extrait un caractere aleatoire de s puis extrait la ville correspondante a ce caractere dans agg
				b = agg.getVilles().get((int) Math.round(Math.random()*(agg.getVilles().size()-1))).getKey() ;
				if(ChargeurProprietes.getPropriete("affichageLogs")){
					System.out.println("Ville 1 a relier : " +a) ;
					System.out.println("Ville 2 a relier : " +b) ;
				}
				agg.ajouterRoute(a,b) ; 	
				if(ChargeurProprietes.getPropriete("affichageLogs")) {
					System.out.println("Villes apres execution de ajouterRoute()") ;
					System.out.println(agg.getVille(a).toString()) ;
					System.out.println(agg.getVille(b).toString()) ;
				}
			} catch(Exception e) {
				if(ChargeurProprietes.getPropriete("affichageExceptions")) System.out.println(e) ;
			}
			if(ChargeurProprietes.getPropriete("affichageLogs")) agg.afficheRoutes() ;
		} while(!agg.estConnexe()) ;
		
		if(nbOperations > 0) agg = randomOperationsSurAgglo(agg, nbOperations) ;
		
		return agg ;
	}	
	
	@SuppressWarnings("unused")
	private static String alphabetGen(int n) {
		StringBuilder sb = new StringBuilder() ;
		for(char c = 'a'; c < 'a'+n; c++) sb.append(c) ; //le "+1" sert a ajouter une lettre qui ne sera pas dans les villes, pour forcer les erreurs
		return sb.toString() ;
	}
}
