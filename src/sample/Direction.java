package sample;

/**
 * Created by davidperrey on 23/01/2016.
 */
public class Direction {

    private int x;
    private int y;

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

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}
