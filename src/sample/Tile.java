package sample;


import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/*
    Description de la classe :
 */
public class Tile {

    private final int id;
    private static final int WIDTH = 32;                     // largeur de l'image
    private static final int HEIGHT = 32;                    // hauteur de l'image
    private final int posX;                             // position en x
    private final int posY;                             // position en y

    protected final boolean solid;                        // true si la tile est solid
    protected final boolean platform;                     // true si la tile est traversable par le dessous


    private SpriteSheet sprite;                   // optimisation : à changer en image


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
        System.out.println("img/tile/"+tileset+"/"+data+".png");
        try {
            this.sprite = new SpriteSheet("img/tile/"+tileset+"/"+data+".png", 32, 32);


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


    /**
     * renvoie l'image de la tile
     * @return : image de la tile
     */
    /* public SpriteSheet getSprite() {

        return this.sprite;
    } */
}