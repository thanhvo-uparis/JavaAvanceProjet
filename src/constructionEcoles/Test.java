package constructionEcoles
public class Test {
	public static void main(String[] args) {
		System.out.println("création d'une agglomeration de 5 villes" + agglomeration(5));
		System.out.println("choix de l'ajout d'une route " + 1 );
		System.out.println("ajout d'une route entre a et b " ajouterRoute(a,b));
		System.out.println("choix de l'ajout d'une route " + 1 );
		System.out.println("ajout d'une route entre b et c " ajouterRoute(b,c));
		System.out.println("choix de l'ajout d'une route " + 1 );
		System.out.println("ajout d'une route entre c et d " ajouterRoute(c,d));
		System.out.println("choix de l'ajout d'une route " + 1 );
		System.out.println("ajout d'une route entre d et e " ajouterRoute(d,e));
		System.out.println("Fin de l'ajout des routes" + 2);
		System.out.println("choix de l'ajout d'une ecole " + 1 );
		System.out.println("ajout d'une ecole pour la ville a " ajouterEcole(a));
		System.out.println("choix du retrait d'une ecole " + 1 );
		System.out.println("retrait d'une ecole pour la ville a " RetirerEcole(a));
		System.out.println("Fin du programme" + 3 );
	}
}
