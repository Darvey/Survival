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
public class Item {


    private static Label txtItemOnclic = new Label();

    protected String name;                  // nom de l'item
    protected String description;           // déscription de l'item
    protected float weight;                 // poid de l'item
    protected int nbr;                      // nombre de cet Item présent dans le groupe

    protected ImageView itemView;           // node de l'item
    protected Image itemImg;                // image de l'item

    protected ImageView thumbnailView;      // thumbnail node
    protected Image thumbnailImg;           // thumbnail image

    /**
    * Constructor
    * @param name the name of the item
    * @param w the weight of the item
    */
    public Item(String name,float w,boolean haveThumbnail)
    {
        this.nbr = 1;
        this.name = name;
        this.weight = w;
        this.description = "";

        if(haveThumbnail) {
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
                        "poid : "+this.weight;
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
     * Action for a thumbnail clic
     */
    final EventHandler<MouseEvent> mouseListener = new EventHandler<MouseEvent>(){
        public void handle(MouseEvent e) {
            txtItemOnclic.setText(toStringItem());
        }
    };

}
