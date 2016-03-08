package sample;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.util.pathfinding.Mover;

import java.util.Objects;
import java.util.UUID;


/**
 * Class abstraite qui gère les entités (joueur, monstres...)
 */
public abstract class Entity {

    /** identifiants */
    private final String id;
    protected final String name;

    /** élément graphique */
    protected final SpriteSheet sprite;
    protected Animation[] animations;

    /** car. secondaire */
    protected int health;
    protected float moveSpeed;
    protected int dodge;
    protected int stealth;

    /** init des var d'animations */
    protected String state;
    protected String land;
    protected String facing;

    /** position et dimensions */
    protected int posX;
    protected int posY;
    protected int prevPosX;
    protected int prevPosY;
    protected int width;
    protected int height;
    protected static final int OFFSET_BOT = 3;
    protected static final int OFFSET_LEFT = 1;
    protected static final int OFFSET_RIGHT = 1;

    /** position d'apparition sur la carte */
    protected int popX;
    protected int popY;

    /** var de déplacement */
    protected float accX;
    protected float accY;
    protected float accLimit;
    protected float velX;
    protected float velY;
    protected float cutVelX;
    protected float cutVelY;
    protected float velLimit;
    //private int velXInteger;
    //private int velYInteger;
    protected float friction;
    protected float gravity;
    protected float gravityIntensity;
    protected boolean isFlying;


    /** niveau dans lequel l'entité est présente */
    protected Level level;


    /**
     * default Constructor
     */
    public Entity() throws SlickException{

        this(null, "null", 0, 0, 0, 0);
    }


    /**
     * Constructor
     */
    public Entity(SpriteSheet sprite, String name, int posX, int posY, int width, int height) throws SlickException{

        this.id = UUID.randomUUID().toString();
        this.name = name;

        this.sprite = sprite;

        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
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

        boolean collision = this.level.getTile(this.posX + (this.width / 2), this.posY + this.height - this.OFFSET_BOT).solid ||
                this.level.getTile(this.posX + this.OFFSET_LEFT, this.posY + this.height - this.OFFSET_BOT).solid ||
                this.level.getTile(this.posX + this.width - this.OFFSET_RIGHT, this.posY + this.height - this.OFFSET_BOT).solid;

        return collision;
    }


    /**
     * Est-ce qu'il y a une collision en haut ?
     * @return : true si collision
     */
    protected boolean collisionTop(){

        return (this.level.getTile(this.posX + (this.width / 2), this.posY).solid && !this.level.getTile(this.posX + 16, this.posY).platform);
    }


    /**
     * Est-ce qu'il y a une collision à gauche ?
     * @return : true si collision
     */
    protected boolean collisionLeft(){

        boolean collision =
                this.level.getTile(this.posX, this.posY + (this.height / 2)).solid ||
                        this.level.getTile(this.posX, this.posY).solid ||
                        this.level.getTile(this.posX, this.posY + this.height - 6).solid
                ;
        if(
                this.level.getTile(this.posX, this.posY + (this.height / 2)).platform ||
                this.level.getTile(this.posX, this.posY).platform ||
                this.level.getTile(this.posX, this.posY + this.height - 6).platform
        ){
            collision = false;
        }

        return collision;
    }


    /**
     * Est-ce qu'il y a une collision à droite ?
     * @return : true si collision
     */
    protected boolean collisionRight(){

        boolean collision =
                this.level.getTile(this.posX + this.width, this.posY + (this.height / 2)).solid ||
                this.level.getTile(this.posX + this.width, this.posY).solid ||
                this.level.getTile(this.posX + this.width, this.posY + this.height - 6).solid
        ;
        if(
                this.level.getTile(this.posX + this.width, this.posY + (this.height / 2)).platform ||
                this.level.getTile(this.posX + this.width, this.posY).platform ||
                this.level.getTile(this.posX + this.width, this.posY + this.height - 6).platform
        ){
            collision = false;
        }

        return collision;
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

            if(!this.isFlying) {
                /** intensité de la gravité pour avoir un effet d'accélération à la chute */
                this.gravityIntensity += this.gravity;
                /** application de la gravité */
                this.accY += this.gravityIntensity;
            }

        }else{

            this.gravityIntensity = 0;
            this.land = "ON_GROUND";
            if(!Objects.equals(this.state, "JUMPING") && !this.isFlying) {
                this.accY = 0;
                this.velY = 0;
            }
            //this.state = "WALK"; // => uniquement pour le joueur
        }


        /** évite de "coller" au plafond quand on saute sous un solid */
        if(collisionTop() && Objects.equals(this.state, "JUMPING")){
            //this.state = "FALLING"; // => uniquement pour le joueur
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
        int velXInteger = (int) velX;
        int velYInteger = (int) velY;

        /** on stocke ce qui a été perdu de l'arrondi */
        this.cutVelX += this.velX - velXInteger;
        this.cutVelY += this.velY - velYInteger;

        /** quand ce stockage peut être reconverti en entier */
        if(this.cutVelX >= 1){
            /** on ajoute un pixel de déplacement */
            velXInteger++;
            this.cutVelX--;
        }
        if(this.cutVelY >= 1){
            /** on ajoute un pixel de déplacement */
            velYInteger++;
            this.cutVelY--;
        }


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



    }


    /**
     * arrondi à zero quand la valeur est très faible
     * @param toRound : nombre à arrondir
     * @param roundLimit : seuil d'arrondi
     * @return : nombre arrondi
     */
    protected float approximatelyZero(float toRound, float roundLimit){

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
     * charge les images de l'animation
     * @param spriteSheet : spriteSheet of the player
     * @param startX : position of the column for start
     * @param endX : position of the column for end
     * @param y : position of the line
     * @return : animation which contains the frames
     */
    protected Animation loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int y, int duration) {
        Animation animation = new Animation();
        for (int x = startX; x < endX; x++) {
            animation.addFrame(spriteSheet.getSprite(x, y), duration);
        }
        return animation;
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
     * définie le niveau dans lequel est le joueur
     * @param level : le niveau dans lequel est le joueur
     */
    public void setLevel(Level level){

        this.level = level;
    }

    public void setPosition(int posX, int posY){
        this.posX = posX;
        this.posY = posY;
    }
}
