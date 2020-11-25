package outils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import constructionEcoles.Ville;

public class ListeAdjacence extends HashMap<Character, ArrayList<Character>> {
	
	private static final long serialVersionUID = 1L;

	public ListeAdjacence(List<Ville> villes) {
		super(villes.size()) ;
		for(Ville v : villes) {
			ArrayList<Character> voisins = new ArrayList<Character>(v.getVoisins().size()) ;
			for(Ville voisin : v.getVoisins()) voisins.add(voisin.getKey()) ;
			put(v.getKey(), voisins) ;
		}
	}
	
	public void removeVille(char key) {
		this.remove(key);
		Iterator<Map.Entry<Character, ArrayList<Character>>> it = this.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<Character, ArrayList<Character>> pair = it.next() ;
			pair.getValue().remove(key) ;
		}
	}
	
	public boolean isEmpty() {
		if(this.size() == 0) return true ;
		return false ;
	}
	
	public Character degreZero() {
		Iterator<Map.Entry<Character, ArrayList<Character>>> it = this.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<Character, ArrayList<Character>> pair = it.next() ;
			if(pair.getValue().size() == 0) return pair.getKey() ;
		}
		return null ;
	}
	
	public Character[] degreUnEtVoisin() {
		Iterator<Map.Entry<Character, ArrayList<Character>>> it = this.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<Character, ArrayList<Character>> pair = it.next() ;
			if(pair.getValue().size() == 1) return new Character [] {pair.getKey(), pair.getValue().get(0)} ;
		}
		return null ;
	}
	
	public Character plusHautDegre() {
		int max = 0 ;
		Character maxC = null;
		Iterator<Map.Entry<Character, ArrayList<Character>>> it = this.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<Character, ArrayList<Character>> pair = it.next() ;
			if(pair.getValue().size() > max) {
				max = pair.getValue().size() ;
				maxC = pair.getKey(); 
			}
		}
		return maxC ;
	}
}
