package main.menus;

import java.util.Scanner;

import main.entites.Agglomeration;
import main.menus.util.Affichage;


public class MenuPrincipal {
	
	public static void lancement() {
		Affichage.afficherIntro();
		Scanner sc = new Scanner(System.in) ;
		Agglomeration agg = MenuChargementAgglomeration.choixTypeChargement(sc) ;
		MenuChargementAgglomeration.menuResolutionProbleme(agg, sc);
	}
}
