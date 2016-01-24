package sample;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * Created by Administrateur on 24/01/2016.
 */



public class ItemConsumable extends Item {

    private static Label txtItemOnclic = new Label();

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


    /* add */

    protected String descriptionAlt;        // description alternative quand on est trop bête
    protected int intelligenceNeeded;       // intelligence nécessaire pour avoir la bonne description

    public ItemConsumable(String pName,
                          float pWeight,
                          String pDescription,
                          boolean pHaveThumbnail,
                          String pType,
                          String pFamily,
                          Inventory pInventory,
                          String descriptionAlt,
                          int intelligenceNeeded)
    {
        this.nbr = 1;
        this.name = pName;
        this.weight = pWeight;
        this.description = pDescription;
        this.type = pType;
        this.family = pFamily;
        this.inventory = pInventory;

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
                    //consommation de l'item
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

        System.out.println("Consommation du consommable depuis ItemConsumable");
        // l'item est consommé
        this.setNbr(this.getNbr()-1);
        this.inventory.labelMap.get(this.getName()).setText(Integer.toString(this.getNbr()));
        this.inventory.refreshItemList();

        // => effet du consommable


    }

}
