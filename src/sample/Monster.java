package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Monster extends Entity {

    /*
    entity :
    health
    dodge
    stealth
    moveSpeed
    */
    private int reach;
    private int aggressivity; //0 : fuyard, 1 : se défend, 2 : agressif

    // pour tester ( plus tard on utilisera des tableaux pour les animations... )
    protected ImageView image;
    protected Image imagePath;

    protected int timer;

    public Monster() {

        image = new ImageView();
        Image imagePath = new Image(Main.class.getResourceAsStream("../img/mothaBlue.png"));
        image.setImage(imagePath);

        posX = 200f;
        posY = 200f;

        image.setTranslateY(posY);
        image.setTranslateX(posX);

        timer = 0;

        new AnimationTimer(){
            public void handle(long arg0){
                behaviourMove();
            }
        }.start();
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
