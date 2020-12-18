package main.menus;

import java.util.Scanner;

import main.entites.Agglomeration;
import main.menus.util.Affichage;


public class MenuPrincipal {
	/**
	 * La méthode lancement permet de lancer le menu du choix de type de chargement 
	 * puis le menu de la résolution du problème.
	 * @param sc entrée clavier qui permet de prendre le choix de l'utilisateur	
	 * @param intro permet d'afficher une petite intro au programme
	 */
	public static void lancement(Scanner sc, boolean intro) {
		if(intro) Affichage.afficherIntro();
		Agglomeration agg = MenuChargementAgglomeration.choixTypeChargement(sc) ;
		MenuChargementAgglomeration.menuResolutionProbleme(agg, sc); 
	}
}
