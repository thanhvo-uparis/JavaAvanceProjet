package main;

import java.util.Scanner;

import main.menus.EntreeApp;

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

//TODO Mettre à jour le readme (expliquer très brièvement le rôle des packages, peut-être mettre un lien vers la JavaDoc ? Expliquer brièvement les algos)
//TODO Vérifier que toutes les exceptions sont bien gérées

public class Main {
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in) ; 
		main.io.ChargeurProprietes.chargerProprietes(false);
		EntreeApp.lancement(sc);
		sc.close();
	}
}	
