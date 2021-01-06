# Commentaires

## Bugs

### 1. Crash du jeu au démarrage
En essayant de faire tourner le jeu avec plus de 6 joueurs, celui ci plantera du fait de l'initialisation en dur de la taille des array purses et places.
Solution possible: 
	- (quick and dirty) Ajouter une fonction d'initialisation du jeu dans la classe Game, demandant le nombre de joueurs.
	- (un peu mieux) Créer une classe joueur, qui contiendra les informations d'un joueur. Les arrays pourront être supprimées du jeu, et la liste des joueurs aujourd'hui en String pourra évoluer vers une liste de Joueur qui connaissent chacun leur état.
	
	
### 2. Il manque une protection contre le départ du jeu si le nombre de joueur est strictement inférieur à 2
IndexOutOfBound dans la méthode roll	

### 3. On ne devrait pas pouvoir faire un 6 avec le dé ?
random.nextInt(6) + 1 (le nextInt exclu la borne fournie donc entre [0 et 5] + 1 -> 1 et 6 )


## Refactoring

### nextPlayer 
Il manque une méthode 'nextPlayer()' ou quelque chose comme ça, pour encapsuler la mécanique permettant de passer d'un joueur à l'autre.
 
### cases
Une map pour les 'places' rendrait plus lisible et faciliterait les évolutions du plateau de jeu. En utilisant player.currentPlace pour aller chercher dans une map <Integer, Place> par exemple, on ouvre la possibilité aux évolutions de la complexité des cases par la suite (pièges, bonus etc)

### purses
Serait stocké en tant qu'information du joueur dans la classe Joueur. Sinon en tant que Map pour éviter de se tromper d'index ou d'avoir des soucis d' "outOfBound".


### gameLoop
Pas ma spécialité les jeux, mais je séparerai bien la "gameLoop" du fonctionnement du jeu. La loop donnerait les ordres au jeu, qui lui ne contiendrait que les règles d'un tour pour un joueur. Par exemple, la loop indiquerait à qui est le tour, vérifierait si un joueur à gagné, etc.


## Tests
Pas eu le temps de finir ça. Je ne connais pas Approvals.