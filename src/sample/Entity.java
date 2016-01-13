package sample;

public class Entity {

    protected int hp;
    protected int moveSpeed;
    protected int flee;

    public Entity(){

    }


    /*
        Getters
     */
    public int getPv() {
        return hp;
    }

    public int getSpeed() {
        return moveSpeed;
    }
}
