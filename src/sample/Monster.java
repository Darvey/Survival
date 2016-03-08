package sample;


import org.newdawn.slick.*;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;

import java.util.Iterator;
import java.util.List;

/**
 * Class Abstraite qui gère les monstres
 */
public abstract class Monster extends Entity {

    /** joueur */
    protected EntityPlayer player;

    /** caractéristiques du monstre */
    protected boolean isAggro;
    protected boolean isJumping;
    protected boolean isDead;
    protected boolean isHit;
    protected double hitDirection; // recul du monstre quand il est touché (même direction que les balles)

    protected Rectangle hitBox;

    protected Sound moveSound;
    protected Sound dyingSound;
    protected Sound jumpingSound;


    /**
     * default Constructor
     */
    public Monster(SpriteSheet sprite, String name, int posX, int posY, int width, int height) throws SlickException{

        super(sprite, name, posX, posY, width, height);

        this.isAggro = false;
        this.isJumping = false;
        this.isDead = false;
        this.isHit = false;
        this.hitBox = new Rectangle(posX, posY, width, height);
    }


    public void init(){

    }


    /**
     * mise à jour du monstre
     * @param delta : delta pour la loop variable
     */
    @Override
    public void update(int delta){

        super.update(delta);

        this.hitBox.setX(this.posX);
        this.hitBox.setY(this.posY);


        hit();
        if(isHit){
            this.accX = (float) Math.cos(hitDirection) * 8;
            this.accY = (float) Math.sin(hitDirection) * 8;
            isHit = false;
            System.out.println("Name : "+this.name+" / PV : "+this.health+" / isDead? "+this.isDead);
        }

        if(this.health <= 0){
            this.isDead = true;
        }
    }


    /**
     * rendu graphique
     * @param g : slick graphics
     */
    @Override
    public void render(Graphics g){

        g.drawAnimation(getAnimations(this.state), this.posX, this.posY);
    }


    /**
     * on renseigne le joueur au monstre
     * @param player
     */
    public void setPlayer(EntityPlayer player){

        this.player = player;
    }


    /**
     * renvoie la distance entre le monstre et le joueur
     * @return : la distance entre le monstre et le joueur
     */
    public int getDeltaPlayer(){

        int deltaXPlayer = Math.abs(this.posX - this.player.posX);
        int deltaYPlayer = Math.abs(this.posY - this.player.posY);
        return (int) Math.sqrt(Math.pow(deltaXPlayer, 2) + Math.pow(deltaYPlayer, 2));
    }


    /**
     * calcul de collision entre le monstre et les balles
     */
    public void hit(){

        List<Bullet> bulletList = this.player.getWeapon().getBulletList();
        Iterator<Bullet> iterator;

        iterator = bulletList.iterator();

        Line line = new Line(0, 0, 0, 0);

        /** update des balles et suppression */
        while(iterator.hasNext()){

            Bullet bullet = iterator.next();

            line.set(bullet.lastPosX, bullet.lastPosY, bullet.posX, bullet.posY);


            if(line.intersects(this.hitBox)){
                System.out.println("BULLET : "+bullet.lastPosX+", "+bullet.lastPosY+", "+bullet.posX+", "+bullet.posY);
                System.out.println("HITBOX : "+this.hitBox.getX()+", "+this.hitBox.getY()+", "+this.hitBox.getWidth()+", "+this.hitBox.getHeight());
                this.health -= bullet.damage;
                this.isHit = true;
                this.hitDirection = bullet.direction;
                iterator.remove();
            }

            if(this.health <= 0){
                this.isDead = true;
            }
        }
    }
}
