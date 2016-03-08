package sample;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Created by Administrateur on 02/03/2016.
 */
public class Heal extends Skill {

    /**
     * default Constructor
     */
    public Heal(EntityPlayer player) throws SlickException{

        super(player);
        this.sprite = new SpriteSheet("img/skill/heal.png", 35, 39);
        this.name = "Soin";
        this.description = "Soigne de 30 points de vie le joueur ciblé";
        this.cooldown = 10;
        this.manaCost = 20;
        this.effectTime = 0;
        this.launchTime = 0;
        this.targeted = true;
        this.type = "HEAL";
    }


    /**
     * utilisation du sort
     */
    @Override
    public void use( ){
        super.use();
        System.out.println("HEAL!");
        if(this.target != null){
            if(this.target instanceof EntityPlayer){
                System.out.println("Je lance le sort car c'est un joueur");
            }else{
                System.out.println("Je ne lance pas le sort car ce n'est pas un joueur");
            }
        }else{
            System.out.println("Je ne lance pas le sort car je vise à côté");
        }
        /*
        if((player.pv + 30) >= player.maxPV){
        player.pv = player.maxPV;
        }else{
        player.pv += 30;
        }
        */
    }
}
