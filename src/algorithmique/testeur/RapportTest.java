package algorithmique.testeur;

import constructionEcoles.Agglomeration;

public class RapportTest {
	
	/**
	 * 
	 */
	private String nomAlgo ;
	private Integer k ;
	private Boolean dynamique ;
	
	private int nbVilles, nbEcoles ;
	private boolean contrainteAccessibilite ;
	
	private double temps ;
	
	protected RapportTest(String nomAlgo, Integer k, Boolean dynamique, Agglomeration agg, double temps) {
		this.nomAlgo = nomAlgo ;
		try { this.k = k.intValue() ; } catch(NullPointerException e) { this.k = null ;}
		try { this.dynamique = dynamique.booleanValue() ; } catch(NullPointerException e) { this.dynamique = null ;}
		this.nbVilles = agg.getVilles().size() ;
		this.nbEcoles = agg.nbEcoles() ;
		this.contrainteAccessibilite = agg.respecteAccessibilite() ;
		this.temps = temps ;
	}
	
	protected String getNomAlgo() {
		return nomAlgo;
	}

	protected double getTemps() {
		return temps;
	}

	protected double getScore() {
		return (double) nbEcoles/nbVilles ;
	}

	protected String formatCSV() {
		return "\""+nomAlgo+"\","+k+","+dynamique+","+String.format("%.3f", getScore())+","+String.format("%.3f", temps)+","+nbVilles+","+"\""+contrainteAccessibilite+"\"";
	}
	
	protected static String enteteCSV() {
		return "\"nomAlgo\",\"k\",\"dynamique\",\"score\",\"temps\",\"nbVilles\",\"contrainteAccessibilite\"" ;
	}
	
	public String toString() {
		return "[algo="+nomAlgo+", score="+String.format("%.3f", getScore())+", temps="+String.format("%.3f", temps)+"ms, respecteAccessibilite="+contrainteAccessibilite+"]" ;
	}
}
