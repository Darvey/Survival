package sample;



import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.Animation;
import java.io.*;



import java.util.Objects;


/**
 * Class qui gère le joueur
 */
public class EntityPlayer extends Entity{

    /** car. primaire */
    private int agility;
    private int strength;
    private int constitution;
    private int dexterity;
    private int intelligence;

    //** car. secondaire */
    private int endurance;
    private int modAttackSpeedCacS;
    private int modAttackSpeedCacB;
    private int modAttackSpeedRange;
    private int modDamageCacS;
    private int modDamageCacB;
    private int modDamageRange;
    private int modPrecisionCacS;
    private int modPrecisionCacB;
    private int modPrecisionRange;
    private int modPrecisionGun;
    private int resistanceDisease;
    private int resistancePoison;
    private int resistanceTiredness;
    private int resistancePsy;
    private int resistanceCut;
    private int resistancePierce;
    private int resistanceBlunt;
    private int identify;
    private int learn;
    private int weigth;

    /** inventaire */
    //private final Inventory inv;

    //** contrôles : touches */
    private boolean pressedUp;
    private boolean pressedDown;
    private boolean pressedLeft;
    private boolean pressedRight;

    /** controle : position de la souris */
    private double prevMouseX;
    private double prevMouseY;
    private double mouseX;
    private double mouseY;

    /** variables du saut */
    private int jumpImpulse;
    private int jumpCount;
    private int maxJumpCount;

    /** arme équipée */
    private Weapon weapon;


    /**
     * default Constructor
     * @throws SlickException
     */
    public EntityPlayer() throws SlickException{

        this("null", 0, 0, 0, 0, 0);
    }


    /**
     * Constructor
     * @param name : nom du joueur
     * @param strength : force du joueur
     * @param agility : agilité du joueur
     * @param dexterity : dextérité du joueur
     * @param constitution : constitution du joueur
     * @param intelligence : intelligence du joueur
     * @throws SlickException
     */
    public EntityPlayer(String name, int strength, int agility, int dexterity, int constitution, int intelligence) throws SlickException {

        super(
                new SpriteSheet("img/collider_32x64.png", 32, 64),
                name,   // nom
                0,      // posX
                0,      // posY
                32,     // largeur
                64      // hauteur
        );

        /** init de l'arme */
        weapon = new Weapon(this);


        /** init. de l'inventaire */
        //this.inv = new Inventory(this);

        /** ------- TO ENTITY -------
        /** init des var pour l'animation */
        this.animations = new Animation[1];
        this.state = "IDLE";
        this.facing = "RIGHT";
        /** crée l'animation */
        this.animations[0] = loadAnimation(this.sprite, 0, 1, 0, 100);
        /** ------------------------- */

        /** compteur du nombre de frame de saut */
        this.jumpCount = 0;
        /** temps pendant lequel on peut rester appuyé sur saut pour augmenter la hauteur de celui-ci */
        this.maxJumpCount = 30;
        /** impulsion initiale du saut */
        this.jumpImpulse = 12;

        /** init. du nom et des car. primaires */
        this.strength = strength;
        this.agility = agility;
        this.dexterity = dexterity;
        this.constitution = constitution;
        this.intelligence = intelligence;

        /** init. des altérations de déplacement */
        this.accLimit = 3;
        this.velLimit = 100;
        this.friction = 0.7f; // friction du personnage (0.2 = boue/escalier, 0.7 = normal, 0.9 = glace)

        /** init des var de déplacement */
        this.accX = 0f;
        this.accY = 0f;
        this.velX = 0f;
        this.velY = 0f;

        /** init des contrôles */
        this.pressedDown = false;
        this.pressedLeft = false;
        this.pressedRight = false;
        this.pressedUp = false;

        /** calcul des caractéristiques secondaires */
        calculateSecondarySpecs();

        /** TEST : inventory  */
        //this.inv.addItem("shroom1", 0.2f, true, "consumable");
        //this.inv.addItem("shroom1", 0.2f, true, "consumable");
        //this.inv.addItem("silverCoin", 0.2f, true, "junk");
        //this.inv.addItem("bronzeCoin", 0.1f, true, "tool");
        //this.inv.addItem("shotgun", 5.7f, true, "weapon");

    }


