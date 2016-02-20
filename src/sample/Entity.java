package sample;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;

import java.util.Objects;


public abstract class Entity {

    /** id de l'entité */
    private final long id;

    /** nom */
    protected final String name;

    /** sprite */
    protected SpriteSheet sprite;

    /** car. secondaire */
    protected int health;
    protected float moveSpeed;
    protected int dodge;
    protected int stealth;

    /** init des var d'animations */
    protected Animation[] animations;
    protected String state;
    protected String land;
    protected String facing;

    /** position */
    protected int posX;
    protected int posY;
    private int prevPosX;
    private int prevPosY;

    /** position d'apparition sur la carte */
    //protected int popX;
    //protected int popY;

    /** var de déplacement */
    protected float accX;
    protected float accY;
    protected float accLimit;
    protected float velX;
    protected float velY;
    protected float velLimit;
    //private int velXInteger;
    //private int velYInteger;
    protected float friction;
    protected float gravity;
    private float gravityIntensity;


    /** niveau dans lequel l'entité est présente */
    protected Level level;

    /*
    // collider
    protected int colX;
    protected int colY;
    protected int colWidth;
    protected int colHeight;

    //hitbox
    protected int hitX;
    protected int hitY;
    protected int hitWidth;
    protected int hitHeight;
    */

    /**
     * default Constructor
     */
    public Entity(){
        this("null");
    }

    /**
     * Constructor
     */
    public Entity(String name){

        this.id = 0;
        this.name = "entity";

    }


    /**
     * mise à jour
     * @param delta : delta pour la loop variable
     */
    public void update(int delta){

        this.updateMove(delta);
    }


    /**
     * rendu graphique
     * @param g : slick graphics
     */
    public void render(Graphics g){

        g.drawAnimation(this.getAnimations("WALK"), this.posX, this.posY);
    }


    /**
     * Est-ce qu'il y a une collision en bas ?
     * @return : true si collision
     */
    protected boolean collisionBot(){

        return (this.level.getTile(this.posX + 16, this.posY + 64).solid);
    }


    /**
     * Est-ce qu'il y a une collision en haut ?
     * @return : true si collision
     */
    private boolean collisionTop(){

        return (this.level.getTile(this.posX + 16, this.posY).solid && !this.level.getTile(this.posX + 16, this.posY).platform);
    }


    /**
     * Est-ce qu'il y a une collision à gauche ?
     * @return : true si collision
     */
    private boolean collisionLeft(){

        return (this.level.getTile(this.posX, this.posY + 32).solid);
    }


    /**
     * Est-ce qu'il y a une collision à droite ?
     * @return : true si collision
     */
    private boolean collisionRight(){

        return (this.level.getTile(this.posX + 32, this.posY + 32).solid);
    }


    /**
     * MAJ du déplacement
     * Précision :
     * - accélération : somme des forces qui s'appliquent à l'entité (friction, gravité, impulsion...)
     * - vélocité : accélération + inertie
     */
    public void updateMove(int delta){


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
        velX = approximatelyZero(velX, 0.01f);
        velY = approximatelyZero(velY, 0.01f);


        /** on parse en entier pour que la vélocité correspond à un nombre de pixel */
        int velXInteger = Math.round(velX);
        int velYInteger = Math.round(velY);


        /** on stocke la dernière valeur */
        prevPosX = posX;
        prevPosY = posY;


        /**
         * Algorithmes de déplacements :
         * on déplace dans la direction donnée pixel par pixel
         * grâce à la boucle qui indente directement la position
         * actuelle jusqu'à la position voulue.
         * si il y a collision, on sort de la boucle.
         */

        /** move to the up */
        if(velY < 0){
            while(posY > prevPosY + velYInteger){

                if(collisionTop()){
                    break;
                }
                posY--;
            }
        }


        /** déplacement vers le bas */
        if(velY > 0) {
            while(posY < prevPosY + velYInteger){

                if(collisionBot()){
                    break;
                }
                posY++;
            }
        }


        /** déplacement vers la gauche */
        if(velX < 0){
            while(posX > prevPosX + velXInteger){

                if (collisionLeft()) {
                    break;
                }
                posX--;
            }
        }


        /** déplacement vers la droite */
        if(velX > 0) {
            while(posX < prevPosX + velXInteger) {
                if (collisionRight()) {
                    break;
                }
                posX++;
            }
        }


        /**
         * évite d'être coincé au milieu d'une plateforme
         * donne l'effet que le perso s'aggripe pour monter plus haut
         */
        while(collisionBot() && (Objects.equals(this.state, "JUMPING"))){
            posY--;
        }
    }


    /**
     * arrondi à zero quand la valeur est très faible
     * @param toRound : nombre à arrondir
     * @param roundLimit : seuil d'arrondi
     * @return : nombre arrondi
     */
    private float approximatelyZero(float toRound, float roundLimit){

        float f = toRound;
        if(f > 0f && f < roundLimit)
            f = 0f;
        if(f < 0f && f > -roundLimit)
            f = 0f;

        return f;
    }


    /**
     * renvoie l'animation de l'entité
     * @return : animation de l'entité
     */
    public Animation getAnimations(String state){

        int indexAnimation;
        switch(state){
            case "WALK":
                indexAnimation = 0;
                break;
            case "JUMPING":
                indexAnimation = 0;
                break;
            default:
                indexAnimation = 0;
                break;
        }
        return this.animations[indexAnimation];
    }


    /**
     * renvoie l'accélération de l'entité
     * @param col : OBSOLETE
     * @return : l'accélération
     */
    protected float[] getAcc(boolean[] col){

        float[] acc = new float[2];
        acc[0] = this.accX;
        acc[1] = this.accY;
        return acc;
    }


    /**
     * renvoie l'état de l'entité (OBSOLETE)
     * @return : l'état de l'entité
     */
    public String getState(){

        return this.state;
    }

    /**
     * renvoie l'image de l'entité
     */
    /* public SpriteSheet getSprite() {

        return this.sprite;
    }*/
}
