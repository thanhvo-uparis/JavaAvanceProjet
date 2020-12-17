package main.menus;

import java.util.Scanner;
import java.util.ArrayList ;

import main.entites.Agglomeration;
import main.menus.util.Affichage;


public class MenuPrincipal {
	
	public static void lancement(Scanner sc, boolean intro) {
		if(intro) Affichage.afficherIntro();//fait appel à la méthode afficherIntro de la classe Affichage qui permet d'afficher l'intro du programme
		Agglomeration agg = MenuChargementAgglomeration.choixTypeChargement(sc) ;//permet d'affecter la valeur de l'agglomération à agg depuis la méthode choixTypeChargement de la classe MenuChargementAgglomeration.
		MenuChargementAgglomeration.menuResolutionProbleme(agg, sc);//fait appel à la méthode menuResolutionProbleme depuis la classe MenuChargementAgglomeration avec comme paramètres agg et sc.
	}
}
