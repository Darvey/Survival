package sample;


import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Class qui gère une tile
 *
 * ******** OPTIMISATION ********
 * - modifier le sprite en image
 * (à moins de faire un environnement qui bouge)
 */
public class Tile {

    /** identifiant */
    private final int id;

    /** élément graphique */
    private SpriteSheet sprite;

    /** position et dimensions */
    private final int posX;
    private final int posY;
    private static final int WIDTH = 32;
    private static final int HEIGHT = 32;

    /** caractéristiques de la tile */
    protected final boolean solid;
    protected final boolean platform; // true si la tile est traversable par le dessous


    /**
     * constructor
     * @param data : id de la tile (dans le fichier xml)
     * @param posX : position X
     * @param posY : positon Y
     * @param tileIndex : id de la tile (dans le level)
     * @throws SlickException
     */
    public Tile(String data, int posX, int posY, int tileIndex) throws SlickException{

        this.id = tileIndex;
        data = data.trim(); //suppression des espaces
        this.posX = posX;
        this.posY = posY;
        
        switch(data){
            case "0": // vide
                this.solid = false;
                this.platform = false;
                break;
            case "1": // solid non traversable par le dessous
                this.solid = true;
                this.platform = false;
                break;
            case "2": // solid traversable par le dessous (plate-forme)
                this.solid = true;
                this.platform = true;
                break;
            default:
                this.solid = false;
                this.platform = false;
                break;
        }
    }


    /**
     * init de l'image
     * @param data : id de la tile dans le tileset
     * @param tileset : tilet à utiliser (répertoire d'image)
     */
    public void initGraphic(String data, String tileset){

        data = data.trim();
        String sData = "";
        switch(data){
            case "0":
                sData = "deep";
                break;
            case "1":
                sData = "underground";
                break;
            case "2":
                sData = "ground1";
                break;
            case "3":
                sData = "ground2";
                break;
            case "4":
                sData = "block1";
                break;
            case "5":
                sData = "block2";
                break;
            case "6":
                sData = "block3";
                break;
            case "7":
                sData = "block4";
                break;
            case "8":
                sData = "block5";
                break;
            case "9":
                sData = "block6";
                break;
            case "a":
                sData = "block7";
                break;
            case "b":
                sData = "block8";
                break;
            case "c":
                sData = "underground2";
                break;
            default:
                sData = "deep";
                break;
        }

        try {
            this.sprite = new SpriteSheet("img/tile/"+tileset+"/"+sData+".png", 32, 32);
        }catch(SlickException e){
            e.printStackTrace();
        }
    }


    /**
     * rendu graphique
     */
    public void render(){

        this.sprite.draw(this.posX * WIDTH, this.posY * HEIGHT);
    }
}