package constructionEcoles;



import menus.MenuInitial;



/**
 * La classe Main va permettre a l'utilisateur de modeliser son agglomeration en manipulant une instance d'un objet Agglomeration. A l'aide d'un menu, l'utilisateur va pouvoir :
 * 1) instancier une agglomeration de n villes.
 * 2) relier les villes entre elles a l'aide de routes. L'utilisateur peut relier autant de villes qu'il le souhaite tant que celles-ci ne sont pas reliees toutes reliees deux a deux par une route. Une fois qu'il existe au moins un chemin reliant chaque couple de villes, l'utilisateur peut : 
 * 		a. retirer des ecoles tant que ce retrait satisfait la contrainte d'Accessibilite*.
 * 		b. ajouter des ecoles si la ville n'en a pas deja une. (Pour le moment, la contrainte d'economie n'est pas respectee.) 
 * A chaque fois que l'utilisateur retire ou ajoute une ecole, on affiche la liste des villes contenant des ecoles.
 * A la fin du programme, la liste des villes contenant des ecoles est affichees, avec la liste des routes les reliant.
 * @author Anthony Baptista
 * @author Yann Trividic
 * @version 1.0
 */

//TEST COMMIT PAZRTIE 2///

//TODO 	faire un Main concis et faire une classe UtilMain.java avec tout le nécessaire, mettre le moins de code possible
//		il existe des architectures java pour lesquelles le main tient en une seule ligne (voir 
//TODO reprendre les nommages, supprimer le franglais, utiliser des noms parlant
//TODO compléter la Javadoc
//TODO revoir l'architecture globale du programme et l'emboîtement des packages, faire en sorte que le JAR soit exécutable
//TODO donner à l'utilisateur la possibilité de nommer les villes (utiliser pour ça le constructeur Agglomeration(Ville...villes)
//TODO rendre le code robuste à une agglomération de plus de 26 villes (viser 100 villes)

//TODO une fois que tout ça est fait, penser à l'utilisateur, le prof va tester différents scénarios et on doit fournir un truc robuste
//TODO vérifier que tout le code s'exécute bien en testant différentes toutes les erreurs possibles (fichier inexistant, mauvais input au clavier...)


public class Main {
	public static void main(String[] args) {
		MenuInitial.lancement();
	}

}	

//Me manque encore a g�rer les exceptions que je vais avancer et la complexit� des algorithmes.