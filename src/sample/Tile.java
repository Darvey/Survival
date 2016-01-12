package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/*
    Description de la classe :
 */
public class Tile {

    protected int width; // largeur de l'image
    protected int height; // hauteur de l'image
    protected int posX; // position en x
    protected int posY; // position en y
    protected int posZ; // position en z
    protected ImageView image;
    protected Image imagePath;

    /*
        Constructeur de la classe.
        @param w largeur de l'image
        @param h hauteur de l'imgae
        @param x position en x
        @param y position en y
        @param z position en z
     */
    public Tile(int w, int h, int x, int y, int z, int id) {
        this.width = w;
        this.height = h;
        this.posX = x;
        this.posY = y;
        this.posZ = z;

        image = new ImageView();
        image.setTranslateX(posX);
        image.setTranslateY(posY);

        switch (id) {
            case 1:
                imagePath = new Image(Main.class.getResourceAsStream("/img/ground.png"));
                break;
            case 2:
                imagePath = new Image(Main.class.getResourceAsStream("../img/grass.png"));
                break;
            default:;
        }

        image.setVisible(true);
    }

    /*
        Getters
     */
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

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