package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Translate;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

//import java.util.UUID;


public class Bullet {


    private SpriteSheet sprite;
    protected int posX;
    protected int posY;
    protected double direction;
    protected boolean visibility;
    private final double vel;
    private String id;
    private static int number = 0;


    public Bullet(Weapon weapon, double deltaX, double deltaY) throws SlickException{
        //this.id = UUID.randomUUID().toString();

        this.sprite = new SpriteSheet("img/bullet.png", 32, 32);
        this.number++; //2400!
        //double spread = Math.random() * 2 - 1;
        this.posX = weapon.getPosX();
        this.posY = weapon.getPosY();
        this.direction = Math.atan2(deltaY, deltaX) + ((Math.random() * 2 - 1) / 10);
        this.vel = 20 + (Math.random()*2-1);

        //this.image.setVisible(pVisibility);
    }

    /**
     * mise à jour de la balle
     * @return
     */
    public void update(){

        this.posX += Math.cos(this.direction) * this.vel;
        this.posY += Math.sin(this.direction) * this.vel;
    }

    /**
     * rendu graphique de la balle
     */
    public void render(){

        this.sprite.draw(this.posX, this.posY);
    }
}
