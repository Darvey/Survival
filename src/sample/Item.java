package sample;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Item {

    protected String name;
    protected String description;

    protected ImageView image;
    protected Image imagePath;

    // On poura peut être ajouter ici une imgae pour la miniature dans l'inventaire.

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

    public ImageView getImage() {
        return image;
    }
}
