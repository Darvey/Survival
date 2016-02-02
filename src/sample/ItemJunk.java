package sample;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * Created by Administrateur on 24/01/2016.
 */



public class ItemJunk extends Item {

    protected String descriptionAlt;        // description alternative quand on est trop bête
    protected int intelligenceNeeded;       // intelligence nécessaire pour avoir la bonne description

    public ItemJunk(String pName,
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

        System.out.println("Il se passe rien car c'est de la merde ce truc");
        // l'item est pas consommé, c'est de la merde je t'ai dit
        /*this.setNbr(this.getNbr()-1);
        this.inventory.labelMap.get(this.getName()).setText(Integer.toString(this.getNbr()));
        this.inventory.refreshItemList();*/
    }

}
