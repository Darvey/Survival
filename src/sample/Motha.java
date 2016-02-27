package sample;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.util.pathfinding.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class Motha : EN TEST ET EN BORDEL
 *
 * ******* BEHAVIOUR *******
 * commence poser en IDLE sur un solid qu'il quitte (WALK) pour aller
 * se poser sur un autre solid. Si il est proche du joueur, il fonce
 * dessus en WALK
 *
 * PLUS COMPLEXE :
 * même chose mais avec une liste de tile disponible et un déplacement
 * en utilisant un pathfinding
 *
 * ******* TODO *******
 *
 */
public class Motha extends Monster implements Mover {

    protected int destX;
    protected int destY;
    protected int deltaX;
    protected int deltaY;
    protected int idleX;
    protected int idleY;
    protected double direction;
    private List<String> posToIdleList;
    protected Path path;
    protected int currentStep = 0;
    protected String subState;


    /**
     * default Constructor
     */
    public Motha() throws SlickException{

        this(0, 0);
    }


    /**
     * Constructor
     * @param posX : position X du monstre
     * @param posY : position Y du monstre
     * @throws SlickException
     */
    public Motha(int posX, int posY) throws SlickException{


        super(
                new SpriteSheet("img/monster/spriteMotha2.png", 31, 29),
                "Motha",
                posX,
                posY,
                31,
                29
        );

        this.animations = new Animation[3];
        this.state = "IDLE";
        this.facing = "RIGHT";
        this.isFlying = false;
        this.posToIdleList = new ArrayList<String>();
        this.health = 8;


        /** IDLE */
        this.animations[0] = this.loadAnimation(this.sprite, 7, 8, 0, 70);
        /** WALK */
        this.animations[1] = this.loadAnimation(this.sprite, 0, 16, 0, 70);
        /** WALK */
        this.animations[2] = this.loadAnimation(this.sprite, 0, 1, 1, 70);
    }

    @Override
    public void init(){
        this.posToIdle();
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
            this.isFlying = false;
        }else {


            /** si il est posé sur un solid en IDLE */
            if (Objects.equals(this.state, "IDLE") && collisionBot()) {

                this.idleX = this.posX;
                this.idleY = this.posY;
                this.isFlying = false;
                /** il s'envole pour rejoindre un autre endroit */
                if (Math.round(Math.random() * 50) == 0) {

                    /** il s'envole */
                    this.state = "WALK";
                    this.subState = "PREWALK";
                    this.isFlying = true;
                    this.accY -= 2;

                }
            }

            if (Objects.equals(this.state, "WALK") && Objects.equals(this.subState, "PREWALK")) {
                if (!collisionTop()) {

                } else {

                }

                if (posY <= (idleY - 164) || collisionTop()) {
                    this.subState = "WALK";
                } else {
                    this.accX = (this.facing == "RIGHT") ? 1 : -1;
                    this.accY -= 1;
                }
            }

            if (Objects.equals(this.state, "WALK") && Objects.equals(this.subState, "WALK")) {
                if (!collisionBot()) {
                    this.accX = (this.facing == "RIGHT") ? 1 : -1;
                    this.accY += 1;
                } else {
                    this.state = "IDLE";
                    this.subState = "IDLE";
                }
            }

            if (this.posX > 500) {
                this.facing = "LEFT";
            }
            if (this.posX < 100) {
                this.facing = "RIGHT";
            }
        }


        int deltaPlayer = this.getDeltaPlayer();

        //System.out.println("ACCELERATION : "+this.accX+" / "+this.accY);
        super.updateMove(delta);
    }

    /**
     * Update step if is it possibile otherwise, start again
     */
    private boolean updateStep() {
        return false;
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
     * calcul les endroits où le monstre peut se poser
     */
    public void posToIdle(){

        Tile[][] tiles = this.level.getTiles();
        for(int i = 0; i < tiles.length; i++){
            for(int j = 0; j < tiles[i].length; j++){
                // i : colonne / j : ligne
                Tile tile = tiles[i][j];
                /** si la tile est bien solid */
                if(tile.solid){
                    /** si on est pas en haut de la map */
                    if(j != 0){
                        /** si la tile n'a pas une autre tile solid au-dessus d'elle */
                        if(!tiles[i][j-1].solid){
                            /** on l'ajoute à la liste des endroits où il peut se poser */
                            posToIdleList.add(""+i+"-"+(j-2));
                        }
                    }
                }
            }
        }
    }


    /**
     * renvoie l'animation du monstre
     * @return : l'animation du monstre
     */
    public Animation getAnimations(String state){
        int indexAnimation;
        switch(state){
            case "IDLE":
                /** OBSOLETE */
                indexAnimation = 0;
                break;
            case "WALK":
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




}
