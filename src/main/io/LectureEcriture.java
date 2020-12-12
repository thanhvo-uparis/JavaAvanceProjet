package main.io;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.entites.Agglomeration;
import main.entites.Ville;

/**
 *Cette classe manipule un fichier avec un objet Agglomération, écrit un objet dans un fichier avec un chemin spécifié,
 *synchronise le fichier selon le chemin spécifié vers un objet Agglomération.
 *
 * @author thanh-congvo
 * @version 1.0
 */


//TODO Gérer les différentes exceptions (fichier invalide, chemin invalide, etc, voir slides données en TD)
//TODO Faire en sorte que seuls des fichiers .CA puisse être lus, sinon on throw une exception (Il faut créer une exception en plus pour ça)
//TODO ajouter une méthode EcritureCSV pour les RapportTest des algos ?

public class LectureEcriture {
	/**
     * Méthode statique permet de lire pour créer un objet d'agglomération à partir du fichier avec le chemin spécifié
     *
     * @param chemin chemin du file
     * @return objet Agglomeration si la lecture et la synchronisation du fichier sur l'objet sont réussies
     * null si la lecture et la synchronisation du fichier sur l'objet rencontre une exception
     */
    public static Agglomeration lectureDepuisFichier(String chemin) {
        try (BufferedReader br = new BufferedReader(new FileReader(chemin))) { //un objet reader
            String line; //lit ligne par ligne
            List<String> villes = new ArrayList<>(); //Une liste de caractères ville pour enregistrer les villes lues à partir d'un fichier
            List<Character[]> routes = new ArrayList<>(); //Une liste d'un tableau de 2 caractères route pour enregistrer les routes lues à partir d'un fichier
            List<Character> ecoles = new ArrayList<>();  //Une liste de caractères ecoles pour enregistrer les ecoles lues à partir d'un fichier
            while ((line = br.readLine()) != null) { //lit le fichier à la boucle de ligne par ligne, lire jusqu'à ce que le fichier soit terminé
                if (line.startsWith("ville("))
                    villes.add(parseVille(line)); //si la chaîne de lecture commence par "ville (", effectuez un filtrage des données pour ajouter à les villes
                if (line.startsWith("ecole("))
                    ecoles.add(parseEcole(line));  //si la chaîne de lecture commence par "ecole (", effectuez un filtrage des données pour ajouter à les ecoles
                if (line.startsWith("routes("))
                    routes.add(parseRoute(line));  //si la chaîne de lecture commence par "route (", effectuez un filtrage des données pour ajouter à les routes
            }
		
            Agglomeration agg = new Agglomeration(villes.size());  //Initialise l'objet Agglomération à partir des informations lisibles dans le fichier
            for (String v : villes) {  //parcourt les villes si les caractères ne sont pas dans la liste des écoles, alors donc la ville est définie comme aucune école
                if (!ecoles.contains(v)) agg.getVille(v.toString()).setHasEcole(false);
            }
            for (Character[] chars : routes) {  //parcourt les routes à ajouter
                agg.ajouterRoute(chars[0].toString(), chars[1].toString());
            }
            br.close();
            return agg;
        } catch (FileNotFoundException e) {
            //TODO traite exception si le fichier est introuvable après le chemin
            System.err.println("N'existence pas d'un fichier avec chemin: " + chemin);
        } catch (IOException e) {
            //TODO traite exception s'il y a une erreur Input Output Exception lors de la lecture du fichier
            e.printStackTrace();
        } catch (Exception e) {
            //TODO exception requise est à la ligne 42 - agg.getVille (v)
            e.printStackTrace();
        }
        return null; // retourne null si rencontre une exception
    }

		
    /**
     * Méthode statique permet d'écrire depuis l'objet Agglomération dans un fichier
     *
     * @param chemin le chemin du fichier
     * @param agg    L'objet est écrit dans le fichier
     */
    public static void ecritureVersFichier(String chemin, Agglomeration agg) {
        try (FileWriter fileWriter = new FileWriter(chemin)) {
            //écrit une liste des villes au fichier
            for (Ville v : agg.getVilles()) {
                fileWriter.append("ville(" + v.getKey() + ").");
            }
		
		
            //écrit une liste des routes au fichier
            Set<Double> hashRoutes = new HashSet<>(); ////crée une liste hash ( identifiant unique ) de route, vérifie si les routes sont identiques ou non lors de l'examen sur les voisins
            for (Ville v : agg.getVilles()) {
                for (Ville v2 : v.getVoisins()) {
                	Double hash = Math.pow(v.getKey().toCharArray()[0], 2) + Math.pow(v2.getKey().toCharArray()[0], 2); //récupére hash de route
                	if (!hashRoutes.contains(hash)) { //si hash n'est pas encore dans la liste
                        fileWriter.append("route(" + v.getKey() + "," + v2.getKey() + ")."); //écrit au fichier de route
                        hashRoutes.add(hash); // add hash à la liste pour vérifier dans la boucle suivante
                }
                //parcourt les villes et les voisins pour créer une route et add à la liste des routes
                
            }
          }
            hashRoutes.clear();//clear une liste
		
            //écrit une liste des ecoles au fichier
            for (Ville v : agg.getVilles()) {
                if (v.getHasEcole()) {
                    fileWriter.append("ecole(" + v.getKey() + ").");
                }
            }
            fileWriter.flush(); // clear buffer de writer
        } catch (FileNotFoundException e) {
            //TODO traite une exception si le fichier est introuvable après le chemin
            System.err.println("N'existence pas d'un fichier avec chemin: " + chemin);
        } catch (IOException e) {
            //TODO traite exception s'il y a une erreur Input Output Exception lors de la lecture du fichier
            e.printStackTrace();
        }
    }

    /**
     * Methode effectue le filtrage des données des villes lors de la lecture du fichier pour se synchroniser avec l'objet Agglomeration
     *
     * @param ville une chaîne contient ville và key sous la forme "ville(x)"
     * @return key dans la chaîne ville sous la forme "x "
     */
    private static Character parseVille(String ville) {
        return ville.replace("ville(", "").replace(").", "").trim().toCharArray()[0]; //supprime les éléments autour character et prend character
    }

	
    /**
     * Methode effectue le filtrage des données des routes lors de la lecture du fichier pour se synchroniser avec l'objet Agglomeration
     *
     * @param route une chaîne contient route và 2 key sous la forme "route(x,y)"
     * @return un tableau contient 2 key dans une chaîne route sous la forme ['x','y']
     */
    private static Character[] parseRoute(String route) {
        String[] arr = route.replace("route(", "").replace(").", "").trim().split(",");  //supprime les éléments autour
        return new Character[]{arr[0].toCharArray()[0], arr[1].toCharArray()[0]}; //obtient un tableau de 2 éléments
    }

	
    /**
     * Method effectue le filtrage des données des ecoles lors de la lecture du fichier pour se synchroniser avec l'objet Agglomeration
     *
     * @param ecole une chaîne contient ecole và key sous la forme "ecoles(x)"
     * @return key dans une chaîne ville sous la forme "x"
     */
    private static Character parseEcole(String ecole) {
        return ecole.replace("ecole(", "").replace(").", "").trim().toCharArray()[0]; //supprime les éléments autour character et prend character
    }

}
