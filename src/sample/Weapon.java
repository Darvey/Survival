package sample;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Weapon extends Item{

    //protected String name; inherit
    protected int precision;
    protected int damage;
    protected int speed;
    protected String type;
    protected int strenghtNeeded;
    protected int dexterityNeeded;
    protected int inteligenceNeeded;

    /**
     * Constructor
     */
    public Weapon(String pName,
                  float pWeight,
                  boolean pHaveThumbnail,
                  String pType,
                  Inventory pInventory,
                  int pPrecision,
                  int pDamage,
                  int pSpeed,
                  int pStrenghtNeeded,
                  int pDexterityNeeded,
                  int pInteligenceNeeded)
    {
        this.name = pName;
        this.weight = pWeight;
        this.type = pType;
        this.inventory = pInventory;
        this.precision = pPrecision;
        this.damage = pDamage;
        this.speed = pSpeed;
        this.strenghtNeeded = pStrenghtNeeded;
        this.dexterityNeeded = pDexterityNeeded;
        this.inteligenceNeeded = pInteligenceNeeded;

        /*this.description = "";
        this.image = new ImageView();
        this.imagePath = new Image(Main.class.getResourceAsStream("../img/gun/"+name+".png"));
        this.image.setImage(imagePath);*/
    }





}
