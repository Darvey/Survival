package sample;

import org.lwjgl.Sys;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Class Blob
 *
 * ******* BEHAVIOUR *******
 * reste en WALK et JUMP de temps en temps dans une direction aléatoire
 * si le joueur est proche, il JUMP plus souvent dans sa direction
 *
 * ******* TODO *******
 * - corriger le bug qui fait qu'il passe de RIGHT à LEFT rapidement
 * quand il est au niveau du joueur
 */
public class Blob extends Monster {

    private boolean isJumping = false;
    private Animation[] animations = new Animation[3];


    /**
     * default Constructor
     */
    public Blob() throws SlickException{

        this(0, 0);
    }


    /**
     * Constructor
     * @param posX : position X sur la carte
     * @param posY : position Y sur la carte
     */
    public Blob(int posX, int posY) throws SlickException {

        super(
                new SpriteSheet("img/monster/spriteBlob.png", 13, 14),
                "Blob",
                posX,
                posY,
                13,
                14
        );

        //this.animations = new Animation[1];
        this.state = "IDLE";
        this.facing = "RIGHT";
        /** crée l'animation */
        /** IDLE */
        this.animations[0] = loadAnimation(this.sprite, 0, 6, 0);
        /** JUMP */
        this.animations[1] = loadAnimation(this.sprite, 0, 1, 1);
        /** WALK (OBSOLETE) */
        this.animations[2] = loadAnimation(this.sprite, 0, 6, 0);
    }


    /**
     * charge les images de l'animation
     * @param spriteSheet : spriteSheet of the player
     * @param startX : position of the column for start
     * @param endX : position of the column for end
     * @param y : position of the line
     * @return : animation which contains the frames
     */
    private Animation loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int y) {
        Animation animation = new Animation();
        for (int x = startX; x < endX; x++) {
            animation.addFrame(spriteSheet.getSprite(x, y), 100);
        }
        return animation;
    }


    /**
    * renvoie l'animation du joueur
    * @return : l'animation du joueur
    */
    public Animation getAnimations(String state){
        int indexAnimation;
        switch(state){
            case "IDLE":
                /** OBSOLETE */
                indexAnimation = 0;
                break;
            case "JUMPING":
                indexAnimation = 1;
                break;
            case "WALK":
                indexAnimation = 2;
                break;
            default:
                indexAnimation = 0;
                break;
        }
        return this.animations[indexAnimation];
    }

    /**
     * mise à jour du monstre
     * @param delta
     */
    public void update(int delta){

        /** à déplacer */
        this.moveSpeed = 4;
        this.friction = 0.7f;
        this.gravity = 0.2f;


        super.update(delta);
    }


    /**
     * mise à jour des déplacements du monstre
     * @param delta
     */
    @Override
    public void updateMove(int delta){

        if(this.state == "IDLE"){
            /** à l'image où le blob est "haut" */
            if(this.getAnimations("IDLE").getFrame() == 5){
                if(isAggro) {
                    if (Math.round(Math.random() * 4) == 0) {
                        this.state = "JUMPING";
                    }
                }else{
                    if (Math.round(Math.random() * 40) == 0) {
                        this.state = "JUMPING";
                    }
                }
            }
        }

        if(this.state == "JUMPING"){

            /** direction du saut */
            if(this.facing == "RIGHT"){
                this.accX = 1;
            }else{
                this.accX = -1;
            }
            /** hauteur du saut */
            this.accY -= 2 + Math.random();

            /**
             * vérification de si le blob est en train de sauter
             * pour ne pas interferer avec la collisionBot à la
             * première frame de saut
             */
            if(!isJumping) {
                isJumping = true;
            }else{
                if (this.collisionBot()) {
                    this.state = "IDLE";
                    /** pour revenir à la première frame du IDLE (effet d'écrasement) */
                    this.getAnimations("IDLE").restart();
                    isJumping = false;
                }
            }

        }

        /** calcul de la distance avec le joueur */
        int deltaXPlayer = Math.abs(this.posX - this.player.posX);
        int deltaYPlayer = Math.abs(this.posY - this.player.posY);
        int deltaPlayer = (int) Math.sqrt(Math.pow(deltaXPlayer, 2) + Math.pow(deltaYPlayer, 2));

        /** gestion de l'agressivité */
        if(deltaPlayer < 200){
            this.isAggro = true;
            if(this.posX < this.player.posX){
                this.facing = "RIGHT";
            }else{
                this.facing = "LEFT";
            }
        }else{
            this.isAggro = false;
        }

        /** change de sens vers les bords */
        if(this.posX > 500){
            this.facing = "LEFT";
        }else if(this.posX < 100){
            this.facing = "RIGHT";
        }

        super.updateMove(delta);
    }
    

    /**
     * rendu graphique
     * @param g : slick graphics
     */
    @Override
    public void render(Graphics g){

        g.drawAnimation(getAnimations(this.state), this.posX, this.posY);
    }


    /**
     * OBSOLETE
     * @param col : OBSOLETE
     * @return
     */
    @Override
    protected float[] getAcc(boolean[] col){

        float[] acc = new float[2];
        acc[0] = this.accX;
        acc[1] = this.accY;
        return acc;
    }
}
