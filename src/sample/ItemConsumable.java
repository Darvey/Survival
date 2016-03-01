package sample;


import org.newdawn.slick.SlickException;




public class ItemConsumable extends Item {



    protected String descriptionAlt;        // description alternative quand on est trop bête
    protected int intelligenceNeeded;       // intelligence nécessaire pour avoir la bonne description



    public ItemConsumable(String name, Tile tile) throws SlickException{

        super(name, tile);

        this.type = "consumable";
    }
}
