L'analyse reste succinte.
Le premier mot qui me vient à l'esprit est spaghettis.
Je ne suis pas parvenu à comprendre distinctement la logique mise en oeuvre.
Le développeur se base sur des index dans des tableaux pour garder en mémoire différentes données
liées au joueur.

Les conditions me semblent curieuses.


Personnellement, je modéliserai le parcours d'un joueur
1) je donne le nombre de joueurs (max 8)
2) je saisis le nom des joueurs
3) le joueur lance le dé
4) le joueur avance de n cases
5) le joueur répond à la question
6) si la réponse est ok alors il a un point
sinon rien.
7) Joueur suivant et on recommence.

Je proposerai de créer des objets pertinents :
peut-être 
- un objet Joueur, 
- un objet Question, 
- un objet Plateau
- un objet Dé
...

Je créerais des classes dédiées :
un service lancer de dé
un service répondre à une question
un service avancer sur le plateau
un service création des joueurs
...










