package sample;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.PathFinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class Motha
 *
 * ******* BEHAVIOUR *******
 * reste en WALK et fonce vers le joueur quand il est trop près
 *
 * PLUS COMPLEXE :
 * commence poser en IDLE sur un solid qu'il quitte (WALK) pour aller
 * se poser sur un autre solid. Si il est proche du joueur, il fonce
 * dessus en WALK
 *
 * ******* TODO *******
 *
 */
public class MothaTest extends Monster implements Mover {

    protected int destX;
    protected int destY;
    protected int deltaX;
    protected int deltaY;
    protected double direction;
    private List<String> posToIdleList;
    protected Path path;
    protected int currentStep = 0;
    protected String subState = "IDLE";
    //private List<Bullet> bulletList;

    /**
     * default Constructor
     */
    public MothaTest() throws SlickException{

        this(0, 0);
    }


    /**
     * Constructor
     * @param posX : position X du monstre
     * @param posY : position Y du monstre
     * @throws SlickException
     */
    public MothaTest(int posX, int posY) throws SlickException{


        super(
                new SpriteSheet("img/monster/mothaStatic2.png", 32, 32),
                "Blob",
                posX,
                posY,
                32,
                32
        );

        this.animations = new Animation[1];
        this.state = "IDLE";
        this.facing = "RIGHT";
        this.isFlying = true;
        this.posToIdleList = new ArrayList<String>();



        /** crée l'animation */
        /** WALK */
        this.animations[0] = this.loadAnimation(this.sprite, 0, 1, 0, 70);

    }

    @Override
    public void init(){

        this.posToIdle();

        /** il cherche un nouvel endroit pour se poser */
        int nextPos = (int) Math.round(Math.random() * posToIdleList.size());
        System.out.println(posToIdleList.get(nextPos));
        String[] parts = posToIdleList.get(nextPos).split("-");
        this.destX = Integer.parseInt(parts[0]) * 32;
        this.destY = Integer.parseInt(parts[1]) * 32;

        /** pathfinding */
        PathFinder pathFinder = new AStarPathFinder(this.level, 1000, true);

        if(Objects.equals(this.subState, "PREWALK")) {

            path = pathFinder.findPath(this, this.posX / 32, this.posY / 32, this.posX / 32, (this.posY / 32) -2);
        }else{
            path = pathFinder.findPath(this, this.posX / 32, this.posY / 32, this.destX / 32, this.destY / 32);
        }

        while(path == null){
            System.out.println("##### is nul #####");
            path = pathFinder.findPath(this, this.posX / 32, this.posY / 32, 1, 1);
        }

        /** pathfinding */
        //path = pathFinder.findPath(this, 1, 1, 10, 1);
        System.out.println("******* DEBUT DU TEST *******");
        System.out.println("state : "+this.state);
        System.out.println("path : "+path);
        System.out.println("length : "+path.getLength());
        for(int i = 0; i < path.getLength(); i++){
            System.out.println("index : "+i+" / X : "+path.getStep(i).getX()+" / Y : "+path.getStep(i).getY());
        }
        System.out.println("******* FIN DU TEST *******");
    }


    /**
     * mise à jour des déplacements du monstre
     * @param delta : pour la loop variable
     */
    @Override
    public void updateMove(int delta){

        /** si il est posé sur un solid en IDLE */
        if(Objects.equals(this.state, "IDLE")) {

            this.isFlying = false;
            /** il s'envole pour rejoindre un autre endroit */
            if (Math.round(Math.random() * 50) == 0) {
                this.state = "WALK";
                this.subState = "PREWALK";
            }
        }

        if(Objects.equals(this.state, "WALK") && Objects.equals(this.subState, "PREWALK")) {
            this.isFlying = true;

            if (this.updateStep()) {

                this.state = "WALK";
                this.subState = "WALK";
            }
        }

        if(Objects.equals(this.state, "WALK") && Objects.equals(this.subState, "WALK")) {



            this.isFlying = true;

            if (this.updateStep()) {

                this.state = "IDLE";
                this.subState = "IDLE";
                this.isFlying = false;
            }
        }


        //System.out.println("ACCELERATION : "+this.accX+" / "+this.accY);
        super.updateMove(delta);
    }

    /**
     * Update step if is it possibile otherwise, start again
     */
    private boolean updateStep() {
        //System.out.println("step: " + currentStep + " , lenghtPath: "+ path.getLength());
        //System.out.println("X : "+path.getStep(currentStep).getX());
        System.out.println(this.subState+" / "+currentStep);
        if (currentStep >= path.getLength()) {

            currentStep = -1;
            return true;

        }else if(currentStep == -1){
            this.init();
            this.currentStep++;
            return true;
        }else{



            /** direction du vol */
            int stepDeltaX = (path.getStep(currentStep).getX() * 32) - this.posX;
            int stepDeltaY = (path.getStep(currentStep).getY() * 32) - this.posY;
            double stepDirection = Math.atan2(stepDeltaY, stepDeltaX);

            /** vitesse du vol dans la direction donnée */
            this.accX = (float) Math.cos(stepDirection) * 2;
            this.accY = (float) Math.sin(stepDirection) * 2;

           // System.out.println("POSITION : "+(this.posX/32)+" / "+(this.posY/32));
           // System.out.println("TOGO : "+path.getStep(currentStep).getX()+" / "+path.getStep(currentStep).getY());
           // System.out.println("ACCELERATION : "+this.accX+" / "+this.accY);

            if (path.getStep(currentStep).getX() == (Math.round(this.posX / 32))) {
                if (path.getStep(currentStep).getY() == (Math.round(this.posY / 32))) {
                    currentStep++;
                }
            }


            return false;
        }

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
                            posToIdleList.add(""+i+"-"+(j-1));
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
            case "WALK":
                indexAnimation = 0;
                break;
            default:
                indexAnimation = 0;
                break;
        }
        return this.animations[indexAnimation];
    }




}
