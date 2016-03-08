package sample;

import org.newdawn.slick.SpriteSheet;

/**
 * Created by Administrateur on 02/03/2016.
 */
public abstract class Skill {

    /** joueur qui lance la compétence */
    protected EntityPlayer player;

    protected SpriteSheet sprite;
    /** temps avant que l'on puisse relancé le sort */
    protected int cooldown;
    /** coût en mana pour lancer le sort */
    protected int manaCost;
    /** nom du sort */
    protected String name;
    /** description du sort */
    protected String description;
    /** temps pendant lequel le sort fait effet (0 = instantané) */
    protected int effectTime;
    /** temps que l'on met pour lancer le sort */
    protected int launchTime;
    /** sort ciblé ou non */
    protected boolean targeted;
    protected Entity target;

    /**
     * type du sort
     *
     * HEAL
     * PREVENT
     * RESISTANCE
     * DAMAGE
     * CREATION
     * ENTITY_ALTERATION
     * ITEM_ALTERATION
     * BUFF
     * DEBUFF
     */
    protected String type;


    /**
     * default Constructor
     */
    public Skill(){
        this(null);
    }


    /**
     * Constructor
     * @param player
     */
    public Skill(EntityPlayer player){

        this.player = player;
    }


    /**
     * utilisation de la compétence
     */
    public void use(){

        if(this.targeted){
            this.target = this.player.getEntityUnderMouse();;
        }

    }
}

