package algorithmique.testeur;

public class RapportTest {
	
	/**
	 * 
	 */
	private String nomAlgo ;
	private Integer k ;
	private Boolean dynamique ;
	
	private int nbVilles, nbEcoles ;
	
	private double temps ;
	
	public RapportTest(String nomAlgo, Integer k, Boolean dynamique, int nbVilles, int nbEcoles, double temps) {
		this.nomAlgo = nomAlgo ;
		this.k = k ;
		this.dynamique = dynamique ;
		this.nbVilles = nbVilles ;
		this.nbEcoles = nbEcoles ;
		this.temps = temps ;
	}
	
	public String getNomAlgo() {
		return nomAlgo;
	}

	public double getTemps() {
		return temps;
	}

	public double getScore() {
		return (double) nbEcoles/nbVilles ;
	}

	public String formatCSV() {
		return "\""+nomAlgo+"\","+k+","+dynamique+","+String.format("%.3f", getScore())+","+String.format("%.3f", temps)+","+nbVilles;
	}
	
	public String toString() {
		return "[algo="+nomAlgo+", score="+String.format("%.3f", getScore())+", temps="+String.format("%.3f", temps)+"ms]" ;
	}
}
