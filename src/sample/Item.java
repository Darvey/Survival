package sample;

/**
 * Item
 * peu aussi représenter un groupe d'items identiques du moment que l'attribut 'nbr' dépasse 1.
 */

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

abstract class Item {

    // ********** ATTRIBUTES ********** //

    protected static Label txtItemOnclic = new Label();

    protected String name;                  // nom de l'item
    protected String description;           // déscription de l'item
    protected float weight;                 // poid de l'item
    protected int nbr;                      // nombre de cet Item présent dans le groupe

    protected ImageView itemView;           // node de l'item
    protected Image itemImg;                // image de l'item

    protected ImageView thumbnailView;      // thumbnail node
    protected Image thumbnailImg;           // thumbnail image

    protected String type;                  // type de l'item (food / drink...)
    protected String family;                // famille de l'item (champignon / herbe / médicament / potion...)

    protected Inventory inventory;          // inventaire qui contient l'item

    // ********** CONSTRUCTORS ********** //

    /**
    * Constructor
    * @param pName              the name of the item
    * @param pWeight            the weight of the item
    * @param pHaveThumbnail     if the item has a thumbnail
    * @param pType              type of the item (weapon / consumable / tool / junk)
    * @param pInventory         inventory which contains the item
    */
    public Item(String pName,
                float pWeight,
                boolean pHaveThumbnail,
                String pType,
                Inventory pInventory)
    {
        this.nbr = 1;
        this.name = pName;
        this.weight = pWeight;
        this.description = "";
        this.inventory = pInventory;
        this.type = pType;

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
            this.itemImg = new Image(Main.class.getResourceAsStream("../img/item/"+name+".png"));
            this.itemView.setImage(itemImg);
        }
    }


    // ********** METHODES ********** //


    /**
    * Default Constructor
    */
    public Item(){

    }

    /**
     * Method that returns the name, description, and the weight of an item as a string
     * @return String value
     */
    public String toStringItem()
    {
        return this.name+" : \n"+
                        this.description+" \n"+
                        "poids : "+ this.weight;
    }

    /**
     * Action for a thumbnail leftclic
     */
    final EventHandler<MouseEvent> mouseListener = e -> {

        switch(e.getButton()){
            case PRIMARY:
                //affichage de la description
                txtItemOnclic.setText(toStringItem());
                break;
            case SECONDARY:
                //utilisation de l'item
                use();
                break;
        }
    };

    public void use(){
        type = this.getType();
        switch(type){
            case "weapon":
                System.out.println("Equippement de l'arme");
                break;
            case "tool":
                System.out.println("Utilisation de l'outil");
                break;
            case "consumable":
                System.out.println("Consommation du consommable");
                // fonction effet()
                this.setNbr(this.getNbr()-1);
                this.inventory.labelMap.get(this.getName()).setText(Integer.toString(this.getNbr()));
                this.inventory.refreshItemList();
                break;
            case "junk":
                System.out.println("Putain ça sert à rien ce truc");
                break;
            default:
                break;
        }
    }

    // ********** SETTERS ********** //

    public void setNbr(int nbr) {
        this.nbr = nbr;
    }

    // ********** GETTERS ********** //

    public String getType(){
        return this.type;
    }

    public static Label getTxtItemOnclic() {
        return txtItemOnclic;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public float getWeight() {
        return this.weight * this.nbr;
    }

    public int getNbr() {
        return this.nbr;
    }

    public ImageView getImage() {
        return this.itemView;
    }

    public ImageView getThumbnail(){
        return this.thumbnailView;
    }

}
