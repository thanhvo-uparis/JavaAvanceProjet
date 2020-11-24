package outils;

package constructionEcoles.outils;
import constructionEcoles.Agglomeration;
import constructionEcoles.Ville;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LectureEcriture {

    public static Agglomeration lectureDepuisFichier(String chemin) {
        try (BufferedReader br = new BufferedReader(new FileReader(chemin))) { // Lire un fichier
            String line;                    //  Lecture par ligne
		
            List<Character> villes = new ArrayList<>(); // List stocke les villes lue au fichier
            List<Character[]> routes = new ArrayList<>(); // List stocke les routes lue au fichier
            List<Character> ecoles = new ArrayList<>();  // List stocke les ecoles lue au fichier 
		
            while ((line = br.readLine()) != null) { // La boucle lit ligne par ligne jusqu'à ce que le fichier soit terminé		     
                if (line.startsWith("ville("))
                    villes.add(parseVille(line)); // si la chaîne de caractère commence par ville( puis coupe le caractère dans cette chaîne et ajoute-le à la liste des villes)
                
		if (line.startsWith("ecole("))
                    ecoles.add(parseEcole(line));// si la chaîne de caractère commence par école( puis coupe le caractère dans cette chaîne et ajoute-le à la liste des ecoles)
		    
                if (line.startsWith("routes("))
                    routes.add(parseRoute(line));// si la chaîne de caractère commence par route( puis coupe le caractère dans cette chaîne et ajoute-le à la liste des routes)
            }
		
            Agglomeration agg = new Agglomeration(villes.size()); //  Déclaration 1 agg avec la taille qui est la taille de la liste villes lue au fichier
		
            for (char v : villes) { //Liste les villes si les key ne sont pas dans la liste des écoles, alors set ville de cette key n'a pas d'école
                if (!ecoles.contains(v)) agg.getVille(v).setHasEcole(false);
            }
		
            for (Character[] chars : routes) {//Liste la liste des routes pour ajouter routes à agg
                agg.ajouterRoute(chars[0], chars[1]);
            }
            return agg;
		
        } catch (FileNotFoundException e) {
            //TODO exception si le chemin est incorrect
            System.err.println("N'existence pas d'un fichier avec chemin: " + chemin);
		
        } catch (IOException e) {
            //TODO exception de lecture et d'écriture de fichier
            e.printStackTrace();
		
        } catch (Exception e) {
            //TODO exception est à la ligne 28 - agg.getVille(v)
            e.printStackTrace();
        }
        return null; // Si une exception est rencontrée, rerourne: un objet nul
    }

	
    public static void ecritureVersFichier(String chemin, Agglomeration agg) {
	    
        try (FileWriter fileWriter = new FileWriter(chemin)) {
            //écriture une liste des villes
            for (Ville v : agg.getVilles()) {
                fileWriter.append("ville(" + v.getKey() + ").");
            }
		
            //TODO écriture des routes

            //ecriture des écoles
            for (Ville v : agg.getVilles()) {
                if (v.getHasEcole()) {
                    fileWriter.append("ecole(" + v.getKey() + ").");
                }
            }
        } catch (FileNotFoundException e) {
            //TODO exception si le chemin est incorrect
            System.err.println("N'existence pas d'un fichier avec chemin: " + chemin);
		
        } catch (IOException e) {
            //TODO exception de lecture et d'écriture de fichier
            e.printStackTrace();
        }
    }

    private static Character parseVille(String ville) {
        return ville.replace("ville(", "").replace(").", "").trim().toCharArray()[0]; // parese série de ville(a). pour récupérer le key par la façon de replace
    }

    private static Character[] parseRoute(String route) {
        String[] arr = route.replace("route(", "").replace(").", "").trim().split(",");  // parese série de route(a). pour récupérer le key par la façon de replace
        return new Character[]{arr[0].toCharArray()[0], arr[1].toCharArray()[0]};
    }

    private static Character parseEcole(String ecole) {
        return ecole.replace("ecole(", "").replace(").", "").trim().toCharArray()[0]; // parese série de ecole(a,b). pour récuperer un tableau de 2 key par la façon de replace (obtention string a,b), puis divise la chaîne string par caractère, sort le tableau [a, b]
    }

}
