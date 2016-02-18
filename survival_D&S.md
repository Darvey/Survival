#Présentation du jeu

#Principe du jeu

#Gameplay

A redéfinir :
* vue du haut
* vue de côté

Avantage de la vue de côté :
* Combats plus dynamiques
* Graphisme et animations plus simples
* Compétences plus intéressantes
* Plus de richesse dans le gameplay
* Plus de visibilité par rapport aux hauteurs

Inconvénients de la vue de côté :
* Perte du côté exploration
=> Essayer de créer une exploration propre à la vue de côté
=> Créer des niveaux complexes
* Coop plus bordélique
* "Impasses" gênantes en PVP

##Touches

De base les touches utilisées sont :
* Q : Déplacement à gauche
* D : Déplacement à droite
* Z : Saut
* S : S'accroupir ?
* E : Touche action (ramasser des objets, ouvrir...)
* R : Recharger l'arme
* 1, 2, 3, 4... : Compétences actives
* Clic gauche : Tir principal
* Clic droite : Tir secondaire
* Molette : Changement d'arme rapide

#Background


#Personnage

##Création

Lors de la création du personnage on choisit son prénom/nom, son apparence, sa classe de base et on attribue des points dans les caractéristiques primaires. Les caractéristiques secondaires sont calculées en fonction des caractéristiques primaires et de la classe.

##Classes

A la création, le personnage a une classe de base et il pourra se spécialiser un partir d'un certain niveau.

###Classes de base

Les classes de base sont :
* Soldat : Apte au combat, il a des bonus dans l'utilisation des armes et possède des aptitude pour infliger des dégats, se protéger...
 
* Savant : Apte pour soigner, créer...

* Roublard : Doué pour se déplacer, se cacher...

###Arbres de talent et spécialisation de classe


###Spécialisation de classe

L'accès à au rang ??? (au niveau ???) de l'arbre de talent permet la spécialisation du personnage.

Le soldat peut se spécialiser en :
* Fantassin : Expert en armes légères 
* Chasseur : Expert de la survie en environnement hostile, en armes à longue portée
* Artilleur : Expert en armes lourdes

