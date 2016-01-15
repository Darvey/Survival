package sample;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Item {

    protected String name;
    protected String description;
    protected float weight;

    protected ImageView image;
    protected Image imagePath;

    // On poura peut être ajouter ici une imgae pour la miniature dans l'inventaire.

    public Item(String name){
        this.name = name;
        this.description = "";

        image = new ImageView();
        Image imagePath = new Image(Main.class.getResourceAsStream("../img/"+name+".png"));
        image.setImage(imagePath);
    }

    /*
        Default Constructor
     */
    public Item(){

    }

    /*
        Getters
     */
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }

    public float getWeight() {
        return weight;
    }

    public ImageView getImage() {
        return image;
    }
}
