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
            List<String[]> routes = new ArrayList<>(); //Une liste d'un tableau de 2 caractères route pour enregistrer les routes lues à partir d'un fichier
            List<String> ecoles = new ArrayList<>();  //Une liste de caractères ecoles pour enregistrer les ecoles lues à partir d'un fichier
            while ((line = br.readLine()) != null) { //lit le fichier à la boucle de ligne par ligne, lire jusqu'à ce que le fichier soit terminé
                if (line.startsWith("ville("))
                    villes.add(parserVille(line)); //si la chaîne de lecture commence par "ville (", effectuez un filtrage des données pour ajouter à les villes
                if (line.startsWith("ecole("))
                    ecoles.add(parserEcole(line));  //si la chaîne de lecture commence par "ecole (", effectuez un filtrage des données pour ajouter à les ecoles
                if (line.startsWith("route("))
                    routes.add(parserRoute(line));  //si la chaîne de lecture commence par "route (", effectuez un filtrage des données pour ajouter à les routes
            }
		
            Agglomeration agg = new Agglomeration(villes.size());  //Initialise l'objet Agglomération à partir des informations lisibles dans le fichier
            for (String v : villes) {  //parcourt les villes si les caractères ne sont pas dans la liste des écoles, alors donc la ville est définie comme aucune école
                if (!ecoles.contains(v)) agg.getVille(v.toString()).setHasEcole(false);
            }
            for (String[] ville : routes) {  //parcourt les routes à ajouter
                agg.ajouterRoute(ville[0], ville[1]);
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
                fileWriter.append("ville(" + v.getKey() + ").\r\n");
            }
		
		
            //écrit une liste des routes au fichier
            Set<Double> hashRoutes = new HashSet<>(); ////crée une liste hash ( identifiant unique ) de route, vérifie si les routes sont identiques ou non lors de l'examen sur les voisins
            for (Ville v : agg.getVilles()) {
                for (Ville v2 : v.getVoisins()) {
                	Double hash = Math.pow(v.getKey().toCharArray()[0], 2) + Math.pow(v2.getKey().toCharArray()[0], 2); //récupére hash de route
                	if (!hashRoutes.contains(hash)) { //si hash n'est pas encore dans la liste
                        fileWriter.append("route(" + v.getKey() + "," + v2.getKey() + ").\r\n"); //écrit au fichier de route
                        hashRoutes.add(hash); // add hash à la liste pour vérifier dans la boucle suivante
                }
                //parcourt les villes et les voisins pour créer une route et add à la liste des routes
                
            }
          }
            hashRoutes.clear();//clear une liste
		
            //écrit une liste des ecoles au fichier
            for (Ville v : agg.getVilles()) {
                if (v.getHasEcole()) {
                    fileWriter.append("ecole(" + v.getKey() + ").\r\n");
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
     * @param ligne
     * @return nomVille key dans la chaîne ville
     */
     public static String parserVille(String ville) {
          String nomVille = ville.split("\\(")[1].split("\\).")[0];
          return nomVille;
	 }
	
    /**
     * Methode effectue le filtrage des données des routes lors de la lecture du fichier pour se synchroniser avec l'objet Agglomeration
     *
     * @param ligne
     * @return route
     */
       public static String[] parserRoute(String route) {
            String[] donnees = route.split("\\(")[1].split("\\).")[0].split(",");
            return donnees;  
        }

	
    /**
     * Method effectue le filtrage des données des ecoles lors de la lecture du fichier pour se synchroniser avec l'objet Agglomeration
     *
     * @param ligne 
     * @return nomVille
     */
     public static String parserEcole(String ecole) {
         String nomVille = ecole.split("\\(")[1].split("\\).")[0];
         return nomVille;
	 }
     
     
     public static void main(String[] args) {
         Agglomeration agglomeration = LectureEcriture.lectureDepuisFichier("./src/test/exemple.ca");
         agglomeration.afficheBilan() ;
         LectureEcriture.ecritureVersFichier("./src/resources/out.ca",agglomeration);
     }

}