    /**
     * update
     * @param delta : utilisé pour la loop variable
     */
    @Override
    public void update(int delta){

        super.update(delta); // updateMove()
        weapon.update(this.posX, this.posY, this.mouseX, this.mouseY);
    }


    /**
     * update du déplacement
     */
    @Override
    public void updateMove(int delta){

        /** variables pour les tests de déplacement / saut */
        this.moveSpeed = 4;
        this.jumpImpulse = 10;
        this.friction = 0.7f;
        this.gravity = 0.2f;

        // --------------------------- OBSOLETE -----------------------------------
        boolean[] col = new boolean[4];
        col[0] = false;
        col[1] = false;
        col[2] = false;
        col[3] = false;
        float[] acc = getAcc(col);
        this.accX = acc[0];
        this.accY = acc[1];
        // -------------------------------------------------------------------------

        // --------------------------- A VIRER D'ICI --------------------------------
        this.accLimit = 1000;
        this.velLimit = 100;
        // --------------------------------------------------------------------------

        /** gestion de la gravité */
        if(!collisionBot()) {

            this.land = "ON_AIR";
            /** intensité de la gravité pour avoir un effet d'accélération à la chute */
            this.gravityIntensity += this.gravity;
            /** application de la gravité */
            this.accY += this.gravityIntensity;

        }else{

            this.gravityIntensity = 0;
            this.land = "ON_GROUND";
            if(!Objects.equals(this.state, "JUMPING")) {
                this.accY = 0;
                this.velY = 0;
            }
            this.state = "WALK";
        }


        /** évite de "coller" au plafond quand on saute sous un solid */
        if(collisionTop() && Objects.equals(this.state, "JUMPING")){
            this.state = "FALLING";
        }


        /** récupération de l'état (OBSOLETE) */
        this.state = getState();


        /** seuil de l'accélération */
        if (this.accX > this.accLimit)
            this.accX = this.accLimit;
        if (this.accX < -this.accLimit)
            this.accX = -this.accLimit;
        if (this.accY > this.accLimit)
            this.accY = this.accLimit;
        if (this.accY < -this.accLimit)
            this.accY = -this.accLimit;


        /** ajout de l'accélération à la vélocité */
        this.velX += this.accX;
        this.velY += this.accY;


        /** reinit de l'accélération */
        this.accX = 0;
        this.accY = 0;


        /** application de la friction */
        this.velX *= this.friction;
        this.velY *= this.friction;


        /** seuil de la vélocité */
        if (this.velX > this.velLimit)
            this.velX = this.velLimit;
        if (this.velX < -this.velLimit)
            this.velX = -this.velLimit;
        if (this.velY > this.velLimit)
            this.velY = this.velLimit;
        if (this.velY < -this.velLimit)
            this.velY = -this.velLimit;


        /** fonction d'arrondi */
        this.velX = approximatelyZero(this.velX, 0.01f);
        this.velY = approximatelyZero(this.velY, 0.01f);


        /** on parse en entier pour que la vélocité correspond à un nombre de pixel */
        int velXInteger = Math.round(velX);
        int velYInteger = Math.round(velY);


        /** on stocke la dernière valeur */
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;


        /**
         * Algorithmes de déplacements :
         * on déplace dans la direction donnée pixel par pixel
         * grâce à la boucle qui indente directement la position
         * actuelle jusqu'à la position voulue.
         * si il y a collision, on sort de la boucle.
         */

        /** move to the up */
        if(this.velY < 0){
            while(this.posY > this.prevPosY + velYInteger){

                if(collisionTop()){
                    break;
                }
                this.posY--;
            }
        }


        /** déplacement vers le bas */
        if(this.velY > 0) {
            while(this.posY < this.prevPosY + velYInteger){

                if(collisionBot()){
                    break;
                }
                this.posY++;
            }
        }


        /** déplacement vers la gauche */
        if(this.velX < 0){
            while(this.posX > this.prevPosX + velXInteger){

                if (collisionLeft()) {
                    break;
                }
                this.posX--;
            }
        }


        /** déplacement vers la droite */
        if(this.velX > 0) {
            while(this.posX < this.prevPosX + velXInteger) {
                if (collisionRight()) {
                    break;
                }
                this.posX++;
            }
        }


        /**
         * évite d'être coincé au milieu d'une plateforme
         * donne l'effet que le perso s'aggripe pour monter plus haut
         */
        while(collisionBot() && (Objects.equals(this.state, "JUMPING"))){
            this.posY--;
        }
    }


