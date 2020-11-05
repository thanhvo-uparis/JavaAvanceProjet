# JavaAvanceProjet

Programme de construction  d'�coles :

Pour pouvoir utiliser ce programme, veuillez suivre les instructions suivantes afin de le configurer correctement et d'en avoir une utilisation optimale.

Configuration recommand�e (Fabriqu� avec):

IDE Eclipse 2020-09
JAVA SE.12
Windows 10 

Protocole d'installation du programme:
Une fois la configuration n�cessaire install�e, vous devez t�l�charger le projet.
Ensuite, ouvrez le projet directement sur eclipse(File > Open projects From File Systeme puis choisissez le projet).

Protocole de lancement du programme:
Le projet �tant charg� dans Eclipse, il ne reste plus qu'� le lancer via le bouton Run.

Execution du programme:

Maintenant que le programme est lanc�, il ne vous reste plus qu'� l'utiliser correctement. 

Voici un bref descriptif de son fonctionnement:

Dans un premier temps rentrez le nombres de villes dans l'agglom�ration(attention, ce nombre doit �tre compris entre 1 et 26).
Une fois le nombre de villes rntr�es on vous donnera la possibilit� de relier les villes via des routes.

Vous avez un menu permettant soit d'ajouter une route soit de terminer l'ajout de routes soit de quitter le programme.
Vous devrez donc saisir une premiere ville( elle doit etre valide)puis une deuxieme ville (elle doit aussi etre valide).
Ces deux routes seront alors reli�es entre elles.
Pour pouvoir valider que l'ajout de routes est termin�, toutes les villes doivent �tre reli�es entre elles.

Maintenant que toutes les routes ont �t� ajout�es, vous arrivez sur un menu qui vous permet d'ajouter ou retirer une �cole ou de terminer.
Si vous ajoutez une ecole, rentrez le nom de la ville dans laquelle rajouter une �cole, si la ville poss�de deja une �cole elle ne sera pas ajout�e.
Si vous retirez une �cole, rentrez le nom de l'�cole dans laquelle retirer une �cole, si la ville ne poss�de pas d'�cole, elle ne sera pas retir�e. Si le retrait de cette �cole ne respecte pas la contrainte d'accessibilit�, alors elle ne sera pas retir�e non plus.
A la fin de chaque action, les villes poss�dant des �coles seront affich�es.

Une fois que vous �tes satisfait vous n'avez plus qu'a terminer le programme.


Version: 1.0 

Auteurs:

@Baptista anthony
@thanh-vo
@Trividic Yann 

GitHub du projet:

https://github.com/thanhvo-uparis/JavaAvanceProjet





/*Projet, date début 12/10/2020, Construction d'écoles 

Problème de théorie des graphes.
Dans le cadre d'une politique ambitieuse concernant l'éducation, un élu souhaite construire de nouvelles écoles, modernes, dans la communauté d'agglomération dont il est responsable. Durant la campagne électorale, il a promis deux choses :

- Accessibilité : Chaque ville doit posséder son école, ou être directement reliée à une ville qui possède une école.

- Economie : Le coût du projet doit être le plus bas possible, ce qui signifie que le nombre d'écoles à construire doit être le plus petit possible.

Il nous demande de l'aider en développant un logiciel qui permet : 
1. de représenter les villes d'une communauté d'agglomération, et les routes qui les relient ;
2. de simuler la construction d'école dans les villes de la communauté ;
3. de calculer le coût d'une solution (le nombre d'école), et le minimiser.

- Toutes les routes sont à double-sens et de même longueur.
- Le coût est estimé en fonction du nombre d'écoles à construire.




Ce problème est un problème de théorie des graphes. Il s'agit de représenter la communauté d'agglomération avec un graphe dont les sommets seraient les villes et les arêtes seraient les routes qui relient les villes entre-elles.

Ainsi, le problème peut se traduire de la manière suivante :
- Les villes avec écoles sont représentées avec sommet pondéré à 0.
- Les villes sans école sont représentées avec sommet pondéré à 1.
- Chaque sommet doit être pondéré à 1 ou avoir l'un de ses voisins pondéré à 1.
- Le nombre de sommets pondéré à 1 doit être minimal.
- La somme des pondération est égale au nombre d'écoles à construire.


Les questions auxquelles il faut répondre :
- Quelle est la meilleure manière de représenter ce graphe ? (matrice d'adjacence, liste d'arêtes, liste d'adjacence ?)
- Quel algorithme appliquer pour pondérer de manière optimale le graphe ? Potentiellement ranger les sommets par degrés décroissants et pondérer ainsi ? 
*/