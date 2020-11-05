# JavaAvanceProjet : Construction d'ecoles

Version : 1.0 
Auteurs : Baptista Anthony, thanh-vo, Trividic Yann 
Dépôt GitHub du projet : ![https://github.com/thanhvo-uparis/JavaAvanceProjet](https://github.com/thanhvo-uparis/JavaAvanceProjet)
Date de debut : 12/10/2020




Pour pouvoir utiliser ce programme, veuillez suivre les instructions suivantes afin de le configurer correctement et d'en avoir une utilisation optimale.

**Configuration recommandee (developpe avec) :**

	IDE Eclipse 2020-09
	JAVA SE.12
	Windows 10 ou Ubuntu 18.04

**Protocole d'installation du programme** :
Une fois la configuration necessaire installee, vous devez telecharger le projet. Une fois la configuration necessaire installee, vous devez telecharger le projet. Creez un nouveau Java Project du nom de votre choix (`File > New Project... > Java > Java Project`). Une fois le projet cree, vous devez importer l'archive `JAR` dans celui-ci. Clic droit sur le projet `Import... > General > Archive File` selectionnez l'archive, puis votre projet. Vous pouvez maintenant executer l'un des programmes.

Si cela ne fonctionne pas, vous pouvez alternativement cloner le depot GitHub depuis Eclipse. Pour importer le projet, faites `File > Import... Projects from Git (with smart import) > Clone URI` et remplissez le champ avec ![https://github.com/thanhvo-uparis/JavaAvanceProjet](https://github.com/thanhvo-uparis/JavaAvanceProjet). Le projet s'installe ensuite facilement.


**Protocole d'installation du programme :**

Pour comprendre la structure du programme, référez-vous au document `class-diagram.svg`.

Le projet etant charge dans Eclipse, il ne reste plus qu'a le lancer via le bouton `Run`.
Vous aurez ici le choix entre deux fonctions programme : `Test` ou `Main`.

* __La classe__  `Test` permet de tester les differents cas de figure pouvant se presenter a l'execution du programme. Elle permet de passer en argument `nbVilles` (le nombre de villes constituant l'Agglomeration a tester) et `nbOperations` (le nombre d'opérations ajout ou retraits d'école qui sera effectué).
Le programme relie aleatoirement des Ville jusqu'a ce que l'agglomeration soit connexe.
Le programme execute ensuite `nbOperations` operations sur ces villes (ajout d'ecole oou retrait d'ecole).
Toutes les operations sont affichees pour permettre de comprendre l'execution du programme pas a pas.
Si vous voulez executer la classe `Test`, il faut parametrer les deux arguments : clic droit sur `Test.java` puis `Run As... > Run Configurations...`. Un menu s'ouvre : aller dans l'onglet `Arguments` et rentrez dans le champ `Program arguments` deux entiers naturels : le premier est `nbVilles` et le second `nbOperations`. Ils doivent être séparés par une espace.

* __Pour la classe__  `Main`, cliquez sur `Run As... > Java Application` puis selectionnez ensuite `Main`.


**Execution du programme**:


Maintenant que le programme est lance, il ne vous reste plus qu'a l'utiliser correctement. 


Voici un bref descriptif du fonctionnement de la classe `Main` :

Dans un premier temps rentrez le nombres de villes dans l'agglomeration (attention, ce nombre doit etre compris entre 1 et 26).
Une fois le nombre de villes rentrees, le programme vous donnera la possibilite de relier les villes via des routes.

Vous avez un menu permettant soit d'ajouter une route soit de terminer l'ajout de routes soit de quitter le programme.
Vous devrez donc saisir une premiere ville (elle doit etre dans l'agglomeration) puis une deuxieme ville (elle doit aussi etre valide).

Ces deux routes seront alors reliees entre elles.
Pour pouvoir valider que l'ajout de routes est termine, toutes les villes doivent etre reliees entre elles.

Maintenant que toutes les routes ont ete ajoutees, vous arrivez sur un menu qui vous permet d'ajouter ou retirer une ecole ou de terminer.
Si vous voulez ajouter une ecole, rentrez le nom de la ville dans laquelle ajouter une ecole, si la ville possede deja une ecole elle ne sera pas ajoutee.
Si vous voulez retirer une ecole, rentrez le nom de l'ecole dans laquelle retirer une ecole, si la ville ne possede pas d'ecole, elle ne sera pas retiree. Si le retrait de cette ecole ne respecte pas la contrainte d'accessibilite, alors elle ne sera pas retiree non plus.
A la fin de chaque action, les villes possedant des ecoles seront affichees.




Une fois que vous etes satisfait vous n'avez plus qu'a terminer le programme.




**Quelques informations contextuelles** :


Il s'agit d'un probleme de theorie des graphes.
Dans le cadre d'une politique ambitieuse concernant l'education, un elu souhaite construire de nouvelles ecoles, modernes, dans la communaute d'agglomeration dont il est responsable. Durant la campagne electorale, il a promis deux choses :

- Accessibilite : Chaque ville doit posseder son ecole, ou etre directement reliee a une ville qui possede une ecole.

- Economie : Le cout du projet doit etre le plus bas possible, ce qui signifie que le nombre d'ecoles a construire doit etre le plus petit possible.

Il nous demande de l'aider en developpant un logiciel qui permet : 
1. de representer les villes d'une communaute d'agglomeration, et les routes qui les relient ;
2. de simuler la construction d'ecole dans les villes de la communaute ;
3. de calculer le coût d'une solution (le nombre d'ecole), et le minimiser.

- Toutes les routes sont a double-sens et de meme longueur.
- Le cout est estime en fonction du nombre d'ecoles a construire.


Ce probleme est un probleme de theorie des graphes. Il s'agit de representer la communaute d'agglomeration avec un graphe dont les sommets seraient les villes et les arêtes seraient les routes qui relient les villes entre-elles.

Ainsi, le probleme peut se traduire de la maniere suivante :
- Les villes avec ecoles sont representees avec sommet pondere a `false`.
- Les villes sans ecole sont representees avec sommet pondere a `true`.
- Chaque sommet doit etre pondere a `true` ou avoir l'un de ses voisins pondere a `true`.
- Le nombre de sommets pondere a `true` doit etre minimal.
- La somme des ponderation est egale au nombre d'ecoles a construire.


Les questions auxquelles il faut repondre :
- Quelle est la meilleure maniere de representer ce graphe ? (matrice d'adjacence, liste d'aretes, liste d'adjacence ?)
- Quel algorithme appliquer pour ponderer de maniere optimale le graphe ? Potentiellement ranger les sommets par degres decroissants et ponderer ainsi ? 