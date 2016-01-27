#Todo liste

##Collisions
* Améliorer le système de collision pour le rendre utilisable par tous les objets
  * Création d'une classe collision avec 2 types de collisions :
    * Collision entre player/monstre (4 lines) et les tiles (rectangle)
    * Collision entre player (4lines) et monstre (4lines)

##IA monstres
* Tester plusieurs comportements
* Mettre en place une fonction de pathfinding

##Item
* ~~Ajouter un item à ramasser sur la Map~~
* ~~Ramassage d'un item~~
* ~~Utilisation d'un item de l'inventaire~~
* ~~Affichage nombre d'item dans l'inventaire~~
* Tester les effets de differents items sur le personnage ( *cédric* en cours )
* Lors du ramassage d'un item, mettre en place le principe de petit inventaire qui s'ouvre.
* Mettre un coffre/une boite avec un principe de statut fermé => ouvert

##Weapon
* ~~Revoir les items : creation d'item en tant que classe abstraite + ajouter les classes filles~~
* ~~Creation d'une arme et ajout dans l'inventaire~~
* Mettre en place le système d'arme/inventaire expliqué plus bas
* Utilisation d'une arme (cac et gun)
* Les balles

##Pixel art
* ~~création des bordures de tile~~
* création d'un bloc pour les tests de collision (un rocher)
* création d'une arme au cac

##Map
* ~~création d'une map générée~~
* ajouter le 3ème niveau de tile à la génération (herbes hautes)
* ajouter des champignons par-ci par-là de façon aléatoire
* unifier la taille de la map (on la redéfinit dans main, dans level...)

##Inventaire
* Refonte graphique (*david*) (en cours)
* ~~ajout description d'un item (*david*)~~

#Explication du principe d'arme / inventaire
* Lorsqu'elle est ramassé, l'arme passe dans l'inventaire
* Si on double-clic/drag'n'drop l'arme, celle-ci est équipée (elle sort de l'inventaire)
* L'arme se retrouve donc dans la fiche de perso (équipement) dans un système ressemblant à l'inventaire
* Chaque joueur pourrait avoir une place pour une grande arme et deux petites dans son équipement
** ex : un fusil à pompe, un revolver et un couteau
* On peut changer rapidement d'arme équipée avec la roulette de souris
* Les clics permettent d'utiliser l'arme (clic gauche : tir principal, clic droit : tir secondaire si il y a)

Note : Il faudrait des classes ou des fichiers qui contiennent les différentes armes et leurs caractéristiques

En terme de code, il faut faire un test quand on double-clic sur une arme pour savoir si c'est bien une arme (ajout d'un attribut ou test genre "instance de quelle classe ?"), si oui elle va dans l'équipement (onEquipement = true), et depuis l'équipement, si elle est sélectionné => equipped = true (ou avec roulette).
