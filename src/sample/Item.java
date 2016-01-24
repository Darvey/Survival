package sample;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import sun.plugin2.util.ColorUtil;

/**
 * Un item
 * peu aussi représenter un groupe d'items identiques du moment que l'attribut 'nbr' dépasse 1.
 */
abstract class Item {


    private static Label txtItemOnclic = new Label();

    protected String name;                  // nom de l'item
    protected String description;           // déscription de l'item
    protected float weight;                 // poid de l'item
    protected int nbr;                      // nombre de cet Item présent dans le groupe

    protected ImageView itemView;           // node de l'item
    protected Image itemImg;                // image de l'item

    protected ImageView thumbnailView;      // thumbnail node
    protected Image thumbnailImg;           // thumbnail image

    protected Inventory inventory;          // inventaire qui contient l'item
    protected String type;                  // type de l'item (weapon / tool / consumable / junk)

    /**
    * Constructor
    * @param pName the name of the item
    * @param pWeight the weight of the item
    * @param pHaveThumbnail if the item has a thumbnail
    * @param pType type of the item (weapon / consumable / tool / junk)
    * @param pInventory inventory which contains the item
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
            this.itemImg = new Image(Main.class.getResourceAsStream("../img/"+name+".png"));
            this.itemView.setImage(itemImg);
        }
    }

    /**
    * Default Constructor
    */
    public Item(){

    }

    /**
     * @return a String with each value of the item
     */
    public String toStringItem()
    {
        String res =    this.name+" : \n"+
                        this.description+" \n"+
                        "poids : "+this.weight;
        return res;
    }

    // ----- GETTERS ----- //


    public static Label getTxtItemOnclic() {
        return txtItemOnclic;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getWeight() {
        return weight * nbr;
    }

    public int getNbr() {
        return nbr;
    }

    public void setNbr(int nbr) {
        this.nbr = nbr;
    }

    public ImageView getImage() {
        return this.itemView;
    }

    public ImageView getThumbnail(){

        return this.thumbnailView;
    }

    /**
     * Action for a thumbnail leftclic
     */
    final EventHandler<MouseEvent> mouseListener = new EventHandler<MouseEvent>(){
        public void handle(MouseEvent e) {

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

            //System.out.println(inventory.player.constitution);
            //System.out.println(nbr);
            //inventory.player.constitution++;
            //nbr = 12;
            //inventory.deleteItem(getName());
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

    public String getType(){
        return this.type;
    }
}
