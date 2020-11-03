package constructionEcoles;

import java.util.Scanner;
import java.io.*;


public class Main {
	
	//On demande à l'utilisateur un nombre de villes qui servira à construire l'agglomération. Ce nombre doit etre compris entre 1 et 26 auquel cas on affiche une erreur.//
	public static void main(String[] args) {
	Scanner sc = new Scanner(System.in);
	System.out.println("Entrez un nombre de villes ");
	Agglomeration agg = new Agglomeration(sc.nextInt());
	
	
	/* On crée ici le 1er menu qui va nous afficher les choix suivants : 1/ajouter une route ou 2/fin.
	 
	   La boucle while permet de verifier que l'agglomeration est connexe, c'est-à-dire qu'elle vérifie que toute les villes sont réliéesd par au minimium une route.Tant que ce n'est pas le cas on continue 
	   de  demander à l'utilisateur d'ajouter des routes.
	  
	   Si on choisit ajouter une route, on utilise la fonction void ajouterRoute en prenant en argument les deux villes rentrées au clavier.
	 
	   Si on choisit fin, une vérification est faite pour s'assurer que toutes villes sont reliées. On utilise pour cela la fonction boolean estConnexe.
	   Si ce n'est pas le cas, on affiche un message d'erreur à l'utilisateur afin qu'il continue. 
	*/

	while (estConnexe(agg) = false ){
		{
			int z;
			BufferedReader syl1=new BufferedReader (new InputStreamReader(System.in));
			System.out.println("1-ajouter une route");
			System.out.println("2-Fin");

			System.out.println("Veuillez entrer votre choix");
			z=Integer.parseInt(syl1.readLine());
			switch(z)
			{
			case 1:{
				System.out.println("Entrez 2 villes à relier ");
				Scanner sc1 sc2 = new Scanner(System.in);
				ajouterRoute(nextVille,nextVille);
			}

			}
			break;
			case 2: {
			System.out.println("Au revoir");
			}
			}
	
		
	}
	
	/* On crée ici le 2eme menu qui va permettre à l'utilisateur de soit: 1/ajouter une ecole 2/retirer une ecole 3/ fin.
	 * 
	 * Si l'on choisit ajouter une ecole, on utilise la fonction void ajouterEcole en utilisant comme argument la ville rentrée au clavier par l'utilisateur.
	 * 
	 * Si l'on choisit retirer une école, on utilise la fonction void retirerEcole en utilisant comme argument la ville rentrée au clavier par l'utilisateur.
	 * 
	 * Si l'on choisit fin , le programme s'arrete.
	 */
	
	int y;
	while (y != 3){ 
	BufferedReader syl1=new BufferedReader (new InputStreamReader(System.in));
	System.out.println("1-ajouter une ecole");
	System.out.println("2-retirer une ecole");
	System.out.println("3-fin");

	System.out.println("Veuillez entrer votre choix");
	z=Integer.parseInt(syl1.readLine());
	switch(y)
	{
	case 1:{
		Scanner sc3 = new Scanner(System.in);
		ajouterEcole(nextVille);
		afficheVilleAEcole(agg);
	}

	}
	break;
	case 2:{
		Scanner sc4 = new Scanner(System.in);
		retirerEcole(nextVille);
		afficheVilleAEcole(agg);
	}
	break;
	case 3:{
		system.out.println("fin du programme");
		afficheVilleAEcole(agg);
	}
	}
}
