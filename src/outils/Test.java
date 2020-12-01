package outils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map ;


public class Test extends HashMap<Integer, BilanIterationAlgo> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public double getAvgTime() {
		double a = 0 ;
		int count = 0 ;
		Iterator<Map.Entry<Integer, BilanIterationAlgo>> it = this.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<Integer, BilanIterationAlgo> pair = it.next() ;
			a += pair.getValue().getTemps() ;
			count++ ;
		}
		return a/count ;
	}
	
	public double getEcolePerVilles() {
		double ecoles = 0 ;
		double villes = 0 ;
		Iterator<Map.Entry<Integer, BilanIterationAlgo>> it = this.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<Integer, BilanIterationAlgo> pair = it.next() ;
			
			ecoles += pair.getValue().getScore() ;
			villes += pair.getKey() ;
		}
		return ecoles/villes ;
	}
	
	public String toString(int key) {
		return this.get(key).toString() ;
	}
	
	public String toString() {
		return "EcolePerVilles = "+getEcolePerVilles()+", AvgTime = "+getAvgTime() ;
	}
}
