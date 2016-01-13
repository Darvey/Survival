package sample;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Item {

    protected String name;
    protected String description;

    protected ImageView image;
    protected Image imagePath;

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
