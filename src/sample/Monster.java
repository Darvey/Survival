package sample;


import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Class Abstraite qui gère les monstres
 */
public abstract class Monster extends Entity {

    protected EntityPlayer player;
    protected int innerTimer;
    protected boolean isAggro;

    /**
     * default Constructor
     */
    public Monster(SpriteSheet sprite, String name, int posX, int posY, int width, int height) throws SlickException{

        super(sprite, name, posX, posY, width, height);
        innerTimer = 0;
        isAggro = false;
    }


    /**
     * mise à jour du monstre
     * @param delta : delta pour la loop variable
     */
    public void update(int delta){

        super.update(delta);
        innerTimer += delta;
    }


    /**
     * on renseigne le joueur au monstre
     * @param player
     */
    public void setPlayer(EntityPlayer player){
        this.player = player;
    }
}
