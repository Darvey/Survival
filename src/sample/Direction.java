package sample;

/**
 * Created by davidperrey on 23/01/2016.
 *
 *  in the game we consider the directions in this way :
 *
 *  x = 1        EST
 *  x = -1       WEST
 *  y = 1        SOUTH
 *  y = -1       NORTH
 *
 */
public class Direction {

    // ********** ATTRIBUTES ********** //

    private int x;
    private int y;

    // ********** CONSTRUCTORS ********** //


    /**
     * Constructor
     *
     * @param pX    direction on the X axis
     * @param pY    direction on the Y axis
     */
    public Direction(int pX,int pY)
    {
        if( pX > -2 && pX < 2 && pY > -2 && pY < 2)
        {
            this.x = pX;
            this.y = pY;
        }
        else
        {
            System.out.print("erreur init direction : mauvaises valeurs");
        }
    }

    // ********** SETTERS ********** //

    public void setY(int pY) {
        if(pY > -2 && pY < 2)
        {
            this.y = pY;
        }
    }

    public void setX(int pX) {
        if(pX > -2 && pX < 2)
        {
            this.x = pX;
        }
    }

    // ********** GETTERS ********** //

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}