Le savant peut se spécialiser en :
* Médecin : Expert pour se soigner ou soigner les autres joueurs
* Ingénieur : Expert dans la création d'entités (tourelles, murs protecteurs...) pour l'aider 
* Alchimiste : Expert en élément (a accès à des sorts d'éléments secondaires)

Le roublard peut se spécialiser en : 
* Maitre lame : Expert en arme au corps à corps
* Trappeur : Expert en explosif, en piège
* Assassin : Expert en déplacement, camouflage et en "burst"


On peut donc soulever plusieurs axes principaux :
* Dégats (DPS) : Fantassin, Chasseur, Maitre lame
* Dégats (Burst) : Artilleur, Alchimiste, Assasin
* Utilitaires : Médecin, Ingénieur, Trappeur

##Caractéristiques

###Car. primaires
* Force (STR) : Force physique du personnage
* Agilité (AGI) : Agilité du corps, des mouvements
* Dextérité (DEX) : Dextérité avec les mains, les doigts, la vue, précision
* Constitution (CON) : Constitution du corps, résistance, endurance
* Intelligence (INT) : Intelligence innée, capacité de réflexion, d'apprentissag

###Car. secondaires
* points de vie [con]
* esquive [agi]
* vitesse de déplacement [agi]
* endurance (temps pour sprinter) [con]
* mod. vitesse des armes lourdes au corps à corps (massue) [str, agi]
* mod. vitesse des armes légères au corps à corps (poignard) [agi, dex]
* mod. vitesse des armes à distance (arc) [dex]
* mod. précision des armes lourdes au corps à corps [str, agi]
* mod. précision des armes légères au corps à corps [dex, agi]
* mod. précision des armes à distance [dex]
* mod. précision des armes à feu [dex]
* mod. dégat des armes lourdes au corps à corps [str]
* mod. dégat des armes légères au corps à corps [str, dex]
* mod. dégat des armes à distance [dex]
* poids que l'on peut porter [str]
* discretion [agi]
* resistance à la maladie [con]
* resistance au poison [con]
* resistance à la fatigue [con]
* resistance psychique [int]
* resistance aux dégats perforants [-]
* resistance aux dégats tranchants [-]
* resistance aux dégats contondants [-]
* connaissance [int]
* apprentissage [int]

###Compétences passives

Les compétences passives se présentent sous la forme de bonus qui perdurent.
 
Elles sont accessibles via l'arbre de talent (compétences de classes et de spécialisations) et de dévotion (compétences de dévotion).

* ex : Oeil du chasseur (Comp. de spécialisation du chasseur) - Augmente de 10% la précision avec les armes à feu et à distance.
* ex : Corps de pierre (Comp. de dévotion à l'élément terre) - Augmente les PV de 5% et la resistance aux armes perforantes et tranchantes de 10%.
* ex : Hautes Etudes (Comp. de classe du savant) - Augmente la connaissance et l'apprentissage de 20%.

##Compétences actives

Les compétences actives sont des sorts activables grâce aux raccourcis. Une fois lancée, elles sont soumis à un cooldown.

Elles sont accessibles via l'arbre de talent (compétences de classes et de spécialisations) et de dévotion (compétences de dévotion).

* ex : Recherche du point faible (Comp. de spécialisation de l'assassin) - La prochaine attaque sera un coup critique qui infligera 250% des dégats.
* ex : Pose d'une tourelle de niveau I (Comp. de spécialisation de l'ingénieur) - Déploie une tourelle qui attaque automatiquement les ennemis proches.
* ex : Camouflage (Comp. de classe du roublard) - Devient invisible pendant 3 secondes.
* ex : Serrage de fesse (Comp. de classe du soldat) - Prévient 25% des dégats pendant 5 secondes.
* ex : Pluie revigorante (Comp. de dévotion à l'élément eau) - Fait tomber une pluie qui soigne les alliés dans une zone autour du lanceur de 10pv chaque seconde.
* ex : Projectiles enflammés (Comp. de dévotion à l'élément feu) - Pendant 4 secondes, les projectiles deviennent enflammés et infligent 5 dégats de feu supplémentaires.

##Arbre de talent

DE FACON COMPLEXE : Au début, le joueur peut mettre des points dans des compétences d'un premier arbre de talent (1 point par niveau ?). Ces compétences sont classées par rang. Les 3 premiers niveaux, il peut mettre des points dans les compétences de rang 1 associées à sa classe. Et ce jusqu'au rang 3 (niveau 9). Au niveau 10, le joueur doit choisir une spécialisation qui lui ouvre un nouvel arbre de talent : l'arbre de spécialisation.
A partir du niveau 11, le joueur va donc mettre ses points dans l'arbre de spécialisation associé à sa spécialité.

OU PLUS SIMPLEMENT : le joueur commence directement avec sa spécialisation, la classe de base est alors simplement indicative et pourrait permettre d'avoir certaines compétences en commun (ex : "Hautes Etudes" pour les 3 spécialisations de la classe Savant).

A partir du moment où le joueur est dévoué (dévotion) à un élément, il a accès à un nouvel arbre : l'arbre de dévotion.

##Devotion et éléments

###La dévotion et ses axes

Dans un premier temps, le joueur doit se dévouer à un élément en accomplissant un temple. Une fois qu'il est dévoué, il le reste même en cas de mort du personnage. Cette dévotion lui donne accès à un arbre de dévotion qui contient des compétences associées à l'élément auquel il est dévoué. Si la spécialisation est rapportée au physique, au réel, la dévotion est liée à la magie, au surnaturel.

Les éléments principaux sont :
* Feu : Axé sur les dégats (dps)
* Terre : Axé sur la défense
* Eau : Axé sur l'utilitaire
* Air : Axé sur les dégats (burst)
* Aether : Non jouable

Ces axes sont indicatifs et sont adaptés en fonction de la classe du personnage. Les fantassins dévoués au feu ou à la terre seront toujours axés sur les dégats, mais les premiers plus que les seconds. 

Les éléments secondaires sont : 
* Boue (eau+terre) : Axé sur la défense, les ralentissements, les boucliers, les changements de statut, la maladie...
* Magma (terre+feu) : Axé sur les auras (dégats, défenses), sur la réduction des résistances...
* Vapeur (eau+feu) : Axé sur les dégats de zone, sur les compétences à double effet (malus pour ennemis, bonus pour alliés)
* Electricité (feu+air) : Axé sur les gros dégats bien gras sur cible unique
* Metal (terre+air) : Axé sur les dégats et la défense (renvoi des dégats)
* Glace (eau+air) : Axé sur des dégats directs suivis par des ralentissements, des changements de statuts...

###Plusieurs exemples

L'association Spécialisation / Dévotion permettra donc de personnaliser au mieux son personnage et de le rendre quasiment unique. Un médecin du feu va créer un bouclier qui soigne et inflige des dégats aux ennemis alentours alors que celui dévoué à la terre va créer une zone qui soignera et augmentera les PV de ses alliés. Un fantassin de la terre pourra se revêtir d'une écorce qui le protegera contre les dégats et en fera un vrai tank, alors que celui de l'eau fera en sorte que ses balles ralentissent les ennemis voire les immobilisent. Un alchimiste pourra créer des élémentaux associés à son élément de prédilection et un ingénieur pourra renforcer ses créations. L'assassin de l'air va augmenter drastiquement son burst en invoquant des éclairs à chaque coup de poignard alors que celui du feu laissera son ennemis mourrir à petit feu (ohoh) après lui avoir infliger des brûlures.

Pour accentuer encore plus la personnalisation, on pourrait, dans l'arbre de dévotion, avoir 3 branches principales associées aux éléments secondaires.

Exemple d'application pour l'assassin :
* Boue : Ses coups vont innoculer une maladie à l'ennemis qui va lui apporter des debuffs
* Magma : Ses coups vont rendre ardent sa cible qui va infliger des dégats sous la forme d'une aura à ses alliés
* Metal : Ses coups critiques font qu'il renvoit tous les dégats à sa cible pendant x secondes
* Electricité : Fera plus de coups critiques, et de plus gros dégats directs
* Glace : Ses coups vont ralentir sa cible, ses coups critiques vont l'immobiliser
* Vapeur : Va créer une zone qui infligera des dégats à tous les ennemis et dans laquelle il pourra se téléporter

A voir si la branche "boue" de l'assasin de terre et d'eau est la même.

###Background

Par rapport au background, le nom "dévotion" est donné car j'imaginais des dieux associés à chaque élément. Il y aurait donc 5 dieux principaux (eau, feu, terre, air, aether) ainsi que des dieux secondaires. 

Dans l'idée le dieu de la boue de eau/terre est différent de celui de terre/eau (sinon on aurait 5 dieux + 6 dieux, je trouve ça naze ahah).

Par exemple (avec des noms à la con) :

* Dieu de l'eau : Kadba (ref ! oh !)
* Dieu de la **boue** (eau+terre) : **Dlagadou**
* Dieu de la glace (eau+air) : Sameulisi
* Dieu de la vapeur (eau+feu) : Stimmy

* Dieu de la terre : Darvey (ref ! oh ! oh !)
* Dieu de la **boue** (terre+eau) : **Heob**
* Dieu du magma (terre+feu) : Amgam
* Dieu des métaux (terre+air) : Grunt

Idem pour les autres, ce qui donnerait 5 dieux principaux + 12 dieux secondaires. Bon tout ça c'est vraiment pour le background mais ça peut être rapproché du gameplay dans le sens où, au lieu de dire clairement "je fais une dévotion à l'eau", on dirait "je fais une dévotion à Kadba". Et c'est l'esthétique des compétences actives, l'aspect de l'arbre, le temple, la description dans le background... qui ramèneront à l'élément eau. Pareil, que Dlagadou soit le dieu de la boue on s'en fout mais si les branches de l'arbre de dévotion ont des noms un peu mystique avec un aspect propre à l'élément suggéré, je trouve ça mieux que d'avoir une branche boue...

Donc en résumé Dlagadou (eau + terre) :
* Niveau background c'est genre le dieu du limon originel ou une connerie dans le genre
* Niveau gameplay, ça donne des compétences axées sur l'utilitaire (eau) et la défense (terre).
et Heob (terre + eau) :
* Niveau background c'est genre le dieu des marécages
* Niveau gameplay, ça donne des compétences axées sur l'utilitaire (eau) et la défense (terre) => pareil donc, ou presque si on considère qu'on avance plus l'un des éléments plutôt que l'autre. Dans un sens ça va nous aider à orienter nos idées pour les compétences.


#Monstres

Tout plein !


#Items

##Armes

Les armes sont réparties dans les catégories :
* armes à feu légère (fusil à pompe, revolver, fusil mitrailleur...)
* armes à feu lourde (lance-roquette, gatling, mortier...)
* armes légères au corps à corps (poignard, dague, lance...)
* armes lourdes au corps à corps (épée lourde, masse, marteau de guerre...)
* armes à distance (arc, fronde, arbalete...)

Elles ont également un type de dégat :
* perforant (poignard, revolver (toutes les armes qui utilisent des balles), lance...)
* tranchant(épée, hache, cimeterre...)
* contondant (masse, marteau, coup de savate dans la gueule...)


Les explosifs et les armes types mortier, lance-roquette... ont des règles particulières. On peut considérer que les types de dégats à ajouter à la liste sont :
* Explosif ?
* Chimique ?

##Equipements

Les items "équipement", sont des items que l'on peut équiper (oh ?) dans son inventaire sans que ce soit une arme (jeans, blouson, blouse, armure en cuir, gilet par balle...). 

##Consommables

Les items "consommable", sont des items utilisables qui se "détruisent" une fois utilisé (une baie, une bouteille d'eau, un bandage...). Comporte également les munitions (flèches, balles, essence pour lance-flamme...)

##Outils

Les items "outil", sont des items utilisables qui ne se détruisent pas une fois utilisé ou suite à plusieurs utilisations à cause de l'usure (hâche pour le bois, pioche, passe-partout, pierre à aiguiser...)

##Autres (junk)

Les items "junk", sont tous les autres items qui ne sont pas utilisables depuis l'inventaire mais qui peuvent avoir une utilité :
* pour le craft (un caillou, un bout de bois...)
* en tant que monnaie (tout, mais spécialement pièces, joyaux...)
* ou pas... (vieille godasse usagée...)



#Environnements

#Temples

#Donjons








#
