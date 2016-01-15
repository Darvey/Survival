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

    public Monster(String pName, float pPosX, float pPosY) {

        image = new ImageView();
        Image imagePath = new Image(Main.class.getResourceAsStream("../img/mothaBlue.png"));
        image.setImage(imagePath);

        this.name = pName;
        this.posX = pPosX;
        this.posY = pPosY;

        image.setTranslateY(posY);
        image.setTranslateX(posX);

        timer = 0;

        new AnimationTimer(){
            public void handle(long arg0){
                behaviourMove();
            }
        }.start();
    }

    public void playerIsMoving(float pPosX, float pPosY){

        System.out.println("Grrr ! Moi "+this.name+" vois un joueur situé en :"+pPosX+"/"+pPosY);
    }

    public void behaviourMove(){

        timer += 1;
        this.posX += Math.sin(timer/36) * 4;
        this.image.setTranslateX(posX);
        this.posY += Math.cos(timer/8) * 2;
        this.image.setTranslateY(posY);
    }

    public ImageView getImage() {
        return image;
    }
}
