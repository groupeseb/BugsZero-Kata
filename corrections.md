
# Game.java

- Très procédural, il faudrait plutot passer par de l'objet pour les "entités" et la logique du jeu

- utiliser un type pour ArrayList et LinkedList
- Plutot utiliser des List/Set pour les "places" (nombre de joueurs ?)
    + le nombre de joueur est fixé dans le code (6 max), sans préciser le minimum non plus
    + il faudrait utiliser une configuration (.properties) ou au moins mettre des constantes
- Si on avait un objet Joueur on aurait pas besoin d'un autre tableau "purses". Idem pour un objet "Board" qui contiendrait la liste des joueurs et leur position.
- l'utilisation de primitifs (int, boolean, []) n'est pas recommandé
- Je vois pas l'intérêt du LinkedList à moins de vouloir garder l'ordre (la logique voudrait que les questions soient aléatoires)


- il n'y a pas de type pour la question. Dans le code il s'agirait d'un String.
    + il faudrait plutot une liste de Question (objet) qui aurait un thème et un intitulé, pour ne pas avoir à gérer 4 listes pour chaque thème
- utilisation d'un int pour "currentPlayer" à cause des tableaux
    + dans l'ensemble, on perd la notion de "quoi", important en POO, en utilisant des primitfs et des tableaux
- `isGettingOutOfPenaltyBox` n'est pas clair sur "qui"/"quoi" n'est plus pénalisé
    + le nom n'est d'ailleurs pas clair, le joueur est sorti ou doit sortir ?

- `Game()` ne devrait pas initialiser les questions en supposant que les questions soient "stockées" ailleurs, et pas générées comme ça :
    + Cette façon rendrait lourd la construction du jeu, et ne permettrait pas de réutiliser la logique de récupération des questions => il faut passer par un service découplé pour récupérer les questions au fur et à mesure, ou plusieurs en une fois.
    + passer par une méthode spécifique permetterait une meilleure testabilité (on teste la récupération et pas le jeu en lui même) et maintenabilité (on peut faire varier la récupération de question : fichier, bdd, personnalisée par les joueurs...)
- `addLast` est équivalent à `add`, mais pour la clareté de l'ajout (à la fin), pourrait être laissé comme ça.
- La façon d'initialiser une question n'est pas uniforme.
    + Il faudrait passer par une méthode en précisant le thème et le numéro.
    + L'idéal étant de passer par un constructeur d'un objet "Question" qui aurait en paramètre le thème et l'intitulé de la question, qui serait donc construit dans un service à part.

- `createRockQuestion()` comme dit plus haut, ne devrait pas être dans là (dans le Game) mais dans une classe à part (factory ou constructeur spécifique)

- `isPlayable()` n'est pas utilisée, donc on ne vérifie pas le nombre de joueurs

- `add()`
    + Le nom n'est pas clair sur ce qui est ajouté
    + on se retrouve avec une difficulté pour gérer un joueur car le "lien" entre les places/purses/penaltyBox est l'indice du tablaeu, plutot qu'un objet Joueur, un état (libre/pénalisé) et un compteur individuel de pièces acquises, d'autant plus que le joueur en lui même n'est même pas stocké avec un indice pour rester cohérent
    + l'utilisation de `howManyPlayers()` est dépendant du nombre actuel de joueurs pour le placer dans le tableau
        - Risque de OutOfBoundException
        - On ne devrait pas avoir à connaitre le nombre de joueur courant pour stocker un joueur, mais plutot pour vérifier combien il en a pour ne pas dépasser la limite
    + Les printLn, si nécessaires, devraient probablement être à l'extérieur de la méthode, pour limiter l'affichage à la logique (architecture MVC notamment)
    + du coup le second message, en plus d'une faute, serait écrit à chaque fois que l'on ajoute une personne, plutôt que de le faire à la fin (ou via une compteur, par exemple "X/8" à chaque "was added")
    + le return est inutile. Si l'ajout d'un joueur ne réussi pas, on ne le saura pas.
        - il faudrait retourner le résultat du `players.add()` ou un booléen une fois que tout est fait, ou une exception si le comportement l'ajout d'un joueur n'est pas normal, notamment pour gérer le nombre de joueurs maximum, si la vérification n'est pas faite avant.

- `roll()`
    + le nom n'est pas explicite. On ne lance pas un dès, on fait une action sur un joueur par rapport à un lancé de dès déjà fait.
    + la méthode ne devrait pas gérer autant de choses : affichage console, sortie ou entrée dans la zone de pénalité, bouger le joueur et poser la question

- `movePlayerAndAskQuestion`

- `askQuestion`
    + utiliser `equals` pour une équivalence

- `currentCategory`
    + possibilité de simplifier les if en les regroupant

- `wasCorrectlyAnswered`
    + mettre en commun la partie "sortie de la zone pénalité" et la partie dans le `else`


- `wrongAnswer`

- `didPlayerWin`

# GameRunner.java

- `notAWinner` n'est pas utile en tant que propriété interne au runner

- Le random donné en paramètre n'est pas idéal car il englobe le fait de "jouer" alors qu'il ne concerne que le lancé de dès

- ne simule pas bien un lancé de dé (max 6)


# Améliorations générales
- faire du code review régulier (donc avec push et PR régulier) pour éviter d'en arriver à un tel code
- Ne pas écrire directement dans la console dans les appels de méthodes (couplage logique/vue)
- Passer par une BDD pour stocker les données et les questions jouées (pour ne pas répéter des question, style "Qui veut gagner des Millions")
- Eventuellement passer par de l'héritage pour les questions, au lieu d'avoir un thème, il s'agirait d'une sous classe qui hérite de Question (SuperClass), ce qui permettrait d'avoir un comportement différent poru chaque question ou de récupérer facilement toutes les questions d'un même thème