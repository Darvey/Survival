package sample;

/**
 * A simple class to represent a position on a 2D grid
 */

public class Position {

    // ********** ATTRIBUTES ********** //

    private int x;
    private int y;

    // ********** CONSTRUCTORS ********** //

    /**
     * Constructor
     *
     * @param x     the position on the X axis
     * @param y     the position on the Y axis
     */
    public Position(int x,int y){
        this.x = x;
        this.y = y;
    }

    // ********** SETTERS ********** //

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    // ********** GETTERS ********** //

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
