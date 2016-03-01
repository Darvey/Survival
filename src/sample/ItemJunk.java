package sample;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.newdawn.slick.SlickException;

/**
 * Created by Administrateur on 24/01/2016.
 */



public class ItemJunk extends Item {

    protected String descriptionAlt;        // description alternative quand on est trop bête
    protected int intelligenceNeeded;       // intelligence nécessaire pour avoir la bonne description

    public ItemJunk(String name, Tile tile) throws SlickException{

        super(name, tile);

        this.type = "junk";
    }
}
