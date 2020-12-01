package outils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import constructionEcoles.Ville;

public class ListeAdjacence extends HashMap<String, ArrayList<String>> {
	
	private static final long serialVersionUID = 1L;

	protected ListeAdjacence(List<Ville> villes) {
		super(villes.size()) ;
		for(Ville v : villes) {
			ArrayList<String> voisins = new ArrayList<String>(v.getVoisins().size()) ;
			for(Ville voisin : v.getVoisins()) voisins.add(voisin.getKey()) ;
			put(v.getKey(), voisins) ;
		}
	}
		
	protected ListeAdjacence(ListeAdjacence la) {
		super(la.size()) ;
		Iterator<Map.Entry<String, ArrayList<String>>> it = la.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<String, ArrayList<String>> pair = it.next() ;
			ArrayList<String> voisins = new ArrayList<String>(pair.getValue().size()) ;
			for(String s : pair.getValue()) voisins.add(s) ; //shallow copying
			put(pair.getKey(), voisins) ;
		}
	}	
	
	protected void removeVilleEtVoisins(String key) {
		ArrayList<String> voisinsVilleARemove = new ArrayList<String>(0) ; //shallow copy
		for(String voisin : this.get(key)) {
			//System.out.println(voisin) ;
			voisinsVilleARemove.add(voisin) ;
		} //on crée une shallow copy de la ville qu'on veut enlever de la liste d'adjacence, avec ses voisins
		voisinsVilleARemove.add(key) ;
		
		for(ArrayList<String> entreeListeAdjacence : this.values()) {
			for(String voisin : voisinsVilleARemove) entreeListeAdjacence.remove(voisin) ;
			entreeListeAdjacence.remove(key) ;
		} //pour chacune des entrées de la liste d'adjacence, on enlève de leurs valeurs tous les éléments contenus dans entreeListeAdjacence
		
		for(String voisin : voisinsVilleARemove) {
			this.remove(voisin) ;
		} //on enlève les éléments de la liste voisinsVilleARemove de la liste d'adjacence
	}
	
	protected void degreZero(LinkedList<String> file) {
		Iterator<Map.Entry<String, ArrayList<String>>> it = this.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<String, ArrayList<String>> pair = it.next() ;
			if(pair.getValue().isEmpty()) file.add(pair.getKey()) ;
		}
	}

	protected void voisinsDegreUn(LinkedList<String> file) {
		Iterator<Map.Entry<String, ArrayList<String>>> it = this.entrySet().iterator();
		//System.out.print("Voisins de degré 1 : ");
		while(it.hasNext()) {
			Map.Entry<String, ArrayList<String>> pair = it.next() ;
			if(pair.getValue().size() == 1 && (!file.contains(pair.getValue().get(0)))) {
				file.add(pair.getValue().get(0)) ;
				//System.out.print(pair.getValue().get(0)+ " ");
			}
		}										// la deuxième condition permet d'éviter les doublons
		//System.out.print("\n");
	}

	protected String plusHautDegre() {
		int max = 0 ;
		String maxC = null;
		Iterator<Map.Entry<String, ArrayList<String>>> it = this.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<String, ArrayList<String>> pair = it.next() ;
			if(pair.getValue().size() > max) {
				max = pair.getValue().size() ;
				maxC = pair.getKey(); 
			}
		}
		return maxC ;
	}
	
	protected ArrayList<String> plusHautsDegres() {
		ArrayList<String> al = new ArrayList<String>(0) ;
		int max = this.get(plusHautDegre()).size() ;
		
		Iterator<Map.Entry<String, ArrayList<String>>> it = this.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<String, ArrayList<String>> pair = it.next() ;
			if(pair.getValue().size() == max) al.add(pair.getKey()) ;
		}
		return al ;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder() ;
		sb.append("Liste d'adjacence : "+this.size()+" villes\n") ;
		Iterator<Map.Entry<String, ArrayList<String>>> it = this.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<String, ArrayList<String>> pair = it.next() ;
			sb.append(pair.getKey()+" :") ;
			for(String v : pair.getValue()) sb.append(" "+v) ;
			sb.append("\n") ;
		}	
		return sb.toString();
	}
}