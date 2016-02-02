package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.UUID;

/**
 * Created by Administrateur on 02/02/2016.
 */
public class Bullet {

    protected ImageView image;
    protected Image imagePath;
    protected double posX;
    protected double posY;
    protected double direction;
    protected double vel;
    protected double spread;
    protected String id;

    public Bullet(int pPosX, int pPosY, double pDirection){
        this.id = UUID.randomUUID().toString();
        image = new ImageView();
        imagePath = new Image(Main.class.getResourceAsStream("../img/bullet.png"));
        image.setImage(imagePath);
        this.spread = (double)Math.random()*2-1;
        this.posX = pPosX;
        this.posY = pPosY;
        this.direction = pDirection + (this.spread / 10);
        this.vel = 10 + (Math.random()*2-1);
        this.spread = 0;

        image.setTranslateX(posX);
        image.setTranslateY(posY);
        //image.setTranslateZ(-15);

    }

    public void update(){
        this.posX += Math.cos(this.direction) * this.vel;
        this.posY += Math.sin(this.direction) * this.vel;
    }

    public void display(){
        image.setTranslateX(posX);
        image.setTranslateY(posY);
    }

    public ImageView getImage(){
        return this.image;
    }
}