    /**
     * render
     * @param g : slick graphics
     */
    @Override
    public void render(Graphics g){

        super.render(g);
        weapon.render(g);
    }


    /**
     * crée une sauvegarde du joueur
     * @param f : nom du fichier
     */
    public void save(String f){
        try {
            ObjectOutputStream oos;
            oos = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(
                                    new File(f + ".psave"))));

            oos.writeObject(this); // objet à sauvegarder : this
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * calcul les caractéristiques secondaires à partir des primaires
     */
    private void calculateSecondarySpecs(){

        /**
         * str = force physique
         * agi = agilité du corps
         * dex = agilité des doigts, manipulation
         * con = endurance / resistance du corps
         * int = intelligence innée / force mental
        */

        /** déplacement */
        this.moveSpeed = 0.1f;
        this.stealth = Math.round(10 + ((this.agility + 1)/ 2));
        this.endurance = Math.round(10 + ((this.constitution + 1)/ 2));

        /** défense */
        this.health = 100 + this.constitution;
        this.dodge = Math.round(5 + (this.agility / 5));

        /** attaque */
        this.modAttackSpeedCacS = Math.round(this.agility / 5);                         //vitesse d'attaque avec arme au corps à corps (dague)
        this.modAttackSpeedCacB = Math.round((this.agility + this.strength) / 9);       //vitesse d'attaque avec arme au corps à corps (masse)
        this.modAttackSpeedRange = Math.round((this.dexterity + this.agility) / 10);    //vitesse d'attaque avec arme à distance (arc, fronde)
        this.modDamageCacS = Math.round(this.dexterity / 6);
        this.modDamageCacB = Math.round(this.strength / 6);
        this.modDamageRange = Math.round(this.strength / 8);
        this.modPrecisionCacS = Math.round((this.dexterity + this.agility) / 11);
        this.modPrecisionCacB = Math.round(this.agility / 9);
        this.modPrecisionRange = Math.round(this.dexterity / 8);
        this.modPrecisionGun = Math.round(this.dexterity / 8);

        /** résistances en pourcentage */
        this.resistanceDisease = Math.round(15 + (this.constitution / 4));
        this.resistancePoison = Math.round(5 + (this.constitution / 6));
        this.resistanceTiredness = Math.round(20 + (this.constitution / 4));
        this.resistancePsy = Math.round(10 + (this.intelligence / 3));
        this.resistanceCut = 0;
        this.resistancePierce = 0;
        this.resistanceBlunt = 0;

        /** autre */
        this.learn = Math.round(this.intelligence / 2);
        this.identify = Math.round(this.intelligence);
        this.weigth = Math.round(20 + this.strength);
    }


    /**
     * affichage l'inventaire
     */
    public void displayInventory(){

        //this.inv.display();
    }



    @Override
    protected float[] getAcc(boolean[] col){

        if (this.pressedUp) {
            /**
             * si on est WALKING / ON_GROUND, si pressedUP => JUMPING / ON_AIR
             * jusqu'à ce qu'on lache la touche saut ou jusqu'à la fin du compteur de saut
             * soit => FALLING / ON_AIR jusqu'à la collision au sol => WALKING / ON_GROUND
            //  */
            if((!Objects.equals(this.state, "JUMPING")) && (!Objects.equals(this.state, "FALLING")) && (Objects.equals(this.land, "ON_GROUND"))){
                if(col[0]){
                    this.accY = 0;
                }else {
                    this.land = "ON_AIR";
                    this.state = "JUMPING";
                    this.accY -= this.jumpImpulse;                              //première impulsion / hauteur mini
                    this.jumpCount++;
                }
            }else if(Objects.equals(this.state, "JUMPING")){
                this.jumpCount++;
                if(this.jumpCount < this.maxJumpCount) {
                    if(this.jumpCount != 0) {
                        this.accY -= (this.maxJumpCount / this.jumpCount);      //effet dégressif du saut
                    }
                }else{
                    this.jumpCount = 0;
                    this.state = "FALLING";
                    this.accY = 0;
                }
                /** permet de ne pas resauter si on atteint une plateforme en haut de saut / évite le double saut */
                if(collisionBot()){
                    this.pressedUp = false;
                }
            }else{
                /** on ne saute pas plusieurs fois si on maintient appuyé */
                this.pressedUp = false;
                this.jumpCount = 0;
            }

        }else{
            /** quand on relache la touche */
            if(Objects.equals(this.state, "JUMPING")){
                this.jumpCount = 0;
                this.state = "FALLING";
                this.accY = 0;
            }
        }

        //if (this.pressedDown) {
            /*if(col[1]){
                this.accY = 0;
            }else {
                this.accY += this.moveSpeed;
            }*/

            /** plus tard : pour s'accroupir ? */
        //}

        if (this.pressedLeft) {
            if(col[2]){
                this.accX = 0;
            }else {
                this.accX -= this.moveSpeed;
            }
        }

        if (this.pressedRight) {
            if(col[3]){
                this.accX = 0;
            }else {
                this.accX += this.moveSpeed;
            }
        }


        float[] acc = new float[2];
        acc[0] = this.accX;
        acc[1] = this.accY;
        return acc;
    }


