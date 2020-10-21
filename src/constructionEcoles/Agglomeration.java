package constructionEcoles;
import java.util.ArrayList; 
import java.util.LinkedList;

public class Agglomeration {
	private ArrayList<Ville> villes;
	
	public Agglomeration() {
		this.villes = new ArrayList<Ville>() ;
	}
	
	public Agglomeration(Ville...villes) {
		this.villes = new ArrayList<Ville>() ;		
		for (Ville a : villes) villes.add(a) ;
	}
	
	public Agglomeration(int nb_villes) throws Exception {
		if(nb_villes < 0 || nb_villes > 26) throw new Exception("Nombre de villes invalide.");
		this.villes = new ArrayList<Ville>(nb_villes);
	}
	
	public String placerEcoles() {
		StringBuilder sb = new StringBuilder() ;
		//[...] 
		return sb.toString() ;
	}
	
	public void ajouterRoute(Ville a, Ville b) throws Exception {
		if(a.voisins.contains(b)) throw new Exception("Les deux villes sont déjà reliées");
		a.voisins.add(b) ;
		b.voisins.add(a) ;
	}
	
	public void ajouterEcole(Ville a) throws Exception {
		if(a.getHasEcole()) throw new Exception("La ville a déjà une école.");
		if(a.hasEcoleVoisins()) throw new Exception("La ville est déjà proche d'une école.");
		a.setHasEcole(true);
	}
	
	public void retirerEcole(Ville a) throws Exception {
		if(!a.getHasEcole()) throw new Exception("La ville n'a déjà pas d'école.");
		if(!a.hasEcoleVoisins()) throw new Exception("La ville ne sera plus procheune école.");
		a.setHasEcole(false);
	}
	
	public void clearEcole() {
		for(Ville a : villes) a.setHasEcole(false);
	}
	
	public boolean estConnexe() {
		LinkedList<Ville> file = new LinkedList<Ville>() ;
		ArrayList<Character> marques = new ArrayList<Character>(0);
		
		file.add(villes.get(0)) ;
		marques.add(villes.get(0).getKey()) ;
		
		while(!file.isEmpty()) {
			Ville v = file.pollFirst() ;
			for(Ville a : v.voisins) {
				if(!marques.contains(a.getKey())) {
					marques.add(a.getKey()) ;
					file.offer(a) ;
				}
			}
		}
		
		if(marques.size() != villes.size()) return false ;
		return true ;
	}
	
	public String afficheVilleAEcole() {
		StringBuilder sb = new StringBuilder() ;
		for(Ville a : villes) {
			if(a.hasEcole()) sb.add(a.getKey()+" ") ;
		}
		return sb.toString() ;
	}
	
	public int nbEcoles() {
		int c = 0 ;
		for(Ville a : villes) {
			if(a.hasEcole()) c++ ;
		}
		return c ;
	}
	
	@Override
	public String toString() {
		return("Agglomération de "+villes.size()+" villes et "+nbEcoles()+" écoles.") ;
	}
}
