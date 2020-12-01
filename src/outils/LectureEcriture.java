package outils;

import constructionEcoles.Agglomeration;
import constructionEcoles.Route;
import constructionEcoles.Ville;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *Cette classe manipule un fichier avec un objet AgglomÃ©ration, Ã©crit un objet dans un fichier avec un chemin spÃ©cifiÃ©,
 *synchronise le fichier selon le chemin spÃ©cifiÃ© vers un objet AgglomÃ©ration.
 *
 * @author thanh-congvo
 * @version 1.0
 */


//TODO GÃ©rer les diffÃ©rentes exceptions (fichier invalide, chemin invalide, etc, voir slides donnÃ©es en TD)
//TODO faire en sorte que seuls des fichiers .CA puisse Ãªtre lus, sinon on throw une exception (Il faut crÃ©er une exception en plus pour Ã§a)

public class LectureEcriture {
    /**
     * MÃ©thode statique permet de lire pour crÃ©er un objet d'agglomÃ©ration Ã  partir du fichier avec le chemin spÃ©cifiÃ©
     *
     * @param chemin chemin du file
     * @return objet Agglomeration si la lecture et la synchronisation du fichier sur l'objet sont rÃ©ussies
     * null si la lecture et la synchronisation du fichier sur l'objet rencontre une exception
     */
    public static Agglomeration lectureDepuisFichier(String chemin) {
        try (BufferedReader br = new BufferedReader(new FileReader(chemin))) { //un objet reader
            String line; //lit ligne par ligne
            List<Character> villes = new ArrayList<>(); //Une liste de caractÃ¨res ville pour enregistrer les villes lues Ã  partir d'un fichier
            List<Character[]> routes = new ArrayList<>(); //Une liste d'un tableau de 2 caractÃ¨res route pour enregistrer les routes lues Ã  partir d'un fichier
            List<Character> ecoles = new ArrayList<>();  //Une liste de caractÃ¨res ecoles pour enregistrer les ecoles lues Ã  partir d'un fichier
            while ((line = br.readLine()) != null) { //lit le fichier Ã  la boucle de ligne par ligne, lire jusqu'Ã  ce que le fichier soit terminÃ©
                if (line.startsWith("ville("))
                    villes.add(parseVille(line)); //si la chaÃ®ne de lecture commence par "ville (", effectuez un filtrage des donnÃ©es pour ajouter Ã  les villes
                if (line.startsWith("ecole("))
                    ecoles.add(parseEcole(line));//si la chaÃ®ne de lecture commence par "ecole (", effectuez un filtrage des donnÃ©es pour ajouter Ã  les ecoles
                if (line.startsWith("routes("))
                    routes.add(parseRoute(line));// Si la chaÃ®ne de lecture commence par "route (", effectuez un filtrage des donnÃ©es pour ajouter Ã  les routes
            }
		
            Agglomeration agg = new Agglomeration(villes.size()); //Initialise l'objet AgglomÃ©ration Ã  partir des informations lisibles dans le fichier
            for (character v : villes) { // parcourt les villes si les caractÃ¨res ne sont pas dans la liste des Ã©coles, alors donc la ville est dÃ©finie comme aucune Ã©cole
                if (!ecoles.contains(v)) agg.getVille(v.toString).setHasEcole(false);
            }
            for (Character[] chars : routes) {// parcourt les routes Ã  ajouter
                agg.ajouterRoute(chars[0].toString(), chars[1].toString());
            }
            br.close();
            return agg;
        } catch (FileNotFoundException e) {
            //TODO traite exception si le fichier est introuvable aprÃ¨s le chemin
            System.err.println("N'existence pas d'un fichier avec chemin: " + chemin);
        } catch (IOException e) {
            //TODO traite exception s'il y a une erreur Input Output Exception lors de la lecture du fichier
            e.printStackTrace();
        } catch (Exception e) {
            //TODO exception requise est Ã  la ligne 42 - agg.getVille (v)
            e.printStackTrace();
        }
        return null; // retourne null si rencontre une exception
    }

		
    /**
     * MÃ©thode statique permet d'Ã©crire depuis l'objet AgglomÃ©ration dans un fichier
     *
     * @param chemin le chemin du fichier
     * @param agg    L'objet est Ã©crit dans le fichier
     */
    public static void ecritureVersFichier(String chemin, Agglomeration agg) {
        try (FileWriter fileWriter = new FileWriter(chemin)) {
            // Ã©crit une liste des villes au fichier
            for (Ville v : agg.getVilles()) {
                fileWriter.append("ville(" + v.getKey() + ").");
            }
		
		
            //Ã©crit une liste des routes au fichier
            Set<Double> hashRoutes = new HashSet<>(); //crÃ©e une liste des routes
            for (Ville v : agg.getVilles()) {
                for (Ville v2 : v.getVoisins()) {
                	Double hash = Math.pow(v.getKey().toCharArray()[0], 2) + Math.pow(v2.getKey().toCharArray()[0], 2); //lấy mã hash của route
                	if (!hashRoutes.contains(hash)) { // nếu hash chưa có trong danh sách
                        fileWriter.append("route(" + v.getKey() + "," + v2.getKey() + ")."); // ghi ra file route
                        hashRoutes.add(hash); // add hash vào danh sách để kiểm tra trong vòng lặp tiếp theo
                }
                // parcourt les villes et les voisins pour crÃ©er une route et add Ã  la liste des routes
                
            }
          }
            hashRoutes.clear();//clear danh sách
		
            //Ã©crit une liste des ecoles au fichier
            for (Ville v : agg.getVilles()) {
                if (v.getHasEcole()) {
                    fileWriter.append("ecole(" + v.getKey() + ").");
                }
            }
        } catch (FileNotFoundException e) {
            //TODO traite une exception si le fichier est introuvable aprÃ¨s le chemin
            System.err.println("N'existence pas d'un fichier avec chemin: " + chemin);
        } catch (IOException e) {
            //TODO traite exception s'il y a une erreur Input Output Exception lors de la lecture du fichier
            e.printStackTrace();
        }
    }

