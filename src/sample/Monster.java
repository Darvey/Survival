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

    public Monster(String pName, int pPosX, int pPosY) {

        image = new ImageView();
        Image imagePath = new Image(Main.class.getResourceAsStream("../img/monster/mothaStatic.png"));
        image.setImage(imagePath);

        this.name = pName;
        this.posX = pPosX;
        this.posY = pPosY;
        this.playerPosX = -1000000;
        this.playerPosY = -1000000;

        image.setTranslateY(posY);
        image.setTranslateX(posX);

        timer = 0;

        new AnimationTimer(){
            public void handle(long arg0){
                //behaviourMove();
            }
        }.start();
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
        //System.out.println(deltaPos);
        float aggX = -deltaX / deltaPos * 3;
        float aggY = -deltaY / deltaPos * 3;
        //System.out.println(aggX+" - "+aggY);

        if(deltaPos < 200) {
            //System.out.println("Grrr ! Moi "+this.name+" vois un joueur à "+Math.round(deltaPos)+" pixels de moi");

            this.posX += aggX;
            this.posY += aggY;


        }else{
            this.posX += Math.sin(timer/36) * 4;
            this.posY += Math.cos(timer/8) * 2;

        }

     }
    public void display(){
        this.image.setTranslateX(posX);
        this.image.setTranslateY(posY);
    }

    public ImageView getImage() {
        return image;
    }
}
