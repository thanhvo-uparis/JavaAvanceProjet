package main.menus;

import java.util.Scanner;

import main.menus.util.Affichage;

/**
 * Classe static permettant de rentrer dans l'application en elle-même
 * @author Yann Trividic
 * @author Anthony Baptista
 */
public class EntreeApp {
	
	/**
	 * La méthode lancement permet de lancer le menu du choix de type de chargement 
	 * puis le menu de la résolution du problème.
	 * @param sc entrée clavier qui permet de prendre le choix de l'utilisateur	
	 */
	public static void lancement(Scanner sc) {
		Affichage.afficherIntro();
		MenuChargementAgglomeration.choixTypeChargement(sc) ;
	}
}
