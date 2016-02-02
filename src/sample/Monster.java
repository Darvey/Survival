package sample;

import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;


public class Monster extends Entity implements MoveListener {

    /*
    entity :
    health
    dodge
    stealth
    moveSpeed
    */
    protected final String name;


    protected int reach;
    protected int aggressivity; //0 : fuyard, 1 : se défend, 2 : agressif
    protected float attackSpeed;

    // pour tester ( plus tard on utilisera des tableaux pour les animations... )

    protected final SpriteAnimation animationWalk;

    //interaction joueur
    protected float playerPosX;
    protected float playerPosY;
    protected float deltaPos;
    float deltaX;
    float deltaY;

    protected int timeAttack;





    public Monster(String pName, int pPosX, int pPosY) {

        //image et animation
        image = new ImageView();
        imagePath = new Image(Main.class.getResourceAsStream("../img/mothaBlue_walk2.png"));
        image.setImage(imagePath);
        image.setViewport(new Rectangle2D(0, 0, 52, 89));

        animationWalk = new SpriteAnimation(image, Duration.millis(1200), 17, 4, 0, 0, 31, 29);
        animationWalk.setCycleCount(Animation.INDEFINITE);
        animationWalk.play();


        this.name = pName;
        this.posX = pPosX;
        this.posY = pPosY;
        this.playerPosX = -1000000;
        this.playerPosY = -1000000;
        System.out.println(posX);

        this.popX = this.posX;

        //collider
        this.colX = 7;
        this.colY = 10;
        this.colWidth = 15;
        this.colHeight = 14;

        //move
        this.accLimit = 3;
        this.velLimit = 6;
        this.friction = 0.5f;               //friction du personnage (0.2 = boue/escalier, 0.5 = normal, 0.9 = glace)
        this.accX = 2f;
        this.accY = 0f;
        this.velX = 0f;
        this.velY = 0f;
        this.moveSpeed = 3;

        this.attackSpeed = 2.3f;

        this.state = "PASSIVE_WALK";
        this.facing = "RIGHT";

        this.timeAttack = 0;



    }

    public void playerIsMoving(float pPosX, float pPosY){

        //System.out.println("Grrr ! Moi "+this.name+" vois un joueur situé en :"+pPosX+"/"+pPosY);
        this.playerPosX = pPosX;
        this.playerPosY = pPosY;
    }

    public void attack(){

        if(timeAttack == 0){
            timeAttack = (int)(60 / attackSpeed);
            System.out.println("Grrr, je t'attaque toutes les "+timeAttack+" frames ! Prends ça dans ta gueule !");
        }else{
            timeAttack--;
        }
    }

    /*
        ------- GETTERS -------
     */
    @Override
    protected float[] getAcc(boolean[] col){

        //calcul de l'accélération
        this.deltaX = this.posX - this.playerPosX;
        this.deltaY = this.posY - this.playerPosY;
        this.deltaPos = (float)Math.sqrt((float)Math.pow(deltaX, 2) + (float)Math.pow(deltaY, 2));
        float aggX;
        float aggY;

        if(deltaPos != 0) {
            aggX = -deltaX / deltaPos * 3;
            aggY = -deltaY / deltaPos * 3;
        }else{
            aggX = 0;
            aggY = 0;
        }

        switch(this.state) {
            case "AGRESSIVE_WALK":
                if(aggX > 0) {
                    this.facing = "RIGHT";
                }else{
                    this.facing = "LEFT";
                }
                this.accX += aggX;
                this.accY += aggY;
                break;
            case "ATTACK":
                attack();
                break;
            case "PASSIVE_WALK":
                if(posX > popX + 300) {
                    this.facing = "LEFT";
                }else if(posX < popX - 300){
                    this.facing = "RIGHT";
                }
                if(this.facing == "RIGHT"){
                    this.accX += this.moveSpeed;
                }else if(this.facing == "LEFT"){
                    this.accX -= this.moveSpeed;
                }
                break;
            default:
                break;
        }

        // return de l'accélération
        float[] acc = new float[2];
        acc[0] = this.accX;
        acc[1] = this.accY;
        return acc;
    }

    @Override
    public String getState() {
        //ici, quelle est son statut ? en fonction de la position du joueur etc.
        if (this.deltaPos < 200 && this.deltaPos >= 50) {
            return this.state = "AGRESSIVE_WALK";
        } else if (this.deltaPos < 50) {
            return this.state = "ATTACK";
        } else {
            return this.state = "PASSIVE_WALK";
        }
    }
}
