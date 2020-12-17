package main.menus;

import java.util.Scanner;
import java.util.ArrayList ;

import main.entites.Agglomeration;
import main.menus.util.Affichage;


public class MenuPrincipal {
	
	public static void lancement(Scanner sc, boolean intro) {
		if(intro) Affichage.afficherIntro();
		Agglomeration agg = MenuChargementAgglomeration.choixTypeChargement(sc) ;
		MenuChargementAgglomeration.menuResolutionProbleme(agg, sc);
	}
}
