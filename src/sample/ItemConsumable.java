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

    // ********** ATTRIBUTES ********** //

    protected String descriptionAlt;        // description alternative quand on est trop bête
    protected int intelligenceNeeded;       // intelligence nécessaire pour avoir la bonne description

    // ********** CONSTRUCTORS ********** //

    /**
     * Constructor
     *
     * @param pName                     name of the consumable
     * @param pWeight                   weight of the consumable
     * @param pDescription              description of the consumable
     * @param pHaveThumbnail            if the consumable have a thumbnail in img Folder or not
     * @param pType                     type of the consumable
     * @param pFamily                   family of the consumable
     * @param pInventory                inventory where is the item
     * @param pDescriptionAlt           alternative description
     * @param pIntelligenceNeeded       intelligence needed to use the item
     */
    public ItemConsumable(String pName,
                          float pWeight,
                          String pDescription,
                          boolean pHaveThumbnail,
                          String pType,
                          String pFamily,
                          Inventory pInventory,
                          String pDescriptionAlt,
                          int pIntelligenceNeeded)
    {

        this.nbr = 1;
        this.name = pName;
        this.weight = pWeight;
        this.description = pDescription;
        this.type = pType;
        this.family = pFamily;
        this.inventory = pInventory;
        this.descriptionAlt = pDescriptionAlt;
        this.intelligenceNeeded = pIntelligenceNeeded;

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

    // ********** METHODS ********** //

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
