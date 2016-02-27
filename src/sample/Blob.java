package sample;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import java.util.Objects;

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

        this.animations = new Animation[3];
        this.state = "IDLE";
        this.facing = "RIGHT";
        this.isFlying = false;

        this.health = 3;

        /** IDLE */
        this.animations[0] = this.loadAnimation(this.sprite, 0, 6, 0, 100);
        /** JUMP */
        this.animations[1] = this.loadAnimation(this.sprite, 0, 1, 1, 100);
        /** DYING */
        this.animations[2] = this.loadAnimation(this.sprite, 0, 6, 2, 100);
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
            case "DYING":
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
     * @param delta : pour la loop variable
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
     * @param delta : pour la loop variable
     */
    @Override
    public void updateMove(int delta){
        if(this.isDead){
            this.state = "DYING";
        }
        if(Objects.equals(this.state, "DYING")){
            if (this.getAnimations("DYING").getFrame() == 5) {
                this.getAnimations("DYING").stop();
            }
        }else {
            if (Objects.equals(this.state, "IDLE")) {
                /** à l'image où le blob est "haut" */
                if (this.getAnimations("IDLE").getFrame() == 5) {
                    if (isAggro) {
                        if (Math.round(Math.random() * 4) == 0) {
                            this.state = "JUMPING";
                        }
                    } else {
                        if (Math.round(Math.random() * 40) == 0) {
                            this.state = "JUMPING";
                        }
                    }
                }
            }

            if (Objects.equals(this.state, "JUMPING")) {

                /** direction du saut */
                if (Objects.equals(this.facing, "RIGHT")) {
                    this.accX = 1;
                } else {
                    this.accX = -1;
                }
                /** hauteur du saut */
                this.accY -= 2 + Math.random();

                /**
                 * vérification de si le blob est en train de sauter
                 * pour ne pas interferer avec la collisionBot à la
                 * première frame de saut
                 */
                if (!isJumping) {
                    isJumping = true;
                } else {
                    if (this.collisionBot()) {
                        this.state = "IDLE";
                        /** pour revenir à la première frame du IDLE (effet d'écrasement) */
                        this.getAnimations("IDLE").restart();
                        isJumping = false;
                    }
                }
            }
        }

        /** distance avec le joueur */
        int deltaPlayer = this.getDeltaPlayer();

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
     * OBSOLETE
     * @param col : OBSOLETE
     * @return : col
     */
    @Override
    protected float[] getAcc(boolean[] col){

        float[] acc = new float[2];
        acc[0] = this.accX;
        acc[1] = this.accY;
        return acc;
    }
}
