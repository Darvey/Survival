Version 0.0.1 :
*portage Slick

[...]

#Version 0.0.4 :

##Librairies
*utilisation de la librairie jogg-0.0.7.jar
*utilisation de la librairie jorbis-0.0.15.jar
##Bugs
*correction d'un bug dans la direction des balles
*correction d'un bug de hitbox pour les monstres 
##Sons
*ajout d'un "sound" au moment où on tire (premier test sonore)
*ajout de "sounds" pour les ennemis (WALK et DYING pour Blob, WALK pour Motha)
*test d'ajout d'une musique
##Gameplay
*ajout des "damage" des armes / balles
*si les PV des ennemis atteignent 0, ils meurent
*ajout de la fonctionnalité "grimper une échelle"
##Inventaire
*ajout des "items" de level (grass, mushroom, ladder)
*ramassage d'items positionnés sur la carte
*affichage de l'inventaire et des items ramassés
*utilisation ou non d'items dans l'inventaire
*affichage du cadre de sélection de l'item

#Version 0.0.5 :

##Collision
*les collisions sont faites sur 3 points (2 extremités + milieu) au lieu d'un (milieu)
##Caméra
*ajout du scrolling de la caméra + reset du scrolling pour l'inventaire (reste en place)
##Compétences
*ajout de la classe Skill
*test de la compétence Heal : soigne un joueur
*test de la compétence Bolt : inflige des dégats à une entité

#Version 0.0.5 (TODO) :
*~~revoir les collisions~~
*effets de l'utilisation des items
*hud
*utilisation des compétences (passive pour commencer)
*faire en sorte que l'on puisse être touché par les ennemis