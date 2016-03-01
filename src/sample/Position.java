package sample;

/**
 * Classe qui représente une position X, Y sur la carte
 */
public class Position {

    /** position en X */
    private int x;
    /** position en Y */
    private int y;


    /**
     * default Constructor
     */
    public Position(){

        this(-1, -1);
    }


    /**
     * Constructor
     *
     * @param x     the position on the X axis
     * @param y     the position on the Y axis
     */
    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }


    /**
     * donne la position en X
     * @param x : la position en X
     */
    public void setX(int x) {

        this.x = x;
    }

    /**
     * donne la position en Y
     * @param y : la position en Y
     */
    public void setY(int y) {

        this.y = y;
    }


    /**
     * renvoie la position en Y
     */
    public int getX() {
        return x;
    }


    /**
     * renvoie la position en Y
     */
    public int getY() {
        return y;
    }
}
