package sample;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Consumable extends Item{

    // ********** ATTRIBUTES ********** //

    //protected String name; inherit
    protected int precision;
    protected int damage;
    protected int speed;

    protected String type;
    protected int strenghtNeeded;
    protected int dexterityNeeded;
    protected int intelligenceNeeded;

    // ********** CONSTRUCTORS ********** //

    /**
     * Constructor
     *
     * @param pName             name of the consumable
     * @param pWeight           weight of the consumable
     * @param pHaveThumbnail    if the consumable have a thumbnail or not in img Folder
     * @param pType             Type of the consumable
     * @param pInventory
     */
    public Consumable(String pName, float pWeight, boolean pHaveThumbnail, String pType, Inventory pInventory)
    {
        this.nbr = 1;
        this.name = pName;
        this.weight = pWeight;
        this.type = pType;
        this.inventory = pInventory;

        /*this.description = "";
        this.image = new ImageView();
        this.imagePath = new Image(Main.class.getResourceAsStream("../img/gun/"+name+".png"));
        this.image.setImage(imagePath);*/

        if(pHaveThumbnail) {
            // Image de l'item
            this.itemView = new ImageView();
            this.itemImg = new Image(Main.class.getResourceAsStream("../img/item/"+name+".png"));
            this.itemView.setImage(itemImg);
            // Miniature de l'item
            this.thumbnailView = new ImageView();
            this.thumbnailImg = new Image(Main.class.getResourceAsStream("../img/item/thumbnail/"+name+".png"));
            this.thumbnailView.setImage(thumbnailImg);
            // clic dans l'inventaire
            this.thumbnailView.setOnMouseClicked(mouseListener);
        }else{
            // Image de l'item
            this.itemView = new ImageView();
            this.itemImg = new Image(Main.class.getResourceAsStream("../img/"+name+".png"));
            this.itemView.setImage(itemImg);
        }
    }

    // **********  METHODS ********** //

    public void use(){
        System.out.println("Je mange depuis consumable");
    }





}
