package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Monster extends Entity implements MoveListener {

    /*
    entity :
    health
    dodge
    stealth
    moveSpeed
    */
    protected String name;
    protected int reach;
    protected int aggressivity; //0 : fuyard, 1 : se défend, 2 : agressif

    // pour tester ( plus tard on utilisera des tableaux pour les animations... )
    protected ImageView image;
    protected Image imagePath;

    protected int timer;

    protected float playerPosX;
    protected float playerPosY;

    protected String state;

    protected int timeAttack;

    public Monster(String pName, int pPosX, int pPosY) {

        image = new ImageView();
        Image imagePath = new Image(Main.class.getResourceAsStream("../img/monster/mothaStatic.png"));
        image.setImage(imagePath);

        this.name = pName;
        this.posX = pPosX;
        this.posY = pPosY;
        this.playerPosX = -1000000;
        this.playerPosY = -1000000;
        System.out.println(posX);
        image.setTranslateY(this.posY);
        image.setTranslateX(this.posX);

        timer = 0;

        state = new String("PASSIVE_WALK");

        timeAttack = 0;

    }

    public void playerIsMoving(float pPosX, float pPosY){

        //System.out.println("Grrr ! Moi "+this.name+" vois un joueur situé en :"+pPosX+"/"+pPosY);
        this.playerPosX = pPosX;
        this.playerPosY = pPosY;
    }

    public void behaviourMove(){

        timer += 1;

        float deltaX = this.posX - this.playerPosX;
        float deltaY = this.posY - this.playerPosY;
        float deltaPos = (float)Math.sqrt((float)Math.pow(deltaX, 2) + (float)Math.pow(deltaY, 2));
        float aggX;
        float aggY;

        if(deltaPos != 0) {
            aggX = -deltaX / deltaPos * 3;
            aggY = -deltaY / deltaPos * 3;
        }else{
            aggX = 0;
            aggY = 0;
        }

        //ici, quelle est son statut ? en fonction de la position du joueur etc.
        if(deltaPos < 200 && deltaPos >= 50){
            this.state = "AGRESSIVE_WALK";
        }else if(deltaPos < 50){
            this.state = "ATTACK";
        }else{
            this.state = "PASSIVE_WALK";
        }

        //ici, quelle est son comportement, par statut
        switch(this.state) {
            case "AGRESSIVE_WALK":
                this.posX += aggX;
                this.posY += aggY;
                break;
            case "ATTACK":
                attack();
                break;
            case "PASSIVE_WALK":
                this.posX += Math.sin(timer/36) * 4;
                this.posY += Math.cos(timer/8) * 2;
                break;
            default:
                break;
        }
    }
    public void display(){
        this.image.setTranslateX(this.posX);
        this.image.setTranslateY(this.posY);
    }

    public void attack(){

        if(timeAttack == 0){
            timeAttack = 120;
            System.out.println("Grrr, je t'attaque toutes les "+timeAttack+" frames ! Prends ça dans ta gueule !");
        }else{
            timeAttack--;
        }
    }

    public ImageView getImage() {

        return image;
    }
}
