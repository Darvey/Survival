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

    protected boolean solid;

    protected ImageView image;
    protected Image imagePath;

    /*
        Constructeur de la classe.
        @param h Tile height
        @param w Tile width
        @param x X position
        @param y Y position
        @param z Z position
        @param id Tile id
     */
    public Tile(int h, int w, int x, int y, int z, int id) {
        this.width = w;
        this.height = h;
        this.posX = x;
        this.posY = y;
        this.posZ = z;

        image = new ImageView();
        image.setTranslateX(posX);
        image.setTranslateY(posY);
        image.setTranslateZ(posZ);

        switch (id) {
            case 1:
                imagePath = new Image(Main.class.getResourceAsStream("../img/ground.png"));
                this.solid = false;
                break;
            case 2:
                imagePath = new Image(Main.class.getResourceAsStream("../img/grass.png"));
                this.solid = false;
                break;
            default:;
        }
        image.setImage(imagePath);
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