package sample;

/**
 * Created by Administrateur on 02/03/2016.
 */
public class Bolt extends Skill {

    /**
     * default Constructor
     */
    public Bolt(EntityPlayer player){

        super(player);
        this.name = "Foudre";
        this.description = "Inflige 10 dégats au joueur ou au monstre ciblé";
        this.cooldown = 10;
        this.manaCost = 20;
        this.effectTime = 0;
        this.launchTime = 0;
        this.targeted = true;
        this.type = "DAMAGE";
    }


    /**
     * utilisation du sort
     */
    @Override
    public void use( ){
        super.use();
        System.out.println("BOLT!");
        if(this.target != null){
            System.out.println("Je lance le sort car c'est un joueur ou un monstre");
            this.target.health -= 10;
        }else{
            System.out.println("Je ne lance pas le sort car je vise à côté");
        }

    }
}
