package sample;

public class Entity {

    protected int health;
    protected float moveSpeed;
    protected int dodge;
    protected int stealth;

    // position du joueur sur la carte
    protected int posX;
    protected int posY;

    public Entity(){

    }


    /*
        Getters
     */
    public int getHealth() {
        return health;
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    public int getDodge() {
        return dodge;
    }

    public int getStealth() {
        return stealth;
    }
}
