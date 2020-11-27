package outils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import constructionEcoles.Ville;

public class ListeAdjacence extends HashMap<Character, ArrayList<Character>> {
	
	private static final long serialVersionUID = 1L;

	protected ListeAdjacence(List<Ville> villes) {
		super(villes.size()) ;
		for(Ville v : villes) {
			ArrayList<Character> voisins = new ArrayList<Character>(v.getVoisins().size()) ;
			for(Ville voisin : v.getVoisins()) voisins.add(voisin.getKey()) ;
			put(v.getKey(), voisins) ;
		}
	}
	
	protected void removeVilleEtVoisins(char key) {
		for(ArrayList<Character> ville : this.values()) {
			ville.remove(key) ;
			for(Character voisin : this.get(key)) ville.remove(voisin) ;
		}
		this.remove(key) ;
	}
	
	protected void degreZero(LinkedList<Character> file) {
		Iterator<Map.Entry<Character, ArrayList<Character>>> it = this.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<Character, ArrayList<Character>> pair = it.next() ;
			if(pair.getValue().isEmpty()) file.add(pair.getKey()) ;
		}
	}

	protected void voisinsDegreUn(LinkedList<Character> file) {
		Iterator<Map.Entry<Character, ArrayList<Character>>> it = this.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<Character, ArrayList<Character>> pair = it.next() ;
			if(pair.getValue().size() == 1 && (!file.contains(pair.getValue().get(0)))) file.add(pair.getValue().get(0)) ;
		}										// la deuxième condition permet d'éviter les doublons
	}

	protected Character plusHautDegre() {
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
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder() ;
		sb.append("Liste d'adjacence : "+this.size()+" villes\n") ;
		Iterator<Map.Entry<Character, ArrayList<Character>>> it = this.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<Character, ArrayList<Character>> pair = it.next() ;
			sb.append(pair.getKey()+" : ") ;
			for(Character v : pair.getValue()) sb.append(" "+v) ;
			sb.append("\n") ;
		}	
		return sb.toString();
	}
}