package sample;
import javafx.geometry.BoundingBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/*
    Description de la classe :
 */
public class Tile {

    // ********** ATTRIBUTES ********** //

    protected int width;                            // largeur de l'image
    protected int height;                           // hauteur de l'image
    protected int posX;                             // position en x
    protected int posY;                             // position en y
    protected int posZ;                             // position en z

    protected boolean solid;                        // true if Tile is a solid Element (with a BoundingBox)

    protected ImageView image;
    protected Image imagePath;

    protected BoundingBox bbox;
    
    /**
        Construcor

        @param h    Tile height
        @param w    Tile width
        @param x    X position
        @param y    Y position
        @param z    Z position
        @param id   Tile id
     */
    public Tile(int h, int w, int x, int y, int z, String id) {

        this.width = w;
        this.height = h;
        this.posX = x;
        this.posY = y;
        this.posZ = z;

        this.image = new ImageView();
        this.image.setTranslateX(posX);
        this.image.setTranslateY(posY);
        this.image.setTranslateZ(posZ);


        switch (id) {
            case "0000" :
                imagePath = new Image(Main.class.getResourceAsStream("../img/tile/grass/grass_0.png"));
                this.solid = false;
                break;
            case "0001" :
                imagePath = new Image(Main.class.getResourceAsStream("../img/tile/grass/grass_1.png"));
                this.solid = false;
                break;
            case "0010" :
                imagePath = new Image(Main.class.getResourceAsStream("../img/tile/grass/grass_2.png"));
                this.solid = false;
                break;
            case "0011" :
                imagePath = new Image(Main.class.getResourceAsStream("../img/tile/grass/grass_3.png"));
                this.solid = false;
                break;
            case "0100" :
                imagePath = new Image(Main.class.getResourceAsStream("../img/tile/grass/grass_4.png"));
                this.solid = false;
                break;
            case "0101" :
                imagePath = new Image(Main.class.getResourceAsStream("../img/tile/grass/grass_5.png"));
                this.solid = false;
                break;
            case "0110" :
                //imagePath = new Image(Main.class.getResourceAsStream("../img/tile/grass/highGrass.png"));
                imagePath = new Image(Main.class.getResourceAsStream("../img/collider_32x32.png"));
                this.solid = true;
                break;
            case "0111" :
                imagePath = new Image(Main.class.getResourceAsStream("../img/tile/grass/grass_7.png"));
                this.solid = false;
                break;
            case "1000" :
                imagePath = new Image(Main.class.getResourceAsStream("../img/tile/grass/grass_8.png"));
                this.solid = false;
                break;
            case "1001" :
                imagePath = new Image(Main.class.getResourceAsStream("../img/tile/grass/grass_9.png"));
                this.solid = false;
                break;
            case "1010" :
                imagePath = new Image(Main.class.getResourceAsStream("../img/tile/grass/grass_a.png"));
                this.solid = false;
                break;
            case "1011" :
                imagePath = new Image(Main.class.getResourceAsStream("../img/tile/grass/grass_b.png"));
                this.solid = false;
                break;
            case "1100" :
                imagePath = new Image(Main.class.getResourceAsStream("../img/tile/grass/grass_c.png"));
                this.solid = false;
                break;
            case "1101" :
                imagePath = new Image(Main.class.getResourceAsStream("../img/tile/grass/grass_d.png"));
                this.solid = false;
                break;
            case "1110" :
                imagePath = new Image(Main.class.getResourceAsStream("../img/tile/grass/grass_e.png"));
                this.solid = false;
                break;
            case "1111" :
                imagePath = new Image(Main.class.getResourceAsStream("../img/tile/grass/grass_f.png"));
                this.solid = false;
                break;
            default:
                imagePath = new Image(Main.class.getResourceAsStream("../img/tile/grass/grass_0.png"));
                this.solid = false;
                break;
        }
        image.setImage(imagePath);
        image.setVisible(true);
    }


    // ********** SETTERS ********** //

    public void setSolid(boolean b)
    {
        this.solid = b;
    }

    // ********** GETTERS ********** //

    public boolean getSolid()
    {
        return solid;
    }

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