package sample;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * Created by Administrateur on 24/01/2016.
 */



public class ItemWeapon extends Item {

    private static Label txtItemOnclic = new Label();

    protected String name;                  // nom de l'item
    protected String description;           // déscription de l'item
    protected float weight;                 // poid de l'item
    protected int nbr;                      // nombre de cet Item présent dans le groupe

    protected ImageView itemView;           // node de l'item
    protected Image itemImg;                // image de l'item

    protected ImageView thumbnailView;      // thumbnail node
    protected Image thumbnailImg;           // thumbnail image

    protected String type;                  // type de l'item (gun / distance / cac)
    protected String family;                // famille de l'item (épée / masse / revolrer...)

    protected Inventory inventory;          // inventaire qui contient l'item


    /* add */

    protected int strenghtNeeded;           // force nécessaire pour avoir la bonne description
    protected int dexterityNeeded;          // dexterité nécessaire pour avoir la bonne description
    protected int intelligenceNeeded;       // intelligence nécessaire pour avoir la bonne description

    protected int precision;                // precision de l'arme
    protected int damage;                   // dégat de l'arme
    protected int speed;                    // vitesse de l'arme
    protected String damageType;            // type de dégat (contondant / perforant / tranchant)
    protected String associateItem;         // item associé pour l'utilisation (balles / flèche...)


    public ItemWeapon(String pName,
                      float pWeight,
                      String pDescription,
                      boolean pHaveThumbnail,
                      String pType,
                      String pFamily,
                      Inventory pInventory,
                      int pStrenghtNeeded,
                      int pDexterityNeeded,
                      int pIntelligenceNeeded,
                      int pPrecision,
                      int pDamage,
                      int pSpeed,
                      String pDamageType,
                      String pAssociateItem)

    {
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
                    //utilisation de l'arme (équippement)
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

        System.out.println("Equippement de l'arme depuis ItemWeapon");
        // l'arme n'est pas consommé
        /*this.setNbr(this.getNbr()-1);
        this.inventory.labelMap.get(this.getName()).setText(Integer.toString(this.getNbr()));
        this.inventory.refreshItemList();*/

        // => effet de l'arme


    }

}
