package main.algorithmique.testeur;

import main.entites.Agglomeration;

/**
 * Classe utilitaire permettant d'extraire des informations sur l'exécution d'un algorithme
 * @author Yann Trividic
 * @version 1.0
 */
public class Rapport {
	
	private String nomAlgo ;
	private Integer k ;
	private Boolean dynamique ;
	private Boolean filePriorite ;
	
	private int nbVilles, nbEcoles ;
	private boolean contrainteAccessibilite ;
	
	private double temps ;
	
	protected Rapport(String nomAlgo, Integer k, Boolean dynamique, Boolean filePriorite, Agglomeration agg, double temps) {
		this.nomAlgo = nomAlgo ;
		try { this.k = k.intValue() ; } catch(NullPointerException e) { this.k = null ;}
		try { this.dynamique = dynamique.booleanValue() ; } catch(NullPointerException e) { this.dynamique = null ;}
		try { this.filePriorite = filePriorite.booleanValue() ; } catch(NullPointerException e) { this.filePriorite = null ;}
		this.nbVilles = agg.getVilles().size() ;
		this.nbEcoles = agg.getNbEcoles() ;
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
		return "\""+nomAlgo+"\","+k+","+dynamique+","+filePriorite+","+String.format("%.3f", getScore())+","+String.format("%.3f", temps)+","+nbVilles+","+"\""+contrainteAccessibilite+"\"";
	}
	
	protected static String enteteCSV() {
		return "\"nomAlgo\",\"k\",\"dynamique\",\"filePriorite\",\"score\",\"temps\",\"nbVilles\",\"contrainteAccessibilite\"" ;
	}
	
	public String toString() {
		return "[algo="+nomAlgo+", score="+String.format("%.3f", getScore())+", temps="+String.format("%.3f", temps)+"ms, respecteAccessibilite="+contrainteAccessibilite+"]" ;
	}
}
