package sample;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * Un item
 * peu aussi représenter un groupe d'items identiques du moment que l'attribut 'nbr' dépasse 1.
 */
public class Item {

    protected String name;                  // nom de l'item
    protected String description;           // déscription de l'item
    protected float weight;                 // poid de l'item
    protected int nbr;                      // nombre de cet Item présent dans le groupe

    protected ImageView image;              // node de l'item
    protected Image imagePath;              // image de l'item

    /**
    * Constructor
    * @param name the name of the item
    * @param w the weight of the item
    */
    public Item(String name,float w)
    {
        this.nbr = 1;
        this.name = name;
        this.weight = w;
        this.description = "";
        this.image = new ImageView();
        this.imagePath = new Image(Main.class.getResourceAsStream("../img/"+name+".png"));
        this.image.setImage(imagePath);
        this.image.setOnMouseClicked(mouseListener);
    }

    /**
    * Default Constructor
    */
    public Item(){

    }


    // ----- GETTERS ----- //

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
        return image;
    }

    // others

    final EventHandler<MouseEvent> mouseListener = new EventHandler<MouseEvent>(){


        public void handle(MouseEvent e) {

        }
    };

}
