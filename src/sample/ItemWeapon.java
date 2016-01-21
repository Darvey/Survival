package sample;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ItemWeapon extends Item{

    //protected String name; inherit
    protected int precision;
    protected int damage;
    protected int speed;
    protected int newAttr;
    protected int type;
    protected int strenghtNeeded;
    protected int dexterityNeeded;
    protected int inteligenceNeeded;

    /**
    * Constructor
    */
    public ItemWeapon(String name,
                      float weight,
                      int precision,
                      int damage,
                      int speed,
                      int type,
                      int strenghtNeeded,
                      int dexterityNeeded,
                      int inteligenceNeeded)
    {
        this.name = name;
        this.weight = weight;
        this.precision = precision;
        this.damage = damage;
        this.speed = speed;
        this.type = type;
        this.strenghtNeeded = strenghtNeeded;
        this.dexterityNeeded = dexterityNeeded;
        this.inteligenceNeeded = inteligenceNeeded;

        /*this.description = "";
        this.image = new ImageView();
        this.imagePath = new Image(Main.class.getResourceAsStream("../img/gun/"+name+".png"));
        this.image.setImage(imagePath);*/
    }





}
