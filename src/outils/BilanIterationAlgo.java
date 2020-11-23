package outils;

public class BilanIterationAlgo {
	private int score ;
	private double temps ;
	
	public BilanIterationAlgo(int score, double temps) {
		this.score = score ;
		this.temps = temps ;
	}

	public int getScore() {
		return score;
	}

	public double getTemps() {
		return temps;
	}
	
	public String toString() {
		return "score : "+this.score+", temps : "+this.temps ;
	}
}
