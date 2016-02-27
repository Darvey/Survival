package sample;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;




public class Bullet {

    /** identifiant */
    private String id;

    /** élément graphique */
    private SpriteSheet sprite;

    /** position de la balle */
    protected int posX;
    protected int posY;
    protected int lastPosX;
    protected int lastPosY;

    /** direction de la balle */
    protected double direction;

    /** vitesse de la balle */
    private final double vel;

    /** pour compter le nombre total de balle */
    private static int number = 0;


    public Bullet(Weapon weapon, double deltaX, double deltaY) throws SlickException{

        this.sprite = new SpriteSheet("img/bullet.png", 32, 32);
        this.number++; // jusqu'à environ 2400 en simultané
        this.posX = weapon.getPosX();
        this.posY = weapon.getPosY();
        this.direction = Math.atan2(deltaY, deltaX) + ((Math.random() * 2 - 1) / 10);
        this.vel = 20 + (Math.random()*2-1);
    }

    /**
     * mise à jour de la balle
     * @return
     */
    public void update(){

        this.lastPosX = this.posX;
        this.lastPosX = this.posY;

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
