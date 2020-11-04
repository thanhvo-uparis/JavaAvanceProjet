# JavaAvanceProjet
Projet, date début 12/10/2020, Construction d'écoles 

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