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
*ajout d'un "sound" au moment o� on tire (premier test sonore)
*ajout de "sounds" pour les ennemis (WALK et DYING pour Blob, WALK pour Motha)
*test d'ajout d'une musique
##Gameplay
*ajout des "damage" des armes / balles
*si les PV des ennemis atteignent 0, ils meurent
*ajout de la fonctionnalit� "grimper une �chelle"
##Inventaire
*ajout des "items" de level (grass, mushroom, ladder)
*ramassage d'items positionn�s sur la carte
*affichage de l'inventaire et des items ramass�s
*utilisation ou non d'items dans l'inventaire
*affichage du cadre de s�lection de l'item

#Version 0.0.5 :

##Collision
*les collisions sont faites sur 3 points (2 extremit�s + milieu) au lieu d'un (milieu)
##Cam�ra
*ajout du scrolling de la cam�ra + reset du scrolling pour l'inventaire (reste en place)
##Comp�tences
*ajout de la classe Skill
*test de la comp�tence Heal : soigne un joueur
*test de la comp�tence Bolt : inflige des d�gats � une entit�

#Version 0.0.5 (TODO) :
*~~revoir les collisions~~
*effets de l'utilisation des items
*hud
*utilisation des comp�tences (passive pour commencer)
*faire en sorte que l'on puisse �tre touch� par les ennemis