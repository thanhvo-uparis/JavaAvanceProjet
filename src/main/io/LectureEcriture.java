package main.io;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import main.entites.Agglomeration;
import main.entites.Ville;
import main.exceptions.FichierAgglomerationSyntaxeException;
import main.exceptions.NbVillesException;
import main.exceptions.VilleException;

/**
 * Cette classe manipule les fichiers CA utilisés pour contenir les informations relatives à un objet Agglomeration.
 * Cette classer permet aussi bien la lecture que l'écriture des fichiers CA.
 * @author thanh-congvo
 * @author Yann Trividic
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
            boolean sansErreurRoutes = true, sansErreurEcoles = true, sansErreurVilles = true;
            List<String> villes = new ArrayList<>(); //Une liste de caractères ville pour enregistrer les villes lues à partir d'un fichier
            List<String[]> routes = new ArrayList<>(); //Une liste d'un tableau de 2 caractères route pour enregistrer les routes lues à partir d'un fichier
            List<String> ecoles = new ArrayList<>();  //Une liste de caractères ecoles pour enregistrer les ecoles lues à partir d'un fichier
            
            while ((line = br.readLine()) != null) { //lit le fichier à la boucle de ligne par ligne, lire jusqu'à ce que le fichier soit terminé
                if (line.toLowerCase().startsWith("ville(") || line.toLowerCase().startsWith("v(") || line.toLowerCase().startsWith("villes(")) {
                    String ville = parserVille(line) ; // on évite les doublons
                	if(!villes.contains(ville) && ville.length() != 0) {
                		villes.add(ville); //si la chaîne de lecture commence par "ville (", effectuez un filtrage des données pour ajouter à les villes
                	} else sansErreurVilles = false ;
                } else if(line.toLowerCase().startsWith("ecole(") || line.toLowerCase().startsWith("e(") || line.toLowerCase().startsWith("ecoles(")) {
                    String ecole = parserEcole(line) ;
                	if(!ecoles.contains(ecole) && ecole.length() != 0) {
                		ecoles.add(ecole) ;
                	} else sansErreurEcoles = false ;                
                } else if(line.toLowerCase().startsWith("route(") || line.toLowerCase().startsWith("r(") || line.toLowerCase().startsWith("routes(")) {
                	routes.add(parserRoute(line));  //si la chaîne de lecture commence par "route (", effectuez un filtrage des données pour ajouter à les routes
                } else throw new FichierAgglomerationSyntaxeException("Fichier corrompu. Une ligne de votre fichier "+chemin+" pas pu être parsée. Veuillez présenter un fichier CA valide.") ;
            }

            Ville [] villesParsees = villes.stream().map(ville -> new Ville(ville)).collect(Collectors.toList()).toArray(new Ville[villes.size()]);
            if(villesParsees.length == 0) throw new FichierAgglomerationSyntaxeException("Nous n'avons pu lire aucune ville de votre fichier "+chemin) ;
            if(villesParsees.length == 1) throw new NbVillesException("Nous n'avons pu lire qu'une  seule ville de votre fichier "+chemin+".\nUne agglomération comporte au moins deux villes.") ;
            Agglomeration agg = new Agglomeration(villesParsees);  //Initialise l'objet Agglom�ration � partir des informations lisibles dans le fichier
            
            
            for (String ecole : ecoles) {  //parcourt les villes si les caractères ne sont pas dans la liste des écoles, alors donc la ville est définie comme aucune école
                try {
                	if (!villes.contains(ecole)) {
                		sansErreurVilles = false ;
                	} else agg.getVille(ecole).setHasEcole(true);
                } catch(VilleException e) {}
            }
            
            for (String[] ville : routes) {  //parcourt les routes à ajouter
                if(ville.length != 2) {
                	sansErreurRoutes = false ;
                } else if(villes.contains(ville[0]) && villes.contains(ville[1])) {
                	agg.ajouterRoute(ville[0], ville[1]) ;
                } else {
                	sansErreurRoutes = false ;
            		sansErreurVilles = false ;
                }
            }
            br.close();
            
            if(!sansErreurVilles) System.err.println("Certaines villes ont été déclarées plusieurs fois dans votre fichier, ou des déclarations de villes n'ont pas pu être lues car votre fichier est corrompu.") ; ;
            if(!sansErreurRoutes) System.err.println("Il est possible que certaines routes n'aient pas pu être correctement ajoutées à l'agglomération car votre fichier est corrompu.") ;
            if(!sansErreurEcoles) System.err.println("Il est possible que certaines écoles n'aient pas pu être correctement ajoutées à l'agglomération car votre fichier est corrompu.") ;
            if(sansErreurVilles && sansErreurEcoles && sansErreurRoutes) System.out.println("Le fichier a été lu sans rencontrer une seule erreur.") ;
            Thread.sleep(3); //pour permettre aux messages de s'afficher dans le bon ordre
            System.out.println("\nL'agglomération suivante a "+((!sansErreurEcoles || !sansErreurRoutes || !sansErreurVilles)?"pourtant ":"")
            					+"pu être extraite de votre fichier "+chemin+" :") ;
            agg.afficheBilan();
            return agg;
            
        } catch (FileNotFoundException e) {
            //TODO traite exception si le fichier est introuvable après le chemin
            System.err.println("Le fichier \""+(chemin.length()==0?"vide":chemin)+"\" n'exite pas");
        } catch (IOException e) {
            System.err.println("Problème de lecture avec le fichier \""+(chemin.length()==0?"vide":chemin)+"\"");
		} catch(Exception e) {
			System.err.println(e) ; //FIXME 
			e.printStackTrace();
		}
        //catch (Exception e) {
            //System.err.println("Une des villes n'a pas pu être lue correctement lors de la lecture du fichier "+chemin+".s");
        //}
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
                if (v.getAEcole()) {
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
        System.out.println("Votre fichier "+chemin+" a bien été généré.") ;
    }

    /**
     * Methode effectue le filtrage des données des villes lors de la lecture du fichier pour se synchroniser avec l'objet Agglomeration
     * @param ligneVille La ligner à parser
     * @return nomVille la clé associée à la ville, une chaîne vide sinon
     */
     public static String parserVille(String ligneVille) {
    	 String nomVille = "" ;
    	 try { 
             nomVille = ligneVille.split("\\(")[1].split("\\).")[0];
    	 } catch(Exception e) { System.err.println("La ligne suivante n'a pas pu être lue correctement : "+ligneVille) ;}
         return nomVille;
	 }
	
    /**
     * Methode effectue le filtrage des données des routes lors de la lecture du fichier pour se synchroniser avec l'objet Agglomeration
     * @param ligneRoute La ligner à parser
     * @return donnees Le couple de routes à relier si elles ont pu être lues, un string vide sinon
     */
       public static String[] parserRoute(String ligneRoute) {
    	   String[] donnees = {} ;
           try {
        	   donnees = ligneRoute.split("\\(")[1].split("\\).")[0].split(",");
      	 } catch(Exception e) { System.err.println("La ligne suivante n'a pas pu être lue correctement : "+ligneRoute) ;}
           return donnees;  
        }

	
    /**
     * Method effectue le filtrage des données des ecoles lors de la lecture du fichier pour se synchroniser avec l'objet Agglomeration
     * @param ligneEcole La ligner à parser
     * @return nomVille la clé associée à la ville contenant une école, une chaîne vide si erreur
     */
     public static String parserEcole(String ligneEcole) {
    	 String nomVille = "" ;
    	 try { 
             nomVille = ligneEcole.split("\\(")[1].split("\\).")[0];
      	 } catch(Exception e) { System.err.println("La ligne suivante n'a pas pu être lue correctement : "+ligneEcole) ;}
         return nomVille;
	 }
}
