# JavaAvanceProjet : Construction d'ecoles

Version : 2.0 
Auteurs : Baptista Anthony, thanh-vo, Trividic Yann 
Dépôt GitHub du projet : ![https://github.com/thanhvo-uparis/JavaAvanceProjet](https://github.com/thanhvo-uparis/JavaAvanceProjet)
Date de debut : 12/10/2020



Pour pouvoir utiliser ce programme, veuillez suivre les instructions suivantes afin de le configurer correctement et d'en avoir une utilisation optimale.

**Configuration recommandee (developpe avec) :**

	IDE Eclipse 2020-09
	JAVA SE.12
	Windows 10 ou Ubuntu 18.04

**Protocole d'installation du programme** :
Ouvrez un terminal et rendez-vous dans le répertoire contenant l'archive JAR. Exécutez la commande `java -jar NOMDUJAR.jar`. Suivez les instructions affichées dans le terminal.

Si cela ne fonctionne pas, vous pouvez alternativement cloner le depot GitHub depuis Eclipse. Pour importer le projet, faites `File > Import... Projects from Git (with smart import) > Clone URI` et remplissez le champ avec ![https://github.com/thanhvo-uparis/JavaAvanceProjet](https://github.com/thanhvo-uparis/JavaAvanceProjet). Le projet s'installe ensuite facilement en sélectionnant la branche Partie2.



**Execution du programme**:


Maintenant que le programme est lancé il ne vous reste plus qu'à l'utiliser correctement. 


ETAPE 1: menu de chargement de l'agglomération.

Dans un premier temps, on vous présente une petite introduction et un menu qui vous demande de :

- 1) Charger une agglomération vous-même.

- 2) Charger une agglomération depuis un fichier

- 3) Générer une agglomération aléatoirement

- 0) Quitter le programme


**1) Charger une agglomération vous-même**
Tout d'abord, on vous demande le nombre de villes dans l'agglomération(2 villes minimum)
Ensuite, on laisse la possibilité si vous le souhaiter, de nommer vos villes.
Une fois les villes nommées, un récapitulatif de l'agglomération vous est proposé.
On vous demande maintenant si vous souhaitez une agglomération connexe.
Vous avez maintenant un menu qui vous permet d'ajouter des routes.
Vous devez choisir 2 villes à relier, si l'une d'elle n'existe pas vous aurez une erreur.
En choisissant 2 dans ce menu, vous confirmerez avoir rentré vos routes.Si vous souhaitez une agglomération connexe et qu'elle ne l'est pas, vous aurez une erreur.

Une fois fait, vous arriverez sur le menu de résolution des écoles (voir ETAPE 2)

**2) Charger une agglomération depuis un fichier**
On vous demande de rentrer le chemin vers un fichier CA pour créer l'agglomération.
Le fichier doit respecter l'écriture suivante:
Ajout d'une ville: ville(A).
Ajout d'une route: route(A,B).,
Une fois fait vous avez un récapitulatif de l'agglomération.
Vous arrivez ensuite sur le menu de résolution des écoles (voir ETAPE 2)

**3) Générer une agglomération aléatoirement**
On vous demande combien de villes vous souhaitez générer( ne mettez pas un trop grand nombre si vous souhaitez la résoudre manuellement; entre 2 et 500).
Une fois choisi, un récapitulatif de l'agglomération vous est fait.
on vous présente ensuite le menu de résolution des écoles( voir ETAPE 2).


ETAPE 2 : Résolution des écoles

Vous avez ici le menu qui vous permet de résoudre le problème. Vous avez 4 choix possibles.:

- 1) Résoudre manuellement la position de vos écoles: 

Vous arrivez sur un menu qui vous permet d'ajouter une école, d'en retirer une ou alors de valider les modifications
Si vous voulez ajouter une école, rentrez le nom de la ville dans laquelle ajouter une école, si la ville possède deja une école elle ne sera pas ajoutée.
Si vous voulez retirer une école, rentrez le nom de l'ecole dans laquelle retirer une école, si la ville ne possède pas d'école, elle ne sera pas retirée. Si le retrait de cette école ne respecte pas la contrainte d'accessibilité, alors elle ne sera pas retiree non plus.
A la fin de chaque action, les villes possédant des écoles seront affichées.

Une fois que vous êtes satisfait vous n'avez plus qu'à choisir 3 pour finir les modifications.
On vous renverra sur le menu précédent.
Faites 0 si vous souhaitez quitter le programme.

- 2) Résoudre automatiquement le problème:

Vous arrivez sur le menu principal Algorithmes qui vous laisse 7 possibilités:

			1- Appliquer l'algorithme de votre choix sur l'agglomération:
				Vous avez le choix de l'algorithme de votre choix à utiliser. On vous sort le résultat et vous rement sur le le menu principal Algorithmes.
				
			2- Appliquer notre meilleur Algorithme sur votre agglomération: 
				Va utiliser le meilleur Algorithme possible pour votre configuration. Vous renvoie ensuite sur le menu Principal Algorithmes.
				
			3- Comparer les différents résultats de nos différents algorithmes sur votre agglomération.
				Vous affiche une comparaison de nos différents algorithmes (score + temps ). Vous renvoie ensuite sur le menu principal Algorithmes.
				
			4- Permet de voir des tests sur la complexité des algorithmes disponibles.
			
			5- Sauvegarder votre agglomération dans un fichier CA 
				Permet de sauvegarder la configuration de l'agglomération actuelle. Vous demande le chemin vers le où sauvegarder votre fichier.
				Vous renvoie ensuite sur le menu Principal Algorithmes.
				
		    6- Retourner au menu de chargement d'agglomération.
		   
		    0- Permet de quitter le programme.
		   	
- 3) Sauvegarder votre agglomération dans un fichier CA.

Permet de sauvegarder votre agglomération dans un fichier CA. Vous demande le chemin où sauvegarder le fichier.

- 0) Quitter le programme.

Vous permet de quitter le programme.
			


**Quelques informations contextuelles** :


Il s'agit d'un probleme de théorie des graphes.
Dans le cadre d'une politique ambitieuse concernant l'éducation, un élu souhaite construire de nouvelles ecoles, modernes, dans la communaute d'agglomeration dont il est responsable. Durant la campagne electorale, il a promis deux choses :

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