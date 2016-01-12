package sample;

/*
    Description de la classe :

 */
public class Tile {

    protected int width; // largeur de l'image
    protected int height; // hauteur de l'image
    protected int posX; // position en x
    protected int posY; // position en y

    /*
        Constructeur de la classe.
        @param w largeur de l'image
        @param h hauteur de l'imgae
        @param x position en x
        @param y position en y
     */
    public Tile(int w,int h,int x,int y){
        this.width = w;
        this.height = h;
        this.posX = x;
        this.posY = y;
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
}
