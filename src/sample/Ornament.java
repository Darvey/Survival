package sample;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/*
    Decorative object. Can be placed anywhere on the map. They can be mushrooms, stones etc.
 */
public class Ornament {

    protected int posX; // position en x
    protected int posY; // position en y
    protected int posZ; // position en z

    protected ImageView image;
    protected Image imagePath;

    public Ornament(int x,int y,int z,String path){
        this.posX = x;
        this.posY = y;
        this.posZ = z;

        image = new ImageView();
        image.setTranslateX(posX);
        image.setTranslateY(posY);
        image.setTranslateZ(posZ);
        imagePath = new Image(Main.class.getResourceAsStream("../img/"+path+".png"));
    }

    /*
        Getters
    */
    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getPosZ() {
        return posZ;
    }

    public ImageView getImage() {
        return image;
    }

}