    /**
     * Methode effectue le filtrage des donnÃ©es des villes lors de la lecture du fichier pour se synchroniser avec l'objet Agglomeration
     *
     * @param ville  une chaÃ®ne contient ville vÃ  key sous la forme "ville(x)"
     * @return  key dans la chaÃ®ne ville sous la forme "x "
     */
    private static Character parseVille(String ville) {
        return ville.replace("ville(", "").replace(").", "").trim().toCharArray()[0]; //supprime les Ã©lÃ©ments autour character et prend character
    }

	
    /**
     * Methode effectue le filtrage des donnÃ©es des routes lors de la lecture du fichier pour se synchroniser avec l'objet Agglomeration
     *
     * @param route une chaÃ®ne contient route vÃ  2 key sous la forme "route(x,y)"
     * @return  un tableau contient 2 key dans une chaÃ®ne route sous la forme ['x','y']
     */
    private static Character[] parseRoute(String route) {
        String[] arr = route.replace("route(", "").replace(").", "").trim().split(",");  //supprime les Ã©lÃ©ments autour
        return new Character[]{arr[0].toCharArray()[0], arr[1].toCharArray()[0]}; //obtient un tableau de 2 Ã©lÃ©ments
    }

	
    /**
     * Method effectue le filtrage des donnÃ©es des ecoles lors de la lecture du fichier pour se synchroniser avec l'objet Agglomeration
     *
     * @param ecole une chaÃ®ne contient ecole vÃ  key sous la forme "ecoles(x)"
     * @return  key dans une chaÃ®ne ville sous la forme "x"
     */
    private static Character parseEcole(String ecole) {
        return ecole.replace("ecole(", "").replace(").", "").trim().toCharArray()[0]; //supprime les Ã©lÃ©ments autour character et prend character
    }

}
