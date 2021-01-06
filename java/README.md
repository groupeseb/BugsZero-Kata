L'ajout des gamer et des questions static ==> rendre dynamique via l'externalisation des variables (BDD par exemple)

ArrayList players = new ArrayList() ==> List accepte les doublons, donc il faut utiliser Set Set<String> players = new LinkedHashSet<>();

Vu que nous avons la taille des elements a inserer dans notre liste
il n'est plus besoin d'utiliser LinkedList, c'est mieux d'uliser ArrayList avec la taille exacte
Exemple : List<String> popQuestions = new ArrayList<>(50);

popQuestions.addLast() ==> avec l'utilisation ArrayList ça doit etre popQuestions.add(objet)
meme chose pour la suppression popQuestions.removeFirst() ==> popQuestions.remove(index)

Tous les chaines de caractères doivent étre déclarer comme constantes : exemple : private static final String POP = "Pop"; return POP au lieu de return "Pop";

Comparaison de valeur pas de référence : Utiliser le méthode Equals au lieu de l'opération "==" : exemple if (currentCategory() == "Pop") ==> if (POP.equals(currentCategory()))

C'est mieux d'utiliser les log (Log4j par exemple) au lieux de System.out.println()

return !(purses[currentPlayer] == 6); ==> return purses[currentPlayer] != 6;

boolean winner = didPlayerWin(); return winner; ==> tous simplement return  didPlayerWin();

Le bout de code :
    boolean winner = didPlayerWin();
    currentPlayer++;
    if (currentPlayer == players.size()) currentPlayer = 0;
    return winner;

A remplacer par :
    currentPlayer++;
    if (currentPlayer == players.size()) currentPlayer = 0;
    return didPlayerWin();