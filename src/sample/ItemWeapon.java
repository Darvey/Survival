package sample;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.newdawn.slick.SlickException;

/**
 * Created by Administrateur on 24/01/2016.
 */



public class ItemWeapon extends Item {

    protected int strenghtNeeded;           // force nécessaire pour avoir la bonne description
    protected int dexterityNeeded;          // dexterité nécessaire pour avoir la bonne description
    protected int intelligenceNeeded;       // intelligence nécessaire pour avoir la bonne description

    protected int precision;                // precision de l'arme
    protected int damage;                   // dégat de l'arme
    protected int speed;                    // vitesse de l'arme
    protected String damageType;            // type de dégat (contondant / perforant / tranchant)
    protected String associateItem;         // item associé pour l'utilisation (balles / flèche...)


    public ItemWeapon() throws SlickException {

        this.type = "weapon";

    }
        /*
    }
        this.nbr = 1;
        this.name = pName;
        this.weight = pWeight;
        this.description = pDescription;
        this.type = pType;
        this.family = pFamily;
        this.inventory = pInventory;

        this.strenghtNeeded = pStrenghtNeeded;
        this.dexterityNeeded = pDexterityNeeded;
        this.intelligenceNeeded = pIntelligenceNeeded;
        this.precision = pPrecision;
        this.damage = pDamage;
        this.speed = pSpeed;
        this.damageType = pDamageType;
        this.associateItem = pAssociateItem;
        */




}