    @Override
    /**
     * renvoie le statut du joueur
     */
    public String getState(){

        return this.state;
    }


    /**
     * renvoie l'animation du joueur
     * @return : l'animation du joueur
     */
    public Animation getAnimations(String state){
        int indexAnimation;
        switch(state){
            case "WALK":
                indexAnimation = 0;
                break;
            default:
                indexAnimation = 0;
                break;
        }
        return this.animations[indexAnimation];
    }


    /**
     * renvoie le niveau dans lequel est le joueur
     * @return : le niveau dans lequel est le joueur
     */
    public Level getLevel(){

        return this.level;
    }


    /**
     * renvoie l'arme équipée
     * @return : l'arme équipée
     */
    public Weapon getWeapon(){

        return this.weapon;
    }



    public void setPressedUp(){

        this.pressedUp = true;
    }

    public void setPressedDown(){

        this.pressedDown = true;
    }

    public void setPressedLeft(){

        this.pressedLeft = true;
    }

    public void setPressedRight(){

        this.pressedRight = true;
    }

    public void setReleasedUp(){

        this.pressedUp = false;
    }

    public void setReleasedDown(){

        this.pressedDown = false;
    }

    public void setReleasedLeft(){

        this.pressedLeft = false;
    }

    public void setReleasedRight(){

        this.pressedRight = false;
    }

    public void setMouse(int oldX, int oldY, int newX, int newY){

        this.mouseX = newX;
        this.mouseY = newY;
        this.prevMouseX = oldX;
        this.prevMouseY = oldY;
    }



    public int getEndurance(){

        return this.endurance;
    }

    public int getModAttackSpeedCacS(){

        return modAttackSpeedCacS;
    }

    public String toString(){

        return "CARACTERISTIQUES PRIMAIRES"+
        "\nagility : "+this.agility+
        "\nstrength : "+this.strength+
        "\nconstitution : "+this.constitution+
        "\ndexterity : "+this.dexterity+
        "\nintelligence : "+this.intelligence+
        "\nCARACTERISTIQUES SECONDAIRE"+
        "\nendurance : "+this.endurance+
        "\nmodAttackSpeedCacS : "+this.modAttackSpeedCacS+
        "\nmodAttackSpeedCacB : "+this.modAttackSpeedCacB+
        "\nmodAttackSpeedRange : "+this.modAttackSpeedRange+
        "\nmodDamageCacS : "+this.modDamageCacS+
        "\nmodDamageCacB : "+this.modDamageCacB+
        "\nmodDamageRange : "+this.modDamageRange+
        "\nmodPrecisionCacS : "+this.modPrecisionCacS+
        "\nmodPrecisionCacB : "+this.modPrecisionCacB+
        "\nmodPrecisionRange : "+this.modPrecisionRange+
        "\nmodPrecisionGun : "+this.modPrecisionGun+
        "\nresistanceDisease : "+this.resistanceDisease+
        "\nresistancePoison : "+this.resistancePoison+
        "\nresistanceTiredness : "+this.resistanceTiredness+
        "\nresistancePsy : "+this.resistancePsy+
        "\nresistanceCut : "+this.resistanceCut+
        "\nresistancePierce : "+this.resistancePierce+
        "\nresistanceBlunt : "+this.resistanceBlunt+
        "\nidentify : "+this.identify+
        "\nlearn : "+this.learn+
        "\nweigth : "+this.weigth;
    }
}
